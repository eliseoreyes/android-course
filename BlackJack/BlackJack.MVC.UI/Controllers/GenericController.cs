using BlackJack.MVC.UI.Extensions;
using BlackJack.Utility;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.AspNetCore.Mvc;

namespace BlackJack.MVC.UI.Controllers
{
    public abstract class GenericController<T> : Controller where T : class
    {
        dynamic manager;
        //protected DbContextOptions<DVDCentralEntities> options;
        protected HttpClient httpClient;
        private ApiClient apiClient;

        public GenericController(HttpClient httpClient)
        {
            manager = (T)Activator.CreateInstance(typeof(T));
            this.apiClient = new ApiClient(httpClient.BaseAddress.AbsoluteUri);
        }


        public virtual ActionResult Index()
        {
            ViewBag.Title = "List of " + typeof(T).Name + "s";
            var entities = apiClient.GetList<T>(typeof(T).Name);
            return View(entities);
        }

        // GET: DegreeTypeController/Details/5
        public virtual ActionResult Details(Guid id)
        {
            string methodname = System.Reflection.MethodBase.GetCurrentMethod().Name;
            ViewBag.Title = methodname + " for " + typeof(T).Name;
            var entity = apiClient.GetItem<T>(typeof(T).Name, id);
            return View(entity);
        }

        // GET: DegreeTypeController/Create
        [Authorize]
        public virtual ActionResult Create(string returnUrl = null)
        {
            ViewData["ReturnUrl"] = returnUrl;
            //if (Authenticate.IsAuthenticated(HttpContext))
            //{
            string methodname = System.Reflection.MethodBase.GetCurrentMethod().Name;
            ViewBag.Title = methodname + " " + typeof(T).Name;
            return View();
            //}
            //else
            //{
            //    return RedirectToAction("Login", "User", new { returnUrl = UriHelper.GetDisplayUrl(HttpContext.Request) });
            // }
        }

        [HttpPost]
        [ValidateAntiForgeryToken]
        public virtual ActionResult Create(T entity, bool rollback = false)
        {
            try
            {
                var response = apiClient.Post<T>(entity, typeof(T).Name);
                string result = response.Content.ReadAsStringAsync().Result;

                result = result.Replace("\"", "");
                //entity.Id = Guid.Parse(result);

                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }

        // GET: DegreeTypeController/Edit/5
        public virtual ActionResult Edit(Guid id, string returnUrl = null)
        {

            ViewData["ReturnUrl"] = returnUrl;
            if (Authenticate.IsAuthenticated(HttpContext))
            {
                string methodname = System.Reflection.MethodBase.GetCurrentMethod().Name;
                //apiClient = new ApiClient($"https://localhost:7070/api/{id}");
                ViewBag.Title = methodname + " " + typeof(T).Name;
                var entity = apiClient.GetItem<T>(typeof(T).Name, id);
                return View(entity);
            }
            else
            {
                return RedirectToAction("Login", "User", new { returnUrl = UriHelper.GetDisplayUrl(HttpContext.Request) });
            }


        }

        // POST: DegreeTypeController/Edit/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public virtual ActionResult Edit(Guid id, T entity, bool rollback = false)
        {
            try
            {
                //apiClient = new ApiClient($"https://localhost:7070/api/{id}");
                var response = apiClient.Put<T>(entity, typeof(T).Name, id);
                string result = response.Content.ReadAsStringAsync().Result;
                return RedirectToAction(nameof(Index));
            }
            catch (Exception ex)
            {
                ViewBag.Error = ex.Message;
                return View();
            }
        }

        // GET: DegreeTypeController/Delete/5
        public virtual ActionResult Delete(Guid id)
        {
            string methodname = System.Reflection.MethodBase.GetCurrentMethod().Name;
            ViewBag.Title = methodname + " " + typeof(T).Name;
            //apiClient = new ApiClient($"https://localhost:7070/api/{id}");
            var entity = apiClient.GetItem<T>(typeof(T).Name, id);
            return View(entity);
        }

        // POST: DegreeTypeController/Delete/5
        [HttpPost]
        [ValidateAntiForgeryToken]
        public virtual ActionResult Delete(Guid id, T entity, bool rollback = false)
        {
            try
            {
                //apiClient = new ApiClient($"https://localhost:7070/api/{id}");
                var response = apiClient.Delete(typeof(T).Name, id);
                string result = response.Content.ReadAsStringAsync().Result;
                return RedirectToAction(nameof(Index));
            }
            catch
            {
                return View();
            }
        }
    }
}
