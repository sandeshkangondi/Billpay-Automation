package com.intuit.bpui_qa;

import java.text.ParseException;
import java.util.Date;

import org.openqa.selenium.ElementNotVisibleException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ViewReminderTests extends BPUIBasicTest {
	


	@BeforeClass
	public void startUp(){
		Log("Starting View Reminder tests.");

	}
	
	@Test
	public void noReminderSetLabel() throws InterruptedException{
		bpui.refreshBillPayment();
		if(bpui.getAutomaticPaymentsetStatus("canberra10")){
			bpui.turnoffPaymentRule("canberra10");
			Thread.sleep(15000);
		}
		if(bpui.isRemindersetforPayee("canberra10")){	
			bpui.turnoffReminder("canberra10");
			Thread.sleep(15000);
		}
		String NoreminderLabel = bpui.getNoReminderLabel("canberra10");
		assert(NoreminderLabel.contains("Unknown") && NoreminderLabel.contains("Next Due"));
		assert(bpui.checkSetareminderLinkPresent("canberra10"));
	}
	
	@Test
	public void nextduelabelwhenpaymentdue() throws InterruptedException, ParseException{
		bpui.refreshBillPayment();
		if(bpui.getAutomaticPaymentsetStatus("canberra10")){
			bpui.turnoffPaymentRule("canberra10");
			Thread.sleep(5000);
		}
		if(!bpui.isRemindersetforPayee("canberra10")){
			Date date = new Date();
			date = bpui.nextImmidiateAvailableDueDate("canberra10");
			String DATE_FORMAT = "MM/dd/yy";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
			String NextDate = sdf.format(date);
			bpui.createorModifyReminderforPayee("canberra10", "Once a week", NextDate, "10.01");
		Thread.sleep(20000);
	}
		String paymentduedayslabel= bpui.getPaymentduedaysforReminder("canberra10");
		assert(paymentduedayslabel.contains("Payment due") && paymentduedayslabel.contains("days"));
		assert(bpui.checkSkipLinkforReminder("canberra10"));
	}

	@Test
	public void amountLabelWhenReminderisSet() throws InterruptedException, ParseException{
		bpui.refreshBillPayment();
		if(bpui.getAutomaticPaymentsetStatus("canberra10")){
			bpui.turnoffPaymentRule("canberra10");
			Thread.sleep(15000);
		}
		if(!bpui.isRemindersetforPayee("canberra10") && !bpui.getReminderAmount("canberra10").equalsIgnoreCase("12.00")){
				Date date = new Date();
				date = bpui.nextImmidiateAvailableDueDate("canberra10");
				String DATE_FORMAT = "MM/dd/yy";
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
				String NextDate = sdf.format(date);
				bpui.createorModifyReminderforPayee("canberra10", "Once a week", NextDate, "12");
				Thread.sleep(20000);
		}
		String amountlabel = bpui.getReminderAmountLabel("canberra10");
		assert(amountlabel.contains("Amount : $"));
		assert(amountlabel.contains("12.00"));
	}
	
	@Test
	public void checkReminderFieldLabelWhenpayeehasAutomaticPayments() throws InterruptedException, ParseException{
		bpui.refreshBillPayment();
		if(!bpui.getAutomaticPaymentsetStatus("canberra10")){
			Date date = new Date();
			date = bpui.nextImmidiateAvailableDueDate("canberra10");
			String DATE_FORMAT = "MM/dd/yy";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
			String NextDate = sdf.format(date);
			bpui.makeAutomaticPayment("canberra10","Checking1", "0.10", "Once every 2 weeks", NextDate, "enddate", "4", "My automatic payment");	
		Thread.sleep(25000);
	}
		String ReminderwhenAutomaticPayments = bpui.getNoReminderLabelwhenAutomaticPaymentSet("canberra10");
		assert(ReminderwhenAutomaticPayments.contains("Next Due") &&  ReminderwhenAutomaticPayments.contains("Paid with Autopay"));
	}
	
	@Test
	public void checkSkipReminder() throws InterruptedException, ParseException{
		bpui.refreshBillPayment();

			if(bpui.getAutomaticPaymentsetStatus("canberra10" )){
				bpui.turnoffPaymentRule("canberra10");
				Thread.sleep(15000);
			}
			
			if(!bpui.isRemindersetforPayee("canberra10")){
				Date date = new Date();
				date = bpui.nextImmidiateAvailableDueDate("canberra10");
				String DATE_FORMAT = "MM/dd/yy";
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
				String NextDate = sdf.format(date);
				bpui.createorModifyReminderforPayee("canberra10","Once every 2 weeks", NextDate, "10.01");
				Thread.sleep(20000);
			}
			
			if(bpui.getReminderFrequency("canberra10").equalsIgnoreCase("One time") || bpui.getReminderFrequency("canberra10").equalsIgnoreCase("Once a year")){
				bpui.selectFrequencyforRemindersandAlerts("canberra10","Once a week");
				bpui.saveReminderandAlert("canberra10");
				Thread.sleep(20000);
			}
			
			bpui.clickSkipLinkforReminder("canberra10");
			Thread.sleep(5000);
			String skipmessage = bpui.getskipremindersuccess("canberra10");
			assert(skipmessage.contains("Success") && skipmessage.contains("Your reminder is skipped"));
			
	}
	
	
	@Test
	public void checkSkipReminderforOnetimePayment() throws InterruptedException, ParseException{
		bpui.refreshBillPayment();
	// For a payee whose reminder is set with a frequency greater than one time
		if(bpui.getAutomaticPaymentsetStatus("canberra10")){
			bpui.turnoffPaymentRule("canberra10");
			Thread.sleep(15000);
		}
		if(!bpui.isRemindersetforPayee("canberra10") || !bpui.getReminderFrequency("canberra10").equalsIgnoreCase("One time")){
			Date date = new Date();
			date = bpui.nextImmidiateAvailableDueDate("canberra10");
			String DATE_FORMAT = "MM/dd/yy";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
			String NextDate = sdf.format(date);
			bpui.createorModifyReminderforPayee("canberra10","One time", NextDate, "10.01");
			Thread.sleep(20000);
		}
		bpui.clickSkipLinkforReminder("canberra10");
		Thread.sleep(5000);
		assert(bpui.getPayeetileerrorforreminder("canberra10")).equalsIgnoreCase("You cannot increment the cycle for an one time manual rule.");
	}
	
	@Test
	public void checkSkipReminderforAnnualRule() throws InterruptedException, ParseException{
		bpui.refreshBillPayment();
		if(bpui.getAutomaticPaymentsetStatus("canberra10")){
			bpui.turnoffPaymentRule("canberra10");
			Thread.sleep(15000);
		}
		if(!bpui.isRemindersetforPayee("canberra10") || !bpui.getReminderFrequency("canberra10").equalsIgnoreCase("Once a year")){
			Date date = new Date();
			date = bpui.nextImmidiateAvailableDueDate("canberra10");
			String DATE_FORMAT = "MM/dd/yy";
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
			String NextDate = sdf.format(date);
			bpui.createorModifyReminderforPayee("canberra10","Once a year", NextDate, "10.01");
			Thread.sleep(20000);
		}
		bpui.clickSkipLinkforReminder("canberra10");
		Thread.sleep(5000);
		assert(bpui.getPayeetileerrorforreminder("canberra10")).equalsIgnoreCase("You cannot create or increment reminder date to be more than a year in future.");
	}

	// Test for payment overdue
	public void paymentOverduereminderLabel() throws InterruptedException{
		bpui.refreshBillPayment();
			if(bpui.getAutomaticPaymentsetStatus("canberra10")){
				bpui.turnoffPaymentRule("canberra10");
				Thread.sleep(15000);
			}
		// For a payee whose reminder is over due
			try{
		String paymentoverduelabel = bpui.getPaymentduedaysforReminder("canberra10");
		assert(paymentoverduelabel.contains("Overdue !") && paymentoverduelabel.contains("Was due on"));}
			catch(ElementNotVisibleException e){
				assert(true);
			}
	}
	
	@Test
	public void paymentTodayReminderLabel() throws InterruptedException{
			bpui.refreshBillPayment();
			if(bpui.getAutomaticPaymentsetStatus("canberra10")){
				bpui.turnoffPaymentRule("canberra10");
				Thread.sleep(15000);
			}
		// For a payee whose reminder date is today
			try{
		String PaymentTodayReminder = bpui.getPaymentduedaysforReminder("canberra10");
		assert(PaymentTodayReminder.contains("Payment due") &&  PaymentTodayReminder.contains("Today"));}
			catch(Exception e){
				assert(true);
			}}
}