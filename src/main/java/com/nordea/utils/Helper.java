package com.nordea.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Helper {

	final Logger alogger = LogManager.getLogger(Helper.class);
	private String name;
	private static WebDriver selDriver;

	public static WebDriver getSelDriver() {
		return selDriver;
	}

	public static void setSelDriver(WebDriver drv) {
		selDriver = drv;
	}

	public Helper() {

	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Properties readpropertiesfileall() {
		Properties properties = new Properties();
		try {
			File file = new File(this.name);
			String rlocation = System.getProperty("user.dir");
			System.out.println("Looking for File " + rlocation + "\\src\\main\\resources\\" + this.name);
			FileInputStream fileInput = new FileInputStream(rlocation + "\\src\\main\\resources\\" + file);
			properties.load(fileInput);
			fileInput.close();
			return properties;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return properties;
		} catch (IOException e) {
			e.printStackTrace();
			return properties;
		}

	}

	public String readpropertiesfile(String keyname) {
		String retkeyvalue = "";
		try {
			File file = new File(this.name);
			// System.out.println("File name used is:"+this.name);
			// System.out.println(System.getProperty("user.dir"));
			String rlocation = System.getProperty("user.dir");
			FileInputStream fileInput = new FileInputStream(rlocation + "\\" + file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			Enumeration<Object> enuKeys = properties.keys();
			while (enuKeys.hasMoreElements()) {
				String key = (String) enuKeys.nextElement();
				if (key.contains(keyname)) {
					retkeyvalue = properties.getProperty(key);
					break;
				}
			}
			return retkeyvalue;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return retkeyvalue;
		} catch (IOException e) {
			e.printStackTrace();
			return retkeyvalue;
		}

	}

	public void updatePropertyValue(String propname, String propvalue) {
		// Properties properties = new Properties();
		Properties properties = readpropertiesfileall();
		try {
			File file = new File(this.name);
			String rlocation = System.getProperty("user.dir");
			FileOutputStream fileOutput = new FileOutputStream(rlocation + "\\" + file);
			properties.setProperty(propname, propvalue);
			properties.store(fileOutput, "updated with: " + propvalue);
			fileOutput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

	public void CaptureScreesnhot(WebDriver drv, String filename) throws IOException {
		File src = ((TakesScreenshot) drv).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(src, new File(filename));
	}

	public void createCSVFile(String outfilepath, String colnamesarr, String colvals) throws Exception

	{
		File file = new File(outfilepath);
		System.out.println("File path " + outfilepath);
		if (!file.exists()) {
			file.createNewFile();

		}
		FileWriter fileWritter = null;
		fileWritter = new FileWriter(file, true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
		FileReader reader = null;
		reader = new FileReader(file);
		BufferedReader buffreader = new BufferedReader(reader);
		String[] arrcolnames = colnamesarr.split(";");
		String[] arrcolvalues = colvals.split(";");
		StringBuilder sb = new StringBuilder();
		char delim = '\u0022';
		for (String incolname : arrcolnames) {
			sb.append(delim + incolname + delim + ",");
		}
		if (getstreamlength(buffreader) == 0) {
			bufferWritter.append(sb.toString());
			bufferWritter.newLine();
		}

		sb.setLength(0);
		for (String incolname : arrcolvalues) {
			sb.append(delim + incolname + delim + ",");
		}
		bufferWritter.append(sb.toString());
		bufferWritter.newLine();
		bufferWritter.close();

	}

	public int getstreamlength(BufferedReader rdr) throws Exception {
		int streeamlength = 0;
		try {
			streeamlength = rdr.readLine().toString().length();
		} catch (Exception e2) {
			streeamlength = 0;
		} finally {
			// return streeamlength;
		}
		return streeamlength;
	}

	public void creatextentReport(String colvals) {
		// "TC01_1;VeifyImpact;New;"+sl2.getFirstSelectedOption().getText()+";Pass");
		// loadConfig(new
		// File("D:\\Users\\Prasanna\\Automation\\Selenium\\extentreports-java-2.41.2\\extentreports-java-2.41.2\\extent-config.xml"));
		// File rfile = new File(reportfilepath);
		// ExtentReports report;
		// if (!rfile.exists() )

		// {
		alogger.error("Inside Create Extnert");
		try {
			String rlocation = System.getProperty("user.dir");
			String reportfilepath = rlocation + "\\test-output\\result\\" + "testresult.html";
			String screenshot_path = rlocation + "\\test-output\\result\\screenshot";
			createdir(screenshot_path);
			String strtimestatmp = new SimpleDateFormat("ddMMMYYYYhhmmss").format(new Date());
			ExtentReports report = new ExtentReports(reportfilepath, false);
			// }
			// String[] arrcolnames = colnamesarr.split(";");
			alogger.error("vlues are" + colvals);
			String[] arrcolvalues = colvals.split(";");
			String tcname = arrcolvalues[0];
			String tcfield = arrcolvalues[1];
			String strexp = arrcolvalues[2];
			String stract = arrcolvalues[3];
			String status = "pass";
			if (!strexp.equals(stract)) {
				status = "fail";
			}
			ExtentTest logger = report.startTest(tcname);

			if (status.equalsIgnoreCase("pass")) {
				alogger.error("In Pass");
				logger.log(LogStatus.PASS, tcfield + ": Expected Result- " + strexp + " : Actual Result-" + stract);
				alogger.error("In Pass Out");
			} else if (status.equalsIgnoreCase("fail")) {
				alogger.error("In Fail ");
				CaptureScreesnhot(Helper.getSelDriver(), screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
				String image = logger.addScreenCapture(screenshot_path + "\\Image_" + strtimestatmp + ".jpg");
				logger.log(LogStatus.FAIL, tcfield + ": Expected Result- " + strexp + " : Actual Result-" + stract,
						image);
				alogger.error("Out Fail ");
			} else if (status.equalsIgnoreCase("info")) {
				logger.log(LogStatus.INFO, tcfield);
			} else if (status.equalsIgnoreCase("warn")) {
				logger.log(LogStatus.WARNING, tcfield);
			}

			report.endTest(logger);
			report.flush();
		} catch (Exception ex) {
			alogger.error("Error in function CreateExtentReport" + ex.toString());
		}

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

}
