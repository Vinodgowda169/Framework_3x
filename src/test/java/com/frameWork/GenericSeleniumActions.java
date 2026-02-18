package com.frameWork;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.fasterxml.jackson.databind.ObjectMapper;

import utils.LoggerUtil;

public class GenericSeleniumActions extends Driver {
	WebDriver driver;
	
	public static Driver div;
	private static final Logger logger = LogManager.getLogger(GenericSeleniumActions.class);
	
	public GenericSeleniumActions() {
		this.driver = Driver.driver;
	}

	
	
	/*
	 * public boolean click(String locator, String value, String stepDescription) {
	 * boolean status = false;
	 * 
	 * try { WebElement ele = null; waitTillElementPresent(locator, value,
	 * stepDescription); int size = 0; try { boolean st = true;
	 * 
	 * ele = driver.findElement(By.xpath(value)); waitTillElementPresent(locator,
	 * value, stepDescription); ele.click(); waitTillElementNotPresent(locator,
	 * value, stepDescription); size = driver.findElements(By.xpath(value)).size();
	 * while (size==1) { JavascriptExecutor js = (JavascriptExecutor) driver;
	 * js.executeScript("arguments[0].click();", ele); size =
	 * driver.findElements(By.xpath(value)).size();
	 * 
	 * if(size==0) { st = true; break; }
	 * 
	 * }
	 * 
	 * } catch (ElementClickInterceptedException e) { boolean st = true;
	 * JavascriptExecutor js = (JavascriptExecutor) driver; while (size==1) {
	 * js.executeScript("arguments[0].click();", ele); size =
	 * driver.findElements(By.xpath(value)).size();
	 * 
	 * if(size==0) { st = true; break; }
	 * 
	 * }
	 * 
	 * }
	 * 
	 * if (size == 0) { status = true;
	 * ExtentTestManager.pass_Step_Detailed("Sucessfuly Clicked on: " +
	 * stepDescription); } else {
	 * ExtentTestManager.fail_Step_Detailed("Unable To Click on: " +
	 * stepDescription);
	 * 
	 * return false; } status = true; } catch (Exception e) { e.printStackTrace(); }
	 * return status;
	 * 
	 * }
	 */
	
	public boolean click(String locator, String xPath, String stpDescription) throws Exception {
		 
		boolean status = false;
 
		try {
 
			if (!waitForPresenceAndVisibilityOfElement("EVALUATEXPATH", xPath)) {
 
				//GlobalVariables.errorMap.put("error", "Failed : Unable to Find the WebElement : " + stpDescription);
 
				return false;
 
			}
 
			WebElement buttonOrLink = createWebElement("EVALUATEXPATH", xPath);
 
			boolean booleanWait = true;
 
			int wt = 0;
 
			while (booleanWait) {
 
				try {
 
					if (waitForElementToBeClickable("EVALUATEXPATH", xPath)) {
 
						try {
 
							buttonOrLink.click();
 
							break;
 
						} catch (ElementNotInteractableException e) {
 
							JavascriptExecutor js = (JavascriptExecutor) driver;
 
							js.executeScript("arguments[0].click();", buttonOrLink);
 
							break;
 
						}
					} else {
 
						JavascriptExecutor js = (JavascriptExecutor) driver;
 
						js.executeScript("arguments[0].click();", buttonOrLink);
 
						break;
 
					}
				} catch (ElementClickInterceptedException e) {
					
					Thread.sleep(2000);
 
					wt = wt + 2;
 
				} catch (ElementNotInteractableException e) {
 
					Thread.sleep(2000);
 
					wt = wt + 2;
				} catch (StaleElementReferenceException e) {
 
					Thread.sleep(2000);
 
					wt = wt + 2;
 
				}
				if (wt > 120) {
 
					ExtentTestManager.fail_Step_Detailed("Unable To Click on: " + stpDescription);
					ExtentTestManager.captureScreenshot_Base64_Detailed("Unable To Click on: " + stpDescription);
					logger.info("Unable To Click on: " + stpDescription);
					
					return false;
 
				}
 
			}
			ExtentTestManager.pass_Step_Detailed("Sucessfully Clicked on: " + stpDescription);
			logger.info("Sucessfully Clicked on: " + stpDescription);
			
			status = true;
 
		} catch (Exception e) {
			ExtentTestManager.fail_Step_Detailed("Unable To Click on: " + stpDescription);
			ExtentTestManager.captureScreenshot_Base64_Detailed("Unable To Click on: " + stpDescription);
			logger.info("Unable To Click on: " + stpDescription);
			
			
			System.out.println(e);
 
			e.printStackTrace(System.out);
 
			return false;
 
		}
 
		return status;
 
	}
	
	
	
	public boolean waitForElementToBeClickable(String m_locator, String m_value) throws Exception {
		 
		boolean bStatus = false;
 
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(Driver.MaxTime)));
 
		List<WebElement> e;
		try {
			if (m_locator.equalsIgnoreCase("EVALUATEXPATH")) {
 
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(m_value)));
 
				e = driver.findElements(By.xpath(m_value));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATECSSSELECTOR")) {
 
				wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(m_value)));
 
				e = driver.findElements(By.cssSelector(m_value));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATEID")) {
 
				wait.until(ExpectedConditions.elementToBeClickable(By.id(m_value)));
 
				e = driver.findElements(By.id(m_value));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATELINKTEXT")) {
 
				wait.until(ExpectedConditions.elementToBeClickable(By.linkText(m_value)));
 
				e = driver.findElements(By.linkText(m_value));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATENAME")) {
 
				wait.until(ExpectedConditions.elementToBeClickable(By.name(m_value)));
 
				e = driver.findElements(By.name(m_value));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATECLASS")) {
 
				wait.until(ExpectedConditions.elementToBeClickable(By.className(m_value)));
 
				e = driver.findElements(By.className(m_value));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			}
		} catch (Exception e1) {
 
			System.out.println(e1);
 
			e1.printStackTrace(System.out);
 
		}
 
		return bStatus;
	}
	
	
	public boolean waitForPresenceAndVisibilityOfElement(String locatorType, String locatorValue) throws Exception {
		 
		boolean bStatus = false;
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(Driver.MaxTime)));
 
		List<WebElement> e;
 
		try {
			if (locatorType.equalsIgnoreCase("EVALUATEXPATH")) {
 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locatorValue)));
 
				// wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(m_value)));
 
				// wait.until(ExpectedConditions.elementToBeClickable(By.xpath(value)));
 
				e = driver.findElements(By.xpath(locatorValue));
 
				if (e.size() > 0) {
 
					bStatus = true;
 
				}
 
			} else if (locatorType.equalsIgnoreCase("EVALUATEXPATH_presence_visibiblity_clickable")) {
 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(locatorValue)));
 
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locatorValue)));
 
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(locatorValue)));
 
				e = driver.findElements(By.xpath(locatorValue));
 
				if (e.size() > 0) {
 
					bStatus = true;
 
				}
 
			}
			else if (locatorType.equalsIgnoreCase("EVALUATECSSSELECTOR")) {
 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(locatorValue)));
 
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(locatorValue)));
 
				// wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(value)));
 
				e = driver.findElements(By.cssSelector(locatorValue));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			} else if (locatorType.equalsIgnoreCase("EVALUATEID")) {
 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.id(locatorValue)));
 
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id(locatorValue)));
 
				// wait.until(ExpectedConditions.elementToBeClickable(By.id(value)));
 
				e = driver.findElements(By.id(locatorValue));
 
				if (e.size() > 0) {
 
					bStatus = true;
 
				}
 
			}
			else if (locatorType.equalsIgnoreCase("EVALUATELINKTEXT")) {
 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.linkText(locatorValue)));
 
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText(locatorValue)));
 
				// wait.until(ExpectedConditions.elementToBeClickable(By.linkText(value)));
 
				e = driver.findElements(By.linkText(locatorValue));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			}
			else if (locatorType.equalsIgnoreCase("EVALUATENAME")) {
 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(locatorValue)));
 
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.name(locatorValue)));
 
				// wait.until(ExpectedConditions.elementToBeClickable(By.linkText(value)));
 
				e = driver.findElements(By.name(locatorValue));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			}
			else if (locatorType.equalsIgnoreCase("EVALUATECLASS")) {
 
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className(locatorValue)));
 
				wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className(locatorValue)));
 
				// wait.until(ExpectedConditions.elementToBeClickable(By.linkText(value)));
 
				e = driver.findElements(By.className(locatorValue));
 
				if (e.size() == 1) {
 
					bStatus = true;
 
				}
 
			}
		} catch (Exception e1) {
 
			System.out.println(e1);
 
			e1.printStackTrace(System.out);
 
		}
 
		return bStatus;
 
	}
	
	
	public WebElement createWebElement(String m_locator, String m_value) throws Exception {
		 
		WebElement element = null;
 
		Thread.sleep(100);
 
		try {
 
			if (m_locator.equalsIgnoreCase("EVALUATEXPATH")) {
 
				if (waitForPresenceAndVisibilityOfElement(m_locator, m_value)) {
 
					element = driver.findElement(By.xpath(m_value));
 
				} else {
 
					System.out.println("Element not found with locator " + m_locator + " value as " + m_value);
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATECSSSELECTOR")) {
 
				if (waitForPresenceAndVisibilityOfElement(m_locator, m_value)) {
 
					element = driver.findElement(By.cssSelector(m_value));
 
				} else {
 
					System.out.println("Element not found with locator " + m_locator + " value as " + m_value);
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATEID")) {
 
				if (waitForPresenceAndVisibilityOfElement(m_locator, m_value)) {
 
					element = driver.findElement(By.id(m_value));
 
				} else {
 
					System.out.println("Element not found with locator " + m_locator + " value as " + m_value);
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATELINKTEXT")) {
 
				if (waitForPresenceAndVisibilityOfElement(m_locator, m_value)) {
 
					element = driver.findElement(By.linkText(m_value));
 
				} else {
 
					System.out.println("Element not found with locator " + m_locator + " value as " + m_value);
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATENAME")) {
 
				if (waitForPresenceAndVisibilityOfElement(m_locator, m_value)) {
 
					element = driver.findElement(By.name(m_value));
 
				} else {
 
					System.out.println("Element not found with locator " + m_locator + " value as " + m_value);
 
				}
 
			} else if (m_locator.equalsIgnoreCase("EVALUATECLASS")) {
 
				if (waitForPresenceAndVisibilityOfElement(m_locator, m_value)) {
 
					element = driver.findElement(By.className(m_value));
 
				} else {
 
					System.out.println("Element not found with locator " + m_locator + " value as " + m_value);
 
				}
 
			}
		} catch (Exception e) {
 
			e.printStackTrace();
 
		}
 
		return element;
 
	}
	public List<WebElement> createWebElements(String m_locator, String m_value,String Discripation) throws Exception {
 
		List<WebElement> elements = null;
		if (m_locator.equalsIgnoreCase("EVALUATEXPATH")) {
 
			if (waitForPresenceAndVisibilityOfElements(m_locator, m_value,Discripation)) {
 
				elements = driver.findElements(By.xpath(m_value));
 
			} else {
 
				System.out.println("Elements are not found with locator " + m_locator + " value as " + m_value);
 
			}
 
		} else if (m_locator.equalsIgnoreCase("EVALUATECSSSELECTOR")) {
 
			if (waitForPresenceAndVisibilityOfElements(m_locator, m_value,Discripation)) {
 
				elements = driver.findElements(By.cssSelector(m_value));
 
			} else {
 
				System.out.println("Elements are not found with locator " + m_locator + " value as " + m_value);
 
			}
 
		} else if (m_locator.equalsIgnoreCase("EVALUATEID")) {
 
			if (waitForPresenceAndVisibilityOfElements(m_locator, m_value,Discripation)) {
 
				elements = driver.findElements(By.id(m_value));
 
			} else {
 
				System.out.println("Elements are not found with locator " + m_locator + " value as " + m_value);
 
			}
 
		} else if (m_locator.equalsIgnoreCase("EVALUATELINKTEXT")) {
 
			if (waitForPresenceAndVisibilityOfElements(m_locator, m_value,Discripation)) {
 
				elements = driver.findElements(By.linkText(m_value));
 
			} else {
 
				System.out.println("Elements are not found with locator " + m_locator + " value as " + m_value);
 
			}
 
		} else if (m_locator.equalsIgnoreCase("EVALUATENAME")) {
 
			if (waitForPresenceAndVisibilityOfElements(m_locator, m_value,Discripation)) {
 
				elements = driver.findElements(By.name(m_value));
 
			} else {
 
				System.out.println("Elements are not found with locator " + m_locator + " value as " + m_value);
 
			}
 
		} else if (m_locator.equalsIgnoreCase("EVALUATECLASS")) {
 
			if (waitForPresenceAndVisibilityOfElements(m_locator, m_value,Discripation)) {
 
				elements = driver.findElements(By.className(m_value));
 
			} else {
 
				System.out.println("Elements are not found with locator " + m_locator + " value as " + m_value);
 
			}
 
		}
 
		return elements;
 
	}
	
	

	public boolean logout(String stepDescription) {
		boolean status = false;

		try {
			Thread.sleep(6000);
			int s = driver.findElements(By.xpath("//input[@name='username']")).size();
			if (s != 1) {

				if (!click("xpath", "//i[contains(@class,'userdropdown-icon')]", stepDescription)) {
					ExtentTestManager.fail_Step_Detailed("Unable To Click on: " + stepDescription);
					logger.info("Unable To Click on: " + stepDescription);

				}
				if (!click("xpath", "//a[text()='Logout']", stepDescription)) {
					ExtentTestManager.fail_Step_Detailed("Unable To Click on: " + stepDescription);
					logger.info("Unable To Click on: " + stepDescription);
					

				} else {
					ExtentTestManager.pass_Step_Detailed("Sucessfuly Clicked on: " + stepDescription);
					logger.info("Sucessfuly Clicked on: " + stepDescription);
					

				}
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		return status;

	}

//	public boolean clickUsingReplace1(String locator, String value, String valueToReplace, String stepDescription) {
//		boolean status = false;
//
//		try {
//			value = value.replace("#replace", valueToReplace);
//			waitTillElementClickable(locator, value, stepDescription);
//
//			WebElement clickk = driver.findElement(By.xpath(value));
//
//			try {
//
//				clickk.click();
//			} catch (NoSuchElementException e) {
//				JavascriptExecutor js = (JavascriptExecutor) driver;
//				js.executeScript("arguments[0].click();", clickk);
//				status = true;
//			}
//
//			if (driver.findElements(By.xpath(value)).size() == 1) {
//
//				ExtentTestManager.pass_Step_Detailed("Sucessfuly Clicked on: " + stepDescription);
//				status = true;
//			} else {
//				ExtentTestManager.fail_Step_Detailed("Unable To Click on: " + stepDescription);
//
//				return false;
//			}
//			status = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return status;
//
//	}
	
	
		
	public boolean clickUsingReplace(String locator, String xPath, String valueToReplace, String stpDescription) throws Exception {
		 
		boolean status = false;
 
		try {
			xPath = xPath.replace("#replace", valueToReplace);
			if (!waitForPresenceAndVisibilityOfElement("EVALUATEXPATH", xPath)) {
 
				//GlobalVariables.errorMap.put("error", "Failed : Unable to Find the WebElement : " + stpDescription);
 
				return false;
 
			}
 
			WebElement buttonOrLink = createWebElement("EVALUATEXPATH", xPath);
 
			boolean booleanWait = true;
 
			int wt = 0;
 
			while (booleanWait) {
 
				try {
 
					if (waitForElementToBeClickable("EVALUATEXPATH", xPath)) {
 
						try {
 
							buttonOrLink.click();
 
							break;
 
						} catch (ElementNotInteractableException e) {
 
							JavascriptExecutor js = (JavascriptExecutor) driver;
 
							js.executeScript("arguments[0].click();", buttonOrLink);
 
							break;
 
						}
					} else {
 
						JavascriptExecutor js = (JavascriptExecutor) driver;
 
						js.executeScript("arguments[0].click();", buttonOrLink);
 
						break;
 
					}
				} catch (ElementClickInterceptedException e) {
 
					Thread.sleep(2000);
 
					wt = wt + 2;
 
				} catch (ElementNotInteractableException e) {
 
					Thread.sleep(2000);
 
					wt = wt + 2;
				} catch (StaleElementReferenceException e) {
 
					Thread.sleep(2000);
					
 
					wt = wt + 2;
 
				}
				if (wt > 120) {
 
					
					ExtentTestManager.fail_Step_Detailed("Unable To Click on: " + stpDescription);
					ExtentTestManager.captureScreenshot_Base64_Detailed("Unable To Click on: " + stpDescription);
					logger.info("Unable To Click on: " + stpDescription);
					

					return false;
 
				}
 
			}
			ExtentTestManager.pass_Step_Detailed("Sucessfuly Clicked on: " + stpDescription);
			logger.info("Sucessfuly Clicked on: " + stpDescription);
			
			status = true;
 
		} catch (Exception e) {
			ExtentTestManager.fail_Step_Detailed("Unable To Click on: " + stpDescription);
			ExtentTestManager.captureScreenshot_Base64_Detailed("Unable To Click on: " + stpDescription);
			logger.info("Unable To Click on: " + stpDescription);
			System.out.println(e);
 
			e.printStackTrace(System.out);
 
 
			return false;
 
		}
 
		return status;
 
	}

	public boolean existReplace(String locator, String value, String text, String stepDescription) {
		boolean status = false;

		try {
			value = value.replace("#replace", text);
			waitTillElementPresent(locator, value, stepDescription);
			int size = driver.findElements(By.xpath(value)).size();

			if (size == 1) {
				status = true;
				ExtentTestManager.pass_Step_Detailed("Exist: " + stepDescription);
				ExtentTestManager.captureScreenshot_Base64_Detailed("Exist: " + stepDescription);
				logger.info("Exist: " + stepDescription);
			} else {
				ExtentTestManager.fail_Step_Detailed("Not Exist: " + stepDescription);
				ExtentTestManager.captureScreenshot_Base64_Detailed("Not Exist: " + stepDescription);
				logger.info("Not Exist: " + stepDescription);

				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public boolean exist(String locator, String value, String stepDescription) {
		boolean status = false;

		try {
			waitTillElementPresent(locator, value, stepDescription);
			int size = driver.findElements(By.xpath(value)).size();

			if (size == 1) {
				status = true;
				ExtentTestManager.pass_Step_Detailed("Exist: " + stepDescription);
				ExtentTestManager.captureScreenshot_Base64_Detailed("Exist: " + stepDescription);
				logger.info("Exist: " + stepDescription);
				
			} else {
				ExtentTestManager.fail_Step_Detailed("Not Exist: " + stepDescription);
				ExtentTestManager.captureScreenshot_Base64_Detailed("Not Exist: " + stepDescription);
				logger.info("Not Exist: " + stepDescription);

				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public boolean enterText(String locator, String value, String text, String stepDescription, String step) {
		boolean status = false;

		try {
			waitTillElementPresent(locator, value, stepDescription);
			WebElement element = driver.findElement(By.xpath(value));
			element.clear();
			
			if (!text.isEmpty()) {
				waitTillElementPresent(locator, value, stepDescription);
				element.sendKeys(text);
				
				int size = driver.findElements(By.xpath(value)).size();

				if (size == 1) {
					if(step.equalsIgnoreCase("password")) {
						text="********";
					}
						ExtentTestManager.pass_Step_Detailed("Successfully entered " + text + ": " + stepDescription);
						logger.info("Successfully entered " + text + ": " + stepDescription);
					
					
					status = true;
				} else {
					
					if(step.equalsIgnoreCase("password")) {
						text="********";
					}
					
					ExtentTestManager.fail_Step_Detailed("Unable to enter: " + text + ": " + stepDescription);
					logger.info("Unable to enter: " + text + ": " + stepDescription);
					return false;
				}
			} else {
				
				if(step.equalsIgnoreCase("password")) {
					text="********";
				}
				
				ExtentTestManager.fail_Step_Detailed("Unable to enter: " + stepDescription);
				logger.info("Unable to enter: " + text + ": " + stepDescription);
				
				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			
		}
		return status;

	}

	public boolean launchApplication(String url) {
		boolean status = false;

		try {
			if (!url.isEmpty()) {
				driver.get(url);
				ExtentTestManager.pass_Step_Detailed("Successfully launched " + url + " ");
				logger.info("Successfully launched " + url + " ");
			} else {
				ExtentTestManager.fail_Step_Detailed("Unable to launch " + url + " ");
				logger.info("Unable to launch " + url + " ");
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

				} else {
					ExtentTestManager.fail_Step_Detailed("Unable to locate the: " + stepDescription);
					logger.info("Unable to locate the: " + stepDescription);
					return false;

				}

			}

			if (locator.equalsIgnoreCase("name")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(value)));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(value)));
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 1) {
					status = true;
				} else {
					return false;
				}

			}

			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	public boolean waitForPresenceAndVisibilityOfElements(String locator, String value, String stepDescription) {
		boolean status = false;
		try {
			System.out.println("");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(Driver.MaxTime)));

			if (locator.equalsIgnoreCase("xpath")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath(value)));
				
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 1) {
					status = true;

				} else {
					ExtentTestManager.fail_Step_Detailed("Unable to locate the: " + stepDescription);
					logger.info("Unable to locate the: " + stepDescription);
					return false;

				}

			}

			if (locator.equalsIgnoreCase("name")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(value)));
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 1) {
					status = true;
				} else {
					return false;
				}

			}

			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}

	public boolean waitTillElementNotPresent(String locator, String value, String stepDescription) {
		boolean status = false;
		try {
			System.out.println("");
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.valueOf(Driver.MaxTime)));

			if (locator.equalsIgnoreCase("xpath")) {
				wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(value)));
				//wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(value)));
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 0) {
					status = true;

				} else {
					ExtentTestManager.fail_Step_Detailed("Unable to locate the: " + stepDescription);
					logger.info("Unable to locate the: " + stepDescription);
					return false;

				}

			}

			if (locator.equalsIgnoreCase("name")) {
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.name(value)));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(value)));
				int size = driver.findElements(By.xpath(value)).size();
				if (size == 1) {
					status = true;
				} else {
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

				} else {
					ExtentTestManager.fail_Step_Detailed("Unable to locate the: " + stepDescription);
					logger.info("Unable to locate the: " + stepDescription);
					return false;

				}
			}

			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	
	
	
	
	public boolean enterTextKeyboardAction(String locator, String value, String text, String stepDescription) {
		boolean status = false;

		try {
			waitTillElementPresent(locator, value, stepDescription);
			WebElement element = driver.findElement(By.xpath(value));
			element.clear();
			
			if (!text.isEmpty()) {
				waitTillElementPresent(locator, value, stepDescription);
				element.sendKeys(text);
				Thread.sleep(10000);
				// To click downArrow
				Actions actions = new Actions(driver);
		        actions.sendKeys(element, Keys.ARROW_DOWN).build().perform();

		        // To press Enter afterward
		        element.sendKeys(Keys.ENTER);
				
				int size = driver.findElements(By.xpath(value)).size();

				if (size == 1) {

					ExtentTestManager.pass_Step_Detailed("Successfully entered " + text + ": " + stepDescription);
					logger.info("Successfully entered " + text + ": " + stepDescription);
					
					status = true;
				} else {
					ExtentTestManager.fail_Step_Detailed("Unable to enter: " + text + ": " + stepDescription);
					logger.info("Unable to enter: " + text + ": " + stepDescription);
					
					
					return false;
				}
			} else {
				ExtentTestManager.fail_Step_Detailed("Unable to enter: " + stepDescription);
				logger.info("Unable to enter: " + text + ": " + stepDescription);
				
				return false;
			}
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;

	}
	
	
	
	
	

}
