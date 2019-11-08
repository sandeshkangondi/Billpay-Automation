package com.intuit.bpui_qa;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.EbillHistoryPage;
import java.text.ParseException;

@Test(groups = { "US50" } )

public class EBillHistoryTests extends BPUIBasicTest {

	
	
	EbillHistoryPage historyPg;
	boolean myBillLinkVisible;

	
	@BeforeClass(alwaysRun=true)

	public void startUp(){

		Log("Starting eBill History Page tests.");
		
		Site.gotoPaymentHistoryPage();
		
		historyPg = Site.gotoEbillHistoryPage();		
		
	}

	
	
	
	
	@Test(groups={"Acceptance"} )

	public void testColumnHeadersAreShown(){

		Log("[Test Case]: Verify the default headers in the eBill history table.");
					
		
		assert historyPg.verifyEBillColumnHeadersAreShown();

	}
	
	
	
	@Test(groups = "Acceptance")
	
	public void testPopUpAppearsForViewIconOptions(){
		
		Log("[Test Case]: Verify that the appropriate pop up is displayed when we click on 'View' Icon");
		
		historyPg.clickViewIcon();
		
		assert historyPg.verifyViewPopUpIsDisplayed();
		
		historyPg.clickXButtonOnViewEditDeletePopUp();
	}
	
	
	
	@Test(groups = "Acceptance" ,  dependsOnMethods = {"testPopUpAppearsForViewIconOptions"})
	
	public void testPopUpAppearsForEditIconOptions(){
		
		Log("[Test Case]: Verify that the appropriate pop up is displayed when we click on 'Edit' Icon");
		
		historyPg.clickEditIcon();
		
		assert historyPg.verifyEditPopUpIsDisplayed();
		
		historyPg.clickXButtonOnViewEditDeletePopUp();
	}

	
	
	@Test( groups = "Acceptance" , dependsOnMethods = {"testPopUpAppearsForEditIconOptions"})

	public void testPopUpAppearsForDeleteIconOptions(){
		
		Log("[Test Case]: Verify that the appropriate pop up is displayed when we click on 'Delete' Icon");
		
		historyPg.clickDeleteIcon();
		
		assert historyPg.verifyDeletePopUpIsDisplayed();
		
		historyPg.clickXButtonOnViewEditDeletePopUp();
	}
	
	
	
	
	
	
	@Test( groups = "Acceptance" , dependsOnMethods = {"testPopUpAppearsForDeleteIconOptions"})
		
		public void testViewPopUpDisappearsWhenCloseButtonIsPressed(){
			
			Log("[Test Case]: Verify that View pop up disappears when we click 'Close' button");
			
			historyPg.clickViewIcon();
			
			assert historyPg.verifyViewPopUpIsDisplayed();
			
			
			historyPg.clickCloseButtonOnViewPopUp();
			
			assert ! historyPg.verifyViewPopUpIsDisplayed();
			
		
			
		}
			
			
				
	
	@Test( groups = "Acceptance" , dependsOnMethods = {"testViewPopUpDisappearsWhenCloseButtonIsPressed"})
	
	public void testEditPopUpDisappearsWhenCloseButtonIsPressed(){
		
		Log("[Test Case]: Verify that Edit pop up disappears when we click 'Don't save changes' button");
		
		historyPg.clickEditIcon();
		
		assert historyPg.verifyEditPopUpIsDisplayed();
		
		historyPg.clickCloseButtonOnEditPopUp();
		
		assert ! historyPg.verifyEditPopUpIsDisplayed();
		
	}
	
	
	
	
	
	@Test( groups = "Acceptance" , dependsOnMethods = {"testEditPopUpDisappearsWhenCloseButtonIsPressed"})
	
	public void testDeletePopUpDisappearsWhenCloseButtonIsPressed(){
		
		Log("[Test Case]: Verify that View pop up disappears when we click 'Close' button");
		
		historyPg.clickDeleteIcon();
		
		assert historyPg.verifyDeletePopUpIsDisplayed();
		
		historyPg.clickCloseButtonOnDeletePopUp();
		
		assert ! historyPg.verifyDeletePopUpIsDisplayed();
		
		
	}
	

	
	
	
	
	@Test(groups={"Acceptance"} )
	
	public void testMandatoryCheckForDueDate(){
		
		Log("[Test Case]: Verify the mandatory check for Due Date for every ebill detail.");
		
		assert historyPg.verifyDueDateOptions();
		
	}
	
	
	
	@Test(groups={"Acceptance"} )
	
	public void testEbillWithViewLink(){
		
		Log("[Test Case]: Verify Ebill link is available for a payee functionality.");
		
		assert historyPg.verifyEbillLinkOptions();
		
	}
	
	
	
	
	@Test(groups={"Acceptance"} )
	
	public void testEbillStatus(){
		
		Log("[Test Case]: Verify the status of the Ebills for a payee functionality.");
		
		
		assert historyPg.verifyEbillStatus(); 
		
	}
	
	
	
	
	@Test(groups={"Acceptance"} )
	
	public void testAmountIsNullIfNotScheduledIfNotPaid(){
		
		Log("[Test Case]: Verify the status of the Ebills if payment is not scheduled or not paid functionality.");
		
		
		assert historyPg.verifyAmountPaidIsNull();
		
	}
	
	
	

	@Test(groups={"Acceptance"} )

	public void testDeliverOnDateIsNotNullIfStatusIsPaidOrScheduled(){
	
	Log("[Test Case]: Verify that the Deliver On Date is not NULL when status of the Ebills is either Paid or Scheduled.");
		
	assert historyPg.verifyDatePaid();
	
	}
	
	

	
	
	@Test(groups = {"Acceptance"} )
	
	public void testEditCancelLinksForPaidAndScheduledBills(){
		
		Log("[Test Case]: Verify the Edit and Cancel Icons are present only for Paid and Scheduled eBill");
		
		assert historyPg. verifyEditCancelLinksPresent();
	}
	
	
	
	
	@Test(groups={"Acceptance"} , dependsOnMethods = {"testDeletePopUpDisappearsWhenCloseButtonIsPressed"})
	
	public void testVerifyLast7DaysFilter(){
	
		Log("[Test Case]: Verify Last 7 Days filter on My bills page functionality.");
		
				
		historyPg.openFilterMenu();
					
		historyPg.selectLast7Days();
	
		assert historyPg.verifyIfEbillRecordsAreFilteredAsPerDateSelection();
	
			
	}

	



	@Test(groups={"Acceptance"} , dependsOnMethods = {"testVerifyLast7DaysFilter"})

	public void testVerifyLast2MonthsFilter(){
	
		Log("[Test Case]: Verify Last 2 Months filter on My Bills page functionality.");
		
			
		historyPg.openFilterMenu();
	
		historyPg.selectLast2Months();
	
		assert historyPg.verifyIfEbillRecordsAreFilteredAsPerDateSelection();
	
			
	}


	
	

	@Test(groups={"Acceptance"} , dependsOnMethods = {"testVerifyLast2MonthsFilter"})
	
	public void testVerifyLast6MonthsFilter(){
		
		Log("[Test Case]: Verify Last 6 Months filter on My Bills page functionality.");
		
				
		historyPg.openFilterMenu();
	
		historyPg.selectLast6Months();
	
		assert historyPg.verifyIfEbillRecordsAreFilteredAsPerDateSelection();
	
			
	}
	
	
	
	
	
	@Test(groups = {"Regression"} , dependsOnMethods = {"testVerifyLast6MonthsFilter"} )	
	
	public void testAscendingReceivedOnSortOrder()throws ParseException{
		
		Log("[Test Case]: Verify the ascending sort order of the Received On Column.");

		
		
		historyPg.sortReceivedOn();

		assert historyPg.verifyIfReceivedIsInAscendingOrder();
		
	}
	
	
	
	@Test(groups = {"Regression"} , dependsOnMethods = {"testAscendingReceivedOnSortOrder"} )	
	
	public void testDecendingReceivedOnSortOrder()throws ParseException{
		
		Log("[Test Case]: Verify the descending sort order of the Sent On Column.");

		historyPg.sortReceivedOn();

		assert historyPg.verifyIfReceivedIsInDescendingOrder();
		
	}
	
	

	
	@Test(groups = {"Regression"} , dependsOnMethods = {"testDecendingReceivedOnSortOrder"} )
	
	public void testAscendingDueDateOnSortOrder()throws ParseException{
		
		Log("[Test Case]: Verify the ascending sort order of the Sent On Column.");

		historyPg.sortDueDate();

		assert historyPg.verifyIfDueDateIsInAscendingOrder();
		
	}
	
	
	
	
	
	@Test(groups = {"Regression"} , dependsOnMethods = {"testAscendingDueDateOnSortOrder"} )
	
	public void testDecendingDueDateOnSortOrder()throws ParseException{
		
		Log("[Test Case]: Verify the descending sort order of the Sent On Column.");

		historyPg.sortDueDate();

		assert historyPg.verifyIfDueDateIsInDescendingOrder();
		
	}
	
	
	
	@Test(groups = {"Regression"} , dependsOnMethods = {"testDecendingDueDateOnSortOrder"} )

	public void testVerifyAscendingOrderOfPayeeColumn() throws ParseException{

		Log("[Test Case]: Verify the ascending sort order of the Paid To Column.");

		historyPg.sortPayeeName();

		assert historyPg.verifyIfPayeeIsSorted();

	}

	
	
	
	@Test(groups = {"Regression"}, dependsOnMethods = {"testVerifyAscendingOrderOfPayeeColumn"})

	public void testVerifyDescendingOrderOfPayeeColumn() throws ParseException{

		Log("[Test Case]: Verify the descending sort order of the Paid To Column.");

		historyPg.sortPayeeName();

		assert historyPg.verifyIfPayeeIsSortedInReverseOrder();

	}
	

	
	
	@Test(groups = {"Regression"} , dependsOnMethods = {"testVerifyDescendingOrderOfPayeeColumn"} )
	

	public void testVerifyAscendingOrderOfStatusColumn() throws ParseException{

		Log("[Test Case]: Verify the ascending sort order of the Status Column.");

		historyPg.sortStatus();

		assert historyPg.verifyIfStatusIsSorted();

	}
	
	
	
	
	
	@Test(groups = {"Regression"}, dependsOnMethods = {"testVerifyAscendingOrderOfStatusColumn"})

	public void testVerifyDescendingOrderOfStatusColumn() throws ParseException{

		Log("[Test Case]: Verify the descending sort order of the Status Column.");

		historyPg.sortStatus();

		assert historyPg.verifyIfStatusIsSortedInReverseOrder();
		
		}
		

	@Test (groups = "Regression" ) 
    
    public void testDefaultSearchTextValue(){

          Log("[Test Case]: Verify the default search text box value");

          Log("Actual String: " + historyPg.getSearchTextBoxDefaultText());
          
          String defaulttext = historyPg.getSearchTextBoxDefaultText();
          
          assert defaulttext.equalsIgnoreCase("Search Payment History");
          
    }

	

}




