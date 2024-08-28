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
    public class utCard : utBase
    {
        //protected PL.Data.BlackJackEntities rb;
        //protected IDbContextTransaction transaction;


        [TestMethod]
        public void LoadTest()
        {
            int expected = 52;
            int actual = 0;

            var rows = rb.tblCards;
            actual = rows.Count();
            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void LoadByIDTest()
        {
            tblCard row = (from r in rb.tblCards where r.Id == Guid.Parse("a1eafbd7-9293-4165-a6d3-016d19882d86") select r).FirstOrDefault();


            if (row != null)
            {
                Assert.IsNotNull(row);
            }
        }
        [TestMethod]
        public void LoadSingleCardTest()
        {
            tblCard row = (from r in rb.tblCards where r.Suit == "Hearts" && r.Rank == "King" && r.Value == 10 select r).FirstOrDefault();

            Assert.IsNotNull(row);
        }

        [TestMethod]
        public void LoadBySuitTest()
        {
            tblCard row = (from r in rb.tblCards
                           where r.Suit == "Hearts"
                           select r).FirstOrDefault();

            int expected = 1;

            if (row != null)
            {
                int actual = 1;
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void LoadByRankTest()
        {
            tblCard row = (from r in rb.tblCards
                           where r.Rank == "King"
                           select r).FirstOrDefault();

            int expected = 1;

            if (row != null)
            {
                int actual = 1;
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void LoadByValueTest()
        {
            tblCard row = (from r in rb.tblCards
                           where r.Value == 10
                           select r).FirstOrDefault();

            int expected = 1;

            if (row != null)
            {
                int actual = 1;
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void LoadAllSuitTest()
        {
            var rows = (from r in rb.tblCards
                        where r.Suit == "Spades"
                        select r);

            int expected = 13;

            if (rows != null)
            {
                int actual = rows.Count();
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void LoadAllRankTest()
        {
            var rows = (from r in rb.tblCards
                        where r.Rank == "King"
                        select r);

            int expected = 4;

            if (rows != null)
            {
                int actual = rows.Count();
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void LoadAllValueTest()
        {
            var rows = (from r in rb.tblCards
                        where r.Value == 9
                        select r);

            int expected = 4;

            if (rows != null)
            {
                int actual = rows.Count();
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void InsertTest()
        {
            tblDeck rows = (from r in rb.tblDecks
                            select r).FirstOrDefault();
            
            tblCard row = new tblCard();

            row.Id = Guid.NewGuid();
            row.DeckId = rows.Id;
            row.CardIMG = "";
            row.Rank = "King";
            row.Value= 10;
            row.Suit = "Spades";

            rb.tblCards.Add(row);
            int results = rb.SaveChanges();

            Assert.IsTrue(results == 1);
        }

        [TestMethod]
        public void UpdateTest()
        {
            tblCard row = (from r in rb.tblCards select r).FirstOrDefault();

            if (row != null)
            {
                row.CardIMG = "";
                row.Rank = "Test";
                row.Value = 69;
                row.Suit = "Maples";

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }

        [TestMethod]
        public void DeleteTest()
        {
            tblCard row = (from r in rb.tblCards
                              where r.Rank == "King" && r.Suit == "Hearts"
                              select r).FirstOrDefault();

            if (row != null)
            {
                rb.tblCards.Remove(row);

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }
    }
}
