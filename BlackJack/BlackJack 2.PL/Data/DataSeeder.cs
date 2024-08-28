using BlackJack.PL.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Data
{
    public static class DataSeeder
    {
        private static string GetHash(string password)
        {
            using (var hasher = SHA256.Create())
            {
                var hashbytes = Encoding.UTF8.GetBytes(password);
                return Convert.ToBase64String(hasher.ComputeHash(hashbytes));
            }
        }

        private static ModelBuilder mb;

        #region Entity Lists
        private static List<tblGame> Games = new List<tblGame>() {
            new tblGame() {
                Status=true,
                Id=Guid.NewGuid(),
            },
            new tblGame() {
                Status=false,
                Id=Guid.NewGuid(),
            },
            new tblGame() {
                Status=true,
                Id=Guid.NewGuid(),
            },
        };

        private static List<tblUser> Users = new List<tblUser>() {
            new tblUser() {
                Id = Guid.NewGuid(),
                Nickname = "Stef",
                Username = "Stefan",
                Password = GetHash("maple"),
                IsComputerPlayer = false,
                Wins = 37,
                Score = 12,
                GameId = Games[0].Id
            },
            new tblUser() {
                Id = Guid.NewGuid(),
                Nickname = "Zach",
                Username = "Zachary",
                Password = GetHash("justinbieberrocks"),
                IsComputerPlayer = false,
                Score = 13,
                Wins = 1,
                GameId = Games[0].Id
            },
            new tblUser() {
                Id = Guid.NewGuid(),
                Nickname = "Andy",
                Username = "Anthony",
                Password = GetHash("rebeccapurple"),
                IsComputerPlayer = false,
                Wins = 14,
                Score = 15,
                GameId = null
            },
        };

        private static List<tblDeck> Decks = new List<tblDeck>() {
            new tblDeck() {
                GameId = Games[0].Id,
                Id = Guid.NewGuid(),
            },
            new tblDeck() {
                GameId = Games[1].Id,
                Id = Guid.NewGuid(),
            },
            new tblDeck() {
                GameId = Games[2].Id,
                Id = Guid.NewGuid(),
            },
        };
        
        //  *****FINISH SEEDING ALL CARDS*****

        private static List<tblCard> Cards = new List<tblCard>() {
            #region hearts
            new tblCard() {
                Suit = "Hearts",
                Rank = "Ace",
                Value = 1,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "2",
                Value = 2,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "3",
                Value = 3,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "4",
                Value = 4,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "5",
                Value = 5,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "6",
                Value = 6,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "7",
                Value = 7,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "8",
                Value = 8,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "9",
                Value = 9,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "10",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "Jack",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "Queen",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Hearts",
                Rank = "King",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
#endregion
            #region Spades
            new tblCard() {
                Suit = "Spades",
                Rank = "Ace",
                Value = 1,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "2",
                Value = 2,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "3",
                Value = 3,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "4",
                Value = 4,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "5",
                Value = 5,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "6",
                Value = 6,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "7",
                Value = 7,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "8",
                Value = 8,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "9",
                Value = 9,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "10",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "Jack",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "Queen",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Spades",
                Rank = "King",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
#endregion
            #region Diamonds
            new tblCard() {
                Suit = "Diamonds",
                Rank = "Ace",
                Value = 1,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "2",
                Value = 2,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "3",
                Value = 3,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "4",
                Value = 4,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "5",
                Value = 5,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "6",
                Value = 6,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "7",
                Value = 7,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "8",
                Value = 8,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "9",
                Value = 9,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "10",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "Jack",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "Queen",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Diamonds",
                Rank = "King",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
#endregion
            #region Clubs
            new tblCard() {
                Suit = "Clubs",
                Rank = "Ace",
                Value = 1,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "2",
                Value = 2,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "3",
                Value = 3,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "4",
                Value = 4,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "5",
                Value = 5,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "6",
                Value = 6,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "7",
                Value = 7,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "8",
                Value = 8,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "9",
                Value = 9,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "10",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "Jack",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "Queen",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
            new tblCard() {
                Suit = "Clubs",
                Rank = "King",
                Value = 10,
                Id = Guid.NewGuid(),
                CardIMG = "",
                DeckId = Decks[0].Id,
            },
#endregion
        };

        private static List<tblPlayerHand> PlayerHands = new List<tblPlayerHand>
        {
            new tblPlayerHand
            {
                Id = Guid.NewGuid(),
                UserId = Users[0].Id,
                CardId =Cards[0].Id,
            },
            new tblPlayerHand
            {
                Id = Guid.NewGuid(),
                UserId = Users[0].Id,
                CardId =Cards[4].Id,
            },
            new tblPlayerHand
            {
                Id = Guid.NewGuid(),
                UserId = Users[1].Id,
                CardId =Cards[4].Id,
            },
            new tblPlayerHand
            {
                Id = Guid.NewGuid(),
                UserId = Users[1].Id,
                CardId =Cards[2].Id,
            },
        };
        #endregion
        public static void SeedData(ModelBuilder modelBuilder)
        {
            mb = modelBuilder;

            // Data Seeding below
            FillData(Games);
            FillData(Users);
            FillData(Decks);
            FillData(Cards);
            FillData(PlayerHands);
        }

        /// <summary>
        /// Simple helper method to seed data
        /// </summary>
        /// <param name="list">The list of entities to seed</param>
        private static void FillData<T>(List<T> list) where T : class
        {
            mb.Entity<T>().HasData(list);
        }
    }
}