package com.think.app.login;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.think.app.BaseSeleniumTest;
import com.think.app.facades.LoginElements;
import com.think.app.facades.LoginFacade;

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
