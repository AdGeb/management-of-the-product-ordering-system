package com.app.dao.generic;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DbConnection {
    private static DbConnection ourInstance = new DbConnection();
    public static DbConnection getInstance() {
        return ourInstance;
    }

    private EntityManagerFactory entityManagerFactory
            = Persistence.createEntityManagerFactory("HIBERNATE_PERSISTENCE");


    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    private DbConnection() {
    }

    public void close()
    {
        if(entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }
}
