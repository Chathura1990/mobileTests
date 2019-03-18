package itsm_mobile_base_files.framework.mainClass;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.TestNG;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class TestNGRunner {

    public static Logger log = Logger.getLogger("TestNGRunner");

    private static TestNG testNG = new TestNG();

    public static TestNG configureTestNG() {

        File pathToJarFile = null;
        String jarDir = null;
        String pathToTestsSuiteFile = null;
        String pathToLog4jConfigFile = null;

        try {
            pathToJarFile = new File(URLDecoder
                    .decode(TestNGRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));

            try {
                jarDir = (pathToJarFile.getAbsolutePath()).substring(0,
                        (pathToJarFile.getAbsolutePath()).lastIndexOf(File.separator));
                if ((jarDir != null) && (new File(jarDir)).exists() && (new File(jarDir)).isDirectory()) {
                    pathToTestsSuiteFile = jarDir + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "testcases.xml";
                    pathToLog4jConfigFile = jarDir + File.separator + ".." + File.separator + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "log4j.properties";
                }
            } catch (SecurityException se) {
                System.err.println("[*ERROR]: security access violation reading .jar directory attributes!");
                se.printStackTrace();
                System.exit(1);
            }
        } catch (UnsupportedEncodingException uee) {
            System.err.println("[*ERROR]: couldn't decode path to .jar file!");
            uee.printStackTrace();
            System.exit(127);
        }

        Path currentRelativePath = Paths.get("");//getting current path
        String path1 = currentRelativePath.toAbsolutePath().toString();
        PropertyConfigurator.configureAndWatch(path1 + "/src/main/resources/log4j.properties");

        Path currentRelativePath2 = Paths.get("");//getting current path
        String path2 = currentRelativePath2.toAbsolutePath().toString();
        testNG.setTestSuites(Arrays.asList(path2 + "/src/main/resources/testcases.xml"));
        return testNG;
    }

    public static void main(String[] args) {
        parseCli(args);
        configureTestNG().run();
//            ApplicationManager.reportLog("End Testing...");
        if (testNG.getStatus() == 1) {
            log.info("Process finished with exit code: 1");
            System.exit(1);
        } else if (testNG.getStatus() == 0) {
            log.info("Process finished with exit code: 0");
            System.exit(0);
        }
    }

    private static void parseCli(String[] args) {
        JCommander jCommander = new JCommander(Parameters.instance());
        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            log.error(e.getMessage(), e);
            System.exit(1);
            log.info("Process finished with exit code: 1");
        }
        if (Parameters.instance().isHelp()) {
            jCommander.usage();
            System.exit(0);
            log.info("Process finished with exit code: 0");
        }
    }
}

