package com.intuit.bpui_qa.Pages;



import java.text.DateFormat;

import java.text.ParseException;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Collections;

import java.util.Comparator;

import java.util.Date;

import java.util.Iterator;

import java.util.List;



import com.intuit.bpui_qa.WebPage;

import com.intuit.bpui_qa.utils.WaitForElement;



import org.openqa.selenium.By;

import org.openqa.selenium.Keys;

//import org.openqa.selenium.RenderedWebElement;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;

import org.openqa.selenium.support.PageFactory;



public class PaymentHistoryPage extends WebPage{

	

	@FindBy(linkText="Back to main page")

	private WebElement backToMainPage;
	private WebElement myBillLink;

	

	@FindBy(id="searchGridPanel")

	private WebElement tableGrid;

	

	public PaymentHistoryPage(WebDriver driver){

		super(driver);

		System.out.println("In Payment History Page...........");

		backToBody();

		switchFrame("body");

//		waitUntilElementLoaded();

	}

	

	public boolean verifyPayeeIsCanceled(String payeeName){

		List<WebElement> rows = driver.findElements(By.className("x-grid3-row-table"));

		Iterator<WebElement> eachRow = rows.iterator();

		WebElement payeeElm;

		WebElement actualRow;

		String status = "";

		while(eachRow.hasNext()){

			actualRow = eachRow.next();

			

			payeeElm = actualRow.findElement(By.className("x-grid3-col-bpPayeeName"));

			System.out.println("Payee name from table: " + payeeElm.getText());

			if(payeeName.equals(payeeElm.getText())){

					status = actualRow.findElement(By.className("x-grid3-col-paymentStatus")).getText();

					System.out.println("Status for the above: " + status );

					if(!(status.equalsIgnoreCase("Cancelled")))

						return false;

		}

		}

		return true;

	}

	

	public boolean verifyIfPaymentIsCanceled(String paymentId){

		List<WebElement> rows = driver.findElements(By.className("x-grid3-row-table"));

		Iterator<WebElement> eachRow = rows.iterator();

		WebElement payeeElm;

		WebElement actualRow;

		String status = "";

		while(eachRow.hasNext()){

			actualRow = eachRow.next();

			

			payeeElm = actualRow.findElement(By.className("x-grid3-col-bpPaymentId"));

			System.out.println("Payee name from table: " + payeeElm.getText());

			if(paymentId.equals(payeeElm.getText())){

					status = actualRow.findElement(By.className("x-grid3-col-paymentStatus")).getText();

					System.out.println("Status for the above: " + status );

					if(!(status.equalsIgnoreCase("Cancelled") || status.equalsIgnoreCase("Complete")))

						return false;

		}

		}

		return true;

		

	}

	

	public boolean verifyIfSendOnIsSorted() throws ParseException{

		List<Date> dateList = getSentOnDates();

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, new DateCompare());

		return tempPayeeList.equals(dateList);

	}

	

	public List<Date> getSentOnDates(){

		List dateList = new ArrayList<Date>();

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		

		List<WebElement> sentOn = driver.findElements(By.className("x-grid3-col-startdate"));

		Iterator<WebElement> eachSentOn = sentOn.iterator();

		while(eachSentOn.hasNext()){

			try {

				dateList.add(df.parse(eachSentOn.next().getText()));

			} catch (ParseException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		

		return dateList;



	}



	public boolean verifyIfConfirmationNumberIsInAscOrder(){

		List<String> conformatioNoList = getAllConfirmationNumbers();

		List tempConfirmationNoList = new ArrayList<String>(conformatioNoList);

		Collections.sort(tempConfirmationNoList, new StringCompare());

		return tempConfirmationNoList.equals(conformatioNoList);

	}



	public boolean verifyIfConfirmationNumberIsInDescOrder(){

		List<String> conformatioNoList = getAllConfirmationNumbers();

		List tempConfirmationNoList = new ArrayList<String>(conformatioNoList);

		Collections.sort(tempConfirmationNoList, Collections.reverseOrder(new StringCompare()));

		return tempConfirmationNoList.equals(conformatioNoList);

	}



	public boolean verifyIfPaidToIsSorted(){

		List<String> paidToList = getPaidTo();

		List tempPaidToList = new ArrayList<String>(paidToList);

		Collections.sort(tempPaidToList, new StringCompare());

		return tempPaidToList.equals(paidToList);

	}



	public boolean verifyIfPaidToIsSortedInReverseOrder(){

		List<String> paidToList = getPaidTo();

		List tempPaidToList = new ArrayList<String>(paidToList);

		Collections.sort(tempPaidToList, Collections.reverseOrder(new StringCompare()));

		return tempPaidToList.equals(paidToList);

	}



	public boolean verifyIfPaidFromIsSortedInAscendingOrder(){

		List<String> paidFromList = getAllPaidFromNumbers();

		List tempPaidFromList = new ArrayList<String>(paidFromList);

		Collections.sort(tempPaidFromList, new StringCompare());

		return tempPaidFromList.equals(paidFromList);

	}



	public boolean verifyIfPaidFromIsSortedInDescendingOrder(){

		List<String> paidFromList = getAllPaidFromNumbers();

		List tempPaidFromList = new ArrayList<String>(paidFromList);

		Collections.sort(tempPaidFromList, Collections.reverseOrder(new StringCompare()));

		return tempPaidFromList.equals(paidFromList);

	}



	public List<String> getPaidTo(){

		List paidTo = new ArrayList<String>();

		

		List<WebElement> paidToList = driver.findElements(By.className("x-grid3-col-bpPayeeName"));

		Iterator<WebElement> eachPaidTo = paidToList.iterator();

		while(eachPaidTo.hasNext())

				paidTo.add(eachPaidTo.next().getText());



		return paidTo;

	}



	public boolean verifyIfAmountIsInAscendingOrder() throws ParseException{

		List<Float> amountList = getAllAmounts();

		

		List tempAmountList = new ArrayList(amountList);

		Collections.sort(tempAmountList, new FloatCompare());

		return tempAmountList.equals(amountList);

	}



	public boolean verifyIfAmountIsInDescendingOrder() throws ParseException{

		List<Float> amountList = getAllAmounts();

		

		List tempAmountList = new ArrayList(amountList);

		Collections.sort(tempAmountList, Collections.reverseOrder(new FloatCompare()));

		return tempAmountList.equals(amountList);

	}



	public boolean verifyIfSendOnIsInDescendingOrder() throws ParseException{

		List<Date> dateList = getSentOnDates();

		

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, Collections.reverseOrder(new DateCompare()));

		return tempPayeeList.equals(dateList);

	}

	

	public void sortSentOn(){

		WebElement sentOn = driver.findElement(By.className("x-grid3-hd-startdate"));

		sentOn.click();

	}

	

	public void sortPaidTo(){

		WebElement paidTo = driver.findElement(By.className("x-grid3-hd-bpPayeeName"));

		paidTo.click();

	}



	public void sortConfirmationNumber(){

		WebElement paidTo = driver.findElement(By.className("x-grid3-hd-bpConfirmationNo"));

		paidTo.click();

	}



	public void sortAmount(){

		WebElement paidTo = driver.findElement(By.className("x-grid3-hd-amount"));

		paidTo.click();

	}



	public void sortPaidFrom(){

		WebElement paidFrom = driver.findElement(By.className("x-grid3-hd-bpFundingAccountId"));

		paidFrom.click();

	}



	public boolean verifyHeadersAreShown(){

		if(!isElementVisible(By.className("x-grid3-hd-startdate"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-bpPayeeName"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-amount"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-eBillInfo"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-bpConfirmationNo"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-bpFundingAccountId"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-paymentStatus"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-deliveryMethod"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-categoryId"))) return false;

		

		return true;

	}

	

	public boolean verifyDeaultFooter(){

		WebElement footerLabel = driver.findElement(By.className("historypage-grid-footer-dark-section-left"));

		if(!"Total".equals(footerLabel.getText().trim())) return false;

		

		return verifyFooterTotal();

	}

	

	public boolean verifyFooterTotal(){

		float footerTotalAmount = getAmountTotal();

		float actualTotalTableAmount = (float) 0.0;

		List<Float> allAmountsList = getAllAmounts();

		Iterator<Float> amountItr = allAmountsList.iterator();

		while(amountItr.hasNext())

			actualTotalTableAmount += amountItr.next();

//		System.out.println("Total Amount: " + getAmountTotal());

		

		return actualTotalTableAmount == footerTotalAmount;

		

	}

	

	public List<Float> getAllAmounts(){

		List<Float> amounts = new ArrayList<Float>();

		List<WebElement> amount = driver.findElements(By.className("x-grid3-col-amount"));

		Iterator<WebElement> amountElm = amount.iterator();

		while(amountElm.hasNext()){

			amounts.add(Float.parseFloat(amountElm.next().getText().substring(1).replace(",", "")));

		

		}

		return amounts;

	}



	public List<String> getAllConfirmationNumbers(){

		List confirmationNumbers = new ArrayList<String>();

		

		List<WebElement> confirmNoElmList = driver.findElements(By.className("x-grid3-hd-bpConfirmationNo"));

		Iterator<WebElement> eachPayeeConfirmNoElm = confirmNoElmList.iterator();

		while(eachPayeeConfirmNoElm.hasNext())

			confirmationNumbers.add(eachPayeeConfirmNoElm.next().getText());



		return confirmationNumbers;

	}



	public List<String> getAllPaidFromNumbers(){

		List paidFromFundingAccountNumbers = new ArrayList<String>();

		

		List<WebElement> paidFromElmList = driver.findElements(By.className("x-grid3-hd-bpFundingAccountId"));

		Iterator<WebElement> eachPaidFromElm = paidFromElmList.iterator();

		while(eachPaidFromElm.hasNext())

			paidFromFundingAccountNumbers.add(eachPaidFromElm.next().getText());



		return paidFromFundingAccountNumbers;

	}



	public float getAmountTotal(){

		List<Float> amounts = getAllAmounts();

		Iterator<Float> amountList = amounts.iterator();

		float totalAmount = (float) 0.0;

		while(amountList.hasNext()){

			totalAmount += amountList.next();

		}

		

		return totalAmount;

	}

	

	public void openFilterMenu(){

		WebElement changeMenu = driver.findElement(By.className("x-btn-text"));

		changeMenu.click();



	}

	

	public void clickGo(){

		WebElement goButton = driver.findElement(By.id("mainMenu")).findElement(By.className("x-btn-text"));

		goButton.sendKeys(Keys.ENTER);

		goButton.click();

	}

	

	public void enterDateRange(String minDate, String maxDate){

		WebElement fromDate = driver.findElement(By.id("startdt"));

		fromDate.click();

		fromDate.sendKeys(minDate, (Keys.ENTER));

		

		WebElement toDate = driver.findElement(By.id("enddt"));

		toDate.click();

		toDate.sendKeys(maxDate, (Keys.ENTER));

		

		clickGo();

		try {

			Thread.sleep(5000);

		} catch (InterruptedException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	

	public boolean verifyMenuOnTop(){

		openFilterMenu();

		List<WebElement> mainMenuItems = driver.findElement(By.id("mainMenu")).findElements(By.className("x-menu-item-text"));

		Iterator<WebElement> menuItem = mainMenuItems.iterator();

		List<String> listItems = new ArrayList<String>();

		while(menuItem.hasNext()){

			listItems.add(menuItem.next().getText().trim());

		}

		

		System.out.println("Menu Items: " + listItems);

		return true;

		

	}

	

	public Date getFromDateNextToMenuOnTop(){

		WebElement textDateRange = driver.findElement(By.id("date-line")).findElement(By.xpath("..")).findElement(By.tagName("span"));

		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

		Date fromDate = null;

		try {

			fromDate = formatter.parse(textDateRange.getText().split("-")[0].trim());

		} catch (ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return fromDate;

	}



	public Date getToDateNextToMenuOnTop(){

		WebElement textDateRange = driver.findElement(By.id("date-line")).findElement(By.xpath("..")).findElement(By.tagName("span"));

		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy");

		Date toDate = null;

		try {

			toDate = formatter.parse(textDateRange.getText().split("-")[1].trim());

		} catch (ParseException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

		return toDate;

	}

	

	public boolean verifyIfRecordsAreFilteredAsPerDateSelection(){

		Date min = getFromDateNextToMenuOnTop();

		Date max = getToDateNextToMenuOnTop();

		Date dateCmp = null;

		List<Date> allActualDates = getSentOnDates();

		Iterator<Date> actualDateItr = allActualDates.iterator();

		while(actualDateItr.hasNext()){

			dateCmp = actualDateItr.next();

			if(!(dateCmp.compareTo(min) >= 0 && dateCmp.compareTo(max) <= 0))

				return false;

		}

		return true;

	}



	public void selectLast7Days(){

		WebElement menuItem = driver.findElement(By.id("Last 7 days"));

		menuItem.click();

		try {

			Thread.sleep(5000);

		} catch (InterruptedException e) {

			// TODO Auto-generated catch block

			e.printStackTrace();

		}

	}

	

	public void selectLast2Months(){

		WebElement menuItem = driver.findElement(By.id("Last 2 Months"));

		menuItem.click();

		try{

			Thread.sleep(5000);

		}catch (InterruptedException e) {

			// TODO: handle exception

		}

	}



	public void selectLast6Months(){

		WebElement menuItem = driver.findElement(By.id("Last 6 months"));

		menuItem.click();

		try{

			Thread.sleep(5000);

		}catch (InterruptedException e) {

			// TODO: handle exception

		}

	}



	public String getSearchTextBoxValue(){

		return driver.findElement(By.id("search-duedate-field")).findElement(By.tagName("input")).getAttribute("value");

	}

	

	class DateCompare implements Comparator<Date>{

		

		public int compare(Date o1, Date o2) {

			

			// TODO Auto-generated method stub

			return o1.compareTo(o2);

		}

	}



	class StringCompare implements Comparator<String>{

		

		public int compare(String o1, String o2) {

			

			// TODO Auto-generated method stub

			return o1.compareToIgnoreCase(o2);

		}

	}



	class FloatCompare implements Comparator<Float>{

		

		public int compare(Float o1, Float o2) {

			

			// TODO Auto-generated method stub

			return o1.compareTo(o2);

		}

	}



	

	@Override

	public List<WebElement> pageElementsToWait() {

		expectedElements.add(backToMainPage);

		expectedElements.add(tableGrid);

		// TODO Auto-generated method stub

		return expectedElements;

	}

	

	@Override

	public String setPageName(){

		pageName = "Payment History Page";

		return pageName;

	}

	
	public void clickMyBill(){
		driver.findElement(By.linkText("My Bills")).click();
//		wait.until(visibilityOfElementLocated(By.id("searchGridPanel")));
	}
	
	


}

