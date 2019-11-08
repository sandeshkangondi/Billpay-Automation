package com.intuit.bpui_qa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;
import com.intuit.bpui_qa.beans.FundingAccountsBean;
import com.intuit.bpui_qa.Site;

@Test(groups={"US750"})
public class ManageFundingAccountsTests extends BPUIBasicTest {
	
	ManageFundingAccountsPage mngFundingAccounts;
	BPUIPage bpui;
	String defaultFundingAccount;
	List<String> fundinAccounts = new ArrayList<String>();
	String payeeName;
	
	@BeforeClass(alwaysRun=true)
	public void startUp(){
		Logger.info("Starting view manage funding account tests.");
		mngFundingAccounts = Site.gotoManageFundingAccountsPage();
	}
	
	@Test(dataProvider="expectedApprovedAccounts", groups={"acceptance"})
	public void verifyAccountsWithApprovedStatusAreDisplayedInTable(ArrayList<String> expectedApprovedAccounts){
		Logger.step("Verifying Funding Accounts with APPROVED statuses are displayed in the table.");

		assert mngFundingAccounts.getFundingAccountNames().containsAll(expectedApprovedAccounts);
	}
	
	@Test(dataProvider="expectedVerifiedAccounts", groups={"acceptance"})
	public void verifyAccountsWithVerifiedStatusAreDisplayedInTable(ArrayList<String> expectedVerifiedAccounts){
		Logger.step("Verifying Funding Accounts with VERIFIED statuses are displayed in the table.");

		assert mngFundingAccounts.getFundingAccountNames().containsAll(expectedVerifiedAccounts);
	}
	
	@Test(groups={"acceptance"})
	public void verifyAccountNumbersAreMaskedOrNot(){
		Log("[STEP]: Verifying account numbers are masked or not.");
		ArrayList<String> listedAccountNumbers = mngFundingAccounts.getFundingAccountNumbersOnly();
		Iterator<String> listOfAccountNumbers = listedAccountNumbers.iterator();
		String accountNumber;
		while(listOfAccountNumbers.hasNext()){
			accountNumber = listOfAccountNumbers.next();
			assert accountNumber.startsWith("*");
			// This could be removed, if we run across any account number of
			// length less than 5.
			assert accountNumber.length() == 5;
		}
	}
	
//	@Test(groups={"acceptance"})
//	public void verifyDefaultAccountDoesNotHaveDeleteLink(){
//		Log("[STEP]: Verifying default does not contain 'Delete' link.");
//		System.out.println("List of Links: " + mngFundingAccounts.getLinksByAccountName(mngFundingAccounts.getDefaultFundingAccountNameAtTheBottom()));
//		mngFundingAccounts.getFundingAccountNamesWhichAreNotDefault();
////		HashMap<String, String> data = mngFundingAccounts.getDetailsByAccountName(mngFundingAccounts.getDefaultFundingAccountNameAtTheBottom());
////		data.keySet()
//		
//	}
	
//	@Test
//	public void ManageFundingAccountsPage() throws InterruptedException{
//		Log("[STEP]: Verifying the manage funding account page.");
//		mngFundingAccounts = Site.gotoManageFundingAccountsPage();
//		Log("[STEP]: In manage funding accounts page.");
//		System.out.println("Funding account names: " + mngFundingAccounts.getFundingAccountNames());
//		System.out.println("Funding account nick names: " + mngFundingAccounts.getFundingAccountNickNamesOnly());
//		System.out.println("Funding account links: " + mngFundingAccounts.getFundingAccountsLinksTextOnly());
//		System.out.println("Funding account data: " + mngFundingAccounts.getFundingAccountTableData());
//		System.out.println("Default Funding account label: " + mngFundingAccounts.getDefaultFundingAccountLabelAtTheBottom());
//		System.out.println("Default Funding account name: " + mngFundingAccounts.getDefaultFundingAccountNameAtTheBottom());
//		System.out.println("Default Funding account number: " + mngFundingAccounts.getDefaultFundingAccountNumberAtTheBottom());
//		System.out.println("NOTE: " + mngFundingAccounts.getAddFundingAccountNote());
//		mngFundingAccounts.clickAddFundingAccount();
//		Thread.sleep(5000);
////		assert( mngFundingAccounts.verifyManageFundingAccountsPage());
////		defaultFundingAccount = mngFundingAccounts.getDefaultFundingAccount();
////		fundinAccounts = mngFundingAccounts.getDefaultFundingAccountsList();
//	}
	
//	@Test(dependsOnMethods="ManageFundingAccountsPage")
//	public void verifyDefaultFundingAccountFirst(){
//		Log("Verifying the default funding account top.");
//		bpui = Site.goBackToBpUiPage();
//		assert(bpui.verifyDefaultFundingAccountOnTop(defaultFundingAccount));
//	}
//	
//	@Test(dependsOnMethods="ManageFundingAccountsPage")
//	public void verifyDefaultFundingAccountSecond(){
//		Log("Verifying the default funding account bottom.");
//		assert(bpui.verifyDefaultFundingAccountBottom(defaultFundingAccount));
//	}
//	
//	@Test(dependsOnMethods={"verifyDefaultFundingAccountFirst", "verifyDefaultFundingAccountSecond"})
//	public void verifyFundingAccountListOnTop() throws InterruptedException{
//		Log("Verify funding accounts on top pay from dropdown");
//		bpui.openTopFundinAccountsList();
//		List<String> fundingAccountsTop = bpui.getFundingAccountListOnTop();
//		assert bpui.verifyFundingAccounts(fundingAccountsTop, fundinAccounts);
//		bpui.closeTopFundinAccountsList();
//	}
//
//	@Test(dependsOnMethods={"verifyFundingAccountListOnTop"})
//	public void verifyChangeFundingOnTopReflectsBottom(){
//		Log("Select funding account on top and check if reflected on bottom");
//		bpui.openTopFundinAccountsList();
//		bpui.selectTopFundingAccount("Checking1");
//		System.out.println("Bottom selected funding account: " + bpui.verifyDefaultFundingAccountBottom("Checking1"));
//		bpui.closeTopFundinAccountsList();
//	}
//
//	@Test(dependsOnMethods={"verifyFundingAccountListOnBottom"})
//	public void verifyChangeFundingOnBottomReflectsTop(){
//		Log("Select funding account in bottom and check if reflected on top");
//		bpui.openBottomFundinAccountsList();
//		bpui.selectTopFundingAccount("Checking2");
//		System.out.println("Top selected funding account: " + bpui.verifyDefaultFundingAccountOnTop("Checking2"));
//		bpui.closeBottomFundinAccountsList();
//	}
//
//	@Test(dependsOnMethods={"verifyDefaultFundingAccountFirst", "verifyDefaultFundingAccountSecond"})
//	public void verifyFundingAccountListOnBottom() throws InterruptedException{
//		Log("Verify funding accounts on bottom pay from dropdown.");
//		bpui.openBottomFundinAccountsList();
//		List<String> fundingAccountsBottom = bpui.getFundingAccountListOnBottom();
//		assert bpui.verifyFundingAccounts(fundingAccountsBottom, fundinAccounts);
//		bpui.closeBottomFundinAccountsList();
//	}
//	
//	
//	@Test(dependsOnMethods={"verifyDefaultFundingAccountFirst", "verifyDefaultFundingAccountSecond"})
//	public void verifyFundingAccountsForAnExistingPayee() throws InterruptedException{
//		Log("Verify funding accounts for an existing payee.");
//		payeeName = bpui.getFirstPayeeName();
//		bpui.togglePayeeDrawer(payeeName);
//		bpui.goToAutomaticPaymentTab(payeeName);
//		bpui.openFundingAccountListForPayee(payeeName);
//		List<String> payeeFundingAccounts = bpui.getFundingAccountListForAPayee(payeeName);
//		assert bpui.verifyFundingAccounts(payeeFundingAccounts, fundinAccounts);
//		
//		bpui.togglePayeeDrawer(payeeName);
//	}
//	
////	@Test(dataProvider="unknownPayeeTop", dependsOnMethods={"verifyDefaultFundingAccountFirst", "verifyDefaultFundingAccountSecond"})
////	public void verifyNewPayeeFundingAccounts(String payeeName, String accountNumber, String confirmActNumber, String address1, String city, String state, String zip) throws InterruptedException{
////		Log("Running testAddPayeeSuccess test...");
////		
////		bpui.enterUnkownPayeeDetails(payeeName, accountNumber, confirmActNumber, address1, city, state, zip);
////		bpui.clickAddPayee();
////		Thread.sleep(30000);
////
////		bpui.togglePayeeDrawer(payeeName);
////		bpui.goToAutomaticPaymentTab(payeeName);
////		bpui.openFundingAccountListForPayee(payeeName);
////		List<String> payeeFundingAccounts = bpui.getFundingAccountListForAPayee(payeeName);
////		assert bpui.verifyFundingAccounts(payeeFundingAccounts, fundinAccounts);
////
////		Log("Finishing testAddPayeeSuccess test...");
////	}

	@DataProvider(name="expectedApprovedAccounts")
	public Object[][] getApprovedAccounts(){
		FundingAccountsBean fundingAccounts = new FundingAccountsBean(); 
		System.out.println("Approved Funding Accounts: " + fundingAccounts.getApprovedFundingAccounts());
		ArrayList<String> accounts = fundingAccounts.getApprovedFundingAccounts();
//		accounts.add("Checking1");
//		accounts.add("Checking2");
//		accounts.add("intuit");
//		accounts.add("tmobile");
		return new Object[][] {
			new Object[] {accounts}
		};
	}

	@DataProvider(name="expectedVerifiedAccounts")
	public Object[][] getVerifiedAccounts(){
		FundingAccountsBean fundingAccounts = new FundingAccountsBean(); 
		System.out.println("Verified Funding Accounts: " + fundingAccounts.getVerifiedFundingAccounts());
		ArrayList<String> accounts = fundingAccounts.getVerifiedFundingAccounts();
//		accounts.add("Checking1");
//		accounts.add("Checking2");
//		accounts.add("intuit");
//		accounts.add("tmobile");
		return new Object[][] {
			new Object[] {accounts}
		};
	}

}
