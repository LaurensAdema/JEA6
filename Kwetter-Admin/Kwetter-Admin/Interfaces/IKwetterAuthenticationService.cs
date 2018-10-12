using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Models;

namespace ma.ade.Kwetter2.Admin.Interfaces
{
    public interface IKwetterAuthenticationService : IBaseService<User>
    {
        Task<Token> LoginAsync(User user);
    }
}
