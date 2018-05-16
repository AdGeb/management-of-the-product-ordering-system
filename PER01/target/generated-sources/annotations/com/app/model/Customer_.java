package com.app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, Country> country;
	public static volatile SingularAttribute<Customer, String> surname;
	public static volatile SetAttribute<Customer, CustomerOrder> customer_order;
	public static volatile SingularAttribute<Customer, String> name;
	public static volatile SingularAttribute<Customer, Long> id;
	public static volatile SingularAttribute<Customer, Integer> age;

	public static final String COUNTRY = "country";
	public static final String SURNAME = "surname";
	public static final String CUSTOMER_ORDER = "customer_order";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String AGE = "age";

}

