using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Models
{
    public class Flag
    {
        public long Id { get; set; }
        public DateTime Date { get; set; }
        public string Reason { get; set; }
        public  User Flagger { get; set; }
    }
}
