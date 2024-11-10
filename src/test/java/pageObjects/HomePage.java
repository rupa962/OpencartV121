package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

//generally in page object classes have constructor, locator, action methods
//usually in every page object class we have the constructor with PageFactory.initElements
//hence, we have created a BasePage class which has this PageFactory.initElements & 
//we are using inheritance concept by using "extends" keyword. 
//in order to access the immediate parent class variable/method/constructor we use "super" keyword.

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) 
	{
		super(driver);
	}
	

	@FindBy(xpath = "//a[@title='My Account']")	
	WebElement lnkMyaccount;
		
	@FindBy(xpath = "//a[normalize-space()='Register']")	
	WebElement lnkRegister;
	
	@FindBy(xpath = "//a[normalize-space()='Login']")	//Login link added in steps
	WebElement lnkLogin;
	
	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}
	
	public void clickRegister()
	{
		lnkRegister.click();
	}

	public void clickLogin()
	{
		lnkLogin.click();
	}
}
