using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Entities
{
    public class tblCard : IEntity
    {
        public Guid Id { get; set; }
        public string Suit { get; set; }
        public string Rank { get; set; }
        public int Value { get; set; }
        public string CardIMG { get; set; }
        public Guid DeckId { get; set; }
        public virtual tblDeck Deck { get; set; }
        public string SortField
        {
            get { return Rank; }
        }
    }
}
