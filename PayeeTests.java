package com.intuit.bpui_qa;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;

public class PayeeTests extends BasicTest {

	BPUIPage bpui;
	
	@BeforeClass(alwaysRun = true)
	public void startUp(){
//		System.out.println("Starting payee tests.....");
//		LoginPage login = new LoginPage(driver);
//		PostLogin ptLogin = new PostLogin(driver);
//		login.login();
//		bpui = ptLogin.navigateToBillPayUI();
//		  

	}
	
//	@Test
//	public void AddUnKnownPayeeTest(){
//		bpui.addUnKnownPayee("QA Auto Payee", "123456", "El Camino Real", "Santa Clara", "California");
////		bpui.searchKnownPayee("American Express");
//		assert(true);
//	}
	
	@Test 
	public void ManageFundinAccounts(){
//		bpui.manageFundingAccountsPage();
		assert(true);
	}
	
//	@Test
//	public void PayFromFundinAccounts(){
//		bpui.changeFundingAccountOnTop("Checking1");
//	}
	
}
