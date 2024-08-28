using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL.Models
{
    public class User
    {
        public Guid Id { get; set; }
        public string Nickname { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public bool IsComputerPlayer { get; set; }
        public int Wins { get; set; }
        public int Score { get; set; }
        public Guid GameId { get; set; }
        public Game Game { get; set; }

      //  public ICollection<PlayerHand> PlayerHands { get; }
    }
}
