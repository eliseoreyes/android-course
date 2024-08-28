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
    public class PlayerHandManager : GenericManager<tblPlayerHand>
    {
        public PlayerHandManager(DbContextOptions<BlackJackEntities> options) : base(options)
        {

        }
        private static DbContextOptions<BlackJackEntities> _options { get; set; }

        public static void SetOptions(DbContextOptions<BlackJackEntities> options)
        {
            _options = options;
        }
        public int CalculateHandScore(User user)
        {
            try
            {
                user.Score = 0;
                List<PlayerHand> PlayerCards = new List<PlayerHand>();
                using (BlackJackEntities bj = new BlackJackEntities(_options))
                {
                    var playerhands = bj.tblPlayerHands.Where(a => a.UserId == user.Id).ToList();

                    foreach (var playerHand in playerhands)
                    {

                        var card = bj.tblCards.FirstOrDefault(c => c.Id == playerHand.CardId);

                        if (card != null)
                        {
                            if(user.Score <= 10 && card.Rank == "Ace")
                            {
                                card.Value = 11;
                            }
                            user.Score += card.Value;
                        }
                    }
                    
                   
                }
                return user.Score;
            }
            catch (Exception)
            {

                throw;
            }
        }

        public int Insert(PlayerHand PlayerHand, bool rollback = false)
        {
            try
            {
                tblPlayerHand row = new tblPlayerHand();
                row.Id = Guid.NewGuid();
                row.UserId = PlayerHand.UserId;
                row.CardId = PlayerHand.CardId;
                return base.Insert(row, rollback);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public int Update(PlayerHand playerHand, bool rollback = false)
        {
            try
            {
                return base.Update(new tblPlayerHand
                {
                    Id = playerHand.Id,
                    UserId = playerHand.UserId,
                    CardId = playerHand.CardId,
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

        public List<PlayerHand> Load()
        {
            try
            {
                List<PlayerHand> rows = new List<PlayerHand>();
                base.Load()
                .ForEach(c => rows.Add(
                    new PlayerHand
                    {
                        Id = c.Id,
                        UserId = c.UserId,
                        CardId = c.CardId,
                    }));
                return rows;

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public PlayerHand LoadById(Guid id)
        {
            try
            {
                tblPlayerHand row = base.LoadById(id);

                if (row != null)
                {
                    PlayerHand playerHand = new PlayerHand
                    {
                        Id = row.Id,
                        UserId = row.UserId,
                        CardId = row.CardId,
                    };
                    return playerHand;
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
