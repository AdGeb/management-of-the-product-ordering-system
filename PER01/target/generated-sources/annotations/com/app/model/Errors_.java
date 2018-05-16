package com.app.model;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Errors.class)
public abstract class Errors_ {

	public static volatile SingularAttribute<Errors, LocalDateTime> date;
	public static volatile SingularAttribute<Errors, Long> id;
	public static volatile SingularAttribute<Errors, String> message;

	public static final String DATE = "date";
	public static final String ID = "id";
	public static final String MESSAGE = "message";

}

