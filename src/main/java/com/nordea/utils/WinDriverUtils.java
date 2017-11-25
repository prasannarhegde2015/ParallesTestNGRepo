package com.nordea.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.winium.DesktopOptions;
import org.openqa.selenium.winium.WiniumDriver;

import com.nordea.framework.Context;
import com.nordea.framework.Global;

public class WinDriverUtils {

	public WiniumDriver globalWinDriver = null;

	public void InitializeWinDriver() throws MalformedURLException {
		System.out.println("Starting Winappr ...");
		Global.gethelperutils().setName("config.properties");
		Helper hlp = Global.gethelperutils();
		hlp.setName("config.properties");
		String winapp = hlp.readpropertiesfileall().getProperty("winapppath");
		System.out.println("winapp" + winapp);
		DesktopOptions options = new DesktopOptions();
		options.setApplicationPath(winapp);
		try {
			globalWinDriver = new WiniumDriver(new URL("http://localhost:1223"), options);
			if (globalWinDriver == null)

			{
				System.out.println("global Driver was not created...");
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Context.setWinDriver(globalWinDriver);
		// context.setDriver(globalDriver);

	}

}
