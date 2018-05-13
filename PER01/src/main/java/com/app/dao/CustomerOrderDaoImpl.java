package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbTables;
import com.app.model.Customer;
import com.app.model.CustomerOrder;
import com.app.service.ErrorService;
import com.app.service.ErrorServiceImpl;
import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CustomerOrderDaoImpl extends AbstractGenericDao<CustomerOrder> implements CustomerOrderDao {

    private ErrorService errorService = new ErrorServiceImpl();

    @Override
    public Optional<CustomerOrder> findOne(Long id) {
        EntityManagerFactory entityManagerFactory = this.getEntityManagerFactory();
        Optional<CustomerOrder> item = Optional.empty();

        if (entityManagerFactory!= null && id != null) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                item = Optional.of((CustomerOrder) entityManager.find(CustomerOrder.class, id));
                Hibernate.initialize(item.get().getProduct().getStocks());
                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Customer_Order, ("FAILED TO FIND ENTITY WITH ID = " + id), LocalDateTime.now());
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }
        return item;
    }

    @Override
    public List<CustomerOrder> findAll() {
        List<CustomerOrder> items = null;

        if (this.getEntityManagerFactory()!= null)
        {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select c from CustomerOrder c");
                items = (List<CustomerOrder>)query.getResultList();
                items.forEach(c -> Hibernate.initialize(c.getCustomer().getCustomer_order()));
                items.forEach(c -> Hibernate.initialize(c.getProduct().getStocks()));

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
