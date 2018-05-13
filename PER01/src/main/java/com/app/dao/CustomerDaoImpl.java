package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbStatus;
import com.app.dao.generic.DbTables;
import com.app.model.Customer;
import com.app.service.ErrorService;
import com.app.service.ErrorServiceImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class CustomerDaoImpl extends AbstractGenericDao<Customer> implements CustomerDao {

    private ErrorService errorService = new ErrorServiceImpl();
//    private EntityManagerFactory this.getEntityManagerFactory() = this.getEntityManagerFactory();


    @Override
    public DbStatus add(Customer customer) {
        EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            entityManager.merge(customer);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            errorService.addErrror(DbTables.Customer, "FAILED TO ADD CUSTOMER", LocalDateTime.now());
            return DbStatus.ERROR;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            return DbStatus.INSERTED;
        }
    }

    @Override
    public DbStatus update(Customer customer) {
        EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();

        try {
            tx.begin();

            entityManager.merge(customer);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            errorService.addErrror(DbTables.Customer, "FAILED TO UPADTE CUSTOMER", LocalDateTime.now());
            return DbStatus.ERROR;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            return DbStatus.UPDATED;
        }
    }

    @Override
    public Optional<Customer> findOne(Long id) {
        Optional<Customer> item = Optional.empty();

        if (this.getEntityManagerFactory() != null && id != null) {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                item = Optional.of((Customer) entityManager.find(Customer.class, id));
                Hibernate.initialize(item.get().getCustomer_order());
                Hibernate.initialize(item.get().getCountry().getCustomers());
                Hibernate.initialize(item.get().getCountry().getProducers());
                Hibernate.initialize(item.get().getCountry().getShops());


                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Category, ("FAILED TO FIND ENTITY WITH ID = " + id), LocalDateTime.now());
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }
        return item;
    }

    @Override
    public Optional<Customer> findOneByNameSurnameCountry(String name, String surname, String countryName) {
        Optional<Customer> item = Optional.empty();
        if(name != null && surname != null && countryName != null && this.getEntityManagerFactory() != null){
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();
            try {
                tx.begin();

                Query query = entityManager.createQuery("select c from Customer c where c.name = :name and c.surname = :surname and c.country.countryName = :countryName");
                query.setParameter("name", name);
                query.setParameter("surname", surname);
                query.setParameter("countryName", countryName);
                item = Optional.of((Customer) query.getSingleResult());

                tx.commit();
            }catch (Exception e){
                if(tx != null){
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Customer, ";FAILED TO FIND CUSTOMER WITH NAME = " + name + ", SURNAME = " + surname + ", COUNTRYNAME = " + countryName, LocalDateTime.now());
            }finally {
                if(entityManager != null){
                    entityManager.close();
                }
            }
        }
        return item;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> items = null;

        if (this.getEntityManagerFactory()!= null)
        {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select c from Customer c");
                items = (List<Customer>)query.getResultList();
                items.forEach(c -> Hibernate.initialize(c.getCustomer_order()));

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

