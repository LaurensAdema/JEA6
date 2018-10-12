using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Interfaces;
using Microsoft.AspNetCore.Authentication;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;

namespace ma.ade.Kwetter2.Admin.Authentication
{
    public static class KwetterAuthenticationExtensions
    {
        public static AuthenticationBuilder AddKwetter<TAuthService>(this AuthenticationBuilder builder)
            where TAuthService : class, IKwetterAuthenticationService
        {
            return AddKwetter<TAuthService>(builder, KwetterAuthenticationDefaults.AuthenticationScheme, _ => { });
        }

        public static AuthenticationBuilder AddKwetter<TAuthService>(this AuthenticationBuilder builder, string authenticationScheme)
            where TAuthService : class, IKwetterAuthenticationService
        {
            return AddKwetter<TAuthService>(builder, authenticationScheme, _ => { });
        }

        public static AuthenticationBuilder AddKwetter<TAuthService>(this AuthenticationBuilder builder, Action<KwetterAuthenticationOptions> configureOptions)
            where TAuthService : class, IKwetterAuthenticationService
        {
            return AddKwetter<TAuthService>(builder, KwetterAuthenticationDefaults.AuthenticationScheme, configureOptions);
        }

        public static AuthenticationBuilder AddKwetter<TAuthService>(this AuthenticationBuilder builder, Action<KwetterAuthenticationOptions> configureOptions, Func<IServiceProvider, TAuthService> service)
            where TAuthService : class, IKwetterAuthenticationService
        {
            return AddKwetter<TAuthService>(builder, KwetterAuthenticationDefaults.AuthenticationScheme, configureOptions, service);
        }

        public static AuthenticationBuilder AddKwetter<TAuthService>(this AuthenticationBuilder builder, string authenticationScheme, Action<KwetterAuthenticationOptions> configureOptions)
            where TAuthService : class, IKwetterAuthenticationService
        {
            builder.Services.AddSingleton<IPostConfigureOptions<KwetterAuthenticationOptions>, KwetterAuthenticationPostConfigureOptions>();
            builder.Services.AddTransient<IKwetterAuthenticationService, TAuthService>();

            return builder.AddScheme<KwetterAuthenticationOptions, KwetterAuthenticationHandler>(
                authenticationScheme, configureOptions);
        }

        public static AuthenticationBuilder AddKwetter<TAuthService>(this AuthenticationBuilder builder, string authenticationScheme, Action<KwetterAuthenticationOptions> configureOptions, Func<IServiceProvider, TAuthService> service)
            where TAuthService : class, IKwetterAuthenticationService
        {
            builder.Services.AddSingleton<IPostConfigureOptions<KwetterAuthenticationOptions>, KwetterAuthenticationPostConfigureOptions>();
            builder.Services.AddTransient<IKwetterAuthenticationService>(service);
            return builder.AddScheme<KwetterAuthenticationOptions, KwetterAuthenticationHandler>(
                authenticationScheme, configureOptions);
        }
    }
}
