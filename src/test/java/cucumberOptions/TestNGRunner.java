package cucumberOptions;

import org.testng.annotations.DataProvider;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features="src/test/java/features/web",glue="StepDefenitions",monochrome=true,tags="@tag1",plugin={"pretty","html:target/cucumber.html","json:target/cucumber.json","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:","rerun:target/failed_screenshot.txt"})
public class TestNGRunner extends AbstractTestNGCucumberTests{
	
		@Override
		@DataProvider(parallel=false)
		public Object[][] scenarios(){
			
			return super.scenarios();		}

}
