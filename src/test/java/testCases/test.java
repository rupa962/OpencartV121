package testCases;

import java.util.List;
import java.util.Random;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class test {


	@SuppressWarnings("deprecation")
	@Test
	public void testing()
	{
		//Below are the ways of generating random data		
	
		//1. Creating an object for "Random" class & use methods like nextInt(num), nextlong(num), nextDouble(num)
		//which will create a number between 0 & the "num" specified
		
//		Random random = new Random();
//		int in = random.nextInt(999999999);
//		System.out.println("The random integer generated is: " +in);
		
		//2. There is a class called RandomStringUtils in org.apache.commons.lang3
		//which has static methods like randomAlphabetic(int count), randomNumeric(int count), randomAlphanumeric(int count)
		//which will create a string of length specified as parameter
	
//		String Str = RandomStringUtils.random(6, true,true);
//		System.out.println("The random string generated is: " +Str);
		
		
		//3. There is a class called RandomStringUtils in org.apache.commons.lang3
		//which has non static methods like nextAlphabetic(int count), nextNumeric(int count), nextAlphanumeric(int count)
		//which will create a string of length specified as parameter
		//create an object for RandomStringUtils class & invoke the methods.
		
		RandomStringUtils rsu = new RandomStringUtils();
		String alpha = rsu.nextAlphabetic(5);
		String number = rsu.nextNumeric(10);
		String alphanum = rsu.nextAlphanumeric(6);
		
		System.out.println("The random alphabet string generated is: " +alpha);
		System.out.println("The random numeric string generated is: " +number);
		System.out.println("The random aphanumeric string generated is: " +alphanum);
		
		//String[] s = {"a", "s"};
		
	
		
		
	}
	
}
