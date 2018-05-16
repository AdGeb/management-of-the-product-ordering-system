package com.app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Payment.class)
public abstract class Payment_ {

	public static volatile SetAttribute<Payment, CustomerOrder> customer_order;
	public static volatile SingularAttribute<Payment, EPayment> payment;
	public static volatile SingularAttribute<Payment, Long> id;

	public static final String CUSTOMER_ORDER = "customer_order";
	public static final String PAYMENT = "payment";
	public static final String ID = "id";

}

