package com.nordea.workflows;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.nordea.framework.Local;
import com.nordea.pages.HomePage;
import com.nordea.pages.IncidentPage;
import com.nordea.pages.LoginPage;
import com.relevantcodes.extentreports.ExtentTest;

public class IncidentCreation {
	public void OpenBrowser() {

		// Global.getdriverutils().InitializeDriver();
	}

	public void performLogin(String username, String password)
			throws InstantiationException, IllegalAccessException, InterruptedException {

		/*
		 * LoginPage lpg = new LoginPage(dr); lpg.swithchtoNavFrame();
		 * lpg.enterusername(username); lpg.enterpassword(password); lpg.clickLogin();
		 */

		Local.pages().getpage(LoginPage.class).swithchtoNavFrame();
		Local.pages().getpage(LoginPage.class).watiforUname();
		Local.pages().getpage(LoginPage.class).enterusername(username);
		Local.pages().getpage(LoginPage.class).enterpassword(password);
		Local.pages().getpage(LoginPage.class).clickLogin();
		/*
		 * context.local().pages().getpage(LoginPage.class).swithchtoNavFrame();
		 * context.local().pages().getpage(LoginPage.class).enterusername( username);
		 * context.local().pages().getpage(LoginPage.class).enterpassword( password);
		 * context.local().pages().getpage(LoginPage.class).clickLogin();
		 */

	}

	public void clickCreateNewIncident(ExtentTest tclogger)
			throws InterruptedException, InstantiationException, IllegalAccessException, IOException {

		Local.pages().getpage(HomePage.class).swithchtoDefault();
		Local.pages().getpage(HomePage.class).swithchtoMainrame();
		Local.pages().getpage(HomePage.class).waitfor10thingslink();
		Local.pages().getpage(HomePage.class).swithchtoDefault();
		Local.pages().getpage(HomePage.class).swithchtoNavFrame();
		Local.pages().getpage(HomePage.class).clickIncidentLink();
		Local.pages().getpage(HomePage.class).swithchtoDefault();
		Local.pages().getpage(HomePage.class).swithchtoMainrame();
		Local.pages().getpage(HomePage.class).clickCreateNew();
		String dyincnum = "INC" + new SimpleDateFormat("dd_MMM_YYYY_hh_mm_ss").format(new Date());
		Local.pages().getpage(IncidentPage.class).EnterIncidentNumber(dyincnum);

		Local.pages().getpage(IncidentPage.class).SelectImpact("2 - Medium");
		Local.pages().getpage(IncidentPage.class).SelectState("New");
		Local.pages().getpage(IncidentPage.class).EnterIncDesc("INC Created by Sel");
		Local.pages().getpage(IncidentPage.class).EnterComments("New Incinde thas been creaed");
		Local.pages().getpage(IncidentPage.class).clickSubmit();
		// Thread.sleep(3000);
		Local.pages().getpage(IncidentPage.class).Clickrdynalink(dyincnum);
		Thread.sleep(3000);
		Local.pages().getpage(IncidentPage.class).verifynumber(dyincnum, tclogger);
		Local.pages().getpage(IncidentPage.class).ScrolltoConfirmComment();
		Local.pages().getpage(IncidentPage.class).verifyComttext("Any text", tclogger);
		Local.pages().getpage(IncidentPage.class).ClickDeleteButton();
		Local.pages().getpage(IncidentPage.class).ClickConfirmDeleteButton();

	}

	public void performLogout() throws InstantiationException, IllegalAccessException, InterruptedException {
		Local.pages().getpage(HomePage.class).swithchtoDefault();
		Local.pages().getpage(HomePage.class).clickLogoutLink();
	}

}
