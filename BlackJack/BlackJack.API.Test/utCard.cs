using BlackJack.BL;
using BlackJack.BL.Models;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.API.Test
{
    [TestClass]
    public class utCard : utBase
    {
        [TestMethod]
        public async Task LoadTestAsync()
        {
            await base.LoadTestAsync<Card>();
        }

        [TestMethod]
        public async Task InsertTestAsync()
        {
            Card Card = new Card { Rank = "Test", Suit = "Test" };
            await base.InsertTestAsync<Card>(Card);

        }

        [TestMethod]
        public async Task DeleteTestAsync()
        {
            await base.DeleteTestAsync<Card>(new KeyValuePair<string, string>("Rank", "King"));
        }

        [TestMethod]
        public async Task LoadByIdTestAsync()
        {
            await base.LoadByIdTestAsync<Card>(new KeyValuePair<string, string>("Rank", "King"));
        }

        //[TestMethod]
        //public async Task UpdateTestAsync()
        //{
        //    Card Card = new Card { Suit = "Test", Rank = "Test", CardImg = "" };
        //    await base.UpdateTestAsync<Card>(new KeyValuePair<string, string>("Rank", "Queen"), Card);

        //}

    }
}

