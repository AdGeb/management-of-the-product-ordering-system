package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.Producer;

import java.util.Optional;

public interface ProducerDao extends GenericDao<Producer> {

    public Optional<Producer> findOneByName(String name) throws Exception;
}
