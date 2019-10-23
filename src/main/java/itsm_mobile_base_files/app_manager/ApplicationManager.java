package itsm_mobile_base_files.app_manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import itsm_mobile_base_files.app_manager.insta.Calculator;
import itsm_mobile_base_files.app_manager.selector_helper.SelectorService;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static itsm_mobile_base_files.framework.global_parameters.GlobalParameters.APPIUM_HUB_URL;
import static itsm_mobile_base_files.framework.global_parameters.GlobalParameters.LONG_WAIT;

public class ApplicationManager {

    public static AppiumDriver<MobileElement> driver;

    private final static boolean DEBUG = true;
    private static final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    public static Logger log = Logger.getLogger("ApplicationManager");

    private SelectorService selectorService;
    private Calculator calc;

    public void init() throws MalformedURLException, InterruptedException {
        /*
         * open Appium and setup mobile application on Emulator
         */
        DesiredCapabilities dc = new DesiredCapabilities();
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME,"Appium");
        dc.setCapability(MobileCapabilityType.PLATFORM_NAME,"ANDROID");
        dc.setCapability(MobileCapabilityType.PLATFORM_VERSION,"8.0.0");
        dc.setCapability(MobileCapabilityType.DEVICE_NAME,"SM-A720F");
        dc.setCapability(MobileCapabilityType.APP,"C:\\Users\\CRajapakse\\Videos\\apk files\\calcu.apk");

        URL url = new URL(APPIUM_HUB_URL);

        long start = System.currentTimeMillis();
        driver = new AppiumDriver<>(url,dc);
        long finish = System.currentTimeMillis();

        long totalTimeInMillis = finish - start;
        double seconds = (totalTimeInMillis / 1000.0) % 60;
        double minutes = (double) ((totalTimeInMillis / (1000 * 60)) % 60);
        reportLog("Total time to load the page -> " + "milliseconds: " + totalTimeInMillis + " minutes:" + minutes + " seconds:" + seconds);
        Thread.sleep(5000);

        selectorService = new SelectorService(driver);
        calc = new Calculator(driver);
    }

    public void stop() throws InterruptedException {
        Thread.sleep(LONG_WAIT);
        driver.quit();
    }

    //Method for adding logs passed from test cases
    public static String reportLog(String message) {
        if (DEBUG) {
            Reporter.setEscapeHtml(false);
            Date date = new Date();
            log.info("-- " + message);
            Reporter.log(dateFormat.format(date) + " /" + " " + message);
        }
        return message;
    }

    public SelectorService getSelectorService(){ return selectorService; }

    public Calculator getCalc(){ return calc; }
}
