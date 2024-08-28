using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL.Models
{
    public class Game
    {
        public Guid Id { get; set; }
        public bool Status { get; set; }   
        public virtual List <Deck> Decks { get; set; }
    }
}
