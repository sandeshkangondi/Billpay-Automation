package com.intuit.bpui_qa;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.PaymentHistoryPage;

public class DeletePayeeTests extends BPUIBasicTest {

	String createdPayeeName="Delete Payee";
	PaymentHistoryPage historyPg;
	
	@BeforeClass
	public void startUp(){
		Log("Starting delete payee tests.");
	}
	
//	@Test(dataProvider="unknownPayeeTop")
//	public void testAddUnknownPayee(String payeeName, String accountNumber, String confirmActNumber, String address1, String city, String state, String zip) throws InterruptedException{
//		Log("[Test Case]: Creating a payee.");
//
//		String expectedStatus = "Successfully added You are ready to make payments";
//		createdPayeeName = payeeName;
//		Log("Step: Entering the payee details");
//		Log("Data: PayeeName: " + payeeName);
//		Log("Data: Account Number: " + accountNumber);
//		Log("Data: Confirm Account Number: " + confirmActNumber);
//		Log("Data: Address: " + address1);
//		Log("Data: City: " + city);
//		Log("Data: State: " + state);
//		Log("Data: Zip: " + zip);
//		bpui.enterUnkownPayeeDetails(payeeName, accountNumber, confirmActNumber, address1, city, state, zip);
//		Log("Step: Clicking 'Add Payee'.");
//		bpui.clickAddPayee();
//		Thread.sleep(10000);
//		Log("Step: Check whether the first payee is the newly added payee.");
//		assert payeeName.equals(bpui.getFirstPayeeName());
//		Log("Step: Verify the status message after adding the payee.");
//		assert expectedStatus.equals(bpui.getStatusMessage(payeeName));
//		
//	}

	@Test
	public void testVerifyDeleteHeader(){
		createdPayeeName = bpui.getFirstPayeeName();
		Log("[Test Case]: Verify the header of the delete payee functionality, in options tray.");
		
		Log("Step: Open the 'Options' tray for the payee: " + createdPayeeName);
		bpui.openPayeeOptionsTray(createdPayeeName);
		
		Log("Step: Verifying the header of the delete payee, in options tray");
		assert bpui.verifyHeaderOfDeleteInOptionsTray(createdPayeeName);
		
	}

	@Test
	public void testVerifyDeleteLinks(){
		Log("[Test Case]: Verify the UI of the delete payee functionality.");
		
		Log("Step: Open the 'Options' tray for the payee: " + createdPayeeName);
		bpui.openPayeeOptionsTray(createdPayeeName);
		
		Log("Step: Verifying the UI of the delete payee, in options tray");
		assert bpui.verifyUIOfDeleteInOptionsTray(createdPayeeName);
		
	}

	@Test
	public void testVerifyDeleteMainHeader(){
		Log("[Test Case]: Verify the UI of the delete dialog box.");
		
		Log("Step: Open the 'Options' tray for the payee: " + createdPayeeName);
		bpui.openPayeeOptionsTray(createdPayeeName);
		
		bpui.clickDeleteItLink(createdPayeeName);
		Log("Step: Verifying the UI of the delete dialog box, in options tray");
		assert bpui.verifyDeletePayeeDialogHeader(createdPayeeName);
		
	}
	
	@Test
	public void testVerifyDeleteMainScenario() throws InterruptedException{
		Log("[Test Case]: Check the delete functionality.");
		
		Log("Step: Open the 'Options' tray for the payee: " + createdPayeeName);
//		bpui.openPayeeOptionsTray(createdPayeeName);
//		historyPg = bpui.goToPaymentsHistoryPage();
//		bpui.clickDeleteItLink(createdPayeeName);
		Log("Step: Click 'Yes Delete' button on the dialog window.");
		
		bpui.clickYesDeleteButton();
		Thread.sleep(5000);
		
		assert !bpui.checkIfPayeeNameIsInScheduledPaymentList(createdPayeeName);
	}

	@Test
	public void testVerifyHistoryPageIsUpdated(){
		historyPg = Site.gotoPaymentHistoryPage();
		Site.navigateToBillPayUI();
		historyPg = bpui.goToPaymentsHistoryPage();
		assert historyPg.verifyPayeeIsCanceled(createdPayeeName);
	}

	@DataProvider(name="unknownPayeeTop")
	public Object[][] getUnknownPayeeDetails(){
		return new Object[][] {
			new Object[] {"Payee " + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "1234", "1234", "3440, El Camino Real", "Santa Clara", "California", "95051"},
		};
	}


}
