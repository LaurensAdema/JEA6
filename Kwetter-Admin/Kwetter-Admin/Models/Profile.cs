using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Models
{
    public class Profile
    {
        public long Id { get; set; }

        public string FirstName { get; set; }

        public string LastName { get; set; }

        public string Bio { get; set; }

        public string Location { get; set; }

        public string Website { get; set; }

        public string PictureLocation { get; set; }
    }
}
