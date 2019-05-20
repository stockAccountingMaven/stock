package CommonFunLibrary;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import Utilities.PropertyFileUtil;
import junit.framework.Assert;

public class FunctionLibrary
{
	public static WebDriver startBrowser(WebDriver driver) throws Exception
	{
		if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Firefox"))
		{
			driver =new FirefoxDriver();
		}
//		else 
//			if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("Chrome"))
//			{
//				System.setProperty("webdriver.chrome.driver", "CommonJarFiles/chromedriver.exe");
//				driver = new ChromeDriver();
//			}else
//				if(PropertyFileUtil.getValueForKey("Browser").equalsIgnoreCase("IE"))
//				{
//					System.setProperty("webdriver.ie.driver", "CommonJarFiles/IEDriverServer.exe");
//					driver = new InternetExplorerDriver();
//				}
		return driver;
	}
	
	public static void openApplication(WebDriver driver) throws Exception
	{
		driver.manage().window().maximize();
		driver.get(PropertyFileUtil.getValueForKey("URL"));
	}
	
	public static void clickAction(WebDriver driver,String locatorType,String locatorValue)
	{
		if (locatorType.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(locatorValue)).click();
		}else
			if(locatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(locatorValue)).click();
			}else 
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorValue)).click();
				}
	}
	
	public static void typeAction(WebDriver driver,String locatorType,String locatorValue,String data)
	{
		if(locatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(locatorValue)).clear();
			driver.findElement(By.id(locatorValue)).sendKeys(data);
		}else
			if(locatorType.equalsIgnoreCase("name"))
			{
				driver.findElement(By.name(locatorValue)).clear();
				driver.findElement(By.name(locatorValue)).sendKeys(data);
			}else
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					driver.findElement(By.xpath(locatorValue)).clear();
					driver.findElement(By.xpath(locatorValue)).sendKeys(data);
				}
	}
	public static void closeBrowser(WebDriver driver)
	{
		driver.close();
	}
	
	public static void waitForElement(WebDriver driver,String locatorType,String locatorValue,String waittime)
	{
		WebDriverWait wait = new WebDriverWait(driver, Integer.parseInt(waittime));
		if (locatorType.equalsIgnoreCase("id"))
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(locatorValue)));
		}else
			if(locatorType.equalsIgnoreCase("name"))
			{
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(locatorValue)));
			}else 
				if(locatorType.equalsIgnoreCase("xpath"))
				{
					wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(locatorValue)));
				}
	}
	public static void titleValidation(WebDriver driver,String exp_data)
	{
		String act_data=driver.getTitle();
		if (exp_data.equalsIgnoreCase(act_data)) 
		{
		System.out.println("Title Matched");	
		}else
		{
			System.out.println("Title MisMatched");
		}
	}
	
	public static String generateDate()
	{
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("YYYY_MM_dd_ss");
	return	sdf.format(date);
	}
	
	public static void captureData(WebDriver driver,String locatorType,String locatorValue) throws Throwable
	{ 
		String data = "";
		
		if(locatorType.equalsIgnoreCase("id"))
		{
			
			data = driver.findElement(By.id(locatorValue)).getAttribute("value");
		}
		
		else if(locatorType.equalsIgnoreCase("xpath"))
		{
			
			data = driver.findElement(By.id(locatorValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("D:/NewOJT/NewOJT/VasuDeva/CaptureData/Data.txt");
		
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(data);
		bw.flush();
		
		bw.close();
}
	
	public static void tablevalidation(WebDriver driver,String column) throws Throwable
	{
		FileReader fr = new FileReader("D:/NewOJT/NewOJT/VasuDeva/CaptureData/Data.txt");
		
		BufferedReader br = new BufferedReader(fr);
		
		String exp_data = br.readLine();
		
		int column1 = Integer.parseInt(column);
		
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).isDisplayed())
		{
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).clear();
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).sendKeys(exp_data);
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.btn"))).click();
		}
		else
		{
			
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.panel"))).click();
		
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).clear();
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box"))).sendKeys(exp_data);
		
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.btn"))).click();
		}
		
		WebElement table = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("webtable.path")));
		
		List<WebElement> rows =  table.findElements(By.tagName("tr"));
		
		for(int i=1;i<rows.size();i++)
		{
		
			String act_data = driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div/div/table[@id='tbl_a_supplierslist']/tbody/tr["+i+"]/td["+column1+"]/div/span")).getText();
					
					Assert.assertEquals(act_data, exp_data);
			
			break;
		}
		
	}
	
	public static void stockCategories(WebDriver driver)
	{
		WebElement stockItem = driver.findElement(By.xpath("//*[@id='mi_a_stock_Items'"));
		WebElement stockCategory = driver.findElement(By.xpath("//*[@id='mi_a_stock_categories']/a"));
		
		Actions mouse = new Actions(driver);
		
		mouse.moveToElement(stockItem).build().perform();
		mouse.moveToElement(stockCategory).click().build().perform();
	}
	
	public static void tablevalidation1(WebDriver driver,String exp_data) throws Throwable
	{
		
		if(driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).isDisplayed())
		{
			
			
		
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).clear();
		
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).sendKeys(exp_data);
		
		driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.btn1"))).click();
		}
		else
		
		{
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.panel1"))).click();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).clear();
			
			driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("search.box1"))).sendKeys(exp_data);
			
			driver.findElement(By.xpath("//*[@id='btnsubmit']")).click();
		}
		
		WebElement table = driver.findElement(By.xpath(PropertyFileUtil.getValueForKey("webtable.path1")));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		for(int i=1; i<rows.size(); i++)
		{
			
			String act_data = driver.findElement(By.xpath(xpathExpression);
		}
		
		//WebElement table = driver.findElement(By.xpath("//*[@id='ewContentColumn']/div[3]/form/div/div//table[@id='tbl_a_stock_categorieslist']/tbody/tr[))
	}
	
}