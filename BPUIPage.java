package com.intuit.bpui_qa.Pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.intuit.bpui_qa.WebPage;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import com.intuit.bpui_qa.utils.JsToolKit;

public class BPUIPage extends WebPage{

	@FindBy(id="manage_funding_accounts")
	private WebElement manageFundingAccounts;
	
	@FindBy(id="searchPayeeButton")
	private WebElement addPayeeTop;
	
	@FindBy(id="newPayeeWidget")
	private WebElement newPayeeWidget;
	
	@FindBy(id="bottomPanel")
	private WebElement bottomPanel;
	
	@FindBy(id="receipt")
	private WebElement receipt;
	
	@CacheLookup
	private WebElement fundingAccount;
	private WebElement confirmAccountNo;
	private WebElement address;
	private WebElement cityElm;
	private WebElement zipElm;
	private WebElement newPayeeSave;
	private WebElement cancelPayee;
	private String payeeId = null;
	private String ramdomPaymentId = null;
	private WebElement dateElm;
	
	public BPUIPage(WebDriver driver){
		super(driver);
		System.out.println("In BPUI Page constructor");
		
	}
	
	public void enterAccountNumber1(String accountNumber){
		fundingAccount = driver.findElements(By.id("fundingAccountNo")).get(1);
		fundingAccount.click();
		fundingAccount.sendKeys(accountNumber, (Keys.ENTER));
	}
	
	public void enterAccountNumber2(String accountNumber){
		confirmAccountNo = driver.findElements(By.id("confirmAccount")).get(1);
		confirmAccountNo.click();
		confirmAccountNo.sendKeys(accountNumber, (Keys.ENTER));
	}
	
	public void enterAddress1(String address1){
		address = driver.findElements(By.id("address")).get(1);
        address.click();
        address.sendKeys(address1, (Keys.ENTER));

	}
	
	public void enterCity(String city){
		cityElm = driver.findElements(By.id("city")).get(1);
        cityElm.click();
        cityElm.sendKeys(city, (Keys.ENTER));

	}

	public void enterState(String state){
		WebElement stateElm = driver.findElements(By.id("combobox-div")).get(1).findElement(By.xpath("..")).findElement(By.className("newpayee-statecombo"));
		System.out.println("State Element: " + stateElm);
		stateElm.click();
		stateElm.sendKeys(state, (Keys.ENTER));

	}

	public void enterZip(String zip){
		zipElm = driver.findElements(By.id("newpayee-zip")).get(1).findElement(By.tagName("input"));
		zipElm.click();
		zipElm.sendKeys(zip, (Keys.ENTER));
	}
	
	public void clickAddPayee(){
		newPayeeSave = driver.findElements(By.id("newPayeeSave")).get(1);
		newPayeeSave.click();
	}
	
	public String getSearchPayeeErrorMessage(){
		List<WebElement> searchPayeeErrors = driver.findElements(By.className("search-payee-errormessage"));
		if(searchPayeeErrors.size() > 1)
			return searchPayeeErrors.get(searchPayeeErrors.size() - 1).getText();
		return searchPayeeErrors.get(0).getText();
	}
	
	public void clickCancelAddPayee(){
		cancelPayee = driver.findElements(By.id("newPayeeCancel")).get(1);
		cancelPayee.click();
	}
	
	public void clickSearchPayeeOnTop(){
		driver.findElement(By.id("header-search")).findElement(By.id("searchPayeeButton")).click();
	}
	
	public void clickSearchPayeeBottom(){
		driver.findElement(By.id("bottom-search")).findElement(By.id("searchPayeeButton")).click();
	}
	
	public void enterUnkownPayeeDetails(String payeeName, String accountNumber, String accountNumber2, String address1, String city, String state, String zip) throws InterruptedException{
		searchTopPayee(payeeName);
		driver.findElement(By.id("searchPayeeButton")).click();
		// Wait for search to complete
		
		Thread.sleep(5000);

		enterAccountNumber1(accountNumber);
		Thread.sleep(2000);
		enterAccountNumber2(accountNumber2);
		Thread.sleep(2000);
		enterAddress1(address1);
		Thread.sleep(2000);
		enterCity(city);
		Thread.sleep(2000);
		enterState(state);
		Thread.sleep(2000);
		enterZip(zip);

//        newPayeeSave.click();
	}
	

	public void resortAfterAddingPayee(){
		driver.findElement(By.className("sort-dirty")).click();
	}
	
	public String getAddPayeeFooterError(){
		return driver.findElements(By.className("newpayee-footer-errormessage")).get(1).getText();
	}
	
	public String getAddress1Error(){
		WebElement addressError = driver.findElements(By.id("address")).get(1).findElement(By.xpath(".."));
		return addressError.findElement(By.className("errorDiv")).getText().trim();
	}
	
	public String getCityError(){
		WebElement cityError = driver.findElements(By.id("city")).get(1).findElement(By.xpath(".."));
		return cityError.findElement(By.className("errorDiv")).getText().trim();
	}
	
	public String getStateError(){
		WebElement state = driver.findElements(By.id("combobox-div")).get(1).findElement(By.xpath(".."));
		return state.findElement(By.className("errorDiv")).getText().trim();
	}
	
	public String getZipError(){
		WebElement zip = driver.findElements(By.id("newpayee-zip")).get(1).findElement(By.xpath(".."));
		return zip.findElement(By.className("errorDiv")).getText().trim();
	}
	
	public String getConfirmAccountError(){
		WebElement confirmAct = driver.findElements(By.id("confirmAccount")).get(1).findElement(By.xpath(".."));
		return confirmAct.findElement(By.className("errorDiv")).getText().trim();
	}
	 
	public void verifyMandatoryFieldsError(String payeeName){
		searchTopPayee(payeeName);
		driver.findElement(By.id("searchPayeeButton")).click();
		// Wait for search to complete
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		clickAddPayee();
		
		System.out.println("Address Error message: " + getAddress1Error());
		System.out.println("City Error Message: " + getCityError());
		System.out.println("State Error Message: " + getStateError());
		System.out.println("Zip Error message: " + getZipError());
		
	}

	public void searchTopPayee(String payeeName){
//		Actions builder = new Actions(driver);
////		Action selecMultiple = builder.build();
//		
////		builder.sendKeys(driver.findElement(By.id(getTopAddPayeeTextId())), payeeName);
//		
//		Locatable hoverItem = (Locatable) driver.findElement(By.id(getTopAddPayeeTextId()));
//		
//		Mouse mouse = ((HasInputDevices) driver).getMouse();
////		mouse.mouseDown(arg0)
//		mouse.mouseDown(hoverItem.getCoordinates()); 
//		mouse.mouseUp(hoverItem.getCoordinates()); 
		driver.findElement(By.className("searchPayeeComboCt")).click();
//		driver.findElement(By.id(getTopAddPayeeTextId())).click();
////		driver.findElement(By.id(getTopAddPayeeTextId())).sendKeys(payeeName, (Keys.DOWN));
		driver.findElement(By.className("searchPayeeComboCt")).clear();
		
		driver.findElement(By.className("searchPayeeComboCt")).sendKeys(payeeName);

	}

	private void searchBottomPayee(String payeeName){

		driver.findElement(By.id(getBottomAddPayeeTextId())).click();
//		driver.findElement(By.id(getBottomAddPayeeTextId())).sendKeys(payeeName, (Keys.DOWN));
		driver.findElement(By.id(getBottomAddPayeeTextId())).clear();
		driver.findElement(By.id(getBottomAddPayeeTextId())).sendKeys(payeeName, (Keys.ENTER));

	}

	public void searchKnownPayeeOnTop(String payeeName){
		System.out.println("Search in top: " + getTopAddPayeeTextId());
		searchTopPayee(payeeName);
		wait.until(visibilityOfElementLocated(By.className("top-panel-search-results")));
	}

	public String getStatusMessage(String payeeName){
		return driver.findElement(By.id(getPayeeId(payeeName))).findElement(By.className("status-message")).getText();
	}
	
	public void searchForSingleCharacter(String payeeName){
		searchTopPayee(payeeName);
	}
	
	public void searchForSingleCharacterBottom(String payeeName){
		searchBottomPayee(payeeName);
	}

	public void searchKnownPayeeOnBottom(String payeeName){
		
		System.out.println("Search in bottom: " + getBottomAddPayeeTextId());
		searchBottomPayee(payeeName);
		wait.until(visibilityOfElementLocated(By.className("bottom-panel-search-results")));
	}

	public boolean veirfyKnownPayeeResultsTop(String payeeName){
		List<WebElement> searchResults = getSearchPayeeResultsTop();
		Reporter.log("****** Verifying Search results for the payee search on top ********", true);
		return verifySearchResults(searchResults, payeeName);
	}
	
	private int getRandomNumberForRange(int end){
		return (int)(Math.random()* (end - 1) ) + 1;
		
	}
	
	public boolean verifyAbleToSelectAnyResult(){
		List<WebElement> searchResults = getSearchPayeeResultsTop();
		if(searchResults.size() <= 0)
			return false;
		if(searchResults.size() == 1)
			return true;
		Reporter.log("Selecting a result search size: " + searchResults.size(), true);
		
		int randomIndex = (int)(Math.random()* (searchResults.size() - 1) ) + 1;
		
		Reporter.log("Random index: " + randomIndex, true);
		WebElement randomSearchResultElement = searchResults.get(randomIndex);
		String randomResult = randomSearchResultElement.getText();
		Reporter.log("Randon search result: " + randomResult , true);
		randomSearchResultElement.click();
		String actualSelectedMerchant = driver.findElement(By.id(getTopAddPayeeTextId())).getAttribute("value");
		Reporter.log("Selected Search Result: " + actualSelectedMerchant, true);
		
		return randomResult.equals(actualSelectedMerchant);
	}
	
	public boolean verifyKnownPayeeResultsBottom(String payeeName){
		List<WebElement> searchResults = getSearchPayeeResultsBottom();
		Reporter.log("Bottom results length: " + searchResults.size(), true);
		Reporter.log("****** Verifying Search results for the payee search bottom ********", true);
		return verifySearchResults(searchResults, payeeName);
	}
	
	private boolean verifySearchResults(List<WebElement> searchResults, String payeeName){
		Iterator<WebElement> searchItr = searchResults.iterator();
		String temp = "";
		while(searchItr.hasNext()){
			temp = searchItr.next().getText();
			System.out.println("Payee in dom: " + temp);
			if(!temp.contains(payeeName))
				return false;
		}
		
		return true;

	}
	
	public boolean verifyResultsForSingleCharSearch(String payeeChar){
		return Long.toString((Long) js.executeScript("return Ext.query('*[class=search-item]').length;")).equals("0");
	}
	
	public boolean checkPayeeNameExists(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement name = driver.findElement(By.id(payeeId)).findElement(By.className("payee-name"));
		
		if(!((WebElement)name).isDisplayed())
			return false;
		
		return name.getText().trim().length() >= 2;
	}
	
	public boolean checkNickNameExists(String payeename){
		String id = getPayeeId(payeename);
		WebElement nickName = driver.findElement(By.id(id)).findElement(By.className("payee-nickname"));
		
		if(!((WebElement)nickName).isDisplayed())
			return false;
		
		return true;
		
	}
	
	public boolean checkAmountField(String payeeName){
		payeeId = getPayeeId(payeeName);
		
		if(!((WebElement)getAmountForPayee(payeeId)).isDisplayed())
			return false;

		WebElement amountLabel = driver.findElement(By.id(payeeId)).findElement(By.className("main-payment-details")).findElements(By.tagName("label")).get(0);

		if(!((WebElement)amountLabel).isDisplayed())
			return false;

		if(!"Amount:".equalsIgnoreCase(amountLabel.getText().trim()))
			return false;
		
		return true;
				
	}
	
	private WebElement getAmountForPayee(String payeeId){
		WebElement amount = driver.findElement(By.id(payeeId)).findElement(By.className("payment-amount-field"));
		return amount;
	}
	
	public boolean checkSendOn(String payeeName){
		payeeId = getPayeeId(payeeName);
		
		if(!((WebElement)getSendOnElement(payeeId)).isDisplayed())
			return false;
		
		return true;

	}
	
	private WebElement getSendOnElement(String payeeId){
		WebElement sendOn = driver.findElement(By.id(payeeId)).findElement(By.className("send-on-date"));
		
		return sendOn;
	}

	public boolean checkPayButton(String payeeName){
		payeeId = getPayeeId(payeeName);
		
		WebElement payButton = driver.findElement(By.id(payeeId)).findElement(By.className("pay-button"));

		if(!((WebElement)payButton).isDisplayed())
			return false;
		
		if(!("Pay".equalsIgnoreCase(payButton.getText().trim())))
			return false;
		
		return true;

	}
	
	public boolean checkMemoLinkUI(String payeeName){
		payeeId = getPayeeId(payeeName);

		WebElement memo = driver.findElement(By.id(payeeId)).findElement(By.className("payment-memo"));

		if(!((WebElement)memo).isDisplayed())
			return false;

		if(!((WebElement)memo.findElement(By.className("payment-memo-plus"))).isDisplayed())
			return false;

		if(!((WebElement)memo.findElement(By.className("payment-memo-link"))).isDisplayed())
			return false;
		
		return true;

	}

	public List<WebElement> getSearchPayeeResultsBottom(){
		return driver.findElement(By.className("bottom-panel-search-results")).findElements(By.className("search-item"));
	}

	public List<WebElement> getSearchPayeeResultsTop(){
		return driver.findElement(By.className("top-panel-search-results")).findElements(By.className("search-item"));
//		return driver.findElements(By.className("search-item"));
	}

	public String getTopAddPayeeTextId(){
		return newPayeeWidget.findElement(By.tagName("input")).getAttribute("id");
//		return (String) js.executeScript("return Ext.DomQuery.selectNode('input', 'newPayeeWidget').id;");
	}
	
	public String getBottomAddPayeeTextId(){
		return bottomPanel.findElement(By.tagName("input")).getAttribute("id");
//		return (String) js.executeScript("return Ext.DomQuery.selectNode('input', 'bottomPanel').id;");
	}
	
	public List<String> getPayeeList(){
		List<String> payeeNames = new ArrayList<String>();
		List<WebElement> payees = driver.findElement(By.id("payee-list-panel")).findElements(By.className("payee"));
		Iterator<WebElement> element = payees.iterator();
		WebElement elmT;
		while(element.hasNext()){
			elmT = element.next();
			payeeNames.add(elmT.findElement(By.className("payee-name")).getText());
			
		}
		return payeeNames;
	}
	
	public boolean verifyIfPayeesAreSortedByDefault(){
		List payeeList = getPayeeList();
		System.out.println("Original Payee List: " + payeeList);
		List tempPayeeList = new ArrayList(payeeList);
		Collections.sort(tempPayeeList, new Comparator<String>() {

			public int compare(String o1, String o2) {
				
				// TODO Auto-generated method stub
				return o1.compareToIgnoreCase(o2);
			}
			
		});
		System.out.println("Payee list after sorting: " + tempPayeeList);
		return tempPayeeList.equals(payeeList);

	}
	
	public String getSortbyLabel(){
		return driver.findElement(By.id("sortBy-label")).getText();
	}
	
	public String getSelectedSortOption(){
		return driver.findElement(By.id("sort-payee-combo")).findElement(By.tagName("input")).getAttribute("value");
	}
	
	public int getPayeeCount(){
		return driver.findElement(By.id("payee-list-panel")).findElements(By.className("payee")).size();
	}
	
	public boolean verifySortingExists(){
		WebElement sortElement = driver.findElement(By.id("sortby-payfrom-div1"));
		if(!sortElement.findElement(By.id("sortBy-label")).isEnabled()) return false;
		if(!sortElement.findElement(By.id("sort-payee-combo")).isEnabled()) return false;
		
		return true;
		
	}
	
	public String getPayeeId(String payeeName){
		List<WebElement> payees = driver.findElement(By.id("payee-list-panel")).findElements(By.className("payee"));
		Iterator<WebElement> element = payees.iterator();
		WebElement elmT;
		while(element.hasNext()){
			elmT = element.next();
			// THIS SHOULD BE TEMPORARY, WILL WORK ON CHANGING THE DOM TO GET TO A BETTER STATE.
			if(elmT.findElement(By.className("payee-name")).getText().contains(payeeName))
				return elmT.getAttribute("id");
//			if(payeeName.contains(elmT.findElement(By.className("payee-name")).getText()))
//				return elmT.getAttribute("id");
					
		}
		return null;
		
	}
	
	public void togglePayeeDrawer(String payeeName){
		payeeId = getPayeeId(payeeName);
		 
		driver.findElement(By.id(payeeId)).findElement(By.className("toggle-drawer-button")).click();
	}
	
	public void openPayeeOptionsTray(String payeeName){
		payeeId = getPayeeId(payeeName);
		System.out.println("Payee tile id: " + payeeId);
		if(!getDrawerStatus(payeeName))
			driver.findElement(By.id(payeeId)).findElement(By.className("toggle-drawer-button")).click();
	}
	
	public boolean getDrawerStatus(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement drawer = driver.findElement(By.id(payeeId)).findElement(By.className("drawer"));
		
		return ((WebElement)drawer).isDisplayed();

	}
	
	public boolean verifyAccountNumberFields(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement accountNoFields = driver.findElement(By.id(payeeId)).findElement(By.className("accountno-fields"));
		
		if(!((WebElement)accountNoFields).isDisplayed())
			return false;
		
		WebElement accountNumberLabel = accountNoFields.findElement(By.className("account-details-editform-span"));

		if(!((WebElement)accountNumberLabel).isDisplayed())
			return false;
		
		if(!("Account number:".equalsIgnoreCase(accountNumberLabel.getText())))
			return false;
		
		WebElement accountNumber = accountNoFields.findElement(By.className("accountNo"));

		if(!((WebElement)accountNumber).isDisplayed())
			return false;

		WebElement confirmAccountNumberLabel = accountNoFields.findElement(By.className("confirmaccount-label"));

		if(!((WebElement)confirmAccountNumberLabel).isDisplayed())
			return false;

		if(!("Confirm account number:".equalsIgnoreCase(confirmAccountNumberLabel.getText())))
			return false;

		WebElement confirmAccountNumber = accountNoFields.findElement(By.className("confirm"));

		if(!((WebElement)confirmAccountNumber).isDisplayed())
			return false;

		if(!confirmAccountNumber.getAttribute("value").trim().equals(""))
			return false;
		
		return true;

	}
	
	public boolean verifyContactFields(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement contactFields = driver.findElement(By.id(payeeId)).findElement(By.id("company"));
		
		if(!((WebElement)contactFields).isDisplayed())
			return false;
		
		WebElement nameLabel = contactFields.findElement(By.className("account-details-editform-span"));

		if(!((WebElement)nameLabel).isDisplayed())
			return false;
		
		if(!("Name:".equals(nameLabel.getText().trim())))
			return false;
		
		WebElement name = contactFields.findElement(By.className("name"));

		if(!((WebElement)name).isDisplayed())
			return false;
		
		if(!(payeeName.equals(name.getAttribute("value"))))
			return false;
		
		WebElement addressFields = contactFields.findElement(By.id("addressFieldsToFill"));
		
		if(!((WebElement) addressFields).isDisplayed())
			return false;
		
		return true;
		

	}
	
	public boolean verifyAccountInfoFooter(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement footerFields = driver.findElement(By.id(payeeId)).findElement(By.className("account-details-footer"));
		
		if(!((WebElement)footerFields).isDisplayed())
			return false;

		WebElement saveButton = footerFields.findElement(By.name("saveAlertsButton"));

		if(!((WebElement)saveButton).isDisplayed())
			return false;
		
		if(!"Save".equals(saveButton.getText().trim()))
			return false;
		
		WebElement resetChanges = footerFields.findElement(By.name("cancelChangesLink"));

		if(!((WebElement)resetChanges).isDisplayed())
			return false;

		if(!("Don't make changes".equals(resetChanges.getText().trim())))
			return false;
		
		return true;

		
	}
	
	public void goToAccountInfoTab(String payeeName){
		payeeId = getPayeeId(payeeName);
		driver.findElement(By.id(payeeId)).findElement(By.linkText("Account Information")).click();
	}
	
	public boolean isConfirAcctNumerEnabledInAcctInfo(String payeeName){
		payeeId = getPayeeId(payeeName);
//		System.out.println("True/False: " + driver.findElement(By.id(payeeId)).findElement(By.className("confirm")).getAttribute("readonly"));
		return Boolean.parseBoolean(driver.findElement(By.id(payeeId)).findElement(By.className("confirm")).getAttribute("readonly"));
	}
	
	public void saveEditedPayee(String payeeName){
		payeeId = getPayeeId(payeeName);
		driver.findElement(By.id(payeeId)).findElement(By.className("account-details-save")).click();
	}
	
	public void clearPersonalPayeeDetails(String payeename){
		String id = getPayeeId(payeename);
		WebElement name = driver.findElement(By.id(id)).findElement(By.className("name"));
		WebElement nickName = driver.findElement(By.id(id)).findElement(By.className("nickname"));
		WebElement addr1 = driver.findElement(By.id(id)).findElement(By.className("addr1"));
		WebElement city = driver.findElement(By.id(id)).findElement(By.className("city"));
		WebElement state = driver.findElement(By.id(id)).findElement(By.className("state"));
		WebElement zip1 = driver.findElement(By.id(id)).findElement(By.name("zip1"));
		WebElement zip2 = driver.findElement(By.id(id)).findElement(By.name("zip2"));
		
		name.clear();
		nickName.clear();
		addr1.clear();
		city.clear();
		state.clear();
		zip1.clear();
		zip2.clear();
	}

	public void editAccountNumber(String payeeName, String accountNumber){
		payeeId = getPayeeId(payeeName);
		WebElement accntNumberElm = driver.findElement(By.id(payeeId)).findElement(By.className("accountNo"));
		accntNumberElm.click();
		accntNumberElm.clear();
		accntNumberElm.sendKeys(accountNumber, (Keys.ENTER));

	}

	public void editConfirmAccountNumber(String payeeName, String accountNumber){
		payeeId = getPayeeId(payeeName);
		WebElement confirmAccntNumberElm = driver.findElement(By.id(payeeId)).findElement(By.className("confirm"));
		confirmAccntNumberElm.click();
		confirmAccntNumberElm.clear();
		confirmAccntNumberElm.sendKeys(accountNumber, (Keys.ENTER));

	}

	public void editName(String payeeName, String name){
		payeeId = getPayeeId(payeeName);
		WebElement nameElm = driver.findElement(By.id(payeeId)).findElement(By.className("name"));
		nameElm.click();
		nameElm.clear();
		nameElm.sendKeys(name, (Keys.ENTER));
	}

	public void editNickName(String payeeName, String nickname){
		payeeId = getPayeeId(payeeName);
		WebElement nickNameElm = driver.findElement(By.id(payeeId)).findElement(By.className("nickname"));
		nickNameElm.click();
		nickNameElm.clear();
		nickNameElm.sendKeys(nickname, (Keys.ENTER));
	}

	public void editAddress1(String payeeName, String address1){
		payeeId = getPayeeId(payeeName);
		WebElement addr1 = driver.findElement(By.id(payeeId)).findElement(By.className("addr1"));
		addr1.click();
		addr1.clear();
		addr1.sendKeys(address1, (Keys.ENTER));
	}

	public void editCity(String payeeName, String city){
		payeeId = getPayeeId(payeeName);
		WebElement cityElm = driver.findElement(By.id(payeeId)).findElement(By.className("city"));
		cityElm.click();
		cityElm.clear();
		cityElm.sendKeys(city, (Keys.ENTER));
	}

	public void editState(String payeeName, String state){
		payeeId = getPayeeId(payeeName);
		WebElement stateElm = driver.findElement(By.id(payeeId)).findElement(By.className("state"));
		stateElm.click();
		stateElm.clear();
		stateElm.sendKeys(state, (Keys.ENTER));
	}

	public void editZip1(String payeeName, String zip){
		payeeId = getPayeeId(payeeName);
		WebElement zip1Elm = driver.findElement(By.id(payeeId)).findElement(By.name("zip1"));
		zip1Elm.click();
		zip1Elm.clear();
		zip1Elm.sendKeys(zip, (Keys.ENTER));
	}

	public void editZip2(String payeeName, String zip){
		payeeId = getPayeeId(payeeName);
		WebElement zip2Elm = driver.findElement(By.id(payeeId)).findElement(By.name("zip2"));
		zip2Elm.click();
		zip2Elm.clear();
		zip2Elm.sendKeys(zip, (Keys.ENTER));
	}

	public void editPhone(String payeeName, String phoneNumber){
		payeeId = getPayeeId(payeeName);
		String[] phoneNumbers = phoneNumber.split("-");
		WebElement phone1Elm = driver.findElement(By.id(payeeId)).findElement(By.className("account-details-phone1"));
		phone1Elm.click();
		phone1Elm.clear();
		phone1Elm.sendKeys(phoneNumbers[0], (Keys.ENTER));
		
		WebElement phone2Elm = driver.findElement(By.id(payeeId)).findElement(By.className("account-details-phone2"));
		phone2Elm.click();
		phone2Elm.clear();
		phone2Elm.sendKeys(phoneNumbers[1], (Keys.ENTER));

		WebElement phone3Elm = driver.findElement(By.id(payeeId)).findElement(By.className("account-details-phone3"));
		phone3Elm.click();
		phone3Elm.clear();
		phone3Elm.sendKeys(phoneNumbers[2], (Keys.ENTER));

	}

	public void clearNickNameEditPayee(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement nickName = driver.findElement(By.id(payeeId)).findElement(By.className("nickname"));

		nickName.clear();
	}
	
	public boolean verifyNameEditInfoMandatoryError(String payeeName){
		payeeId = getPayeeId(payeeName);
		return "Name is Required".equals(driver.findElement(By.id(payeeId)).findElement(By.className("nameError")).getText());
	}

	public boolean verifyAddress1EditInfoMandatoryError(String payeeName){
		payeeId = getPayeeId(payeeName);
		return "Address is Required".equals(driver.findElement(By.id(payeeId)).findElement(By.className("address1Error")).getText());
	}

	public boolean verifyCityEditInfoMandatoryError(String payeeName){
		payeeId = getPayeeId(payeeName);
		return "City is Required".equals(driver.findElement(By.id(payeeId)).findElement(By.className("cityError")).getText());
	}

	public boolean verifyStateEditInfoMandatoryError(String payeeName){
		payeeId = getPayeeId(payeeName);
		return "State is Required".equals(driver.findElement(By.id(payeeId)).findElement(By.className("stateError")).getText());
	}

	public boolean verifyPhoneEditInfoErrorMessage(String payeeName){
		payeeId = getPayeeId(payeeName);
		return "You have to enter 10 digits".equals(driver.findElement(By.id(payeeId)).findElement(By.className("phoneError")).getText());
	}

	public boolean verifySuccessMessage(String payeeName){
		payeeId = getPayeeId(payeeName);
		System.out.println("Footer tile message: " + getStatusMessage(payeeName));
		return "Success Your changes have been saved.".equals(getStatusMessage(payeeName));
	}

	public boolean verifyZipEditInfoMandatoryError(String payeeName){
		payeeId = getPayeeId(payeeName);
		return "You have to enter 5 or 9 digits".equals(driver.findElement(By.id(payeeId)).findElement(By.className("zipError")).getText());
	}

	public boolean verifyZipEditInfoValidationMessage(String payeeName){
		return verifyZipEditInfoMandatoryError(payeeName);
	}

	public boolean verifyConfirmAccountNumberError(String payeeName){
		payeeId = getPayeeId(payeeName);
		return "The two account numbers are different".equals(driver.findElement(By.id(payeeId)).findElement(By.className("confirmAccountError")).getText());
	}
	public void goToAutomaticPaymentTab(String payeeName){
		payeeId = getPayeeId(payeeName);
		if(!getAutomaticPaymentstatus(payeeName))
		driver.findElement(By.id(payeeId)).findElement(By.linkText("Automatic payment")).click();
	}
	
	
	public String getFooterStatusMessage(String payeeName){
		payeeId = getPayeeId(payeeName);
		return driver.findElement(By.id(payeeId)).findElement(By.className("statusText")).getText();
	}
	
	public boolean verifyUIOfDeleteInOptionsTray(String payeeName){
		payeeId = getPayeeId(payeeName);
		if(!driver.findElement(By.id(payeeId)).findElement(By.className("delete-it")).getText().equalsIgnoreCase("Delete it")) 
			return false;

		if(!driver.findElement(By.id(payeeId)).findElement(By.className("hide-it")).getText().equalsIgnoreCase("Hide it")) 
			return false;

		return true;
	}

	public void clickDeleteItLink(String payeeName){
		payeeId = getPayeeId(payeeName);
		driver.findElement(By.id(payeeId)).findElement(By.className("delete-it")).click();	
	}

	public boolean verifyDeletePayeeDialogHeader(String payeeName){
		System.out.println("Delete Window Header text: " + driver.findElements(By.className("delete-payee-header-companyname")).get(1).findElement(By.tagName("strong")).getText());
		String actualheader = driver.findElements(By.className("delete-payee-header-companyname")).get(1).findElement(By.tagName("strong")).getText();
		if(!(("Are you sure you want to delete "+payeeName+"?").equalsIgnoreCase(actualheader)))
			return false;
		
		return true;
	}
	
	public void clickYesDeleteButton(){
		driver.findElements(By.className("delete-payee-button")).get(1).click();
	}
	

	public boolean verifyHeaderOfDeleteInOptionsTray(String payeeName){
		payeeId = getPayeeId(payeeName);
		if(!driver.findElement(By.id(payeeId)).findElement(By.className("delete-hide-payment-header")).getText().equalsIgnoreCase("Not paying this anymore?")) 
			return false;

		return true;
	}

	public String getFirstPayeeName(){
		return driver.findElement(By.id("payee-list-panel")).findElement(By.className("payee")).findElement(By.className("payee-name")).getText();
		
	}
	
	public boolean verifyEditPayeeNickNameFotterMessage(String payeeName){
		payeeId = getPayeeId(payeeName);
		System.out.println("Actual Error Message: " + getFooterStatusMessage(payeeName));
		System.out.println("Expected error message: " + "Please provide consumer payee nick name");
		return "Please provide consumer payee nick name".equalsIgnoreCase(getFooterStatusMessage(payeeName));
	}
	
	public ManageFundingAccountsPage gotoManageFundingAccountsPage(){
		manageFundingAccounts.click();
		return PageFactory.initElements(driver, ManageFundingAccountsPage.class);
	}
	
	public void clickManageFundingAccountsLink(){
		clickLinkWithText("Manage funding account(s)");
//		manageFundingAccounts.click();
	}

	public boolean verifyDefaultFundingAccountOnTop(String defaultFundingAccount){
		System.out.println("Value on top: " + driver.findElement(By.id(getTopFundingAcountValue())).getAttribute("value"));
		return defaultFundingAccount.equalsIgnoreCase(driver.findElement(By.id(getTopFundingAcountValue())).getAttribute("value"));
	}
	
	public boolean verifyDefaultFundingAccountBottom(String defaultFundingAccount){
		System.out.println("Bottom funding account id: " + getBottomFundingAccountTextId());
		return defaultFundingAccount.equalsIgnoreCase(driver.findElement(By.id(getBottomFundingAccountTextId())).getAttribute("value"));
	}
	
	public String getBottomFundingAccountTextId(){
		return (String) js.executeScript("return document.getElementById('pay-from-funding-account').getElementsByTagName('input')[0].id;");

	}
	
	public String getTopFundingAcountValue(){
		return (String) js.executeScript("return document.getElementById('funding-account-combo-top').getElementsByTagName('input')[0].id;");
	}
	
	public void openTopFundinAccountsList(){
		WebElement topFundingDropDown = driver.findElement(By.id("funding-account-combo-top")).findElement(By.tagName("img"));
		topFundingDropDown.click();
	}

	public void closeTopFundinAccountsList(){
		WebElement topFundingDropDown = driver.findElement(By.id("funding-account-combo-top")).findElement(By.tagName("img"));
		topFundingDropDown.click();
	}

	public void selectTopFundingAccount(String fundingAccountName){
		WebElement topFundingAccounts = driver.findElement(By.className("top-funding-account-list")).findElement(By.tagName("div"));
		List<WebElement> topFundingAcountsList = topFundingAccounts.findElements(By.tagName("div"));
//		List<String> topFundingAccountList = new ArrayList<String>();
		Iterator<WebElement> fundingAccount = topFundingAcountsList.iterator();
		WebElement fundElm;
		while(fundingAccount.hasNext()){
			fundElm = fundingAccount.next();
			if(fundingAccountName.equals(fundElm.getText())){
				fundElm.click();
				break;
			}
		}
	}

	public void openBottomFundinAccountsList(){
		WebElement bottomFundingDropDown = driver.findElement(By.id("pay-from-funding-account")).findElement(By.tagName("img"));
		bottomFundingDropDown.click();
	}

	public void closeBottomFundinAccountsList(){
		WebElement bottomFundingDropDown = driver.findElement(By.id("pay-from-funding-account")).findElement(By.tagName("img"));
		bottomFundingDropDown.click();
	}

	public void openFundingAccountListForPayee(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement payeeFundingDropDown = driver.findElement(By.id(payeeId)).findElement(By.className("automatic-payment-fundingaccount-comboct")).findElement(By.tagName("img"));
		payeeFundingDropDown.click();
		
	}

	public List<String> getFundingAccountListOnTop(){
		WebElement topFundingAccounts = driver.findElement(By.className("top-funding-account-list")).findElement(By.tagName("div"));
		List<WebElement> topFundingAcountsList = topFundingAccounts.findElements(By.tagName("div"));
		List<String> topFundingAccountList = new ArrayList<String>();
		Iterator<WebElement> fundingAccount = topFundingAcountsList.iterator();
		while(fundingAccount.hasNext()){
			topFundingAccountList.add(fundingAccount.next().getText());
		}
		return topFundingAccountList;
			
	}
	
	public String getSelectedFundingAccountFromTop(){
		System.out.println("Top Funding accounts: " + driver.findElements(By.id("funding-account-combo-top")).size());
		return driver.findElement(By.id("funding-account-combo-top")).findElement(By.tagName("input")).getAttribute("value").trim();
	}

	public String getSelectedFundingAccountFromBottom(){
		return driver.findElement(By.id("pay-from-funding-account")).findElement(By.tagName("input")).getAttribute("value").trim();
	}

	public List<String> getFundingAccountListOnBottom(){
		WebElement bottomFundingAccounts = driver.findElement(By.className("bottom-funding-account-list")).findElement(By.tagName("div"));
		List<WebElement> bottomFundingAcountsList = bottomFundingAccounts.findElements(By.tagName("div"));
		List<String> bottomFundingAccountList = new ArrayList<String>();
		Iterator<WebElement> fundingAccount = bottomFundingAcountsList.iterator();
		while(fundingAccount.hasNext()){
			bottomFundingAccountList.add(fundingAccount.next().getText());
		}
		return bottomFundingAccountList;
			
	}

	public List<String> getFundingAccountListForAPayee(String payeeName){
		payeeId = getPayeeId(payeeName);
		WebElement payeeFundingAccounts = driver.findElement(By.className("automatic-payment-funding-account-list")).findElement(By.tagName("div"));
		List<WebElement> payeeFundingAcountsList = payeeFundingAccounts.findElements(By.tagName("div"));
		List<String> bottomFundingAccountList = new ArrayList<String>();
		Iterator<WebElement> fundingAccount = payeeFundingAcountsList.iterator();
		while(fundingAccount.hasNext()){
			bottomFundingAccountList.add(fundingAccount.next().getText());
		}
		return bottomFundingAccountList;
			
	}

	public boolean verifyFundingAccounts(List<String> firstList, List<String> secondList){
		secondList.removeAll(firstList);
		return secondList.isEmpty();
	}

	
	public void changeFundingAccountOnTop(String fundingAccountName){
		
		String payFromId = (String) js.executeScript("return document.getElementById('pay-from-container').getElementsByTagName('input')[0].id;");
		System.out.println("Id for payfrom: " + payFromId);
		driver.findElement(By.id(payFromId)).click();
		driver.findElement(By.id(payFromId)).sendKeys(fundingAccountName, (Keys.ENTER));
	}
	
	public void addPayee(){
		switchFrame("body");
		addPayeeTop.click();
	}
	
	public List<String> getScheduledPaymentPayeeNames(){
		WebElement receipt = driver.findElement(By.id("receipt"));
		WebElement scheduledPayments = receipt.findElement(By.className("scheduled-payments"));
		List<WebElement> payeeNames = scheduledPayments.findElements(By.className("payeename"));
		List<String> finalPayeeNames = new ArrayList<String>();
		Iterator<WebElement> elm = payeeNames.iterator();
		while(elm.hasNext()){
			finalPayeeNames.add(elm.next().getText());
		}
		System.out.println("Final Payees List: " + finalPayeeNames);
		return finalPayeeNames;
	}

	private List<WebElement> getAllScheduledPayments(){
		WebElement receipt = driver.findElement(By.id("receipt"));
		WebElement scheduledPayments = receipt.findElement(By.className("scheduled-payments"));
		return scheduledPayments.findElements(By.tagName("tr"));
	}
	
	private WebElement getFirstScheduledRecurPayment(){
		List<WebElement> scheduledPayments = getAllScheduledPayments();
		Iterator<WebElement> elm = scheduledPayments.iterator();
		WebElement paymentElm;
		while(elm.hasNext()){
			paymentElm = elm.next();
			if(paymentElm.getAttribute("automaticpayment") != null && paymentElm.getAttribute("automaticpayment").equals("true")){
				return paymentElm;
			}
		}
		return null;
	}
	
	private List<String> getAllRecurringPaymentIds(){
		List<WebElement> scheduledPayments = getAllScheduledPayments();
		Iterator<WebElement> elm = scheduledPayments.iterator();
		List<String> paymentIds = new ArrayList<String>();
		while(elm.hasNext()){
			paymentIds.add(elm.next().getAttribute("id"));
		}
		return paymentIds;		
	}
	
	public void cancelRandomRecurringPayment() {
		WebElement cancelDialog = driver.findElements(By.className("cancel-payment-div")).get(1);
		
		System.out.println("Text: " + cancelDialog.getText());
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		cancelDialog.findElement(By.xpath("..//..//..//..")).findElement(By.name("cancelautomaticpayment")).click();
		waitForAjaxCallsToComplete(JsToolKit.EXTJS);

//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		waitForElementsToDisappear(By.id(ramdomPaymentId));
	}
	
	public boolean verifyCancelledPaymentIsDeletedFromReceipt(){
		return isElementVisible(By.id(ramdomPaymentId));
	}

	public int getRecurringPaymentsCount(){
		return getAllRecurringPaymentIds().size();
	}
	
	public String getCancelRecurringPaymentHeader(){

		List<String> recurrPaymentIds = getAllRecurringPaymentIds();
		System.out.println("Dize: " + recurrPaymentIds.size());
		if(recurrPaymentIds.size() < 2)
			return null;
		this.ramdomPaymentId = recurrPaymentIds.get(recurrPaymentIds.size() - 2);
		driver.findElement(By.id(ramdomPaymentId)).findElement(By.className("icon-delete")).click();
//		WebElement firstScheduledRecurPayment = getFirstScheduledRecurPayment();
		
//		firstScheduledRecurPayment.findElement(By.className("icon-delete")).click();
		return driver.findElements(By.className("cancel-payment-div")).get(1).findElement(By.xpath("..//..//..//..")).findElement(By.className("cancel-automatic-payment-header")).getText();
//		WebElement scheduledPayments = driver.findElement(By.className("scheduled-payments"));
//		List<WebElement> payeeNames = scheduledPayments.findElements(By.tagName("tr"));
//		List<String> finalPayeeNames = new ArrayList<String>();
//		Iterator<WebElement> elm = payeeNames.iterator();
//		WebElement tElm;
//		WebElement cancelPayment;
//		while(elm.hasNext()){
//			tElm = elm.next();
//			if(tElm.getAttribute("automaticpayment") != null && tElm.getAttribute("automaticpayment").equals("true")){
//				tElm.findElement(By.className("icon-delete")).click();
//			}
//		}
////		System.out.println("Cancel payment header: " + driver.findElements(By.className("cancel-payment-div")).get(2).getText());
//		System.out.println("Cancel payment header: " + driver.findElements(By.className("cancel-payment-div")).get(2).findElement(By.xpath("..//..//..//..")).findElement(By.className("cancel-automatic-payment-header")).getText());
//		System.out.println("Final Payees List: " + finalPayeeNames);
//		return finalPayeeNames;
	}
	
	public String getPaymentId(){
		return this.ramdomPaymentId;
	}

//	public List<String> getScheduledPaymentPayeeNames(){
//		WebElement scheduledPayments = driver.findElement(By.className("scheduled-payments"));
//		List<WebElement> payeeNames = scheduledPayments.findElements(By.className("payeename"));
//		List<String> finalPayeeNames = new ArrayList<String>();
//		Iterator<WebElement> elm = payeeNames.iterator();
//		while(elm.hasNext()){
//			finalPayeeNames.add(elm.next().getText());
//		}
//		System.out.println("Final Payees List: " + finalPayeeNames);
//		return finalPayeeNames;
//	}

	public boolean checkIfPayeeNameIsInScheduledPaymentList(String payeeName){
		return getScheduledPaymentPayeeNames().contains(payeeName);
	}
	
	public void clickViewAllPayments(){
		driver.findElement(By.linkText("View all payments")).click();
//		wait.until(visibilityOfElementLocated(By.id("searchGridPanel")));
	}
	
	public PaymentHistoryPage goToPaymentsHistoryPage(){
		driver.findElement(By.linkText("View all payments")).click();
		wait.until(visibilityOfElementLocated(By.id("searchGridPanel")));
		return PageFactory.initElements(driver, PaymentHistoryPage.class);
	}
	
	// METHODS FOR VIEW SCHEDULED PAYMENT TEST CASES
	// When the same payment is tried to be rescheduled we see an error
	public String getRepeatedScheduledPaymentError() throws InterruptedException{	
		searchTopPayee("canberra10");
		clickSearchPayeeOnTop();
		enterUnkownPayeeDetails("canberra10","999","999", "4010 west michgan ave", "Kalamazoo", "Michigan", "49006");
		clickAddPayee();
		refreshBillPayment(); 
		addPayment("canberra10", "16", "07/29/2011");
		refreshBillPayment();
		addPayment("canberra10", "16", "07/29/2011");
		//RefreshBillPayment();
		Thread.sleep(5000);
		return(driver.findElements(By.className("tile-message")).get(0).findElement(By.className("tile-message-content")).getText());
	}
	
	// Help text for scheduled payments below the header
	public String getHelpTextScheduledPayements(){
		return (driver.findElement(By.className("scheduled-payments-cont")).findElement(By.className("receipt-edit-delete-info")).getText());		
	}
	
	// Refresh BPUI page for firefox
	public void refreshBillPayment() throws InterruptedException{
		Thread.sleep(10000);
        driver.switchTo().defaultContent();
        driver.switchTo().frame("primary");
        Thread.sleep(10000);
        driver.findElement(By.name("nav_primary1")).click();
        driver.switchTo().defaultContent();
        driver.switchTo().frame("body");
		}
	
	// Method to schedule a payment for a payee	
	public void addPayment(String payeename, String amount, String date) throws InterruptedException{
		String id = getPayeeId(payeename);
		WebElement elmt = driver.findElement(By.id(id));
		Thread.sleep(5000);
		clickCalenderDate(payeename, date);
		//elmt.findElement(By.className("send-on-date")).findElement(By.className("x-form-text")).sendKeys(date, (Keys.ENTER));
        elmt.findElement(By.className("payment-amount-field")).click();
        elmt.findElement(By.className("payment-amount-field")).clear();
		elmt.findElement(By.className("payment-amount-field")).sendKeys(amount, (Keys.ENTER));
		elmt.findElement(By.className("pay-button")).click();	
		
	}
	
	// To check if a similar scheduled payment exists
	public boolean verifyScheduledPaymentDetails(String payeename, String amount, String date){
		WebElement receipt = driver.findElement(By.id("receipt"));
		List<WebElement> ScheduledPayments = (List<WebElement>) receipt.findElement(By.className("scheduled-payments")).findElements(By.name("parent"));
		Iterator<WebElement> elements = ScheduledPayments.iterator();
		WebElement elmt;
		while(elements.hasNext()){
		elmt = elements.next();{
		if ((elmt.findElement(By.className("payeename"))).getText().equalsIgnoreCase(payeename) && (elmt.findElement(By.className("receiptPaymentAmount"))).getText().equalsIgnoreCase(amount) && (elmt.findElement(By.className("receiptDate"))).getText().equalsIgnoreCase(date) /*&& (elmt.findElement(By.className("icon-edit"))).getText().equalsIgnoreCase("Edit Payment") && (elmt.findElement(By.className("icon-delete"))).getText().equalsIgnoreCase("Cancel Payment")*/)
		return true;
		}}
		return false;}
	
	// Method to return the header for scheduled payments frame
	public String getScheduledPaymentsHeader(){
	return (driver.findElement(By.className("receipt-scheduled-payments-header"))).getText();
	}	
	
	// To sum up all the individual scheduled payments
	public float getScheduledPaymentsSum(){
		Float Total = new Float(0);
		WebElement receipt = driver.findElement(By.id("receipt"));
		List<WebElement> ScheduledPayments = (List<WebElement>) receipt.findElement(By.className("scheduled-payments")).findElements(By.name("parent"));
		Iterator<WebElement> elements = ScheduledPayments.iterator();
		WebElement elmt;
		while(elements.hasNext()){
		elmt = elements.next();
		Total += Float.parseFloat(elmt.findElement(By.className("receiptPaymentAmount")).getText().substring(1));	
		}	
		return (Total);
	}
	
	// Returns the total for the scheduled payments
	public float getScheduledPaymentTotalFigure(){
		Float f = new Float(driver.findElement(By.className("scheduled-payments-total")).findElement(By.className("receiptPaymentAmount")).getText().substring(1));
		return f;			
	}
	
	public String getAddpayeeTopMessage(){
	return null;		
	}
	
	// The text besides the add payee box on top of BPUI page that says "Need to pay someone new?"
	public String getTopPanelLabel(){
		return(driver.findElement(By.className("top-panel-search-label")).getText());
	}
	
	// Error that we see when we add a single alphabet payee on top
	public String getErrorTopPanel(){		
		return (driver.findElement(By.className("x-menu")).getText());
	}
	
	// The icon associated with the error that we see when we add a single alphabet payee on top
	public boolean IsErrorIconTopPanelPresent(){
		return (driver.findElement(By.className("icon-error")))!=null;
	}

	
	// CALENDER	

	// To click a active date on the calender
	public void clickCalenderDate(String payeename, String actualdate) throws InterruptedException{
		Hashtable<Integer, String> months = new Hashtable<Integer, String>();
		months.put(1, "January");
		months.put(2, "February");
		months.put(3, "March");
		months.put(4, "April");
		months.put(5, "May");
		months.put(6, "June");
		months.put(7, "July");
		months.put(8, "August");
		months.put(9, "September");
		months.put(10, "October");
		months.put(11, "November");
		months.put(12, "December");
		
		String[] y = actualdate.split("/");

		Integer MonthNumber = Integer.parseInt(y[0]);
		Integer calenderdate, dateprovided;
		String Month = (String) months.get(MonthNumber);
		String date = y[1];
		String year = y[2];
		dateprovided = Integer.parseInt(date);
		
		String id = getPayeeId(payeename);
		driver.findElement(By.id(id)).findElement(By.className("x-form-date-trigger")).click();
		while(!driver.findElement(By.className("x-date-pickerplus")).findElement(By.className("x-date-pickerplus-middle")).getText().equalsIgnoreCase(Month + " '"+ year)){
			driver.findElement(By.className("x-date-right")).findElement(By.className("x-unselectable")).click();
			System.out.println(driver.findElement(By.className("x-date-pickerplus")).findElement(By.className("x-date-pickerplus-middle")).getText());
			Thread.sleep(5000);
		}
		
		List<WebElement> dates = driver.findElements(By.className("x-date-pickerplus")).get(0).findElements(By.className("x-date-active"));
		if(!driver.findElement(By.className("x-date-pickerplus")).findElement(By.className("x-date-pickerplus-middle")).getText().equalsIgnoreCase(Month + " '"+ year)){
		dates = driver.findElements(By.className("x-date-pickerplus")).get(1).findElements(By.className("x-date-active"));
		}
		Iterator<WebElement> d = dates.iterator();
		WebElement x;
		while(d.hasNext()){
			x= d.next();
			calenderdate = Integer.parseInt(x.findElement(By.className("x-date-date")).getText());
			if(calenderdate.equals(dateprovided)){
				x.findElement(By.className("x-date-date")).click();
				break;			
				}}
}

	// To get the next immediate available by looking from the calender
	public Date nextImmidiateAvailableDueDate(String payeename) throws ParseException, InterruptedException{
		String id = getPayeeId(payeename);
		driver.findElement(By.id(id)).findElement(By.className("x-form-date-trigger")).click();
		
		List<WebElement> dates = driver.findElements(By.className("x-date-active"));
		Iterator<WebElement> d = dates.iterator();
		WebElement x;
		while(d.hasNext()){
			x= d.next();
			String y = x.getAttribute("class");		
			if(!y.contains("x-date-disabled")){
				x.findElement(By.className("x-date-date")).click();
				break;
			}
		}
			    String dateString = driver.findElement(By.id(payeeId)).findElement(By.className("send-on-date")).findElement(By.className("x-form-text")).getAttribute("value");
			    System.out.println(dateString);
			    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
			    Date convertedDate = dateFormat.parse(dateString); 
			    return convertedDate;
			  } 
	
	// To add a certain number of months, days, year to the current date
	public String addTimetoCurrentDate(int months, int days, int years){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		cal.add(Calendar.YEAR, years);
		cal.add(Calendar.MONTH, months);
		Date modifiedDate = cal.getTime();
		System.out.println("1 year ago: " + cal.getTime());	
	    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy"); 
	    String newDate = dateFormat.format(modifiedDate);
	    System.out.println(newDate);
		return newDate;
	}
	
	

	
	// To get the automatic payment status
	public boolean getAutomaticPaymentstatus(String payeename){
		String id = getPayeeId(payeename);
			try{
				WebElement AutomaticPaymentSection = driver.findElement(By.id(id)).findElement(By.className("automatic-payment-header-text"));
			}
			catch(NoSuchElementException e){
				return false;
			}
		return true;
		
	}


	
	// To get the number of automatic payments set for automatic payments
	public String getAutomaticPaymentNumberofPayments(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("recurringEnddate")).getText();
	}

	
//METHODS FOR ADD MANAGED PAYEE TEST CASES
	
	// Help text inside the top add payee box
	public String getTopAddPayeeHelptext(){
		return driver.findElements(By.className("searchPayeeComboCt")).get(0).getAttribute("value");
	}
	
	// Help text inside the bottom add payee box
	public String getBottomAddPayeeHelptext(){
		return driver.findElements(By.className("searchPayeeComboCt")).get(1).getAttribute("value");
	}
	
	// Zipcode label for a three factor payee
	public String ThreeFactorPayeeZipText() throws InterruptedException{
		Thread.sleep(5000);
		return(driver.findElements(By.className("zipfields")).get(1).findElement(By.id("zipLabel")).getText());		
	}
	
	// To check if the window is for a two factor payee
	public boolean TwoFactorPayeeValidation(){	
		return (!driver.findElements(By.className("zipfields")).get(1).findElement(By.id("zipLabel")).isDisplayed());
	}

	// To check if the window is for a three factor payee
	public boolean ThreeFactorPayeeValidation(){
		return ((driver.findElements(By.className("zipfields")).get(1).findElement(By.id("zipLabel")).isDisplayed()) && !driver.findElements(By.id("addressLabel")).get(1).isDisplayed());
	}
	
	// To check if the window is for a six factor payee
	public boolean SixFactorPayeeValidation(){
		return ((driver.findElements(By.id("addressLabel")).get(1).isDisplayed()));
	}

	// Return the factor type of the payee 
	public Integer gettypeofPayee(String payeename) throws InterruptedException{
		searchTopPayee(payeename);
		Thread.sleep(5000);
		driver.findElement(By.id("searchPayeeButton")).click();
		Thread.sleep(5000);
		if (TwoFactorPayeeValidation())
			return 2;
		if(ThreeFactorPayeeValidation())
		return 3;
		if(SixFactorPayeeValidation())
			return 6;
		return null;
	}

	// "Other questions" link for a two factor payee
	public String getTwoFactorPayeeHelpText(){
		return(driver.findElements(By.id("newpayee-helpLink"))).get(1).getText();
	}
	
	// To enter two factor payee details
	public void enterTwoFactorPayeeDetails(String payeeName, String accountNumber, String accountNumber2) throws InterruptedException{
		searchTopPayee(payeeName);
		driver.findElement(By.id("searchPayeeButton")).click();
		// Wait for search to complete		
		Thread.sleep(5000);
		enterAccountNumber1(accountNumber);
		Thread.sleep(2000);
		enterAccountNumber2(accountNumber2);
//      newPayeeSave.click();
	}
	
	// To enter three factor payee details
	public void enterThreeFactorPayeeDetails(String payeeName, String accountNumber, String accountNumber2, String zip, String date) throws InterruptedException{
		searchTopPayee(payeeName);
		driver.findElement(By.id("searchPayeeButton")).click();
		// Wait for search to complete		
		Thread.sleep(5000);
		enterAccountNumber1(accountNumber);
		Thread.sleep(2000);
		enterAccountNumber2(accountNumber2);
		Thread.sleep(2000);
		enterDate(date);
		Thread.sleep(2000);
		enterZip(zip);
//      newPayeeSave.click();
	}
	
	public void enterDate(String date) throws InterruptedException{
		dateElm= driver.findElement(By.id("newPayeeDueDateField"));
		Thread.sleep(5000);
		dateElm.click();
		dateElm.clear();
		dateElm.sendKeys(date, (Keys.ENTER));
	}
	
	// To check if the add payee button is enabled for 2/3 factor payee
	public boolean isAddPayeeButtonEnabled(){
		return (driver.findElements(By.id("newPayeeSave"))).get(1) == null;
	}
	
	// 
	public String getknownMerchantAccountErrorMessage(){	
	return(driver.findElements(By.className("newpayee-youraccount-wrapper"))).get(1).findElement(By.className("newpayee-form-youraccountdiv")).findElements(By.className("parent")).get(1).findElement(By.className("errorDiv")).getText();		
	}
	
	// Text below the billing address for a two factor payee
	public String getBillingAddressTwoFactorPayee(){		
		return(driver.findElements(By.className("newpayee-datastore-addr1"))).get(1).getText();
	}
	
	// The dropdown elements when you search for a payee with a few alphabets
	public List<String> returnSearchPayeesDropdown(String SearchText) throws InterruptedException{		
		searchTopPayee(SearchText);
		WebElement elmt;
		List<WebElement> SearchPayeesDropdown =  driver.findElements(By.className("x-combo-list-inner")).get(3).findElements(By.className("search-item"));
		System.out.println(SearchPayeesDropdown);
		Thread.sleep(5000);
		ArrayList<String> SearchPayeesDropdownElements = new ArrayList<String>() ;
		Iterator<WebElement> x = SearchPayeesDropdown.iterator();		
		while(x.hasNext()){
		elmt = x.next();
		SearchPayeesDropdownElements.add(elmt.getText()) ;
		}
		return SearchPayeesDropdownElements;
		}
	
	// To check if the payees in the dropdown for a search will be sorted
	public boolean verifyIfPayeesDropdownSorted(String SearchText) throws InterruptedException{	
		List<String> DropdownPayeeList = returnSearchPayeesDropdown(SearchText);
		System.out.println("Original Payee List: " + DropdownPayeeList);
		List<String> tempDropdownPayeeList = new ArrayList<String>(DropdownPayeeList);
		Collections.sort(tempDropdownPayeeList, new Comparator<String>() {
		public int compare(String o1, String o2) {
		return o1.compareToIgnoreCase(o2);
		}});
		System.out.println("Payee list after sorting: " + tempDropdownPayeeList);
		return tempDropdownPayeeList.equals(DropdownPayeeList);
	}
	
	public void clickCancelinAddPaymentWindow(){
		driver.findElements(By.id("newPayeeCancel")).get(1).click();
	}

	
	
	// METHODS FOR VIEW REMINDER
	
	// To get the displayed amount text when a reminder is set up
	public String getAmountextReminder(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("amountDiv")).getText();
	}
	
	// To get the date displayed on the calender
	public String getReminderDateDisplayed(String payeename){
		String id = getPayeeId(payeename);
		String month = driver.findElement(By.id(id)).findElement(By.className("calendar-icon-month")).getText(); 
		String day = driver.findElement(By.id(id)).findElement(By.className("calendar-icon-day")).getText();
		return (month + " " + day);
	}
	
	// To click the skip link for a reminder
	public void clickSkipLinkforReminder(String payeename){
		String id = getPayeeId(payeename);
	driver.findElement(By.id(id)).findElement(By.className("skipLink")).click();
	}
	
	// To check the skip link for a reminder
	public boolean checkSkipLinkforReminder(String payeename){
		String id = getPayeeId(payeename);
	return driver.findElement(By.id(id)).findElement(By.className("skipLink")).getText().equalsIgnoreCase("Skip");
	}
	
	// To get the payment due days for a reminder
	public String getPaymentduedaysforReminder(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("duedays")).getText();
	}
	
	// To get the label text when reminder is not set for a payee
	public String getNoReminderLabel(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("nextDueTextDiv")).getText();
	}
	
	// To get the label text for reminder when automatic payment is set for a payee
	public String getNoReminderLabelwhenAutomaticPaymentSet(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("boldText")).findElement(By.xpath("..")).getText();
	}
	
	// To get the amount label for a reminder
	public String getReminderAmountLabel(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("amountDiv")).getText();
	}
	
	// To get number of days due for a reminder
	public Integer getDuedaysforReminder(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		String duedaystext = driver.findElement(By.id(id)).findElement(By.className("duedays")).getText();
		Pattern daysOnly = Pattern.compile("\\d+");
		Matcher makeMatch = daysOnly.matcher(duedaystext);
		makeMatch.find();
		String inputdays = makeMatch.group();
		return Integer.parseInt(inputdays);
	}
	
	public boolean isRemindersetforPayee(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickRemindersandAlertsforPayee(payeename);
		if(driver.findElement(By.id(id)).findElement(By.className("green")).getText().equalsIgnoreCase("Currently On"))
			return true;
		else if (driver.findElement(By.id(id)).findElements(By.className("grey")).get(3).getText().equalsIgnoreCase("Currently Off"))
			return false;
		return false;
	}
	
	// To open the reminders and alerts tray in options
	public void clickRemindersandAlertsforPayee(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
				if(!getDrawerStatusByNicknameandname(payeename))
					openPayeeOptionsTraybyNameandNickname(payeename);
					driver.findElement(By.id(id)).findElements(By.className("x-tab-strip-text")).get(0).click();
		}
	
	// To get the drawer status by giving payeename and payee nick name
	public boolean getDrawerStatusByNicknameandname(String payeeName){
		String id = getPayeeId(payeeName);
		WebElement drawer = driver.findElement(By.id(id)).findElement(By.className("drawer"));
		return (drawer).isDisplayed();
	}
	
	// To check if set a reminder link is present
	public boolean checkSetareminderLinkPresent(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(payeeId)).findElement(By.className("setReminderLink")).getText().equalsIgnoreCase("Set a reminder");
	}
	
	// To return the skip reminder success message
	public String getskipremindersuccess(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("green")).getText();
	}
	
	// To return the skip reminder success message saying the reminder is skipped
	public String getskipremindersuccessmessage(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("green")).findElement(By.xpath("..")).getText();
	}
	
	// To get the payee tile error for a reminder
	public String getPayeetileerrorforreminder(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("red")).getText();
	}
	
	
// METHODS FOR MAKING RECURRING PAYMENTS
	
	// Labels when automatic payments are not set up.
	public List<String> getAutomaticPaymentLabelswhennotsetUp(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		List<WebElement> RecurringPayments = null;
		List<String> RecurringPaymentLabels = new ArrayList<String>();
		RecurringPayments = driver.findElement(By.id(id)).findElements(By.className("automatic-payment-label"));
		WebElement RecPay ;
		int count =0;
		Iterator<WebElement> RecPayIt = RecurringPayments.iterator();
		RecurringPaymentLabels.add(driver.findElement(By.id(id)).findElement(By.className("automatic-payment-header-text")).getText());
		while(RecPayIt.hasNext() && count<6){
		RecPay = RecPayIt.next();
		RecurringPaymentLabels.add(RecPay.getText());
		count++;
		}
		RecurringPaymentLabels.add(driver.findElement(By.id(payeeId)).findElement(By.className("automatic-ebill-memotext")).getText());
		return RecurringPaymentLabels;		
	}
	
	// Labels when automatic payments are set up.
	public List<String> getAutomaticPaymentLabelswhensetUp(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		List<WebElement> RecurringPayments = null;
		List<String> RecurringPaymentLabels = new ArrayList<String>();
		RecurringPayments = driver.findElement(By.id(id)).findElements(By.className("automatic-payment-content")).get(1).findElements(By.className("automatic-payment-label"));
		WebElement RecPay ;
		int count =0;
		Iterator<WebElement> RecPayIt = RecurringPayments.iterator();
		RecurringPaymentLabels.add(driver.findElement(By.id(id)).findElement(By.className("automatic-payment-header-text")).getText());
		while(RecPayIt.hasNext() && count<6){
		RecPay = RecPayIt.next();
		RecurringPaymentLabels.add(RecPay.getText());
		count++;
		}
		return RecurringPaymentLabels;		
	}
	
	// To get all the help links present for recurring payment
	public List<WebElement> getHelpLinksforRecurringPayments(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		Thread.sleep(5000);
		List<WebElement> helpLinks = new ArrayList<WebElement>();
		WebElement payee = driver.findElement(By.id(id));
		helpLinks.add(payee.findElement(By.className("automatic-payment-amount-link")));
		helpLinks.add(payee.findElement(By.className("automatic-payment-startdate-link")));
		helpLinks.add(payee.findElement(By.className("automatic-ebill-memotext")).findElement(By.tagName("a")));
		helpLinks.add(payee.findElement(By.className("automatic-payment-questions")));
		return helpLinks;
	}
	
	// To open the options tray for a payee
	public void openPayeeOptionsTraybyNameandNickname(String payeeName){
		String id = getPayeeId(payeeName);
		if(!getDrawerStatusByNicknameandname(payeeName))
		driver.findElement(By.id(id)).findElement(By.className("toggle-drawer-button")).click();
	}
	
	// To open the automatic payments tray in options
	public void clickAutomaticPaymentforPayee(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
				if(!getDrawerStatusByNicknameandname(payeename))
					openPayeeOptionsTraybyNameandNickname(payeename);
					driver.findElement(By.id(id)).findElements(By.className("x-tab-strip-text")).get(1).click();
		}

	// To return a payee as a WebElement
	public WebElement getPayeeElement(String payeename) throws InterruptedException{
		Thread.sleep(5000);
		List<WebElement> payees = driver.findElement(By.id("payee-list-panel")).findElements(By.className("payee"));
		Iterator<WebElement> element = payees.iterator();
		WebElement elmT;
		while(element.hasNext()){
			elmT = element.next();
			if((elmT.findElement(By.className("main-payment-header")).findElement(By.className("payee-name")).getText().equalsIgnoreCase(payeename)) && elmT.findElement(By.className("options-open-drawer-icon")).getText().equalsIgnoreCase("View Options")){
				return elmT;}}
		return elmT = null;
		}

	// To return the funding accounts present for a payee in the automatic payments tab 
	public List<String> getFundingAccountforAutomaticPayments(String payeename) throws InterruptedException{
		clickAutomaticPaymentforPayee(payeename);
		String id = getPayeeId(payeename);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-fundingaccount-comboct")).findElement(By.className("x-form-trigger")).click();
		List<WebElement> fundingAccounts = driver.findElement(By.className(id + "-automatic-payment-funding-account-list")).findElements(By.className("x-combo-list-item"));
		//wait.until(visibilityOfElementLocated(By.className("automatic-payment-funding-account-list"))).findElements(By.className("x-combo-list-item"));
		WebElement fundingAccountElmt;
		Iterator<WebElement> elements = fundingAccounts.iterator(); 
		List<String> fundingAccountsforAutomaticPayments= new ArrayList<String>();
		while (elements.hasNext()){
			fundingAccountElmt = elements.next();
			fundingAccountsforAutomaticPayments.add(fundingAccountElmt.getText());
			}
		return fundingAccountsforAutomaticPayments;
	}
	
	// To return the frequencies for automatic payments for a payee in the automatic payments tab 
	public List<String> getFrequencyforAutomaticPayments(String payeename) throws InterruptedException{
		clickAutomaticPaymentforPayee(payeename);
		String id = getPayeeId(payeename);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-frequency-comboct")).findElement(By.className("x-form-trigger")).click();
		Thread.sleep(5000);
		List<WebElement> frequency = driver.findElement(By.className(id+"-frequency-options")).findElements(By.className("x-combo-list-item"));
		Iterator<WebElement> frequencies = frequency.iterator();
		WebElement freq;
		List<String> AutomaticpaymentFreqs = new ArrayList<String>();
		while (frequencies.hasNext()){
			freq = frequencies.next();
			AutomaticpaymentFreqs.add(freq.getText());
		}
		return AutomaticpaymentFreqs;
	}
	
	// To enter the amount for an automatic payment
	public void enterAutomaticPaymentAmount(String payeename, String amount) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount-ct")).findElement(By.tagName("input")).clear();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount-ct")).findElement(By.tagName("input")).click();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount-ct")).findElement(By.tagName("input")).sendKeys(amount, Keys.ENTER);
	}
	
	// To modify the amount for an automatic payment in due date model
	public void modifyAutomaticPaymentAmount(String payeename, String amount) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		Thread.sleep(10000);
		System.out.println(payeeId);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount1-ct")).findElement(By.tagName("input"));
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount1-ct")).findElement(By.tagName("input")).click();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount1-ct")).findElement(By.tagName("input")).clear();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount1-ct")).findElement(By.tagName("input")).click();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount1-ct")).findElement(By.tagName("input")).sendKeys(amount, Keys.ENTER);
	}
	
	// To fetch the amount that was set for for an automatic payment for due date model
	public String getAutomaticPaymentsetAmount(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		Thread.sleep(5000);
		return driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount1-ct")).findElement(By.tagName("input")).getAttribute("value").substring(1);
	}
	
	//To fetch the amount that was set for an automatic payment for send date model
	public String getAutomaticPaymentsetAmountforSendDateModel(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		Thread.sleep(5000);
		return driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount-ct")).findElement(By.tagName("input")).getAttribute("value").substring(1);
	}
	
	
	// To enter the date for an automatic payment
	public void enterAutomaticPaymentDate(String payeename, String date) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		driver.findElement(By.id(id)).findElement(By.className("startdate")).clear();
		driver.findElement(By.id(id)).findElement(By.className("startdate")).click();
		driver.findElement(By.id(id)).findElement(By.className("startdate")).sendKeys(date, Keys.ENTER);
	}
	
	// To enter the memo for an automatic payment
	public void enterAutomaticPaymentMemo(String payeename, String memo) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		driver.findElement(By.id(id)).findElement(By.className("auto-payment-memo")).clear();
		driver.findElement(By.id(id)).findElement(By.className("auto-payment-memo")).click();
		driver.findElement(By.id(id)).findElement(By.className("auto-payment-memo")).sendKeys(memo, Keys.ENTER);
	}
	
/*	// To click the save button for an automatic payment
	public void clickAutomaticaPaymentSave(String payeename, String payeenickname) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename,payeenickname);
		driver.findElement(By.id(payeeId)).findElement(By.className("automatic-payment-save")).click();
	}*/
	
	// To select the end date for an automatic payment
	public void selectEndDateforAutomaticPayment(String payeename, String endDateSelect) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		if (endDateSelect.equalsIgnoreCase("noenddate")){
			driver.findElement(By.id(id)).findElement(By.className("radio1")).click();
		}
		else if (endDateSelect.equalsIgnoreCase("enddate")){
			driver.findElement(By.id(id)).findElement(By.className("radio3")).click();
		}}
	
	// To select the number of days for automatic payments
	public void enterAutomaticPaymentDays(String payeename, String AutomaticEndDate) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-days")).findElement(By.tagName("input")).clear();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-days")).findElement(By.tagName("input")).click();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-days")).findElement(By.tagName("input")).sendKeys(AutomaticEndDate, Keys.ENTER);
	}
	
	// To click the don't save an automatic payment link.
	public void clickdontSaveAutomaticPayment(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-cancel")).click();
	}
	
	// To close the options drawer for a payee
	public void closeOptionsDrawer(String payeename){
		String id = getPayeeId(payeename);
		if(getDrawerStatusByNicknameandname(payeename)){
			driver.findElement(By.id(id)).findElement(By.className("close-drawer")).click();
		}}
	
	// To get the error message for the start date in Automatic Payments	
	public String getStartDateErrorAutomaticPayment(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("start-date-error")).getText();	
	}
	
	// To get the error message for the amount in Automatic Payments	
	public String getAmountErrorAutomaticPayment(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElements(By.className("automatic-payment-errorDiv")).get(1).getText();	
	}
	
	// To get the error message for the funding account in Automatic Payments	
	public String getFundingAccountErrorAutomaticPayment(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElements(By.className("automatic-payment-errorDiv")).get(0).getText();
	}
	
	// To get the error message for the amount in Automatic Payments	
	public String getSaveMessageAutomaticPayment(String payeename){
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("automatic-payment-save-message")).getText();	
	}
	
	// To select the funding account for an automatic payment
	public void selectAutomaticPaymentFundingAccount(String payeename, String fundingAccount) throws InterruptedException{
		goToAutomaticPaymentTab(payeename);
		String id = getPayeeId(payeename);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-fundingaccount-comboct")).findElement(By.className("x-form-trigger")).click();
		List<WebElement> fundingAccounts = driver.findElement(By.className(id + "-automatic-payment-funding-account-list")).findElements(By.className("x-combo-list-item"));
		Thread.sleep(5000);
		WebElement fundingAccountElmt;
		Iterator<WebElement> elements = fundingAccounts.iterator(); 
		while (elements.hasNext()){
			fundingAccountElmt = elements.next();
			if(fundingAccountElmt.getText().equalsIgnoreCase(fundingAccount))
				fundingAccountElmt.click();
			}}
	

	
	// To make an automatic payment
	public void makeAutomaticPayment(String payeename, String fundingAccount, String amount, String frequency, String date, String endDateSelect, String AutomaticEndDate, String memo) throws InterruptedException{
		selectAutomaticPaymentFundingAccount(payeename, fundingAccount);
		enterAutomaticPaymentAmount(payeename, amount);
		selectAutomaticPaymentFrequency(payeename, frequency);
		enterAutomaticPaymentDate(payeename, date);
		if(endDateSelect.equalsIgnoreCase("enddate")){
		selectEndDateforAutomaticPayment(payeename, endDateSelect);
		enterAutomaticPaymentDays(payeename, AutomaticEndDate);}
		enterAutomaticPaymentMemo(payeename, memo);
		clickAutomaticaPaymentSave(payeename);
	}
	
	// To select the frequency for an automatic payment
	public void selectAutomaticPaymentFrequency(String payeename, String frequency) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-frequency-comboct")).findElement(By.className("x-form-trigger")).click();
		Thread.sleep(5000);
		List<WebElement> frequencyAP = driver.findElement(By.className(id+"-frequency-options")).findElement(By.className("x-combo-list-inner")).findElements(By.className("x-combo-list-item"));
		Iterator<WebElement> frequencies = frequencyAP.iterator();
		WebElement freq;
		while (frequencies.hasNext()){
			freq = frequencies.next();
			if(freq.getText().equalsIgnoreCase(frequency)){
				freq.click();}
		}}
	
	// To get the default number of automatic payments for automatic payments
	public String getAutomaticPaymentDefaultNumberofPayments(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		//selectEndDateforAutomaticPayment(payeename, payeenickname, "enddate");
		//return driver.findElement(By.id(payeeId)).findElement(By.className("automatic-payment-days")).findElement(By.tagName("input")).getAttribute("value");
		clickAutomaticPaymentforPayee(payeename);
		Thread.sleep(5000);
		return driver.findElement(By.id(id)).findElement(By.className("recurringEnddate")).getText().substring(6,7);
	}
	
	// To enter the number of payments for an automatic payment
	public void enterAutomaticPaymentDefaultNumberofPayments(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		selectEndDateforAutomaticPayment(payeename, "enddate");
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-days")).findElement(By.tagName("input")).clear();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-days")).findElement(By.tagName("input")).click();
		driver.findElement(By.id(id)).findElement(By.className("automatic-payment-days")).findElement(By.tagName("input")).sendKeys("8", Keys.ENTER);
	}
	
	//To get the default amount for an automatic payment
	public String getAmountAutomaticPayment(String payeename) throws InterruptedException{
		String id = getPayeeId(payeename);
		clickAutomaticPaymentforPayee(payeename);
		return driver.findElement(By.id(id)).findElement(By.className("automatic-payment-amount-ct")).findElement(By.tagName("input")).getAttribute("value");
	}
	
	// To get the frequency that is selected
	public String getAutomaticPaymentsSelectedFrequency(String payeename) throws InterruptedException{
		clickAutomaticPaymentforPayee(payeename);
		String id = getPayeeId(payeename);
		return driver.findElement(By.id(id)).findElement(By.id(payeeId+"-frequency-selected")).getAttribute("value");
	}
	
	// Click "Don't save changes" for a recurring payment
	public void dontSaveChangesRecurringPayment(String payeename) throws InterruptedException{
		clickAutomaticPaymentforPayee(payeename);
		String id = getPayeeId(payeename);
		driver.findElement(By.id(id)).findElements(By.className("automatic-payment-cancel")).get(0).click();
	}
	
	// Check the number of similar payments in Scheduled Payments list
	public Integer getNumberofSmiliarScheduledPayments(String payeename, String amount) throws InterruptedException{
	Thread.sleep(5000);
	WebElement receipt = driver.findElement(By.id("receipt"));
	List<WebElement> ScheduledPayments = (List<WebElement>) receipt.findElement(By.className("scheduled-payments")).findElements(By.name("parent"));
	Iterator<WebElement> elements = ScheduledPayments.iterator();
	WebElement elmt;
	Integer countpayments = 0;
	while(elements.hasNext()){
	elmt = elements.next();
	if ((elmt.findElement(By.className("payeename"))).getText().equalsIgnoreCase(payeename) && (elmt.findElement(By.className("receiptPaymentAmount"))).getText().equalsIgnoreCase("$"+amount)){
		countpayments++;
	}}
	return countpayments;
	}

	// Check number of similar scheduled payments in Payment history

	
	
	
	
	
	// Get number of days for a month in a given year
/*	public Integer numberofDaysfromPresentdayinMonth(Integer year, Integer Month, Integer day ){
		Calendar cal = new GregorianCalendar(1999, Month, 1);	
	}*/
	
/*	// Get the next immediate payment date in due date model
	public Date getNextImmidiateDeliveryDateAvaialable(String payeename, String payeenickname) throws InterruptedException{
		String id = getPayeeId(payeename);
		String DATE_FORMAT = "MM/dd/yyyy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(DATE_FORMAT);
		Date date = new Date();	
		Calendar c1 = Calendar.getInstance(); 
		c1.setTime(date);
		String NextDate = sdf.format(c1.getTime());
		try{
		do{
		c1.add(Calendar.DATE,1);
		date = c1.getTime();
		NextDate= sdf.format(date);
		//System.out.println("Date + 20 days is : " + sdf.format(c1.getTime()));
		driver.findElement(By.id(payeeId)).findElement(By.className("send-on-date")).findElement(By.className("x-form-text")).clear();
		driver.findElement(By.id(payeeId)).findElement(By.className("send-on-date")).findElement(By.className("x-form-text")).click();
		driver.findElement(By.id(payeeId)).findElement(By.className("send-on-date")).findElement(By.className("x-form-text")).sendKeys(NextDate);
		driver.findElement(By.id(payeeId)).findElement(By.className("payment-amount-field")).click();
		Thread.sleep(5000);
		}while(!driver.findElement(By.id(payeeId)).findElement(By.className("deliver-by-label-error")).getText().equalsIgnoreCase(NextDate+" is an invalid date"));	
	}
		catch(Exception e){
		}
		return date;
	}*/
	
	// Click on Set a reminder for a payee
	public void clickSetaReminderforPayee(String payeename ){
		String id = getPayeeId(payeename);
		driver.findElement(By.id(id)).findElement(By.className("reminder")).click();
	}
	
	// To get the number of weeks left from a given date which is equal to the number of payments left for the month
	public Integer getNumberofWeeksLeftinMonth(Date date){
		Calendar cal = new GregorianCalendar(date.getYear()+1900, date.getMonth()+1, date.getDate());
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		Integer x = new Integer((days-date.getDate()));
		return x/7;
	}
		
	
	   // To get the payee id by name and nick name
	   public String getPayeeIdByNicknameandName(String payeeName, String payeenickname){
	        List<WebElement> payees = (List<WebElement>) driver.findElement(By.id("payee-list-panel")).findElements(By.className("payee"));
	        Iterator<WebElement> element = payees.iterator();
	        WebElement elmT;
	        while(element.hasNext()){
	            elmT = element.next();
	            if(payeeName.equalsIgnoreCase(elmT.findElement(By.className("payee-name")).getText()) && (elmT.findElement(By.className("payee-nickname"))).getText().equalsIgnoreCase(payeenickname))
	            {
	                return elmT.getAttribute("id");                    
	        }}
	        return null;        
	    }
	   
	   
		// To check if the scheduled payment is present on the scheduled payments list
		public boolean checkIfScheduledPaymentPresent(String payeename, String amount, String date) throws InterruptedException{
			Thread.sleep(5000);
			WebElement receipt = driver.findElement(By.id("receipt"));
			List<WebElement> ScheduledPayments = (List<WebElement>) receipt.findElement(By.className("scheduled-payments")).findElements(By.name("parent"));
			Iterator<WebElement> elements = ScheduledPayments.iterator();
			WebElement elmt;
			while(elements.hasNext()){
			elmt = elements.next();	
			if ((elmt.findElement(By.className("payeename"))).getText().equalsIgnoreCase(payeename) && (elmt.findElement(By.className("receiptPaymentAmount"))).getText().equalsIgnoreCase("$"+amount) && (elmt.findElement(By.className("receiptDate"))).getText().equalsIgnoreCase(date))
				return true;
			}
			return false;
		}
		
		// To insert a text memo for a scheduled payment
		public void insertMemoforScheduledPayment(String payeename, String amount,  String date, String memo) throws InterruptedException{
		clickEditIcononScheduledPayments(payeename, amount, date);

		driver.findElement(By.className("x-form-textarea")).click();
		driver.findElement(By.className("x-form-textarea")).sendKeys(memo, (Keys.ENTER));
		}
		
		// To click the edit icon for a scheduled payment for a payee on the scheduled payment frame
		public void clickEditIcononScheduledPayments(String payeename, String amount,  String date) throws InterruptedException{
			try{
				driver.findElement(By.className("x-window-body"));
			}
			catch(NoSuchElementException e){
				WebElement receipt = driver.findElement(By.id("receipt"));
				List<WebElement> ScheduledPayments = (List<WebElement>) receipt.findElement(By.className("scheduled-payments")).findElements(By.name("parent"));
				Iterator<WebElement> elements = ScheduledPayments.iterator();
				WebElement elmt;
				while(elements.hasNext()){
				elmt = elements.next();
				if ((elmt.findElement(By.className("payeename"))).getText().equalsIgnoreCase(payeename) && (elmt.findElement(By.className("receiptPaymentAmount"))).getText().equalsIgnoreCase("$"+amount) && (elmt.findElement(By.className("receiptDate"))).getText().equalsIgnoreCase(date))
					elmt.findElement(By.className("icon-edit")).click();
				Thread.sleep(5000);
				}}}

		// To click the save payment button for a scheduled payment
		public void clickSaveEditScheduledPayment(String payeename, String amount,  String date) throws InterruptedException{		
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			driver.findElements(By.className("editPaymentSave")).get(1).click();
	}

		
		// Error thrown when a date exceeding the length permitted is entered
		public String getMemoErrorScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			return driver.findElements(By.className("edit-statusText")).get(1).getText();
		}

		// Close the edit payment window
		public void closeEditPaymentWindow(){
			try{
				driver.findElement(By.className("x-window")).findElement(By.id("x-tool-close")).click();
			}
			catch(Exception e){
			}
		}

		// Amount that is present in the amount field in the payment window
		public String getDefaultAmountScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			return driver.findElement(By.className("edit-payment-amount-field")).getAttribute("value");
		}

		// Date that is present in the amount field in the payment window
		public String getDefaultDateScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			System.out.println(driver.findElements(By.className("edit-payment-date")).get(1).findElement(By.className("x-form-text")).getAttribute("value"));
			return driver.findElements(By.className("edit-payment-date")).get(1).findElement(By.className("x-form-text")).getAttribute("value");
		}

		
		// The funding accounts for a scheduled payment
		public List<String> getFundingAccountsScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			driver.findElements(By.className("edit-payment-fundingaccount")).get(1).findElement(By.className("x-form-trigger")).click();
			Thread.sleep(5000);
			List<WebElement> FundingAccountelements = driver.findElements(By.className("x-combo-list-inner")).get(3).findElements(By.className("x-combo-list-item"));
			List<String> FundingAccounts = new ArrayList<String>();
			Iterator<WebElement> elements = FundingAccountelements.iterator();
			WebElement elmt;
			while (elements.hasNext()){
				elmt=elements.next();
				FundingAccounts.add(elmt.getText());
			}
			return FundingAccounts;
		}

		// The categories available for a scheduled payment
		public List<String> getCategoriesScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			driver.findElements(By.className("edit-payment-category")).get(1).findElement(By.className("x-form-trigger")).click();
			Thread.sleep(5000);
			List<WebElement> Categoryelements = driver.findElements(By.className("x-combo-list-inner")).get(4).findElements(By.className("x-combo-list-item"));
			List<String> Categories = new ArrayList<String>();
			Iterator<WebElement> elements = Categoryelements.iterator();
			WebElement elmt;
			while (elements.hasNext()){
				elmt=elements.next();
				Categories.add(elmt.getText());
			}
			return Categories;
		}

		// To change the funding accounts for a scheduled payment
		public void changeFundingAccountforScheduledpayment(String payeename, String amount, String date, String account) throws InterruptedException{		
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			driver.findElements(By.className("edit-payment-fundingaccount")).get(1).findElement(By.className("x-form-trigger")).click();
			Thread.sleep(5000);
			List<WebElement> FundingAccountelements = driver.findElements(By.className("x-combo-list-inner")).get(3).findElements(By.className("x-combo-list-item"));
			Iterator<WebElement> elements = FundingAccountelements.iterator();
			WebElement elmt;
			while (elements.hasNext()){
				elmt=elements.next();
				if(elmt.getText().equalsIgnoreCase(account))
					elmt.click();
			}}
		
		// funding account that is present in the amount field in the payment window
		public String getFundingAccountforScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			return driver.findElements(By.className("edit-payment-fundingaccount")).get(1).findElement(By.className("x-form-text")).getAttribute("value");
		}

		// To change the category for a scheduled payment
		public void changeCategoryforScheduledPayment(String payeename, String amount, String date, String category) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			driver.findElements(By.className("edit-payment-category")).get(1).findElement(By.className("x-form-trigger")).click();
			Thread.sleep(5000);
			List<WebElement> Categoryelements = driver.findElements(By.className("x-combo-list-inner")).get(4).findElements(By.className("x-combo-list-item"));
			Iterator<WebElement> elements = Categoryelements.iterator();
			WebElement elmt;
			while (elements.hasNext()){
				elmt=elements.next();
				if(elmt.getText().equalsIgnoreCase(category))
					elmt.click();
			}
	}

		// Category that is present in the amount field in the payment window
		public String getCategoryforScheduledPayment(String payeename, String amount, String date) throws InterruptedException{	
				clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			return driver.findElements(By.className("edit-payment-category")).get(1).findElement(By.className("x-form-text")).getAttribute("value");
		}

		// To change the date field for a scheduled payment
		public void editDateforScheduledPayment(String payeename, String amount, String date, String holiday)throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			driver.findElements(By.className("edit-payment-date")).get(1).findElement(By.className("x-form-text")).clear();
			driver.findElements(By.className("edit-payment-date")).get(1).findElement(By.className("x-form-text")).click();
			driver.findElements(By.className("edit-payment-date")).get(1).findElement(By.className("x-form-text")).sendKeys(holiday, (Keys.ENTER));
		}

		// To change the amount field for a scheduled payment
		public void editAmountforScheduledPayment(String payeename, String amount, String date, String TransactionLimit)throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			driver.findElement(By.className("edit-payment-amount-field")).clear();
			driver.findElement(By.className("edit-payment-amount-field")).click();
			driver.findElement(By.className("edit-payment-amount-field")).sendKeys(TransactionLimit, (Keys.ENTER));
		}

		// Error thrown when an invalid date is entered while editing a scheduled payment
		public String getDateErrorforScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			return driver.findElements(By.className("edit-payment-delivery-date")).get(1).getText();
		}

		// Error thrown when an invalid amount is entered while editing a scheduled payment
		public String getAmountErrorforScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			clickEditIcononScheduledPayments(payeename, amount, date);
			Thread.sleep(5000);
			return driver.findElements(By.className("edit-content-amounterror")).get(1).getText();
		}

		// To delete a scheduled payment
		public void deleteScheduledPayment(String payeename, String amount, String date, String ask) throws InterruptedException {
			clickcancelScheduledPayment(payeename, amount, date);
			Thread.sleep(5000);
			if (ask.equalsIgnoreCase("yes")){
			try
			{driver.findElements(By.name("cancelautomaticpayment")).get(4).click();}
			catch(Exception e){
				return;
			}
			}
			else if (ask.equalsIgnoreCase("no")){
				try{driver.findElements(By.name("canceldelete")).get(4).click();
			}
				catch(Exception e){
					return;
				}
				}
		}
		
		// Cancel schedule payment
		public void clickcancelScheduledPayment(String payeename, String amount, String date) throws InterruptedException{
			Thread.sleep(50000);
			    WebElement receipt = driver.findElement(By.id("receipt"));
				List<WebElement> ScheduledPayments = (List<WebElement>) receipt.findElement(By.className("scheduled-payments")).findElements(By.name("parent"));
				Iterator<WebElement> elements = ScheduledPayments.iterator();
				WebElement elmt;
				while(elements.hasNext()){
				elmt = elements.next();
				if ((elmt.findElement(By.className("payeename"))).getText().equalsIgnoreCase(payeename) && (elmt.findElement(By.className("receiptPaymentAmount"))).getText().equalsIgnoreCase("$"+amount) && (elmt.findElement(By.className("receiptDate"))).getText().equalsIgnoreCase(date))
					elmt.findElement(By.className("icon-delete")).click();
					Thread.sleep(25000);
				}}

		// To check if the scheduled payment is present on a payee tile
		public boolean checkScheduledPaymentonPayeeTile(String payeename, String amount, String date, String payeenickname){
			List<WebElement> payees = (List<WebElement>) driver.findElement(By.id("payee-list-panel")).findElements(By.className("upper-panel"));
			Iterator<WebElement> elements = payees.iterator();
			WebElement elmt;
			while(elements.hasNext()){	
				elmt = elements.next();
				if ((elmt.findElement(By.className("main-payment-header")).findElement(By.className("payee-name")).getText().equalsIgnoreCase(payeename)&& (elmt.findElement(By.className("main-payment-header")).findElement(By.className("payee-nickname"))).getText().equalsIgnoreCase(payeenickname)))	
				{
					List<WebElement> payments = (List<WebElement>)driver.findElements(By.className("historyPayment"));
					Iterator<WebElement> paymentElements = payments.iterator();
					WebElement paymentElmt;
					while(paymentElements.hasNext()){
						paymentElmt = paymentElements.next();
					if (paymentElmt.findElement(By.className("historyPaymentDate")).getText().equalsIgnoreCase(date) && paymentElmt.findElement(By.className("historyPaymentAmount")).getText().equalsIgnoreCase("$"+amount)){	
						return true;
					}}}}
			return false;
		}
		
		// To check if there is an automatic payment set for a payee
		public boolean getAutomaticPaymentsetStatus(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			clickAutomaticPaymentforPayee(payeename);
			try{
				return !(driver.findElement(By.id(id)).findElement(By.className("automatic-payment-header-off")).getText().equalsIgnoreCase("Currently Off"));
			}
			catch(ElementNotVisibleException e){
				return driver.findElement(By.id(id)).findElement(By.className("automatic-payment-header-on")).getText().equalsIgnoreCase("Currently On");
			}}
		
		// To turn off a payment rule for a payee if it has been set up.
		public void turnoffPaymentRule(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			clickAutomaticPaymentforPayee(payeename);
			if(getAutomaticPaymentsetStatus(payeename)){
			driver.findElement(By.id(id)).findElement(By.className("automatic-payment-turn-off")).click();
			Thread.sleep(5000);
			driver.findElements(By.name("cancelautomaticpayment")).get(4).click();}
		}

		// To turn off a reminder for a payee
		public void turnoffReminder(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			clickRemindersandAlertsforPayee(payeename);
			driver.findElement(By.id(id)).findElement(By.className("reminders-turn-off")).click();
		}

		// To create or modify a reminder for a payee
		public void createorModifyReminderforPayee(String payeename, String selectedfreq, String date, String amount) throws InterruptedException{
			selectFrequencyforRemindersandAlerts(payeename, selectedfreq);
			enterAmountforReminders(payeename, amount);
			enterDateforReminders(payeename, date);
			saveReminderandAlert(payeename);	
		}

		// To select a frequency for reminders and alerts
		public List<String> selectFrequencyforRemindersandAlerts(String payeename, String selectedfreq) throws InterruptedException{
			clickFrequencyforReminderandAlert(payeename);
			String id = getPayeeId(payeename);
			List<WebElement> frequencies = driver.findElement(By.className(id+"-rem-frequency-options")).findElements(By.className("x-combo-list-item"));
			Iterator<WebElement> freq = frequencies.iterator();
			List<String> frequenciesforReminders = new ArrayList<String>();
			WebElement frequency;
			while(freq.hasNext()){
				frequency = freq.next();
				if (frequency.getText().equalsIgnoreCase(selectedfreq))
				{
					frequency.click();
				}}
			return frequenciesforReminders;
		}

		// To enter the amount for a reminder and alert
		public void enterAmountforReminders(String payeename, String amount) throws InterruptedException{
			clickRemindersandAlertsforPayee(payeename);
			String id = getPayeeId(payeename);
			Thread.sleep(5000);
			driver.findElement(By.id(id)).findElements(By.className("reminder-amount")).get(1).clear();
			driver.findElement(By.id(id)).findElements(By.className("reminder-amount")).get(1).click();
			driver.findElement(By.id(id)).findElements(By.className("reminder-amount")).get(1).sendKeys(amount, Keys.ENTER);
		}	
		
		// To enter the reminder due date
		public void enterDateforReminders(String payeename, String date) throws InterruptedException{
			clickRemindersandAlertsforPayee(payeename);
			String id = getPayeeId(payeename);
			driver.findElement(By.id(id)).findElement(By.className("reminder-duedate")).click();
			driver.findElement(By.id(id)).findElement(By.className("reminder-duedate")).clear();
			driver.findElement(By.id(id)).findElement(By.className("reminder-duedate")).sendKeys(date, Keys.ENTER);
		}


		// To click the frequency drop down for a reminder and alert
		public void clickFrequencyforReminderandAlert(String payeename) throws InterruptedException{
			clickRemindersandAlertsforPayee(payeename);
			String id = getPayeeId(payeename);
			driver.findElement(By.id(id+"-rem-frequency-selected")).click();
		}

		// To click save for reminders and alerts
		public void saveReminderandAlert(String payeename) throws InterruptedException{
			clickRemindersandAlertsforPayee(payeename);
			String id = getPayeeId(payeename);
			driver.findElement(By.id(id)).findElement(By.name("saveAlertsButton")).click();
		}

		// To get the amount field error for a reminder
		public String getReminderAmountError(String payeename){
			String id = getPayeeId(payeename);
			return driver.findElement(By.id(id)).findElement(By.className("reminder-amount-error")).getText();
		}

		public String getReminderAmount(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			clickRemindersandAlertsforPayee(payeename);
			return driver.findElement(By.id(id)).findElement(By.className("reminder-amount")).getText();
		}

		public String getReminderFrequency(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			clickRemindersandAlertsforPayee(payeename);
			return driver.findElement(By.id(id)).findElement(By.id(payeeId + "-rem-frequency-selected")).getAttribute("value");
		}

		// Check if the list of webElements is a list of links
		public boolean checkifListhasLinks(List<WebElement> webElms){
			WebElement ListElm;
			Iterator<WebElement> ListElements = webElms.iterator();
			while(ListElements.hasNext()){
				ListElm=ListElements.next();
				if (!checkifLink(ListElm))
					return false;
				}
			return true;
		}
		
		// Check if something is a link
		public boolean checkifLink(WebElement wb){
			if(wb.getAttribute("href").equalsIgnoreCase("#"))
				return true;
			return false;
		}

		
		// To get the error message for the amount in Automatic Payments when it is set	
		public String getAmountErrorAutomaticPaymentwhenPaymentisSet(String payeename){
			String id = getPayeeId(payeename);
			return driver.findElement(By.id(id)).findElements(By.className("automatic-payment-errorDiv")).get(5).getText();	
		}

		public boolean checkTurnofflinkforRecurringPayments(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			clickAutomaticPaymentforPayee(payeename);
			Thread.sleep(5000);
			return (driver.findElement(By.id(id)).findElement(By.className("automatic-payment-turn-off")).getText().equalsIgnoreCase("Turn off"));
		}

		// To delete a payee
		public void deletePayee(String payeename, String nickname) throws InterruptedException{
			String id = getPayeeId(payeename);
				if(!getDrawerStatusByNicknameandname(payeename))
				openPayeeOptionsTraybyNameandNickname(payeename);
			driver.findElement(By.id(id)).findElement(By.className("delete-it")).click();	
			Thread.sleep(5000);
			driver.findElements(By.className("delete-payee-button")).get(1).click();
		}

		/*
		 * Hide a payee.
		 */
		public void hidePayee(String payeeName){
			String payeeId = this.getPayeeId(payeeName);
			driver.findElement(By.id(payeeId)).findElement(By.className("hidePortlet")).click();
			waitForAjaxCallsToComplete(JsToolKit.EXTJS);
		}

		// Commented the following, as the EbillHistoryPage object is not checked-in yet.
		/*public EbillHistoryPage goToEbillHistoryPage(){
			driver.findElement(By.linkText("My Bills")).click();
			wait.until(visibilityOfElementLocated(By.id("eBillGridPanel")));
			return PageFactory.initElements(driver, EbillHistoryPage.class);
					
		}*/
		
		@Override
		public List<WebElement> pageElementsToWait() {
			expectedElements.add(addPayeeTop);
			expectedElements.add(receipt);
			expectedElements.add(manageFundingAccounts);
			return expectedElements;
		}
	
		@Override
		public String setPageName(){
			pageName = "Bill Pay UI Main Page";
			return pageName;
		}

		public boolean verifyIfEbillLinkIsPresent(){			
			WebElement myBillLinkElement = driver.findElement(By.linkText("My Bills"));
			return isElementVisible(myBillLinkElement); // myBillLinkElement.isDisplayed() && myBillLinkElement.isEnabled();
				 
		}
		
		/*
		 * The following methods are still under experimentation.
		 */
		public void enterAmountForFirstPayee(){
			String id = getPayeeId(getFirstPayeeName());
			typeIntoFirstTextBoxWithValueUnderId("10", id, "$0.00");
		}
		
		public void enterDateForFirstPayee(){
			String id = getPayeeId(getFirstPayeeName());
			typeIntoFirstTextBoxWithValueUnderId("10/12/11", id, "mm/dd/yy");
		}
		
		public void enterIntoTopPayee(){
			typeIntoFirstTextBoxWithValue("T-Mobile", "Enter person or business");
		}
		
		public void enterIntoBottomPayee(){
			typeIntoTextBoxWithValue("American", "Enter person or business", 2);
		}
		
		public void clickLabel(){
			String id = getPayeeId(getFirstPayeeName());
			clickOnRadioButtonWithLabelUnderParentId("Amount:", id);
		}
		
		public void clickOnFirstRadioButton(){
			String id = getPayeeId("Discover Credit Cards");
			clickOnRadioButtonWithLabelUnderParentId("Use my bill information to determine the date and amount", id);
			clickOnRadioButtonWithLabelUnderParentId("Always pay minimum due on my bill", id);
			clickOnRadioButtonWithLabelUnderParentId("Create my own rule", id);
			clickOnRadioButtonWithLabelUnderParentId("Pay minimum due", id, 2);
			typeIntoTextBoxWithValue("200.00", "$100.00", 1, id);
			typeIntoTextBoxWithValue("210.00",  "$0.00", 2, id);
			clickOnRadioButtonWithLabelUnderParentId("When the bill arrives", id);
		}
				
		// METHODS FOR EBILL CUSTOM RULES TESTS
		
		// To open the Custom rule section
		public void openeBillCustomRulesSection(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillAutomaticPayments(payeename);
			clickOnRadioButtonWithLabelUnderParentId("Create my own rule", id);
			Thread.sleep(5000);	
		}	
		
		// To open the automatic payment tab that uses eBills
		public void openeBillAutomaticPayments(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			openPayeeOptionsTray(payeename);
			Thread.sleep(2000);
			goToAutomaticPaymentTab(payeename);
			Thread.sleep(2000);
			clickOnRadioButtonWithLabelUnderParentId("Use my bill information to determine the date and amount", id);
			Thread.sleep(5000);	
		}	
		
		// To get the value for the custom rule cut off amount
		public String getCustomRulecutOffAmount(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillCustomRulesSection(payeename);
			return driver.findElement(By.id(id)).findElement(By.className("ruleamountfield")).getAttribute("value");
		}		
				
		// To get the value of the pay amount field if less than cut off amount
		public String getCustomRulelesserthancutOffAmount(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillCustomRulesSection(payeename);
			return driver.findElement(By.id(id)).findElement(By.className("lessthan-amount")).getAttribute("value");	
		}
		
		// To get the value of the pay amount field if more than cut off amount
		public String getCustomRulegreaterthancutOffAmount(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillCustomRulesSection(payeename);
			return driver.findElement(By.id(id)).findElement(By.className("morethan-amount")).getAttribute("value");	
		}
		
		// To enter an amount for the amount field which is less/more than custom rule cutoff amount.
		public void enterCustomRulePayAmount(String payeename, int index, String amount) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillCustomRulesSection(payeename);
			Thread.sleep(5000);
			selectCustomRule(payeename, index, "Pay");
			typeIntoTextBoxWithValue(amount, "$0.00", index, id);
		}
		
		// To enter the custom rule cut off amount.
		public void enterCustomRulecutOffAmount(String payeename, String amount) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillCustomRulesSection(payeename);
			Thread.sleep(5000);
			typeIntoTextBoxWithValue(amount, "$100.00", 1, id);
		}
		
		// To get the cutoff amount text
		public String getCutoffAmountText(String payeename) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillCustomRulesSection(payeename);
			return driver.findElement(By.id(id)).findElement(By.className("automatic-ebill-myrule-valuediv")).getAttribute("textContent");
		}
				
		// To select the funding Account while using automatic payments for eBills
		public void selectAutomaticPaymentFundingAccountifeBillUsed(String payeename, String fundingAccount) throws InterruptedException{
			String id = getPayeeId(payeename);
			System.out.println(id);
			openeBillCustomRulesSection(payeename);			
			driver.findElement(By.id(id)).findElement(By.className("automatic-ebill-fundingaccount-comboct")).findElement(By.className("x-form-trigger")).click();
			Thread.sleep(10000);
			//List<WebElement> fundingAccounts = driver.findElement(By.className("x-combo-list-inner")).findElements(By.className("x-combo-list-item"));
			List<WebElement> fundingAccounts = driver.findElement(By.className("x-combo-list")).findElements(By.className("x-combo-list-item"));
			WebElement fundingAccountElmt;
			Iterator<WebElement> elements = fundingAccounts.iterator();
			while (elements.hasNext()){
				fundingAccountElmt = elements.next();
				if(fundingAccountElmt.getText().equalsIgnoreCase(fundingAccount)){
					fundingAccountElmt.click();
		}}
}
		
		// To select the custom rule
		public void selectCustomRule(String payeename, int index, String label) throws InterruptedException{
			String id = getPayeeId(payeename);
			openeBillCustomRulesSection(payeename);
			clickOnRadioButtonWithLabelUnderParentId(label, id, index);
		}
		
		// To set a custom rule
		public void setCustomRule(String payeename, String fundingaccount, String dueamount, String label1, String label2, String lesspayamount, String greaterpayamount, String sendpayment) throws InterruptedException{
			
			selectAutomaticPaymentFundingAccountifeBillUsed(payeename, fundingaccount);
			selectCustomRule(payeename, 1, label1);
			selectCustomRule(payeename, 2, label2);
			if(label1.equalsIgnoreCase("Pay"))
			enterCustomRulePayAmount(payeename, 1, lesspayamount);
			if(label2.equalsIgnoreCase("Pay"))
				enterCustomRulePayAmount(payeename, 1, greaterpayamount);
			selectSendPayment(payeename, sendpayment);
			clickAutomaticaPaymentSave(payeename);
		}
		
			// To get the number of days prior to due date for sending a payment
			public String getSendPaymentDaysbeforeDueDate(String payeename)throws InterruptedException{
				String id = getPayeeId(payeename);
				openeBillCustomRulesSection(payeename);
				return driver.findElement(By.id(id)).findElement(By.className("automatic-ebill-days")).findElement(By.tagName("input")).getAttribute("value");
			}

			// To get the send payment days field length
			public String getSendPaymentDaysbeforeDueDateLength(String payeename) throws InterruptedException{
				String id = getPayeeId(payeename);
				openeBillCustomRulesSection(payeename);
				return driver.findElement(By.id(id)).findElement(By.className("automatic-ebill-days")).findElement(By.tagName("input")).getAttribute("maxLength");
			}

			// To get the pay field errors
			public String getPayFieldErrors(String payeename, int index) throws InterruptedException{
				openeBillCustomRulesSection(payeename);
				//clickOnRadioButtonWithLabelUnderParentId(label, id, index);
				if(index!=2)
				return driver.findElement(By.className("lefterror")).getText();
				else
					return driver.findElement(By.className("righterror")).getText();
			}
			
			// To click the save button for an automatic payment
			public void clickAutomaticaPaymentSave(String payeename) throws InterruptedException{
				String id = getPayeeId(payeename);
				openeBillAutomaticPayments(payeename);
				driver.findElement(By.id(id)).findElement(By.className("automatic-payment-save")).click();
			}		
			
			// To get the automatic payment error message
			public void selectSendPayment(String payeename, String sendPayment) throws InterruptedException{
				String id = getPayeeId(payeename);
				openeBillAutomaticPayments(payeename);
				clickOnRadioButtonWithLabelUnderParentId(sendPayment, id);
			}
			
			// To get all the required labels
			public void getAllRadioButtonLabels(){
				
			}
}	
