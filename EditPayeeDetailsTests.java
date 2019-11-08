package com.intuit.bpui_qa;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class EditPayeeDetailsTests extends BPUIBasicTest {

	@BeforeClass
	public void startUp(){
		Log("Starting edit payee details tests.");
		
	}
	
	@Test(dataProvider="editPayee")
	public void testConfirmAccountDisabled(String payeeName) throws InterruptedException{
		bpui.togglePayeeDrawer(payeeName);
		Thread.sleep(1000);
		bpui.goToAccountInfoTab(payeeName);
		Thread.sleep(1000);
		assert bpui.isConfirAcctNumerEnabledInAcctInfo(payeeName);
	}
	
	@Test(dataProvider="editPayee", dependsOnMethods="testConfirmAccountDisabled")
	public void testConfirmAccountEnabled(String payeeName) throws InterruptedException{
		bpui.editAccountNumber(payeeName, "1234567");
		assert !bpui.isConfirAcctNumerEnabledInAcctInfo(payeeName);
	}

	@Test(dataProvider="editPayee", dependsOnMethods="testConfirmAccountEnabled")
	public void testErrorMessageConfirmAccnt(String payeeName) throws InterruptedException{
		bpui.saveEditedPayee(payeeName);
		assert bpui.verifyConfirmAccountNumberError(payeeName);
		bpui.editConfirmAccountNumber(payeeName, "1234567");
	}

	@Test(dataProvider="editPayee", dependsOnMethods="testConfirmAccountEnabled")
	public void testWorkingEditScenario(String payeeName) throws InterruptedException{
		bpui.editAccountNumber(payeeName, "12345678");
		bpui.editConfirmAccountNumber(payeeName, "12345678");

		bpui.editName(payeeName, payeeName);
		bpui.editNickName(payeeName, payeeName);
		bpui.editAddress1(payeeName, "180 Elm Ct");
		bpui.editCity(payeeName, "Sunnyvale");
		bpui.editState(payeeName, "California");
		bpui.editZip1(payeeName, "95051");
		bpui.editPhone(payeeName, "122-123-1234");
		bpui.saveEditedPayee(payeeName);
		assert bpui.verifySuccessMessage(payeeName);
		
	}



	@Test(dataProvider="editPayee", dependsOnMethods="testErrorMessageConfirmAccnt")
	public void testErrorMessageNickName(String payeeName) throws InterruptedException{
		bpui.togglePayeeDrawer(payeeName);

		bpui.clearNickNameEditPayee(payeeName);
		bpui.saveEditedPayee(payeeName);
		Thread.sleep(4000);
		assert bpui.verifyEditPayeeNickNameFotterMessage(payeeName);
	}

	@Test(dataProvider="editPayee", dependsOnMethods="testErrorMessageNickName")
	public void testMandatoryErrorMessages(String payeeName) throws InterruptedException{
		bpui.clearPersonalPayeeDetails(payeeName);
		bpui.saveEditedPayee(payeeName);
		assert bpui.verifyNameEditInfoMandatoryError(payeeName);
		assert bpui.verifyAddress1EditInfoMandatoryError(payeeName);
		assert bpui.verifyCityEditInfoMandatoryError(payeeName);
		assert bpui.verifyStateEditInfoMandatoryError(payeeName);
		assert bpui.verifyZipEditInfoMandatoryError(payeeName);
//		bpui.saveEditedPayee(payeeName);
//		assert bpui.verifyConfirmAccountNumberError(payeeName);
	}

	@Test(dataProvider="editPayee", dependsOnMethods="testErrorMessageNickName")
	public void testValidationErrorZipCode1(String payeeName) throws InterruptedException{
		bpui.editName(payeeName, payeeName);
		bpui.editAddress1(payeeName, "180 Elm Ct");
		bpui.editCity(payeeName, "Sunnyvale");
		bpui.editNickName(payeeName, payeeName);
		bpui.editState(payeeName, "California");
		bpui.editZip1(payeeName, "9505");
		assert bpui.verifyZipEditInfoValidationMessage(payeeName);
		
	}

	@Test(dataProvider="editPayee", dependsOnMethods="testValidationErrorZipCode1")
	public void testValidationErrorZipCodeLength(String payeeName) throws InterruptedException{
		bpui.editZip1(payeeName, "95051");
		bpui.editZip2(payeeName, "12");
		assert bpui.verifyZipEditInfoValidationMessage(payeeName);
		
	}

	@Test(dataProvider="editPayee", dependsOnMethods="testValidationErrorZipCode1")
	public void testValidationErrorPhoneNumber(String payeeName) throws InterruptedException{
		bpui.editPhone(payeeName, "12-12-123");
		bpui.saveEditedPayee(payeeName);
		assert bpui.verifyPhoneEditInfoErrorMessage(payeeName);
		
	}


	@DataProvider(name="editPayee")
	public Object[][] EditPayee(){
		return new Object[][] {
			new Object[] {"Test Edit Payee"},
		};
	}

}
