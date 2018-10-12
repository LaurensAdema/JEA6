using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Interfaces;
using ma.ade.Kwetter2.Admin.Service;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace Kwetter2.Admin.Controllers
{
    [Authorize]
    public class TweetController : Controller
    {
        private readonly ITweetService _tweetService;

        public TweetController(ITweetService tweetService)
        {
            _tweetService = tweetService;
        }

        // GET: Tweet
        public async Task<ActionResult> Index()
        {
            return View(await _tweetService.GetAllAsync());
        }

        // GET: Tweet/Details/5
        public async Task<ActionResult> Details(long id)
        {
            return View(await _tweetService.GetAsync(id));
        }

        // GET: Tweet/Edit/5
        public async Task<ActionResult> Edit(long id)
        {
            return View(await _tweetService.GetAsync(id));
        }

        // POST: Tweet/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Edit(long id, IFormCollection collection)
        {
            try
            {
                // TODO: Add update logic here

                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

        // GET: Tweet/Delete/5
        public async Task<ActionResult> Delete(long id)
        {
            return View(await _tweetService.GetAsync(id));
        }

        // POST: Tweet/Delete/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public async Task<ActionResult> DeleteAsync(long id, IFormCollection collection)
        {
            try
            {
                await _tweetService.DeleteAsync(id);
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }
    }
}