package com.intuit.bpui_qa;


import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.RemindersAlertsPage;


@Test(groups = { "US54" } )
public class RemindersAlertsNotificationTests extends BPUIBasicTest {
	
	RemindersAlertsPage notificationPg;	
	BPUIPage bpui;
	
	@BeforeClass(alwaysRun=true)

	public void startUp(){

		Log("Starting Reminders and Alerts Page tests.");
		
		notificationPg = Site.goToReminderAlertNotificationPage();	
	}
	
		
	
	@Test (groups = {"Navigation"} )
	
	public void testBackToMainPageLinkTop() throws InterruptedException
	{
		Log("[STEP]: Verifying 'Back to main page' link.");
		
		bpui = Site.goBackToBpuiPageFromNotificationPage();
		
		Thread.sleep(5000);
		
		Log("[STEP] : In BPUI page");
		
		notificationPg = Site.goToReminderAlertNotificationPage();		
		
		assert notificationPg.isPageLoaded();

	}
	
	
	
		
	@Test(groups = {"Regression"} )
	
	public void testHeaderText(){
		
		Log("[Test Case]: Verify the default header on the Reminder and Alerts notification page.");
					
		
		assert notificationPg.verifyDefaultHeader();
	}

	
	
	@Test(groups = {"Regression"} )
	
	public void testEmailAddress(){
		
		Log("[Test Case]: Verify the email address of the User is displayed on the Reminder and Alerts notification page.");
					
		
		assert notificationPg.verifyEmailText();
	}
	
	
	
		
	
	@Test(groups = {"Acceptance"} ,dataProvider="disabledEbillsNotifications")
	
	public void testGrayedOutCheckBoxForEbillsNotifications(String notification) throws InterruptedException{
		
		Log("Verifying the inactive check box for [ " + notification + "  ] on the notification page...");
		
		Thread.sleep(5000);	
		
		assert notificationPg.verifyInactiveCheckBoxForEbills(notification);		
		
		
	}
	
	
	
	@Test(groups = {"Acceptance"} ,dataProvider="disabledPaymentsNotifications")
	
	public void testGrayedOutCheckBoxForPaymentsNotifications(String notification) throws InterruptedException{
		
		Log("Verifying the inactive check box for [ " + notification + "  ] on the notification page...");
		
		Thread.sleep(5000);
		
		assert notificationPg.verifyInactiveCheckBoxForPayments(notification);		
		
		
	}
	
	
	
	@Test(groups = {"Acceptance"} ,dataProvider="disabledEbillsNotificationsForFundingAccounts")
	
	public void testGrayedOutCheckBoxForFundingAccountsNotifications(String notification) throws InterruptedException{
		
		Log("Verifying the inactive check box for [ " + notification + "  ] on the notification page...");
		
		Thread.sleep(5000);
		
		assert notificationPg.verifyInactiveCheckBoxForFundingAccounts(notification);		
		
		
	}
	
	
		
	
	@Test (groups = {"Navigation"} )
	
	public void testBackToMainPageAtTop()throws InterruptedException{		

		Log("[STEP]: Verifying Back to main page link...");

				
		notificationPg.clickBackToMainPageLinkAtTop();
			

		System.out.println("DONE ************************** ");

		Thread.sleep(5000);		
		
	}
	
	
	
	@Test(groups = {"Acceptance"} ,  dataProvider="securityNotifications")
	
	public void testSaveSecurityNotifications(String notification )throws InterruptedException{		

		Log("[STEP]: Verifying clicking the checkbox for Security notifications and saving the changes...");
		
		Thread.sleep(5000);

		notificationPg.clickOnCheckBox(notification);
		
		Thread.sleep(5000);
		
		notificationPg.clickOnButton("Save");
		
		
		Thread.sleep(10000);		
		
		assert notificationPg.verifySuccessMessage();
		
	}
	
	
	
	@Test(groups = {"Acceptance"} ,dataProvider="eBillsNotifications")
	
	public void testSaveEbillsNotifications(String notification )throws InterruptedException{		

		Log("[STEP]: Verifying clicking the checkbox for Ebills notifications and saving the changes...");
		
		Thread.sleep(5000);

		notificationPg.clickOnCheckBox(notification);
		
		Thread.sleep(5000);
		
		notificationPg.clickOnButton("Save");
		

		Thread.sleep(10000);		
		
		assert notificationPg.verifySuccessMessage();
		
	}

	
	
	
	
	@Test(groups = {"Acceptance"} ,dataProvider="paymentsNotifications")
	
	public void testSavePaymentsNotifications(String notification )throws InterruptedException{		

		Log("[STEP]: Verifying clicking the checkbox for Payments notifications and saving the changes...");
		
		Thread.sleep(5000);

		notificationPg.clickOnCheckBox(notification);
		
		Thread.sleep(5000);
		
		notificationPg.clickOnButton("Save");
		
		
		Thread.sleep(10000);		
		
		assert notificationPg.verifySuccessMessage();
		
	}
	
	
	
	
	
	@DataProvider(name="securityNotifications")

	public Object[][] getSecurityNotificationOptions(){

		return new Object[][] {

			 { "Notification - {Payee nickname} added" } , 
			 {"Notification - {Payee nickname} edited"	},

		};

	}
	
	
	@DataProvider(name="eBillsNotifications")

	public Object[][] getEbillsNotificationOptions(){

		return new Object[][] {

				 { "Notification - Your {payee nickname} e-bill payment is due"     } , 
				 { "Notification - Your {payee nickname} e-bill was not received"	},
				 { "Action required - Automatic payment to {payee nickname} not made based on your rule"	},
				 { "Notification - E-bill for {payee nickname} is now set up"	},
				 { "Action required - {Payee nickname} e-bill not delivered; verify login information"	},
				 { "Action required - {Payee nickname} e-bill not delivered; verify account number"	},

			};

	}
	
	
	
	@DataProvider(name="paymentsNotifications")

	public Object[][] getPaymentsNotificationOptions(){

		return new Object[][] {

				 { "Notification - Your payment to {payee nickname} was made"     } , 
				 { "Notification - Automatic payment for {payee nickname} scheduled"	},
				 
			};

	}
	
	
	
	
	
	@DataProvider(name="disabledEbillsNotifications")

	public Object[][] getDisabledNotificationForEbills(){

		return new Object[][] {

				 { "Notification - Your {payee nickname} e-bill has arrived"     } , 
				 { "Notification - E-bill setup for {payee nickname} was unsuccessful"	},
				 { "Action required - Additional steps required for your e-bill setup for {payee nickname}" },
				 { "Notification - {Payee nickname} e-bill has been discontinued" },
				
			};

	}
	
	
	
	
	@DataProvider(name="disabledPaymentsNotifications")

	public Object[][] getDisabledNotificationForPayments(){

		return new Object[][] {

				 { "Notification - Your automatic payment to {payee nickname} was canceled" },
				 { "Notification - This week's payments" },
				 { "Notification - Payment to {payee nickname} unsuccessful" },
				 { "Notification - Automatic payment rule for {payee nickname } about to expire" },
			};

	}
	
	
	
	
	
	@DataProvider(name="disabledEbillsNotificationsForFundingAccounts")

	public Object[][] getDisabledNotificationForFundingAccounts(){

		return new Object[][] {

				 { "Action required - Your {funding account nickname} has been blocked" },
				 { "Notification - {Funding account nickname} not approved" },
			};

	}
	

	

}
