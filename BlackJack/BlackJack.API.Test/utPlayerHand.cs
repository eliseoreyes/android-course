using BlackJack.BL.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.API.Test
{
    [TestClass]
    public class utPlayerHand : utBase
    {
        [TestMethod]
        public async Task LoadTestAsync()
        {
            await base.LoadTestAsync<PlayerHand>();
        }

        [TestMethod]
        public async Task InsertTestAsync()
        {
            PlayerHand PlayerHand = new PlayerHand { Id = Guid.NewGuid() };
            await base.InsertTestAsync<PlayerHand>(PlayerHand);

        }

        //[TestMethod]
        //public async Task DeleteTestAsync()
        //{
        //    await base.DeleteTestAsync<PlayerHand>(new KeyValuePair<string, string>("Id", "3fbc4be2-b735-415a-8d74-80397ea37f1d"));
        //}

        //[TestMethod]
        //public async Task LoadByIdTestAsync()
        //{
        //    await base.LoadByIdTestAsync<PlayerHand>(new KeyValuePair<string, string>("Id", "3fbc4be2-b735-415a-8d74-80397ea37f1d"));
        //}

        //[TestMethod]
        //public async Task UpdateTestAsync()
        //{
        //    PlayerHand playerHand = new PlayerHand { CardId = Guid.Parse("becd53b3-e86d-411a-a202-0bbc718e05dd") };
        //    await base.UpdateTestAsync<PlayerHand>(new KeyValuePair<string, string>("Id", "3fbc4be2-b735-415a-8d74-80397ea37f1d"), playerHand);

        //}

    }
}