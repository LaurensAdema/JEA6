using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Kwetter2.Admin.Controllers
{
    public class AuthenticationController : Controller
    {
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
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Login(long id, IFormCollection collection)
        {
            try
            {
                // TODO: Add Sigin logic here

                return SignIn();
            }
            catch
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

            return SignOut();
        }
    }
}