package com.intuit.bpui_qa;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;

public class PayeeDetailsTests extends BPUIBasicTest{

//	BPUIPage bpui;
	String payeeName = null;

	@BeforeClass
	public void startUp(){
		Log("Starting Payee Details tests.");
//		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
//		
//		PostLogin ptLogin = login.login();
		
//		bpui = ptLogin.navigateToBillPayUI();
		
		
	}
	
	
	@Test(description="Verify by default for a payee, only the upper panle is displayed and the drawer is hidden")
	public void testDefaultDrawerStatus(){
		Log("Verify by default for a payee, only the upper panle is displayed and the drawer is hidden");
		assert !bpui.getDrawerStatus(payeeName);
		payeeName = bpui.getFirstPayeeName();
	}
	
	@Test(description="Verify for a payee, whether the Payee Name is displayed or not.")
	public void testVerifyAccountNameIsDisplayed(){
		Log("Verify for a payee, whether the Payee Name is displayed or not.");
		assert bpui.checkPayeeNameExists(bpui.getFirstPayeeName());
	}
	
	@Test(description="Verify for a payee, whether the nick name is displayed.")
	public void testVerifyNickNameIsDisplayed(){
		Log("Verify for a payee, whether the nick name is displayed. This is optional.");
		assert bpui.checkNickNameExists(bpui.getFirstPayeeName());
	}
	
	@Test(description="Verify for a payee, whether the amount field is displayed.")
	public void testVerifyAmountField(){
		Log("Verify for a payee, whether the amount field is displayed.");
		assert bpui.checkAmountField(bpui.getFirstPayeeName());
	}
	
	@Test(description="Verify the send on field for a payee")
	public void testVerifySendOnField(){
		Log("Verify the send on field for a payee");
		assert bpui.checkSendOn(bpui.getFirstPayeeName());
	}
	
	@Test(description="Verify whether the 'Pay' button is displayed or not.")
	public void testVerifyPayButtonIsShown(){
		Log("Verify whether the 'Pay' button is displayed or not.");
		assert bpui.checkPayButton(bpui.getFirstPayeeName());
	}
	
	@Test(description="Verify whether memo link is shown as expected.")
	public void testVerifyMemoLinkUI(){
		Log("Verify whether memo link is shown as expected.");
		assert bpui.checkMemoLinkUI(bpui.getFirstPayeeName());
	}
	
	@Test(description="Verify toggling of the options tray.")
	public void testVerifyTogglingDrawer() throws InterruptedException{
		Log("Verify toggling of the options tray.");
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
		assert bpui.getDrawerStatus(bpui.getFirstPayeeName());
		Thread.sleep(1000);
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
		assert !bpui.getDrawerStatus(bpui.getFirstPayeeName());
	}
	
	@Test(dependsOnMethods="testVerifyTogglingDrawer", description="Verify account details in Account Information.")
	public void testVerifyAccountDetailsAccountInfo() throws InterruptedException{
		Log("Verify account details in Account Information.");
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
		Thread.sleep(1000);
		bpui.goToAccountInfoTab(bpui.getFirstPayeeName());
		Thread.sleep(1000);
		bpui.verifyAccountNumberFields(bpui.getFirstPayeeName());
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
	}

	@Test(dependsOnMethods="testVerifyTogglingDrawer", description="Verify contact details in Account Information.")
	public void testVerifyAccountDetailsContactInfo() throws InterruptedException{
		Log("Verify contact details in Account Information.");
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
		Thread.sleep(1000);
		bpui.goToAccountInfoTab(bpui.getFirstPayeeName());
		Thread.sleep(1000);
		bpui.verifyContactFields(bpui.getFirstPayeeName());
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
	}

	@Test(dependsOnMethods="testVerifyTogglingDrawer", description="Verify footer in Account Information.")
	public void testVerifyAccountDetailsFooterInfo() throws InterruptedException{
		Log("Verify footer in Account Information.");
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
		Thread.sleep(500);
		bpui.goToAccountInfoTab(bpui.getFirstPayeeName());
		Thread.sleep(1000);
		bpui.verifyAccountInfoFooter(bpui.getFirstPayeeName());
		bpui.togglePayeeDrawer(bpui.getFirstPayeeName());
	}

}
