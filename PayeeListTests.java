package com.intuit.bpui_qa;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PayeeListTests extends BPUIBasicTest{
	
	@BeforeClass
	public void startUp(){
		Log("Starting payee list tests.");
	}
	
	@Test
	public void testVerifySortingIsDiaplayed(){
		Log("[Test Case]: By default the BPUI Page, should show sorting option on top.");
		Log("Step: Verifying the sorting is displayed on top.");
		assert bpui.verifySortingExists();
	}

	@Test(dependsOnMethods="testVerifySortingIsDiaplayed")
	public void testVerifyPayeesCount(){
		Log("[Test Case]: Existing payess should be displayed.");
		Log("Step: Verifying payess should be shown.");
		assert bpui.getPayeeCount() > 0;
	}
	
	@Test
	public void testVerifySortingLabel(){
		Log("[Test Case]: Sort by label should be displayed.");
		Log("Step: Verifying whether 'Sort by' label is displayed or not.");
		assert bpui.getSortbyLabel().equalsIgnoreCase("Sort by:");
	}
	
	@Test
	public void testVerifySoritingValue(){
		Log("[Test Case]: Default option for Sorting should be 'Name (ascending)'.");
		Log("Step: Verifying the defailt sort options.");
		assert bpui.getSelectedSortOption().equalsIgnoreCase("Name (ascending)");
	}
	
	@Test(dependsOnMethods="testVerifyPayeesCount")
	public void testVerifyPayessAreSorted(){
		Log("[Test Case]: Verify default payees should be sorted by ascending order.");
		Log("Step: Checking the sort order of the payees.");
		assert bpui.verifyIfPayeesAreSortedByDefault();
	}
}
