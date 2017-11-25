package com.nordea.framework;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.winium.WiniumDriver;

public class Context {
	private static Global gbl;
	private static com.nordea.framework.Local lcl;
	private static WebDriver globalDriver;
	private static WiniumDriver globalWinDriver;

	public static Global global() {
		gbl = new Global();
		return gbl;
	}

	public static com.nordea.framework.Local local() {
		System.out.println("Inside Context");
		lcl = new com.nordea.framework.Local();
		return lcl;
	}

	public static WebDriver getDriver() {
		return globalDriver;
	}

	public static void setDriver(WebDriver drv) {
		globalDriver = drv;
	}

	public static WiniumDriver getWinDriver() {
		return globalWinDriver;
	}

	public static void setWinDriver(WiniumDriver wdrv) {
		globalWinDriver = wdrv;
	}

	/*
	 * public Global getglobal() { return this.gbl; } public Local getlocal() {
	 * return this.lcl; }
	 */

}
