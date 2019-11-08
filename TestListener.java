package com.intuit.bpui_qa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import static org.openqa.selenium.OutputType.BASE64;

public class TestListener extends TestListenerAdapter{

	/**
	 * @uml.property  name="drvProvider"
	 * @uml.associationEnd  
	 */
	private DriverProvider drvProvider;
	/**
	 * @uml.property  name="driver"
	 * @uml.associationEnd  
	 */
	WebDriver driver;

	
	@Override
	public void onTestSuccess(ITestResult tr) {
		Reporter.log(tr.getMethod().getMethodName() + " passed.");
	}
	
	
	@Override
	public void onTestFailure(ITestResult tr) {
		System.out.println("Test Failed: ");
		drvProvider = DriverProvider.createDriver();
		driver = drvProvider.getWebDriver();
		Reporter.log("<br>**** Test: " + tr.getMethod().getMethodName()+" FAILED *****");
//		Reporter.log(driver.getPageSource());
		if(!(driver instanceof HtmlUnitDriver))
			takeAScreenShotOfTheApp(tr.getMethod().getMethodName());
//		driver.close();
	}
	
	public void takeAScreenShotOfTheApp(String testName) {
	    String imageName = "FAILED_" + testName + ".png";
		File imageDirectory = new File("target/Screenshots");
		if(!imageDirectory.exists())
			imageDirectory.mkdirs();
		
//	    String imagePath = "target\\Screenshots" + File.separator + imageName;
		String imagePath = imageDirectory.getAbsoluteFile() + File.separator + imageName;
	    try {
	    	String base64Screenshot = ((TakesScreenshot)driver).getScreenshotAs(BASE64);
	    	byte[] decodedScreenshot = Base64.decodeBase64(base64Screenshot.getBytes());
	    	FileOutputStream fos = new FileOutputStream(new File(imagePath));
	    	fos.write(decodedScreenshot);
	    	fos.close();
	    	Reporter.log("<a href='" + imagePath + "'>" + imageName + "</a>");
	    	
//	        Reporter.log("screenshot saved at "+file.getAbsolutePath()+"\\reports\\"+result.getName()+".jpg");
//	        Reporter.log("<a href='../"+result.getName()+".jpg' <img src='../"+result.getName()+".jpg' hight='100' width='100'/> </a>");
//	        BaseClass.selenium.captureScreenshot(file.getAbsolutePath()+"\\reports\\"+result.getName()+".jpg");
	        Reporter.setCurrentTestResult(null);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}


}
