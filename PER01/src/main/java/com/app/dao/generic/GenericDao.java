package com.app.dao.generic;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T> {
    DbStatus add(T t);
    DbStatus update(T t);
    DbStatus delete(Long id);
    Optional<T> findOne(Long id);
    List<T> findAll();
}
