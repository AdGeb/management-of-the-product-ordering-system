package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.Trade;

import java.util.Optional;

public interface TradeDao extends GenericDao<Trade> {
    Optional<Trade> findOneByName(String tradeName);
}
