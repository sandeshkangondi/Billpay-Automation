package com.intuit.bpui_qa;

import java.text.ParseException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


@Test(groups = { "US1203" } )

public class PayFieldSuggestionsTests extends BPUIBasicTest{
	
	@BeforeClass(alwaysRun=true)

	public void startUp(){

		Log("Starting Payee Tile Pay Field Enhancement Tests.");
		
		//performStartup();
	}
	
	
	
	@Test(groups={"Acceptance"} ,  dataProvider="amountSuggestionsData")

	public void testCountOfAmountSuggestionsDisplayed(String payeeName , int expectedAmountSuggestionCount) throws InterruptedException{

		Log("[Test Case]: Verify the payment history list count getting displayed on clicking in amount field.");
		
		Thread.sleep(5000);
		
		bpui.clickOnFirstPayeeAmountField(payeeName);
		
		Thread.sleep(5000);	
		
		assert bpui.getAmountSuggestionListSize() == expectedAmountSuggestionCount;
		
		Thread.sleep(5000);	
	
	}
	
	
	@Test(groups={"Acceptance"} )
	
	public void testEBillAmountSuggestionDisplayed() throws InterruptedException{
		
		Log("[Test Case]: Verify the Ebill related info is displyed at the top in the list.");
		
		Thread.sleep(5000);
		
		bpui.clickOnFirstPayeeAmountField("threeFactoriLodf");
		
		Thread.sleep(5000);	
		
		assert bpui.checkEbillSuggestions();
	
		Thread.sleep(5000);	
		
		
	}
	
	

	
	@Test(groups={"Acceptance"} ,  dataProvider="payeesList")
	
	
	public void testAmountsSuggestedAreInOrderOfDates(String payeeName) throws InterruptedException, ParseException{

		Log("[Test Case]: Verify that the most recent payment is displayed at the top.");
		
		Thread.sleep(5000);
		
		bpui.clickOnFirstPayeeAmountField(payeeName);
	
	
		Thread.sleep(5000);		
		
		assert bpui.c();
		
		Thread.sleep(5000);	

	}
	
	
	
	@Test(groups={"Acceptance"} ,  dataProvider="payeesList")
	
	public void testAmountIsSelectedFromSuggestions(String payeeName) throws InterruptedException{
		

		Log("[Test Case]: Verify user is able to select amount from Payment Suggestion list.");
		
		Thread.sleep(5000);
		
		bpui.clickOnFirstPayeeAmountField(payeeName);


		Thread.sleep(5000);	
		
		bpui.verifyAmountIsSelectedFromList();
		
		Thread.sleep(5000);	
		
			
	}
	
	
	

	
	
	@DataProvider(name="amountSuggestionsData")

	public Object[][] getEbillsNotificationOptions(){

		return new Object[][] {

				 { "dattu" , 0 } , 
				 { "known payee test NN" , 1 } ,
				 { "Testpayee1" , 2 } ,
				 { "abcd1234" , 3 } ,
				 { "threeFactoriLodf" , 5 } ,
			};

	}
	
	
	@DataProvider(name="payeesList")

	public Object[][] getPayeesNames(){

		return new Object[][] {

				 { "dattu" } , 
				 { "known payee test NN" } ,
				 { "Testpayee1"  } ,
				 { "abcd1234" } ,
				 { "threeFactoriLodf" } ,
			};

	}
}
