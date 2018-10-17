using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Configuration;
using System;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class TweetService : BaseService<Tweet>, ITweetService
    {
        public TweetService(IConfiguration configuration, IHttpContextAccessor httpContextAccessor, Uri baseEndpoint) : base(configuration, httpContextAccessor, baseEndpoint)
        {
        }
    }
}
