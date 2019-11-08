package com.intuit.bpui_qa;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.PaymentHistoryPage;

@Test(groups="CancelRecurringPayment")
public class CancelRecurringPaymentTests extends BPUIBasicTest{

	PaymentHistoryPage historyPage;

	@BeforeClass
	public void startUp(){
		System.out.println("Testing........");
		Log("Starting Search Payee tests.");
		verifyStartupForTheseTests();
	}
	
	private void verifyStartupForTheseTests(){
		if(bpui.getRecurringPaymentsCount() == 0)
			throw new RuntimeException("Setup failed for: " + this.getClass().getName());
	}

	@Test(dataProvider="expectedHeaderForDialog", priority=1, description="Verifying Cancelling single recurring payment dialog header.")
	public void verifyHeaderOfCancelSingleRecurPayment(String expectedHeaderText){
		Log("Verifying Cancelling single recurring payment dialog header.");
		String paymentHeader = bpui.getCancelRecurringPaymentHeader();
		assert paymentHeader != null && paymentHeader.equalsIgnoreCase(expectedHeaderText);
	}
	
	@Test(priority = 2, dependsOnMethods={"verifyHeaderOfCancelSingleRecurPayment"})
	public void cancelRecurringPaymentAndVerify(){
		Log("Cancelling a recurring payment and verifying in receipt area.");
		
		bpui.cancelRandomRecurringPayment();
//		System.out.println("Is is cancelled: " + bpui.verifyCancelledPaymentIsDeletedFromReceipt());
		assert bpui.verifyCancelledPaymentIsDeletedFromReceipt() == false;
	}

	@Test(priority = 3, dependsOnMethods={"cancelRecurringPaymentAndVerify"})
	public void verifyHistoryPage() throws InterruptedException{
		Log("Verifying history page after cancelling payment.");
		historyPage = Site.gotoPaymentHistoryPage();
		Thread.sleep(5000);
		historyPage.verifyIfPaymentIsCanceled(bpui.getPaymentId());
		Thread.sleep(5000);
//		System.out.println("Is is cancelled: " + bpui.verifyCancelledPaymentIsDeletedFromReceipt());
//		assert bpui.verifyCancelledPaymentIsDeletedFromReceipt() == false;
	}

	@DataProvider(name="expectedHeaderForDialog")
	public Object[][] getExpectedHeaderText(){
		return new Object[][] {
			new Object[] {"Are you sure you want to cancel this payment?"}
		};
	}
}
