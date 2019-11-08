package com.intuit.bpui_qa;

import static org.openqa.selenium.lift.Finders.*;
import static org.openqa.selenium.lift.Matchers.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.lift.HamcrestWebDriverTestCase;
import org.openqa.selenium.lift.find.Finder;
import org.openqa.selenium.support.FindBy;

public class GoogleTest extends HamcrestWebDriverTestCase {

	@FindBy(name="userNumber")
	private WebElement userNumber;
	
	@FindBy(name="password")
	private WebElement password;
	
	@FindBy(name="OK")
	private WebElement login;
	
	public WebDriverBackedSelenium wbSel;
	public WebDriver driver;
	
	@Override
  protected WebDriver createDriver() {
		String dev13 = "https://www.ibdev13.com/onlineserv/HB/Signon.cgi";
		WebDriver driver = new FirefoxDriver();
		driver.get(dev13);
/*		driver.findElement(By.className("password")).click();
		driver.findElement(By.className("password")).sendKeys("1234");
		driver.findElement(By.className("userNumber")).sendKeys("IPEQA13");*/
    return driver;
  }
        
  public void testHasAnImageSearchPage() throws Exception {
	  
	String dev13 = "https://www.ibdev13.com/onlineserv/HB/Signon.cgi";
	//WebDriver driver = new FirefoxDriver();
	//goTo("https://www.ibdev13.com/onlineserv/HB/Signon.cgi");
	
    //wbSel = new WebDriverBackedSelenium(driver, dev13);
	//driver.get(dev13);
	Thread.sleep(10000);
	//driver.findElement(By.className("userNumber")).sendKeys("IPEQA13");
	driver.findElement(By.className("password")).sendKeys("1234");
	login.click();
    
	List<WebElement> framesets = driver.findElements(By.tagName("frameset"));
    driver.switchTo().frame(framesets.get(1).findElement(By.name("body")));
    System.out.println("Link: " + driver.findElement(By.linkText("Bill Payment")));
	driver.findElement(By.linkText("Bill Payment")).click();
	//goTo("http://www.google.com");
/*  assertPresenceOf(link("Images"));
    assertPresenceOf(atLeast(4), links().with(text(not(equalTo("Images")))));
    clickOn(link("Images"));
    assertPresenceOf(title().with(text(equalTo("Google Image Search"))));*/
  
  
  }

}