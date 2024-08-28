using BlackJack.BL;
using BlackJack.BL.Models;
using BlackJack.PL.Data;
using BlackJack.PL.Entities;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;

namespace BlackJack.API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly ILogger<UserController> _logger;
        private readonly DbContextOptions<BlackJackEntities> options;

        public UserController(ILogger<UserController> logger,
                               DbContextOptions<BlackJackEntities> options)
        {
            _logger = logger;
            this.options = options;
        }

        // GET: api/<UserController>
        /// <summary>
        /// Gets all users
        /// </summary>
        /// <returns>list of users</returns>
        [HttpGet]
        public IEnumerable<User> Get()
        {
            return new UserManager(options).Load();
        }

        // GET api/<UserController>/5
        /// <summary>
        /// gets one user
        /// </summary>
        /// <param name="id"></param>
        /// <returns>user</returns>
        [HttpGet("{id}")]
        public User Get(Guid id)
        {
            return new UserManager(options).LoadById(id);
        }

        // POST api/<UserController>
        /// <summary>
        /// inserts a user
        /// </summary>
        /// <param name="User"></param>
        /// <param name="rollback"></param>
        /// <returns>rows affected</returns>
        [HttpPost("{rollback?}")]
        public int Post([FromBody] User User, bool rollback = false)
        {
            return new UserManager(options).Insert(User, rollback);
        }

        // PUT api/<UserController>/5
        /// <summary>
        /// updates a user's info
        /// </summary>
        /// <param name="id"></param>
        /// <param name="User"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpPut("{id}/{rollback?}")]
        public int Put(Guid id, [FromBody] User User, bool rollback = false)
        {
            return new UserManager(options).Update(User, rollback);
        }

        // DELETE api/<UserController>/5
        /// <summary>
        /// deletes a user
        /// </summary>
        /// <param name="id"></param>
        /// <param name="rollback"></param>
        /// <returns>true or false</returns>
        [HttpDelete("{id}/{rollback?}")]
        public int Delete(Guid id, bool rollback = false)
        {
            return new UserManager(options).Delete(id, rollback);
        }

        
    }
}
