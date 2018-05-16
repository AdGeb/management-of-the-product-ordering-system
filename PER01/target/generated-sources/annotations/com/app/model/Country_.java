package com.app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Country.class)
public abstract class Country_ {

	public static volatile SetAttribute<Country, Shop> shops;
	public static volatile SingularAttribute<Country, Long> id;
	public static volatile SingularAttribute<Country, String> countryName;
	public static volatile SetAttribute<Country, Customer> customers;
	public static volatile SetAttribute<Country, Producer> producers;

	public static final String SHOPS = "shops";
	public static final String ID = "id";
	public static final String COUNTRY_NAME = "countryName";
	public static final String CUSTOMERS = "customers";
	public static final String PRODUCERS = "producers";

}

