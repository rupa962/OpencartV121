package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//DataProvider 1
	
	@DataProvider (name = "LoginData")
	public String[][] getData() throws IOException
	{
		String path = ".\\testData\\OpenCart_LoginData.xlsx";		//taking excel file from testData folder, 
													//here .\\ indicates current project, similar to System.getProperty("user.dir")	
		
		//creating object for ExcelUtility class at the time of object creation only excel file path should be sent to the object 
		//as the "ExcelUtility" class contains the constructor
		ExcelUtility xlutil = new ExcelUtility(path);
		
		int totalrows = xlutil.getRowCount("Sheet1");
		int totalcols = xlutil.getCellCount("Sheet1", 1);
		
		//creating a String type of 2 dimensional array of size same as rows & columns in the excel.
		
		String logindata[][] = new String[totalrows][totalcols];
		
		try {
		for(int i=1; i<=totalrows; i++)		//here we are starting index as "1" as we have to ignore the header present at index '0'.
		{
			for(int j=0; j<totalcols; j++)
			{
				logindata[i-1][j] = xlutil.getCellData("Sheet1", i, j);		//1,0
				
				//here we reading data from row 1(ignore row 0 - header) from excel & 
				//assigning it to the array 1st position(which index is 0). Hence, we are keeping i-1
			}
		}
		}
		catch(Exception e)
		{
			return logindata;
		}
		
		return logindata;		//returning two dimensional array
	}
	
	
	//DataProvider 2
	
	
	//DataProvider 3
	
	
	//DataProvider 4
	
	

}
