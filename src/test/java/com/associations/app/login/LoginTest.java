package com.associations.app.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.associations.app.BaseSeleniumTest;
import com.associations.app.facades.LoginElements;
import com.associations.app.facades.LoginFacade;

public class LoginTest extends BaseSeleniumTest
{
	private LoginFacade loginFacade = new LoginFacade(new LoginElements(driver));

	@Test
	public void testSuccessfulLogin() throws Exception
	{
		loginFacade.clickMainViewLoginButton();
		loginFacade.sendKeysToUsername("jonas@test.at");
		loginFacade.sendKeysToPassword("jonas");
		loginFacade.clickSubmitLoginButton();
		Thread.sleep(1000);
		Assertions.assertEquals("Logout", loginFacade.getMainViewLoginButtonText());
	}
	
}
