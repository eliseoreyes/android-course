using BlackJack.BL;
using BlackJack.BL.Models;
using BlackJack.PL.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlackJack.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CardController : ControllerBase
    {
        private readonly ILogger<CardController> _logger;
        private readonly DbContextOptions<BlackJackEntities> options;

        public CardController(ILogger<CardController> logger,
                               DbContextOptions<BlackJackEntities> options)
        {
            _logger = logger;
            this.options = options;
        }

        /// <summary>
        /// Return a list of cards
        /// </summary>
        /// <returns>List of cards</returns>
        [HttpGet]
        public IEnumerable<Card> Get()
        {
            return new CardManager(options).Load();
        }

        /// <summary>
        /// loads one card by id
        /// </summary>
        /// <param name="id"></param>
        /// <returns>one card</returns>
        [HttpGet("{id}")]
        public Card Get(Guid id)
        {
            return new CardManager(options).LoadById(id);
        }

        /// <summary>
        /// Adds a card
        /// </summary>
        /// <param name="Card"></param>
        /// <param name="rollback"></param>
        /// <returns>row affected</returns>
        [HttpPost("{rollback?}")]
        public int Post([FromBody] Card Card, bool rollback = false)
        {
            return new CardManager(options).Insert(Card, rollback);
        }

        /// <summary>
        /// updates card attributes
        /// </summary>
        /// <param name="id"></param>
        /// <param name="Card"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpPut("{id}/{rollback?}")]
        public int Put(Guid id, [FromBody] Card Card, bool rollback = false)
        {
            return new CardManager(options).Update(Card, rollback);
        }

        /// <summary>
        /// Deletes a card
        /// </summary>
        /// <param name="id"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpDelete("{id}/{rollback?}")]
        public int Delete(Guid id, bool rollback = false)
        {
            return new CardManager(options).Delete(id, rollback);
        }
    }
}
