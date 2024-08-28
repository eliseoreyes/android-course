using BlackJack.BL;
using BlackJack.BL.Models;
using BlackJack.PL.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlackJack.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class DeckController : ControllerBase
    {
        private readonly ILogger<DeckController> _logger;
        private readonly DbContextOptions<BlackJackEntities> options;

        public DeckController(ILogger<DeckController> logger,
                               DbContextOptions<BlackJackEntities> options)
        {
            _logger = logger;
            this.options = options;
        }

        // GET: api/<DeckController>
        /// <summary>
        /// Gets a list of decks
        /// </summary>
        /// <returns>list of decks</returns>
        [HttpGet]
        public IEnumerable<Deck> Get()
        {
            return new DeckManager(options).Load();
        }

        // GET api/<DeckController>/5
        /// <summary>
        /// Gets one specific Deck
        /// </summary>
        /// <param name="id"></param>
        /// <returns>one deck</returns>
        [HttpGet("{id}")]
        public Deck Get(Guid id)
        {
            return new DeckManager(options).LoadById(id);
        }

        // POST api/<DeckController>
        /// <summary>
        /// Inserts a deck
        /// </summary>
        /// <param name="Deck"></param>
        /// <param name="rollback"></param>
        /// <returns>rows affected</returns>
        [HttpPost("{rollback?}")]
        public int Post([FromBody] Deck Deck, bool rollback = false)
        {
            return new DeckManager(options).Insert(Deck, rollback);
        }

        // PUT api/<DeckController>/5
        /// <summary>
        /// Updates a deck
        /// </summary>
        /// <param name="id"></param>
        /// <param name="Deck"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpPut("{id}/{rollback?}")]
        public int Put(Guid id, [FromBody] Deck Deck, bool rollback = false)
        {
            return new DeckManager(options).Update(Deck, rollback);
        }

        // DELETE api/<DeckController>/5
        /// <summary>
        /// Deletes a deck
        /// </summary>
        /// <param name="id"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpDelete("{id}/{rollback?}")]
        public int Delete(Guid id, bool rollback = false)
        {
            return new DeckManager(options).Delete(id, rollback);
        }
    }
}
