using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.Extensions.Options;

namespace ma.ade.Kwetter2.Admin.Authentication
{
    public class KwetterAuthenticationPostConfigureOptions : IPostConfigureOptions<KwetterAuthenticationOptions>
    {
        public void PostConfigure(string name, KwetterAuthenticationOptions options)
        {
            if (string.IsNullOrEmpty(options.Realm))
            {
                throw new InvalidOperationException("Realm must be provided in options");
            }
        }
    }
}
