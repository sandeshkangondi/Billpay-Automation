package com.intuit.bpui_qa;

import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;

public class AddFundingAccounts extends BPUIBasicTest{
	
	ManageFundingAccountsPage mngFundingAccounts;
	
	@BeforeClass
	public void startUp() throws InterruptedException{
		Log("Starting Add Funding Accounts tests.");
		mngFundingAccounts= Site.gotoManageFundingAccountsPage();
		Thread.sleep(5000);
	}
	
/*	@Test(description="Check if there is a button to Add a funding account in the funding accounts page",  groups={"sanity", "comprehensive"})
	public void checkforAddFundingAccountLink(){
		Log("Checking for a AddFunding account link");
		assert(mngFundingAccounts.isAddFundingAccountButtonPresent());
	}
	
	@Test(description="Checkng for all the field names in the dialog window", dataProvider="accountfields", dependsOnMethods = "checkforAddFundingAccountLink", groups="sanity")
	public void testDialogWindowFieldnames(ArrayList<String> accountfields) throws InterruptedException{
		Log("Checking for dialog window fields");
		assertEquals(mngFundingAccounts.getFundingAccountWindowFields(), accountfields);
	}
	
	@Test(description="Checking for all the help instructions on the dialog", groups="sanity"\)
	public void checkforInstructions() throws InterruptedException{
		Log("Checking for the help text on the dialog window");
		String instructions = "Provide your account information below. In the next 2-3 business days, we will make 2 small deposits to the account you are adding. Check the account and note the amounts of the deposits. Come back to the Bill Pay Accounts page and use the \"Validate\" link next to the corresponding account. Enter the amounts of the deposits. If they match our expected values, your account will be approved and ready for use!";
		assert(mngFundingAccounts.getFundingAccountWindowInstructions().contains("Provide your account information below."));
		assert(mngFundingAccounts.getFundingAccountWindowInstructions().contains(instructions));
	}
	
	@Test(description="Checking for general error scenarios", groups="acceptance")
	public void checkforDialogErrorScenarios() throws InterruptedException{
		Log("Checking for general error scenarios");
		String mandatoryfieldError = "This field is required";
		String addAccountError = "Please correct all errors shown above.";
		mngFundingAccounts.clearFundingAccountWindowCheckNumber();
		mngFundingAccounts.clickFundingAccountAddaccountButton();
		assertEquals(mngFundingAccounts.getFundingAccountAccountWindowNickNameError(), mandatoryfieldError);
		assertEquals(mngFundingAccounts.getFundingAccountWindowAccountNumberFieldError(), mandatoryfieldError);
		assertEquals(mngFundingAccounts.getFundingAccountWindowcheckNumberFieldError(), mandatoryfieldError);
		assertEquals(mngFundingAccounts.getFundingAccountWindowfinancialInstitutionFieldError(), mandatoryfieldError);
		assertEquals(mngFundingAccounts.getFundingAccountWindowRoutingNumberFieldError(), mandatoryfieldError);
		assertEquals(mngFundingAccounts.getFundingAccountWindowaccountStatusError(), addAccountError);
	}
	
	@Test(description="Errors on account and routing numbers mismatch", groups={"acceptance", "comprehensive"})
	public void checkforNumberMismatchErrors() throws InterruptedException{
		Log("Checking for errors when account and routing numbers mismatch");
		String numberMismatchError = "Numbers must match";
		mngFundingAccounts.enterFundingAccountWindowConfirmAccountNumber("12");
		mngFundingAccounts.enterFundingAccountWindowConfirmRoutingNumber("12");
		mngFundingAccounts.clickFundingAccountAddaccountButton();
		assertEquals(mngFundingAccounts.getFundingAccountWindowconfirmAccountNumberFieldError(), numberMismatchError);
		assertEquals(mngFundingAccounts.getFundingAccountWindowconfirmRoutingNumberFieldError(), numberMismatchError);
	}
	
	@Test(description="Checking for field lengths in dialog box", groups="acceptance")
	public void validatingDialogWindowFieldLengths() throws InterruptedException{
		Log("Checking field lengths in dialog box");
		assertEquals(mngFundingAccounts.getFundingAccountWindowFinancialInstitutionmaxLength(), "64");
		assertEquals(mngFundingAccounts.getFundingAccountWindowAccountnickNamemaxLength(), "32");
		assertEquals(mngFundingAccounts.getFundingAccountWindowAccountNumbermaxLength(), "32");
		assertEquals(mngFundingAccounts.getFundingAccountWindowConfirmAccountNumbermaxLength(), "32");
		assertEquals(mngFundingAccounts.getFundingAccountWindowRoutingNumbermaxLength(), "9");
		assertEquals(mngFundingAccounts.getFundingAccountWindowConfirmRoutingNumbermaxLength(), "9");
		assertEquals(mngFundingAccounts.getFundingAccountWindowChecknumbermaxLength(), "5");
	}
	
	@Test(description="Checking if the error messages disappear in the dialog box on corrections made", groups="sanity")
	public void validateFieldMismatchErrorMessagesClearedonCorrection() throws InterruptedException{
		Log("");
		mngFundingAccounts.clearFundingAccountWindowCheckNumber();
		mngFundingAccounts.enterFundingAccountWindowConfirmAccountNumber("12");
		mngFundingAccounts.enterFundingAccountWindowConfirmRoutingNumber("12");
		mngFundingAccounts.clickFundingAccountAddaccountButton();
		mngFundingAccounts.enterFundingAccountWindowInstitutionName("Bank of California");
		mngFundingAccounts.enterFundingAccountWindowAccountNickName("BOC");
		mngFundingAccounts.enterFundingAccountWindowAccountNumber("12");
		mngFundingAccounts.enterFundingAccountWindowRoutingNumber("12");
		mngFundingAccounts.enterFundingAccountWindowCheckNumber("9010");
		mngFundingAccounts.clickOutofFocus();
		assertEquals(mngFundingAccounts.getFundingAccountAccountWindowNickNameError(), "");
		assertEquals(mngFundingAccounts.getFundingAccountWindowAccountNumberFieldError(), "");
		assertEquals(mngFundingAccounts.getFundingAccountWindowcheckNumberFieldError(), "");
		assertEquals(mngFundingAccounts.getFundingAccountWindowfinancialInstitutionFieldError(), "");
		assertEquals(mngFundingAccounts.getFundingAccountWindowRoutingNumberFieldError(), "");
		assertEquals(mngFundingAccounts.getFundingAccountWindowconfirmAccountNumberFieldError(), "");
		assertEquals(mngFundingAccounts.getFundingAccountWindowconfirmRoutingNumberFieldError(), "");
	}
	
	@Test(description="Errors when routing numbers are invalid", groups="acceptance", expectedExceptions=ArithmeticException.class)
	public void checkInvalidRoutingNumberErrors() throws InterruptedException{
		Log("Checking for invalid routing number errors");
		mngFundingAccounts.addFundingAccount("Bank of California", "BOC", "12", "12", "073902887", "073902887", "9033");
		assert(mngFundingAccounts.getFundingAccountWindowaccountStatusError().contains("Not valid combo."));
		mngFundingAccounts.clickFundingAccountWindowCancel();
	}
	
	// Does'nt work since we need a working routing number
	@Test(description="Check if a valid funding account can be added", groups={"acceptance","comprehensive"})
	public void testtoAddValidFundingAccounts() throws InterruptedException{
	Log("Checking if a valid funding account can be added");
		mngFundingAccounts.addFundingAccount("Bank of California", "BOC", "12", "12", "073902274", "073902274", "9010");
		assert(!mngFundingAccounts.isFundingAccountWindowOpen());
		// + Method to check if a certain account is present on the funding account page.
	}
	
	@Test(description="Checking for the cancel functionality", groups={"acceptance", "regression"})
	public void testCancelinDialogWindow() throws InterruptedException{
		Log("checking cancel functionality");
		mngFundingAccounts.clearFundingAccountWindowCheckNumber();
		mngFundingAccounts.enterFundingAccountWindowConfirmAccountNumber("12");
		mngFundingAccounts.enterFundingAccountWindowConfirmRoutingNumber("12");
		mngFundingAccounts.enterFundingAccountWindowInstitutionName("Bank of California");
		mngFundingAccounts.enterFundingAccountWindowAccountNickName("BOC");
		mngFundingAccounts.enterFundingAccountWindowAccountNumber("12");
		mngFundingAccounts.enterFundingAccountWindowRoutingNumber("12");
		mngFundingAccounts.enterFundingAccountWindowCheckNumber("9010");
		mngFundingAccounts.clickFundingAccountWindowCancel();
		assert(!mngFundingAccounts.isFundingAccountWindowOpen());
	}
	
	@Test(description="Checking for the default value for the check number",dependsOnMethods = "testDialogWindowFieldnames", groups={"sanity", "comprehensive"})
	public void checkDefaultFieldValues() throws InterruptedException{
		Log("Checking default check number");
		assertEquals(mngFundingAccounts.getFundingAccountWindowCheckvalue(), "9000");
	}*/
	
	@DataProvider(name="accountfields")
	public Object[][] frequencies(){
		ArrayList<String> accFields = new ArrayList<String>();
		accFields.add("Financial Institution name");
		accFields.add("Account nickname");
		accFields.add("Account number");
		accFields.add("Confirm account number");
		accFields.add("Routing number");
		accFields.add("Confirm routing number");
		accFields.add("Starting check number");
		return new Object[][]{new Object[] {accFields}, 
		};
	}
}