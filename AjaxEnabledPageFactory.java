package com.intuit.bpui_qa;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import com.intuit.bpui_qa.StaleReferenceAwareFieldDecorator;

public class AjaxEnabledPageFactory {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends PageFactory> T initializePage(WebDriver driver, Class pageClass) {
		Object page = createInstance(driver, pageClass);
		PageFactory.initElements(new StaleReferenceAwareFieldDecorator(
				new AjaxElementLocatorFactory(driver,
						250)), page);
		return (T) page;
	}

	public static <T extends PageFactory> T createInstance(WebDriver driver, Class pageClassToProxy){
		try {
			try {
				Constructor constructor = pageClassToProxy.getConstructor(WebDriver.class);
				return (T) constructor.newInstance(driver);
			} catch (NoSuchMethodException e) {
				return (T) pageClassToProxy.newInstance();
			}
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}

