package com.app.dao.generic;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();
    public static DbConnection getInstance() {
        return ourInstance;
    }

    /*private SessionFactory sessionFactory
            = new Configuration().configure().buildSessionFactory();*/

    private EntityManagerFactory entityManagerFactory
            = Persistence.createEntityManagerFactory("HIBERNATE_PERSISTENCE");

    /*public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    private DbConnection() {
    }

    public void close()
    {
        /*if (sessionFactory != null)
        {
            sessionFactory.close();
        }*/
        if(entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }
}
