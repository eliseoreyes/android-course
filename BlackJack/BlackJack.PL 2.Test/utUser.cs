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
    public class utUser : utBase
    {
        //protected PL.Data.BlackJackEntities rb;
        //protected IDbContextTransaction transaction;


        [TestMethod]
        public void LoadTest()
        {
            int expected = 3;
            int actual = 0;

            var rows = rb.tblUsers;
            actual = rows.Count();
            Assert.AreEqual(expected, actual);
        }

        [TestMethod]
        public void LoadByIDTest()
        {
            tblUser row = (from r in rb.tblUsers where r.Id == Guid.Parse("233fbed5-201a-48f8-b911-38dd14bf0437") select r).FirstOrDefault();

            if (row != null)
            {
                Assert.IsNotNull(row);
            }
        }

        [TestMethod]
        public void LoadByNickNameTest()
        {
            var rows = (from r in rb.tblUsers
                        where r.Nickname == "Stef"
                        select r);

            int expected = 1;

            if (rows != null)
            {
                int actual = rows.Count();
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void LoadByUsernameTest()
        {
            var rows = (from r in rb.tblUsers
                        where r.Username == "Stefan"
                        select r);

            int expected = 1;

            if (rows != null)
            {
                int actual = rows.Count();
                Assert.AreEqual(expected, actual);
            }
        }

        [TestMethod]
        public void InsertTest()
        {

            tblGame games = (from r in rb.tblGames
                             select r).FirstOrDefault();

            tblUser row = new tblUser();

            row.Id = Guid.NewGuid();
            row.IsComputerPlayer = false;
            row.Username = "test";
            row.Password = "test";
            row.Nickname = "test";
            row.Wins = 37;
            row.GameId = games.Id;

            rb.tblUsers.Add(row);
            int results = rb.SaveChanges();

            Assert.IsTrue(results == 1);
        }

        [TestMethod]
        public void UpdateTest()
        {
            tblCard cards = (from r in rb.tblCards
                             select r).FirstOrDefault();
            tblUser row = (from r in rb.tblUsers select r).FirstOrDefault();

            if (row != null)
            {
                row.Username = "Test";

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }

        [TestMethod]
        public void DeleteTest()
        {
            tblCard cards = (from r in rb.tblCards
                             select r).FirstOrDefault();

            tblUser row = (from r in rb.tblUsers
                                 where r.Username == "Stefan"
                                 select r).FirstOrDefault();

            if (row != null)
            {
                rb.tblUsers.Remove(row);

                int results = rb.SaveChanges();
                Assert.AreNotEqual(0, results);
            }
        }
    }
}
