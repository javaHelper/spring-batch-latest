package com.walking.techie.mysqltoxml.orm;

import javax.persistence.Query;
import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class JpaQueryProviderImpl<E> extends AbstractJpaQueryProvider {

	private Class<E> entityClass;
	private String query;

	@Override
	public Query createQuery() {
		return getEntityManager().createNamedQuery(query, entityClass);
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public void setEntityClass(Class<E> entityClazz) {
		this.entityClass = entityClazz;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.isTrue(StringUtils.hasText(query), "Query cannot be empty");
		Assert.notNull(entityClass, "Entity class cannot be NULL");
	}
}