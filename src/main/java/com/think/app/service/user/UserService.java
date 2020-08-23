package com.think.app.service.user;

import com.think.app.entity.user.User;
import com.think.app.service.Service;

public interface UserService extends Service<User>
{
	User getUserByMailAddress(String mailAddress);
}
