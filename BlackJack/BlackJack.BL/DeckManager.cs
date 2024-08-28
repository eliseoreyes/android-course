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
    public class DeckManager : GenericManager<tblDeck>
    {
        public DeckManager(DbContextOptions<BlackJackEntities> options) : base(options)
        {

        }

        public int Insert(Deck Deck, bool rollback = false)
        {
            try
            {
                tblDeck row = new tblDeck();
                row.Id = Guid.NewGuid();
                row.GameId = Deck.GameId;
                //  Seed new cards into deck
                #region hearts
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "Ace",
                    Value = 1,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "2",
                    Value = 2,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "3",
                    Value = 3,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "4",
                    Value = 4,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "5",
                    Value = 5,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "6",
                    Value = 6,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "7",
                    Value = 7,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "8",
                    Value = 8,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "9",
                    Value = 9,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "10",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "Jack",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "Queen",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Hearts",
                    Rank = "King",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                #endregion
                #region Spades
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "Ace",
                    Value = 1,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "2",
                    Value = 2,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "3",
                    Value = 3,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "4",
                    Value = 4,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "5",
                    Value = 5,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "6",
                    Value = 6,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "7",
                    Value = 7,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "8",
                    Value = 8,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "9",
                    Value = 9,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "10",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "Jack",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "Queen",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Spades",
                    Rank = "King",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                #endregion
                #region Diamonds
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "Ace",
                    Value = 1,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "2",
                    Value = 2,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "3",
                    Value = 3,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "4",
                    Value = 4,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "5",
                    Value = 5,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "6",
                    Value = 6,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "7",
                    Value = 7,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "8",
                    Value = 8,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "9",
                    Value = 9,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "10",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "Jack",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "Queen",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Diamonds",
                    Rank = "King",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                #endregion
                #region Clubs
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "Ace",
                    Value = 1,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "2",
                    Value = 2,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "3",
                    Value = 3,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "4",
                    Value = 4,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "5",
                    Value = 5,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "6",
                    Value = 6,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "7",
                    Value = 7,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "8",
                    Value = 8,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "9",
                    Value = 9,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "10",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "Jack",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "Queen",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                new tblCard()
                {
                    Suit = "Clubs",
                    Rank = "King",
                    Value = 10,
                    Id = Guid.NewGuid(),
                    CardIMG = "",
                    DeckId = row.Id,
                };
                #endregion
                return base.Insert(row, rollback);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public int Update(Deck deck, bool rollback = false)
        {
            try
            {
                return base.Update(new tblDeck
                {
                    Id = deck.Id,
                    GameId = deck.GameId,
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

        public List<Deck> Load()
        {
            try
            {
                List<Deck> rows = new List<Deck>();
                base.Load()
                .ForEach(c => rows.Add(
                    new Deck
                    {
                        Id = c.Id,
                        GameId = c.GameId,
                    }));
                return rows;

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public Deck LoadById(Guid id)
        {
            try
            {
                tblDeck row = base.LoadById(id);

                if (row != null)
                {
                    Deck deck = new Deck
                    {
                        Id = row.Id,
                        GameId = row.GameId,
                    };
                    return deck;
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
