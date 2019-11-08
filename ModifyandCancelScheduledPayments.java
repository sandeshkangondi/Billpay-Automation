package com.intuit.bpui_qa;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;

public class ModifyandCancelScheduledPayments extends BPUIBasicTest {

	@BeforeClass
	public void startUp() throws InterruptedException {
		Log("Starting Modify and Cancel Scheduled Payments tests.");
	}

	@Test(dataProvider = "scheduledPayment", dependsOnMethods = { "defaultValuesPresence" })
	public void validateMemoLength(String payeename, String amount, String date)
			throws InterruptedException {
		Log("Checking memo length for a scheduled payment");
		String longmemo = "Intuit was conceived by Scott Cook, whose prior work at Procter & Gamble helped him realize that personal computers would lend themselves as replacements for paper-and-pencil based personal accounting.[5] On his quest to find a programmer he ended up running into Tom Proulx at Stanford. The two started Intuit, which initially operated out of a modest room on University Avenue in Palo Alto. The first version of Quicken was coded in Microsoft's Compiler BASIC for the IBM PC and UCSD Pascal for the company in the united states.";
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		bpui.insertMemoforScheduledPayment(payeename, amount, date, longmemo);
		bpui.clickSaveEditScheduledPayment(payeename, amount, date);
		Thread.sleep(7000);
		assertEquals(bpui.getMemoErrorScheduledPayment("canberra10", "16.00",
				"08/29"), "Length validation failed: note");
		bpui.closeEditPaymentWindow();
	}

	@Test(dataProvider = "scheduledPayment", dependsOnMethods = { "checkDeleteScheduledPayment" })
	public void defaultValuesPresence(String payeename, String amount,
			String date) throws InterruptedException {
		Log("Checking default values for amount and date in a scheduled payment");
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		assertEquals(
				bpui.getDefaultAmountScheduledPayment(payeename, amount, date),
				"$16.00");
		assertEquals(
				bpui.getDefaultDateScheduledPayment(payeename, amount, date),
				"08/29/11");
		bpui.closeEditPaymentWindow();
	}

	@Test(dataProvider = "fundingAccounts", dependsOnMethods = { "defaultValuesPresence" })
	public void checkFundingAccounts(ArrayList<String> fundingAccounts)
			throws InterruptedException {
		Log("Checking the funding accounts for a scheduled payment");
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		assertEquals((bpui.getFundingAccountsScheduledPayment("canberra10",
				"16.00", "08/29")), fundingAccounts);
		bpui.closeEditPaymentWindow();
	}

	@Test(dataProvider = "categoryItems", dependsOnMethods = { "checkFundingAccounts" })
	public void checkCategories(List<String> categoryItems)
			throws InterruptedException {
		Log("Checking the category items for a scheduled payment");
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		assertEquals((bpui.getCategoriesScheduledPayment("canberra10", "16.00",
				"08/29")), categoryItems);
		bpui.closeEditPaymentWindow();
	}

	@Test(dataProvider = "scheduledPayment", dependsOnMethods = { "checkCategories" })
	public void changePaymentAccount(String payeename, String amount,
			String date) throws InterruptedException {
		Log("Verifying if a change in funding account is reflected");
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		bpui.changeFundingAccountforScheduledpayment(payeename, amount, date,
				"Checking2");
		assertEquals(bpui.getFundingAccountforScheduledPayment(payeename,
				amount, date), "Checking2");
		bpui.closeEditPaymentWindow();
	}

	@Test(dataProvider = "scheduledPayment", dependsOnMethods = { "changePaymentAccount" })
	public void changeCategory(String payeename, String amount, String date)
			throws InterruptedException {
		Log("Verifying if a change in category is reflected");
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		bpui.changeCategoryforScheduledPayment(payeename, amount, date,
				"Household");
		assertEquals(
				bpui.getCategoryforScheduledPayment(payeename, amount, date),
				"Household");
		bpui.closeEditPaymentWindow();
	}

	@Test(dataProvider = "scheduledPayment")
	public void checkErrorScheduledPayment(String payeename, String amount,
			String date) throws InterruptedException {
		Log("validating general error messages for a scheduled payment");
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		String TransactionLimit = "10501", holiday = "234";
		String TransactionLimitError = "Max allowed is $10,500.00", DateError = "Invalid date.";
		bpui.editDateforScheduledPayment(payeename, amount, date, holiday);
		bpui.editAmountforScheduledPayment(payeename, amount, date,
				TransactionLimit);
		Thread.sleep(5000);
		assertEquals(
				bpui.getDateErrorforScheduledPayment(payeename, amount, date),
				DateError);
		assertEquals(
				bpui.getAmountErrorforScheduledPayment(payeename, amount, date),
				TransactionLimitError);
		bpui.closeEditPaymentWindow();
	}

	@Test(dataProvider = "scheduledPayment")
	public void checkDeleteScheduledPayment(String payeename, String amount,
			String date) throws InterruptedException {
		Log("Validating a scheduled payment delete");
		if (!bpui
				.checkIfScheduledPaymentPresent("canberra10", "16.00", "08/29"))
			bpui.addPayment("canberra10", "16", "08/29/2011");
		String ask = "no";
		bpui.deleteScheduledPayment(payeename, amount, date, ask);
		if (ask.equalsIgnoreCase("yes")) {
			assert (!bpui.checkIfScheduledPaymentPresent(payeename, amount,
					date));
			assert (!bpui.checkScheduledPaymentonPayeeTile(payeename, amount,
					date, payeename));
			bpui.refreshBillPayment();
			assert (!bpui.checkIfScheduledPaymentPresent(payeename, amount,
					date));
			assert (!bpui.checkScheduledPaymentonPayeeTile(payeename, amount,
					date, payeename));
		} else if (ask.equalsIgnoreCase("no")) {

			assert (bpui
					.checkIfScheduledPaymentPresent(payeename, amount, date));
			bpui.refreshBillPayment();
			assert (bpui
					.checkIfScheduledPaymentPresent(payeename, amount, date));
		}
	}

	@DataProvider(name = "categoryItems")
	public Object[][] categories() {
		ArrayList<String> cats = new ArrayList<String>();
		cats.add("Auto");
		cats.add("Credit Card");
		cats.add("Household");
		cats.add("Miscellaneous");
		cats.add("None");
		cats.add("Utilities");
		return new Object[][] { new Object[] { cats }, };
	}

	@DataProvider(name = "fundingAccounts")
	public Object[][] accounts() {
		ArrayList<String> accs = new ArrayList<String>();
		accs.add("Checking1");
		accs.add("Checking2");
		return new Object[][] { new Object[] { accs }, };
	}

	@DataProvider(name = "scheduledPayment")
	public Object[][] payment() {
		return new Object[][] { new Object[] { "canberra10", "16.00", "08/29" }, };
	}
}