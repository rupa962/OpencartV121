package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	@Test(groups = {"Regression", "Master"})
	public void verify_accountregistration_test()
	{
		
		logger.info("*****starting TC001_AccountRegistrationTest*****");
		
		try {
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount Link..");
		
		hp.clickRegister();
		logger.info("Clicked on Register Link..");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		
		logger.info("Providing Customer details..");
		regpage.setFirstName(randomString().toUpperCase());
		regpage.setLastName(randomString().toUpperCase());
		regpage.setEmail(randomString()+"@gmail.com");	//randomly generating the email
		regpage.setTelephone(randomNumber());
		
		String pswd = randomAlphanumeric();
		
		regpage.setPassword(pswd);
		regpage.setConfirmPassword(pswd);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		String confmsg = regpage.getConfirmationMsg();
		
		if (confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test failed");
			logger.debug("Debug logs");
			Assert.assertTrue(false);
		}
		
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		
		}
		catch (Exception e)
		{
			e.getMessage();
			Assert.fail();
		}
		
		logger.info("*****Finished TC001_AccountRegistrationTest*****");
	}
	
	
	
	
}
