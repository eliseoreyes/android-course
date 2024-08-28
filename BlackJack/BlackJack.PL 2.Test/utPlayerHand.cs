using BlackJack.PL.Entities;
using Microsoft.EntityFrameworkCore.Storage;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Test
{
    [TestClass]
    public class utPlayerHand : utBase
    {
        //protected PL.Data.BlackJackEntities rb;
        //protected IDbContextTransaction transaction;


        [TestMethod]
        public void LoadTest()
        {
            int expected = 3;
            int actual = 0;

            var rows = rb.tblPlayerHands;
            actual = rows.Count();
            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void LoadByIDTest()
        {
            tblPlayerHand row = (from r in rb.tblPlayerHands where r.Id == Guid.Parse("dabb6425-4cd5-4f1b-843e-1b1a129269e3") select r).FirstOrDefault();

            if (row != null)
            {
                Assert.IsNotNull(row);
            }
        }

        [TestMethod]
        public void InsertTest()
        {
            tblUser rows = (from r in rb.tblUsers
                            select r).FirstOrDefault();

            tblCard cards = (from r in rb.tblCards
                            select r).FirstOrDefault();

            tblPlayerHand row = new tblPlayerHand();

            row.Id = Guid.NewGuid();
            row.UserId = rows.Id;
            row.CardId = cards.Id;

            rb.tblPlayerHands.Add(row);
            int results = rb.SaveChanges();

            Assert.IsTrue(results == 1);
        }

        [TestMethod]
        public void UpdateTest()
        {
            tblCard cards = (from r in rb.tblCards
                             select r).FirstOrDefault();
            tblPlayerHand row = (from r in rb.tblPlayerHands select r).FirstOrDefault();

            if (row != null)
            {
                row.CardId = cards.Id;

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }

        [TestMethod]
        public void DeleteTest()
        {
            tblCard cards = (from r in rb.tblCards
                             select r).FirstOrDefault();

            tblPlayerHand row = (from r in rb.tblPlayerHands
                           where r.CardId == cards.Id
                           select r).FirstOrDefault();

            if (row != null)
            {
                rb.tblPlayerHands.Remove(row);

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }
    }
}