package com.edassist.selenium;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/main/test/com/edlink/tams4/selenium/features")
public class RunCucumberTests {

}
