package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class , groups = "Datadriven")	
	//here as the dataProvider methods are present in separate class, along with method name we also have to provide class name "DataProviders".
	//if the data provider is present in same class then this "dataProviderClass" is not required
	
	public void verify_loginDDT(String email, String pwd, String exp) throws InterruptedException
	{
		logger.info("*****Starting TC003_LoginDDT*****");
		try
		{
		//HomePage
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickLogin();
		
		//LoginPage
		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword(pwd);
		lp.clickLogin();
		
		//MyAccount
		MyAccountPage macc = new MyAccountPage(driver);
		boolean targetPage = macc.isMyAccountPageExists();		//if targetPage is true then login is successful
		
		//Validation
		/* Data is valid - login success - test is passed - logout
		 * Data is valid - login failed - test is failed.
		 * Data is invalid - login success - test is failed - logout
		 * Data is invalid - login failed - test is passed.
		 */
		
		if(exp.equalsIgnoreCase("Valid"))
		{
			if(targetPage == true)
			{
				macc.clickLogout();
				Assert.assertTrue(true);
			}
			else
			{
				Assert.assertTrue(false);
			}
		}
		if(exp.equalsIgnoreCase("Invalid"))
		{
			if(targetPage == true)
			{
				macc.clickLogout();
				Assert.assertTrue(false);
			}
			else
			{
				Assert.assertTrue(true);
			}
		}
		}
		
		catch(Exception e)
		{
			Assert.fail();
		}
		
		Thread.sleep(3000);
		logger.info("*****Finished TC003_LoginDDT*****");
	}
	

}
