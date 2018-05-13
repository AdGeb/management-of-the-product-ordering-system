package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.Shop;

import java.util.Optional;

public interface ShopDao extends GenericDao<Shop> {

    Optional<Shop> findOneByName(String shopName);
}
