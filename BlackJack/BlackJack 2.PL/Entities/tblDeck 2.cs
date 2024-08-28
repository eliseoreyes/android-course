using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Entities
{
    public class tblDeck : IEntity
    {
        public Guid Id { get; set; }
        public Guid GameId { get; set; }
        public virtual tblGame Game { get; set; }
        public virtual ICollection<tblCard> tblCards { get; set; }
        public string SortField
        {
            get { return Id.ToString(); }
        }
    }
}
