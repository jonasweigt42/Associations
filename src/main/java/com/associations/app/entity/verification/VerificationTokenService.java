package com.associations.app.entity.verification;

import com.associations.app.entity.Service;

public interface VerificationTokenService extends Service<VerificationToken>
{
	VerificationToken findByToken(String token);
}
