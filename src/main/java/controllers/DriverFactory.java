/**
 * 
 */
package controllers;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.appium.java_client.windows.WindowsDriver;
import utils.ConfigReader;
import utils.LogUtil;

/**
 * @Author Chandu
 * @Date 27-08-2022
 */
public class DriverFactory
{
	public String USERDIR =System.getProperty("user.dir");
	public ThreadLocal<WindowsDriver> wd = new ThreadLocal<WindowsDriver>();

		/*
		@Parameters({ "Platform" , "App" })
		@BeforeMethod
		public void beforeMethod() throws Exception
		{		
			String platform=ConfigReader.getValue("Platform");
			String app=ConfigReader.getValue("Windows_App");
			LogUtil.infoLog(getClass(), "Platform: "+platform+"  Windows_App: "+app);
	
			setWinDriver(createDriver(platform,app));			
		}
		 */
		@BeforeMethod
		public void beforeMethod() throws Exception
		{		
			String platform=ConfigReader.getValue("Platform");
			String app=ConfigReader.getValue("Windows_App");
			LogUtil.infoLog(getClass(), "Platform: "+platform+"  Windows_App: "+app);
	
			setWinDriver(createDriver(platform,app));			
		}

	public void setWinDriver(WindowsDriver driver) 
	{
		wd.set(driver);
	}

	public WindowsDriver getWinDriver() 
	{
		return wd.get();
	}


	@AfterMethod
	public void afterMethod() 
	{
		getWinDriver().close();	
	}

	public WindowsDriver createDriver(String platform, String app) throws Exception 
	{
		WindowsDriver driver = null;

		DesiredCapabilities capabilities;
		switch(platform.toLowerCase())
		{
		case "windows 10" :
				capabilities = new DesiredCapabilities();
				capabilities.setCapability("app","Microsoft.WindowsMaps_8wekyb3d8bbwe!App");
				
				capabilities.setCapability("platformName", "Windows");
				//capabilities.setCapability("platformVersion", "10");
				capabilities.setCapability("deviceName", "WindowsPC");

			try {
				driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
				
			break;
			
		case "windows 11":
			capabilities = new DesiredCapabilities();
			capabilities.setCapability("app","Microsoft.WindowsMaps_8wekyb3d8bbwe!App");
			
			capabilities.setCapability("platformName", "Windows");
			//capabilities.setCapability("platformVersion", "11");
			capabilities.setCapability("deviceName", "WindowsPC");

			try {
				driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			break;


		default:
			throw new Exception("Please Provide a Valid OS name");
		}
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
			LogUtil.infoLog(getClass(), "  Windows_App: "+app+" app launched succefully: ");
		return driver;		
	}
}
