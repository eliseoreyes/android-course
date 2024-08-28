using BlackJack.BL.Models;
using BlackJack.PL.Entities;
using Castle.Core.Resource;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL.Test
{
    [TestClass]
    public class utUser : utBase
    {


        [TestInitialize]
        public void Initialize()
        {
            //new UserManager(options).Seed();
        }

        [TestMethod]
        public void ExportTest()
        {
            List<User> users = new UserManager(options).Load();
            int expected = 3;

            UserManager.Export(users);
            Assert.AreEqual(expected, users.Count);
        }

        [TestMethod]
        public void LoadTest()
        {
            List<User> users = new UserManager(options).Load();
            Assert.IsTrue(users.Count > 0);
        }

        [TestMethod]
        public void DealTest()
        {
            List<User> users = new UserManager(options).Load();
            new UserManager(options).Deal();
            List<tblPlayerHand> playerHands = new UserManager(options).Deal();
            int expected = 6;
            Assert.AreEqual(expected, playerHands.Count);
        }

        [TestMethod]
        public void InsertTest()
        {
            User user = new User
            {
                Username = "Bill",
                Nickname = "Smith",
                Password = "1234"
            };
            int result = new UserManager(options).Insert(user, true);
            Assert.IsTrue(result > 0);
        }

        [TestMethod]
        public void LoginSuccess()
        {
            Game game = new GameManager(options).Load().FirstOrDefault();
            User user = new User {Nickname = "Stef", Username = "Stefan", Password = "maple", Wins = 37, Score = 12, GameId = game.Id };
            //new UserManager(options).Insert(user, true);
            bool result = new UserManager(options).Login(user);
            Assert.IsTrue(result);
        }

        [TestMethod]
        public void LoginFail()
        {
            try
            {
                Game game = new GameManager(options).Load().FirstOrDefault();
                User user = new User { Id = Guid.NewGuid(), Nickname = "Stef", Username = "Stefan", Password = "xxx", Wins = 37, Score = 12, GameId = game.Id, IsComputerPlayer = false };
                new UserManager(options).Insert(user, true);
                user.Password = "xxxx";
                new UserManager(options).Login(user);
                Assert.Fail();
            }
            catch (LoginFailureException)
            {
                Assert.IsTrue(true);
            }
            catch (Exception)
            {
                Assert.Fail();
            }
        }



    }
}
