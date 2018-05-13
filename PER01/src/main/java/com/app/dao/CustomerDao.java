package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.Customer;

import java.util.Optional;

public interface CustomerDao extends GenericDao<Customer> {

    Optional<Customer> findOneByNameSurnameCountry(String name, String surname, String countryName);
}
