using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using ma.ade.Kwetter2.Admin.Service;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;

namespace Kwetter2.Admin.Controllers
{
    public class TweetController : Controller
    {
        private readonly TweetService _tweetService;

        public TweetController(TweetService tweetService)
        {
            _tweetService = tweetService;
        }

        // GET: Tweet
        public ActionResult Index()
        {
            return View(_tweetService.GetTweets());
        }

        // GET: Tweet/Details/5
        public ActionResult Details(long id)
        {
            return View(_tweetService.GetTweet(id));
        }

        // GET: Tweet/Edit/5
        public ActionResult Edit(long id)
        {
            return View(_tweetService.GetTweet(id));
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
        public ActionResult Delete(long id)
        {
            return View(_tweetService.GetTweet(id));
        }

        // POST: Tweet/Delete/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public ActionResult Delete(long id, IFormCollection collection)
        {
            try
            {
                // TODO: Add delete logic here

                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }
    }
}