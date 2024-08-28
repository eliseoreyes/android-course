using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL.Models
{
    public class PlayerHand
    {
        public Guid Id { get; set; }
        public Guid UserId { get; set; }
        public Guid CardId { get; set; }
        public User User { get; set; }
        public Card Card { get; set; }
        
        public List<Card> Cards { get; set;}
    }
}
