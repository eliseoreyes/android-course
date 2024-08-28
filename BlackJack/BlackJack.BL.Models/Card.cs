using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL.Models
{
    public class Card
    {
        public Guid Id { get; set; }
        public string Suit { get; set; }
        public string Rank { get; set; }
        public int Value { get; set; }
        public string CardImg { get; set; }
        public Guid DeckId { get; set; }
        public Deck Deck { get; set; }
    }
}
