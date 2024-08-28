using BlackJack.BL.Models;
using BlackJack.MVC.UI.Extensions;
using BlackJack.Utility;
using Microsoft.AspNetCore.Mvc;

namespace BlackJack.MVC.UI.Controllers
{
    public class UserController : GenericController<User>
    {
        private readonly HttpClient httpClient;

        public UserController(HttpClient client) : base(client)
        {
            httpClient = client;
        }

        public IActionResult Logout()
        {
            HttpContext.Session.SetObject("user", null);
            return View();
        }

        // GET: UserController/Create
        public IActionResult Login(string returnUrl)
        {
            TempData["returnurl"] = returnUrl;
            return View();
        }

        // POST: UserController/Create
        [HttpPost]
        [ValidateAntiForgeryToken]
        public IActionResult Login(User user, string returnUrl)
        {
            try
            {
                user.Id = Guid.Empty;
                user.Username = "Login";
                user.Nickname = "Login";

                ApiClient apiClient = new ApiClient(httpClient.BaseAddress.ToString());
                var response = apiClient.Post<User>(user, "User", "Login");
                string result = response.Content.ReadAsStringAsync().Result;

                result = result.Replace("\"", "");

                Guid id;

                if (Guid.TryParse(result, out id))
                {
                    user = apiClient.GetItem<User>("User", id);
                    HttpContext.Session.SetObject("user", user);
                    if (TempData["returnurl"] != null)
                    {
                        return Redirect(TempData["returnurl"].ToString());
                    }
                    else
                    {
                        return RedirectToAction("Index", "Movie");
                    }

                }
                else
                {
                    ViewBag.Error = "Unable to login with those credentails.";
                    return View();
                }

            }
            catch (Exception ex)
            {
                ViewBag.Error = ex.Message;
                return View();
            }
        }
    }
}
