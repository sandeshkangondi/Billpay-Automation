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


public class HidePayeeTests extends BPUIBasicTest {
	
	@BeforeClass
	public void startUp(){
		Log("Starting manage funding account tests.");
		verifyStartupForTheseTests();
	}
	
	private void verifyStartupForTheseTests(){
		if(bpui.getPayeeCount() == 0)
			throw new RuntimeException("Setup failed for: " + this.getClass().getName());
	}
	
	@Test
	public void verifyHidePayee(){
		Log("[STEP]: Verifying Hide Payee.");
		int payeeCountBeforeHide = bpui.getPayeeCount();
		Log("Payee count before hide.. " + payeeCountBeforeHide);
		bpui.hidePayee(bpui.getFirstPayeeName());
		int payeeCountAfterHide = bpui.getPayeeCount();
		Log("Payee count after hide.. " + payeeCountAfterHide);
		assert payeeCountBeforeHide - payeeCountAfterHide == 1;
	}

}
