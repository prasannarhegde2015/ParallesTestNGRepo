package com.nordea.pages;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.nordea.framework.Context;
import com.nordea.framework.Global;
import com.nordea.utils.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class LoginPage {

	private WebDriver driver;

	public WebDriver getDriver() {
		return this.driver;
	}

	public void setDriver(WebDriver drv) {
		this.driver = drv;
	}

	@FindBy(id = "user_name")
	private WebElement txtUsername;

	@FindBy(id = "user_password")
	private WebElement txtPassword;

	@FindBy(id = "sysverb_login")
	private WebElement btnLogin;
	@FindBy(name = "gsft_main")
	private WebElement frmleftNav;
	@FindBy(xpath = "//div[contains(text(),\"User name or password invalid\")]")
	private WebElement divErrMsg;

	// private Context context = new Context();

	public LoginPage() throws InterruptedException {
		this.driver = Context.getDriver();
		PageFactory.initElements(driver, this);
		Global.getSeleniumUtils().waitforpgload(this.driver, "Login Page");
		Global.getSeleniumUtils();
		SeleniumUtils.setSelDriver(this.driver);
		Global.getextnit().setSelDriver(this.driver);
	}

	public void swithchtoNavFrame() {
		Global.getSeleniumUtils().Switchtoframe(this.driver, this.frmleftNav, "LoginFrame");

	}

	public void swithchtoDefault() {
		Global.getSeleniumUtils().SwitchtoDelfault(this.driver);
	}

	public void clickLogin() throws InterruptedException {

		Global.getSeleniumUtils().clickElem(this.btnLogin, "Login button");

	}

	public void watiforUname() throws InterruptedException {
		Global.getSeleniumUtils().waitforPresenseofElementtobepresent(this.driver, By.id("user_name"));
	}

	public void checkmessagetext(ExtentTest tclogger) throws InterruptedException, IOException {

		if (Global.getSeleniumUtils().isElemPresent(this.divErrMsg)) {
			Context.global();
			Global.ext.creatextentReport(tclogger, "step_01;Login Validation Field;Error  Message Shown;True;Pass");
		} else {
			Context.global();
			Global.ext.creatextentReport(tclogger, "step_01;Login Validation Field;Error  Message Shown;False;Fail");
		}
	}

	public void enterusername(String UserName) throws InterruptedException {

		Global.getSeleniumUtils().enterValue(this.txtUsername, "UserName", UserName);
	}

	public void enterpassword(String Password) throws InterruptedException {
		Global.getSeleniumUtils().enterValue(this.txtPassword, "Password", Password);

	}

}
