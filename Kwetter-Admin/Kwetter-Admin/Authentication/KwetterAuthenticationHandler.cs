using ma.ade.Kwetter2.Admin.Extensions;
using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using System;
using System.Globalization;
using System.Net.Http;
using System.Security.Claims;
using System.Text.Encodings.Web;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Authentication
{
    public class KwetterAuthenticationHandler : AuthenticationHandler<KwetterAuthenticationOptions>
    {

        //private const string AuthorizationHeaderName = "Authorization";
        //private const string KwetterSchemeName = "Kwetter";
        private readonly IKwetterAuthenticationService _authenticationService;

        public KwetterAuthenticationHandler(
            IOptionsMonitor<KwetterAuthenticationOptions> options,
            ILoggerFactory logger,
            UrlEncoder encoder,
            ISystemClock clock,
            IKwetterAuthenticationService authenticationService)
            : base(options, logger, encoder, clock)
        {
            _authenticationService = authenticationService;
        }

        protected override async Task<AuthenticateResult> HandleAuthenticateAsync()
        {
            Token storedToken;
            if (Request?.HttpContext?.Session != null && Request.HttpContext.Session.TryGetValue("token", out byte[] tokenBytes) && tokenBytes != null)
            {
                storedToken = tokenBytes.ToObject<Token>();
            }
            else
            {
                return AuthenticateResult.NoResult();
            }
            try
            {
                Token newToken = await _authenticationService.ChallengeAsync();
                if (newToken != null)
                {
                    Request?.HttpContext?.Session?.Set("token", newToken.ToByteArray());
                    storedToken = newToken;
                }
            }
            catch (HttpRequestException e)
            {
                Request?.HttpContext?.Session?.Clear();
                return AuthenticateResult.Fail("Invalid token");
            }

            Claim[] claims =
            {
                    new Claim(ClaimTypes.UserData, storedToken.AccessToken),
                    new Claim(ClaimTypes.AuthenticationInstant, DateTime.Now.ToString(CultureInfo.InvariantCulture),
                        ClaimValueTypes.DateTime),
                    new Claim(ClaimTypes.Authentication, true.ToString(), ClaimValueTypes.Boolean)
                };
            ClaimsIdentity identity = new ClaimsIdentity(claims, Scheme.Name);
            ClaimsPrincipal principal = new ClaimsPrincipal(identity);
            AuthenticationTicket ticket = new AuthenticationTicket(principal, Scheme.Name);

            return AuthenticateResult.Success(ticket);
        }

        protected override async Task HandleChallengeAsync(AuthenticationProperties properties)
        {
            Response.Headers["WWW-Authenticate"] = $"Kwetter realm=\"{Options.Realm}\", charset=\"UTF-8\"";
            await base.HandleChallengeAsync(properties);
        }
    }
}
