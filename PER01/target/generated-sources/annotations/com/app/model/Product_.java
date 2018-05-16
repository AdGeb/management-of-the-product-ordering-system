package com.app.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Product.class)
public abstract class Product_ {

	public static volatile SetAttribute<Product, EGuarantee> eGuarantees;
	public static volatile SingularAttribute<Product, BigDecimal> price;
	public static volatile SetAttribute<Product, CustomerOrder> customer_order;
	public static volatile SingularAttribute<Product, String> name;
	public static volatile SingularAttribute<Product, Producer> producer;
	public static volatile SingularAttribute<Product, Long> id;
	public static volatile SingularAttribute<Product, Category> category;
	public static volatile SetAttribute<Product, Stock> stocks;

	public static final String E_GUARANTEES = "eGuarantees";
	public static final String PRICE = "price";
	public static final String CUSTOMER_ORDER = "customer_order";
	public static final String NAME = "name";
	public static final String PRODUCER = "producer";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String STOCKS = "stocks";

}

