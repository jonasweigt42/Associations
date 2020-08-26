package com.think.app.service.association;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.think.app.entity.association.AssocationDao;
import com.think.app.entity.association.Association;

@Service
public class AssiciationServiceImpl implements AssociationService
{
	
	@Autowired
	private AssocationDao dao;

	@Override
	public List<Association> findAll()
	{
		return dao.findAll();
	}

	@Override
	public int count()
	{
		return dao.count();
	}

	@Override
	public void update(Association association)
	{
		dao.update(association);
	}

	@Override
	public void save(Association newEntity)
	{
		dao.save(newEntity);
	}

	@Override
	public void delete(Association association)
	{
		dao.delete(association);
	}

	@Override
	public List<Association> findByUserId(int userId)
	{
		return dao.findByUserId(userId);
	}

}
