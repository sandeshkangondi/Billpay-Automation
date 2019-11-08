package com.intuit.bpui_qa;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EBillCustomRulesTest extends BPUIBasicTest {

	@BeforeClass
	public void startup(){
		Log("Ebill custom rules tests");
	}
	
	@Test(description="Verifying errors for custom rules")
	public void checkCustomRuleErrors() throws InterruptedException{
		bpui.enterCustomRulecutOffAmount("Discover Credit Cards", "0.00");
		bpui.enterCustomRulePayAmount("Discover Credit Cards", 1, "Pay");
		bpui.enterCustomRulePayAmount("Discover Credit Cards", 2, "Pay");
		bpui.clickAutomaticaPaymentSave("Discover Credit Cards");
		// assert Method for getting payment due error
		assert(bpui.getPayFieldErrors("Discover Credit Cards", 1).equalsIgnoreCase("Amount Required"));
		assert(bpui.getPayFieldErrors("Discover Credit Cards", 2).equalsIgnoreCase("Amount Required"));
		assert(bpui.getSaveMessageAutomaticPayment("Discover Credit Cards").equalsIgnoreCase("Please correct all errors shown above"));
		Thread.sleep(10000);
/*		bpui.enterCustomRulecutOffAmount("Discover Credit Cards", "10555.00");
		bpui.clickAutomaticaPaymentSave("Discover Credit Cards");*/
		// assert Method for getting payment due error
	}
	
	@Test(description="Checking the default value for send days")
	public void checkDefaultSendDays() throws InterruptedException{
		assertEquals(bpui.getSendPaymentDaysbeforeDueDate("Discover Credit Cards"),"3");
		assertEquals(bpui.getSendPaymentDaysbeforeDueDate("Discover Credit Cards"), "3");
	}
	
	@Test(description = "Verifying if a payment for ebill set with custom rules can be made")
	public void checkeBillPayment() throws InterruptedException{
		bpui.setCustomRule("Discover Credit Cards", "Checking two", "1000.00", "Pay", "Pay", "10", "300", "On due date");
		String statusmessage = "Your changes have been saved.";
		assert(bpui.getStatusMessage("Discover Credit Cards").contains(statusmessage));
		bpui.setCustomRule("Discover Credit Cards", "Checking two", "1000.00", "Pay", "Pay", "10", "300", "When the bill arrives");
		assert(bpui.getStatusMessage("Discover Credit Cards").contains(statusmessage));
	}
	
	@Test(description="Verifying if the amount field changes dynamically")
	public void checkIfCutoffAmountchangesDynamically() throws InterruptedException{
		bpui.enterCustomRulecutOffAmount("Discover Credit Cards", "125.01");
		Thread.sleep(5000);
		assert(bpui.getCutoffAmountText("Discover Credit Cards").contains("$125.01"));
	}
	
	@Test(description="Validating turn off")
	public void checkDefaultsonTurnOff() throws InterruptedException{
		bpui.setCustomRule("Discover Credit Cards", "Checking two", "1000.00", "Pay", "Pay", "10", "300", "When the bill arrives");	
		bpui.turnoffPaymentRule("Discover Credit Cards");
	}
	
	@Test(description = "Checking for text boxes")
	public void checkDefaults(String x) throws InterruptedException{
		bpui.setCustomRule("Discover credit cards", "Checking two", "$10.00", "Pay", "Pay", "350.00", "123.00", "pay minimum due");
		bpui.enterCustomRulecutOffAmount("Discover Credit Cards", "$10.00");
		assert(bpui.getCustomRulegreaterthancutOffAmount("Discover Credit Cards").equalsIgnoreCase("$100.00"));
	}
	
	@Test(description = "Checking the typein box")
	public void ToTest() throws InterruptedException{
	bpui.selectCustomRule("Discover Credit Cards", 3, "Pay");
	Thread.sleep(10000);
	}
	
	
}