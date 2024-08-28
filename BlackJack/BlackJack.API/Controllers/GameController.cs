using BlackJack.BL;
using BlackJack.BL.Models;
using BlackJack.PL.Data;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlackJack.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GameController : ControllerBase
    {
        private readonly ILogger<GameController> _logger;
        private readonly DbContextOptions<BlackJackEntities> options;

        public GameController(ILogger<GameController> logger,
                               DbContextOptions<BlackJackEntities> options)
        {
            _logger = logger;
            this.options = options;
        }

        // GET: api/<GameController>
        /// <summary>
        /// Loads all games
        /// </summary>
        /// <returns>list of games</returns>
        [HttpGet]
        public IEnumerable<Game> Get()
        {
            return new GameManager(options).Load();
        }

        // GET api/<GameController>/5
        /// <summary>
        /// Loads a specific game
        /// </summary>
        /// <param name="id"></param>
        /// <returns>one game</returns>
        [HttpGet("{id}")]
        public Game Get(Guid id)
        {
            return new GameManager(options).LoadById(id);
        }

        // POST api/<GameController>
        /// <summary>
        /// Inserts a game
        /// </summary>
        /// <param name="Game"></param>
        /// <param name="rollback"></param>
        /// <returns>rows affected</returns>
        [HttpPost("{rollback?}")]
        public int Post([FromBody] Game Game, bool rollback = false)
        {
            return new GameManager(options).Insert(Game, rollback);
        }

        // PUT api/<GameController>/5
        /// <summary>
        /// updates a game
        /// </summary>
        /// <param name="id"></param>
        /// <param name="Game"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpPut("{id}/{rollback?}")]
        public int Put(Guid id, [FromBody] Game Game, bool rollback = false)
        {
            return new GameManager(options).Update(Game, rollback);
        }

        // DELETE api/<GameController>/5
        /// <summary>
        /// Deletes a game
        /// </summary>
        /// <param name="id"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpDelete("{id}/{rollback?}")]
        public int Delete(Guid id, bool rollback = false)
        {
            return new GameManager(options).Delete(id, rollback);
        }
    }
}
