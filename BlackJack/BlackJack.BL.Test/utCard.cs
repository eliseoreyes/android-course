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
    public class utCard : utBase
    {
        [TestMethod]
        public void LoadTest()
        {
            List<Card> cards = new CardManager(options).Load();
            int expected = 52;

            Assert.AreEqual(expected, cards.Count);
        }

        [TestMethod]
        public void InsertTest()
        {
            Card card = new Card
            {
                Suit = "XXXXX",
                Rank = "XXXXX",
                Value = 2,
                CardImg = "not implementing yet",
                DeckId = new DeckManager(options).Load().FirstOrDefault().Id
            };

            int result = new CardManager(options).Insert(card, true);
            Assert.IsTrue(result > 0);
        }

        [TestMethod]
        public void UpdateTest()
        {
            Card card = new CardManager(options).Load().FirstOrDefault();
            card.Suit = "Blah blah";

            Assert.IsTrue(new CardManager(options).Update(card, true) > 0);
        }

        [TestMethod]
        public void DeleteTest()
        {
            Card card = new CardManager(options).Load().FirstOrDefault();

            Assert.IsTrue(new CardManager(options).Delete(card.Id, true) > 0);
        }

        [TestMethod]
        public void LoadByIdTest()
        {
            Card card = new CardManager(options).Load().FirstOrDefault();
            Assert.AreEqual(new CardManager(options).LoadById(card.Id).Id, card.Id);
        }


    }
}
