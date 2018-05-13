package com.app.dao;

import com.app.dao.generic.AbstractGenericDao;
import com.app.dao.generic.DbConnection;
import com.app.dao.generic.DbTables;
import com.app.model.EPayment;
import com.app.model.Payment;
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
import java.util.Optional;

public class PaymentDaoImpl extends AbstractGenericDao<Payment> implements PaymentDao {

    private EntityManagerFactory entityManagerFactory = this.getEntityManagerFactory();
    private ErrorService errorService = new ErrorServiceImpl();

    @Override
    public Optional<Payment> findOneByName(EPayment ePayment) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        Optional<Payment> item = Optional.empty();

        try {
            tx.begin();

            Query query = entityManager.createQuery("select p from Payment p where p.payment = :payment");
            query.setParameter("payment", ePayment);
            item = Optional.of((Payment)query.getSingleResult());
            Hibernate.initialize(item.get().getCustomer_order());
            tx.commit();
        } catch (Exception e){
            System.err.println("THERE IS NO SUCH PAYMENT METHOD");
            if(tx != null){
                tx.rollback();
            }
            errorService.addErrror(DbTables.Payment, "FAILED TO FIND PAYMENT BY NAME", LocalDateTime.now() );
        }finally {
            entityManager.close();
        }
        return item;
    }
}
