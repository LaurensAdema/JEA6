using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using System;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class UserService : BaseService<User>, IUserService
    {
        public UserService(IConfiguration configuration, IHttpContextAccessor httpContextAccessor, Uri baseEndpoint) : base(configuration, httpContextAccessor, baseEndpoint)
        {
        }

        public async Task<User> GetMeAsync()
        {
            return await GetAsync<User>(CreateRequestUri(_configuration["api:user:me"]));
        }
    }
}
