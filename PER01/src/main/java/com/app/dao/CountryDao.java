package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.Country;

import java.util.Optional;

public interface CountryDao extends GenericDao<Country> {

    Optional<Country> findOneByName(String countryName);
}
