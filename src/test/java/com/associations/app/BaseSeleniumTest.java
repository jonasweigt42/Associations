package com.associations.app;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BaseSeleniumTest
{
	protected static WebDriver driver;

	@BeforeAll
	public static void setUp() throws Exception
	{
		// init webDriver here
		System.setProperty("webdriver.chrome.driver", "src/test/chromedriver");

		driver = new ChromeDriver();
		// final WebDriverWait wait = new WebDriverWait(driver, 10);
		if (driver == null)
		{
			Assertions.fail("WebDriver not available");
		}
		driver.get("http://127.0.0.1:8080/");
	}

	@AfterAll
	public static void tearDown() throws Exception
	{
		// kill webdriver / Browser here
		driver.close();
		driver.quit();
	}
}
