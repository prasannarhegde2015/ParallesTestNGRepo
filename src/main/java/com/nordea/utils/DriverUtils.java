package com.nordea.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.nordea.framework.Context;
import com.nordea.framework.Global;

public class DriverUtils {
	public String CHROMEdriverpath = "";
	public String iedriverpath = "";
	public WebDriver globalDriver = null;
	public String baseurl = "";
	final Logger logger = LogManager.getLogger(DriverUtils.class);

	public void InitializeDriver() {
		System.out.println("Starting Browser ...");
		Global.gethelperutils().setName("config.properties");
		Helper hlp = Global.gethelperutils();
		hlp.setName("config.properties");
		String browser = hlp.readpropertiesfileall().getProperty("browser");

		switch (browser.toLowerCase()) {
		case "ie": {
			iedriverpath = hlp.readpropertiesfileall().getProperty("iedriverpath");
			baseurl = hlp.readpropertiesfileall().getProperty("baseurl");
			System.setProperty("webdriver.ie.driver", iedriverpath);
			DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			globalDriver = new InternetExplorerDriver(cap);
			globalDriver.manage().window().maximize();
			globalDriver.get(baseurl);
			Context.setDriver(globalDriver);
			// context.setDriver(globalDriver);
			break;
		}
		case "chrome": {
			CHROMEdriverpath = hlp.readpropertiesfileall().getProperty("chromedriverpath");
			baseurl = hlp.readpropertiesfileall().getProperty("baseurl");
			System.setProperty("webdriver.chrome.driver", CHROMEdriverpath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("chrome.switches", "--disable-extensions");
			options.addArguments("disable-infobars");
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			options.setExperimentalOption("prefs", prefs);
			globalDriver = new ChromeDriver(options);
			globalDriver.manage().window().maximize();
			globalDriver.get(baseurl);
			Context.setDriver(globalDriver);
			// context.setDriver(globalDriver);
			break;
		}
		case "firefox": {
			// Set all desired Firefox profiles using profiles Ini
			try {

				File file = new File("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				FirefoxBinary fbinary = new FirefoxBinary(file);
				ProfilesIni profiles = new ProfilesIni();
				FirefoxProfile fp = profiles.getProfile("Selenium");
				globalDriver = new FirefoxDriver(fbinary, fp);
				globalDriver.manage().window().maximize();
				globalDriver.get(baseurl);
				Context.setDriver(globalDriver);
			} catch (Exception e) {
				logger.info("Failure in  initializing Firefox Driver: " + e.toString());
			}

			break;
		}
		default:
			break;
		}

	}

	public WebDriver getDriver()

	{
		if (globalDriver == null) {
			InitializeDriver();

		}
		return globalDriver;
	}

}
