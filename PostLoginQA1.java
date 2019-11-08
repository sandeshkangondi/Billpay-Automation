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

public class PostLoginQA1 extends BasePostLoginPage {
	
	/**
	 * @uml.property  name="navigateBillPayUI"
	 * @uml.associationEnd  readOnly="true"
	 */
	@FindBy(name="body")
	private WebElement navigateBillPayUI;
	
	public PostLoginQA1(WebDriver driver){
		super(driver);
	}
	
	@Override
	public void navigateToBillPayUI(){
        backToBody();
        List<WebElement> framesets = driver.findElements(By.tagName("frameset"));
        driver.switchTo().frame(framesets.get(1).findElement(By.name("body")));
		driver.findElement(By.linkText("Bill Payment")).click();
		waitForAjaxCallsToComplete(JsToolKit.EXTJS);
	}

	@Override
	public List<WebElement> pageElementsToWait() {
		expectedElements.add(navigateBillPayUI);
		return expectedElements;
	}
	
	@Override
	public String setPageName(){
		pageName = "Financial Institution Home Page For QA1";
		return pageName;
	}

}
