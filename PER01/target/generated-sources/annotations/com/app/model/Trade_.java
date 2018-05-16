package com.app.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Trade.class)
public abstract class Trade_ {

	public static volatile SingularAttribute<Trade, String> name;
	public static volatile SingularAttribute<Trade, Long> id;
	public static volatile SetAttribute<Trade, Producer> producers;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String PRODUCERS = "producers";

}

