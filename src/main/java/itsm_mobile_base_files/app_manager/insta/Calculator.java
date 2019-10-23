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
import static org.openqa.selenium.By.xpath;

public class Calculator extends SelectorService {

    public Calculator(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    private By number1 = id("com.google.android.calculator:id/digit_1");
    private By plusMark = id("plus");
    private By equals = id("equals");
    private By results = id("com.google.android.calculator:id/result_final");

    //more options
    private By moreoptions = xpath("//android.widget.ImageView[@content-desc='More options']");
    private By selectTheme = xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView");
    private By lightButton = xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[2]");
    private By okButton = id("android:id/button1");

    public String clickOnePlusOne(){
        click(number1,"number [1]");
        click(plusMark,"sign [+]");
        click(number1,"number [1]");
        click(equals,"sign [=]");
        return getText(results);
    }

    public void changeTheme(){
        click(moreoptions,"[More Options] button");
        click(selectTheme,"[Theme] button");
        click(lightButton,"[Light] button");
        click(okButton, "[OK] button");
    }

}
