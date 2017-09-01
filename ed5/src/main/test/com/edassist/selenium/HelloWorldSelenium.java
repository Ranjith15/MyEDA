package com.edassist.selenium;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class HelloWorldSelenium {

	@Test
	public void loadTestThisWebsite() {
		File file = new File("src/main/test/com/edlink/tams4/selenium/drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		WebDriver driver = new ChromeDriver();

		driver.get("https://coaedawebstg02/TAMS4Web/login/admin");
		Assert.assertEquals("TAMS Desktop", driver.getTitle());
		driver.quit();
	}
}
