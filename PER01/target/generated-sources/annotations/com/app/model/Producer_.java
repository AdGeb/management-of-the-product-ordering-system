package com.app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Producer.class)
public abstract class Producer_ {

	public static volatile SingularAttribute<Producer, Country> country;
	public static volatile SingularAttribute<Producer, Trade> trade;
	public static volatile SingularAttribute<Producer, String> name;
	public static volatile SingularAttribute<Producer, Long> id;
	public static volatile SetAttribute<Producer, Product> products;

	public static final String COUNTRY = "country";
	public static final String TRADE = "trade";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String PRODUCTS = "products";

}

