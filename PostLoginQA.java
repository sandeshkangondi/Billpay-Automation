package com.intuit.bpui_qa.Pages;

import java.util.List;

//import com.intuit.bpui_qa.Site;
import com.intuit.bpui_qa.WebPage;
import com.intuit.bpui_qa.utils.JsToolKit;
import com.intuit.bpui_qa.utils.WaitForElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

public class PostLoginQA extends BasePostLoginPage {
	
	@FindBy(name="nav_primary1")
	private WebElement navigateBillPayUI;
	
	public PostLoginQA(WebDriver driver){
		super(driver);
	}
	
	
	public void navigateToBillPayUI(){
		switchFrame("primary");
		navigateBillPayUI.click();
		backToBody();
		switchFrame("body");
		waitForAjaxCallsToComplete(JsToolKit.EXTJS);
	}

	@Override
	public List<WebElement> pageElementsToWait() {
		expectedElements.add(navigateBillPayUI);
		return expectedElements;
	}
	
	@Override
	public String setPageName(){
		pageName = "Financial Institution Home Page For QA";
		return pageName;
	}

}
