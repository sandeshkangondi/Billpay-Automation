package com.intuit.bpui_qa;

import javax.annotation.PostConstruct;
import javax.swing.plaf.basic.BasicBorders.RadioButtonBorder;

import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.lift.HamcrestWebDriverTestCase;
import org.openqa.selenium.lift.find.DivFinder;
import org.openqa.selenium.lift.find.Finder;

import com.intuit.bpui_qa.WebElement.LabelFinder;

import static org.openqa.selenium.lift.Finders.button;
import static org.openqa.selenium.lift.Finders.link;

import static org.openqa.selenium.lift.Finders.first;

import static com.intuit.bpui_qa.WebElement.InputTagFinder.textbox;
import static com.intuit.bpui_qa.WebElement.InputTagFinder.textboxWithValue;
import static com.intuit.bpui_qa.WebElement.LabelFinder.label;

import static com.intuit.bpui_qa.WebElement.EleFinder.*;
import static com.intuit.bpui_qa.WebElement.FindInput.*;

public class LifeStyle extends HamcrestTestCase{

	private DriverProvider drvPvr = DriverProvider.createDriver();
	private static LifeStyle style;
	
	@Override
	protected WebDriver createDriver() {
		return drvPvr.getWebDriver();
	}
	
	public static LifeStyle getLifeStyle(String url){
		if(style == null)
			style = new LifeStyle(url);
		return style;
		
	}
	
	public LifeStyle(String url){
		goTo(url);
	}
	
	public void clickOnButtonWithText(String buttonText){
		clickOn(button(buttonText));
	}

	public void clickOnTextBoxWithValue(String textBoxValue){
		clickOn(first(textboxWithValue(textBoxValue)));
	}
	
	public static LifeStyle getAvailableLifeStyle(){
		return style;
	}
	
	public void clickLinkWithText(String linkText){
		clickOn(link(linkText));
	}
	
	public void typeIntoSecondTextBox(String text){
		type(text, into(indexOf(textboxes(), 2)));
		
	}
	
	public void typeIntoFirstTextBox(String text){
		type(text, into(first(textbox())));
	}
	
	public void typeIntoFirstTextBoxWithParent(String text, String id){
		type(text, into(first(textbox(id))));
	}
	
	public void typeIntoFirstTextBoxWithValueAndParent(String text, String id, String value){
		type(text, into(first(textbox(id, value))));
	}
	
	public void typeIntoFirstTextBoxWithValue(String text, String value){
		typeWithKeys(text, into(first(textboxWithValue(value))));
	}
	
	public void typeIntoTextBoxWithValue(String text, String value, int index){
		typeWithKeys(text, into(indexOf(textboxWithValue(value), index)));
	}
	
	public void typeIntoTextBoxWithValue(String text, String parentId, String value, int index){
		typeWithKeys(text, into(indexOf(textboxWithValue(value, parentId), index)));
	}
	  
	public void clickOnRadioButtonWithLabelUnderParentId(String label, String parentId){
		clickOn(label(label, parentId));
	}
	
	public void clickOnCheckBoxWithLabelUnderParentId(String label, String parentId){
		clickOn(label(label, parentId));
	}
	
	public void clickLabel(String label){
		clickOn(label(label));
	}
	
	public void clickLabelUnderParent(String label, String parentId){
		clickOn(label(label, parentId));
	}
	
	public void clickLabelUnderParent(String label, String parentId, int index){
		clickOn(indexOf(label(label, parentId), index));
	}
	
	public void waitForElement(){
		waitFor(textboxWithValue("Account Number"), 10000);
	}
	
	
	public void clickFirstLinkWithText(String linkText){
		clickOn(first(link(linkText)));
	}
	
	public void clickOnButtonWithTextVal(String buttonText){
		clickOn(first(buttonFind(buttonText)));
	}
	
	
	public void clickOnTextBoxWithValue(String tagID , String textBoxValue){
		clickOn(first(textbox(tagID , textBoxValue)));
	}
}
