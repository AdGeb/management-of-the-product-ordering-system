package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.model.Country;
import com.app.model.Trade;
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
import java.util.List;
import java.util.Optional;

public class TradeDaoImpl extends AbstractGenericDao<Trade> implements TradeDao {

    private EntityManagerFactory entityManagerFactory = this.getEntityManagerFactory();
    private ErrorService errorService = new ErrorServiceImpl();

    @Override
    public Optional<Trade> findOne(Long id) {
        Optional<Trade> item = Optional.empty();

        if (entityManagerFactory != null && id != null) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                item = Optional.of((Trade) entityManager.find(Trade.class, id));
                Hibernate.initialize(item.get().getProducers());
                item.get().getProducers().forEach(c -> Hibernate.initialize(c.getCountry().getCustomers()));
                item.get().getProducers().forEach(c -> Hibernate.initialize(c.getCountry().getProducers()));
                item.get().getProducers().forEach(c -> Hibernate.initialize(c.getCountry().getShops()));

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }

        return item;
    }

    public Optional<Trade> findOneByName(String tradeName) {
        Optional<Trade> item = Optional.empty();

        if (entityManagerFactory != null && tradeName != null) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                Query query = entityManager.createQuery("select t from Trade t where t.name = :tradename");
                query.setParameter("tradename", tradeName);
                item = Optional.of((Trade) query.getSingleResult());
                Hibernate.initialize(item.get().getProducers());
                item.get().getProducers().forEach(c -> Hibernate.initialize(c.getCountry().getCustomers()));
                item.get().getProducers().forEach(c -> Hibernate.initialize(c.getCountry().getProducers()));
                item.get().getProducers().forEach(c -> Hibernate.initialize(c.getCountry().getShops()));

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }

        return item;
    }

    @Override
    public List<Trade> findAll() {
        List<Trade> items = null;

        if (this.getEntityManagerFactory()!= null)
        {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select t from Trade t");
                items = (List<Trade>)query.getResultList();
                items.forEach(trade -> Hibernate.initialize(trade.getProducers()));
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
