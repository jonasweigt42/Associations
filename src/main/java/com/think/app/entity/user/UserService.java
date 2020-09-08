package com.think.app.entity.user;

import com.think.app.entity.Service;

public interface UserService extends Service<User>
{
	User getUserByMailAddress(String mailAddress);
}
