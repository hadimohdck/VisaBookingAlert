package PageObjects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class resultpage {
    public WebDriver driver;
    public resultpage(WebDriver driver){
        this.driver=driver;
    }
    @Getter
    public By message= By.xpath("//li[@class='errorMessage']");
    @Getter
    public By activeheader=By.xpath("//li[@class='antcl_active']");

    public String printmessage(){
        return driver.findElement(message).getText();
    }

    public String getheadertext(){
        return driver.findElement(activeheader).getText();
    }
}
