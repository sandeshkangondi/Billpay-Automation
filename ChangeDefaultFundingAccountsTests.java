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

@Test(groups={"US391"})
public class ChangeDefaultFundingAccountsTests extends BPUIBasicTest {
	
	ManageFundingAccountsPage mngFundingAccounts;
	BPUIPage bpui;
	List<String> fundinAccounts = new ArrayList<String>();
	String payeeName;
	String defaultFundingAccount;
	String defaultFundingAccountNumber;
	String defaultFundingAccountAtTheBottom;
	
	@BeforeClass(alwaysRun=true)
	public void startUp(){
		Logger.info("Starting change default funding account tests.");
		mngFundingAccounts = Site.gotoManageFundingAccountsPage();
	}
	
	@Test(groups={"acceptance"}, description="Verify changing the default funding accounts is reflected in the bottom.")
	public void changeDefaultFundingAccountAndVerifyIfChangedAtBottom() throws InterruptedException{

		Logger.step("Changing the default in manage funding accounts.");
		defaultFundingAccount = mngFundingAccounts.changeDefaultFundingAccount();
		
		defaultFundingAccountNumber = defaultFundingAccount.split(" ")[1];
		defaultFundingAccount = defaultFundingAccount.split(" ")[0].trim();
		
		defaultFundingAccountAtTheBottom = mngFundingAccounts.getDefaultFundingAccountAtBottom();
		
		assertEquals(defaultFundingAccount, defaultFundingAccountAtTheBottom);
		assertEquals(defaultFundingAccountNumber, mngFundingAccounts.getDefaultFundingAccountNumberAtBottom());
		assertEquals("Changes saved.", mngFundingAccounts.getChangeDefaultFundingAccountStatus());
	}
	
	@Test(groups={"acceptance"}, dependsOnMethods="changeDefaultFundingAccountAndVerifyIfChangedAtBottom", 
		  description="Verify changing the default funding accounts is reflected in the table.")
	public void changeDefaultFundingAccountAndVerifyIfChangedInTable() throws InterruptedException{
		assertEquals(defaultFundingAccount, mngFundingAccounts.getDefaultFundingAccountNickNameFromTable());
		assertEquals(defaultFundingAccountNumber, mngFundingAccounts.getDefaultFundingAccountNumberFromTable());
		assertEquals("Edit", mngFundingAccounts.getLinksByAccountNickName(defaultFundingAccountAtTheBottom).get(0));
	}
	
	@Test(groups={"acceptance"}, dependsOnMethods="changeDefaultFundingAccountAndVerifyIfChangedAtBottom", 
		  description="Verify if the default funding acounts links is only 'Edit'.")
	public void changeDefaultFundingAccountAndVerifyIfLinksChanged() throws InterruptedException{
		int linksSize = mngFundingAccounts.getLinksByAccountNickName(defaultFundingAccountAtTheBottom).size();
		String defaultLinks = mngFundingAccounts.getLinksByAccountNickName(defaultFundingAccountAtTheBottom).get(0);
		mngFundingAccounts.getListOfFundingAccountsInChangeDefaultMenu();
		assertEquals(1, linksSize);
		assertEquals("Edit", defaultLinks);
		assertNotEquals(defaultLinks, "Delete");
	}

	@Test(groups={"acceptance"}, dependsOnMethods="changeDefaultFundingAccountAndVerifyIfChangedAtBottom", 
			  description="Verify if the default funding acounts links is only 'Edit'.")
		public void changeDefaultFundingAccountAndVerifyOnMainPage() throws InterruptedException{
			bpui = Site.goBackToBpUiPage();
			
			assertEquals(defaultFundingAccount, bpui.getSelectedFundingAccountFromTop());
			assertEquals(defaultFundingAccount, bpui.getSelectedFundingAccountFromBottom());
		}
}
