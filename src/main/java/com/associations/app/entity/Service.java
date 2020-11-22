package com.associations.app.entity;

import java.util.List;

public interface Service<T>
{
	List<T> findAll();

	int count();

	void update(T entity);

	void save(T newEntity);
	
	void delete(T entity);
}