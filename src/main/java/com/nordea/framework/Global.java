package com.nordea.framework;

import com.nordea.utils.DriverUtils;
import com.nordea.utils.ExtReporter;
import com.nordea.utils.ExtentIt;
import com.nordea.utils.Helper;
import com.nordea.utils.SeleniumUtils;
import com.nordea.utils.WinDriverUtils;

public class Global {
	public static Helper hlpinst;
	public static ExtReporter extinst;
	public static ExtentIt ext;

	public static DriverUtils getdriverutils() {
		System.out.println("Inside Global");
		return new DriverUtils();
	}

	public static WinDriverUtils getwindriverutils() {
		System.out.println("Inside WinDriver utils");
		return new WinDriverUtils();
	}

	public static Helper gethelperutils() {
		System.out.println("Inside Helper");
		if (hlpinst == null) {
			System.out.println("Creating a new Helper instance ");
			hlpinst = new Helper();
		} else {
			System.out.println("Not creting a new isntnace as Helper instance Existed");
		}
		return hlpinst;
	}

	public static ExtReporter getextntutils(String tcname) {
		System.out.println("Inside Extnent");
		if (extinst == null) {
			System.out.println("Creating a new ExtReport instance ");
			extinst = new ExtReporter(tcname);
		} else {
			System.out.println("Not creting a new isntnace as ExtReport instance Existed");
			System.out.println("Creating a new ExtReport instance ");
			extinst = new ExtReporter(tcname);
		}
		return extinst;
	}

	public static ExtentIt getextnit() {
		System.out.println("Inside ExtnentIT");
		if (ext == null) {
			System.out.println("Creating a new ExtReport instance ");
			ext = new ExtentIt();
		} else {
			System.out.println("Not creting a new isntnace as ExtReport instance Existed");
			System.out.println("Creating a new ExtReport instance ");

		}
		return ext;
	}

	public static SeleniumUtils getSeleniumUtils() {
		System.out.println("Inside Selenium Utils");
		return new SeleniumUtils();
	}

}
