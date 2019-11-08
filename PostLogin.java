package com.intuit.bpui_qa.Pages;

import java.util.List;

//import com.intuit.bpui_qa.Site;
import com.intuit.bpui_qa.WebPage;
import com.intuit.bpui_qa.utils.WaitForElement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import static org.openqa.selenium.lift.Finders.*;
import static org.openqa.selenium.lift.Matchers.*;

import static org.hamcrest.Matchers.*;

public class PostLogin extends WebPage<PostLogin> {
	
	@FindBy(name="nav_primary1")
	private WebElement navigateBillPayUI;
	
	public PostLogin(WebDriver driver){
		super(driver);
	}
	
	
	public void navigateToBillPayUI(){
		switchFrame("primary");
		
		if(flag.getFlag("env").equalsIgnoreCase("qa1")){
			
            backToBody();
            System.out.println(driver.findElements(By.tagName("frameset")).size());
            List<WebElement> framesets = driver.findElements(By.tagName("frameset"));
            driver.switchTo().frame(framesets.get(1).findElement(By.name("body")));
            System.out.println("Link: " + driver.findElement(By.linkText("Bill Payment")));
			driver.findElement(By.linkText("Bill Payment")).click();
		}else if(flag.getFlag("env").equalsIgnoreCase("fmisqa13")){
			driver.findElement(By.linkText("Tico Bill Pay")).click();
			backToBody();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switchFrame("body");
		}
		else{
			navigateBillPayUI.click();
			if(flag.getFlag("env").equalsIgnoreCase("dev"))
				driver.findElement(By.linkText("Bill Pay")).click();
			backToBody();
			switchFrame("body");
		}

	}

	@Override
	public List<WebElement> pageElementsToWait() {
		// TODO Auto-generated method stub
		expectedElements.add(navigateBillPayUI);
		return expectedElements;
	}
	
	@Override
	public String setPageName(){
		pageName = "Financial Institution Home Page";
		return pageName;
	}




}
