package com.associations.app.entity.user;

import com.associations.app.entity.Service;

public interface UserService extends Service<User>
{
	User findByMailAddress(String mailAddress);
	
	User findById(int id);
}
