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

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.intuit.bpui_qa.WebPage;
import com.intuit.bpui_qa.Pages.PaymentHistoryPage.DateCompare;
import com.intuit.bpui_qa.Pages.PaymentHistoryPage.StringCompare;

public class EbillHistoryPage extends WebPage{
	
	@FindBy(linkText="Back to main page")

	private WebElement backToMainPage;
	
	private WebElement myBillLink;
	
	private WebElement tableGrid;

	

	public EbillHistoryPage(WebDriver driver){

		super(driver);

		System.out.println("In EBill History Page...........");

		backToBody();

		switchFrame("body");

	}


	
	
	public void clickMyBillsLink(){
		myBillLink =  driver.findElement(By.linkText("My Bills"));
		myBillLink.click();
		wait.until(visibilityOfElementLocated(By.id("eBillGridPanel")));
		
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
	
	
	public boolean verifyEBillColumnHeadersAreShown(){

		if(!isElementVisible(By.className("x-grid3-hd-receiveddate"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-duedate"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-payeeId"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-amounttotaldue"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-amountmindue"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-eBillInfo"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-billStatus"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-amountpaid"))) return false;

		if(!isElementVisible(By.className("x-grid3-hd-datepaid"))) return false;

		

		return true;

	}
	
	
	
	public boolean verifyDueDateOptions(){
		List<Date> dueDateList = getDateList("DueDate");
		for(int i=0 ; i< dueDateList.size() ; i++){
			Date eachDate = dueDateList.get(i);
			if(eachDate == null){
				return false;
			}
		}
		return true;
		
		
	}
	
	
	
	
	public boolean verifyEbillLinkOptions(){
		List<String> eBillList = getAllEbill();
		for(int i=0 ; i< eBillList.size() ; i++){
			String value = eBillList.get(i);
			if(!value.equals("View")){
				return false;
			}
		}
		return true;
		
		
	}
	
	
	
	public boolean verifyEbillStatus(){
		List<String> eBillStatusList = getAllEbillStatus();
		for(int i=0 ; i< eBillStatusList.size() ; i++){
			String value = eBillStatusList.get(i);
			if(!(value.equals("PAID") || value.equals("SCHEDULED") || value.equals("FILED") || value.equals("UNPAID | File") )){
				return false;
			}
		}
		return true;
		
		
	}
	
	
	public boolean verifyAmountPaidIsNull(){
		List<String> eBillStatusList = getAllEbillStatus();
		List<Float> eBillAmount = getAmountPaidForEbill();
		for(int i=0 ; i< eBillStatusList.size() ; i++){
			String status = eBillStatusList.get(i);
			Float amount = eBillAmount.get(i);
			
			if((status.equals("PAID") || status.equals("SCHEDULED"))  &&  amount == 0.0)
			return false;
			
			if((status.equals("UNPAID | File") || status.equals("FILED"))  &&  amount != 0.0)
				return false;			
		}
		return true;
				
	}
	
	
	public boolean verifyDatePaid(){
		List<String> eBillStatusList = getAllEbillStatus();
		List<Date> eBillReceivedDate = /*getReceivedDate();*/getDateList("DatePaid");
		for(int i=0 ; i< eBillStatusList.size() ; i++){
			String status = eBillStatusList.get(i);
			Date datePaid = eBillReceivedDate.get(i);
			
			if((status.equals("PAID") || status.equals("SCHEDULED"))  && datePaid  == null)
			return false;
			
			if((status.equals("UNPAID | File") || status.equals("FILED"))  &&  datePaid != null)
				return false;
			
		}
		return true;
		
		
		
		
	}
	
	
	public boolean verifyEditCancelLinksPresent(){
		List<String> eBillList = getAllEbillStatus();
		
		List<String> iconEditDelView = getEbillEditDeleteViewOption();
				
		for(int i=0 ; i< eBillList.size() ; i++){
			String eBillStatus = eBillList.get(i);
			String iconValue = iconEditDelView.get(i);
				
			if( eBillStatus.equals("PAID")   )
					if(!(iconValue.equals("View Payment")))
						return false;
			
			if( eBillStatus.equals("SCHEDULED")   )
				if(!(iconValue.equals("Edit Payment\nDelete Payment")))
					return false;
			
			if( eBillStatus.equals("UNPAID | File")   )
				if(!(iconValue == null))
					return false;
			
			if( eBillStatus.equals("FILED")   )
				if(!(iconValue == null))
					return false;
					

		}
		return true;
		
		
	}
	
	

	public boolean verifyIfEbillRecordsAreFilteredAsPerDateSelection(){

		Date min = getFromDateNextToMenuOnTop();

		Date max = getToDateNextToMenuOnTop();

		Date dateCmp = null;

		List<Date> allActualDates = getDateList("DueDate");

		Iterator<Date> actualDateItr = allActualDates.iterator();

		while(actualDateItr.hasNext()){

			dateCmp = actualDateItr.next();

			if(!(dateCmp.compareTo(min) >= 0 && dateCmp.compareTo(max) <= 0))

				return false;

		}

		return true;

	}
	
	
	public void sortReceivedOn(){

		WebElement receivedOn = driver.findElement(By.className("x-grid3-hd-receiveddate"));

		receivedOn.click();

	}
	
	
	public boolean verifyIfReceivedIsInAscendingOrder() throws ParseException{

		List<Date> dateList = getDateList("Received");	

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, new DateCompare());

		return tempPayeeList.equals(dateList);

	}
	
	
	public boolean verifyIfReceivedIsInDescendingOrder() throws ParseException{

		List<Date> dateList = getDateList("Received");	

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, Collections.reverseOrder(new DateCompare()));

		return tempPayeeList.equals(dateList);

	}
	
	public boolean verifyIfDueDateIsInAscendingOrder() throws ParseException{

		List<Date> dateList = getDateList("DueDate");	

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, new DateCompare());

		return tempPayeeList.equals(dateList);

	}
	
	public boolean verifyIfDueDateIsInDescendingOrder() throws ParseException{

		List<Date> dateList = getDateList("DueDate");	

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, Collections.reverseOrder(new DateCompare()));

		return tempPayeeList.equals(dateList);

	}
	
	
	public boolean verifyIfDatePaidIsInAscendingOrder() throws ParseException{

		List<Date> dateList = getDateList("DatePaid");	

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, new DateCompare());

		return tempPayeeList.equals(dateList);

	}
	
	public boolean verifyIfDatePaidIsInDescendingOrder() throws ParseException{

		List<Date> dateList = getDateList("DatePaid");	

		List tempPayeeList = new ArrayList(dateList);

		Collections.sort(tempPayeeList, Collections.reverseOrder(new DateCompare()));

		return tempPayeeList.equals(dateList);

	}
	
	
	public void sortDueDate(){

		WebElement dueDate = driver.findElement(By.className("x-grid3-hd-duedate"));

		dueDate.click();

	}
	
	
	public void sortPayeeName(){

		WebElement paidTo = driver.findElement(By.className("x-grid3-hd-payeeId"));

		paidTo.click();

	}
	
	public boolean verifyIfPayeeIsSorted(){

		List<String> payeeList = getPayees();

		List tempPayeeList = new ArrayList<String>(payeeList);

		Collections.sort(tempPayeeList, new StringCompare());

		return tempPayeeList.equals(payeeList);

	}
	
	
	public boolean verifyIfPayeeIsSortedInReverseOrder(){

		List<String> payeeList = getPayees();

		List tempPayeeList = new ArrayList<String>(payeeList);

		Collections.sort(tempPayeeList, Collections.reverseOrder(new StringCompare()));

		return tempPayeeList.equals(payeeList);

	}
	
	
	public void openFilterMenu(){

		WebElement changeMenu = driver.findElement(By.className("x-btn-text"));

		changeMenu.click();



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

	public void sortBillTotal(){

		WebElement billTotal = driver.findElement(By.className("x-grid3-hd-amounttotaldue"));

		billTotal.click();

	}
	
		
	
	public void sortMinDue(){

		WebElement minDue = driver.findElement(By.className("x-grid3-hd-amountmindue"));

		minDue.click();

	}
	
	
	public void sortStatus(){

		WebElement status = driver.findElement(By.className("x-grid3-hd-billStatus"));

		status.click();

	}
	
	public boolean verifyIfStatusIsSorted(){

		List<String> statusList = getAllEbillStatus();

		List tempStatusList = new ArrayList<String>(statusList);

		Collections.sort(tempStatusList, new StringCompare());

		return tempStatusList.equals(statusList);

	}
	
	public boolean verifyIfStatusIsSortedInReverseOrder(){

		List<String> statusList = getAllEbillStatus();

		List tempStatusList = new ArrayList<String>(statusList);

		Collections.sort(tempStatusList, Collections.reverseOrder(new StringCompare()));

		return tempStatusList.equals(statusList);

	}
	
	
	
	
	public List<Date> getDateList(String dateColumn){

		List dateList = new ArrayList<Date>();

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

		List<WebElement> dateElement = null;		
		
		if(dateColumn.equals("Received"))
			dateElement = driver.findElements(By.className("x-grid3-col-receiveddate"));
		
		if(dateColumn.equals("DueDate"))
			dateElement = driver.findElements(By.className("x-grid3-col-duedate"));	
		
		if(dateColumn.equals("DatePaid"))
			dateElement = driver.findElements(By.className("x-grid3-col-datepaid"));	
			
		Iterator<WebElement> eachDate = dateElement.iterator();

		while(eachDate.hasNext()){

			try {
				
				String dateItem = eachDate.next().getText();
				if(dateItem.equals(""))
					dateList.add(null);
				else
					dateList.add(df.parse(dateItem));

			} catch (ParseException e) {

				// TODO Auto-generated catch block

				e.printStackTrace();

			}

		}

		

		return dateList;



	}
	
	
	public List<String> getAllEbill(){

		List eBillList = new ArrayList<String>();

		List<WebElement> eBillElmList = driver.findElements(By.className("x-grid3-col-eBillInfo"));

		Iterator<WebElement> eBillIterator = eBillElmList.iterator();

		while(eBillIterator.hasNext())

			eBillList.add(eBillIterator.next().getText());



		return eBillList;

	}
	
	
	public List<String> getAllEbillStatus(){

		List eBillList = new ArrayList<String>();

		List<WebElement> eBillElmList = driver.findElements(By.className("x-grid3-col-billStatus"));

		Iterator<WebElement> eBillIterator = eBillElmList.iterator();

		while(eBillIterator.hasNext())

			eBillList.add(eBillIterator.next().getText());



		return eBillList;

	}
	
	
	public List<Float> getAmountPaidForEbill(){

		List<Float> billAmountList = new ArrayList<Float>();

		List<WebElement> amountElement = driver.findElements(By.className("x-grid3-col-amountpaid"));

		Iterator<WebElement> amountElmIterator = amountElement.iterator();

		while(amountElmIterator.hasNext()){
			
			String amountVal = amountElmIterator.next().getText();
			if(!amountVal.equals(""))
				billAmountList.add(Float.parseFloat(amountVal.substring(1)));
			else 
				billAmountList.add((float) 0.0);
				
		

		}

		return billAmountList;

	}
	
	
	
public List<String> getEbillEditDeleteViewOption(){
		
		List eBillList = new ArrayList<String>();

		List<WebElement> eBillElmList = driver.findElements(By.className("x-grid3-col-bpActions"));
		
		Iterator<WebElement> eBillIterator = eBillElmList.iterator();
		
		while(eBillIterator.hasNext()){

			String textEditDeleteView = eBillIterator.next().getText();
			if(textEditDeleteView.equals("Edit Payment\nDelete Payment"))
				eBillList.add(textEditDeleteView);
			
			else if(textEditDeleteView.equals("View Payment"))
				eBillList.add(textEditDeleteView);
			
			else if(textEditDeleteView.equals(""))
				eBillList.add(null);
						
			
		}

		return eBillList;

	}





public List<String> getPayees(){

	List payees = new ArrayList<String>();		

	List<WebElement> payeeList = driver.findElements(By.className("x-grid3-col-payeeId"));

	Iterator<WebElement> eachPayee = payeeList.iterator();

	while(eachPayee.hasNext())

			payees.add(eachPayee.next().getText());

	return payees;

}


public List<String> getBillTotal(String amountType){

	List billTotal = new ArrayList<String>();		

	List<WebElement> billTotalList = null;
	
	if(amountType.equals("totalAmount"))
		billTotalList = driver.findElements(By.className("x-grid3-col-amounttotaldue"));
	
	else if(amountType.equals("minDue"))
		billTotalList = driver.findElements(By.className("x-grid3-col-amountmindue"));

	Iterator<WebElement> eachBill = billTotalList.iterator();

	while(eachBill.hasNext())

			billTotal.add(eachBill.next().getText());

	return billTotal;

}


	
	public void clickDeleteButton() {
		
	    driver.findElement(By.className("grid-delete-payment button-container")).click();
		
		
	}
	
	
	public void clickNoButton() {
		
	    driver.findElement(By.className("noButton")).click();
		
	}
	
	
	public void clickViewIcon(){
		WebElement viewIcon = driver.findElement(By.className("view-payment-details"));
		viewIcon.click();
	}
	
	public boolean verifyViewPopUpIsDisplayed(){
				

	if(driver.findElements(By.className("viewPaymentSave")).size()<1)
		return false;
	
	WebElement closeButtonOnPopUp = driver.findElements(By.className("viewPaymentSave")).get(1);

	String closeTextOnPopUp = closeButtonOnPopUp.getText();
	
	if(!(closeTextOnPopUp.equals("Close")))
		
			return false;
		else 
			
			return true;		
	}
	
	public void clickEditIcon(){		
		WebElement editIcon = driver.findElement(By.className("grid-edit-payment"));
		editIcon.click();
	}
	
	public boolean verifyEditPopUpIsDisplayed(){
		
		if(driver.findElements(By.className("edit-cancel-link")).size()<1)
			return false;
				
		WebElement closeLinkOnPopUp = driver.findElements(By.className("edit-cancel-link")).get(1);

		String closeTextOnPopUp = closeLinkOnPopUp.getText();
				
		if(!(closeTextOnPopUp.equals("Don't save changes")))
			
				return false;
			else 
				
				return true;		
		}
	
	public void clickDeleteIcon(){
		WebElement viewIcon = driver.findElement(By.className("grid-delete-payment"));
		viewIcon.click();
	}
	
	
	public boolean verifyDeletePopUpIsDisplayed(){
				
		if(driver.findElements(By.className("cancel-automatic-payment-header")).size()<4)
			return false;
		
		else{
		WebElement deletePopUpElement = driver.findElements(By.className("cancel-automatic-payment-header")).get(3);
		
		String deleteTextOnPopUp = deletePopUpElement.getText();
		
		if(!(deleteTextOnPopUp.equals("Are you sure you want to cancel this payment?")))
			
				return false;
		
			else 
				
				return true;		
		}
	}
	
	
	public void clickXButtonOnViewEditDeletePopUp(){
		
		if(driver.findElements(By.className("x-tool-close")).size() == 1)
			driver.findElements(By.className("x-tool-close")).get(0).click();
		
		else if(driver.findElements(By.className("x-tool-close")).size() == 2)
			driver.findElements(By.className("x-tool-close")).get(1).click();
		
		else if(driver.findElements(By.className("x-tool-close")).size() == 3)
			driver.findElements(By.className("x-tool-close")).get(2).click();
			
		
	}
	

	
	
	public void clickCloseButtonOnViewPopUp(){
			
		driver.findElements(By.className("viewPaymentSave")).get(1).click();
		
	}
	
	public void clickCloseButtonOnEditPopUp(){
		
		driver.findElements(By.className("edit-cancel-link")).get(1).click();
		
	}
	
	
	public void clickCloseButtonOnDeletePopUp(){
	
		driver.findElements(By.name("canceldelete")).get(4).click();
		
	}
	
	
	public String getSearchTextBoxDefaultText() {
	      
	      return driver.findElement(By.tagName("input")).getAttribute("value");
	      
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


	

	@Override
	public List pageElementsToWait() {
		expectedElements.add(backToMainPage);

		expectedElements.add(tableGrid);

		return expectedElements;
	}



	@Override
	public String setPageName() {
		pageName = "EBill History Page";

		return pageName;
	}

}
