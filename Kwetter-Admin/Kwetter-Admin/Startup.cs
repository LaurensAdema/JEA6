using ma.ade.Kwetter2.Admin.Authentication;
using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Service;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using System;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc.Authorization;

namespace ma.ade.Kwetter2.Admin
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.Configure<CookiePolicyOptions>(options =>
            {
                // This lambda determines whether user consent for non-essential cookies is needed for a given request.
                options.CheckConsentNeeded = context => true;
                options.MinimumSameSitePolicy = SameSiteMode.None;
            });
            Uri baseUri = new Uri(Configuration["api:base"]);
            services
                .AddSingleton(Configuration)
                .AddScoped<IKwetterAuthenticationService>(s => new AuthenticationService(Configuration, new Uri(baseUri, Configuration["api:controllers:authentication"]), null))
                .AddScoped<IUserService>(s => new UserService(Configuration, new Uri(baseUri, Configuration["api:controllers:user"]), null))
                .AddScoped<ITweetService>(s => new TweetService(Configuration, new Uri(baseUri, Configuration["api:controllers:tweet"]), null));

            services.AddCors();
            services.AddAuthentication(KwetterAuthenticationDefaults.AuthenticationScheme)
                .AddKwetter<IKwetterAuthenticationService>(
                    o => { o.Realm = "Admin"; }, 
                    s => new AuthenticationService(Configuration, new Uri(baseUri, Configuration["api:controllers:authentication"])));
            services.AddSingleton<IPostConfigureOptions<KwetterAuthenticationOptions>, KwetterAuthenticationPostConfigureOptions>();

            services.AddDistributedMemoryCache();

            services.AddSession(options =>
            {
                options.IdleTimeout = TimeSpan.FromMinutes(20);
                options.Cookie.HttpOnly = true;
            });

            services.AddMvc(config =>
            {
                var policy = new AuthorizationPolicyBuilder()
                    .RequireAuthenticatedUser()
                    .Build();
                config.Filters.Add(new AuthorizeFilter(policy));
            }).SetCompatibilityVersion(CompatibilityVersion.Version_2_1);
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env)
        {
            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseDatabaseErrorPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
                app.UseHsts();
            }

            app.UseHttpsRedirection();
            app.UseStaticFiles();
            app.UseCookiePolicy();
            app.UseSession();
            app.UseAuthentication();
            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{id?}");
            });
        }
    }
}
