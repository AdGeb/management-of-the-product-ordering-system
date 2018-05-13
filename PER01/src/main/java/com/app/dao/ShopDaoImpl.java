package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbTables;
import com.app.model.Shop;
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

public class ShopDaoImpl extends AbstractGenericDao<Shop> implements ShopDao{

    private ErrorService errorService = new ErrorServiceImpl();


    @Override
    public Optional<Shop> findOne(Long id) {
        Optional<Shop> item = Optional.empty();

        if (this.getEntityManagerFactory() != null && id != null) {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try {
                tx.begin();

                item = Optional.of((Shop) entityManager.find(Shop.class, id));
                Hibernate.initialize(item.get().getStocks());
                Hibernate.initialize(item.get().getCountry().getCustomers());
                Hibernate.initialize(item.get().getCountry().getProducers());
                Hibernate.initialize(item.get().getCountry().getShops());

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Shop, "FAILED TO FIND ENTITY WITH ID = " + id, LocalDateTime.now());
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }
        return item;
    }

    @Override
    public Optional<Shop> findOneByName(String shopName) {
        Optional<Shop> item = Optional.empty();

        if (this.getEntityManagerFactory()!= null && shopName != null)
        {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();


                Query query = entityManager.createQuery("select s from Shop s where s.name = :shopName");
                query.setParameter("shopName", shopName);
                item = Optional.of((Shop) query.getSingleResult());


                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Product, "FAILED TO FIND SHOP WITH NAME = " + shopName, LocalDateTime.now());
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
    public List<Shop> findAll() {
        List<Shop> items = null;

        if (this.getEntityManagerFactory()!= null)
        {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select s from Shop s");
                items = (List<Shop>)query.getResultList();
                items.forEach(s -> Hibernate.initialize(s.getStocks()));

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
