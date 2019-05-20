package DriverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunLibrary.FunctionLibrary;
import Utilities.ExcelFileUtil;

public class DriverScript
{
WebDriver driver;
ExtentReports report;
ExtentTest logger;

	public void startTest() throws Throwable
	{
		ExcelFileUtil excel= new ExcelFileUtil();
		
		for (int i = 1; i <=excel.rowCount("MasterTestCases"); i++)
		{
		String moduleStatus="";	
			if(excel.getData("MasterTestCases", i, 2).equalsIgnoreCase("Y"))
			{
				//Define Module Name
				String TCModule = excel.getData("MasterTestCases", i, 1);
				report=new ExtentReports("./Reports/"+TCModule+".html"+FunctionLibrary.generateDate());
				logger=report.startTest(TCModule);
				int rowcount= excel.rowCount(TCModule);
				for (int j = 1; j <=rowcount; j++)
				{
					String Description=excel.getData(TCModule, j, 0);
					String Object_Type=excel.getData(TCModule, j, 1);
					String Locator_Type=excel.getData(TCModule, j, 2);
					String Locator_Value=excel.getData(TCModule, j, 3);
					String Test_Data=excel.getData(TCModule, j, 4);
					try
					{
					if (Object_Type.equalsIgnoreCase("startBroswer")) 
					{
						driver=FunctionLibrary.startBrowser(driver);
						logger.log(LogStatus.INFO, Description);
									
					}
					if (Object_Type.equalsIgnoreCase("openApplication")) 
					{
						FunctionLibrary.openApplication(driver);
						logger.log(LogStatus.INFO, Description);
						
					}
					if (Object_Type.equalsIgnoreCase("clickAction")) 
					{
						FunctionLibrary.clickAction(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
						
					}
					if (Object_Type.equalsIgnoreCase("typeAction")) 
					{
						FunctionLibrary.typeAction(driver, Locator_Type, Locator_Value,Test_Data);
						logger.log(LogStatus.INFO, Description);
						
					}
					if (Object_Type.equalsIgnoreCase("waitForElement")) 
					{
						FunctionLibrary.waitForElement(driver, Locator_Type, Locator_Value, Test_Data);
						
						logger.log(LogStatus.INFO, Description);
					}
					if (Object_Type.equalsIgnoreCase("closeBrowser")) 
					{
						FunctionLibrary.closeBrowser(driver);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("titleValidation"))
					{
						FunctionLibrary.titleValidation(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
						
					}
					
					if(Object_Type.equalsIgnoreCase("captureData"))
					{
						
						FunctionLibrary.captureData(driver, Locator_Type, Locator_Value);
						logger.log(LogStatus.INFO, Description);
					}
					if(Object_Type.equalsIgnoreCase("tablevalidation"))
					{
						FunctionLibrary.tablevalidation(driver, Test_Data);
						logger.log(LogStatus.INFO, Description);
						
					}
					if(Object_Type.equalsIgnoreCase("stockCategories"))
					{
						FunctionLibrary.stockCategories(driver);
						logger.log(LogStatus.INFO, Description);
					}
					excel.setData(TCModule, j, 5, "Pass");
					moduleStatus="true";
					logger.log(LogStatus.PASS, Description);
					}catch(Exception e)
					{
						excel.setData(TCModule, j, 5, "Fail");
						moduleStatus="False";
						logger.log(LogStatus.FAIL, Description);
						File srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(srcFile, new File("./Screenshots/"+Description+FunctionLibrary.generateDate()+".png"));
						break;
					}
			
				}
			if(moduleStatus.equalsIgnoreCase("true"))
			{
				excel.setData("MasterTestCases", i, 3, "Pass");
				//logger.log(LogStatus.PASS,Description);
			}else
				if(moduleStatus.equalsIgnoreCase("False"))
				{
					excel.setData("MasterTestCases", i, 3, "Fail");
					//logger.log(LogStatus.FAIL,Description);
				}
				
			report.endTest(logger);
			report.flush();
			}
			
			else
			{
				excel.setData("MasterTestCases", i, 3, "Not Executed");
			}
			
		}
	}
}
