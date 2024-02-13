package PageObjects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class VisaLandingPage {
    public WebDriver driver;



    @Getter
    public By termenbuchenbutton=By.xpath("//a[contains(text(), 'Termin buchen')]");


    public VisaLandingPage(WebDriver driver) {
        this.driver=driver;
    }
    public String gettitleofPage() {
        return driver.getTitle();

    }
    public void clicktermenbuchenbutton(){
        driver.findElement(termenbuchenbutton).click();
    }
}
