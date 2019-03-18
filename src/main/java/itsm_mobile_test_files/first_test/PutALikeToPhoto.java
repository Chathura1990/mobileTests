package itsm_mobile_test_files.first_test;

import itsm_mobile_base_files.app_manager.test_base.TestBase;
import org.testng.annotations.Test;

public class PutALikeToPhoto extends TestBase {

    @Priority(1)
    @Test(priority = 1)
    public void click_Like_Button() throws InterruptedException {
        Thread.sleep(2000);
        app.getSelectorService().scroll(682,1712, 585, 622, 495);
        app.getSelectorService().scroll(682,1712, 585, 622, 495);
    }
}
