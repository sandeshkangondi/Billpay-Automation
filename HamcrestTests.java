package com.intuit.bpui_qa;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/*
 * This is an experimental test class, no functionality in this.
 */
public class HamcrestTests extends BPUIBasicTest {
	
	@BeforeClass
	public void startUp(){
		Log("Starting manage funding account tests.");
	}
	
	@Test(groups={"hamcrest"})
	public void verifyHamcrestApi() throws InterruptedException{
		Log("[STEP]: Verifying Hamcrest.");
		Thread.sleep(20000);
		
		bpui.openPayeeOptionsTray("Discover Credit Cards");
		Thread.sleep(2000);
		bpui.goToAutomaticPaymentTab("Discover Credit Cards");
		Thread.sleep(2000);
		bpui.clickOnFirstRadioButton();

		System.out.println("DONE ************************** ");
		Thread.sleep(5000);
		assert false;
	}

}
