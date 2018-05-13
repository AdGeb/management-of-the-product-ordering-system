package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbTables;
import com.app.model.Producer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducerDaoImpl extends AbstractGenericDao<Producer> implements ProducerDao {

    private EntityManagerFactory entityManagerFactory = this.getEntityManagerFactory();

    @Override
    public Optional<Producer> findOneByName(String name) throws Exception {
        Optional<Producer> item = Optional.empty();

        if (entityManagerFactory != null && name != null)
        {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("SELECT p FROM Producer p WHERE name = :name");
                query.setParameter("name", name);
                item = Optional.of((Producer) query.getSingleResult());
                Hibernate.initialize(item.get().getProducts());
                Hibernate.initialize(item.get().getCountry().getProducers());
                Hibernate.initialize(item.get().getCountry().getCustomers());
                Hibernate.initialize(item.get().getCountry().getShops());
                Hibernate.initialize(item.get().getTrade().getProducers());


                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                //throw new Exception(DbTables.Producer + ";FAILED TO FIND ENTITY WITH NAME = " +name);
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
    public Optional<Producer> findOne(Long id) {
        Optional<Producer> item = Optional.empty();

        if (entityManagerFactory != null && id != null)
        {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();


                item = Optional.of(entityManager.find(Producer.class, id));
                Hibernate.initialize(item.get().getProducts());
                Hibernate.initialize(item.get().getCountry().getProducers());
                Hibernate.initialize(item.get().getCountry().getCustomers());
                Hibernate.initialize(item.get().getCountry().getShops());
                Hibernate.initialize(item.get().getTrade().getProducers());
                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
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
    public List<Producer> findAll() {
        List<Producer> items = new ArrayList<>();
        EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try
        {
            tx.begin();

            Query query = entityManager.createQuery("select p from Producer p");
            items = (List<Producer>)query.getResultList();
            items.forEach(c -> Hibernate.initialize(c.getProducts()));
            items.forEach(c -> c.getProducts().forEach(p -> Hibernate.initialize(p.getCustomer_order())));

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

        return items;
    }
}
