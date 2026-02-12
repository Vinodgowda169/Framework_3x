package com.frameWork;



import java.sql.DriverManager;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;

@SuppressWarnings("unused")
public class ExtentTestManager extends Driver{

	
	private static ThreadLocal<ExtentTest> testDetailed = new ThreadLocal<>();
	private static ThreadLocal<ExtentTest> testSummary = new ThreadLocal<>();

	// This is used To start the Detailed report Test Instance
	public static ExtentTest startTestDetailed(String testName) {
		ExtentTest extentTest = ExtentManager.getDetailedExtentInstance().createTest(testName);
		testDetailed.set(extentTest);
		return extentTest;
	}

//This is used To Get the Detailed  report Test Instance
	public static ExtentTest getTestDetailed() {
		return testDetailed.get();
	}

//This is used To start the Summary report Test Instance
	public static ExtentTest startTestSummary(String testName) {
		ExtentTest extentTest = ExtentManager.getSummaryExtentInstance().createTest(testName);
		testSummary.set(extentTest);
		return extentTest;
	}

//This is used To Get the Summary report Test Instance
	public static ExtentTest getTestSummary() {
		return testSummary.get();
	}

	// Below are the actions for Detailed Extent Report
	public static void captureScreenshot_Base64_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		TakesScreenshot ts = (TakesScreenshot) Driver.driver;
		String str = ts.getScreenshotAs(OutputType.BASE64);
		l.info(StepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(str).build());
	}

	public static void pass_Step_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		l.pass(StepDescription);
	}

	public static void fail_Step_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		l.fail(StepDescription);
	}

	public static void skip_Step_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		l.skip(StepDescription);
	}

	public static void warning_Step_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		l.warning(StepDescription);
	}

	public static void info_Step_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		l.info(StepDescription);
	}
	
	public static void exception_Step_Detailed(Exception e) {
		ExtentTest l = testDetailed.get();
		String stackTrace = getStackTraceAsString(e);
		l.fail(MarkupHelper.createCodeBlock(stackTrace));
		
	}

	 private static String getStackTraceAsString(Throwable t) {
	        StringBuilder sb = new StringBuilder();
	        sb.append(t.toString()).append("\n");
	        for (StackTraceElement element : t.getStackTrace()) {
	            sb.append("\tat ").append(element).append("\n");
	        }
	        return sb.toString();
	    }


	public static void info_Json_Step_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		l.info(MarkupHelper.createCodeBlock(StepDescription, CodeLanguage.JSON));
	}

	public static void info_Map_Step_Detailed(String StepDescription) {
		ExtentTest l = testDetailed.get();
		l.info(StepDescription);
	}

//Below are the actions for Summary Extent Report
	public static void captureScreenshot_Base64_Summary(String StepDescription) {
		ExtentTest l = testSummary.get();
		TakesScreenshot ts = (TakesScreenshot) Driver.driver;
		String str = ts.getScreenshotAs(OutputType.BASE64);
		l.info(StepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(str).build());
	}

	public static void pass_Step_Summary(String StepDescription) {
		ExtentTest l = testSummary.get();
		l.pass(StepDescription);
	}

	public static void fail_Step_Summary(String StepDescription) {
		ExtentTest l = testSummary.get();
		l.fail(StepDescription);
	}

	public static void skip_Step_Summary(String StepDescription) {
		ExtentTest l = testSummary.get();
		l.skip(StepDescription);
	}

	public static void warning_Step_Summary(String StepDescription) {
		ExtentTest l = testSummary.get();
		l.warning(StepDescription);
	}

	public static void info_Step_Summary(String StepDescription) {
		ExtentTest l = testSummary.get();
		l.info(StepDescription);
	}
	
	public static void exception_Step_Summary(Exception e) {
		ExtentTest l = testSummary.get();
		String stackTrace = getStackTraceAsString(e);
		l.fail(MarkupHelper.createCodeBlock(stackTrace));
		
	}

	public static void info_Json_Step_Summary(String stringJsonObject) {
		ExtentTest l = testSummary.get();
		
		l.info(MarkupHelper.createCodeBlock(stringJsonObject, CodeLanguage.JSON));
	}

	public static void info_Map_Step_Summary(String StepDescription) {
		ExtentTest l = testSummary.get();
		l.info(StepDescription);
	}

}
