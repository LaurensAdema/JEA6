using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.Extensions.Configuration;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class UserService : BaseService<User>, IUserService
    {
        public UserService(IConfiguration configuration, Uri baseEndpoint, Token token = null) : base(configuration, baseEndpoint, token)
        {
        }
    }
}
