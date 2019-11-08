package com.intuit.bpui_qa;

import java.util.List;

import org.openqa.selenium.WebElement;

public interface BasePage {
	List<WebElement> pageElementsToWait();
	void openPage();
	void initParent();
	
}
