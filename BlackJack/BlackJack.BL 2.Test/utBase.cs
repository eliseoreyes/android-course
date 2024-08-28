using BlackJack.PL.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.Extensions.Configuration;

namespace BlackJack.BL.Test
{
    [TestClass]
    public abstract class utBase
    {
        protected BlackJackEntities dc;
        protected IDbContextTransaction transaction;
        private IConfigurationRoot _configuration;

        //  represent the database configuration
        protected DbContextOptions<BlackJackEntities> options;
        public utBase()
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json");
            _configuration = builder.Build();

            options = new DbContextOptionsBuilder<BlackJackEntities>()
                .UseSqlServer(_configuration.GetConnectionString("DatabaseConnection"))
                .UseLazyLoadingProxies()
                .Options;

            dc = new BlackJackEntities(options);

        }

        [TestInitialize]
        public void TestInitialize()
        {
            transaction = dc.Database.BeginTransaction();
        }

        [TestCleanup]
        public void TestCleanup()
        {
            transaction.Rollback();
            transaction.Dispose();
            dc = null;
        }
    }
}