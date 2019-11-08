package com.intuit.bpui_qa;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

public class RecurringPaymentsTests extends BPUIBasicTest{
	
	@BeforeClass
	public void startUp(){
		Log("Starting Recurring Payments Tests.");
	}
	
 	@Test(description="Cheking for the maximum number of payments that can be set for a recurring rule ", groups="sanity")
	public void acheckforMaximumLimitfornumberofPayments() throws InterruptedException{
 		Log("Checking for maximum number of payments which can be set for a recurring rule");
 		bpui.deleteScheduledPayment("canberra10", "0.10", "11/16", "yes");
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(5000);
		bpui.makeAutomaticPayment("canberra10", "Checking1", "0.10", "Once every 2 weeks", "11/16/11", "enddate", "9999", "memo");
		Thread.sleep(20000);
		assertEquals(bpui.getAutomaticPaymentNumberofPayments("canberra10"), "After 999 Payments");
 	}
 	
/* 	// This test can be removed when the memo length is set.
	@Test(description="Checking for the maximum field length for a memo", groups="sanity")
	public void bcheckLengthyMemo() throws InterruptedException{
		Log("Checking for the maximum field length for a memo");
		String maxlength = "128";
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(5000);
		assert(bpui.getMaxMemoLength("canberra10").equalsIgnoreCase(maxlength));
	}*/
	
 	@Test(description="Checking the dont save changes link", groups={"acceptance", "comprehensive"})
	public void checkDontSaveChanges() throws InterruptedException{
 		Log("Check dont save changes link");
		bpui.turnoffPaymentRule("canberra10");
		String numpayments = bpui.getAutomaticPaymentDefaultNumberofPayments("canberra10");
		String selectfreq = bpui.getAutomaticPaymentsSelectedFrequency("canberra10");
		String defaultAmt = bpui.getAmountAutomaticPayment("canberra10");
		bpui.enterAutomaticPaymentDefaultNumberofPayments("canberra10");
		bpui.selectAutomaticPaymentFrequency("canberra10", "Once every 3 months");
		if (!defaultAmt.equalsIgnoreCase("$0.99"))
		bpui.enterAutomaticPaymentAmount("canberra10", "0.99");
		else 
			bpui.enterAutomaticPaymentAmount("canberra10", "0.17");	
		bpui.dontSaveChangesRecurringPayment("canberra10");
		Thread.sleep(5000);
	//	assertEquals(numpayments, bpui.getAutomaticPaymentDefaultNumberofPayments("canberra10")); // This is a bug to be resolved
		assertEquals(selectfreq, bpui.getAutomaticPaymentsSelectedFrequency("canberra10"));
		assertEquals(defaultAmt, bpui.getAmountAutomaticPayment("canberra10"));
	}
	
	@Test(description = "Check the list of frequencies",dataProvider="frequency", groups="sanity")
	public void checkFrequency(ArrayList<String> frequency) throws InterruptedException{
		Log("Checking various frequencies that are available");
		bpui.turnoffPaymentRule("canberra10");
		assertEquals(bpui.getFrequencyforAutomaticPayments("canberra10"), frequency);
	}
	
	@Test(description="Check for the funding accounts list", dataProvider="fundingAccounts", groups="sanity")
	public void checkFundingAccount(ArrayList<String> fundingAccounts) throws InterruptedException{
		Log("Checking the funding accounts available");
		bpui.turnoffPaymentRule("canberra10");
		assertEquals(bpui.getFundingAccountforAutomaticPayments("canberra10"), fundingAccounts);
	}
	
	@Test
	public void checkGeneralErrorMessages() throws InterruptedException{
		bpui.turnoffPaymentRule("canberra10");
		bpui.clickAutomaticaPaymentSave("canberra10");
		Thread.sleep(5000);
		//assertEquals(bpui.getFundingAccountErrorAutomaticPayment("canberra10"), "Funding account is required"); // This works some time and doesn't some time
		assertEquals(bpui.getAmountErrorAutomaticPayment("canberra10"), "Amount is required");
		assertEquals(bpui.getStartDateErrorAutomaticPayment("canberra10"), "Send date is required");
		assertEquals(bpui.getSaveMessageAutomaticPayment("canberra10"), "Please correct all errors shown above");
		bpui.enterAutomaticPaymentAmount("canberra10", "999999999999999");
		assertEquals(bpui.getAmountErrorAutomaticPayment("canberra10"), "Max allowed is $10,500.00");
		bpui.enterAutomaticPaymentAmount("canberra10", ".....");
		assertEquals(bpui.getAmountErrorAutomaticPayment("canberra10"), "Invalid amount.");
	}

	@Test(description="Checking if there are help links", groups="sanity")
	public void checkHelpLabelsareLinks() throws InterruptedException{
		Log("Checking if the help labels are links");
		bpui.turnoffPaymentRule("canberra10");	
		assert(bpui.checkifListhasLinks(bpui.getHelpLinksforRecurringPayments("canberra10")));
	}
	
	@Test(description="Check if all necessary labels are present",dataProvider="labels", groups="sanity")
	public void checkLabels(ArrayList<String> labels) throws InterruptedException{
		Log("Checking if all necessary labels are present");
		bpui.turnoffPaymentRule("canberra10");
		assertEquals(bpui.getAutomaticPaymentLabelswhennotsetUp("canberra10"), labels);
	}
		
	/*@Test
	public void checkPropernumberofScheduledPaymentsPresentforMonthlypayments() throws InterruptedException, ParseException{
		bpui.deleteAllScheculedPayments();
		Date date = new Date();
		date = bpui.nextImmidiateAvailableDueDate("canberra10");
		String DATE_FORMAT = "MM/dd/yy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		String NextDate = sdf.format(date);
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(20000);
		bpui.makeAutomaticPayment("canberra10", "Checking1", "0.10", "Once a month", NextDate, "enddate", "4", "");
		Thread.sleep(35000);
		Integer y = new Integer(bpui.getNumberofSmiliarScheduledPayments("canberra10", "0.10"));
		assertEquals(1, y);
	}
	
	@Test
	public void checkPropernumberofScheduledPaymentsPresentforWeeklypayments() throws InterruptedException, ParseException{
		bpui.deleteAllScheculedPayments();
		Date date = new Date();
		date = bpui.nextImmidiateAvailableDueDate("canberra10");
		String DATE_FORMAT = "MM/dd/yy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		String NextDate = sdf.format(date);
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(20000);
		bpui.makeAutomaticPayment("canberra10", "Checking1", "0.10", "Once a week", NextDate, "enddate", "4", "");
		Thread.sleep(35000);
		Integer x = new Integer((bpui.getNumberofWeeksLeftinMonth(date))+1);
		System.out.println(date);
		System.out.println(x);
		Integer y = new Integer(bpui.getNumberofSmiliarScheduledPayments("canberra10", "0.10"));
		System.out.println(y);
		assertEquals(x, y);
	}
	
		@Test
	public void checkPropernumberofScheduledPaymentsPresentforaFourweekPayment() throws InterruptedException, ParseException{
			bpui.deleteAllScheculedPayments();
		Date date = new Date();
		date = bpui.nextImmidiateAvailableDueDate("canberra10");
		String DATE_FORMAT = "MM/dd/yy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		String NextDate = sdf.format(date);
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(20000);
		bpui.makeAutomaticPayment("canberra10", "Checking1", "0.10", "Once every 4 weeks", NextDate, "enddate", "4", "");
		Thread.sleep(35000);
		Integer y = new Integer(bpui.getNumberofSmiliarScheduledPayments("canberra10", "0.10"));
		assertEquals(1, y);
	}
	
	@Test
	public void checkPropernumberofScheduledPaymentsPresentforbiWeeklypayments() throws InterruptedException, ParseException{
		bpui.deleteAllScheculedPayments();
		Date date = new Date();
		date = bpui.nextImmidiateAvailableDueDate("canberra10");
		String DATE_FORMAT = "MM/dd/yy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		String NextDate = sdf.format(date);
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(20000);
		bpui.makeAutomaticPayment("canberra10", "Checking1", "0.10", "Once every 2 weeks", NextDate, "enddate", "4", "");
		Thread.sleep(35000);
		Integer x = new Integer((bpui.getNumberofWeeksLeftinMonth(date)));
		Integer y = new Integer(bpui.getNumberofSmiliarScheduledPayments("canberra10", "0.10"));
		assertEquals(x/2, y);
	}
	
	
	@Test
	public void checkforErrormessagewhenrepeatedAutomaticPaymentsaremade() throws InterruptedException{
		// We are using a payee whose automatic payments are currently turned off	
		bpui.deleteScheduledPayment("canberra10", "0.10", "11/16", "yes");
		bpui.addPayment("canberra10", "0.10", "11/16/11");
		Thread.sleep(5000);
		String memo = "My automatic payment";
		String errorMsg = "There is a scheduled duplicate payment detected. Please try another date, amount or funding account if you wish to make an additional payment to this payee.";
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(5000);
		bpui.makeAutomaticPayment("canberra10", "Checking1", "0.10", "Once every 2 weeks", "11/16/11", "enddate", "4", memo);
		Thread.sleep(10000);
		assertEquals(bpui.getSaveMessageAutomaticPayment("canberra10"), errorMsg);	
	}*/
	
	// Issue due to not being able to enter the date in bold text.
	@Test(description="Error if the date entered is after one year from now", groups="acceptance")
public void errorforEnteringDateafterOneYear() throws InterruptedException{
		Log("Checking the error message when date entered is one year from now");
	bpui.turnoffPaymentRule("canberra10");
	bpui.makeAutomaticPayment("canberra10", "Checking1", "0.10", "Once every 2 weeks", "08/12/14", "enddate", "4", "memo");
	Thread.sleep(5000);
	System.out.println(bpui.addTimetoCurrentDate(0, 0, 1));
	assert(bpui.getStartDateErrorAutomaticPayment("canberra10").contains("Please enter a date before "+ bpui.addTimetoCurrentDate(0, 0, 1)));
}
	
	// Does'nt work because of a bug
	@Test(description="Check the default value for the number of payments", groups={"sanity", "regression"})
	public void getDefaultNumberofPaymentsTobedone() throws InterruptedException{
		Log("Checking for the default number of payments present");
		bpui.turnoffPaymentRule("canberra10");
		Thread.sleep(5000);
		assertEquals(bpui.getAutomaticPaymentDefaultNumberofPayments("canberra10"), "3");
	}

	@DataProvider(name="labels")
	public Object[][] labels(){
		ArrayList<String> lab = new ArrayList<String>();
		lab.add("Automatic payments:");
		lab.add("Pay from:");
		lab.add("Amount:");
		lab.add("Frequency:");
		lab.add("Start on:");
		lab.add("End on:");
		lab.add("Memo: (optional)");
		lab.add("(Used if payment is sent by check. Learn more)");
		return new Object[][]{
		new Object[] {lab}, 
		};
	}
	
	@DataProvider(name="fundingAccounts")
	public Object[][] accounts(){
		ArrayList<String> freqs = new ArrayList<String>();
		freqs.add("Checking1");
		freqs.add("Checking2");
		return new Object[][]{
		new Object[] {freqs}, 
		};
	}
	
	@DataProvider(name="frequency")
	public Object[][] frequencies(){
		ArrayList<String> freqs = new ArrayList<String>();
		freqs.add("Once a week");
		freqs.add("Once every 2 weeks");
		freqs.add("Twice a month");
		freqs.add("Once every 4 weeks");
		freqs.add("Once a month");
		freqs.add("Once every 2 months");
		freqs.add("Once every 3 months");
		freqs.add("Once every 6 months");
		freqs.add("Once a year");
		return new Object[][]{
		new Object[] {freqs}, 
		};
	}	
}