package com.intuit.bpui_qa;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;

public class AddUnknownPayeeTests extends BPUIBasicTest{

	String payeeNameForNegativeTest;

	@BeforeClass
	public void startUp(){
		Log("Starting Add Unknown Payee Tests.");
	}
	
	@Test
	public void testAddPayeeMandatoryErrors() throws InterruptedException{
		Thread.sleep(5000);
		Log("[Test Case]: Mandatory fields should throw errors while adding a payee.");
		String expectedError = "This field is required";
		Log("Step: Enter a payee name.");
		bpui.searchTopPayee("QA Test");
		Log("Step: Click Add button.");
		bpui.clickSearchPayeeOnTop();
//		bpui.clickSearchPayee();
		Thread.sleep(2000);
		Log("Step: Click 'Add Payee' button.");
		bpui.clickAddPayee();
		assert expectedError.equals(bpui.getAddress1Error());
		assert expectedError.equals(bpui.getCityError());
		assert expectedError.equals(bpui.getStateError());
		assert expectedError.equals(bpui.getZipError());
		Log("Step: Done verifying the errors. Clicking 'Cancel Payee' button.");
		bpui.clickCancelAddPayee();
	}

	@Test
	public void testAddPayeeAccountNumberError() throws InterruptedException{
		Log("[Test Case]: Account number and Confirm account number should be same, if not there should be error message.");
		String expectedError = "The two account numbers are different.";
		Log("Step: Enter a payee name.");
		bpui.searchTopPayee("QA Test");
		Log("Step: Click Add button.");
		bpui.clickSearchPayeeOnTop();
//		bpui.clickSearchPayee();
		Thread.sleep(2000);
		Log("Step: Enter account number.");
		bpui.enterAccountNumber1("1234");
		Log("Step: Enter a different confirm account number.");
		bpui.enterAccountNumber2("12345");
		Log("Step: Click 'Add Payee' this should result an error.");
		bpui.clickAddPayee();
		
		assert expectedError.equals(bpui.getConfirmAccountError());
		Log("Step: Done verifying the errors, clicking 'Cancel'.");
		
		bpui.clickCancelAddPayee();
	}
	
	@Test(dataProvider="unknownPayeeTop")
	public void testAddPayeeSuccess(String payeeName, String accountNumber, String confirmActNumber, String address1, String city, String state, String zip) throws InterruptedException{
		Log("[Test Case]: Should be able to add unknown payee.");

		String expectedStatus = "Successfully added You are ready to make payments";
		payeeNameForNegativeTest = payeeName;
		Log("Step: Entering the payee details");
		Log("Data: PayeeName: " + payeeName);
		Log("Data: Account Number: " + accountNumber);
		Log("Data: Confirm Account Number: " + confirmActNumber);
		Log("Data: Address: " + address1);
		Log("Data: City: " + city);
		Log("Data: State: " + state);
		Log("Data: Zip: " + zip);
		bpui.enterUnkownPayeeDetails(payeeName, accountNumber, confirmActNumber, address1, city, state, zip);
		Log("Step: Clicking 'Add Payee'.");
		bpui.clickAddPayee();
		Thread.sleep(10000);
		Log("Step: Check whether the first payee is the newly added payee.");
		assert payeeName.equals(bpui.getFirstPayeeName());
		Log("Step: Verify the status message after adding the payee.");
		assert expectedStatus.equals(bpui.getStatusMessage(payeeName));
	}
	
	@Test(dependsOnMethods="testAddPayeeSuccess")
	public void testAddPayeeSorting(){
		Log("[Test Case]: Resorting should work after adding a new payee.");
		Log("Step: Click on the resort icon.");
		bpui.resortAfterAddingPayee();
		Log("Step: Verify the sort order after the resort.");
		assert bpui.verifyIfPayeesAreSortedByDefault();
	}
	
//	@Test(dependsOnMethods="testAddPayeeSuccess", dataProvider="unknownPayeeTop")
//	public void testAddExistingPayee(String payeeName, String accountNumber, String confirmActNumber, String address1, String city, String state, String zip) throws InterruptedException{
//		Log("Running testAddExistingPayee test...");
//		String expectedStatus = "A payee with this same name, account number, and address already exists. Please enter a different name, account number, or address.";
//		
//		bpui.enterUnkownPayeeDetails(payeeNameForNegativeTest, accountNumber, confirmActNumber, address1, city, state, zip);
//		
//		bpui.clickAddPayee();
//		Thread.sleep(10000);
//		assert expectedStatus.equals(bpui.getAddPayeeFooterError());
//		
//		bpui.clickCancelAddPayee();
//		Log("Finishing testAddExistingPayee test...");
//	}

	@Test
	public void testCheckForMinimumLengthValidationTop(){
		String expectedError = "You must enter a name that is at least two characters.";
		Log("[Test Case]: Error should be shown for payee names less than 2 characters. (TOP)");
		Log("Step: Enter a single character.");

		bpui.searchForSingleCharacter("A");
		Log("Step: Click 'Add'.");
		
		bpui.clickSearchPayeeOnTop();

		Log("Step: Verify the message.");
		assert expectedError.equals(bpui.getSearchPayeeErrorMessage());
	}

	@Test
	public void testCheckForMinimumLengthValidationBottom(){
		String expectedError = "You must enter a name that is at least two characters.";
		Log("[Test Case]: Error should be shown for payee names less than 2 characters. (BOTTOM)");
		Log("Step: Enter a single character.");
		bpui.searchForSingleCharacterBottom("B");
		Log("Step: Click 'Add'.");

		bpui.clickSearchPayeeBottom();
		Log("Step: Verify the message.");
		assert expectedError.equals(bpui.getSearchPayeeErrorMessage());
	}

	@DataProvider(name="unknownPayeeTop")
	public Object[][] getKnownMerchantsListForBottom(){
		return new Object[][] {
			//new Object[] {"Payee " + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()), "1234", "1234", "3440, El Camino Real", "Santa Clara", "California", "95051"},
				new Object[] {"Payee ", "1234", "1234", "3440, El Camino Real", "Santa Clara", "California", "95051"},
		};
	}
}
