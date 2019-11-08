package com.intuit.bpui_qa;

import java.text.ParseException;
import java.util.Date;
import org.openqa.selenium.ElementNotVisibleException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddManagedPayeeTests extends  BPUIBasicTest {
	@BeforeClass
	public void startUp(){
		Log("Starting Add Managed Payee Tests");
	}
	
	@Test
	public void checkHelpTextforAddPayee(){
		Log("Verifying the help text presence while adding a payee");
		assertEquals(bpui.getTopAddPayeeHelptext(), bpui.getBottomAddPayeeHelptext());
		assertEquals(bpui.getTopAddPayeeHelptext(), "Enter person or business" );
	}
	
	@Test
	public void AddMerchantSingleAlphabet(){
		Log("Verifying error message when a payee with a single alphabet is tried to be added");
		bpui.searchTopPayee("T");
		bpui.clickSearchPayeeOnTop();
		assert(bpui.IsErrorIconTopPanelPresent());
		assertEquals(bpui.getErrorTopPanel(), "You must enter a name that is at least two characters.");
	}
	
	@Test
	public void TopPanelLabelText(){
		Log("Verifying the label text at the top of the panel");
		assertEquals(bpui.getTopPanelLabel(), "Need to pay someone new?");
	}
	
	@Test
	public void CheckTwoFactorPayeeAdd() throws InterruptedException{
		bpui.refreshBillPayment();
		Log("Checking a two factor payee window");
		bpui.searchTopPayee("T-Mobile");
		bpui.clickSearchPayeeOnTop();	
		Thread.sleep(5000);
		assert(bpui.TwoFactorPayeeValidation());
		bpui.clickCancelinAddPaymentWindow();
	}
	
	@Test
	public void CheckHelpText() throws InterruptedException{
		Log("Checking 'Other questions' help text");
		bpui.searchTopPayee("T-Mobile");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		assertEquals(bpui.getTwoFactorPayeeHelpText(), "Other questions");
		bpui.clickCancelinAddPaymentWindow();
	}
	
	@Test
	public void VerifyBillingAddressTwoFactorPayee() throws InterruptedException{
		bpui.refreshBillPayment();
		Log("Verifying the billing address for a two factor payee");
		bpui.searchTopPayee("T-Mobile");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		assertEquals(bpui.getBillingAddressTwoFactorPayee(), "We already have this address on file, no need to enter anything.");
		bpui.clickCancelinAddPaymentWindow();
	}
	
	@Test
	public void CheckAddPayeeButtonStatusTwofactor() throws InterruptedException{
		Log("Checking for the add payee button status");
		bpui.searchTopPayee("T-Mobile");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		bpui.enterTwoFactorPayeeDetails("T-Mobile", "1234", "12");
		Thread.sleep(5000);
		assert(!bpui.isAddPayeeButtonEnabled());
		bpui.clickCancelinAddPaymentWindow();
	}
	
	@Test
	public void TwoFactorAccountNumberValidation() throws InterruptedException{
		Log("Checking the error message when the two account numbers entered are different for a two factor payee");
		bpui.searchTopPayee("T-Mobile");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		bpui.enterTwoFactorPayeeDetails("T-Mobile", "1234", "12");
		bpui.clickAddPayee();
		Thread.sleep(5000);
		assertEquals(bpui.getknownMerchantAccountErrorMessage(), "The two account numbers are different.");	
	}	
	
	@Test
	public void CheckThreeFactorPayeeAdd() throws InterruptedException{
		Log("Checking for a proper window while adding a three factor payee");
		bpui.refreshBillPayment();
		bpui.searchTopPayee("Juniper Bank");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		assertEquals(bpui.ThreeFactorPayeeZipText(),"ZIP code:");
		bpui.clickCancelinAddPaymentWindow();
	}
	
	@Test
	public void VerifyBillingAddressThreeFactorPayee() throws InterruptedException{
		Log("Verifying the billing address for a three factor payee");
		bpui.refreshBillPayment();
		bpui.searchTopPayee("Juniper Bank");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		assertEquals(bpui.getBillingAddressTwoFactorPayee(), "There are multiple locations. Please enter the ZIP code at right, so we know where to send to.");
		bpui.clickCancelinAddPaymentWindow();
	}
	
	@Test
	public void CheckAddPayeeButtonStatusThreefactor() throws InterruptedException{	
		Log("Checking for the add payee button status for a three factor payee");
		bpui.searchTopPayee("Juniper Bank");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		bpui.enterThreeFactorPayeeDetails("Juniper Bank", "520090234567897", "5200902345678978","191013337", "" );
		Thread.sleep(5000);
		assert(!bpui.isAddPayeeButtonEnabled());
		bpui.clickCancelinAddPaymentWindow();
	}
	
	// Not showing dropdown
/*	@Test
	public void CheckDropdownSorting() throws InterruptedException{
		Log("Verify if sorting exists when we try to add some payee");
		assert(bpui.verifyIfPayeesDropdownSorted("as"));
		bpui.clickCancelinAddPaymentWindow();
	}*/
	
	@Test
	public void CheckTwoFactorPayeeAddition() throws InterruptedException{
		try{bpui.deletePayee("T-Mobile", "");}
		catch(Exception e){}
		Log("Verify if a two factor payee can be successfully added");
		bpui.searchTopPayee("T-Mobile");
		bpui.clickSearchPayeeOnTop();
		Thread.sleep(5000);
		bpui.enterTwoFactorPayeeDetails("T-Mobile", "1234567899", "1234567899");
		bpui.clickAddPayee();
		Thread.sleep(20000);	
		assertEquals(bpui.getFirstPayeeName(),"T-Mobile");
		bpui.refreshBillPayment();
		bpui.deletePayee("T-Mobile", "");
		Thread.sleep(5000);
	}
}
