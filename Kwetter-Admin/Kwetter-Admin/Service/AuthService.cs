using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Models;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class AuthService : BaseService<User>
    {
        public AuthService(Uri baseEndpoint, Token token) : base(baseEndpoint, token)
        {
        }

        public Token Login(User user)
        {
            throw new NotImplementedException();
        }
    }
}
