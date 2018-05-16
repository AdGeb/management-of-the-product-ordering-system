package com.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CustomerOrder.class)
public abstract class CustomerOrder_ {

	public static volatile SingularAttribute<CustomerOrder, LocalDateTime> date;
	public static volatile SingularAttribute<CustomerOrder, Product> product;
	public static volatile SingularAttribute<CustomerOrder, Integer> quantity;
	public static volatile SingularAttribute<CustomerOrder, BigDecimal> discount;
	public static volatile SingularAttribute<CustomerOrder, Payment> payment;
	public static volatile SingularAttribute<CustomerOrder, Long> id;
	public static volatile SingularAttribute<CustomerOrder, Customer> customer;

	public static final String DATE = "date";
	public static final String PRODUCT = "product";
	public static final String QUANTITY = "quantity";
	public static final String DISCOUNT = "discount";
	public static final String PAYMENT = "payment";
	public static final String ID = "id";
	public static final String CUSTOMER = "customer";

}

