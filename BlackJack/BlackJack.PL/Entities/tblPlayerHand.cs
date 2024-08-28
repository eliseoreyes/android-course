using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Entities
{
    public class tblPlayerHand : IEntity
    {
        public Guid Id { get; set; }
        public Guid UserId { get; set; }
        public Guid CardId { get; set; }
        public virtual tblCard Card { get; set; }
        public string SortField
        {
            get { return UserId.ToString(); }
        }
    }
}
