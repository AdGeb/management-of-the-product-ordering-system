package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbTables;
import com.app.model.Category;
import com.app.model.Customer;
import com.app.service.ErrorService;
import com.app.service.ErrorServiceImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryDaoImpl extends AbstractGenericDao<Category> implements CategoryDao {

    private ErrorService errorService = new ErrorServiceImpl();

    @Override
    public Optional<Category> findOne(Long id) {
        Optional<Category> item = Optional.empty();

        if (this.getEntityManagerFactory() != null && id != null) {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                item = Optional.of((Category) entityManager.find(Category.class, id));
                Hibernate.initialize(item.get().getProducts());

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Category, (";FAILED TO FIND ENTITY WITH ID = " + id), LocalDateTime.now());
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }
        return item;
    }

    @Override
    public Optional<Category> findOneByName(String categoryName) throws Exception {
        Optional<Category> item = Optional.empty();

        if (this.getEntityManagerFactory() != null && categoryName != null) {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                Query query = entityManager.createQuery("select c from Category c  where c.name = :categoryName");
                query.setParameter("categoryName", categoryName);
                item = Optional.of((Category)query.getSingleResult());
                Hibernate.initialize(item.get().getProducts());

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                //throw new Exception(DbTables.Category + ";FAILED TO FIND ENTITY WITH NAME = " + categoryName);
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }
        return item;
    }

    @Override
    public List<Category> findAll() {
        List<Category> items = null;

        if (this.getEntityManagerFactory() != null)
        {
            items = new ArrayList<>();
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select c from Category c");
                items = (List<Category>)query.getResultList();
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
        }

        return items;
    }
}
