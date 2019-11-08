package com.intuit.bpui_qa;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;

public class SearchPayeeTests extends BPUIBasicTest{

//	BPUIPage bpui;

	@BeforeClass
	public void startUp(){
		Log("Starting Search Payee tests.");
//		LoginPage login = PageFactory.initElements(driver, LoginPage.class);
//		
//		PostLogin ptLogin = login.login();
		
//		bpui = ptLogin.navigateToBillPayUI();
	}


	@Test
	public void NoSearchResultsForSingleCharFirst(){
		Log("Searching payee with a single character Top");
		bpui.searchForSingleCharacter("A");
		Log("Verifying to see if there are results for a single character search top.");
		assert bpui.verifyResultsForSingleCharSearch("A");
	}

	@Test
	public void NoSearchResultsForSingleCharSecond(){
		Log("Searching payee with a single character bottom");
		bpui.searchForSingleCharacterBottom("T");
		Log("Verifying to see if there are results for a single character search bottom.");
		assert bpui.verifyResultsForSingleCharSearch("T");
	}

	@Test(dataProvider="knownMerchantsTop")
	public void SearchKnownMerchantTop(String payeeName){
		Log("Searching payee name on top: " + payeeName);
		bpui.searchKnownPayeeOnTop(payeeName);
		Log("Verifying payee results on top...");
		assert bpui.veirfyKnownPayeeResultsTop(payeeName);
	}
	
	@Test(dataProvider="knownMerchantsBottom")
	public void SearchKnownMerchantBottom(String payeeName){
		Log("Searching payee name on bottom: " + payeeName);
		bpui.searchKnownPayeeOnBottom(payeeName);
		Log("Verifying payee results on bottom...");
		assert bpui.verifyKnownPayeeResultsBottom(payeeName);
		
	}
	
	@Test
	public void VerifyMerchantSelection(){
		Log("Searching payee name on top: ");
		bpui.searchKnownPayeeOnTop("American");
		bpui.verifyAbleToSelectAnyResult();
	}
	
	
	@DataProvider(name="knownMerchantsTop")
	public Object[][] getKnownMerchantsListForTop(){
		return new Object[][] {
			new Object[] {"American"},
			new Object[] {"T-Mobile"}
		};
	}
	
	@DataProvider(name="knownMerchantsBottom")
	public Object[][] getKnownMerchantsListForBottom(){
		return new Object[][] {
			new Object[] {"American"},
			new Object[] {"T-Mobile"}
		};
	}

}
