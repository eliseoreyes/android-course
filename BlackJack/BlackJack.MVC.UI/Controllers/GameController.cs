using BlackJack.BL.Models;

namespace BlackJack.MVC.UI.Controllers
{
    public class GameController : GenericController<Game>
    {
        public GameController(HttpClient client) : base(client) { }
    }
}
