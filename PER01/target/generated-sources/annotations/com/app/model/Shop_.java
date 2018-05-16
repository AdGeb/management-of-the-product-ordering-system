package com.app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Shop.class)
public abstract class Shop_ {

	public static volatile SingularAttribute<Shop, Country> country;
	public static volatile SingularAttribute<Shop, String> name;
	public static volatile SingularAttribute<Shop, Long> id;
	public static volatile SetAttribute<Shop, Stock> stocks;

	public static final String COUNTRY = "country";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String STOCKS = "stocks";

}

