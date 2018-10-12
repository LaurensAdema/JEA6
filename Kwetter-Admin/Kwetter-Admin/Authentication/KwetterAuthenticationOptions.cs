using Microsoft.AspNetCore.Authentication;

namespace ma.ade.Kwetter2.Admin.Authentication
{
    public class KwetterAuthenticationOptions : AuthenticationSchemeOptions
    {
        public string Realm { get; set; }
    }
}
