package utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;


/**
 * @author Chandu
 *
 */
public class ScreenshotUtil  {


	/**
	 * @param driver
	 * @param testCaseID
	 * @return
	 * @throws IOException
	 */
	/*private ScreenshotUtil(){
		
	}*/
	public String takeScreenshot(WebDriver driver, String testCaseID) throws IOException {
		String path = ConfigReader.getValue("screenshotPath") + "\\" + testCaseID + ".jpg";
		//Screenshot screenshot = new AShot().shootingStrategy(new ViewportPastingStrategy(100)).takeScreenshot(driver);
		
		Screenshot screenshot = new AShot().takeScreenshot(driver);
		File src = new File(System.getProperty("user.dir") + "\\ExecutionReports\\HtmlReport\\" + path);
		ImageIO.write(screenshot.getImage(), "PNG", src);
		return src.getPath();
	}

}
