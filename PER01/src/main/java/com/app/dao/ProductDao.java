package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.Product;

import java.util.Optional;

public interface ProductDao extends GenericDao<Product> {
    public Optional<Product> findOneByName(String productName);
}
