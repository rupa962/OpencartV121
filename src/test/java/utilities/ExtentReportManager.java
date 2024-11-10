package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

public class ExtentReportManager implements ITestListener
{
	public ExtentSparkReporter sparkReporter;	//UI of the report
	public ExtentReports extent;				//populate common info on the report
	public ExtentTest test;						//creating test case entries in the report and update the status of the test methods
	
	String repName;
	
	 public void onStart(ITestContext testContext)	//testContext is a variable, it will capture which test method is currently being executed
	 {
		 //onStart methods will be executed before all the test methods
		 //hence, before starting execution of test methods we create the template of the report
		 //that code for creation of report template will be written here
		 
		/* SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
		 Date dt =new Date();
		 String currentdatetimestamp = df.format(dt);
		 */
		 
		 String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());		//will return current time stamp
		 
		 repName = "Test-Report-" + timeStamp +".html";		//generation of report name
		 sparkReporter = new ExtentSparkReporter (".\\reports\\" + repName);	//specify the location of report
		 
		 sparkReporter.config().setDocumentTitle("opencart Automation Report"); 		//Title of the Report
		 sparkReporter.config().setReportName("opencart Funtional Testing"); 		//Name of the Report
		 sparkReporter.config().setTheme(Theme.STANDARD); 						//setting the theme of the Report
		 
		 extent = new ExtentReports();
		 extent.attachReporter(sparkReporter);		//attaching the UI(sparkReporter) & the common information(extent) 
		 
		 extent.setSystemInfo("Application", "opencart");
		 extent.setSystemInfo("Module", "Admin");
		 extent.setSystemInfo("Sub Module", "Customers");
		 extent.setSystemInfo("User Name", System.getProperty("user.name"));	//gets the user name whoever using the system.
		 extent.setSystemInfo("Environment", "QA");
		 
		 String os = testContext.getCurrentXmlTest().getParameter("os");	
		 //testContext is the variable of ITestContext -- it will capture which test method is currently being executed
		 //getCurrentXmlTest() will get the xml file that is being used to run the test method
		 //getParamter() will get the value of the parameter in xml
		 
		 extent.setSystemInfo("Operating System", os);
		 
		 String browser = testContext.getCurrentXmlTest().getParameter("browser");
		 extent.setSystemInfo("Browser", browser);
		 
		 List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		 if(!includedGroups.isEmpty())		// if included groups is not empty, that means groups values are present in xml.
		 {
			 extent.setSystemInfo("Groups", includedGroups.toString());
		 }
		
	 }
	 
	 public void onTestSuccess(ITestResult result) 	//here result is a variable, which will capture the test method
	 {
		 test = extent.createTest(result.getTestClass().getName());		//create a new entry in the report, name of entry is the class name
		 test.assignCategory(result.getMethod().getGroups());		// to display the groups, that are associated with that method
		 test.log(Status.PASS, result.getName()+ "got successfully executed");  //update the status pass/failed/skipped
	 }

	 public void onTestFailure(ITestResult result)	//here result is a variable, which will capture the test method
	 {
	 
		 test = extent.createTest(result.getTestClass().getName());
		 test.assignCategory(result.getMethod().getGroups());
		 
		 test.log(Status.FAIL , result.getName()+ "got failed"); 
		 test.log(Status.FAIL , result.getThrowable().getMessage());
		 
		 //whenever a test case is failed, we need to attach the screenshot in the report
		 //hence, we have written a method called "captureScreen()" for taking screenshot in BaseClass.
		 
		try 
		{
			String imgPath = new BaseClass().captureScreen(result.getName());
			//creating object for BaseClass & calling captureScreen() method & 
			//String parameter should be sent, for which we are using getName() method upon result variable.
			//As captureScreen() method returns the screenshot path, we are storing it in a String variable
			
			test.addScreenCaptureFromPath(imgPath);	
			//addScreenCaptureFromPath() is a method in ExtentReportManager which will add the screenshot to the report
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		 
	 }
	  
	 public void onTestSkipped(ITestResult result)
	 {
		 test = extent.createTest(result.getTestClass().getName());
		 test.assignCategory(result.getMethod().getGroups());
		 
		 test.log(Status.SKIP , result.getName() + "got skipped");
		 test.log(Status.SKIP , result.getThrowable().getMessage());
	 }
	
	 public void onFinish(ITestContext context)		//this is a mandatory method
	 {
		 extent.flush();			//flush() method will write the test information to their output view.
		 
		 //After test suite(xml) is executed & report is generated, instead of manually opening it, 
		 //we can open it automatically with below piece of code.
		 
		 String pathofExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;	//storing the path of the extentReport generated
		 File extentReport = new File(pathofExtentReport);		//this represent the ExtentReport file
		 
		 try
		 {
			Desktop.getDesktop().browse(extentReport.toURI());	
			//browse(URI) is a method which will open the file directly on the default browser of application.
		 } 
		 catch (IOException e)
		 {
			e.printStackTrace();
		 }
		 
		 

		//once after the automation test is completed, if we want to sent the results or the report to any email directly
		 
//		 try
//		 {
//			 @SuppressWarnings("deprecation")
//			URL url = new URL("file://"+System.getProperty("user.dir")+"\\reports\\"+repName);
//		 
//			 //Create the email message
//			 ImageHtmlEmail email = new ImageHtmlEmail();
//			 email.setDataSourceResolver(new DataSourceUrlResolver(url));
//			 email.setHostName("smtp.googleemail.com");		//this is only for gmail
//			 email.setSmtpPort(465);						//this is only for gmail.
//			 email.setAuthenticator( new DefaultAuthenticator("rupasri962@gmail.com", "password"));
//			 email.setSSLOnConnect(true);
//			 email.setFrom("rupasri962@gmail.com");	//Sender
//			 email.setSubject("Test Results");
//			 email.setMsg("Please find Attached Report...");
//			 email.addTo("rupasriqa@gmail.com");	//Receiver
//			 email.attach(url, "extent report", "please check report...");
//			 email.send();	//send the email
//		 }
//		 catch (Exception e)
//		 {
//			 e.printStackTrace();
//		 }
		   

	 }

}
