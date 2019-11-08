package com.intuit.bpui_qa;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.lift.HamcrestWebDriverTestCase;
import org.openqa.selenium.lift.TestContext;
import org.openqa.selenium.lift.WebDriverTestContext;
import org.openqa.selenium.lift.find.Finder;
import org.openqa.selenium.lift.find.InputFinder;

import static org.openqa.selenium.lift.Finders.*;
import static org.openqa.selenium.lift.Matchers.*;

import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.Clock;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Sleeper;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;
//import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Reporter;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com.intuit.bpui_qa.utils.JsToolKit;
import com.intuit.bpui_qa.utils.ReadFlags;

public abstract class WebPage<T>{

	protected WebPage parent;
	protected WebDriver driver;
	protected WebDriverBackedSelenium wbDriverSel;
	protected JavascriptExecutor js;
	protected Wait<WebDriver> wait;
	protected ReadFlags flag;
	protected List<WebElement> expectedElements = new ArrayList<WebElement>();
	protected WebElement expectedElement;
	protected int DRIVER_WAIT = 120; // 30 seconds
	protected final long explicitWait = 120;
	protected String pageName;
//	private static final int WAIT_FOR_ELEMENT_PAUSE_LENGTH = 50;
	private boolean isPageLoaded = true;
	protected String TIME_OUT_WAIT_CONDITION = "15000";
	private LifeStyle style = LifeStyle.getAvailableLifeStyle();
	
	
	private final long timeoutInMilliseconds = 3000;
    private static final int WAIT_FOR_ELEMENT_PAUSE_LENGTH = 250;
    private final Sleeper sleeper = Sleeper.SYSTEM_SLEEPER;
    private final Clock webdriverClock = new SystemClock();
    
    public WebDriverTestContext context;
    public DriverProvider drvProvider;
    
    public LifeStyle easy;
//    private JavascriptExecutor javaScriptExecutorFacade;
//    private boolean isPageLoaded = false;
	
	public WebPage(WebDriver driver){

		this.driver = driver;
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE CREATING SELBACKED DRIVER %%%%%%%%%%%%%%%%%%%%%");
		
		
		
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE CREATING JAVASCRIPT EXECUTOR %%%%%%%%%%%%%%%%%%%%%");
		js = (JavascriptExecutor) driver;
		
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE CREATING WEBDRIVER WAIT %%%%%%%%%%%%%%%%%%%%%");
		wait = new WebDriverWait(driver, 120);
		
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE READING FLAGS %%%%%%%%%%%%%%%%%%%%%");
		flag = new ReadFlags();
		if (flag.getFlag("env").equalsIgnoreCase("QA1"))
				this.wbDriverSel = new WebDriverBackedSelenium(driver,"https://www.ibdev9.com/onlineserv/HB/Signon.cgi");
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE CREATING ELEMENTLOCATORFACTORY %%%%%%%%%%%%%%%%%%%%%");
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, DRIVER_WAIT);
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE USING ELEMENTLOCATORFACTORY %%%%%%%%%%%%%%%%%%%%%");
//		AjaxEnabledPageFactory.initializePage(driver, this.getClass());
		PageFactory.initElements(finder, this);
		
		
		
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
//		@SuppressWarnings("unchecked")
//		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
//				.withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
//				.ignoring(NoSuchElementException.class);
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE STARTING TO WAIT FOR ALL ELEMENTS %%%%%%%%%%%%%%%%%%%%%");
		
//		waitUntilAllElementsAreLoaded();
//		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%% IN BASE PAGE DONE WAITING FOR ALL ELEMENTS %%%%%%%%%%%%%%%%%%%%%");
//		setPageLoaded(true);
		Log("<br>[ON PAGE]: " + getCurrentPageName());

	}
	
	  private HamcrestWebDriverTestCase createTestCase() {
		    HamcrestWebDriverTestCase testcase = new HamcrestWebDriverTestCase() {
		      @Override
		      protected WebDriver createDriver() {
		        return driver;
		      }
		    };
		    return testcase;
		  }

	public WebPage(WebDriverBackedSelenium wbDriverSel){
		this.wbDriverSel = wbDriverSel;
	}
	
	public WebPage(WebDriver driver, WebDriverBackedSelenium wbDriverSel){
		this.driver = driver;
		this.wbDriverSel = wbDriverSel;
		js = (JavascriptExecutor) driver;
		wait = new WebDriverWait(driver, 4000);

	}
	
	/*
	 * The following method gives the ability to swithFrames.
	 */
	public void switchFrame(String frameId){
		this.driver.switchTo().frame(frameId);
	}
	
	/*
	 * The following method can be used to go back to the body.
	 */
	public void backToBody(){
		this.driver.switchTo().defaultContent();
	}
	
	public ExpectedCondition<WebElement> visibilityOfElementLocated(final By locator) {
		  return new ExpectedCondition<WebElement>() {
		    public WebElement apply(WebDriver driver) {
		      WebElement element =
		          (WebElement) driver.findElement(locator);
		      return element.isDisplayed() ? element : null;
		    }
		  };

	}
	
	public WebElement waitForAnElement(final By by){
		return (WebElement) (wait.until(new ExpectedCondition<WebElement>() {
			@Override
			public WebElement apply(WebDriver d){
				return d.findElement(by);
			}
		}));
	}
	
	public ExpectedCondition<WebElement> visibilityOfElementLocated(final WebElement expctedElement) {
		  return new ExpectedCondition<WebElement>() {
		    public WebElement apply(WebDriver driver) {
		      WebElement element =
		          (WebElement) expctedElement;
		      return element.isDisplayed() ? element : null;
		    }
		  };

	}
	
	public void visibilityOfElementsLocated(List<WebElement> expectedElements){
//		System.out.println("%%%%%%%%%%%% IN visibilityOfElementsLocated STEP1 %%%%%%%%%%%%%%");
		Iterator<WebElement> webElement = expectedElements.iterator();
//		System.out.println("%%%%%%%%%%%% IN visibilityOfElementsLocated STEP2 %%%%%%%%%%%%%%");
		WebElement tempElm;
//		System.out.println("%%%%%%%%%%%% IN visibilityOfElementsLocated STEP3 %%%%%%%%%%%%%%");
		while(webElement.hasNext()){
//			System.out.println("%%%%%%%%%%%% IN visibilityOfElementsLocated WHILE LOOP STEP4-1 %%%%%%%%%%%%%%");
			tempElm = webElement.next();
//			System.out.println("%%%%%%%%%%%% IN visibilityOfElementsLocated WHILE LOOP STEP4-2 %%%%%%%%%%%%%%");
//			System.out.println("!!!!!!!!!!!!!!!!! Is Element Displayed...............: " + tempElm);
//			if(visibilityOfElementLocated(webElement.next()) == null) {this.isPageLoaded = false; this.setPageLoaded(false); break;};
//			System.out.println("%%%%%%%%%%%% IN visibilityOfElementsLocated WHILE LOOP STEP4-3 %%%%%%%%%%%%%%");
			if(waitUntilVisible(tempElm) == false) {this.isPageLoaded = false; this.setPageLoaded(false); break;};
		}
	}
	
	
	private ExpectedCondition<Boolean> elementIsEnabled(final WebElement webElement) {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return (webElement.isEnabled());
            }
        };
    }
	
	public Wait<WebDriver> waitForCondition() {
        return new FluentWait<WebDriver>(driver)
                .withTimeout(5, TimeUnit.SECONDS)
                .pollingEvery(1, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class, NoSuchFrameException.class);
    }
	
	
	public boolean isElementVisible(final By locator){
		try{
			driver.findElement(locator);
		}catch(NoSuchElementException e){
			return false;
		}
		
		return true;
	}

	public boolean isElementVisible(WebElement expectedElement){
		WebElement element = (WebElement) expectedElement;
		
		return element.isDisplayed() ? true : false;
	}
	
	
	private void waitUntilElementAvailable(WebElement webElement) {
        waitUntilEnabled(webElement);
    }
	
	public boolean waitUntilEnabled(WebElement webElement) {
        try {
//        	System.out.println("!!!!! Starting Waiting..........");
            waitForCondition().until(elementIsEnabled(webElement));
            return true;
        } catch (TimeoutException timeout) {
            throw new ElementNotVisibleException("Expected enabled element was not enabled", timeout);
        }
    }
	
	

	
	 public boolean waitUntilVisible(WebElement webElement) {
	        try {
//	        	System.out.println("!!!!! In Wait Until Visible..... " + webElement.getAttribute("class"));
	            waitForCondition().until(elementIsDisplayed(webElement));
//	            System.out.println("!!!!! Done waiting......");
	        } catch (TimeoutException timeout) {
	            throwErrorWithCauseIfPresent(timeout, timeout.getMessage());
	        }
	        return true;
	    }
	
	 
	 private ExpectedCondition<Boolean> elementIsDisplayed(final WebElement webElement) {
	        return new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return (webElement.isDisplayed());
	            }
	        };
	    }
    private void throwErrorWithCauseIfPresent(TimeoutException timeout,
			String message) {
		// TODO Auto-generated method stub
		
	}

	public void waitForElementsToDisappear(final By byElementCriteria) {
        long end = System.currentTimeMillis() + explicitWait;
        while (System.currentTimeMillis() < end) {
            if (!elementIsDisplayed(byElementCriteria)) {
                break;
            }
            waitABit(WAIT_FOR_ELEMENT_PAUSE_LENGTH);
        }
        if (elementIsDisplayed(byElementCriteria)) {
            throw new ElementNotVisibleException("Element should not be displayed displayed: "
                    + byElementCriteria);
        }
    }
    
    protected void waitABit(final long timeInMilliseconds) {
        try {
            Thread.sleep(timeInMilliseconds);
        } catch (InterruptedException e) {
            Log("[ERROR]: Wait Interrupted");
        }
    }
    
    public boolean elementIsDisplayed(final By byElementCriteria) {
        boolean isDisplayed = false;
            List<WebElement> matchingElements = driver.findElements(byElementCriteria);
            if (matchingElements.isEmpty()) {
                return false;
            }            
            WebElement renderedElement  = (WebElement) matchingElements.get(0);
            isDisplayed = renderedElement.isDisplayed();
        return isDisplayed;
    }

    public void waitForAjaxCallsToComplete(String toolKitJs){
    	this.wbDriverSel.waitForCondition(new StringBuilder("selenium.browserbot.getCurrentWindow().").append(toolKitJs).toString(), this.TIME_OUT_WAIT_CONDITION);
    }

		 
	public final void waitUntilAllElementsAreLoaded() {
		try{
		if(pageElementsToWait() != null){
			visibilityOfElementsLocated(pageElementsToWait());
		}
		}catch(NoSuchElementException elmNotFound){
			this.setPageLoaded(false);
		}catch(NoSuchFrameException elmNotVisible){
			this.setPageLoaded(false);
		}
	}
	
	public String getCurrentPageName(){
		return setPageName();
	}
	public abstract List<WebElement> pageElementsToWait();
	
	public void Log(String statement) {
		Reporter.log(statement);
	}

	public abstract String setPageName();

	/**
	 * @return
	 * @uml.property  name="isPageLoaded"
	 */
	public boolean isPageLoaded() {
		return isPageLoaded;
	}

	public void setPageLoaded(boolean isPageLoaded) {
		this.isPageLoaded = isPageLoaded;
	}
	

	public void clickOnButtonWithText(String buttonText){
		style.clickOnButtonWithText(buttonText);
	}

	public void clickLinkWithText(String linkText){
		style.clickLinkWithText(linkText);
	}
	
	public void clickOnTextBoxWithValue(String value){
		style.clickOnTextBoxWithValue(value);
	}

	public void typeIntoSecondTextBox(String text){
		style.typeIntoSecondTextBox(text);
	}
	
	public void typeIntoFirstTextBox(String text){
		style.typeIntoFirstTextBox(text);
	}
	

	public void typeIntoFirstPayeeTextBox(String text, String id){
		style.typeIntoFirstTextBoxWithValueAndParent(text, id, "mm/dd/yy");
	}
	
	
	public void typeIntoFirstTextBoxWithValueUnderId(String text, String id, String value){
		style.typeIntoFirstTextBoxWithValueAndParent(text, id, value);
	}
	
	public void typeIntoFirstTextBoxWithValue(String text, String value){
		style.typeIntoFirstTextBoxWithValue(text, value);
	}
	
	public void typeIntoTextBoxWithValue(String text, String value, int index){
		style.typeIntoTextBoxWithValue(text, value, index);
	}
	
	public void typeIntoTextBoxWithValue(String text, String value, int index, String parentId){
		style.typeIntoTextBoxWithValue(text, parentId, value, index);
	}
	
	public void clickOnRadioButtonWithLabelUnderParentId(String label, String parentId){
		style.clickLabelUnderParent(label, parentId);
	}
	
	public void clickOnRadioButtonWithLabelUnderParentId(String label, String parentId, int index){
		style.clickLabelUnderParent(label, parentId, index);
	}
	
	public void clickOnCheckBoxWithLabelUnderParentId(String label, String parentId){
		style.clickLabelUnderParent(label, parentId);
	}
	
	public void clickOnCheckBoxWithLabel(String label){
		style.clickLabel(label);
	}		public void clickOnButtonWithTextVal(String buttonText){		style.clickOnButtonWithTextVal(buttonText);	}		public void clickOnTextBoxWithValueUnderID(String id , String value){		style.clickOnTextBoxWithValue(id , value);	}
}
