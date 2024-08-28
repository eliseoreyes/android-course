using BlackJack.BL.Models;
using BlackJack.PL.Data;
using BlackJack.PL.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL
{
    public class GameManager : GenericManager<tblGame>
    {
        public GameManager(DbContextOptions<BlackJackEntities> options) : base(options)
        {

        }

        public void EndTurn(User user)
        {
            throw new NotImplementedException();
        }

        public void StartGame(List<User> users)
        {
            Game game = new Game();
            Insert(game, false);
            foreach (User user in users)
            {
                user.GameId = game.Id;
            }

        }

        public int Insert(Game Game, bool rollback = false)
        {
            try
            {
                tblGame row = new tblGame();
                row.Id = Guid.NewGuid();
                row.Status = Game.Status;
                Deck deck = new Deck()
                {
                    GameId = row.Id
                };
                DeckManager deckManager = new DeckManager(options);
                deckManager.Insert(deck, rollback);
                return base.Insert(row, rollback);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public int Update(Game game, bool rollback = false)
        {
            try
            {
                return base.Update(new tblGame
                {
                    Id = game.Id,
                    Status = game.Status,
                }, rollback);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public int Delete(Guid id, bool rollback = false)
        {
            try
            {
                return base.Delete(id, rollback);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public List<Game> Load()
        {
            try
            {
                List<Game> rows = new List<Game>();
                base.Load()
                .ForEach(c => rows.Add(
                    new Game
                    {
                        Id = c.Id,
                        Status = c.Status,
                    }));
                return rows;

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public Game LoadById(Guid id)
        {
            try
            {
                tblGame row = base.LoadById(id);

                if (row != null)
                {
                    Game game = new Game
                    {
                        Id = row.Id,
                        Status = row.Status,
                    };
                    return game;
                }
                else
                {
                    throw new Exception("Row was not found.");
                }

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }
    }
}