package com.intuit.bpui_qa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sourceforge.htmlunit.corejs.javascript.ast.WithStatement;

import org.openqa.selenium.lift.match.SelectionMatcher;
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
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
//import static org.hamcrestcollections.Selector.*;

@Test(groups={"US390"})
public class FundingAccountsBalanceTests extends BPUIBasicTest {

	ManageFundingAccountsPage mngFundingAccounts;
	BPUIPage bpui;
	String defaultFundingAccount;
	List<String> fundinAccounts = new ArrayList<String>();
	String payeeName;
	
	@BeforeClass(alwaysRun=true)
	public void startUp(){
		Logger.info("Starting manage funding account balances tests.");
		mngFundingAccounts = Site.gotoManageFundingAccountsPage();
	}
	
	@Test(groups={"acceptance"})
	public void verifyAccountBalancesAreDisplayed(){
		Log("[STEP]: Verifying account balances are displayed or not.");
		
		ArrayList<String> accountBalances = mngFundingAccounts.getFundingAccountBalances();
		
		
		Iterator<String> listOfAccountBalances = accountBalances.iterator();
		while(listOfAccountBalances.hasNext()){
			assert listOfAccountBalances.next().length() > 0;
		}
	}
}
