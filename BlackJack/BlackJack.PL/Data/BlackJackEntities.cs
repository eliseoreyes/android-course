using BlackJack.PL.Entities;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace BlackJack.PL.Data
{
    public class BlackJackEntities : DbContext
    {
        public BlackJackEntities() { }

        public BlackJackEntities(DbContextOptions<BlackJackEntities> options) : base(options) { }

        public virtual DbSet<tblGame> tblGames { get; set; }
        public virtual DbSet<tblUser> tblUsers { get; set; }
        public virtual DbSet<tblDeck> tblDecks { get; set; }
        public virtual DbSet<tblCard> tblCards { get; set; }
        public virtual DbSet<tblPlayerHand> tblPlayerHands { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
        }
        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            DataSeeder.SeedData(modelBuilder);
        }
    }
}