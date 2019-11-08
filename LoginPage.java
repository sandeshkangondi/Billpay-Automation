package com.intuit.bpui_qa.Pages;

import static org.openqa.selenium.lift.Finders.textbox;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.intuit.bpui_qa.LifeStyle;
import com.intuit.bpui_qa.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.lift.Finders;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import static org.openqa.selenium.lift.Finders.*;
import static org.openqa.selenium.lift.Matchers.*;

import static org.hamcrest.Matchers.*;

public class LoginPage extends WebPage{
	
//	@FindBy(name="userNumber")
	private WebElement userNumber;
	
//	@FindBy(name="password")
	private WebElement password;
	
//	@FindBy(name="OK")
	private WebElement OK;
	
	
	
	public LoginPage(WebDriver driver){
		super(driver);

	}
	
	public void login(){
		String userName = flag.getFlag("userid");
		String pwd = flag.getFlag("pwd");
		
		System.out.println();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH mm ss");

		// Convert the user input into a date object
		Date time1 = new Date();
		
		long l1 = time1.getTime();
		System.out.println("START TIME: " + l1);
//		userNumber.sendKeys(userName);
		typeIntoFirstTextBox(userName);
		typeIntoSecondTextBox(pwd);
//		password.sendKeys(pwd);
		
//		OK.click();
		clickOnButtonWithText("Login");
		Date time2 = new Date();
		long l2 = time2.getTime();
		System.out.println("END TIME: " + l2);
		System.out.println("Difference: " + (l2 - l1)/1000);
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		clickOnButtonWithText("Login");
//		testStyle.clickOnButtonWithText("Login");
//		try {
//			Thread.sleep(15000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public List<WebElement> pageElementsToWait() {
		// TODO Auto-generated method stub
		expectedElements.add(userNumber);
		expectedElements.add(password);
		expectedElements.add(OK);
		return expectedElements;
	}
	
	@Override
	public String setPageName(){
		pageName = "Login Page";
		return pageName;
	}
	
	

}
