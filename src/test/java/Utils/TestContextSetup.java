package Utils;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import PageObjects.PageObjectManager;

public class TestContextSetup {
	
	public WebDriver driver;
	public PageObjectManager pom;
	public TestBase testbase;
	public GenericUtils genericutils;
	public String Testype="Web";
	
	public TestContextSetup() throws IOException {
		testbase=new TestBase();
		pom=new PageObjectManager(testbase.WebDriverManager());
		genericutils=new GenericUtils(testbase.WebDriverManager());
	}

}
