package com.intuit.bpui_qa;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeSuite;

import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.BasePostLoginPage;
import com.intuit.bpui_qa.Pages.PostLogin;
import com.intuit.bpui_qa.utils.DynaTraceUtil;

public class BPUIBasicTest extends BasicTest{

	Site pages = new Site();
	static BPUIPage bpui;
	PostLogin ptLogin;

	@BeforeSuite(alwaysRun=true)
	public void performBpuiStartup(){
		String dev13 = "https://www.ibdev13.com/onlineserv/HB/Signon.cgi";
		String dev11 = "https://www.ibdev11.com/onlineserv/HB/Signon.cgi";
		String dev9 = "https://www.ibdev9.com/onlineserv/HB/Signon.cgi";
		String fmisqa13 = "https://www.candidate13.com/onlineserv/HB/Signon.cgi";
		String currentEnv = dev13;
		performStartup();
		LifeStyle style;
		
		if (readFlag.getFlag("env").equalsIgnoreCase("QA")) {
			wbSel = new WebDriverBackedSelenium(driver, dev13);
			style = LifeStyle.getLifeStyle(dev13);
			
//			driver.get(dev13);
			
		} else if (readFlag.getFlag("env").equalsIgnoreCase("dev")) {
			wbSel = new WebDriverBackedSelenium(driver, dev11);
//			style = new LifeStyle(driver, dev11);
//			driver.get(dev11);
			style = LifeStyle.getLifeStyle(dev11);
			currentEnv = dev11;
		}else if (readFlag.getFlag("env").equalsIgnoreCase("QA1")) {
			wbSel = new WebDriverBackedSelenium(driver, dev9);
//			style = LifeStyle.getLifeStyle(driver, dev13);
//			style = new LifeStyle(driver, dev9);
//			driver.get(dev9);
			style = LifeStyle.getLifeStyle(dev9);
			currentEnv = dev9;
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(readFlag.getFlag("env").equalsIgnoreCase("fmisqa13")){
			wbSel = new WebDriverBackedSelenium(driver, fmisqa13);
			style = LifeStyle.getLifeStyle(fmisqa13);
			currentEnv = fmisqa13;
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Log("Step1: Logging to url: " +currentEnv);
		Site.setDriver(driver);
		DynaTraceUtil.insertMark("Start_LoginBillPayUI", driver);
		
//	    js.executeScript("_dt_addMark(\"Start_LoginBillPayUI\")");  
		ptLogin = Site.Login();
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		DynaTraceUtil.insertMark("Done_LoginBillPayUI", driver);
//		js.executeScript("_dt_addMark(\"Done_LoginBillPayUI\")");
		Log("Step2: Going to Bill Pay UI.");
		
//		DynaTraceUtil.insertMark("Start_NavigateBillPayUI", driver);
//		js.executeScript("_dt_addMark(\"Start_LoginBillPayUI\")");
		bpui = Site.navigateToBillPayUI();
	}
	
}
