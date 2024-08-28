using BlackJack.BL.Models;

namespace BlackJack.MVC.UI.Controllers
{
    public class PlayerHandController : GenericController<PlayerHand>
    {
        public PlayerHandController(HttpClient client) : base(client) { }
    }
}
