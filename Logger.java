package com.intuit.bpui_qa;

import org.testng.Reporter;

public class Logger {

	private static boolean displayToSystem = false;
//	private static final StringBuilder defaultError = new StringBuilder("<br><span style=\"color:red\">[F]: </span>");
//	private static final StringBuilder defaultWarning = new StringBuilder("<br><span style=\"color:orange\">[W]: </span>");
//	private static final StringBuilder defaultSucess = new StringBuilder("<br><span style=\"color:green\">[P]</span>");
//	private static final StringBuilder defaultInfo = new StringBuilder("<br><span style=\"color:blue\">[I]: </span>");
//	private static final StringBuilder step = new StringBuilder("<span style=\"color:purple\">[STEP]: </span>");
	
	
	public void Logger(){
		displayToSystem = false;
	}
	
	public void Logger(boolean displayToSystem){
		displayToSystem = displayToSystem;
	}
		
	public static void error(String error){
		Reporter.log("<br><span style=\"color:red\">[F]: "+error+"</span>", displayToSystem);
//		Reporter.log(defaultError.append(error).toString(), displayToSystem);
	}
	
	public static void warn(String warning){
		Reporter.log("<br><span style=\"color:orange\">[W]: "+warning+"</span>", displayToSystem);
//		Reporter.log(defaultWarning.append(warning).toString(), displayToSystem);
	}
	
	public static void info(String info){
		Reporter.log("<br><span style=\"color:blue\">[I]: "+info+"</span>", displayToSystem);
//		Reporter.log(defaultInfo.append(info).toString(), displayToSystem);
	}

	public static void step(String steps){
		Reporter.log("<br><span style=\"color:purple\">[STEP]: "+ steps +"</span>", displayToSystem);
//		Reporter.log(step.append(steps).toString(), displayToSystem);
	}
	
	public static void success(String success){
		Reporter.log("<br><span style=\"color:green\">[P]: "+success+"</span>", displayToSystem);
//		Reporter.log(defaultSucess.append(success).toString(), displayToSystem);
	}
}
