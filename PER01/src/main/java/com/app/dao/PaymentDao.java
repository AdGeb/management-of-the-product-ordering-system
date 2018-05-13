package com.app.dao;

import com.app.dao.generic.GenericDao;
import com.app.model.EPayment;
import com.app.model.Payment;

import java.util.Optional;

public interface PaymentDao extends GenericDao<Payment> {

    Optional<Payment> findOneByName(EPayment ePayment);

}
