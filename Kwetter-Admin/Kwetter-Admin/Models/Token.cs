using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Models
{
    [Serializable]
    public class Token
    {
        public string AccessToken { get; set; }
        public string SessionId { get; set; }
    }
}
