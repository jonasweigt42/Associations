package com.associations.app.entity.user;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.associations.app.entity.GenericDao;

@Repository
public class UserDao extends GenericDao<User>
{
	@PostConstruct
	public void init()
	{
		setClazz(User.class);
	}
	
	@Transactional
	public User findById(int id)
	{
		TypedQuery<User> query = entityManager.createNamedQuery("User.findbyId", User.class);
		query.setParameter(1, id);
		List<User> users = query.getResultList();
		if (!users.isEmpty())
		{
			return users.get(0);
		}
		return null;
	}
	
	@Transactional
	public User findByMailAddress(String mailAddress)
	{
		TypedQuery<User> query = entityManager.createNamedQuery("User.findbymailAddress", User.class);
		query.setParameter(1, mailAddress);
		List<User> users = query.getResultList();
		if (!users.isEmpty())
		{
			return users.get(0);
		}
		return null;
	}
}