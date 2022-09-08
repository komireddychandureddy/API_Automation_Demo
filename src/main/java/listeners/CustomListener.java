package listeners;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;

import org.codehaus.plexus.util.ExceptionUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.relevantcodes.extentreports.LogStatus;

import utils.LogUtil;
import utils.SendMail;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.ExtentTestManager;


/**
 * @Author Chandu
 * @Date 27-Aug-2022
 */

public class CustomListener extends SendMail implements ITestListener
{

	
	private String getTestMethodName(ITestResult iTestResult) {
        return iTestResult.getMethod().getConstructorOrMethod().getName();
    }

    @Override
    public void onStart(ITestContext iTestContext) {
    	
    	LogUtil.infoLog(getClass(), "I am in onStart method " + iTestContext.getName());
        //System.out.println("I am in onStart method " + iTestContext.getName());
        iTestContext.setAttribute("WindowsDriver", getWinDriver());
        ConfigReader.environmentSetup();
        LogUtil.infoLog(this.getClass(), "Updated environment details at /src/main/resources/environment.properties" );
   		
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        LogUtil.infoLog(getClass(), "I am in onFinish method " + iTestContext.getName());
        //Do tier down operations for extentreports reporting!
        ExtentTestManager.endTest();
        ExtentManager.getReporter().flush();
        LogUtil.infoLog(getClass(), "Report closed ");
        
        if(!(getWinDriver()==null)){
        	getWinDriver().quit();
        }
      
        String htmlReportFile = USERDIR+  ConfigReader.getValue("HtmlReportFullPath");
		File f = new File(htmlReportFile);
		if (f.exists()) {

			try {
				Runtime.getRuntime()
						.exec("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe \"" + htmlReportFile
								+ "\"");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		 if (ConfigReader.getValue("sendMail").equalsIgnoreCase("Y"))
				try {
					sendEmailToClient();
					LogUtil.infoLog(getClass(), "Report mail sent to participants");
				} catch (IOException e1) {
					logStepFail(e1.getMessage());
					e1.printStackTrace();
				} catch (MessagingException e1) {
					logStepFail(e1.getMessage());
					e1.printStackTrace();
				}
				
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
    	LogUtil.infoLog(getClass(), "Testcase started: "+getTestMethodName(iTestResult) );

        ExtentTestManager.startTest(iTestResult.getMethod().getMethodName(),iTestResult.getMethod().getDescription());
        String description=iTestResult.getMethod().getDescription();
		ExtentTestManager.getTest().setDescription(description);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
    	String testName = getTestMethodName(iTestResult);
    	//Extentreports log operation for passed tests.
        ExtentTestManager.getTest().log(LogStatus.PASS, "Test passed : "+iTestResult.getMethod().getMethodName());
        ExtentTestManager.getTest().setEndedTime(new Date());
        getWinDriver().close();
        
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
    	String testName = getTestMethodName(iTestResult);
    	 ExtentTestManager.getTest().setEndedTime(new Date());
    	
        //Take base64Screenshot screenshot.
        String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)getWinDriver()).
                getScreenshotAs(OutputType.BASE64);

        //Extentreports log and screenshot operations for failed tests.
        
        String path = null;
        try {
			 path =takeScreenshot(getWinDriver(), testName);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        String  ErrorMsg=ExceptionUtils.getFullStackTrace(iTestResult.getThrowable());
        logStepFail(ErrorMsg);
        ExtentTestManager.getTest().log(LogStatus.FAIL,"Test Failed : "+iTestResult.getMethod().getMethodName(),
                ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
       
        
	 getWinDriver().close();
		
  
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    	LogUtil.infoLog(getClass(), "Test Skipped"+getTestMethodName(iTestResult));
       ExtentTestManager.getTest().setEndedTime(new Date());
       ExtentTestManager.getTest().log(LogStatus.SKIP, "Test Skipped"+getTestMethodName(iTestResult));
        getWinDriver().close();
    }

   
}
