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

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentIt {
	final Logger alogger = LogManager.getLogger(ExtReporter.class);
	private WebDriver selDriver;
	private String rlocation = "";
	public String reportfilepath = "";
	private String screenshot_path = "";
	String colnamesarr = "TestStep;FieldName;Expected;Actual;Status";

	public WebDriver getSelDriver() {
		return selDriver;
	}

	public void setSelDriver(WebDriver drv) {
		alogger.info("set driver in Ext Class Access mofified");
		selDriver = drv;
	}

	public ExtentIt() {
		rlocation = System.getProperty("user.dir");
		reportfilepath = rlocation + "\\test-output\\result\\" + "testresult.html";
		screenshot_path = rlocation + "\\test-output\\result\\screenshot";
		createdir(screenshot_path);
	}

	public void creatextentReport(ExtentTest logger, String colvals) throws IOException {
		alogger.info("Inside create reports function");
		String[] arrcolvalues = colvals.split(";");
		String tcdesc = arrcolvalues[0].trim();
		String strfld = arrcolvalues[1].trim();
		String strexp = arrcolvalues[2].trim();
		String stract = arrcolvalues[3].trim();
		String status = arrcolvalues[4].trim();

		if (status.equalsIgnoreCase("pass")) {
			alogger.info("Inside status Passs");
			String strtimestatmp = new SimpleDateFormat("ddMMMYYYYhhmmss").format(new Date());
			if (selDriver == null) {
				alogger.info("Seldriver was null inside extent report");
			}
			CaptureScreesnhot(selDriver, screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
			String image = logger.addScreenCapture(screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
			logger.log(LogStatus.PASS,
					tcdesc + ": Field Name- " + strfld + " Expected Result- " + strexp + " : Actual Result-" + stract,
					image);
		} else if (status.equalsIgnoreCase("fail")) {
			String strtimestatmp = new SimpleDateFormat("ddMMMYYYYhhmmss").format(new Date());
			CaptureScreesnhot(selDriver, screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
			String image = logger.addScreenCapture(screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
			logger.log(LogStatus.FAIL,
					tcdesc + ": Field Name- " + strfld + " Expected Result- " + strexp + " : Actual Result-" + stract,
					image);
		} else if (status.equalsIgnoreCase("info")) {
			String strtimestatmp = new SimpleDateFormat("ddMMMYYYYhhmmss").format(new Date());
			CaptureScreesnhot(selDriver, screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
			String image = logger.addScreenCapture(screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
			logger.log(LogStatus.INFO,
					tcdesc + ": Field Name- " + strfld + " Expected Result- " + strexp + " : Actual Result-" + stract,
					image);
		} else if (status.equalsIgnoreCase("warn")) {
			String imgpath = System.getProperty("user.dir") + "\\Report\\" + screenshot_path;
			this.CaptureScreesnhot(selDriver, imgpath);
			// String image = logger.addScreenCapture(imgpath);
			logger.log(LogStatus.WARNING, tcdesc);
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

	public String getscreenshotfilename() {
		String timestamp = new SimpleDateFormat("dd-MM-yyyy_hh_mm_ss").format(new Date());
		String shtname = "Image_" + timestamp + ".jpg";
		return shtname;
	}
}