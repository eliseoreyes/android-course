using BlackJack.BL.Models;

namespace BlackJack.MVC.UI.Controllers
{
    public class CardController : GenericController<Card>
    {
        public CardController(HttpClient client) : base(client) { }
    }
}
