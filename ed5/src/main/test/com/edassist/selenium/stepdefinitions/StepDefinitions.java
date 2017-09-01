package com.edassist.selenium.stepdefinitions;

import java.io.File;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

public class StepDefinitions {

	WebDriver driver;
	String baseURL = "";

	@Before
	public void setup() {
		baseURL = "https://coaedawebstg02/TAMS4Web/login/";
	}

	@Given("^I'm on the (.*) login page$")
	public void imOnPage(String pageName) {
		File file = new File("src/main/test/com/edlink/tams4/selenium/drivers/chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver();
		driver.get(baseURL + pageName);
	}

	@Then("^title should be (.*)$")
	public void title_should_be(String pageTitle) {
		Assert.assertEquals(pageTitle, driver.getTitle());
		driver.quit();
	}

}
