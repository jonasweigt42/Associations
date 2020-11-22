package com.associations.app.facades;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.associations.app.constants.ComponentIdConstants;

public class LoginElements
{
	private WebDriver driver;

	public LoginElements(WebDriver driver)
	{
		this.driver = driver;
	}

	public WebElement getMainViewLoginButton()
	{
		return driver.findElement(By.id(ComponentIdConstants.LOGIN_BUTTON));
	}

	public WebElement getUsernameInputField()
	{
		return driver.findElement(By.name("username"));
	}

	public WebElement getPasswordInputField()
	{
		return driver.findElement(By.name("password"));
	}

	public WebElement getSubmitLoginButton()
	{
		List<WebElement> elements = driver.findElements(By.tagName("vaadin-button"));
		for (WebElement element : elements)
		{
			if (element.getText().equals("Log in"))
			{
				return element;
			}
		}
		return null;
	}
}
