package com.nordea.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nordea.framework.Context;
import com.nordea.framework.Global;
import com.nordea.utils.SeleniumUtils;

public class HomePage {

	private WebDriver driver;

	public WebDriver getDriver() {
		return this.driver;
	}

	public void setDriver(WebDriver drv) {
		this.driver = drv;
	}

	@FindBy(linkText = "Incidents")
	private WebElement lnkIncidents;

	@FindBy(id = "sysverb_new")
	private WebElement btnNew;

	@FindBy(xpath = "//iframe[@title='Navigation Content']")
	private WebElement frmMainPane;

	@FindBy(xpath = "//iframe[@title='Main menu']")
	private WebElement frmleftNav;

	@FindBy(linkText = "10 Things")
	private WebElement lnktenthings;

	@FindBy(xpath = "//button[text()='Logout']")
	private WebElement btnLogout;

	public WebDriverWait wt;
	Logger logger = LogManager.getLogger(HomePage.class);

	public HomePage() throws InterruptedException {
		this.driver = Context.getDriver();
		PageFactory.initElements(driver, this);
		wt = new WebDriverWait(this.driver, 500);
		Global.getSeleniumUtils().waitforpgload(this.driver, "Home Page");
		Global.getSeleniumUtils();
		SeleniumUtils.setSelDriver(this.driver);
	}

	public void swithchtoNavFrame() {
		Global.getSeleniumUtils().Switchtoframe(this.driver, this.frmleftNav, "Left Navigation Frame");
	}

	public void swithchtoMainrame() {
		Global.getSeleniumUtils().Switchtoframe(this.driver, this.frmMainPane, "Content Frame");
	}

	public void swithchtoDefault() {
		Global.getSeleniumUtils().SwitchtoDelfault(this.driver);
	}

	public void clickIncidentLink() throws InterruptedException {

		Global.getSeleniumUtils().clickElem(this.lnkIncidents, "Incidents Link");

	}

	public void clickCreateNew() throws InterruptedException {
		Global.getSeleniumUtils().clickElem(this.btnNew, "New Button");
	}

	public void waitfor10thingslink() throws InterruptedException {
		Global.getSeleniumUtils().waitforPresenseofElement(this.driver, this.lnktenthings, "Link 10 things");
	}

	public void clickLogoutLink() throws InterruptedException {
		Global.getSeleniumUtils().clickElem(this.btnLogout, "logout Button");
	}

}
