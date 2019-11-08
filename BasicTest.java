package com.intuit.bpui_qa;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.lift.HamcrestWebDriverTestCase;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;
import com.intuit.bpui_qa.utils.ReadFlags;
import org.uncommons.reportng.HTMLReporter;
import static org.openqa.selenium.lift.Matchers.*;
import static org.openqa.selenium.lift.Finders.*;

import org.hamcrest.Matchers;

//@Listeners( { com.intuit.bpui_qa.TestListener.class,
//		org.uncommons.reportng.HTMLReporter.class,
//		org.uncommons.reportng.JUnitXMLReporter.class })
public class BasicTest {


	public WebDriver driver;
	public WebDriverBackedSelenium wbSel;
	public static Wait wait;
	protected JavascriptExecutor js;
	public DriverProvider drvProvider;
	public ReadFlags readFlag;
	private HTMLReporter reportNGUtil = new HTMLReporter();
	

	/*
	 * The following method is used to perform startup tasks like instantiating
	 * the webdriver, opening the test URL etc.
	 */
	public void performStartup() {
		Logger.info("Performing startup");
		drvProvider = DriverProvider.createDriver();
		driver = drvProvider.getWebDriver();
		readFlag = new ReadFlags();
		Logger.info("Instantiated the driver");
		wait = new WebDriverWait(driver, 60000);
		((JavascriptExecutor) driver).executeScript("if (window.screen) {window.moveTo(0, 0);window.resizeTo(window.screen.availWidth, window.screen.availHeight);};"); 

	}

	
	@AfterSuite(alwaysRun=true)
	public void closeSession() {
		Logger.info("Closing session");
		drvProvider.getWebDriver().close();
	}

	public static void assertEquals(Object actual, Object expected) {
		try{
			Assert.assertEquals(actual, expected);
		}catch(Throwable e){
			Logger.info("[View Screenshot]");
		}
		
		Assert.assertEquals(actual, expected);
	}
	
	public static void assertNotEquals(Object actual, Object expected) {
		try{
			Assert.assertNotEquals(actual, expected);
		}catch(Throwable e){
			Logger.info("[View Screenshot]");
		}
		
		Assert.assertNotEquals(actual, expected);
	}
	

	public void Log(String statement) {
		Reporter.log("<br>"+statement);
		System.out.println(statement);
	}




}
