package com.nordea.testsuite;

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

import com.nordea.framework.Global;
import com.nordea.framework.Local;
import com.nordea.pages.TimeConverterPage;

public class WinTest {

	final Logger tslogger = LogManager.getLogger(Release1.class);
	public Date dtstart;
	public Date dtend;

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
			tslogger.info("**********************Test Initilzed**************");
			Global.getwindriverutils().InitializeWinDriver();
			System.out.println("No init eroro");
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
	}

	@Test
	public void tc01() throws InstantiationException, IllegalAccessException, InterruptedException {

		Local.pages().getpage(TimeConverterPage.class).pgmthd1();

	}

	@AfterMethod
	public void logafter(ITestResult tresult) {
		tslogger.info("*********************** Test method Completed : [" + tresult.getMethod().getMethodName()
				+ "] ==> " + new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").format(new Date())
				+ "*****************************************************");
		dtend = new Date();
		long diff = dtend.getTime() - dtstart.getTime();
		tslogger.info("************* Total Time Taken *************: " + (diff / 1000) + " Seconds");
	}

	@AfterTest
	public void testcleanup() {
		// Context.getWinDriver().quit();
		tslogger.info("*********************** Test Completed *******************");
	}
}
