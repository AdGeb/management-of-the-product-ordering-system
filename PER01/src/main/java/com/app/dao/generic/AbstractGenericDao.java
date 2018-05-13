package com.app.dao.generic;

import com.app.service.ErrorService;
import com.app.service.ErrorServiceImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

// DOROBIC STATUSY BLEDOW
public abstract class AbstractGenericDao<T> implements GenericDao<T> {

    private Class<T> entity = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    private EntityManagerFactory entityManagerFactory = DbConnection.getInstance().getEntityManagerFactory();

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    //private ErrorService errorService = new ErrorServiceImpl();

    public DbStatus add(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        ErrorService errorService = new ErrorServiceImpl();
        try {
            tx.begin();

            entityManager.merge(t);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            errorService.addErrror(ErrorServiceImpl.findProperTable(entity.getCanonicalName()), "FAILED TO ADD NEW ENTITY", LocalDateTime.now());
            return DbStatus.ERROR;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            return DbStatus.INSERTED;
        }
    }

    public DbStatus update(T t) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        ErrorService errorService = new ErrorServiceImpl();
        try {
            tx.begin();

            entityManager.merge(t);

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            errorService.addErrror(ErrorServiceImpl.findProperTable(entity.getCanonicalName()), "FAILED TO UPDATE ENTITY", LocalDateTime.now());
            return DbStatus.ERROR;
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
            return DbStatus.UPDATED;
        }
    }

    public DbStatus delete(Long id) {
        if (entityManagerFactory != null && id != null) {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();
            ErrorService errorService = new ErrorServiceImpl();

            try {
                tx.begin();

                T t = entityManager.find(entity, id);
                entityManager.remove(t);

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                errorService.addErrror(ErrorServiceImpl.findProperTable(entity.getCanonicalName()), "FAILED TO REMOVE ENTITY", LocalDateTime.now());
                return DbStatus.ERROR;
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
                return DbStatus.DELETED;
            }
        }
        return DbStatus.ERROR;
    }

    public Optional<T> findOne(Long id) {
        Optional<T> item = Optional.empty();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        ErrorService errorService = new ErrorServiceImpl();
        try {
            tx.begin();
            item = Optional.of(entityManager.find(entity, id));
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            errorService.addErrror(ErrorServiceImpl.findProperTable(entity.getCanonicalName()), "FAILED TO FIND AN ENTITY", LocalDateTime.now());
            return Optional.empty();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return item;
    }

    public List<T> findAll() {
        List<T> items = null;
        ErrorService errorService = new ErrorServiceImpl();
        if (entityManagerFactory != null)
        {
            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();

            try
            {
                tx.begin();

                Query query = entityManager.createQuery("select t from " + entity.getCanonicalName() + " t");
                items = (List<T>)query.getResultList();

                tx.commit();
            }
            catch (Exception e)
            {
                if (tx != null)
                {
                    tx.rollback();
                }
                errorService.addErrror(ErrorServiceImpl.findProperTable(entity.getCanonicalName()), "FAILED TO FIND ALL ENTITIES", LocalDateTime.now());
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
