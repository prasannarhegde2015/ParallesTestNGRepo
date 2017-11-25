package com.nordea.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtReporter {

	final Logger alogger = LogManager.getLogger(ExtReporter.class);
	private static WebDriver selDriver;
	private String clstestname = "TestCase";
	private static ExtentTest logger = null;
	private static ExtentReports report = null;
	private static String rlocation = "";
	private static String reportfilepath = "";
	private static String screenshot_path = "";

	public String gettestname() {
		return this.clstestname;
	}

	public void settestname(String tcname) {
		alogger.info("Value Got set as " + tcname);
		this.clstestname = tcname;
	}

	public static WebDriver getSelDriver() {
		return selDriver;
	}

	public static void setSelDriver(WebDriver drv) {
		selDriver = drv;
	}

	public ExtReporter(String tcname) {
		alogger.info("Inside Constrcutor ExtReporter");
		rlocation = System.getProperty("user.dir");
		reportfilepath = rlocation + "\\test-output\\result\\" + "testresult.html";
		screenshot_path = rlocation + "\\test-output\\result\\screenshot";
		createdir(screenshot_path);
		report = new ExtentReports(reportfilepath, false);
		alogger.info("Test Case Name inside Constrcuotr is: " + tcname);
		logger = report.startTest(this.clstestname);

	}

	public void creatextentReport(String colvals) {
		// "TC01_1;VeifyImpact;New;"+sl2.getFirstSelectedOption().getText()+";Pass");
		// loadConfig(new
		// File("D:\\Users\\Prasanna\\Automation\\Selenium\\extentreports-java-2.41.2\\extentreports-java-2.41.2\\extent-config.xml"));
		// File rfile = new File(reportfilepath);
		// ExtentReports report;
		// if (!rfile.exists() )

		// {
		alogger.info("Inside Create Extnert");
		try {

			alogger.info("values are: " + colvals);
			String[] arrcolvalues = colvals.split(";");
			String tcfield = arrcolvalues[0];
			String strexp = arrcolvalues[1];
			String stract = arrcolvalues[2];
			String status = "pass";
			if (!strexp.equals(stract)) {
				status = "fail";
			}

			if (status.equalsIgnoreCase("pass")) {
				alogger.info("In Pass");
				logger.log(LogStatus.PASS, tcfield + ": Expected Result- " + strexp + " : Actual Result-" + stract, "");
				alogger.info("In Pass Out");
			} else if (status.equalsIgnoreCase("fail")) {
				alogger.info("In Fail ");
				String strtimestatmp = new SimpleDateFormat("ddMMMYYYYhhmmss").format(new Date());
				CaptureScreesnhot(ExtReporter.getSelDriver(), screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
				String image = logger.addScreenCapture(screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
				alogger.info("casedetails  " + tcfield + ": Expected Result- " + strexp + " : Actual Result-" + stract);
				logger.log(LogStatus.FAIL, tcfield + ": Expected Result- " + strexp + " : Actual Result-" + stract,
						image);
				alogger.info("Out Fail ");
			} else if (status.equalsIgnoreCase("info")) {
				logger.log(LogStatus.INFO, tcfield);
			} else if (status.equalsIgnoreCase("warn")) {
				logger.log(LogStatus.WARNING, tcfield);
			}

		} catch (Exception ex) {
			alogger.error("[Error in function CreateExtentReport]==========>   " + ex.toString());
		}

	}

	public void CaptureScreesnhot(WebDriver drv, String filename) throws IOException {
		File src = ((TakesScreenshot) drv).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(filename));
	}

	public void createdir(String dirname) {
		File theDir = new File(dirname);

		// if the directory does not exist, create it
		if (!theDir.exists()) {
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;

			try {
				theDir.mkdir();
				result = true;
			} catch (SecurityException se) {
				// handle it
			}
			if (result) {
				System.out.println("DIR created");
			}
		}
	}

	public void endReport() {
		report.endTest(logger);
		report.flush();
	}
}