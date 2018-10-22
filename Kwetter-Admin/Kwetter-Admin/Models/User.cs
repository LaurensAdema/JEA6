using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Models
{
    [Serializable]
    public class User
    {
        public long Id { get; set; }

        public string Email { get; set; }

        public string Password { get; set; }

        public Profile Profile { get; set; }

        public IEnumerable<User> Following { get; set; }

        public IEnumerable<User> Followers { get; set; }
        public IEnumerable<Token> Tokens { get; set; }
    }
}
