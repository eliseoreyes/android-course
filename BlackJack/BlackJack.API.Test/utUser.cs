using BlackJack.BL.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.API.Test
{
    [TestClass]
    public class utUser : utBase
    {
        [TestMethod]
        public async Task LoadTestAsync()
        {
            await base.LoadTestAsync<User>();
        }

        [TestMethod]
        public async Task InsertTestAsync()
        {
            User User = new User { Nickname = "Test", Username = "Test" };
            await base.InsertTestAsync<User>(User);

        }

        //[TestMethod]
        //public async Task DeleteTestAsync()
        //{
        //    await base.DeleteTestAsync<User>(new KeyValuePair<string, string>("Username", "Anthony"));
        //}

        [TestMethod]
        public async Task LoadByIdTestAsync()
        {
            await base.LoadByIdTestAsync<User>(new KeyValuePair<string, string>("Username", "Zachary"));
        }

        //[TestMethod]
        //public async Task UpdateTestAsync()
        //{
        //    User user = new User {Nickname = "Test", Username = "Test" };
        //    await base.UpdateTestAsync<User>(new KeyValuePair<string, string>("Username", "Zachary"), user);

        //}

    }
}
