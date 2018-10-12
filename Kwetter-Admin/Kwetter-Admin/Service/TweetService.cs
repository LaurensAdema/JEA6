using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.Extensions.Configuration;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class TweetService : BaseService<Tweet>, ITweetService
    {
        public TweetService(IConfiguration configuration, Uri baseEndpoint, Token token = null) : base(configuration, baseEndpoint, token)
        {
        }
    }
}
