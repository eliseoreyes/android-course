using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Entities
{
    public class tblUser : IEntity
    {
        public Guid Id { get; set; }
        public Guid? GameId { get; set; }
        public virtual tblGame Game { get; set; }
        public string Nickname { get; set; }
        public string Username { get; set; } = null;
        public string Password { get; set; } = null;
        public bool IsComputerPlayer { get; set; }
        public int Wins { get; set; }
        public int Score { get; set; }
        public virtual ICollection<tblPlayerHand> tblPlayerHands { get; set; }
        public string SortField
        {
            get { return Nickname; }
        }
    }
}
