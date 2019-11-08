package com.intuit.bpui_qa;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

//import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;
import org.openqa.selenium.support.pagefactory.DefaultFieldDecorator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.internal.LocatingElementHandler;

public class StaleReferenceAwareFieldDecorator extends DefaultFieldDecorator {
//	private static final Logger logger = Logger.getLogger(StaleReferenceAwareFieldDecorator.class.getName());

	public StaleReferenceAwareFieldDecorator(ElementLocatorFactory factory) {
		super(factory);
	}

	protected WebElement proxyForLocator(ClassLoader loader,
			ElementLocator locator, boolean renderedProxy) {
		InvocationHandler handler = new StaleReferenceAwareElementLocator(
				locator);

		WebElement proxy;
		if (renderedProxy) {
			proxy = (WebElement) Proxy
					.newProxyInstance(loader, new Class[] {
							WebElement.class, WrapsElement.class },
							handler);
		} else {
			proxy = (WebElement) Proxy.newProxyInstance(loader, new Class[] {
					WebElement.class, WrapsElement.class }, handler);
		}
		return proxy;
	}

	private static class StaleReferenceAwareElementLocator extends
			LocatingElementHandler {
		private final ElementLocator locator;

		public StaleReferenceAwareElementLocator(ElementLocator locator) {
			super(locator);
			this.locator = locator;
		}

		// here is where the magic happens. For a configurable number of times (I configured
		// 5 times, locater finds the element, and then tries to invoke the method on the element
		// In case StaleElementReferenceException is thrown, try again.
		public Object invoke(Object object, Method method, Object[] objects)
				throws Throwable {
			int count = 0;
			WebElement element = null;
			while (count < 3) {
				try{
					element = locator.findElement();
				}catch(Exception ex){
					//logger.debug("Element not found", ex);
					
//					SeleniumUtility.waitFor(250);
					count++;
					continue;
				}
				if ("getWrappedElement".equals(method.getName())) {
					return element;
				}

				try {
					return invokeMethod(method, element, objects);
				} catch (StaleElementReferenceException ex) {
					//logger.debug("Error locating element", ex);
				}
				count++;
			}
			throw new RuntimeException("Cannot invoke " + method.getName()
//					+ " on element " + SeleniumUtility.getElementName(element)
					+ ". Cannot find it");
		}

		private Object invokeMethod(Method method, WebElement element,
				Object[] objects) throws Throwable {
//			logger.debug("Invoking " + method.getName() + " on "
//					+ SeleniumUtility.getElementName(element));
			try {
				return method.invoke(element, objects);
			} catch (InvocationTargetException e) {
				// Unwrap the underlying exception
				throw e.getCause();
			} catch (IllegalArgumentException e) {
				// Unwrap the underlying exception
				throw e.getCause();
			} catch (IllegalAccessException e) {
				// Unwrap the underlying exception
				throw e.getCause();
			}
		}
	}
}
