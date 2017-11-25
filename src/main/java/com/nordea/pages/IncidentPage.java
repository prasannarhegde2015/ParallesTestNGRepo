package com.nordea.pages;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.nordea.framework.Context;
import com.nordea.framework.Global;
import com.nordea.utils.SeleniumUtils;
import com.relevantcodes.extentreports.ExtentTest;

public class IncidentPage {

	private WebDriver driver;
	public WebDriverWait wt;

	public IncidentPage() throws InterruptedException {
		this.driver = Context.getDriver();
		PageFactory.initElements(driver, this);
		wt = new WebDriverWait(this.driver, 500);
		Global.getSeleniumUtils().waitforpgload(this.driver, "Incident Page");
		Global.getSeleniumUtils();
		SeleniumUtils.setSelDriver(this.driver);
		Global.getextnit().setSelDriver(this.driver);

	}

	@FindBy(id = "incident.number")
	private WebElement txtIncNumber;

	@FindBy(id = "incident.short_description")
	private WebElement txtshortdesc;

	@FindBy(id = "incident.comments")
	private WebElement txtcooments;

	@FindBy(id = "incident.impact")
	private WebElement selImpact;

	@FindBy(id = "incident.state")
	private WebElement selState;

	@FindBy(id = "sysverb_insert")
	private WebElement btnSubmit;

	@FindBy(id = "sysverb_delete")
	private WebElement btnDelete;

	@FindBy(id = "ok_button")
	private WebElement btnOKDelete;

	@FindBy(xpath = "//div[@class='activity_field' and  @label='Additional comments']")
	private WebElement elemcomment;

	public void EnterIncidentNumber(String incnumber) throws InterruptedException {
		Global.getSeleniumUtils().enterValue(this.txtIncNumber, "Incident Number", incnumber);
	}

	public void EnterIncDesc(String incdec) throws InterruptedException {
		Global.getSeleniumUtils().enterValue(this.txtshortdesc, "Incident Description", incdec);
	}

	public void EnterComments(String inccmt) throws InterruptedException {
		Global.getSeleniumUtils().enterValue(this.txtcooments, "Incident Details", inccmt);
	}

	public void SelectImpact(String selimp) {
		Global.getSeleniumUtils().selectValue(this.selImpact, "Impact", selimp);
	}

	public void SelectState(String selimp1) {
		Global.getSeleniumUtils().selectValue(this.selState, "State", selimp1);
	}

	public void clickSubmit() throws InterruptedException {
		Global.getSeleniumUtils().clickElem(this.btnSubmit, "Submit");
	}

	public void Clickrdynalink(String Dynaval) throws InterruptedException {
		Global.getSeleniumUtils().clickElemByLinkText(this.driver, Dynaval, " Newly Created Dynamic Link");

	}

	public void verifynumber(String Dynaval, ExtentTest tclogger) throws IOException {

		String acttext = Global.getSeleniumUtils().getText(this.txtIncNumber, "Incident Number");
		if (Dynaval.equalsIgnoreCase(acttext)) {
			Context.global();

			Global.ext.creatextentReport(tclogger, "step_01;Incident Number;" + Dynaval + ";" + acttext + ";Pass");
		} else {
			Context.global();
			Global.ext.creatextentReport(tclogger, "step_01;Incident Number;" + Dynaval + ";" + acttext + ";Fail");
		}

	}

	public void ClickDeleteButton() throws InterruptedException {
		Global.getSeleniumUtils().clickElem(this.btnDelete, "Delete Button");
	}

	public void ScrolltoConfirmComment() throws InterruptedException {
		Global.getSeleniumUtils().scroll(this.elemcomment);
	}

	public void ClickConfirmDeleteButton() throws InterruptedException {
		Global.getSeleniumUtils().waitforPresenseofElement(this.driver, this.btnOKDelete, "Confirm Delete Button");
		Global.getSeleniumUtils().clickElem(this.btnOKDelete, "Confirm Delete Button");
	}

	public void verifyComttext(String exptext, ExtentTest tclogger) throws IOException {
		String acttext = Global.getSeleniumUtils().getText(this.elemcomment, "Added Commnet");

		if (exptext.equalsIgnoreCase(acttext)) {
			Context.global();
			Global.ext.creatextentReport(tclogger, "step_01;Incident Number;" + exptext + ";" + acttext + ";Pass");
		} else {
			Context.global();
			Global.ext.creatextentReport(tclogger, "step_01;Incident Number;" + exptext + ";" + acttext + ";Fail");
		}

	}

}
