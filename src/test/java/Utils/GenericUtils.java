package Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLFeatureNotSupportedException;
import java.text.DateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.util.*;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.SimpleDateFormat; // Format and parse dates
// OR
import java.time.LocalDate; // represent date only (Java 8+)


public class GenericUtils {
    public WebDriver driver;

    public GenericUtils(WebDriver driver) {

        this.driver = driver;
    }
    //General util used to switch between handles
    public void SwitchWindowToChild() {

        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        String parentWindow = i1.next();
        String childWindow = i1.next();
        driver.switchTo().window(childWindow);
    }
    //Function used for a short scroll down
    public void shortScrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(300,500)");
    }
    //Function used to Scroll to the bottom of the page
    public void ScrolltoBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }
    //function used to wait for element visibility
    public void waitForElementVisibility(By locator) {
        long timout = 60;
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    //function used to wait for element click-ability
    public void waitForElementClickability(By locator) {
        long timout = 60;
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timout));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    //function used to take screenshot of the booking page and to return the path where it is stored
    public String takeScreenshotAndSaveandReturnpath(String filePath) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(filePath + ".png"));
        return screenshot.getAbsolutePath();


    }
    //creates a file to store the image internally within the project
    public String createFiletoStoreImage() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date()); // Get current timestamp

        File file = new File(System.getProperty("user.dir") + File.separator+"test-output"+File.separator+"currentrunFinalSS" +File.separator+ timestamp);

        if (!file.createNewFile()) {
            throw new IOException("Error creating file: " + file.getAbsolutePath());
        }
        return file.getAbsolutePath();
    }
    //custom function to check if an element is present
    public boolean checkifelementispresent(By elementlocator) {
        if (driver.findElement(elementlocator).isDisplayed()) {
            return true;
        } else
            return false;
    }
    //function to trigger sound notification
    public void triggerNotification(String message) {
        String osName = System.getProperty("os.name").toLowerCase();
        String osVersion = System.getProperty("os.version").toLowerCase();
        try {
            if (osName.contains("win")) {
                String[] cmd = {
                        "powershell",
                        "Add-Type -AssemblyName System.Speech; $speak = New-Object System.Speech.Synthesis.SpeechSynthesizer; $speak.Speak('Alert! Attention required!');"
                };
                Runtime.getRuntime().exec(cmd);
            } else if (osName.contains("mac")) {
                Runtime.getRuntime().exec(new String[]{"say", "Alert! Attention required!"});
            } else if (osVersion.contains("wsl")) {
                Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", "powershell.exe -c \"(New-Object -ComObject SAPI.SpVoice).Speak('Alert! Attention required!')\"\n"});
            } else {
                // Handle other operating systems
            }
        } catch (IOException e) {
            System.out.println("Failed to notify. Reason: "+ e);
        }
    }
    //function to send Email
    public void sendEmail(String results, String imagePath) throws MessagingException, IOException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"Global.properties");

        Properties prop = new Properties();
        prop.load(fis);
        String senderEmail_properties = prop.getProperty("FromMail");
        String senderEmail_maven=System.getProperty("FromMail");
        String senderEmail=senderEmail_maven!=null?senderEmail_maven:senderEmail_properties;
        String senderPassword_properties = prop.getProperty("FromMailPwd");
        String senderPassword_maven=System.getProperty("FromMailPwd");
        String senderPassword=senderPassword_maven!=null?senderPassword_maven:senderPassword_properties;

        // Setting up SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Creating a session with authentication
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
        try {
            // Creating a message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            String toMail_properties = prop.getProperty("ToMail");
            String toMail_maven=System.getProperty("ToMail");
            String toMail=toMail_maven!=null?toMail_maven:toMail_properties;

// Split the string into individual email addresses using comma as the delimiter
            String[] toMailAddresses = toMail.split(",");

// Create an array to hold the InternetAddress objects for each recipient
            InternetAddress[] recipientAddresses = new InternetAddress[toMailAddresses.length];

// Parse each email address and create an InternetAddress object for it
            for (int i = 0; i < toMailAddresses.length; i++) {
                recipientAddresses[i] = new InternetAddress(toMailAddresses[i].trim());
            }
            message.setRecipients(Message.RecipientType.TO, recipientAddresses);
            message.setSubject("ALERT!");

            // Creating the email body part
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(results);

            // Creating the attachment part
            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(imagePath);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(source.getName());

            // Multipart message to hold text and attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Setting the multipart as the message content
            message.setContent(multipart);

            // Sending the email
            Transport.send(message);

            System.out.println("Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending email: " + e.getMessage());
        }

    }


}
