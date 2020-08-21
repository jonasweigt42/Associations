package com.himo.app.service;

import java.util.List;

public interface AssociationService<T>
{
	List<T> findAll();

	int count();

	void update(T entity);

	void save(T newEntity);
	
	void delete(T entity);
	
	List<T> getRandomEntries(int number);
}
