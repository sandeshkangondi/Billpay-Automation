package com.intuit.bpui_qa;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;
import com.intuit.bpui_qa.Pages.PostLogin;

public class FundingAccountsTests extends BPUIBasicTest{

	ManageFundingAccountsPage mngFundingAccounts;
	
	@BeforeClass
	public void startUp(){
		Log("Starting funding accounts tests.");
	}
	
	@Test
	public void BottomandTopFundingAccount(){
		Log("Verifying funding account at the bottom and its default value");
		assert(bpui.verifyDefaultFundingAccountBottom("Checking1"));
		assert(bpui.verifyDefaultFundingAccountOnTop("Checking1"));
	} 
	
	@Test
	public void ChangingValues1() throws InterruptedException{
		Log("Verifying changing values for funding accounts");
		bpui.changeFundingAccountOnTop("Checking2");
		Thread.sleep(5000);
		assert(bpui.verifyDefaultFundingAccountBottom("Checking2"));
	}
	
	@Test
	public void CheckingFormat() throws InterruptedException{
		Log("Check for the display format of the accounts in Manage funding accounts page ");
		bpui.gotoManageFundingAccountsPage();
		Thread.sleep(5000);
		List<WebElement> fundingAccounts = driver.findElements(By.className("funding-accounts-elements"));
		assert(!fundingAccounts.get(0).getText().contentEquals("Checking2,*") && !fundingAccounts.get(1).getText().contains("Checking1,*"));		
	}
	
	/*	@Test
	public void ManageFundingAccountsPages(){
		Log("Verifying the manage funding account page.");
		assert(mngFundingAccounts.verifyManageFundingAccountsPage());
	}*/
	
/*	@Test(dataProvider="fundingAccounts")
	public void CheckingNicknames(ArrayList<String> fundingAccounts) throws InterruptedException{
		Log("Check for the complete nick names display");
		assertEquals(fundingAccounts, bpui.getFundingAccountsonManageFundingAccountsPage());
	}*/
	
	
/*	@Test
	public void VerifyTitle(){
		Log("Verifying the title on the Manage Funding Accounts page");
		bpui.gotoManageFundingAccountsPage();
		assert (mngFundingAccounts.getHeader().equals("My Funding Accounts"));
	}*/
	
	/*@Test
		public void CheckingFormat(){
		Log("Check for the display format of the accounts in Manage funding accounts page ");
		List<WebElement> fundingAccounts = driver.findElements(By.className("funding-accounts-elements"));
		//assert(!fundingAccounts.get(0).getText().contentEquals("Checking2,*") && !fundingAccounts.get(1).getText().contains("Checking1,*"))	;	
		//assert(!fundingAccounts.get(0).getText().matches() && !fundingAccounts.get(1).getText().contains("Checking1,*"))	;	
	
		String[] Account1 = fundingAccounts.get(0).getText().split(",");
		String[] Account2 = fundingAccounts.get(1).getText().split(",");

		assertEquals(Account1[0], bpui.Accounts.get(0));
		assertEquals(Account2[0], bpui.Accounts.get(1));
		assert (Account1[1].equalsIgnoreCase("*" + bpui.AccountNumbers.get(0).substring(bpui.AccountNumbers.get(0).length()-4)) && Account2[1].equalsIgnoreCase("*" + bpui.AccountNumbers.get(1).substring(bpui.AccountNumbers.get(1).length()-4)));		
	}	
	
	@Test
	public void DefaultSelection(){		
		Log("Verifying the default funding account on the Manage Funding Accounts page ");
		bpui.gotoManageFundingAccountsPage();
		List<WebElement> fundingAccounts = driver.findElements(By.className("funding-accounts-elements"));
		if (driver.findElement(By.className("funding-account-input leftAlign").) && fundingAccounts.get(0).getText().contentEquals(mngFundingAccounts.DefaultFundingAccount())){
	assert(true);		
		}		
	}*/
	
	@DataProvider(name="fundingAccounts")
	public Object[][] accounts(){
		ArrayList<String> facs = new ArrayList<String>();
		facs.add("Checking1");
		facs.add("Checking2");
		return new Object[][]{
		new Object[] {facs}, 
		};
	}
}