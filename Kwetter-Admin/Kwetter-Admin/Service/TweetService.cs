using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Models;

namespace ma.ade.Kwetter2.Admin.Service
{
    public class TweetService : BaseService<Tweet>
    {
        public TweetService(Uri baseEndpoint, Token token) : base(baseEndpoint, token)
        {
        }

        public IEnumerable<Tweet> GetTweets()
        {
            throw new NotImplementedException();
        }

        public Tweet GetTweet(long id)
        {
            throw new NotImplementedException();
        }
    }
}
