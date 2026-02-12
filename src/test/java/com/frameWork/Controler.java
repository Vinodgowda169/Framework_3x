package com.frameWork;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import com.aventstack.extentreports.ExtentReports;

public class Controler extends Driver {

	public static GenericSeleniumActions gsa;
	public static ExtentTestManager ExtentTestManager;
	public static com.frameWork.OrangeHrm OrangeHrm;
	public static Map<String, String> result = new LinkedHashMap<>();

	public static Utils utils;
	String suiteName = "";
	public static ExtentManager ExtentManager;
	WebDriver driver = null;

	public Controler(WebDriver driver) {
		this.driver = driver;
		ExtentTestManager = new ExtentTestManager();
		ExtentManager = new ExtentManager();
		gsa = new GenericSeleniumActions();
		utils = new Utils();
		OrangeHrm = new OrangeHrm();

	}

	public boolean stepControler() {
		boolean status = false;
		try {
			long start = System.nanoTime();
			GlobalVariables.ExecutionStartTime = start;
			
for(Entry<String, String> Scenariomap:scenarioMap.entrySet())
{
	boolean statuskey=true;
	String scenarioKey=Scenariomap.getKey();
	String value=scenarioMap.get(scenarioKey);
	 ExtentTestManager. startTestDetailed(scenarioKey+": Feature started");
	if(value.equalsIgnoreCase("Y")||value.equalsIgnoreCase("Yes"))
	{
		
		for( Entry<String, LinkedHashMap<String, String>> testsCaseMap:testCaseMap.entrySet())
		{
			LinkedHashMap<String, String> testCaseList=testCaseMap.get(scenarioKey);
			
			for(  Entry<String, String> testsCaseListMap:testCaseList.entrySet())
			{
				
				String keyMapList=testsCaseListMap.getKey();
				String valueMapList=testCaseList.get(keyMapList);
				
			if(valueMapList.equalsIgnoreCase("Y")||valueMapList.equalsIgnoreCase("Yes"))
			{
				for(  Entry<String, List<Map<String, String>>> busineLogicFunctionsMap:businessLogicFunctionsMap.entrySet())
				{
					
					List<Map<String, String>> Businesslist=	businessLogicFunctionsMap.get(keyMapList);
					
					for(int i=0;i<Businesslist.size();i++)
					{
						
						Map<String, String> map=Businesslist.get(i)	;
						
						
						for(   Entry<String, String> maps:map.entrySet())
						{
							String testStepKey=maps.getKey();
							String testStepValue=map.get(testStepKey);
							String Descition=testStepValue.split("_")[1].toString();
							if(Descition.equalsIgnoreCase("Y")||Descition.equalsIgnoreCase("Yes"))
							{
								
								
								for(  Entry<String, Map<String, String>> testsDataMap:testDataMap.entrySet())
								{
									
									Map<String, String> mp=	testDataMap.get(scenarioKey);
									
										String dec=mp.get("Decision(Y/N)".toString().trim());
										
										if(statuskey)
										{
										if(dec.equalsIgnoreCase("Y")||dec.equalsIgnoreCase("Yes"))
										{
											
											gsa=new GenericSeleniumActions();
											if(testStepKey.equalsIgnoreCase("launchURL")&&!mp.get("Url").toString().isEmpty())
											{
												
												 if(!gsa.launchApplication(mp.get("Url").toString()))
												 {
														gsa.logout("Logout Button");
														statuskey= false;
													}
											}
											if(testStepKey.equalsIgnoreCase("enterText"))
											{
												if(!gsa.enterText("xpath",webElemets.get(testStepValue.split("_")[0].toString()) ,mp.get(testStepValue.split("_")[0].toString()),keyMapList+"_"+testStepValue.split("_")[0].toString()))
												{
													gsa.logout("Logout Button");
													statuskey= false;
												}
												
											}
											if(testStepKey.equalsIgnoreCase("click"))
											{
												if(!gsa.click("xpath",webElemets.get(testStepValue.split("_")[0].toString()) ,keyMapList+"_"+testStepValue.split("_")[0].toString()))
												{
													gsa.logout("Logout Button");
													statuskey= false;
												}
												
											}
											if(testStepKey.equalsIgnoreCase("clickUsingReplace"))
											{
												if(!gsa.clickUsingReplace("xpath",webElemets.get(testStepValue.split("_")[0].toString()),mp.get(testStepValue.split("_")[0].toString()),keyMapList+"_"+testStepValue.split("_")[0].toString()))
												{
													gsa.logout("Logout Button");
													statuskey= false;
												}
												
											}
											if(testStepKey.equalsIgnoreCase("validateUsingReplace"))
											{
												if(!gsa.existReplace("xpath",webElemets.get(testStepValue.split("_")[0].toString()),mp.get(testStepValue.split("_")[0].toString()),keyMapList+"_"+testStepValue.split("_")[0].toString()))
												{
													gsa.logout("Logout Button");
													statuskey= false;
												}
												
											}
											
											
											
											break;
											
											
											
										}
										}
									
								}
								
								
								
								
								
							}
						
							
							
						}
						
						
						
						
						
					}
					
					
					
					break;
					
					
					
					
				}
				
				
				
				
				
				
				
				
				
				
			}
				
			
				
			}
			
			
			break;
		}
		
		
		
		
	}
	
	
	
	
	
	
}
			
			
			
			
			
			
			
			
			
			
			
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

}
