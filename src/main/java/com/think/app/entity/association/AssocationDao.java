package com.think.app.entity.association;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.think.app.entity.GenericDao;

@Repository
public class AssocationDao extends GenericDao<Association>
{
	@PostConstruct
	public void init()
	{
		setClazz(Association.class);
	}

//	@SuppressWarnings("unchecked")
//	@Transactional
//	public List<Association> findByUserAndDate(int userId, Date date)
//	{
//		return entityManager.createQuery("SELECT a FROM " + Association.class.getName() + " a WHERE a.userId = "
//				+ userId + " AND a.date = " + date).getResultList();
//	}
}
