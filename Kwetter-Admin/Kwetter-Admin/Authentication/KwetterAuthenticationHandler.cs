using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;
using System;
using System.Globalization;
using System.Net.Http.Headers;
using System.Security.Claims;
using System.Text;
using System.Text.Encodings.Web;
using System.Threading.Tasks;

namespace ma.ade.Kwetter2.Admin.Authentication
{
    public class KwetterAuthenticationHandler : AuthenticationHandler<KwetterAuthenticationOptions>
    {
        private const string AuthorizationHeaderName = "Authorization";
        private const string KwetterSchemeName = "Kwetter";
        private readonly IKwetterAuthenticationService _authenticationService;
        private readonly IUserService _userService;

        public KwetterAuthenticationHandler(
            IOptionsMonitor<KwetterAuthenticationOptions> options,
            ILoggerFactory logger,
            UrlEncoder encoder,
            ISystemClock clock,
            IKwetterAuthenticationService authenticationService,
            IUserService userService)
            : base(options, logger, encoder, clock)
        {
            _authenticationService = authenticationService;
            _userService = userService;
        }

        protected override async Task<AuthenticateResult> HandleAuthenticateAsync()
        {
            if (!Request.Headers.ContainsKey(AuthorizationHeaderName))
            {
                //Authorization header not in request
                return AuthenticateResult.NoResult();
            }

            if (!AuthenticationHeaderValue.TryParse(Request.Headers[AuthorizationHeaderName], out AuthenticationHeaderValue headerValue))
            {
                //Invalid Authorization header
                return AuthenticateResult.NoResult();
            }

            if (!KwetterSchemeName.Equals(headerValue.Scheme, StringComparison.OrdinalIgnoreCase))
            {
                //Not Kwetter authentication header
                return AuthenticateResult.NoResult();
            }

            byte[] headerValueBytes = Convert.FromBase64String(headerValue.Parameter);
            string userAndPassword = Encoding.UTF8.GetString(headerValueBytes);
            string[] parts = userAndPassword.Split(':');
            if (parts.Length != 2)
            {
                return AuthenticateResult.Fail("Invalid Kwetter authentication header");
            }
            string email = parts[0];
            string password = parts[1];
            User user = new User { Email = email, Password = password };

            Token token = await _authenticationService.LoginAsync(user);

            if (token == null)
            {
                return AuthenticateResult.Fail("Invalid username or password");
            }

            user = await _userService.GetMeAsync();
            Claim[] claims =
            {
                new Claim(ClaimTypes.Name, $"{user.Profile.FirstName} {user.Profile.LastName}"),
                new Claim(ClaimTypes.Email, user.Email),
                new Claim(ClaimTypes.UserData, token.AccessToken),
                new Claim(ClaimTypes.AuthenticationInstant, DateTime.Now.ToString(CultureInfo.InvariantCulture), ClaimValueTypes.DateTime),
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
