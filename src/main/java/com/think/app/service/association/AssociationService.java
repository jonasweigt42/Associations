package com.think.app.service.association;

import java.util.List;

import com.think.app.entity.association.Association;
import com.think.app.service.Service;

public interface AssociationService extends Service<Association>
{

	List<Association> findByUserId(int userId);
}
