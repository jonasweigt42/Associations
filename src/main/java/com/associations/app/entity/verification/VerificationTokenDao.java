package com.associations.app.entity.verification;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.associations.app.entity.GenericDao;

@Repository
public class VerificationTokenDao extends GenericDao<VerificationToken>
{
	@PostConstruct
	public void init()
	{
		setClazz(VerificationToken.class);
	}
	
	@Transactional
	public VerificationToken findByToken(String token)
	{
		TypedQuery<VerificationToken> query = entityManager.createNamedQuery("VerificationToken.findbyToken", VerificationToken.class);
		query.setParameter(1, token);
		List<VerificationToken> tokens = query.getResultList();
		if (!tokens.isEmpty())
		{
			return tokens.get(0);
		}
		return null;
	}
}
