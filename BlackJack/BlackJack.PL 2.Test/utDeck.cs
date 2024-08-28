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
    public class utDeck : utBase
    {
        //protected PL.Data.BlackJackEntities rb;
        //protected IDbContextTransaction transaction;


        [TestMethod]
        public void LoadTest()
        {
            int expected = 3;
            int actual = 0;

            var rows = rb.tblDecks;
            actual = rows.Count();
            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void LoadByIDTest()
        {
            tblDeck row = (from r in rb.tblDecks where r.Id == Guid.Parse("5c4e34f3-9b9d-4f47-ac15-16cd396b7dc0") select r).FirstOrDefault();


            if (row != null)
            {
                Assert.IsNotNull(row);
            }
        }

        [TestMethod]
        public void InsertTest()
        {
            tblGame rows = (from r in rb.tblGames
                            select r).FirstOrDefault();

            tblDeck row = new tblDeck();

            row.Id = Guid.NewGuid();
            row.GameId = rows.Id;

            rb.tblDecks.Add(row);
            int results = rb.SaveChanges();

            Assert.IsTrue(results == 1);
        }

        [TestMethod]
        public void UpdateTest()
        {
            tblGame rows = (from r in rb.tblGames
                            select r).FirstOrDefault();

            tblDeck row = (from r in rb.tblDecks join g in rb.tblGames on r.GameId equals g.Id
                           where r.GameId != rows.Id select r).FirstOrDefault();



            if (row != null)
            {
                row.GameId = rows.Id;
                

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }

        [TestMethod]
        public void DeleteTest()
        {
            tblGame rows = (from r in rb.tblGames
                            select r).FirstOrDefault();

            tblDeck row = (from r in rb.tblDecks
                           where r.GameId == rows.Id
                           select r).FirstOrDefault();

            if (row != null)
            {
                rb.tblDecks.Remove(row);

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }
    }
}