using BlackJack.BL.Models;
using BlackJack.PL.Data;
using BlackJack.PL.Entities;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Options;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.BL
{
    public class CardManager : GenericManager<tblCard>
    {
        public CardManager(DbContextOptions<BlackJackEntities> options) : base(options)
        {

        }

        public int Insert(Card Card, bool rollback = false)
        {
            try
            {
                tblCard row = new tblCard();
                row.Id = Guid.NewGuid();
                row.Suit = Card.Suit;
                row.Value = Card.Value;
                row.Rank = Card.Rank;
                row.CardIMG = Card.CardImg;
                row.DeckId = Card.DeckId;
                return base.Insert(row, rollback);
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public int Update(Card card, bool rollback = false)
        {
            try
            {
                return base.Update(new tblCard
                {
                    Id = card.Id,
                    Suit = card.Suit,
                    Rank = card.Rank,
                    Value = card.Value,
                    CardIMG = card.CardImg,
                    DeckId = card.DeckId
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

        public List<Card> Load()
        {
            try
            {
                List<Card> rows = new List<Card>();
                base.Load()
                .ForEach(c => rows.Add(
                    new Card
                    {
                        Id = c.Id,
                        Suit = c.Suit,
                        Rank = c.Rank,
                        Value = c.Value,
                        CardImg = c.CardIMG,
                        DeckId = c.DeckId,
                    }));
                return rows;

            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        public Card LoadById(Guid id)
        {
            try
            {
                tblCard row = base.LoadById(id);

                if (row != null)
                {
                    Card card = new Card
                    {
                        Id = row.Id,
                        Suit = row.Suit,
                        Rank = row.Rank,
                        Value = row.Value,
                        CardImg = row.CardIMG,
                        DeckId = row.DeckId,
                    };
                    return card;
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
