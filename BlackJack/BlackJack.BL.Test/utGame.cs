using BlackJack.BL.Models;
using Castle.Core.Resource;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL.Test
{
    [TestClass]
    public class utGame : utBase
    {
        [TestMethod]
        public void LoadTest()
        {
            List<Game> games = new GameManager(options).Load();
            int expected = 3;

            Assert.AreEqual(expected, games.Count);
        }

        [TestMethod]
        public void InsertTest()
        {
            Game game = new Game
            {
                Status = true,
                //UserId = new UserManager(options).Load().FirstOrDefault().Id
            };

            int result = new GameManager(options).Insert(game, true);
            Assert.IsTrue(result > 0);
        }

        [TestMethod]
        public void UpdateTest()
        {
            Game game = new GameManager(options).Load().FirstOrDefault();
            game.Status = false;

            Assert.IsTrue(new GameManager(options).Update(game, true) > 0);
        }

        [TestMethod]
        public void DeleteTest()
        {
            Game game = new GameManager(options).Load().FirstOrDefault();

            Assert.IsTrue(new GameManager(options).Delete(game.Id, true) > 0);
        }

        [TestMethod]
        public void LoadByIdTest()
        {
            Game game = new GameManager(options).Load().FirstOrDefault();
            Assert.AreEqual(new GameManager(options).LoadById(game.Id).Id, game.Id);
        }


    }
}
