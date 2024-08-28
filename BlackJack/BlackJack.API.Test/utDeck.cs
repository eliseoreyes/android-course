using BlackJack.BL.Models;
using DocumentFormat.OpenXml.Wordprocessing;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.API.Test
{
    [TestClass]
    public class utDeck : utBase
    {
        [TestMethod]
        public async Task LoadTestAsync()
        {
            await base.LoadTestAsync<Deck>();
        }

        [TestMethod]
        public async Task InsertTestAsync()
        {
            Deck Deck = new Deck { Id = Guid.NewGuid() };
            await base.InsertTestAsync<Deck>(Deck);

        }

        //[TestMethod]
        //public async Task DeleteTestAsync()
        //{
        //    await base.DeleteTestAsync<Deck>(new KeyValuePair<string, string>("Id", "b55169a2-08f0-43dc-a323-d03f32bd8741"));
        //}

        //[TestMethod]
        //public async Task LoadByIdTestAsync()
        //{
        //    Deck deck = new Deck { Id = Guid.NewGuid() };
        //    await base.LoadByIdTestAsync<Deck>(new KeyValuePair<string, string>("Id", "b55169a2-08f0-43dc-a323-d03f32bd8741"));
        //}

        //[TestMethod]
        //public async Task UpdateTestAsync()
        //{
        //    Deck Deck = new Deck {GameId = Guid.Parse("2bd8d1f6-add2-4837-a499-1366b6e7a1cd"), Id=Guid.Parse("b55169a2-08f0-43dc-a323-d03f32bd8741") };
        //    await base.UpdateTestAsync<Deck>(new KeyValuePair<string, string>("Id", "b55169a2-08f0-43dc-a323-d03f32bd8741"), Deck);

        //}

    }
}
