package com.think.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public abstract class GenericDao<T extends Serializable>
{

	private Class<T> clazz;

	@PersistenceContext
	protected EntityManager entityManager;

	public void setClazz(Class<T> clazzToSet)
	{
		this.clazz = clazzToSet;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<T> findAll()
	{
		return entityManager.createQuery("from " + clazz.getName()).getResultList();
	}

	@Transactional
	public int count()
	{
		return ((Number) entityManager.createQuery("SELECT COUNT(c) FROM " + clazz.getName() + " c").getSingleResult())
				.intValue();
	}

	@Transactional
	public List<T> getRandomEntries(int number)
	{
		List<T> list = new ArrayList<>(number);
		int countedObjects = count();
		if(countedObjects <= number)
		{
			return findAll();
		}
		prepareListWithRandomElements(number, list, countedObjects);
		return list;

	}

	@SuppressWarnings("unchecked")
	private void prepareListWithRandomElements(int number, List<T> list, int countedObjects)
	{
		Random random = new Random();
		while (list.size() != number)
		{
			int randomNumber = random.nextInt(countedObjects);
			Query selectQuery = entityManager.createQuery("SELECT c FROM " + clazz.getName() + " c");
			selectQuery.setFirstResult(randomNumber);
			selectQuery.setMaxResults(1);
			T result = (T) selectQuery.getSingleResult();
			if (!list.contains(result))
			{
				list.add(result);
			}
		}
	}

	@Transactional
	public void save(T entity)
	{
		entityManager.persist(entity);
	}

	@Transactional
	public void update(T entity)
	{
		entityManager.merge(entity);
	}

	@Transactional
	public void delete(T entity)
	{
		entityManager.remove(entity);
	}
}