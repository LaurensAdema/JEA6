using System;
using Hanssens.Net;
using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Models;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Extensions;

namespace Kwetter2.Admin.Controllers
{
    public class AuthenticationController : Controller
    {
        private readonly IKwetterAuthenticationService _authenticationService;

        public AuthenticationController(IKwetterAuthenticationService authenticationService)
        {
            _authenticationService = authenticationService;
        }

        [AllowAnonymous]
        public IActionResult Login()
        {
            if (User.Identity.IsAuthenticated)
            {
                Response.Redirect(Url.Action("Index", "Home"));
            }
            return View();
        }

        // POST: Login
        [AllowAnonymous]
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<IActionResult> Login(IFormCollection collection)
        {
            try
            {
                if (!collection.ContainsKey("email") || !collection.ContainsKey("password"))
                {
                    return BadRequest();
                }

                User user = new User { Email = collection["email"], Password = collection["password"] };
                Token token = await _authenticationService.LoginAsync(user);
                HttpContext.Session.Set("token", token.ToByteArray());
                return RedirectToAction("Index", "Home");
            }
            catch(Exception e)
            {
                return View();
            }
        }

        public IActionResult Logout()
        {
            if (!User.Identity.IsAuthenticated)
            {
                return RedirectToAction("Index", "Home");
            }

            HttpContext.Session.Clear();

            return SignOut();
        }
    }
}