using BlackJack.BL.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.API.Test
{
    [TestClass]
    public class utGame : utBase
    {
        [TestMethod]
        public async Task LoadTestAsync()
        {
            await base.LoadTestAsync<Game>();
        }

        [TestMethod]
        public async Task InsertTestAsync()
        {
            Game Game = new Game { Status = false };
            await base.InsertTestAsync<Game>(Game);

        }

        [TestMethod]
        public async Task DeleteTestAsync()
        {
            await base.DeleteTestAsync<Game>(new KeyValuePair<string, string>("Status", "True"));
        }

        [TestMethod]
        public async Task LoadByIdTestAsync()
        {
            await base.LoadByIdTestAsync<Game>(new KeyValuePair<string, string>("Status", "True"));
        }

        //[TestMethod]
        //public async Task UpdateTestAsync()
        //{
        //    Game game = new Game { Status = false };
        //    await base.UpdateTestAsync<Game>(new KeyValuePair<string, string>("Status", "True"), game);

        //}

    }
}
