using Microsoft.EntityFrameworkCore.Storage;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Principal;
using System.Text;
using System.Threading.Tasks;
using BlackJack.PL.Data;
using BlackJack.PL.Entities;

namespace BlackJack.BL
{
    public abstract class GenericManager<T> where T : class, IEntity
    {
        protected DbContextOptions<BlackJackEntities> options;

        public GenericManager(DbContextOptions<BlackJackEntities> options)
        {
            this.options = options;
        }

        public GenericManager() { }

        public List<T> Load()
        {
            try
            {
                //var rows = new BlackJackEntities(options).Set<T>().ToList<T>().OrderBy(x => x.SortField).ToList<T>();
                //return rows;
                return new BlackJackEntities(options).Set<T>().ToList<T>().OrderBy(x => x.SortField).ToList<T>();
            }
            catch (Exception)
            {

                throw;
            }
        }

        public T LoadById(Guid id)
        {
            try
            {
                var row = new BlackJackEntities(options).Set<T>().Where(t => t.Id == id).FirstOrDefault();
                return row;
            }
            catch (Exception)
            {

                throw;
            }
        }

        public int Insert(T entity, bool rollback = false)
        {
            try
            {
                int results = 0;
                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    IDbContextTransaction dbTransaction = null;
                    if (rollback) dbTransaction = dc.Database.BeginTransaction();

                    entity.Id = Guid.NewGuid();

                    dc.Set<T>().Add(entity);
                    results = dc.SaveChanges();

                    if (rollback) dbTransaction.Rollback();

                }

                return results;
            }
            catch (Exception)
            {

                throw;
            }
        }

        public int Update(T entity, bool rollback = false)
        {
            try
            {
                int results = 0;
                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    IDbContextTransaction dbTransaction = null;
                    if (rollback) dbTransaction = dc.Database.BeginTransaction();

                    dc.Entry(entity).State = Microsoft.EntityFrameworkCore.EntityState.Modified;

                    results = dc.SaveChanges();

                    if (rollback) dbTransaction.Rollback();

                }

                return results;
            }
            catch (Exception)
            {

                throw;
            }
        }

        public int Delete(Guid id, bool rollback = false)
        {
            try
            {
                int results = 0;
                using (BlackJackEntities dc = new BlackJackEntities(options))
                {
                    IDbContextTransaction dbTransaction = null;
                    if (rollback) dbTransaction = dc.Database.BeginTransaction();

                    T row = dc.Set<T>().FirstOrDefault(t => t.Id == id);

                    if (row != null)
                    {
                        dc.Set<T>().Remove(row);
                        results = dc.SaveChanges();
                        if (rollback) dbTransaction.Rollback();
                    }
                    else
                    {
                        throw new Exception("Row does not exist.");
                    }

                }

                return results;
            }
            catch (Exception)
            {

                throw;
            }
        }

    }

}
