using BlackJack.BL.Models;

namespace BlackJack.MVC.UI.Controllers
{
    public class DeckController : GenericController<Deck>
    {
        public DeckController(HttpClient client) : base(client) { }
    }
}
