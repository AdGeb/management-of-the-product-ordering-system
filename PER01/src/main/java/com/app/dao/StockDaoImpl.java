package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbTables;
import com.app.model.Stock;
import com.app.service.ErrorService;
import com.app.service.ErrorServiceImpl;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StockDaoImpl extends AbstractGenericDao<Stock> implements StockDao {

    @Override
    public Optional<Stock> findOne(Long id) {
        Optional<Stock> item = Optional.empty();
        ErrorService errorService = new ErrorServiceImpl();
        if (this.getEntityManagerFactory() != null && id != null) {
            EntityManager entityManager = this.getEntityManagerFactory().createEntityManager();
            EntityTransaction tx = entityManager.getTransaction();


            try {
                tx.begin();

                item = Optional.of((Stock) entityManager.find(Stock.class, id));
                Hibernate.initialize(item.get().getProduct().getStocks());

                tx.commit();
            } catch (Exception e) {
                if (tx != null) {
                    tx.rollback();
                }
                errorService.addErrror(DbTables.Stock, "FAILED TO FIND ENTITY WITH ID = " + id, LocalDateTime.now());
            } finally {
                if (entityManager != null) {
                    entityManager.close();
                }
            }
        }
        return item;
    }

}
