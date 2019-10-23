package itsm_mobile_base_files.app_manager.insta;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import itsm_mobile_base_files.app_manager.model_data.DataCollector;
import itsm_mobile_base_files.app_manager.selector_helper.SelectorService;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;

import static org.openqa.selenium.By.id;

public class Calculator extends SelectorService {

    public Calculator(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    private By number1 = id("com.google.android.calculator:id/digit_1");
    private By plusMark = id("plus");
    private By equals = id("equals");
    private By results = id("com.google.android.calculator:id/result_final");

    public String clickOnePlusOne(){
        click(number1,"number [1]");
        click(plusMark,"sign [+]");
        click(number1,"number [1]");
        click(equals,"sign [=]");
        return getText(results);
    }

}
