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
    public class utPlayerHand : utBase
    {
        [TestMethod]
        public void LoadTest()
        {
            List<PlayerHand> playerHands = new PlayerHandManager(options).Load();
            int expected = 3;

            Assert.AreEqual(expected, playerHands.Count);
        }

        [TestMethod]
        public void InsertTest()
        {
            PlayerHand playerHand = new PlayerHand
            {
                CardId = new CardManager(options).Load().FirstOrDefault().Id,
                UserId = new UserManager(options).Load().FirstOrDefault().Id
            };

            int result = new PlayerHandManager(options).Insert(playerHand, true);
            Assert.IsTrue(result > 0);
        }

        [TestMethod]
        public void UpdateTest()
        {
            PlayerHand playerHand = new PlayerHandManager(options).Load().FirstOrDefault();
            Card card = new CardManager(options).Load().FirstOrDefault();
            playerHand.CardId = card.Id;

            Assert.IsTrue(new PlayerHandManager(options).Update(playerHand, true) > 0);
        }

        [TestMethod]
        public void DeleteTest()
        {
            PlayerHand playerHand = new PlayerHandManager(options).Load().FirstOrDefault();

            Assert.IsTrue(new PlayerHandManager(options).Delete(playerHand.Id, true) > 0);
        }

        [TestMethod]
        public void LoadByIdTest()
        {
            PlayerHand playerHand = new PlayerHandManager(options).Load().FirstOrDefault();
            Assert.AreEqual(new PlayerHandManager(options).LoadById(playerHand.Id).Id, playerHand.Id);
        }


    }
}
