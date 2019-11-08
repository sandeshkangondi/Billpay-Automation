package com.intuit.bpui_qa.Pages;

import java.util.List;

//import com.intuit.bpui_qa.Site;
import com.intuit.bpui_qa.WebPage;
import com.intuit.bpui_qa.utils.ReadFlags;
import com.intuit.bpui_qa.utils.WaitForElement;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import static org.openqa.selenium.lift.Finders.*;
import static org.openqa.selenium.lift.Matchers.*;

import static org.hamcrest.Matchers.*;

public class BasePostLoginPage extends WebPage<BasePostLoginPage> {
	
	
	public static ReadFlags flag = new ReadFlags();

	private BasePostLoginPage pgQA;
	private BasePostLoginPage pgDEV;
	private BasePostLoginPage pgQA1;
	private BasePostLoginPage actualPageObj;
	
	public BasePostLoginPage(WebDriver driver){
		super(driver);
		
	}
	
	public void navigateToBillPayUI(){}

	@Override
	public List<WebElement> pageElementsToWait() {

		return null;
	}
	
	public void pageElementsToWaitForChild(List<WebElement> elements){
		expectedElements = elements;
	}

	@Override
	public String setPageName() {
		pageName = "Base Page Object";
		return pageName;
	}
	
	/*
	 * This is the method which can be used to
	 * retrieve the ACTUAL page object.
	 */
	public BasePostLoginPage getActualABPage(){
		if(flag.getFlag("env").equalsIgnoreCase("qa")){
			switchFrame("primary");
			pgQA = PageFactory.initElements(driver, PostLoginQA.class);
			System.out.println("!!!!! ----->>>>>> " + pgQA.isPageLoaded());
			return pgQA;
		}
		else if(flag.getFlag("env").equalsIgnoreCase("qa1")){
			pgQA1 = PageFactory.initElements(driver, PostLoginQA1.class);
			return pgQA1;
		}
		else if(flag.getFlag("env").equalsIgnoreCase("dev")){
			pgDEV = PageFactory.initElements(driver, PostLoginDev.class);	
		    return pgDEV;
		}
		return null;
	}



}
