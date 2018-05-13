package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbStatus;
import com.app.dao.generic.DbTables;
import com.app.model.Customer;
import com.app.model.Product;
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

public class ProductDaoImpl extends AbstractGenericDao<Product> implements ProductDao {

    private EntityManagerFactory entityManagerFactory = this.getEntityManagerFactory();
    private ErrorService errorService = new ErrorServiceImpl();

    @Override
    public DbStatus add(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            entityManager.merge(product);

            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            errorService.addErrror(DbTables.Product, "FAILED TO ADD PRODUCT", LocalDateTime.now());
            return DbStatus.ERROR;
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
            return DbStatus.INSERTED;
        }
    }

    @Override
    public DbStatus update(Product product) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            entityManager.merge(product);

            tx.commit();
        }catch (Exception e){
            if(tx != null){
                tx.rollback();
            }
            errorService.addErrror(DbTables.Product, "FAILED TO UPDATE PRODUCT", LocalDateTime.now());
            return DbStatus.ERROR;
        }finally {
            if(entityManager != null){
                entityManager.close();
            }
            return DbStatus.UPDATED;
        }
    }

    @Override
    public Optional<Product> findOne(Long id) {
        Optional<Product> item = Optional.empty();

        if (entityManagerFactory != null && id != null)
        {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();


                item = Optional.of(entityManager.find(Product.class, id));
                Hibernate.initialize(item.get().getStocks());
                Hibernate.initialize(item.get().getCustomer_order());
                Hibernate.initialize(item.get().getEGuarantees());
                Hibernate.initialize(item.get().getProducer().getCountry().getCustomers());
                Hibernate.initialize(item.get().getProducer().getCountry().getProducers());
                Hibernate.initialize(item.get().getProducer().getCountry().getShops());
                Hibernate.initialize(item.get().getProducer().getTrade().getProducers());
                Hibernate.initialize(item.get().getProducer().getProducts());
                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Product, "FAILED TO FIND PRODUCT WITH ID = " + id, LocalDateTime.now());
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
    public Optional<Product> findOneByName(String productName) {
        Optional<Product> item = Optional.empty();

        if (entityManagerFactory != null && productName != null)
        {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();


                Query query = entityManager.createQuery("select p from Product p where p.name = :productName");
                query.setParameter("productName", productName);
                item = Optional.of((Product) query.getSingleResult());

                Hibernate.initialize(item.get().getStocks());
                Hibernate.initialize(item.get().getCustomer_order());
                Hibernate.initialize(item.get().getEGuarantees());
                Hibernate.initialize(item.get().getProducer().getCountry().getCustomers());
                Hibernate.initialize(item.get().getProducer().getCountry().getProducers());
                Hibernate.initialize(item.get().getProducer().getCountry().getShops());
                Hibernate.initialize(item.get().getProducer().getTrade().getProducers());
                Hibernate.initialize(item.get().getProducer().getProducts());
                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Product, "FAILED TO FIND PRODUCT WITH NAME = " + productName, LocalDateTime.now());
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
    public List<Product> findAll() {
        List<Product> items = null;

        if (this.getEntityManagerFactory()!= null)
        {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select p from Product  p");
                items = (List<Product>)query.getResultList();
                items.forEach(c -> Hibernate.initialize(c.getEGuarantees()));
                items.forEach(c -> Hibernate.initialize(c.getCustomer_order()));
                items.forEach(p -> Hibernate.initialize(p.getStocks()));
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
