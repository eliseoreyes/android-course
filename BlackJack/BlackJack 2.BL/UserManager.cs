using BlackJack.BL.Models;
using BlackJack.PL.Data;
using BlackJack.PL.Entities;
using BlackJack.Reporting;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Mail;
using System.Reflection.Metadata;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL
{
    public class LoginFailureException : Exception
    {
        public LoginFailureException() : base("Cannot log in with these credentials.")
        {
        }

        public LoginFailureException(string message) : base(message)
        {
        }
    }

    public class UserManager : GenericManager<tblUser>
    {
        public UserManager(DbContextOptions<BlackJackEntities> options) : base(options)
        {
        }
        //private static DbContextOptions<BlackJackEntities> _options { get; set; }

        //public static void SetOptions(DbContextOptions<BlackJackEntities> options)
        //{
        //    _options = options;
        //}

        //private string GetHash(string Password)
        //{
        //    using (var hasher = new System.Security.Cryptography.SHA1Managed())
        //    {
        //        var hashbytes = System.Text.Encoding.UTF8.GetBytes(Password);
        //        return Convert.ToBase64String(hasher.ComputeHash(hashbytes));
        //    }
        //}

        private static string GetHash(string password)
        {
            using (var hasher = SHA256.Create())
            {
                var hashbytes = Encoding.UTF8.GetBytes(password);
                return Convert.ToBase64String(hasher.ComputeHash(hashbytes));
            }
        }

        public static void Export(List<User> users)
        {
            try
            {
                string[,] data = new string[users.Count + 1, 3];
                int counter = 0;

                data[counter, 0] = "Username";
                data[counter, 1] = "Nickname";
                data[counter, 2] = "Wins";

                counter++;
                foreach (User user in users)
                {
                    data[counter, 0] = user.Username;
                    data[counter, 1] = user.Nickname;
                    data[counter, 2] = user.Wins.ToString();
                    counter++;
                }

                Excel.Export("Users.xlsx", data);

            }
            catch (Exception)
            {

                throw;
            }
        }

        public void Hit(User user)
        {
            //  Will Handle if hand is over 21 already in controller
            var random = new Random();
            using (BlackJackEntities bj = new BlackJackEntities(options))
            {
                tblCard row = bj.tblCards.Where(c => bj.tblPlayerHands.Any(ph => ph.CardId == c.Id)).OrderBy(c => random.Next()).FirstOrDefault();

                tblPlayerHand newCard = new tblPlayerHand();
                newCard.Id = Guid.NewGuid();
                newCard.CardId = row.Id;
                newCard.UserId = user.Id;

                PlayerHandManager playerHandManager = new PlayerHandManager(options);
                playerHandManager.CalculateHandScore(user);  //Need to fix

                if (user.Score >= 21)
                {
                    if (user.IsComputerPlayer)
                        DetermineGameOutcome();
                    else
                    {
                        GameManager gameManager = new GameManager(options);
                        gameManager.EndTurn(user);
                    }
                        
                }
            }       
        }

        public void ComputerPlayerMove(User user)
        {
            while(user.Score <=15)
            {
                Hit(user);
            }

            DetermineGameOutcome();

        }

        public List<tblPlayerHand> Deal(bool rollback = false)
        {
            try
            {
                List<tblPlayerHand> dealtHands = new List<tblPlayerHand>();
                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    IDbContextTransaction dbTransaction = null;
                    if (rollback) dbTransaction = dc.Database.BeginTransaction();

                    // Retrieve all users
                    var users = dc.tblUsers.Where(c => dc.tblGames.Any(ph => ph.Id == c.GameId)).ToList();

                    // Retrieve all cards
                    var cards = dc.tblCards
                        .Where(c => dc.tblPlayerHands.Any(ph => ph.CardId == c.Id))
                        .ToList();

                    var random = new Random();

                    foreach (var user in users)
                    {
                        // Shuffle the cards
                        var shuffledCards = cards.OrderBy(c => random.Next()).ToList();

                        // Give First Card to playerhand
                        tblPlayerHand firstCard = new tblPlayerHand();
                        firstCard.Id = Guid.NewGuid();
                        firstCard.CardId = shuffledCards.First().Id; // Get the first card after shuffling
                        firstCard.UserId = user.Id;

                        // Add dealt hand to the list
                        dc.tblPlayerHands.Add(firstCard);
                        dealtHands.Add(firstCard);

                        // Give second card to playerhand
                        tblPlayerHand secondCard = new tblPlayerHand();
                        secondCard.Id = Guid.NewGuid();
                        secondCard.CardId = shuffledCards.Skip(1).First().Id; // Get the second card after shuffling
                        secondCard.UserId = user.Id;

                        // Add dealt hand to the list
                        dc.tblPlayerHands.Add(secondCard);
                        dealtHands.Add(secondCard);
                    }

                    // Save the cards given to playerhand
                    //dc.SaveChanges();

                    if (rollback) dbTransaction.Rollback();

                }

                // Return the list of dealt hands
                return dealtHands;
            }
            catch (Exception)
            {

                throw;
            }
        }

        private void DetermineGameOutcome()
        {

            using (BlackJackEntities bj = new BlackJackEntities(options))
            {
                var users = bj.tblUsers.Where(c => bj.tblGames.Any(ph => ph.Id == c.GameId)).ToList(); // Retrieve all users

                foreach (var user in users)
                {
                    User usermodel = new User()
                    {
                        Id = user.Id,
                        Nickname = user.Nickname,
                        Username = user.Nickname,
                        Password = user.Password,
                        IsComputerPlayer = user.IsComputerPlayer,
                        Wins = user.Wins,
                        Score = user.Score,
                    };

                    Console.WriteLine($"User: {user.Nickname}");

                    //var userCards = bj.tblPlayerHands
                    //    .Where(ph => ph.UserId == user.Id)
                    //    .Select(ph => ph.Cards)
                    //    .ToList(); // Retrieve cards for the current user

                    PlayerHandManager playerHandManager = new PlayerHandManager(options);
                    playerHandManager.CalculateHandScore(usermodel);  
                    var winner = users.OrderByDescending(u => u.Score).FirstOrDefault();
                    winner.Wins += 1;

                }
            }
        }

        public void Seed()
        {
            List<User> users = Load();

            foreach (User user in users)
            {
                if (user.Password.Length != 28)
                {
                    Update(user);
                }
            }

            //if (users.Count == 0)
            //{
            //    // Hardcord a couple of users with hashed passwords
            //Insert(new User { Username = "bfoote", FirstName = "Brian", LastName = "Foote", UserPass = "maple" });
            //    Insert(new User { Username = "kvicchiollo", FirstName = "Ken", LastName = "Vicchiollo", Password = "password" });
            //}
        }

        public bool Login(User user)
        {
            try
            {
                if (!string.IsNullOrEmpty(user.Username))
                {
                    if (!string.IsNullOrEmpty(user.Password))
                    {
                        using (BlackJackEntities dc = new BlackJackEntities(options))
                        {
                            tblUser userrow = dc.tblUsers.FirstOrDefault(u => u.Username == user.Username);

                            if (userrow != null)
                            {
                                // check the password
                                if (userrow.Password == GetHash(user.Password))
                                {
                                    // Login was successfull
                                    user.Id = userrow.Id;
                                    user.Username = userrow.Username;
                                    user.Nickname = userrow.Nickname;
                                    user.Wins = userrow.Wins;
                                    user.Score = userrow.Score;
                                    user.Password = userrow.Password;
                                    return true;
                                }
                                else
                                {
                                    throw new LoginFailureException("Cannot log in with these credentials.  Your IP address has been saved.");
                                }
                            }
                            else
                            {
                                throw new Exception("User could not be found.");
                            }
                        }
                    }
                    else
                    {
                        throw new Exception("Password was not set.");
                    }
                }
                else
                {
                    throw new Exception("User Name was not set.");
                }
            }
            catch (Exception)
            {
                throw;
            }
        }
        public List<User> Load()
        {
            try
            {
                List<User> users = new List<User>();

                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    dc.tblUsers
                        .ToList()
                        .ForEach(u => users
                        .Add(new User
                        {
                            Id = u.Id,
                            Nickname = u.Nickname,
                            Wins = u.Wins,
                            Username = u.Username,
                            Password = u.Password,
                            IsComputerPlayer = u.IsComputerPlayer,
                        }));
                }

                return users;
            }
            catch (Exception)
            {
                throw;
            }
        }

        public User LoadById(Guid id)
        {
            try
            {
                User user = new User();

                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    user = (from u in dc.tblUsers
                            where u.Id == id
                            select new User
                            {
                                Id = u.Id,
                                Nickname = u.Nickname,
                                Wins = u.Wins,
                                Username = u.Username,
                                Password = u.Password,
                                IsComputerPlayer = u.IsComputerPlayer,
                            }).FirstOrDefault();
                }

                return user;
            }
            catch (Exception)
            {
                throw;
            }
        }

        public int Insert(User user, bool rollback = false)
        {
            try
            {
                int results = 0;
                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    // Check if username already exists - do not allow ....
                    bool inuse = dc.tblUsers.Any(u => u.Username.Trim().ToUpper() == user.Username.Trim().ToUpper());

                    if (inuse && rollback == false)
                    {
                        //throw new Exception("This User Name already exists.");
                    }
                    else
                    {
                        IDbContextTransaction transaction = null;
                        if (rollback) transaction = dc.Database.BeginTransaction();

                        tblUser newUser = new tblUser();

                        newUser.Id = Guid.NewGuid();
                        newUser.Nickname = user.Nickname.Trim();
                        newUser.Wins = user.Wins;
                        newUser.Username = user.Username.Trim();
                        newUser.Password = GetHash(user.Password.Trim());
                        newUser.IsComputerPlayer = user.IsComputerPlayer;

                        user.Id = newUser.Id;

                        dc.tblUsers.Add(newUser);

                        results = dc.SaveChanges();
                        if (rollback) transaction.Rollback();
                    }
                }
                return results;
            }
            catch (Exception)
            {
                throw;
            }
        }


        public int Update(User user, bool rollback = false)
        {
            try
            {
                int results = 0;

                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    // Check if username already exists - do not allow ....
                    tblUser existingUser = dc.tblUsers.Where(u => u.Username.Trim().ToUpper() == user.Username.Trim().ToUpper()).FirstOrDefault();

                    if (existingUser != null && existingUser.Id != user.Id && rollback == false)
                    {
                        throw new Exception("This User Name already exists.");
                    }
                    else
                    {
                        IDbContextTransaction transaction = null;
                        if (rollback) transaction = dc.Database.BeginTransaction();

                        tblUser upDateRow = dc.tblUsers.FirstOrDefault(r => r.Id == user.Id);

                        if (upDateRow != null)
                        {
                            upDateRow.Nickname = user.Nickname.Trim();
                            upDateRow.Wins = user.Wins;
                            upDateRow.Username = user.Username.Trim();
                            upDateRow.Password = GetHash(user.Password.Trim());

                            dc.tblUsers.Update(upDateRow);

                            // Commit the changes and get the number of rows affected
                            results = dc.SaveChanges();

                            if (rollback) transaction.Rollback();
                        }
                        else
                        {
                            throw new Exception("Row was not found.");
                        }
                    }
                }
                return results;
            }
            catch (Exception)
            {
                throw;
            }
        }


        public int Delete(Guid id, bool rollback = false)
        {
            try
            {
                int results = 0;

                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    // Check if user is associated with an exisiting order - do not allow delete ....
                    bool inuse = dc.tblUsers.Any(o => o.GameId != null);

                    if (inuse)
                    {
                        throw new Exception("This user is associated with an existing game and therefore cannot be deleted.");
                    }
                    else
                    {
                        IDbContextTransaction transaction = null;
                        if (rollback) transaction = dc.Database.BeginTransaction();

                        tblUser deleteRow = dc.tblUsers.FirstOrDefault(r => r.Id == id);

                        if (deleteRow != null)
                        {
                            dc.tblUsers.Remove(deleteRow);

                            // Commit the changes and get the number of rows affected
                            results = dc.SaveChanges();

                            if (rollback) transaction.Rollback();
                        }
                        else
                        {
                            throw new Exception("Row was not found.");
                        }
                    }
                }
                return results;
            }
            catch (Exception)
            {
                throw;
            }
        }
    }
}
