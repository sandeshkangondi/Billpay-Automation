package com.intuit.bpui_qa;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;

public class ViewScheduledPaymentsTests extends BPUIBasicTest  {
	
	@BeforeClass
	public void startUp() throws InterruptedException{
		Log("Starting View Scheduled Payments Tests tests.");
		Thread.sleep(10000);
	}
	
	@Test
	public void CheckSum() throws InterruptedException{
		Log("Checking sum for a scheduled sum");		
		assertEquals(bpui.getScheduledPaymentsSum(), bpui.getScheduledPaymentTotalFigure());
	}
	
	@Test
	public void CheckHeader() throws InterruptedException{
		Log("Checking header text for a scheduled payment");
		System.out.println(bpui.getScheduledPaymentsHeader());
	assert(bpui.getScheduledPaymentsHeader().equalsIgnoreCase("Scheduled Payments"));
	}
	
	@Test
	public void CheckHelpTexttoClick() throws InterruptedException{
		Log("Checking help text to click");
		assert(bpui.getHelpTextScheduledPayements().contains("Click"));
	}
	
	@Test
	public void CheckHelpTexttoEdit() throws InterruptedException{
		Log("Checking help text to to edit");
		assert(bpui.getHelpTextScheduledPayements().contains("to edit"));
	}
	
	@Test
	public void CheckHelpTexttoCancel() throws InterruptedException{
		Log("Checking help text to to cancel");
		assert(bpui.getHelpTextScheduledPayements().contains("to cancel"));
	}
	
	@Test
	public void CheckByAddPayment() throws InterruptedException{
		Log("Checking if a payment is shown on scheduled payment list when added");
		bpui.deleteScheduledPayment("canberra10", "16.00", "08/29", "yes");
		Thread.sleep(10000);
		bpui.addPayment("canberra10", "16", "08/29/11");
		Thread.sleep(20000);
		assert(bpui.verifyScheduledPaymentDetails("canberra10", "$16.00", "08/29"));
	}
}