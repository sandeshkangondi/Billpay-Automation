package com.intuit.bpui_qa;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;

public class DeleteFundingAccountTest extends BPUIBasicTest {

	ManageFundingAccountsPage mngFundingAccounts;
	
	@BeforeClass
	public void startUp() throws InterruptedException{
		Log("Starting Modify Funding Accounts tests.");
		mngFundingAccounts = Site.gotoManageFundingAccountsPage();
		Thread.sleep(5000);
	}
	
/*	@Test(description="To check the header text for delete window")
	public void checkDeleteWindowHeaderText(){
		Log("Checking header text for delete window");
		String header = "Are you sure you want to delete this account?";
		String headerText = mngFundingAccounts.getDeleteFundingAccountWindowHeader("Checking1", "Checking1");
		assert(headerText.equalsIgnoreCase(header));
	}
	
	@Test(description="To check the cancel functionality")
	public void checkDeleteWindowCancelFunctionality(){
		Log("Checking delete window cancel");
		mngFundingAccounts.openDeleteAccountWindow("bank of california", "boc");
		mngFundingAccounts.clickDeleteFundingAccountWindowCancelButton("bank of california", "boc");
		assert(!mngFundingAccounts.isDeleteFundingAccountWindowOpen("bank of california", "boc"));
	}
	
	@Test(description="To check for valid nicknames in delete window")
	public void checkDeleteWindowFundingAccount() throws InterruptedException{
		Log("Checking the funding account for delete window");
		assert(mngFundingAccounts.getDeleteWindowAccountNickname("Checking1", "Checking1").contains("Checking1"));
	}
	
	@Test(description= "To check the delete window for payments present")
	public void acheckDeleteWindowPayments2() throws InterruptedException{
		Log("Checking for the delete window for payments ");
		Thread.sleep(10000);
		Log("Checking the delete window for payments");
		assert(mngFundingAccounts.isDeleteWindowPaymentsPresent("Checking1", "Checking1"));
	}
	
	@Test(description="To check the close functionality",dependsOnMethods="checkDeleteWindowCancelFunctionality")
	public void checkDeleteWindowCloseFunctionality() throws InterruptedException{
		Log("Checking close functionality for delete window");
		mngFundingAccounts.clickDeleteFundingAccountWindowCloseButton("Checking1", "Checking1");
		assert(mngFundingAccounts.isDeleteWindowPaymentsPresent("Checking1", "Checking1"));
	}
	
	@Test(description= "To check the delete functionality")
	public void checkDeleteFunctionality() throws InterruptedException{
		Log("Checking delete functionality for a funding account");
		mngFundingAccounts.clickDeleteFundingAccountWindowDeleteButton("Bank of Michigan", "Bom111");
		assert(!mngFundingAccounts.isFundingAccountPresent("Bank of Michigan", "Bom111"));
		mngFundingAccounts.addFundingAccount("Bank of Michigan", "Bom111", "123123123", "123123123", "275079714", "275079714", "9000");
	}*/
}
