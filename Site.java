package com.intuit.bpui_qa;



import org.openqa.selenium.WebDriver;

import org.openqa.selenium.support.PageFactory;



import com.intuit.bpui_qa.Pages.BasePostLoginPage;
import com.intuit.bpui_qa.Pages.EbillHistoryPage;
import com.intuit.bpui_qa.Pages.RemindersAlertsPage;

import com.intuit.bpui_qa.Pages.LoginPage;

import com.intuit.bpui_qa.Pages.BPUIPage;

import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;

import com.intuit.bpui_qa.Pages.PaymentHistoryPage;

import com.intuit.bpui_qa.Pages.PostLogin;

import com.intuit.bpui_qa.Pages.PostLoginDev;

import com.intuit.bpui_qa.Pages.PostLoginQA;

import com.intuit.bpui_qa.Pages.PostLoginQA1;

import com.intuit.bpui_qa.utils.ReadFlags;



public class Site {

	private static LoginPage loginPage;

	private static BPUIPage bpuiPage;

	private static ManageFundingAccountsPage mngFundingAccounts;

	private static PostLogin postLoginPage;

	private static BasePostLoginPage actualPage;

//	private static BasePostLoginPage postLoginPageTwo;

	

	private static PaymentHistoryPage paymentHistoryPage;
	
	private static EbillHistoryPage eBillHistoryPage;
	
	private static RemindersAlertsPage notifications;

	private static ReadFlags flag = new ReadFlags();

	private static WebDriver _driver;

	

	public static void setDriver(WebDriver driver){

		_driver = driver;

	}

	

	public static WebDriver getDriver(){

		return _driver;

	}

	

	public static PostLogin Login(){

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% POST LOGIN STEP1 WITH LOGIN RETURN THE PAGE OBJECT %%%%%%%%%%%%%%%%%%%%%");

		loginPage = PageFactory.initElements(getDriver(), LoginPage.class);

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% POST LOGIN STEP2 WITH LOGIN RETURN THE PAGE OBJECT %%%%%%%%%%%%%%%%%%%%%");

		loginPage.login();

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% POST LOGIN STEP3 WITH LOGIN RETURN THE PAGE OBJECT %%%%%%%%%%%%%%%%%%%%%");

		// OK Take me to the parent post login page.

		postLoginPage = PageFactory.initElements(getDriver(), PostLogin.class);



		// Now, give me the actual page object.

//		actualPage = postLoginPage.getActualABPage();

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% DONE WITH LOGIN RETURN THE PAGE OBJECT %%%%%%%%%%%%%%%%%%%%%");

		return postLoginPage;

	}

	

	public static BPUIPage navigateToBillPayUI(){

		postLoginPage.navigateToBillPayUI();

		bpuiPage = PageFactory.initElements(getDriver(), BPUIPage.class);

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% DONE WITH NAVIGATING TO BPUI RETURN THE PAGE OBJECT %%%%%%%%%%%%%%%%%%%%%");

		return bpuiPage;

	}

	

	public static ManageFundingAccountsPage gotoManageFundingAccountsPage(){

		bpuiPage.clickManageFundingAccountsLink();

		

		mngFundingAccounts =  PageFactory.initElements(getDriver(), ManageFundingAccountsPage.class);

		

		if(mngFundingAccounts.isPageLoaded() == false) throw new RuntimeException("Page navigation failed for: " + mngFundingAccounts.getCurrentPageName());

		return mngFundingAccounts;

	}

	

	public ManageFundingAccountsPage fundingAccountsPage(){

		if(mngFundingAccounts == null)

			gotoManageFundingAccountsPage();

		return mngFundingAccounts;

	}

	

	public static PaymentHistoryPage gotoPaymentHistoryPage(){

		bpuiPage.clickViewAllPayments();

		paymentHistoryPage = PageFactory.initElements(getDriver(), PaymentHistoryPage.class);

		

		return paymentHistoryPage;

	}

	

	public static BPUIPage goBackToBpUiPage(){

		mngFundingAccounts.goBackToBpUiPage();

		bpuiPage = PageFactory.initElements(getDriver(), BPUIPage.class);

		return bpuiPage;

	}
	
	public static EbillHistoryPage gotoEbillHistoryPage(){

		paymentHistoryPage.clickMyBill();

		eBillHistoryPage = PageFactory.initElements(getDriver(), EbillHistoryPage.class);

		

		return eBillHistoryPage;

	}
	
	
	
	public static RemindersAlertsPage goToReminderAlertNotificationPage() {
		
	     bpuiPage.clickEditReminderAndAlertPrefrencesLink();

		

		 notifications =  PageFactory.initElements(getDriver(), RemindersAlertsPage.class);

		

		if(notifications.isPageLoaded() == false) throw new RuntimeException("Page navigation failed for: " + notifications.getCurrentPageName());

		return notifications;
		
	}
	
	
	public static BPUIPage goBackToBpuiPageFromNotificationPage(){

		notifications.goBackToBpUiPage();

		bpuiPage = PageFactory.initElements(getDriver(), BPUIPage.class);

		return bpuiPage;

	}

}

