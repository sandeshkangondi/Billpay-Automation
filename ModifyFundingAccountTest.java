package com.intuit.bpui_qa;

import java.util.ArrayList;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;

public class ModifyFundingAccountTest extends BPUIBasicTest {

	ManageFundingAccountsPage mngFundingAccounts;
	
	@BeforeClass
	public void startUp() throws InterruptedException{
		Log("Starting Modify Funding Accounts tests.");
		mngFundingAccounts = Site.gotoManageFundingAccountsPage();
		Thread.sleep(5000);
	}
	
	/*@Test(description="To check for the labels in the edit window", dataProvider="editWindowLabels", groups="sanity")
	public void testEditWindowLabels(ArrayList<String> windowlabels) throws InterruptedException{
		Log("Checking all the labels for the Edit Window labels");
		assertEquals(windowlabels, mngFundingAccounts.getEditWindowLabels("Checking1", "Checking1"));
	}
	
	@Test(description="To check the text associated with the checkbox")
	public void checkDefaultFundingAccountCheckBoxText(){
		Log("Checking text associated with check box");
	assertEquals(mngFundingAccounts.getChangetoDefaultFundingAccountLabel("Checking1", "Checking1"), "Make this my default funding account");	
	}
	
	@Test(description="To change a funding account to default", dependsOnMethods={"checkDefaultFundingAccountCheckBoxText", "checkGeneralErrorMessages", "checkCancelFunctionality"})
	public void changetoDefaultFundingAccount() throws InterruptedException{
		Log("Changing default funding account");
		mngFundingAccounts.changeEditWindowNickname("Checking1", "Checking1", "Checking1");
		mngFundingAccounts.changeDefaultFundingAccount("Checking1", "Checking1");
		assert(mngFundingAccounts.isFundingAccountDefault("Checking1", "Checking1"));
		mngFundingAccounts.changeDefaultFundingAccount("Checking2", "Checking2");
	}
	
	@Test(description="To check the field length for the nickname")
	public void checkNicknameFieldLength(){
		Log("Checking the field length for the nickname");
		assertEquals(mngFundingAccounts.getEditWindowNicknameLength("Checking1", "Checking1"), "32");
	}
	
	@Test(description="To check the nickname modification") 
	public void checkModifyingNickname() throws InterruptedException{
		Log("Checking to modify a nickname");
		mngFundingAccounts.changeEditWindowNickname("Checking1", "Checking1", "Check1");
		mngFundingAccounts.clickEditWindowSave("Checking1", "Checking1");
		assert(mngFundingAccounts.isFundingAccountPresent("Checking1", "Check1"));
		mngFundingAccounts.changeEditWindowNickname("Checking1", "Check1", "Checking1");
		mngFundingAccounts.clickEditWindowSave("Checking1", "Checking1");
	}
	
	@Test(description="To check cancelling a modification in funding account")
	public void checkCancelFunctionality() throws InterruptedException{
		Log("Checking the cancel functionality");
		mngFundingAccounts.clickEditWindowCancel("Checking1", "Checking1");
		assert(!mngFundingAccounts.isEditWindowOpen());
	}
	
	@Test(description="To check for the error messages in the edit dialog")
	public void checkGeneralErrorMessages() throws InterruptedException{
		Log("Checking the general error messages.");
		mngFundingAccounts.changeEditWindowNickname("Checking1", "Checking1", "");
		mngFundingAccounts.clickEditWindowSave("Checking1", "Checking1");
		String errorfornickname = "Nickname is required";
		String saveerror = "Please correct all errors shown above.";
		assert(mngFundingAccounts.getEditWindowNicknameError().equalsIgnoreCase(errorfornickname));
		assert(mngFundingAccounts.getEditWindowSaveError().equalsIgnoreCase(saveerror));
	}*/
	
	@DataProvider(name="editWindowLabels")
	public Object[][] getEditWindowLabels(){
		ArrayList<String> labels = new ArrayList<String>();
		labels.add("Edit account details");
		labels.add("Financial Institution name");
		labels.add("Account nickname");
		labels.add("Account number");
		labels.add("Routing number");
		return new Object[][]{new Object[] {labels}, 
		};
	}
}