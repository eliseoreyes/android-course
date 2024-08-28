using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Entities
{
    public class tblGame : IEntity
    {
        public Guid Id { get; set; }
        public bool Status { get; set; }
        public virtual ICollection<tblUser> tblUsers { get; set; }
        public string SortField
        {
            get { return Status.ToString(); }
        }

    }
}
