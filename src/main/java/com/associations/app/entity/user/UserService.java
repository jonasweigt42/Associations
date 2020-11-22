package com.associations.app.entity.user;

import com.associations.app.entity.Service;

public interface UserService extends Service<User>
{
	User getUserByMailAddress(String mailAddress);
}
