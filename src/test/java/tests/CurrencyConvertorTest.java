package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import constants.ProjectConstants;

public class CurrencyConvertorTest {
	WebDriver driver;
	
	@BeforeMethod
public void initDriver() {
		driver= new ChromeDriver();
		
		driver.get(ProjectConstants.URL);
		driver.findElement(By.xpath("//button[text()='Accept']")).click();
		driver.manage().window().maximize();
	}
	
	@Test(dataProvider ="testdata" )
public void	convertFromEuroToPounds(String currencyValue) throws InterruptedException
	{
		double euroToPoundConversionAmount = 0.84579451;
		
		driver.findElement(By.xpath("//label[text()='Amount']/following::div//input")).sendKeys(currencyValue);
		driver.findElement(By.id("midmarketFromCurrency")).clear();
		Thread.sleep(1000);
		driver.findElement(By.id("midmarketFromCurrency")).sendKeys("Eur");
		driver.findElement(By.xpath("//*[text()='EUR']")).click();
		driver.findElement(By.id("midmarketToCurrency")).clear();
		driver.findElement(By.id("midmarketToCurrency")).sendKeys("GBP");
		driver.findElement(By.xpath("//*[text()='GBP']")).click();
		driver.findElement(By.xpath("//button[text()='Convert']")).click();
		
		Thread.sleep(2000);
		
		String actual = driver.findElement(By.xpath("//*[text()=' British Pounds']")).getText();
		actual=actual.replaceAll("British Pounds", "");
		Double actualValue = Double.valueOf(actual);
		
		
		Assert.assertTrue(actualValue - euroToPoundConversionAmount*Integer.valueOf(currencyValue) < 1);
	}
	
	@AfterMethod
	public void closeDriver() {
		driver.quit();
	}

	@DataProvider
	public Object[][] testdata() {
		return new Object[][] {{"5"}, {"10"} , {"100"}, {"190"}, {"1000"}};
	}
}
