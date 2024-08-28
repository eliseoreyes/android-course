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
    public class utDeck : utBase
    {
        [TestMethod]
        public void LoadTest()
        {
            List<Deck> decks = new DeckManager(options).Load();
            int expected = 3;

            Assert.AreEqual(expected, decks.Count);
        }

        [TestMethod]
        public void InsertTest()
        {
            Deck deck = new Deck
            {
                GameId = new GameManager(options).Load().LastOrDefault().Id
            };

            int result = new DeckManager(options).Insert(deck, true);
            Assert.IsTrue(result > 0);
        }

        [TestMethod]
        public void UpdateTest()
        {
            Deck deck = new DeckManager(options).Load().FirstOrDefault();
            Game game = new GameManager(options).Load().FirstOrDefault();
            deck.GameId = game.Id;

            Assert.IsTrue(new DeckManager(options).Update(deck, true) > 0);
        }

        [TestMethod]
        public void DeleteTest()
        {
            Deck deck = new DeckManager(options).Load().FirstOrDefault();

            Assert.IsTrue(new DeckManager(options).Delete(deck.Id, true) > 0);
        }

        [TestMethod]
        public void LoadByIdTest()
        {
            Deck deck = new DeckManager(options).Load().FirstOrDefault();
            Assert.AreEqual(new DeckManager(options).LoadById(deck.Id).Id, deck.Id);
        }


    }
}
