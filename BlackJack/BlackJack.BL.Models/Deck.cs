using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL.Models
{
    public class Deck
    {
        public Guid Id { get; set; }
        public Guid GameId { get; set; }
     //   public Guid CardId { get; set; } 
        public Game Game { get; set; }
        public List<Card> cards { get; set; }
    }
}
