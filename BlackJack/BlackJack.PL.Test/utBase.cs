using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using BlackJack.PL.Data;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Configuration.Json;

namespace BlackJack.PL.Test
{
    [TestClass]
    public abstract class utBase
    {
        protected BlackJackEntities rb;
        protected IDbContextTransaction transaction;
        private IConfigurationRoot _configuration;

        //  represent the database configuration
        private DbContextOptions<BlackJackEntities> _options;
        public utBase()
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(Directory.GetCurrentDirectory())
                .AddJsonFile("appsettings.json");
            _configuration = builder.Build();

            _options = new DbContextOptionsBuilder<BlackJackEntities>()
                .UseSqlServer(_configuration.GetConnectionString("DatabaseConnection"))
                //.UseLazyLoadingProxies()
                .Options;

            rb = new BlackJackEntities(_options);

        }

        [TestInitialize]
        public void TestInitialize()
        {
            transaction = rb.Database.BeginTransaction();
        }

        [TestCleanup]
        public void TestCleanup()
        {
            transaction.Rollback();
            transaction.Dispose();
            rb = null;
        }
    }
}
