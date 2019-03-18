package itsm_mobile_base_files.framework.listener;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static itsm_mobile_base_files.app_manager.ApplicationManager.*;

public class TestNameListener extends TestListenerAdapter{


    @Override
    public void onStart(ITestContext tr){
        super.onStart(tr);
        log.info("");
        reportLog("[TEST STARTED] - " + tr.getName());
        log.info("");
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        super.onTestSuccess(tr);
        reportLog("[TEST SUCCESSFULLY PASSED]");
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        super.onTestFailure(tr);
        reportLog("[TEST FAILED]");
        String timeStamp;
        File screenShotName;
        //using ITestResult.FAILURE is equals to result.getStatus then it enter into if condition
        if(ITestResult.FAILURE==tr.getStatus()){
            try{
                // To create reference of TakesScreenshot
                TakesScreenshot screenshot=(TakesScreenshot)driver;
                // Call method to capture screenshot
                File src=screenshot.getScreenshotAs(OutputType.FILE);
                //Adding timestamp
                timeStamp = new SimpleDateFormat("yyyy_MM_dd HH_mm_ss").format(Calendar.getInstance().getTime());
                // Copy files to specific location
                Path currentRelativePath = Paths.get("");//getting current path
                String path1 = currentRelativePath.toAbsolutePath().toString();
                screenShotName = new File(path1+"/test-output/ScreenshotOfTheError/"+ timeStamp+ " " + tr.getName()+".png");
                FileUtils.copyFile(src, screenShotName);
                String filePath = screenShotName.toString();
                //String path = ("<img src='"+filePath+"' height='400' width='750'/>");
                reportLog("Successfully captured a screenshot");
                reportLog("Screenshot name and path: " + filePath);
            }catch (Exception e){
                reportLog("Exception while taking screenshot "+e.getMessage());
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult tr) {
        super.onTestSkipped(tr);
        reportLog("[TEST SKIPPED]");
    }
}
