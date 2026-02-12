package com.frameWork;

import java.io.File;
import java.time.Duration;

import org.ini4j.Wini;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;

public class GenericSeleniumActions extends Driver {
	WebDriver driver;
	public static OrangeHrm ohm;
	public static Driver div;

	public GenericSeleniumActions() {
		this.driver = Driver.driver;
	}

	public boolean click(String locator, String value,String stepDescription) {
		boolean status = false;

		try {
			WebElement ele=null;
			waitTillElementPresent(locator, value, stepDescription);
			int size =0;
			try {
				size= driver.findElements(By.xpath(value)).size();
				 ele = driver.findElement(By.xpath(value));
				JavascriptExecutor js= (JavascriptExecutor)driver;
				js.executeScript("arguments[0].click();",ele);		
			} catch (ElementClickInterceptedException e) {
				JavascriptExecutor js= (JavascriptExecutor)driver;
				js.executeScript("arguments[0].click();",ele);		
				}
			

			if(size == 1) {
				status = true;
				ExtentTestManager. pass_Step_Detailed("Sucessfuly Clicked on: "+stepDescription);
			}
			else
			{
				 ExtentTestManager. fail_Step_Detailed("Unable To Click on: "+stepDescription); 

				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	public boolean logout(String stepDescription) {
		boolean status = false;

		try {
			Thread.sleep(6000);
			int s=driver.findElements(By.xpath("//input[@name='username']")).size();
			if(s!=1) {

			if(!click("xpath","//i[contains(@class,'userdropdown-icon')]",stepDescription)) {
				 ExtentTestManager. fail_Step_Detailed("Unable To Click on: "+stepDescription); 

			}
			if(!click("xpath","//a[text()='Logout']",stepDescription)) {
				 ExtentTestManager. fail_Step_Detailed("Unable To Click on: "+stepDescription); 

			}
			else
			{
				ExtentTestManager. pass_Step_Detailed("Sucessfuly Clicked on: "+stepDescription);
	
			}
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;

	}
	public boolean clickUsingReplace(String locator, String value,String valueToReplace,String stepDescription) {
		boolean status = false;

		try {
			value=value.replace("#replace", valueToReplace);
			waitTillElementClickable(locator, value, stepDescription);
			
			WebElement clickk = driver.findElement(By.xpath(value));

			try {
				clickk.click();
			} catch (NoSuchElementException e) {
				JavascriptExecutor js=  (JavascriptExecutor)driver;
				js.executeScript("arguments[0].click();", clickk);
				status = true;
			}

			if(driver.findElements(By.xpath(value)).size() == 1) {
				
				ExtentTestManager. pass_Step_Detailed("Sucessfuly Clicked on: "+stepDescription);
				status = true;
			}
			else
			{
				 ExtentTestManager. fail_Step_Detailed("Unable To Click on: "+stepDescription); 

				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	public boolean existReplace(String locator, String value,String text,String stepDescription) {
		boolean status = false;

		try {
			value=value.replace("#replace", text);
			waitTillElementPresent(locator, value, stepDescription);
		int	size=driver.findElements(By.xpath(value)).size();

			if(size == 1) {
				status = true;
				ExtentTestManager. pass_Step_Detailed("Exist: "+stepDescription);
				ExtentTestManager.captureScreenshot_Base64_Detailed("Exist: "+stepDescription);
			}
			else
			{
				 ExtentTestManager. fail_Step_Detailed("Not Exist: "+stepDescription); 
					ExtentTestManager.captureScreenshot_Base64_Detailed("Not Exist: "+stepDescription);


				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	public boolean exist(String locator, String value,String stepDescription) {
		boolean status = false;

		try {
			waitTillElementPresent(locator, value, stepDescription);
		int	size=driver.findElements(By.xpath(value)).size();

			if(size == 1) {
				status = true;
				ExtentTestManager. pass_Step_Detailed("Exist: "+stepDescription);
			}
			else
			{
				 ExtentTestManager. fail_Step_Detailed("Not Exist: "+stepDescription); 

				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	public boolean enterText(String locator, String value, String text, String stepDescription) {
		boolean status = false;

		try {
			waitTillElementPresent(locator, value, stepDescription);
			WebElement element = driver.findElement(By.xpath(value));
			element.clear();
			if(!text.isEmpty())
			{
				waitTillElementPresent(locator, value, stepDescription);
			element.sendKeys(text);
			int size = driver.findElements(By.xpath(value)).size();

			if (size == 1) {
				
				ExtentTestManager. pass_Step_Detailed("Successfully entered "+text+": "+stepDescription);
				status= true;
			}
			else
			{
				ExtentTestManager. fail_Step_Detailed("Unable to enter: "+text+": "+stepDescription); 
				return false;
			}
			}
			else
			{
				ExtentTestManager. fail_Step_Detailed("Unable to enter: "+stepDescription); 
				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public boolean launchApplication(String url) {
		boolean status = false;

		try {
			if(!url.isEmpty())
			{
			driver.get(url);
			ExtentTestManager. pass_Step_Detailed("Successfully launched "+url+" ");
			}
			else {
				ExtentTestManager. fail_Step_Detailed("Unable to launch "+url+" "); 
				return false;
			}
				
			status = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return status;

	}

	public boolean waitTillElementPresent(String locator, String value, String stepDescription) {
		boolean status = false;
		try {
			System.out.println("");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(Driver.MaxTime)));

			if (locator.equalsIgnoreCase("xpath")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(value)));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(value)));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value)));
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 1) {
					status = true;
					
				}
				else
				{
					ExtentTestManager. fail_Step_Detailed("Unable to locate the: "+stepDescription);
					return false;
					
				}


			}
			
			if (locator.equalsIgnoreCase("name")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(value)));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(value)));
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 1) {
					status = true;
				}
				else
				{
					return false;
				}

			}

			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	public boolean waitTillElementClickable(String locator, String value, String stepDescription) {
		boolean status = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(Driver.MaxTime)));

			if (locator.equalsIgnoreCase("xpath")) {
				
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value)));
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 1) {
					status = true;
					
				}
				else
				{
					ExtentTestManager. fail_Step_Detailed("Unable to locate the: "+stepDescription);
					return false;
					
				}
			}
	
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

}
