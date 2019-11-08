package com.intuit.bpui_qa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;
import com.intuit.bpui_qa.Pages.PostLogin;
import com.intuit.bpui_qa.beans.FundingAccountsBean;
import com.intuit.bpui_qa.Site;

@Test(groups={"US39"})
public class AddFundingAccountsTests extends BPUIBasicTest {
	
	ManageFundingAccountsPage mngFundingAccounts;
	BPUIPage bpui;
	String defaultFundingAccount;
	List<String> fundinAccounts = new ArrayList<String>();
	String payeeName;
	
	@BeforeClass(alwaysRun=true)
	public void startUp(){
		Log("Starting add manage funding account tests.");
		mngFundingAccounts = Site.gotoManageFundingAccountsPage();
	}
	
	@Test(groups={"smoke"})
	public void verifyAccountsWithApprovedStatusAreDisplayedInTable(ArrayList<String> expectedApprovedAccounts){
		Log("[STEP]: Verifying Funding Accounts with APPROVED statuses are displayed in the table.");
		assert mngFundingAccounts.getFundingAccountNames().containsAll(expectedApprovedAccounts);
	}
	


	@DataProvider(name="expectedApprovedAccounts")
	public Object[][] getApprovedAccounts(){
		FundingAccountsBean fundingAccounts = new FundingAccountsBean(); 
		System.out.println("Approved Funding Accounts: " + fundingAccounts.getApprovedFundingAccounts());
		ArrayList<String> accounts = fundingAccounts.getApprovedFundingAccounts();

		return new Object[][] {
			new Object[] {accounts}
		};
	}

}
