package PageObjects;

import org.openqa.selenium.WebDriver;

public class PageObjectManager {
	public VisaLandingPage vlp;
	public TncPage tncp;
	public FormPage fp;
	public resultpage rp;
	public WebDriver driver;
	
	public PageObjectManager(WebDriver driver) {
		this.driver=driver;
	}
	
	public VisaLandingPage getVisaLandingPage() {
		vlp=new VisaLandingPage(driver);
		return vlp;
	}
	public TncPage getTncPage(){
		tncp=new TncPage(driver);
		return tncp;
	}
	public FormPage getFormPage(){
		fp=new FormPage(driver);
		return fp;
	}
	public resultpage getresultpage(){
		rp=new resultpage(driver);
		return rp;
	}


}