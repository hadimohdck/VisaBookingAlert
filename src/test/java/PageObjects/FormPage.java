package PageObjects;

import lombok.Getter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class FormPage {
    public WebDriver driver;
    @Getter
    public By feild1= By.id("xi-sel-400");
    @Getter
    public By feild2= By.id("xi-sel-422");
    @Getter
    public By feild3= By.id("xi-sel-427");
    @Getter
    public By feild4= By.id("xi-sel-428");
    @Getter
    public By selection1=By.xpath("//*[@id='xi-div-30']/div[2]/label");
    @Getter
    public By selection2=By.xpath("//label[@for='SERVICEWAHL_DE_436-0-2-4']");
    @Getter
    public By selection3=By.xpath("//*[@id='inner-436-0-2']/div/div[6]/div/div[3]/label");
    @Getter
    public By wieterbtn=By.id("applicationForm:managedForm:proceed");
    public FormPage(WebDriver driver){
        this.driver=driver;
    }
    public void selectfeild1(){
        WebElement selectElement = driver.findElement(feild1);
        Select select = new Select(selectElement);
        select.selectByVisibleText("Indien");
    }
    public void selectfeild2(){
        WebElement selectElement = driver.findElement(feild2);
        Select select = new Select(selectElement);
        select.selectByValue("1");
    }
    public void selectfeild3(){
        WebElement selectElement = driver.findElement(feild3);
        Select select = new Select(selectElement);
        select.selectByValue("1");
    }
    public void selectfeild4(){
        WebElement selectElement = driver.findElement(feild4);
        Select select = new Select(selectElement);
        select.selectByValue("436-0");
    }
    public void clickselection1(){
        driver.findElement(selection1).click();
    }
    public void clickselection2(){
        driver.findElement(selection2).click();
    }
    public void clickselection3(){
        driver.findElement(selection3).click();
    }
    public void clickWeiterBtn(){
        driver.findElement(wieterbtn).click();
    }
}
