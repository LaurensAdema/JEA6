using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class AuthenticationService : BaseService<User>, IKwetterAuthenticationService
    {
        public AuthenticationService(IConfiguration configuration, IHttpContextAccessor httpContextAccessor, Uri baseEndpoint) : base(configuration, httpContextAccessor, baseEndpoint)
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

        public override Task<User> GetAsync(long id)
        {
            throw new NotSupportedException("Get endpoint is not supported");
        }

        public override Task<IEnumerable<User>> GetAllAsync()
        {
            throw new NotSupportedException("Get all endpoint is not supported");
        }

        public override Task<User> CreateAsync(User entity)
        {
            throw new NotSupportedException("Create endpoint is not supported");
        }

        public override Task UpdateAsync(User entity)
        {
            throw new NotSupportedException("Update endpoint is not supported");
        }

        public override Task DeleteAsync(long id)
        {
            throw new NotSupportedException("Delete endpoint is not supported");
        }
    }
}
