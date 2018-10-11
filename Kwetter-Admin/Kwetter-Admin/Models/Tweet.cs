using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Models
{
    public class Tweet
    {
        public long Id { get; set; }

        public string Message { get; set; }

        public User User { get; set; }

        public DateTime Date { get; set; }

        public IEnumerable<Flag> Flags { get; set; }

        public IEnumerable<User> Likes { get; set; }
    }
}
