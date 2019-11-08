package com.intuit.bpui_qa.Pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.intuit.bpui_qa.WebPage;
import com.intuit.bpui_qa.utils.JsToolKit;
import com.intuit.bpui_qa.utils.WaitForElement;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.lift.Matchers.*;
import static org.openqa.selenium.lift.Finders.*;

import static org.hamcrest.Matchers.*;


public class ManageFundingAccountsPage extends WebPage{
	
	@FindBy(id="back_to_main_page")
	private WebElement backToMainPage;
	
	@FindBy(id="subDiv1")
	private WebElement fundingAccountContainer;
	
//	private WebElement accountNickName;
	
	private String defaultFundingAccount;
	private List<String> fundingAccounts = new ArrayList<String>();

	/*
	 * The following is not supported by WebDriver yet.
	 */
	@FindBy(className="funding-accounts-elements")
	private List<WebElement> fundingAccounts1;
	
	public ManageFundingAccountsPage(WebDriver driver){
		super(driver);
		System.out.println("IN Manage funding accounts page");
	}
	
	@Deprecated
	public boolean verifyManageFundingAccountsPage(){
		WebElement accountList = this.waitForAnElement(By.className("eligible-accounts"));
		
		List<WebElement> fundingAccounts = accountList.findElements(By.className("funding-accounts-elements"));

		if(!fundingAccounts.get(0).getText().contains("Checking1") && !fundingAccounts.get(1).getText().contains("Checking2"))
			return false;

		Iterator<WebElement> accounts =  fundingAccounts.iterator();
		WebElement elmInput;
		WebElement elm;
		String fundingAccountName;
		
		while(accounts.hasNext()){
			elm = accounts.next();
			elmInput = elm.findElement(By.tagName("input"));
			fundingAccountName = elm.getText().split(",")[0];
			setDefaultFundAccountsList(fundingAccountName);
			if(elmInput.getAttribute("checked") != null && elmInput.getAttribute("checked").equalsIgnoreCase("true")){
				setDefaultFundingAccount(fundingAccountName);
			}
		}
		
		return true;

	}
	
	/*
	 * This method can be used for retrieving all the funding account names in the
	 * table displayed.
	 */
	public ArrayList<String> getFundingAccountNames(){
		List<WebElement> fundingAccountNames = driver.findElements(By.className("funding-account-fi-name"));
		ArrayList<String> accountNames = new ArrayList<String>();
		Iterator<WebElement> accounts = fundingAccountNames.iterator();
		while(accounts.hasNext()){
			accountNames.add(accounts.next().getText());
		}
		return accountNames;
	}
	
	/*
	 * This method can be used for retrieving all the funding account nick names in the
	 * table displayed.
	 */
	public ArrayList<String> getFundingAccountNickNamesOnly(){
		List<WebElement> fundingAccountNickNames = driver.findElements(By.className("funding-account-nick-name"));
		ArrayList<String> accountNames = new ArrayList<String>();
		Iterator<WebElement> accounts = fundingAccountNickNames.iterator();
		WebElement accountNumber;
		WebElement accountNickName;
		while(accounts.hasNext()){
			accountNickName = accounts.next();
			accountNumber = accountNickName.findElement(By.className("funding-account-number"));
			accountNames.add(accountNickName.getText().replaceAll(accountNumber.getText(), "").trim());
		}
		return accountNames;
	}
	
	/*
	 * This method can be used for retrieving all the funding account numbers in the
	 * table displayed.
	 */
	public ArrayList<String> getFundingAccountNumbersOnly(){
		List<WebElement> fundingAccountNumbers = driver.findElements(By.className("funding-account-number"));
		ArrayList<String> accountNumbers = new ArrayList<String>();
		Iterator<WebElement> fundingAccountNumber = fundingAccountNumbers.iterator();
		while(fundingAccountNumber.hasNext()){
			accountNumbers.add(fundingAccountNumber.next().getText());
		}
		return accountNumbers;
	}
	
	/*
	 * This method can be used for retrieving all the funding account balances in the
	 * table displayed.
	 */
	public ArrayList<String> getFundingAccountBalances(){
		List<WebElement> fundingAccountBalances = driver.findElements(By.className("funding-account-balance"));
		ArrayList<String> accountBalances = new ArrayList<String>();
		Iterator<WebElement> accounts = fundingAccountBalances.iterator();
		while(accounts.hasNext()){
			accountBalances.add(accounts.next().getText());
		}
		return accountBalances;
	}
	/*
	 * This method can be used for retrieving all the funding account links in the
	 * table displayed.
	 */
	public ArrayList<String> getFundingAccountsLinksTextOnly(){
		List<WebElement> fundingAccountLinks = driver.findElements(By.className("funding-account-links"));
		ArrayList<String> accountLinks = new ArrayList<String>();
		Iterator<WebElement> fundingAccounLink = fundingAccountLinks.iterator();
		List<WebElement> fundingAccountActualLinks;
		Iterator<WebElement> links;

		while(fundingAccounLink.hasNext()){
			fundingAccountActualLinks = fundingAccounLink.next().findElements(By.tagName("a"));
			links = fundingAccountActualLinks.iterator();
			while(links.hasNext())
				accountLinks.add(links.next().getText());
		}
		return accountLinks;
		
	}

	/*
	 * This method can be used to extract the funding account data, displayed
	 * in the table of the manage funding accounts page.
	 * Returns: An arraylist of hashmaps(String, String) with the following keys:
	 *          - default: true/false.
	 *          - name: funding account name.
	 *          - nickName: funding account nick name.
	 *          - accountNumber: account number only.
	 *          - link*: combination of "link"+count meaning if there are two links 
	 *                   there will be two keys like: "link1", "link2" etc.
	 */
	public ArrayList<HashMap> getFundingAccountTableData(){
		ArrayList<HashMap> accountData = new ArrayList<HashMap>();
		HashMap<String, String> fundingData = new HashMap<String, String>();
		List<WebElement> accountDetails = driver.findElements(By.className("funding-account-row"));
		Iterator<WebElement> accounts = accountDetails.iterator();
		WebElement account;
		List<WebElement> links;
		int linkCount=1;
		WebElement accountNumber;
		WebElement accountNickName;
		int index;
		while(accounts.hasNext()){
			account = accounts.next();
			if(account.getAttribute("class").contains("default"))
				fundingData.put("default", "true");
			else
				fundingData.put("default", "false");
			fundingData.put("name", account.findElement(By.className("funding-account-fi-name")).getText());
			accountNickName = account.findElement(By.className("funding-account-nick-name"));
			fundingData.put("nickNameAndAccountNumber", accountNickName.getText());
			accountNumber = accountNickName.findElement(By.className("funding-account-number"));
			index = accountNickName.getText().indexOf(accountNumber.getText());
			fundingData.put("nickName", accountNickName.getText().substring(0, index));
			fundingData.put("accountNumber", account.findElement(By.className("funding-account-number")).getText());
			
			links = account.findElements(By.className("funding-account-links"));
			
			Iterator<WebElement> fundingAccounLink = links.iterator();
			List<WebElement> fundingAccountActualLinks;
			Iterator<WebElement> actualLinks;
			while(fundingAccounLink.hasNext()){
				fundingAccountActualLinks = fundingAccounLink.next().findElements(By.tagName("a"));
				actualLinks = fundingAccountActualLinks.iterator();
				while(actualLinks.hasNext()){
					fundingData.put("link"+linkCount, actualLinks.next().getText());
					linkCount++;
				}
				linkCount=1;
			}
			
			accountData.add(fundingData);
			fundingData = new HashMap<String, String>();
		}
		return accountData;
	}

	/*
	 * Changes the default funding account, with the following steps:
	 * - Clicks "Change" link at the bottom.
	 * - Selects a random funding account.
	 * - Returns that string "Account Nick Name + Masked account number"
	 *   back to the calling method.
	 */
	public String changeDefaultFundingAccount(){
		WebElement changeLink = driver.findElement(By.id("defaultFundingAccountToolbar")).findElement(By.tagName("button"));
		changeLink.click();

		List<WebElement> elementList = driver.findElement(By.id("fundingAccountMenu")).findElements(By.className("x-menu-list-item"));
		System.out.println("Element List: " + elementList.size());
		WebElement randomAccountElement = elementList.get(getRandomNumberForRange(elementList.size()));
		String randomFundingAccountFromList = randomAccountElement.getText().trim();
		randomAccountElement.click();
		
		waitForAjaxCallsToComplete(JsToolKit.EXTJS);
		return randomFundingAccountFromList;
	}
	
	
	public void getListOfFundingAccountsInChangeDefaultMenu(){
		clickChangeDefaultAccountLink();
		List<WebElement> elementList = driver.findElement(By.id("fundingAccountMenu")).findElements(By.className("x-menu-list-item"));
		Iterator<WebElement> elementsList = elementList.iterator();
		WebElement elmT;
		while(elementsList.hasNext()){
			elmT = elementsList.next();
			if(!elmT.findElement(By.tagName("a")).getAttribute("class").contains("x-hidden"))
				System.out.println("Account in the list: " + elmT.getText());
		}
		System.out.println("Data: " + getFundingAccountTableData());
		
	}
	
	public void verifyIfOnlyApprovedFundingAccountsCanBeMarkedDefault(){
		clickChangeDefaultAccountLink();
		List<WebElement> elementList = driver.findElement(By.id("fundingAccountMenu")).findElements(By.className("x-menu-list-item"));
		Iterator<WebElement> elementsList = elementList.iterator();
		WebElement elmT;
		ArrayList<HashMap> fundingAccountData = getFundingAccountTableData();
		boolean isApproved = true;
		ArrayList<String> linksByAccountNickNameAndAccountNumber;
		while(elementsList.hasNext()){
			elmT = elementsList.next();
			if(!elmT.findElement(By.tagName("a")).getAttribute("class").contains("x-hidden")){
				System.out.println("Account in the list: " + elmT.getText());
				linksByAccountNickNameAndAccountNumber = getLinksByAccountNickNameAndAccountNumber(elmT.getText().trim());
				System.out.println("Links by account nick name and account number: " + linksByAccountNickNameAndAccountNumber);
			}
		}
//		System.out.println("Data: " + getFundingAccountTableData());
	}
	
	private int getRandomNumberForRange(int end){
		return (int)(Math.random()* (end - 1) ) + 1;
		
	}

	/*
	 * Get Default Funding account name at the bottom.
	 */
	public ArrayList<String> getFundingAccountNamesWhichAreNotDefault(){
		ArrayList<String> accountNames = getFundingAccountNames();
		int indexOfDefaultAccount = accountNames.indexOf(getDefaultFundingAccountNameAtTheBottom());
		accountNames.remove(indexOfDefaultAccount);
		
		return accountNames;
	}

	public String getDefaultFundingAccountAtBottom(){
		WebElement defaultFundingAccount = driver.findElement(By.className("default-account-div"));
		return defaultFundingAccount.findElement(By.className("accountNickName")).getText().trim();
	}
	
	public String getDefaultFundingAccountNumberAtBottom(){
		WebElement defaultFundingAccount = driver.findElement(By.className("default-account-div"));
		return defaultFundingAccount.findElement(By.className("accountNumberCls")).getText().trim();
	}
	
	
	public String getChangeDefaultFundingAccountStatus(){
		WebElement defaultFundingAccount = driver.findElement(By.className("default-account-div"));
		return defaultFundingAccount.findElement(By.id("statusText")).getText().trim();
	}
	
	public String getDefaultFundingAccountNickNameFromTable(){
		ArrayList<HashMap> fundingAccountData = getFundingAccountTableData();
		Iterator<HashMap> itr = fundingAccountData.iterator();
		String isDefault = "false";
		HashMap data;
		while(itr.hasNext()){
			data = itr.next();
			isDefault = data.get("default").toString();
			if(isDefault.equalsIgnoreCase("true"))
				return data.get("nickName").toString().trim();
		}
		return null;
	}
	

	
	public String getDefaultFundingAccountNumberFromTable(){
		ArrayList<HashMap> fundingAccountData = getFundingAccountTableData();
		Iterator<HashMap> itr = fundingAccountData.iterator();
		String isDefault = "false";
		HashMap data;
		while(itr.hasNext()){
			data = itr.next();
			isDefault = data.get("default").toString();
			if(isDefault.equalsIgnoreCase("true"))
				return data.get("accountNumber").toString();
		}
		return null;
	}

	public ArrayList<String> getLinksByAccountNickName(String accountNickName){
		System.out.println("Account Nick Name to get links for: " + accountNickName);
		ArrayList<String> linksList = new ArrayList<String>();
		ArrayList<HashMap> data = this.getFundingAccountTableData();
		Iterator<HashMap> fundingAccountDetails = data.iterator();
		
		int index = 0;
		Set keys;
		String keyT = null;
		String value = null;
		HashMap<String, String> accountDet = new HashMap<String, String>();
		while(fundingAccountDetails.hasNext()){
			accountDet = fundingAccountDetails.next(); 
			if(accountDet.get("nickName").toString().equals(accountNickName)){
				keys = accountDet.keySet();
			
				for (Iterator i = keys.iterator(); i.hasNext();) {
					keyT = (String) i.next();
					if(!keyT.isEmpty() && keyT.contains("link")){
						if(!accountDet.get(keyT).isEmpty())
							linksList.add((String) accountDet.get(keyT));
					}
			    	}
			}
		}
		return linksList;
	}
	
	public ArrayList<String> getLinksByAccountNickNameAndAccountNumber(String accountNickName){
		System.out.println("Account Nick Name to get links for: " + accountNickName);
		ArrayList<String> linksList = new ArrayList<String>();
		ArrayList<HashMap> data = this.getFundingAccountTableData();
		Iterator<HashMap> fundingAccountDetails = data.iterator();
		
		int index = 0;
		Set keys;
		String keyT = null;
		String value = null;
		HashMap<String, String> accountDet = new HashMap<String, String>();
		while(fundingAccountDetails.hasNext()){
			accountDet = fundingAccountDetails.next(); 
			if(accountDet.get("nickNameAndAccountNumber").toString().equals(accountNickName)){
				keys = accountDet.keySet();
			
				for (Iterator i = keys.iterator(); i.hasNext();) {
					keyT = (String) i.next();
					if(!keyT.isEmpty() && keyT.contains("link")){
						if(!accountDet.get(keyT).isEmpty())
							linksList.add((String) accountDet.get(keyT));
					}
			    	}
			}
		}
		return linksList;
	}

	/*
	 * Get Default Funding account name at the bottom.
	 */
	public String getDefaultFundingAccountNameAtTheBottom(){
		String fundingAccountNickAccountName = getDefaultFundingAccountAtBottom();
		return fundingAccountNickAccountName.split(" ")[0];
	}
	
	/*
	 * Get Default Funding account number at the bottom.
	 */
	public String getDefaultFundingAccountNumberAtTheBottom(){
		String fundingAccountNickAccountName = getDefaultFundingAccountAtBottom();
		return fundingAccountNickAccountName.split(" ")[1];
	}

	/*
	 * Get Default Funding account label at the bottom.
	 */
	public String getDefaultFundingAccountLabelAtTheBottom(){
		WebElement defaultFundingAccount = driver.findElement(By.className("default-account-div"));
		return defaultFundingAccount.findElement(By.className("default-account-label")).getText().trim();
	}

	/*
	 * Click "Add Funding Account" button.
	 */
	public void clickAddFundingAccount(){
		WebElement addFundingAccount = driver.findElement(By.className("new-funding-account"));
		addFundingAccount.findElement(By.tagName("button")).click();
	}

	/*
	 * Get Default Funding account Note at the bottom.
	 */
	public String getAddFundingAccountNote(){
		WebElement addFundingAccount = driver.findElement(By.className("new-funding-account"));
		return addFundingAccount.findElement(By.tagName("span")).getText();
	}
	
	/*
	 * Click "Change" link at the bottom.
	 */
	public void clickChangeDefaultAccountLink(){
		driver.findElement(By.linkText("Change")).click();
	}

	/*
	 * Get data based on account name.
	 */
	public HashMap<String, String> getDetailsByAccountName(String accountName){
		ArrayList<HashMap> data = this.getFundingAccountTableData();
		Iterator<HashMap> fundingAccountDetails = data.iterator();
		int index = 0;
		while(fundingAccountDetails.hasNext()){
			if(fundingAccountDetails.next().get("name").toString().equals(accountName))
				return data.get(index);
			index++;
		}
		return null;
	}
	
	/*
	 * WORK IN PROGRESS
	 * Get links available based on account name.
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<String> getLinksByAccountName(String accountName){
		System.out.println("Account Name to get links for: " + accountName);
		ArrayList<String> linksList = new ArrayList<String>();
		ArrayList<HashMap> data = this.getFundingAccountTableData();
		Iterator<HashMap> fundingAccountDetails = data.iterator();
		
//		System.out.println("Data: " + data);
		int index = 0;
		Set keys;
		String keyT = null;
		String value = null;
		HashMap<String, String> accountDet = new HashMap<String, String>();
//		HashSet<String> 
		while(fundingAccountDetails.hasNext()){
			accountDet = fundingAccountDetails.next(); 
//			System.out.println("Account Name: " + accountDet);
			if(accountDet.get("name").toString().equals(accountName)){
				keys = accountDet.keySet();
//				System.out.println("Keys: " + keys);
			
				for (Iterator i = keys.iterator(); i.hasNext();) {
					keyT = (String) i.next();
					if(keyT.contains("link"))
						linksList.add((String) accountDet.get(keyT));
//						value = (String) accountDet.get(keyT);
//					System.out.println(key + " = " + value);
			    	}
			}
//			if(fundingAccountDetails.next().get("accountName").toString().equals(accountName))
//				linkList.add(data.get(index).get(""));
//			index++;
		}
		return linksList;
	}
	@Deprecated
	private void setDefaultFundingAccount(String defaultAccount){
		defaultFundingAccount = defaultAccount;
	}
	
	@Deprecated
	private void setDefaultFundAccountsList(String fundingAccount){
		fundingAccounts.add(fundingAccount);
	}
	
	@Deprecated
	public String getDefaultFundingAccount(){
		return defaultFundingAccount;
	}
	
	@Deprecated
	public List<String> getDefaultFundingAccountsList(){
		return fundingAccounts;
	}

	public void goBackToBpUiPage(){

		clickLinkWithText("ete");
//		backToMainPage.click();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		waitForAjaxCallsToComplete(JsToolKit.EXTJS);
	}
	
	@Override
	public List<WebElement> pageElementsToWait() {
		// TODO Auto-generated method stub
		expectedElements.add(backToMainPage);
		expectedElements.add(fundingAccountContainer);
		return expectedElements;
	}
	
	@Override
	public String setPageName(){
		pageName = "Manage Funding Page";
		return pageName;
	}


}
