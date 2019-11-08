package com.intuit.bpui_qa;

import java.text.ParseException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.PaymentHistoryPage;

public class PaymentHistoryTests extends BPUIBasicTest {
	
	PaymentHistoryPage historyPg;
	
	@BeforeClass(alwaysRun=true)
	public void startUp(){
		Log("Starting Payment History payee tests.");
		historyPg = bpui.goToPaymentsHistoryPage();
	}

	@Test
	public void testHeadersAreShown(){
		Log("[Test Case]: Verify the default headers in the history table.");
		assert historyPg.verifyHeadersAreShown();
	}
	
	@Test
	public void testDefaultSortOrder() throws ParseException{
		Log("[Test Case]: Verify by default the sent on is sorted.");
		assert historyPg.verifyIfSendOnIsSorted();
	}
	
	@Test
	public void testTheDefaultFooter(){
		Log("[Test Case]: Verify the default footer in the payments history table.");
		assert historyPg.verifyDeaultFooter();
		
	}
	
	
	@Test
	public void testDescendingSentOnSortOrder() throws ParseException{
		Log("[Test Case]: Verify the descending sort order of the Sent On Column.");
		historyPg.sortSentOn();
		assert historyPg.verifyIfSendOnIsInDescendingOrder();
	}

	@Test
	public void testVerifyAscendingOrderOfPaidToColumn() throws ParseException{
		Log("[Test Case]: Verify the ascneding sort order of the Paid To Column.");
		historyPg.sortPaidTo();
		assert historyPg.verifyIfPaidToIsSorted();
	}

	@Test
	public void testVerifyDescendingOrderOfPaidToColumn() throws ParseException{
		Log("[Test Case]: Verify the descending sort order of the Paid To Column.");
		historyPg.sortPaidTo();
		assert historyPg.verifyIfPaidToIsSortedInReverseOrder();
	}

	@Test
	public void testVerifyAscendingOrderOfAmountColumn() throws ParseException{
		Log("[Test Case]: Verify the ascneding sort order of the Amount Column.");
		historyPg.sortAmount();
		assert historyPg.verifyIfAmountIsInAscendingOrder();
	}

	@Test
	public void testVerifyDescendingOrderOfAmountColumn() throws ParseException{
		Log("[Test Case]: Verify the descending sort order of the Amount Column.");
		historyPg.sortAmount();
		assert historyPg.verifyIfAmountIsInDescendingOrder();
	}

	@Test
	public void testVerifyAscendingOrderOfConfirmNoColumn() throws ParseException{
		Log("[Test Case]: Verify the ascneding sort order of the Confirmation Number Column.");
		historyPg.sortConfirmationNumber();
		assert historyPg.verifyIfConfirmationNumberIsInAscOrder();
	}

	@Test
	public void testVerifyDescendingOrderOfConfirmNoColumn() throws ParseException{
		Log("[Test Case]: Verify the descending sort order of the Confirmation Number Column.");
		historyPg.sortConfirmationNumber();
		assert historyPg.verifyIfConfirmationNumberIsInDescOrder();
	}

	@Test
	public void testVerifyAscendingOrderOfPaidFromColumn() throws ParseException{
		Log("[Test Case]: Verify the ascneding sort order of the Paid From Column.");
		historyPg.sortPaidFrom();
		assert historyPg.verifyIfPaidFromIsSortedInAscendingOrder();
	}

	@Test
	public void testVerifyDescendingOrderOfPaidFromColumn() throws ParseException{
		Log("[Test Case]: Verify the descending sort order of the Paid from Column.");
		historyPg.sortPaidFrom();
		assert historyPg.verifyIfPaidFromIsSortedInDescendingOrder();
	}
	
	@Test
	public void testMenuItemsOnTop(){
		Log("[Test Case]: Verify menu items.");
		assert historyPg.verifyMenuOnTop();
	}
	
	@Test
	public void testVerifyLast7DaysFilter(){
		Log("[Test Case]: Verify Last 7 Days filter functionality.");
		historyPg.selectLast7Days();
		assert historyPg.verifyIfRecordsAreFilteredAsPerDateSelection();
		assert historyPg.verifyFooterTotal();
	}

	@Test
	public void testVerifyLast2MonthsFilter(){
		Log("[Test Case]: Verify Last 2 Montsh filter functionality.");
		historyPg.openFilterMenu();
		historyPg.selectLast2Months();
		assert historyPg.verifyIfRecordsAreFilteredAsPerDateSelection();
		assert historyPg.verifyFooterTotal();
	}

	@Test
	public void testVerifyLast6MonthsFilter(){
		Log("[Test Case]: Verify Last 6 Montsh filter functionality.");
		historyPg.openFilterMenu();
		historyPg.selectLast6Months();
		assert historyPg.verifyIfRecordsAreFilteredAsPerDateSelection();
		assert historyPg.verifyFooterTotal();
	}

	@Test
	public void testVerifyCustomDateRangeFilter(){
		Log("[Test Case]: Verify Custom Date Range filter functionality.");
		historyPg.openFilterMenu();
		historyPg.enterDateRange("04/10/2011", "04/25/2011");
		assert historyPg.verifyIfRecordsAreFilteredAsPerDateSelection();
		assert historyPg.verifyFooterTotal();
	}
	
	@Test
	public void testDefaultSearchTextValue(){
		Log("[Test Case]: Verify the default search text box value");
		Log("Actual String: " + historyPg.getSearchTextBoxValue());
	}


}
