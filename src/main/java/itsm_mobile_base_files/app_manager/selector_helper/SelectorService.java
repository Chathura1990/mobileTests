package itsm_mobile_base_files.app_manager.selector_helper;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static io.restassured.RestAssured.when;
import static itsm_mobile_base_files.app_manager.ApplicationManager.log;
import static itsm_mobile_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_mobile_base_files.framework.global_parameters.GlobalParameters.*;
import static org.openqa.selenium.By.xpath;

public class SelectorService {

    protected AndroidDriver driver;
    private WebDriverWait wait;
    private TouchAction tAction = new TouchAction(driver);

    public SelectorService(AndroidDriver driver) {
        this.driver = driver;
    }

    /**
     * this method makes WebDriver poll the DOM for a certain amount of time when trying to locate an element.
     * @param units
     */
    public void implicit_Wait(int units) {
        driver.manage().timeouts().implicitlyWait(units, TimeUnit.SECONDS);
    }

    /**
     * this method will wait until completely load the page
     * @param units
     */
    public void pageLoad_Timeout(int units) {
        driver.manage().timeouts().pageLoadTimeout(units, TimeUnit.SECONDS);
    }

    public void randomWait() {
        try {
            Thread.sleep((long)(Math.random() * MILLISECONDS_WAIT));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected WebElement visibilityOfElementLocatedBylocator(By locator, int unit) //Visibility Of Element Located By Xpath
    {
        wait = new WebDriverWait(driver, unit);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public Boolean invisibilityOfElementLocatedByLocator(By locator, int unit)//Invisibility Of Element Located By Xpath
    {
        wait = new WebDriverWait(driver, unit);
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public void scroll(int clickXOffset, int clickYOffSet, int duration, int moveToX, int moveToY) throws InterruptedException {
        tAction.press(point(clickXOffset, clickYOffSet))
                .waitAction(waitOptions(Duration.ofMillis(duration)))
                .moveTo(point(moveToX, moveToY))
                .release().perform();
        Thread.sleep(LONG_WAIT*2);
    }

    protected void waitElementToBeClickable(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, IMPLICIT_WAIT);
        wait.until(ExpectedConditions.elementToBeClickable(locator));
        reportLog("Wait element to be clickable: (" + locator + ")");
    }

    protected void click(By locator, String text) {
        waitElementToBeClickable(locator);
        if (isELementPresent(locator)) {
            driver.findElement(locator).click();
        }
        reportLog("Clicked '" + text + "' using element: (" + locator + ")");
    }

    public void type(By locator, String text) {//Click on field,clear field and enter the text
        waitElementToBeClickable(locator);
        driver.findElement(locator).click();
        if (text != null) {
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(text);
            reportLog("Typed: '" + text + "' in the field: (" + locator + ")");
        }
    }

    protected void attach(By locator, File file) {
        if (file != null) {
            driver.findElement(locator).sendKeys(file.getAbsolutePath());
        }
    }

    protected String getText(By locator) {
        implicit_Wait(IMPLICIT_WAIT);
        return visibilityOfElementLocatedBylocator(locator,IMPLICIT_WAIT).getText();
    }

    public String getAttribute(By locator, String value) {
        return driver.findElement(locator).getAttribute(value);
    }

    public void selectAnOptionFromDropdown(By locator,int option){
        Select select = new Select(driver.findElement(locator));
        List<WebElement> options = select.getOptions();
        options.get(option).click();
    }

    public String getWebsiteTitle() {
        return driver.getTitle();
    }

    protected boolean isELementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    protected void clearField(By locator) {
        visibilityOfElementLocatedBylocator(locator, IMPLICIT_WAIT);
        driver.findElement(locator).click();
        (new Actions(driver)).moveToElement(driver.findElement(locator))
                .sendKeys(Keys.chord(Keys.CONTROL + "a"))
                .sendKeys(Keys.BACK_SPACE)
                .build().perform();
    }

    protected boolean checkDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            reportLog("Please Correct the Date Format");
            return false;
        }
        reportLog("Date is in valid format");
        return true;
    }

    public void checkDateIsFutureDate(String date, String text) throws ParseException {
        Date OderDate = new SimpleDateFormat("mm/dd/yyyy").parse(date);
        //get current date
        Date current = new Date();
        reportLog("Get Current Date: " + current);
        reportLog("Get " + text + " Date: " + OderDate);
        //compare both dates
        if (OderDate.after(current)) {
            reportLog("The " + text + " Date is future day");
            Assert.assertFalse(OderDate.after(current));
        } else {
            reportLog("The " + text + " date is older than current");
        }
    }

    protected void checkImageIsAvailableOrNot(By locator1, String text1, String text2) {
        WebElement image = visibilityOfElementLocatedBylocator(locator1, IMPLICIT_WAIT);
        reportLog("wait for the element (" + text1 + ")");

        Object result = ((JavascriptExecutor) driver).executeScript(
                "return arguments[0].complete && " + "typeof arguments[0].naturalWidth != \"undefined\" && " + "arguments[0].naturalWidth > 0", image);

        boolean loaded = false;
        if (result instanceof Boolean) {
            loaded = (Boolean) result;
            reportLog("'" + text2 + "' image is available -->" + " " + loaded);
        }
    }

    public void assertSuccessMessage(String expectedMsg) {
        WebElement message = visibilityOfElementLocatedBylocator(xpath("//div[@class='ui-pnotify-text']"), IMPLICIT_WAIT);
        String SuccessMsg = message.getText();
        reportLog("Expected Message --> " + expectedMsg);
        reportLog("Actual Message--> " + SuccessMsg);
        Assert.assertEquals(SuccessMsg, expectedMsg);
    }

    protected int calculateAge(By locator) {
        String dob = driver.findElement(locator).getAttribute("value");
        SimpleDateFormat myFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date current = new Date();
        Date birthdate = null;
        try {
            birthdate = myFormat.parse(dob);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = current.getTime() / 1000 - Objects.requireNonNull(birthdate).getTime() / 1000;
        return Math.round(time) / 31536000;
    }

    protected void assertNumberOfOptionsInDropdown(By locator, String text, int realAmount){
        reportLog("**** Check "+text+" dropdown in Single Rx *******");
        List<WebElement> optionList = driver.findElement(locator).findElements(By.tagName("option"));
        WebElement element1 = driver.findElement(locator);
        Select select = new Select(element1);
        int numberOfOptions = select.getOptions().size();
        reportLog("Number of "+text+" --> "+ numberOfOptions);
        int j=1;
        for(WebElement option : optionList){
            reportLog(text+" -->"+" "+ option.getText());
            j++;
        }
        Assert.assertEquals(numberOfOptions,realAmount);
    }

    protected void checkTheLinkStatus(){
        wait = new WebDriverWait(driver, MILLISECONDS_WAIT);
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
        when().get(driver.getCurrentUrl()).then().statusCode(200);
        log.info("Page is completely loaded: " + ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }

    public void checkAnchorTagLinkStatus(By locator, String value) throws IOException {
        WebElement link = driver.findElement(locator);
        HttpURLConnection connection = (HttpURLConnection) new URL(link.getAttribute("href")).openConnection();
        connection.setConnectTimeout(LONG_WAIT);
        connection.setInstanceFollowRedirects( false );
        connection.connect();
        int code = connection.getResponseCode();
        connection.disconnect();

        if(code == 200){
            reportLog("Link: "+link+" code status is "+code);
        }else{
            reportLog("This link is corrupted: "+ link);
        }
    }
}
