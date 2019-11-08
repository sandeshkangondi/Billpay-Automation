package com.intuit.bpui_qa;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;

@Test(groups={"US750"})
public class ManageFundingAccountsNavTests extends BPUIBasicTest {
	ManageFundingAccountsPage fundingAccounts;
	
	@BeforeClass(alwaysRun=true)
	public void startUp(){
		Logger.info("Starting navigation tets for manage funding accounts page.");
		bpui.clickManageFundingAccountsLink();
			
	}
	
	@Test(groups={"navigation"})
	public void verifyIfAbleToNavigateToManageFundingAccountsPage(){
		Logger.step("Verify able to navigate to manage funding accounts page works.");
		fundingAccounts = PageFactory.initElements(drvProvider.getWebDriver(), ManageFundingAccountsPage.class);	
		assert fundingAccounts.isPageLoaded();
	}
	
	@Test(groups={"navigation"}, dependsOnMethods="verifyIfAbleToNavigateToManageFundingAccountsPage")
	public void verifyIfAbleToGoBackToBillPayUIPage(){
		Logger.step("Verify able to navigation from manage funding accounts page to billpay ui works.");
		fundingAccounts.goBackToBpUiPage();
		bpui = PageFactory.initElements(drvProvider.getWebDriver(), BPUIPage.class);
		assert bpui.isPageLoaded();
	}
	
	
	@Test(groups={"navigation"}, dependsOnMethods="verifyIfAbleToGoBackToBillPayUIPage")
	public void failTest(){
		
		Logger.step("Verify able to navigation from manage funding accounts page to billpay ui works.");
		System.out.println("****** Is on Managefunding accounts page: " + fundingAccounts.isPageLoaded());
		assertEquals("test", "test1");
	}

}
