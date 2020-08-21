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
	EntityManager entityManager;

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

	@SuppressWarnings("unchecked")
	@Transactional
	public List<T> getRandomEntries(int number)
	{
		List<T> list = new ArrayList<T>(number);

		while (list.size() != number)
		{
			Random random = new Random();
			int randomNumber = random.nextInt(count());
			Query selectQuery = entityManager.createQuery("SELECT c FROM " + clazz.getName() + " c");
			selectQuery.setFirstResult(randomNumber);
			selectQuery.setMaxResults(1);
			T result = (T) selectQuery.getSingleResult();
			if (!list.contains(result))
			{
				list.add(result);
			}
		}
		return list;

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
