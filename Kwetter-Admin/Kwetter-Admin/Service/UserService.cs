using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Models;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class UserService : BaseService<User>
    {
        public UserService(Uri baseEndpoint, Token token) : base(baseEndpoint, token)
        {
        }

        public IEnumerable<User> GetUsers()
        {
            throw new NotImplementedException();
        }

        public User GetUser(long id)
        {
            throw new NotImplementedException();
        }
    }
}
