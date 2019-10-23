package itsm_mobile_test_files.first_test;

import itsm_mobile_base_files.app_manager.test_base.TestBase;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CalculatorTests extends TestBase {

    @Priority(1)
    @Test(priority = 1)
    public void click_Like_Button() throws InterruptedException {
        Thread.sleep(2000);
        String result = app.getCalc().clickOnePlusOne();
        System.out.println(result);
        Assert.assertEquals(result,"2");
    }

    @Priority(2)
    @Test(priority = 2)
    public void changeTheme() throws InterruptedException {
        Thread.sleep(2000);
        app.getCalc().changeTheme();
    }


}
