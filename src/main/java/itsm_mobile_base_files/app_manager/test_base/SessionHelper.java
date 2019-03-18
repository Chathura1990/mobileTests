package itsm_mobile_base_files.app_manager.test_base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import itsm_mobile_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static itsm_mobile_base_files.app_manager.ApplicationManager.log;
import static itsm_mobile_base_files.app_manager.ApplicationManager.reportLog;
import static itsm_mobile_base_files.framework.global_parameters.GlobalParameters.VALID_PASSWORD;
import static itsm_mobile_base_files.framework.global_parameters.GlobalParameters.VALID_USERNAME;
import static org.openqa.selenium.By.*;

public class SessionHelper extends SelectorService {

    private By LoginButton = id("log_in_button");
    private By UserName = id("login_username");
    private By Password = id("password");
    private By SubmitButton = id("button_text");
    private By YourStory = id("chathura_s_r's story at column 0");

    public SessionHelper(AndroidDriver driver) {
        super(driver);
    }

    public void setup_App_And_login() throws InterruptedException {
        log.info("");
        click(LoginButton,"[Log in] button");
        type(UserName, VALID_USERNAME);
        driver.findElement(By.id("com.instagram.android:id/login_logo_padding")).click();
        type(Password, VALID_PASSWORD);
        click(SubmitButton, "[Login] button");
        Thread.sleep(2000);

        if(driver.findElement(YourStory).isDisplayed()){
            reportLog("Logged in Successful");
        }else{
            reportLog("Please Check your Credentials and try to signin again");
        }
    }
}
