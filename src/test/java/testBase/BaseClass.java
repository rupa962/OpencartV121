package testBase;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;	//Log4j
import org.apache.logging.log4j.Logger;		//Log4j

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class BaseClass {
	
	public static WebDriver driver;
	public Logger logger;	//Log4j
	public Properties p;
	
	@BeforeClass(groups = {"Sanity", "Regression", "Master"})
	@Parameters({"os", "browser"})
	public void setup(String os, String br) throws IOException
	{
		logger = LogManager.getLogger(this.getClass());		//Log4j
		
		//Loading config.properties file
		FileInputStream file = new FileInputStream ("./src//test//resources//config.properties");
		p = new Properties();
		p.load(file);
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//As we are getting the OS & Browser from XML, using if, else or switch statements 
			//for comparing the xml value & then setPlatform & setBrowserName accordingly.
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				capabilities.setPlatform(Platform.LINUX);
			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching OS");
				return;
			}
			
			//browser
			switch(br.toLowerCase())
			{
			case "chrome" : capabilities.setBrowserName("chrome"); break;
			case "edge"	: capabilities.setBrowserName("MicrosoftEdge"); break;
			case "firefox"	: capabilities.setBrowserName("firefox"); break;
			default: System.out.println("Invalid browser name.."); return;
			}
			
			//String huburl = "http://localhost:4444/wd/hub";	
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())		//choose the browser based on the value provided in xml
			{
			case "chrome": driver = new ChromeDriver(); break;
			case "edge": driver = new EdgeDriver(); break;
			case "firefox": driver = new FirefoxDriver(); break;
			default: System.out.println("Invalid browser name.."); return;	//specifying return as if browser name is invalid, 
			//no need to execute any other test case. it will exit from the execution automatically.
			}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL"));	//reading URL from config.Properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass(groups = {"Sanity", "Regression", "Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	//There is a class called RandomStringUtils in org.apache.commons.lang3
	//which has static methods like randomAlphabetic(int count), randomNumeric(int count), randomAlphanumeric(int count)
	//which will create a string of length specified as parameter
	
	public String randomString()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber()
	{
		String generatedNumber = RandomStringUtils.randomNumeric(10);
		return generatedNumber;	
	}
	
	public String randomAlphanumeric()
	{
		String generatedString = RandomStringUtils.randomAlphabetic(3);
		String generatedNumber = RandomStringUtils.randomNumeric(3);
		
		return (generatedString+"@"+generatedNumber);
	}
	
	
	//There is a class called RandomStringUtils in org.apache.commons.lang3
	//which has non static methods like nextAlphabetic(int count), nextNumeric(int count), nextAlphanumeric(int count)
	//which will create a string of length specified as parameter
	//create an object for RandomStringUtils class & invoke the methods.
	
	
	/*RandomStringUtils rsu = new RandomStringUtils();
	
	public String randomString()
	{
		String generatedString = rsu.nextAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber()
	{
		String generatedNumber = rsu.nextNumeric(10);
		return generatedNumber;	
	}
	
	public String randomAlphanumeric()
	{
		String generatedString = rsu.nextAlphanumeric(6);
		return generatedString;
	}*/
	

	public String captureScreen(String tname) throws IOException
	{
		
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath = System.getProperty("user.dir")+"\\screenshots"+ tname + "_" + timeStamp + ".png";
		File targetFile = new File(targetFilePath);
		
		sourceFile.renameTo(targetFile);
		
		return targetFilePath;		//we are returning the path(where screenshots exists) of screenshot 
									//to attach it to the reports(will be used in ExtentReportManager class)
		
		
	}
	
	
}
