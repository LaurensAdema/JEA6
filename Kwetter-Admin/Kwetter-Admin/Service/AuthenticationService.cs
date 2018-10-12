using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.Extensions.Configuration;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class AuthenticationService : BaseService<User>, IKwetterAuthenticationService
    {
        public AuthenticationService(IConfiguration configuration, Uri baseEndpoint, Token token = null) : base(configuration, baseEndpoint, token)
        {
        }

        public async Task<Token> LoginAsync(User user)
        {
            try
            {
                return await base.PostAsync<Token, User>(base.CreateRequestUri(_configuration["api:authentication:login"]), user);
            }
            catch (Exception e)
            {
                Console.WriteLine(e);
                throw;
            }
        }

        public override Task<User> GetAsync(long id) => throw new NotSupportedException("Get endpoint is not supported");
        public override Task<IEnumerable<User>> GetAllAsync() => throw new NotSupportedException("Get all endpoint is not supported");
        public override Task<User> CreateAsync(User entity) => throw new NotSupportedException("Create endpoint is not supported");
        public override Task UpdateAsync(User entity) => throw new NotSupportedException("Update endpoint is not supported");
        public override Task DeleteAsync(long id) => throw new NotSupportedException("Delete endpoint is not supported");
    }
}
