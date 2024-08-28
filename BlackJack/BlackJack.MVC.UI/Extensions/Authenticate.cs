using BlackJack.BL.Models;

namespace BlackJack.MVC.UI.Extensions
{
    public static class Authenticate
    {

        public static bool IsAuthenticated(HttpContext context)
        {
            if (context.Session.GetObject<User>("user") != null)
            {
                User user = context.Session.GetObject<User>("user");
                return true;
            }
            else
            {
                return false;
            }
        }



    }
}
