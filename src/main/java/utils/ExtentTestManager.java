package utils;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.util.HashMap;
import java.util.Map;
/**
 * @Author Chandu
 * @Date 15-Aug-2022
 */ 
public class ExtentTestManager {
    static Map extentTestMap = new HashMap();
    static ExtentReports extent = ExtentManager.getReporter();
 
    public static synchronized ExtentTest getTest() {
        return (ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId()));
    }
 
    public static synchronized void endTest() {
        extent.endTest((ExtentTest)extentTestMap.get((int) (long) (Thread.currentThread().getId())));
    }
 
    public static synchronized ExtentTest startTest(String testName, String desc) {
        ExtentTest test = extent.startTest(testName, desc);
        extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);
        return test;
    }
    public static synchronized void stepInfo(String stepName) {
    	  ExtentTestManager.getTest().log(LogStatus.INFO, "Test Info : "+stepName);
    }
    public static void stepSkip(String stepName) {
  	  ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped : "+stepName);
  }
    public static void stepPass(String stepName) {
  	  ExtentTestManager.getTest().log(LogStatus.PASS, "Test Pass : "+stepName);
  }
    public static void stepFail(String stepName) {
  	  ExtentTestManager.getTest().log(LogStatus.FAIL, "Test Fail : "+stepName);
  }
  public static void stepError(String stepName) {
	  ExtentTestManager.getTest().log(LogStatus.ERROR, "Test Error : "+stepName);
}
  public static void stepWarning(String stepName) {
	  ExtentTestManager.getTest().log(LogStatus.WARNING, "Test Warning : "+stepName);
}
}
  