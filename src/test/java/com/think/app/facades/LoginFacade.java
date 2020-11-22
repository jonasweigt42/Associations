package com.think.app.facades;

public class LoginFacade
{
	private LoginElements loginElements;

	public LoginFacade(LoginElements loginElements)
	{
		this.loginElements = loginElements;
	}
	
	public void clickMainViewLoginButton()
	{
		loginElements.getMainViewLoginButton().click();
	}
	
	public String getMainViewLoginButtonText()
	{
		return loginElements.getMainViewLoginButton().getText();
	}
	
	public void sendKeysToUsername(CharSequence... keysToSend)
	{
		loginElements.getUsernameInputField().sendKeys(keysToSend);
	}
	
	public void sendKeysToPassword(CharSequence... keysToSend)
	{
		loginElements.getPasswordInputField().sendKeys(keysToSend);
	}
	
	public void clickSubmitLoginButton()
	{
		loginElements.getSubmitLoginButton().click();
	}
	
}
