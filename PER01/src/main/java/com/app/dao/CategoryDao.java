package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.Category;

import java.util.Optional;

public interface CategoryDao extends GenericDao<Category> {
    Optional<Category> findOneByName(String categoryName) throws Exception;
}
