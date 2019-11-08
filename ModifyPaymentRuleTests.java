package com.intuit.bpui_qa;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

	public class ModifyPaymentRuleTests  extends BPUIBasicTest{
		
		@BeforeClass
		public void startUp(){
			Log("Starting Modify payment rule tests.");
		}
		
		// To Modify a payment rule successfully
		@Test
		public void modifyPaymentRuleSuccessfully() throws InterruptedException{
			Log("Verifying a successful payment rule modification for delivery date model");	
			String Amount = "10.00";
			if(!bpui.getAutomaticPaymentsetStatus("NET"))
				bpui.makeAutomaticPayment("NET", "Checking1", "0.10", "Once a month", "08/12/2011", "enddate", "4", "");
				Thread.sleep(10000);
				bpui.modifyAutomaticPaymentAmount("NET", Amount);
				bpui.clickAutomaticaPaymentSave("NET");
				Thread.sleep(10000);
				assertEquals(bpui.getAutomaticPaymentsetAmount("NET"), Amount);
		}
		
		// To modify a recurring payment unsuccessfully and check if the drawer is still open with the appropriate error
		@Test
		public void modifyPaymentRuleUnsuccessfully() throws InterruptedException{
			Log("Verifying an unsuccessful payment rule modification for delivery date model");
			if(!bpui.getAutomaticPaymentsetStatus("NET"))
				bpui.makeAutomaticPayment("NET", "Checking1", "10", "Once every 2 weeks", "08/22/11", "enddate", "04/22/12", "automatic payment");
			Thread.sleep(20000);
			String SetAmount = bpui.getAutomaticPaymentsetAmount("NET");
			String AmountOverLimit = "10,501";
			String InvalidAmount = "10,5......01";
			bpui.modifyAutomaticPaymentAmount("NET", InvalidAmount);
			assertEquals(bpui.getAmountErrorAutomaticPaymentwhenPaymentisSet("NET"), "Invalid amount.");
			bpui.modifyAutomaticPaymentAmount("NET", AmountOverLimit);
			assertEquals(bpui.getAmountErrorAutomaticPaymentwhenPaymentisSet("NET"), "Max allowed is $10,500.00");
			bpui.clickAutomaticaPaymentSave("NET");
			assertEquals(bpui.getSaveMessageAutomaticPayment("NET"), "Please correct all errors shown above");
			assertEquals(bpui.getAmountErrorAutomaticPaymentwhenPaymentisSet("NET"), "Max allowed is $10,500.00");
			bpui.refreshBillPayment();
			assertEquals(bpui.getAutomaticPaymentsetAmount("NET"), SetAmount);
		}
		
		// Check dont save changes link
		@Test
		public void checkDontSaveChanges() throws InterruptedException{
			Log("Verifying the 'Don't make changes' link in automatic payments");
			if(!bpui.getAutomaticPaymentsetStatus("NET"))
				bpui.makeAutomaticPayment("NET", "Checking1", "10", "Once every 2 weeks", "08/22/11", "enddate", "04/22/12", "automatic payment");
			Thread.sleep(5000);
		String SetAmount = bpui.getAutomaticPaymentsetAmount("NET");
		String Amount = "1234.6";
		bpui.modifyAutomaticPaymentAmount("NET", Amount);
		bpui.dontSaveChangesRecurringPayment("NET");
		assertEquals(SetAmount, bpui.getAutomaticPaymentsetAmount("NET"));
		bpui.refreshBillPayment();
		assertEquals(SetAmount, bpui.getAutomaticPaymentsetAmount("NET"));
		}
		
		// CANCEL A PAYMENT RULE
		
		// Testing cancelling a payment rule functionality
		@Test
		public void cancellingPaymentRule() throws InterruptedException{
			Log("Verifying 'turn off' functionality for an automatic payment");
			if(!bpui.getAutomaticPaymentsetStatus("NET"))
				bpui.makeAutomaticPayment("NET", "Checking1", "10", "Once every 2 weeks", "08/22/11", "enddate", "04/22/12", "automatic payment");
			Thread.sleep(10000);
			bpui.turnoffPaymentRule("NET");
			bpui.refreshBillPayment();
			assert(!bpui.getAutomaticPaymentsetStatus("NET"));
		}
		
		@Test
		public void checkTurnOffLink() throws InterruptedException{
			Log("Checking if 'turn off' link is present for an automatic payment when set up");
			//bpui.turnoffPaymentRule("NET");
			if(!bpui.getAutomaticPaymentsetStatus("NET"))
			bpui.makeAutomaticPayment("NET", "Checking1", "0.10", "Once every 2 weeks", "08/12/11", "enddate", "4", "memo");
			Thread.sleep(10000);
			assert(bpui.checkTurnofflinkforRecurringPayments("NET"));
		}
		
		@Test(dataProvider="labels")
		public void checkLabels(List<String> labels) throws InterruptedException{
			if(!bpui.getAutomaticPaymentsetStatus("NET"))
				bpui.makeAutomaticPayment("NET", "Checking1", "0.10", "Once a month", "08/12/2011", "enddate", "4", "");
			bpui.refreshBillPayment();
			assertEquals(bpui.getAutomaticPaymentLabelswhensetUp("NET"), labels);
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
			lab.add("Memo:");
			return new Object[][]{
			new Object[] {lab}, 
			};
		}
}