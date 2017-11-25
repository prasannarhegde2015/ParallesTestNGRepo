package com.nordea.pages;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.winium.WiniumDriver;

import com.nordea.framework.Context;

public class TimeConverterPage {
	private WiniumDriver wdrv;

	public TimeConverterPage() {
		this.wdrv = Context.getWinDriver();
	}

	public void pgmthd1() {
		System.out.println("Inside Windows Page Method ");
		try {
			wdrv.findElementById("textBox1").sendKeys("gg");
			wdrv.findElementById("button1").click();
			Set<String> handles = wdrv.getWindowHandles();
			System.out.println("Got 11111alert winwindow:   ===  " + handles.size());
			Iterator<String> iterator = handles.iterator();
			while (iterator.hasNext()) {
				try {
					String wns = (String) iterator.next();
					System.out.println("String" + wns);
				} catch (Exception e) {
					System.out.println("Long" + iterator.next());
				}

			}
			// wdrv.findElementByName("OK").click();
		} catch (Exception ex) {
			System.out.println("Error In test Case" + ex.toString());
		}

	}

}
