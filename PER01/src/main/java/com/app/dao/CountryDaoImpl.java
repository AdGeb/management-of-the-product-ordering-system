package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbTables;
import com.app.model.Country;
import com.app.service.ErrorService;
import com.app.service.ErrorServiceImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CountryDaoImpl extends AbstractGenericDao<Country> implements CountryDao {

    private ErrorService errorService = new ErrorServiceImpl();
    private EntityManagerFactory entityManagerFactory = this.getEntityManagerFactory();

    @Override
    public Optional<Country> findOne(Long id) {
        Optional<Country> item = Optional.empty();

        if (entityManagerFactory != null && id != null)
        {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                item = Optional.of((Country)entityManager.find(Country.class,id));
                Hibernate.initialize(item.get().getCustomers());
                Hibernate.initialize(item.get().getProducers());
                Hibernate.initialize(item.get().getShops());

                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                //errorService.addErrror(DbTables.Country, "FAILED TO FIND ENTITY WITH INDEX = " + id, LocalDateTime.now());
            }
            finally {
                if (entityManager != null)
                {
                    entityManager.close();
                }
            }
        }

        return item;
    }

    @Override
    public Optional<Country> findOneByName(String countryName) {
        Optional<Country> item = Optional.empty();

        if (entityManagerFactory != null && countryName != null)
        {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select c from Country c where c.countryName = :countryName");
                query.setParameter("countryName", countryName);
                item = Optional.of((Country)query.getSingleResult());
                Hibernate.initialize(item.get().getCustomers());
                Hibernate.initialize(item.get().getProducers());
                Hibernate.initialize(item.get().getShops());

                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                //errorService.addErrror(DbTables.Country, ";FAILED TO FIND ENTITY WITH NAME = " + countryName, LocalDateTime.now());
            }
            finally {
                if (entityManager != null)
                {
                    entityManager.close();
                }
            }
        }

        return item;
    }

    @Override
    public List<Country> findAll() {
            List<Country> items = null;

            if (this.getEntityManagerFactory()!= null)
            {
                EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
                EntityTransaction tx = entityManager.getTransaction();

                try
                {
                    tx.begin();

                    Query query = entityManager.createQuery("select c from Country c");
                    items = query.getResultList();
                    items.forEach(c -> Hibernate.initialize(c.getShops()));
                    items.forEach(c -> Hibernate.initialize(c.getProducers()));
                    items.forEach(c -> Hibernate.initialize(c.getCustomers()));

                    tx.commit();
                }
                catch (Exception e)
                {
                    if (tx != null)
                    {
                        tx.rollback();
                    }
                    // errorService.addErrror(ErrorServiceImpl.findProperTable(entity.getCanonicalName()), "FAILED TO FIND ALL ENTITIES", LocalDateTime.now());
                    return null;
                }
                finally {
                    if (entityManager != null)
                    {
                        entityManager.close();
                    }
                }
            }

            return items;
    }
}
