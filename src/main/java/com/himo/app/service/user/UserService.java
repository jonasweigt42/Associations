package com.himo.app.service.user;

import com.himo.app.entity.user.User;
import com.himo.app.service.AssociationService;

public interface UserService extends AssociationService<User>
{
	User getUserByMailAddress(String mailAddress);
}
