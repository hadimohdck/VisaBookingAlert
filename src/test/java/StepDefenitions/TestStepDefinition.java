package StepDefenitions;

import PageObjects.FormPage;
import PageObjects.TncPage;
import PageObjects.VisaLandingPage;
import PageObjects.resultpage;
import Utils.GenericUtils;
import Utils.TestContextSetup;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import javax.mail.MessagingException;
import java.io.IOException;

public class TestStepDefinition {
    public WebDriver driver;
    public TestContextSetup ts;
    public VisaLandingPage vlp;
    public TncPage tncp;
    public FormPage fp;
    public resultpage rp;

    public TestStepDefinition(TestContextSetup ts) {
        this.ts = ts;
        this.vlp = ts.pom.getVisaLandingPage();
        this.tncp = ts.pom.getTncPage();
        this.fp = ts.pom.getFormPage();
        this.rp = ts.pom.getresultpage();

    }

    @Given("User is on the VISA booking landing page")
    public void User_is_on_the_VISA_Landing_page() throws InterruptedException {

        Assert.assertTrue(vlp.gettitleofPage().contains("Termin buchen"));

    }

    @When("User User clicks on TerminBuchen")
    public void userUserClicksOnTerminBuchen() throws InterruptedException {
        ts.genericutils.waitForElementVisibility(vlp.getTermenbuchenbutton());
        vlp.clicktermenbuchenbutton();
        Thread.sleep(5000);
    }

    @And("agrees to the terms and conditions")
    public void agreesToTheTermsAndConditions() throws InterruptedException {
        Thread.sleep(5000);
        ts.genericutils.shortScrollDown();
        ts.genericutils.waitForElementVisibility(tncp.getAccepttncCB());
        tncp.clickaccepttncCB();
    }

    @And("clicks on Weiter")
    public void clicksOnWeiter() throws InterruptedException {
        tncp.clickwieterbtn();
        Thread.sleep(5000);
    }

    @And("User fills the form")
    public void userFillsTheForm() throws InterruptedException {
        ts.genericutils.waitForElementVisibility(fp.getFeild1());
        fp.selectfeild1();
        Thread.sleep(2000);
        ts.genericutils.waitForElementVisibility(fp.getFeild2());
        fp.selectfeild2();
        Thread.sleep(2000);
        ts.genericutils.waitForElementVisibility(fp.getFeild3());
        fp.selectfeild3();
        Thread.sleep(2000);
        ts.genericutils.waitForElementVisibility(fp.getFeild4());
        fp.selectfeild4();
        Thread.sleep(2000);
        ts.genericutils.waitForElementVisibility(fp.getSelection1());
        fp.clickselection1();
        ts.genericutils.ScrolltoBottom();
        ts.genericutils.waitForElementVisibility(fp.getSelection2());
        fp.clickselection2();
        ts.genericutils.waitForElementVisibility(fp.getSelection3());
        fp.clickselection3();
        ts.genericutils.ScrolltoBottom();
        ts.genericutils.waitForElementVisibility(fp.getWieterbtn());
        ts.genericutils.waitForElementClickability(fp.getWieterbtn());
        fp.clickWeiterBtn();
        Thread.sleep(10000);

    }

    @Then("User sends mail regarding status of booking")
    public void userSendsMailRegardingStatusOfBooking() throws IOException, MessagingException, InterruptedException {
        ts.genericutils.waitForElementVisibility(rp.getActiveheader());
        int flag = 0;
        for (int i = 0; i < 30; i++) {
            if (rp.getheadertext().contains("3")) {
                String storagepath = ts.genericutils.createFiletoStoreImage();
                ts.genericutils.triggerNotification("Booking has opened");
                String pathname = ts.genericutils.takeScreenshotAndSaveandReturnpath(storagepath);
                ts.genericutils.sendEmail("The booking has opened. Please check the attached Screenshot", pathname);
                flag = 1;
                break;
            } else {
                Thread.sleep(15000);
                ts.genericutils.ScrolltoBottom();
                ts.genericutils.waitForElementVisibility(fp.getWieterbtn());
                ts.genericutils.waitForElementClickability(fp.getWieterbtn());
                fp.clickWeiterBtn();
            }
            System.out.println("Try number : " + i);
        }
        if (flag == 0) {
            System.out.println("No slots open yet");
        }

    }
}
