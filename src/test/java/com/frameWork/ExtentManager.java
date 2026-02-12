package com.frameWork;



import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {

	private static ExtentReports extentDetailed;
	private static ExtentReports extentSummary;

	public static ExtentReports getDetailedExtentInstance() {
		if (extentDetailed == null) {
//			String path = "config/RMB/Detail-report.html";
			String path = GlobalVariables.exeMap.get("detailedReport");

			ExtentSparkReporter reporter = new ExtentSparkReporter(path);
			// Setting the extent report html page title
			reporter.config().setDocumentTitle("Automation Report");
			// Setting the Test Name for Extent report
			reporter.config().setReportName(GlobalVariables.projectname +"- Detailed Report");

			extentDetailed = new ExtentReports();
			extentDetailed.attachReporter(reporter);

			// Adding System Info here
			extentDetailed.setSystemInfo("OS", System.getProperty("os.name"));
			extentDetailed.setSystemInfo("Java Version", System.getProperty("java.version"));
			extentDetailed.setSystemInfo("Execution Type", "Local Machine");
			extentDetailed.setSystemInfo("Browser Type", GlobalVariables.browserType);
			extentDetailed.setSystemInfo("Executed By", "Vinod");
			extentDetailed.setSystemInfo("Overall Status", "QA");
		}
		else
		{
			extentDetailed.setSystemInfo("Total Test Execution Time", GlobalVariables.Executionduration);

		}
		return extentDetailed;
	}

	public static ExtentReports getSummaryExtentInstance() {
		if (extentSummary == null) {
//			String path = "config/RMB/Summary-report.html";
			String path = GlobalVariables.exeMap.get("summaryReport");

			ExtentSparkReporter reporter = new ExtentSparkReporter(path);
			// Setting the theme to dark
			reporter.config().setTheme(Theme.DARK);
			// Setting the extent report html page title
			reporter.config().setDocumentTitle("Automation Report");
			// Setting the Test Name for Extent report
			reporter.config().setReportName("Selenium Test Report");

			extentSummary = new ExtentReports();
			extentSummary.attachReporter(reporter);

			// Adding System Info here
			extentSummary.setSystemInfo("OS", System.getProperty("os.name"));
			extentSummary.setSystemInfo("Java Version", System.getProperty("java.version"));
			extentSummary.setSystemInfo("Host Name", "Local Machine");
			extentSummary.setSystemInfo("Tester", "Naga");
			extentSummary.setSystemInfo("Environment", "QA");
		}
		return extentSummary;
	}

	public static void flushReports() {
		if (extentDetailed != null) {
			extentDetailed.flush();
		}
		if (extentSummary != null) {
			extentSummary.flush();
		}
	}

}

