package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class AccountRegistrationPage extends BasePage {

	public AccountRegistrationPage(WebDriver driver) 
	{	
		super(driver);	
	}
	
	@FindBy(xpath = "//input[@id='input-firstname']")
	WebElement txtFirstname;
	
	@FindBy(xpath = "//input[@id='input-lastname']")
	WebElement txtLastname;
	
	@FindBy(xpath = "//input[@id='input-email']")
	WebElement txtEmail;
	
	@FindBy(xpath = "//input[@id='input-telephone']")
	WebElement txtTelephone;
	
	@FindBy(xpath = "//input[@id='input-password']")
	WebElement txtPassword;
	
	@FindBy(xpath = "//input[@id='input-confirm']")
	WebElement txtConfirmPassword;

	@FindBy(xpath = "//input[@name='agree']")
	WebElement chkdPolicy;
	
	@FindBy(xpath = "//input[@value='Continue']")
	WebElement btnContinue;
	
	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement msgConfirmation;
	
	public void setFirstName (String fname)
	{
		txtFirstname.sendKeys(fname);
	}
	
	public void setLastName (String lname)
	{
		txtLastname.sendKeys(lname);
	}
	
	public void setEmail (String email)
	{
		txtEmail.sendKeys(email);
	}
	
	public void setTelephone (String tel)
	{
		txtTelephone.sendKeys(tel);
	}
	
	public void setPassword (String pwd)
	{
		txtPassword.sendKeys(pwd);
	}
	
	public void setConfirmPassword (String pwd)
	{
		txtConfirmPassword.sendKeys(pwd);
	}
	
	public void setPrivacyPolicy()
	{
		chkdPolicy.click();
	}
	
	public void clickContinue()
	{
		//solution 1
		btnContinue.click();
		
		//solution 2
		//btnContinue.submit();
		
		//solution 3
		//Actions act = new Actions();
		//act.moveToElement(btnContinue).click().perform();
		
		//solution 4
		//JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("arguments[0].click()" , btnContinue);
		
		//solution 5
		//btnContinue.sendKeys(Keys.RETURN);
		
		//solution 6
		//WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//mywait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();		
	}
	
	//As we can't write validations in the page object class, we are writing below method() to capture the text from the web element.	
	
	public String getConfirmationMsg()
	{
		try 
		{
			return (msgConfirmation.getText());
		}
		catch(Exception e)
		{
			return (e.getMessage());
		}	
	}
	
	
	
}
