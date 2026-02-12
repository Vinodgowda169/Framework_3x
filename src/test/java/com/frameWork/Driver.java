package com.frameWork;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ini4j.Wini;
import org.json.JSONObject;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphbuilder.struc.LinkedList;

public class Driver extends Utils{
	public static GenericSeleniumActions gsa;
	public static Map<String, String> webElemets=null;
	public static JSONObject PrefValues = new JSONObject();
	public static Map<String,Map<String, String>> returnInput = new LinkedHashMap<String,Map<String, String>>();
	public static Map<String,List<Map<String, String>>> inputTestDataMap = new LinkedHashMap<String,List<Map<String, String>>>();
	public static Map<String,LinkedHashMap<String, String>> TestData = new LinkedHashMap<String,LinkedHashMap<String, String>>();
	public static Map<String, String> scenarioMap = new LinkedHashMap<String, String>();
	public static Map<String, LinkedHashMap<String, String>> testCaseMap = new  LinkedHashMap<String, LinkedHashMap<String, String>>();
	public static Map<String,Map<String, String>> testDataMap = new LinkedHashMap<String,Map<String, String>>();
	public static Map<String,List<Map<String, String>>> businessLogicFunctionsMap = new LinkedHashMap<String,List<Map<String, String>>>();
	

	
	public static WebDriver driver = null;

	public enum browserList {
		Firefox, Chrome, ChromeHeadless, Incognito, Edge, API;
	}

	public void typesOfBrowser(String BROWSER) throws Exception {
		boolean is64bit = false;
		// JFrame frame = showPreLoader();
		String OS = System.getProperty("os.name");
		if (OS.contains("Windows")) {
			is64bit = (System.getenv("ProgramFiles(x86)") != null);
		} else {
			is64bit = (System.getProperty("os.arch").indexOf("64") != -1);
		}

		if (BROWSER.equalsIgnoreCase("Chrome-Headless")) {
			BROWSER = "ChromeHeadless";
		}
		if (BROWSER.equalsIgnoreCase("Chrome-Incognito")) {
			BROWSER = "Incognito";
		}
		switch (browserList.valueOf(BROWSER)) {

		case Firefox:

			System.out.println("OS: " + OS);
			if (OS.toLowerCase().contains("linux")) {
				System.out.println("Linux Selected");
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriverLinux");
			} else if (is64bit) {
				System.out.println("Win 64 Selected");
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver_64bits.exe");
			} else {
				System.out.println("Win 32 Selected");
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/geckodriver_32bits.exe");
			}

			FirefoxProfile profile = new FirefoxProfile();
			FirefoxOptions FFoptions = new FirefoxOptions();

			// FirefoxOptions Firefoxcaps = DesiredCapabilities.firefox();
			driver = new FirefoxDriver(FFoptions);
			driver.manage().timeouts().pageLoadTimeout(240, TimeUnit.SECONDS);
			Thread.sleep(2000);
			break;

		case Chrome:

			System.out.println("OS: " + OS);
			if (OS.toLowerCase().contains("linux")) {
				System.out.println("Linux Selected");
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver");
			} else if (OS.toLowerCase().contains("mac")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriverMac");
			} else {

				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
			}
			HashMap<String, Object> prefs = new HashMap<String, Object>();
			ChromeOptions options = new ChromeOptions();
			prefs.put("profile.default_content_setting_values.notifications", 1);
			
			options.addArguments("--test-type");
			options.addArguments("start-maximized");
			options.addArguments("--disable-geolocation");
			options.addArguments("--enable-strict-powerful-feature-restrictions");
			options.addArguments("--disable-notifications");
			options.addArguments("disable-infobars");
			if (OS.toLowerCase().contains("linux")) {
				options.addArguments("--no-sandbox");
			}
			options.setExperimentalOption("useAutomationExtension", false);
			// Ignore Certificates Errors
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");
			options.addArguments("force-device-scale-factor=1.5");
			// options.addArguments("--window-size=1920,1200");

			String iheadless = "";
			if (PrefValues.has("headless")) {
				iheadless = PrefValues.get("headless").toString();
			}
			try {
				if (iheadless.equalsIgnoreCase("true")) {
					options.addArguments("--headless");
					options.addArguments("--window-size=1024,768");
				}
			} catch (Exception e) {

			}
			// Download PDF Files
			if (PrefValues.has("Download_Folder_Path")) {
				String DownloadFilePath = PrefValues.get("Download_Folder_Path").toString();
				prefs.put("download.default_directory", DownloadFilePath);
				prefs.put("download.prompt_for_download", false);
				prefs.put("profile.default_content_settings.popups", 0);
			} else {
				String folderPath = System.getProperty("user.dir") + "\\ASF";
				File folder = new File(folderPath);
				if (!folder.exists()) {
					folder.mkdirs(); // creates the folder (and parent dirs if missing)
					System.out.println("Folder created at: " + folderPath);
				}
				prefs.put("download.default_directory", folderPath);
				prefs.put("plugins.always_open_pdf_externally", true);
			}

			options.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(options);
			Thread.sleep(1000);
			// driver.manage().window().maximize();
			break;

		case ChromeHeadless: {

			System.out.println("OS: " + OS);
			if (OS.toLowerCase().contains("linux")) {
				System.out.println("Linux Selected");
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver");
			} else if (OS.toLowerCase().contains("mac")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriverMac");
			} else {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/src/test/resources/drivers/chromedriver.exe");
			}

			HashMap<String, Object> headlessprefs = new HashMap<String, Object>();
			ChromeOptions headlessoptions = new ChromeOptions();
			if (OS.toLowerCase().contains("linux")) {
				headlessoptions.addArguments("--no-sandbox");
			}
			headlessoptions.addArguments("--disable-setuid-sandbox");
			headlessprefs.put("profile.default_content_setting_values.notifications", 2);
			headlessprefs.put("profile.default_content_setting.popups", 0);
			headlessprefs.put("autofill.profile_enabled", false);
			headlessoptions.addArguments("--test-type");
			headlessoptions.addArguments("--disable-geolocation");
			headlessoptions.addArguments("--enable-strict-powerful-feature-restrictions");
			headlessoptions.addArguments("--disable-notifications");
			headlessoptions.addArguments("disable-infobars");
			headlessoptions.setExperimentalOption("useAutomationExtension", false);
			headlessoptions.addArguments("--ignore-ssl-errors=yes");
			headlessoptions.addArguments("--ignore-certificate-errors");
			headlessoptions.addArguments("--headless=new");
			headlessoptions.addArguments("--disable-dev-shm-usage");
			headlessoptions.addArguments("force-device-scale-factor=0.5");
			headlessoptions.addArguments("--window-size=1920,1200");
			if (PrefValues.has("PDF_Download_Folder_Path")) {
				String DownloadFilePath = PrefValues.get("PDF_Download_Folder_Path").toString();
				headlessprefs.put("download.default_directory", DownloadFilePath);
				headlessprefs.put("plugins.always_open_pdf_externally", true);
			} else {
				String folderPath = System.getProperty("user.dir") + "\\ASF";
				File folder = new File(folderPath);
				if (!folder.exists()) {
					folder.mkdirs(); // creates the folder (and parent dirs if missing)
					System.out.println("Folder created at: " + folderPath);
				}

				headlessprefs.put("download.default_directory", folderPath);
				headlessprefs.put("plugins.always_open_pdf_externally", true);
			}
			headlessoptions.setExperimentalOption("prefs", headlessprefs);
			driver = new ChromeDriver(headlessoptions);
			Thread.sleep(1000);
			break;
		}
		}

	}
	public static void driverLoader() {
		try {
			Driver obj = new Driver();
			readINIFile("Dev");
			webElemets = Utils.readWebElemets(webelementsSheetRefer);
			//returnInput = inputExcelMapping();
			scenarioMap=scenarios();
			testCaseMap=testCase();
			businessLogicFunctionsMap=businessLogicFunctions();
			testDataMap=testData();
			
			
			obj.typesOfBrowser(browser);
			setExtendReport();
		} catch (Exception e) {
			e.printStackTrace();
		}
 
	}
	public static void main(String[] args) throws Exception {
		
		driverLoader();
		Controler controler = new Controler(driver);
		if(!controler.stepControler())
		{
			driver.quit();
			GlobalVariables.Executionduration=Utils.duration();
			ExtentManager.getDetailedExtentInstance();
			ExtentManager.flushReports();
			System.out.println("Excution Completed");

		}
		else
		{
			driver.quit();
			GlobalVariables.Executionduration=Utils.duration();
			ExtentManager.getDetailedExtentInstance();
			ExtentManager.flushReports();
			System.out.println("Excution Completed");

		}
	}
	
}
