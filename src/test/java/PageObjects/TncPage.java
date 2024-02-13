package PageObjects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class TncPage {
    public WebDriver driver;
    @Getter
    public By accepttncCB=By.id("xi-cb-1");

    public By wieterbtn=By.xpath("//span[contains(text(), 'Weiter')]");
    public TncPage(WebDriver driver){
        this.driver=driver;
    }
    public void clickaccepttncCB(){
        driver.findElement(accepttncCB).click();
    }
    public void clickwieterbtn(){
        driver.findElement(wieterbtn).click();
    }

}
