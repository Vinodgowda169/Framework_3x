package com.frameWork;

import java.util.List;
import java.util.Map.Entry;
import java.util.logging.LogManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import com.graphbuilder.struc.LinkedList;

public class OrangeHrm extends Driver {

	public static GenericSeleniumActions gsa;
	public static Utils utils;
	String suiteName = "";
	// WebDriver driver = null;
	public OrangeHrm() {
		this.driver = Driver.driver;
		gsa = new GenericSeleniumActions();
		utils = new Utils();		
	}

	
	
	
	
	
    @SuppressWarnings({ "static-access", "unused" })
	public boolean Login(String username , String password,String url) {
		boolean status=false;
		 
		 try {
			GlobalVariables.browserType=browser;
			 String usernameXapth = webElemets.get("usernameXpath");
			 String passwordXpath = webElemets.get("passwordXpath");
			 String LoginXpath = webElemets.get("LoginXpath");
			 
			  gsa=new GenericSeleniumActions();
			 gsa.launchApplication(url);
			 if(!gsa.enterText("xpath",usernameXapth,username,""))
			 {
				 ExtentTestManager. fail_Step_Detailed("Unable To enter the Username field :"+username); 
				//log.info("Unable To enter the Username field :"+username);
				return false;
			 }
			 if(!gsa.enterText("xpath",passwordXpath ,password,""))
			 {
				 ExtentTestManager. fail_Step_Detailed("Unable To enter the password field :"+"*******"); 
				//log.info("Unable To enter the password field :"+"*******");	 
				return false;

			 }
			if(! gsa.click("xpath", "//button[text()=' Login ']","Login Button"))
			{
				 
					return false;

			}
			 ExtentTestManager. pass_Step_Detailed("Sucessfuly logined"); 
			 ExtentTestManager.captureScreenshot_Base64_Detailed("Sucessfuly logined"); 
			  //log.info("Sucessfuly login Happened");

			
			 status=true;
		 }catch (Exception e) {
			 e.printStackTrace();
			 ExtentTestManager. exception_Step_Detailed(e); 
			 ExtentTestManager. fail_Step_Detailed("Unable To Login"); 
			 GlobalVariables.log.info("Sucessfuly login Happened");
				return false;

		}
		return status;
	}
	
}
