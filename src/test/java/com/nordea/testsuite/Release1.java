package com.nordea.testsuite;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.nordea.framework.Context;
import com.nordea.framework.Global;
import com.nordea.framework.Local;
import com.nordea.pages.LoginPage;
import com.nordea.workflows.IncidentCreation;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Release1 {

	final Logger tslogger = LogManager.getLogger(Release1.class);
	public Date dtstart;
	public Date dtend;
	ExtentReports report = null;

	@BeforeSuite
	public void beforeSuite() {
		System.out.println("BeforeSuite");
		System.setProperty("log4j.configurationFile", "lg4j2.xml");
	}

	@BeforeClass
	public void initlog() {
		System.out.println("Getting conf File Before Class");

	}

	@BeforeTest
	public void initializetest() {
		try {
			System.out.println("Getting conf File");
			System.setProperty("log4j.configurationFile", "log4j2.xml");
			Context.global();
			Global.getextnit();
			report = new ExtentReports(Global.getextnit().reportfilepath, false);
			tslogger.info("**********************Test Initilzed**************");
			// Global.getdriverutils().InitializeDriver();
		} catch (Exception e) {
			System.out.println("Errro" + e);
		}

	}

	@BeforeMethod
	public void itest(Method method) {
		tslogger.info("*********************** Test method  Started : [" + method.getName() + "] ==> "
				+ new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())
				+ "*****************************************************");
		dtstart = new Date();
		Global.getdriverutils().InitializeDriver();
	}

	@Test(groups = { "postive" })
	public void tc01() throws InstantiationException, IllegalAccessException, InterruptedException, IOException {
		ExtentTest logger1 = report.startTest("TC_01");
		Local.workflows().getworkflow(IncidentCreation.class).performLogin("admin", "ServiceNow97bd916$");
		Local.workflows().getworkflow(IncidentCreation.class).clickCreateNewIncident(logger1);
		Local.workflows().getworkflow(IncidentCreation.class).performLogout();
		report.endTest(logger1);
		report.flush();
	}

	@Test(groups = { "negetive" })
	public void tc02() throws InstantiationException, IllegalAccessException, InterruptedException, IOException {
		ExtentTest loggertc2 = report.startTest("TC_02");
		Local.workflows().getworkflow(IncidentCreation.class).performLogin("admin", "ServiceNow9337bd916$");
		Local.pages().getpage(LoginPage.class).checkmessagetext(loggertc2);
		report.endTest(loggertc2);
		report.flush();
	}

	@AfterMethod
	public void logafter(ITestResult tresult) {

		tslogger.info("*********************** Test method Completed : [" + tresult.getMethod().getMethodName()
				+ "] ==> " + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())
				+ "*****************************************************");
		dtend = new Date();
		long diff = dtend.getTime() - dtstart.getTime();
		tslogger.info("************* Total Time Taken *************: " + (diff / 1000) + " Seconds");
		tslogger.info("************* test result *************: " + tresult.toString());
		Context.getDriver().close();

	}

	@AfterTest
	public void testcleanup() {
		// Context.getDriver().close();
		// Context.getDriver().quit();
		Context.getDriver().quit();
		tslogger.info("*********************** Test Completed *******************");
	}

}
