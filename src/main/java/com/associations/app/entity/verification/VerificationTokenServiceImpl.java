package com.associations.app.entity.verification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService
{

	@Autowired
	private VerificationTokenDao dao;
	
	@Override
	public List<VerificationToken> findAll()
	{
		return dao.findAll();
	}

	@Override
	public int count()
	{
		return dao.count();
	}

	@Override
	public void update(VerificationToken entity)
	{
		dao.update(entity);
	}

	@Override
	public void save(VerificationToken newEntity)
	{
		dao.save(newEntity);
	}

	@Override
	public void delete(VerificationToken entity)
	{
		dao.delete(entity);
	}

	@Override
	public VerificationToken findByToken(String token)
	{
		return dao.findByToken(token);
	}

}
