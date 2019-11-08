package com.intuit.bpui_qa.Pages;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.bpui_qa.WebPage;

public class RemindersAlertsPage extends WebPage{
	
	@FindBy(linkText="Back to main page")

	private WebElement backToMainPage;

	public RemindersAlertsPage(WebDriver driver) {

		super(driver);

		System.out.println("In Reminders and Alerts Notification Page...........");

		backToBody();

		switchFrame("body");
	}

	
	public boolean verifyDefaultHeader(){
		
		String headerText = driver.findElement(By.className("global-alerts-header-div")).findElements(By.tagName("span")).get(0).getText();
		
		return headerText.equals("Reminders and Alerts");
	}
	
	
	
	public boolean verifyEmailText(){
		
		String emailHeaderText = driver.findElement(By.className("pageHeaderRight")).findElement(By.id("howDoIChangeId")).findElement(By.xpath("..")).getText();
		
		return emailHeaderText.equals("Emails are sent to: name@domain.com How do I change this?");
	}
	
	
	
	
	
	
	
	
	
	@Override
	public List pageElementsToWait() {
		expectedElements.add(backToMainPage);

		return expectedElements;
	}



	@Override
	public String setPageName() {
		pageName = "Reminders and Alerts Notification Page";

		return pageName;
	}


	public void goBackToBpUiPage() {
		
		backToMainPage.click();
		
	}


	public void goBackToBpUiPageTop() {
		
		driver.findElement(By.linkText("Back to main page")).click();
		
	}


	public void goBackToBpUiPageBottom() {
		
	
		driver.findElement(By.className("floatLeft")).findElement(By.linkText("Back to main page")).click();
		
	}

	
	public void clickOnFirstNotificationOption(){
		clickOnCheckBoxWithLabel("Notification - Your {payee nickname} e-bill has arrived");

	}


	public void clickBackToMainPageLinkAtTop(){ 
		
		clickOnButtonWithTextVal("Back to main page");

	}
	
	
	public void clickOnCheckBox(String notificationLabelName){
		
		clickOnCheckBoxWithLabel(notificationLabelName);
		
	}
	
	
	public void clickOnButton(String buttonText){

		clickOnButtonWithTextVal(buttonText);

	}
	
	
	public boolean verifySuccessMessage(){
		
		String successMsg = driver.findElement(By.id("statusText")).getText();
		return successMsg.equals("Changes saved");
	}
	
	
	public boolean verifyInactiveCheckBoxForEbills(String notification){
		
		
		WebElement notificationLabel = getLabelElementForEbills(notification);
		
		WebElement checkbox = notificationLabel.findElement(By.xpath("..")).findElement(By.tagName("input"));

		
		return !checkbox.isEnabled();
		
	}
	
	public WebElement getLabelElementForEbills(String label){
		List<WebElement> notificationLabels = driver.findElement(By.id("ebills-main-div")).findElements(By.className("checkBoxText"));
		Iterator<WebElement> element = notificationLabels.iterator();
		WebElement elmT;
		while(element.hasNext()){
			elmT = element.next();
			
			if(elmT.getText().equals(label))
				return elmT;
				
		}
		return null;
		
	}
	
	
	public boolean verifyInactiveCheckBoxForPayments(String notification){
		
		
		WebElement notificationLabel = getLabelElementForPayments(notification);
		
		WebElement checkbox = notificationLabel.findElement(By.xpath("..")).findElement(By.tagName("input"));

		
		return !checkbox.isEnabled();
		
	}
	
	public WebElement getLabelElementForPayments(String label){
		List<WebElement> notificationLabels = driver.findElement(By.id("payments-main-div")).findElements(By.className("checkBoxText"));
		Iterator<WebElement> element = notificationLabels.iterator();
		WebElement elmT;
		while(element.hasNext()){
			elmT = element.next();
			
			if(elmT.getText().equals(label))
				return elmT;
				
		}
		return null;
		
	}

	
	public boolean verifyInactiveCheckBoxForFundingAccounts(String notification){
		
		
		WebElement notificationLabel = getLabelElementForFundingAccounts(notification);
		
		WebElement checkbox = notificationLabel.findElement(By.xpath("..")).findElement(By.tagName("input"));

		
		return !checkbox.isEnabled();
		
	}
	
	public WebElement getLabelElementForFundingAccounts(String label){
		List<WebElement> notificationLabels = driver.findElement(By.id("accounts-main-div")).findElements(By.className("checkBoxText"));
		Iterator<WebElement> element = notificationLabels.iterator();
		WebElement elmT;
		while(element.hasNext()){
			elmT = element.next();
			
			if(elmT.getText().equals(label))
				return elmT;
				
		}
		return null;
		
	}
	
	
	
	
}
