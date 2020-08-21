package com.think.app.service.user;

import com.think.app.entity.user.User;
import com.think.app.service.AssociationService;

public interface UserService extends AssociationService<User>
{
	User getUserByMailAddress(String mailAddress);
}
