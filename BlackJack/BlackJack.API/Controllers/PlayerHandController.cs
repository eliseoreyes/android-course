using BlackJack.BL;
using BlackJack.BL.Models;
using BlackJack.PL.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlackJack.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PlayerHandController : ControllerBase
    {
        private readonly ILogger<PlayerHandController> _logger;
        private readonly DbContextOptions<BlackJackEntities> options;

        public PlayerHandController(ILogger<PlayerHandController> logger,
                               DbContextOptions<BlackJackEntities> options)
        {
            _logger = logger;
            this.options = options;
        }

        // GET: api/<PlayerHandController>
        /// <summary>
        /// Gets all players hands
        /// </summary>
        /// <returns>list of player hands</returns>
        [HttpGet]
        public IEnumerable<PlayerHand> Get()
        {
            return new PlayerHandManager(options).Load();
        }

        // GET api/<PlayerHandController>/5
        /// <summary>
        /// Gets one player's hand
        /// </summary>
        /// <param name="id"></param>
        /// <returns>a single playerhand</returns>
        [HttpGet("{id}")]
        public PlayerHand Get(Guid id)
        {
            return new PlayerHandManager(options).LoadById(id);
        }

        // POST api/<PlayerHandController>
        /// <summary>
        /// inserts a card to playerhand
        /// </summary>
        /// <param name="PlayerHand"></param>
        /// <param name="rollback"></param>
        /// <returns>rows affected</returns>
        [HttpPost("{rollback?}")]
        public int Post([FromBody] PlayerHand PlayerHand, bool rollback = false)
        {
            return new PlayerHandManager(options).Insert(PlayerHand, rollback);
        }

        // PUT api/<PlayerHandController>/5
        /// <summary>
        /// updates a player's hand
        /// </summary>
        /// <param name="id"></param>
        /// <param name="PlayerHand"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpPut("{id}/{rollback?}")]
        public int Put(Guid id, [FromBody] PlayerHand PlayerHand, bool rollback = false)
        {
            return new PlayerHandManager(options).Update(PlayerHand, rollback);
        }

        // DELETE api/<PlayerHandController>/5
        /// <summary>
        /// deletes a card from player's hand
        /// </summary>
        /// <param name="id"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpDelete("{id}/{rollback?}")]
        public int Delete(Guid id, bool rollback = false)
        {
            return new PlayerHandManager(options).Delete(id, rollback);
        }
    }
}
