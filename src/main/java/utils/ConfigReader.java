
package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.Properties;
/**
 * This ConfigReader file will read the config file 
 *
 */

public class ConfigReader {
	

	/**
	 * will read the properties file with this function
	 * @param filePath
	 * @return
	 */
	/*private ConfigReader(){
		
	}*/
	public static Properties loadPropertyFile(String filePath) {
		// Read from properties file
		File file = new File(filePath);
		Properties prop = new Properties();

		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(file);
			prop.load(fileInput);
		} catch (Exception e) {
			LogUtil.errorLog(ConfigReader.class, "Caught the exception", e);
			
		

		}
		return prop;

	}
	/**
	 * will get sting value from properties file
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {

		Properties prop = loadPropertyFile("src\\main\\resources\\PropertiesFiles\\config.properties");
		 return prop.getProperty(key);
	}
	/**
	 * will get int value from properties file
	 * @param key
	 * @return
	 */
	public static int getIntValue(String key) {
		Properties prop = loadPropertyFile("src\\main\\resources\\PropertiesFiles\\config.properties");

		String strKey = prop.getProperty(key);

		return Integer.parseInt(strKey);
	}

	public static void environmentSetup() {
		try 
		{
			Properties properties = new Properties();
			properties.setProperty("Author", "Chandu");
			//properties.setProperty("app", getProperty(key));
			properties.setProperty("OS", System.getProperty("os.name"));
			properties.setProperty("OS Architecture", System.getProperty("os.arch"));
			properties.setProperty("OS Bit", System.getProperty("sun.arch.data.model"));
			properties.setProperty("Java Version", Runtime.class.getPackage().getImplementationVersion());
			properties.setProperty("Host Name", InetAddress.getLocalHost().getHostName());
			properties.setProperty("Host IP Address", InetAddress.getLocalHost().getHostAddress());
			
			File file = new File("./src/main/resources/environment.properties");
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.store(fileOut, "Envronement Setup");
			fileOut.close();
			
	        
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
	        
		} 
	}
}
