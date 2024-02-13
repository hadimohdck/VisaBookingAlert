package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;


public class TestBase {
	public WebDriver driver;
	public WebDriver WebDriverManager() throws IOException {
		
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\Global.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String url=prop.getProperty("QAurl");
		String browser_properties=prop.getProperty("browser");
		String browser_maven=System.getProperty("browser");
		String browser=browser_maven!=null ? browser_maven:browser_properties;
		System.out.println(browser);
		 // Wait for up to 10 seconds
		if(driver==null)
		{
			if(browser.equalsIgnoreCase("chrome")) 
			{	System.getProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--remote-allow-origins=*");

				// Add options to make Selenium-driven browser look more like a regular user's browser
				options.addArguments("--disable-blink-features=AutomationControlled"); // Remove "navigator.webdriver" flag
				options.addArguments("--disable-infobars"); // Disable infobars
				options.addArguments("--start-maximized"); // Start the browser maximized
				options.addArguments("--disable-extensions"); // Disable extensions

				// Add a fake user-agent to make it look like a regular browser
//				options.addArguments("user-agent=Mozilla/5.0 (Macintosh; Intel Mac OS X 14_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/119.0.0.0 Safari/537.36");
//
//				options.setCapability("unexpectedAlertBehavior", "accept");
//
//				Map<String, Object> cloudOptions = new HashMap<>();
//				cloudOptions.put("unexpectedAlertBehavior", "accept");
				driver=new ChromeDriver(options);
			}
			if(browser.equalsIgnoreCase("firefox"))
			{	driver=new FirefoxDriver();
				System.getProperty("webdriver.gecho.driver",System.getProperty("user.dir")+"\\src\\test\\resources\\geckodriver.exe");			
			}

			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			driver.get(url);
			
		}

	    return driver;
	    
		
		}

}
