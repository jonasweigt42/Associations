package com.think.app.entity.association;

import java.util.List;

import com.think.app.entity.Service;

public interface AssociationService extends Service<Association>
{

	List<Association> findByUserId(int userId);
}