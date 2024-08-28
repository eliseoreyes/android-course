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
    public class utGame : utBase
    {
        //protected PL.Data.BlackJackEntities rb;
        //protected IDbContextTransaction transaction;


        [TestMethod]
        public void LoadTest()
        {
            int expected = 3;
            int actual = 0;

            var rows = rb.tblGames;
            actual = rows.Count();
            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void LoadByIDTest()
        {
            tblGame row = (from r in rb.tblGames where r.Id == Guid.Parse("2f182d5b-212e-4e5c-8a05-46ad04c3fea2") select r).FirstOrDefault();


            if (row != null)
            {
                Assert.IsNotNull(row);
            }
        }

        [TestMethod]
        public void LoadByStatusTest()
        {
            var rows = (from r in rb.tblGames
                        where r.Status == true
                        select r);

            int expected = 2;

            if (rows != null)
            {
                int actual = rows.Count();
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void InsertTest()
        {
            tblGame rows = (from r in rb.tblGames
                            select r).FirstOrDefault();

            tblGame row = new tblGame();

            row.Id = Guid.NewGuid();
            row.Status = false;

            rb.tblGames.Add(row);
            int results = rb.SaveChanges();

            Assert.IsTrue(results == 1);
        }

        [TestMethod]
        public void UpdateTest()
        {
            tblGame row = (from r in rb.tblGames select r).FirstOrDefault();

            

            if (row != null)
            {
                row.Status = true;


                int results = rb.SaveChanges();
                Assert.AreEqual(1, results);
            }
        }

        [TestMethod]
        public void DeleteTest()
        {
            

            tblGame row = (from r in rb.tblGames
                           select r).FirstOrDefault();

            if (row != null)
            {
                rb.tblGames.Remove(row);

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }
    }
}