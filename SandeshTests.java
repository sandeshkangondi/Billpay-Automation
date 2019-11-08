package com.intuit.bpui_qa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.intuit.bpui_qa.Pages.BPUIPage;
import com.intuit.bpui_qa.Pages.LoginPage;
import com.intuit.bpui_qa.Pages.ManageFundingAccountsPage;
import com.intuit.bpui_qa.Pages.PostLogin;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SandeshTests extends BPUIBasicTest{	
	
	ManageFundingAccountsPage mngFundingAccounts;

	@BeforeClass
	public void startUp() throws InterruptedException{
		Thread.sleep(10000);
	}

	@Test
	public void Test1() throws InterruptedException, ParseException{
		System.out.println("Test1");
		assert(true);
	}
	
	@Test
	public void Test2(){
		System.out.println("Test2");
		assert(true);
	}
}