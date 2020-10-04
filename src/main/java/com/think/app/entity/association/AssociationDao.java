package com.think.app.entity.association;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.think.app.entity.GenericDao;

@Repository
public class AssociationDao extends GenericDao<Association>
{
	@PostConstruct
	public void init()
	{
		setClazz(Association.class);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Association> findByUserId(int userId)
	{
		return entityManager.createQuery("SELECT a FROM " + Association.class.getName() + " a WHERE a.userId = "
				+ userId).getResultList();
	}
}