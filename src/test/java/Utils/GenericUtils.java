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

    public void SwitchWindowToChild() {

        Set<String> s1 = driver.getWindowHandles();
        Iterator<String> i1 = s1.iterator();
        String parentWindow = i1.next();
        String childWindow = i1.next();
        driver.switchTo().window(childWindow);
    }

    public void shortScrollDown() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(300,500)");
    }

    public void ScrolltoBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public void waitForElementVisibility(By locator) {
        long timout = 60;
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementClickability(By locator) {
        long timout = 60;
        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(timout));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public String takeScreenshotAndSaveandReturnpath(String filePath) throws IOException {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(filePath + ".png"));
        return screenshot.getAbsolutePath();


    }

    public String createFiletoStoreImage() throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date()); // Get current timestamp

        File file = new File(System.getProperty("user.dir") + "\\test-output\\currentrunFinalSS\\" + timestamp); // Replace with your desired path

        if (!file.createNewFile()) {
            throw new IOException("Error creating file: " + file.getAbsolutePath());
        }
        return file.getAbsolutePath();
    }

    public boolean checkifelementispresent(By elementlocator) {
        if (driver.findElement(elementlocator).isDisplayed()) {
            return true;
        } else
            return false;
    }

    public void sendEmail(String results, String imagePath) throws MessagingException, IOException {
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\Global.properties");

        Properties prop = new Properties();
        prop.load(fis);
        final String senderEmail = prop.getProperty("FromMail");
        final String senderPassword = prop.getProperty("FromMailPwd");

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
            String toMail = prop.getProperty("ToMail");

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
