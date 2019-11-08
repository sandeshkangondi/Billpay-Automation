package com.intuit.bpui_qa;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

//import org.openqa.jetty.util.EventProvider;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.lift.HamcrestWebDriverTestCase;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.firefox.FirefoxDriver;
import static org.openqa.selenium.lift.Finders.*;
import static org.openqa.selenium.lift.Matchers.*;


//import com.intuit.bpui_qa.BasicTest.ScreenshootingRemoteWebDriver;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.intuit.bpui_qa.utils.ReadFlags;

public class DriverProvider{

	private static WebDriver driver;
	private static WebDriverBackedSelenium wbSel;
	private static DesiredCapabilities cap;
	
	private static DriverProvider drvProvider;
	private static WebDriverEventListener webDriverEventListener = new EventListener();
	
	private DriverProvider(){
		
	}

	public static DriverProvider createDriver(){
		if(drvProvider == null){
			drvProvider = new DriverProvider();
			if(driver == null)
				driver = getDriver();
			setDriver(driver);
		}
		
		return drvProvider;
	}
	
	/*
	 * Retrieve the desired browser capabilities.
	 */
	private static DesiredCapabilities getDesiredCapabilities(String browserName){
		
		
		if(browserName.equalsIgnoreCase("firefox")){
			cap = DesiredCapabilities.firefox();
		}
		else if(browserName.equalsIgnoreCase("ie")){
			cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		}
		else if(browserName.equalsIgnoreCase("chrome"))
			cap = DesiredCapabilities.chrome();
		else if(browserName.equalsIgnoreCase("htmlunit"))
			cap = DesiredCapabilities.htmlUnit();

		cap.setJavascriptEnabled(true);
		cap.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		return cap;
	}
	
	
	static class ScreenshootingRemoteWebDriver extends RemoteWebDriver implements TakesScreenshot {

        public ScreenshootingRemoteWebDriver(URL remoteURL, DesiredCapabilities desiredCapabilities) {
            super(remoteURL, desiredCapabilities);
        }

        public <X> X getScreenshotAs(OutputType<X> target) throws WebDriverException {
            String base64 = execute(DriverCommand.SCREENSHOT).getValue().toString();
            return target.convertFromBase64Png(base64);
        }

    }
	/*
	 * The following is the method which creates the WebDriver instance.
	 */
	private static  WebDriver getDriver(){
		
		ReadFlags readFlag = new ReadFlags();
		String browserName = readFlag.getFlag("browser");
		System.out.println("Browser: " + readFlag.getFlag("browser"));
		System.out.println("Env: " + System.getenv());
		if(readFlag.getFlag("remote").equalsIgnoreCase("true") ){
			try {
				driver = new EventFiringWebDriver(new ScreenshootingRemoteWebDriver(new URL("http://" + readFlag.getFlag("ipaddress") + ":" + readFlag.getFlag("port") + "/wd/hub"), getDesiredCapabilities(readFlag.getFlag("browser")))).register(webDriverEventListener);
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}
		}else{
			if(browserName.equalsIgnoreCase("firefox")){
//				FirefoxProfile profile = new FirefoxProfile();
//				
//				profile.setPreference("extensions.firebug.onByDefault", true);
//				
//				profile.setPreference("general.useragent.override", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2.10) Gecko/20100914 Firefox/3.6.10 (.NET CLR 2.0.40607)");

				driver = new EventFiringWebDriver(new FirefoxDriver()).register(webDriverEventListener);
			}
			else if(browserName.equalsIgnoreCase("ie"))
				driver = new EventFiringWebDriver(new InternetExplorerDriver());
			else if(browserName.equalsIgnoreCase("chrome"))
				driver = new EventFiringWebDriver(new ChromeDriver());
			else if(browserName.equalsIgnoreCase("htmlunit"))
				driver = new EventFiringWebDriver(new HtmlUnitDriver(BrowserVersion.FIREFOX_3_6));
		}
		if(driver instanceof HtmlUnitDriver)
			((HtmlUnitDriver) driver).setJavascriptEnabled(true);
		driver.manage().deleteAllCookies();
		return driver;
	}
	
	public static void setDriver(WebDriver driver) {
		DriverProvider.driver = driver;
	}
	
	public static WebDriver getWebDriver(){
		return driver;
	}

}
