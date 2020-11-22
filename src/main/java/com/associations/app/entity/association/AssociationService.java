package com.associations.app.entity.association;

import java.util.List;

import com.associations.app.entity.Service;

public interface AssociationService extends Service<Association>
{

	List<Association> findByUserId(int userId);
	
	List<Association> findByWordId(int wordId);
}