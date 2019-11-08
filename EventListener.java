package com.intuit.bpui_qa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.testng.Reporter;

public class EventListener implements WebDriverEventListener {

	/**
	 * @uml.property  name="lastFindBy"
	 * @uml.associationEnd  
	 */
	private By lastFindBy;
	/**
	 * @uml.property  name="lastSucceufulFindBy"
	 * @uml.associationEnd  
	 */
	private By lastSucceufulFindBy;
	/**
	 * @uml.property  name="originalValue"
	 */
	private String originalValue;
	
	
	public void beforeNavigateTo(String url, WebDriver driver){
		Logger.info("NAVIGATING TO: '"+url+"'");
//		Reporter.log("<br>NAVIGATING TO: '"+url+"'");
	}

	public void beforeChangeValueOf(WebElement element, WebDriver driver){
		originalValue = element.getAttribute("value");
	}

	public void afterChangeValueOf(WebElement element, WebDriver driver){
		Logger.success("CHNAGED VALUE FOR ELEMENT: "+lastFindBy+" FROM '"+originalValue+"' TO '"+element.getAttribute("value")+"'");
//		Reporter.log("<br><span style=\"color:green\">[P]</span> CHNAGED VALUE FOR ELEMENT: "+lastFindBy+" FROM '"+originalValue+"' TO '"+element.getAttribute("value")+"'");
	}

	public void beforeFindBy(By by, WebElement element, WebDriver driver){
		lastFindBy = by;
	}

	public void onException(Throwable error, WebDriver driver){
		if (error.getClass().equals(NoSuchElementException.class)){
			if(lastSucceufulFindBy.equals(lastFindBy)){
				Reporter.log("<br><span style=\"color:orange\">[R]</span>  ERROR: Retrying Element: "+lastFindBy);
			}
				Reporter.log("<br><span style=\"color:red\">[F]</span>  ERROR: Element not found "+lastFindBy);
		} else {
			Reporter.log("<br><span style=\"color:red\">[F]</span>  ERROR: " + error);
		}
	}

	public void beforeNavigateBack(WebDriver driver){}
	public void beforeNavigateForward(WebDriver driver){}
	public void beforeClickOn(WebElement element, WebDriver driver){}
	public void beforeScript(String script, WebDriver driver){
		Reporter.log("**** EXECUTING JAVA SCRIPT. ****");
		Reporter.log(script);
		Reporter.log("******************************************");
	}
	public void afterClickOn(WebElement element, WebDriver driver){}
	public void afterFindBy(By by, WebElement element, WebDriver driver){
		Reporter.log("<br><span style=\"color:green\">[P]</span> ELEMENT FOUND: " + lastFindBy);
		lastSucceufulFindBy = lastFindBy;
	}
	public void afterNavigateBack(WebDriver driver){}
	public void afterNavigateForward(WebDriver driver){}
	public void afterNavigateTo(String url, WebDriver driver){
		Reporter.log("<br><span style=\"color:green\">[P]</span> NAVIGATED TO:'"+url+"'");
	}
	
	public void afterScript(String script, WebDriver driver){
		
	}

}