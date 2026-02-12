package com.frameWork;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.ini4j.Wini;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphbuilder.struc.LinkedList;

public class Utils {
	static String browser = "", URL = "", username = "", password = "", MaxTime = "", webelementsSheetRefer = "",
			ExecutedBy = "", SuiteName = "";

	public static JSONObject readINIFile(String region) {
		JSONObject regionObj = new JSONObject();
		try {
			region = region.replace("EnvType-", "");
			region = region.replace("\\", "").replace("\"", "");
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("Config.ini");
			Wini ini = new Wini(new File(path));

			ObjectMapper a = new ObjectMapper();
			JSONObject obj = new JSONObject(a.writeValueAsString(ini));
			regionObj = (JSONObject) obj.get(region);
			URL = regionObj.get("URL").toString();

			browser = regionObj.get("Browser").toString();
			username = regionObj.get("Username").toString();
			password = regionObj.get("Password").toString();
			MaxTime = regionObj.get("MaxTime").toString();
			webelementsSheetRefer = regionObj.get("webelementsSheetRefer").toString();
			ExecutedBy = regionObj.get("ExecutedBy").toString();

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace(System.out);
		}
		return regionObj;

	}

	public static Map<String, String> readWebElemets(String SheetName) {
		Map<String, String> returnWebElemts = new LinkedHashMap<String, String>();
		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("WebElemets.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);
			XSSFSheet sheet = wb.getSheet(SheetName);
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
				String Key = sheet.getRow(i).getCell(0).getStringCellValue();
				String value = sheet.getRow(i).getCell(1).getStringCellValue();
				if ((!Key.equalsIgnoreCase("") || !Key.isEmpty())
						&& (!value.equalsIgnoreCase("") || !value.isEmpty())) {

					returnWebElemts.put(Key, value);

				}

			}
			fio.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnWebElemts;

	}

	public static Map<String, java.util.LinkedList<String>> inputEXcel() {
		Map<String, java.util.LinkedList<String>> returnInput = new LinkedHashMap<String, java.util.LinkedList<String>>();

		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("InputExcel.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);
			for (int sh = 0; sh < wb.getNumberOfSheets(); sh++) {
				java.util.LinkedList<String> li = new java.util.LinkedList<String>();
				XSSFSheet sheet = wb.getSheetAt(sh);
				String sheetname = wb.getSheetName(sh).toString();
				for (int i = 1; i < sheet.getLastRowNum(); i++) {
					String Key = sheet.getRow(i).getCell(0).getStringCellValue();
					if ((!Key.equalsIgnoreCase(""))) {
						li.add(Key);

					}

				}
				returnInput.put(sheetname, li);

			}
			fio.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInput;

	}

	public static Map<String, Map<String, String>> inputExcelMapping() {
		Map<String, Map<String, String>> returnInput = new LinkedHashMap<String, Map<String, String>>();

		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("InputExcel.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);

			XSSFSheet sheet = wb.getSheetAt(0);

			for (int i1 = 1; i1 < sheet.getLastRowNum(); i1++) {
				LinkedHashMap<String, String> li = new LinkedHashMap<String, String>();
				String ScenarioName = sheet.getRow(i1).getCell(0).getStringCellValue();
				if (!ScenarioName.isEmpty()) {

					for (int i = 1; i < sheet.getLastRowNum(); i++) {
						String Key = sheet.getRow(i).getCell(1).getStringCellValue();
						String value = sheet.getRow(i).getCell(2).getStringCellValue();

						if ((!Key.equalsIgnoreCase(""))) {
							li.put(Key, value);

						} else {
							break;
						}

					}
				}
				returnInput.put(ScenarioName, li);

			}
			fio.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInput;

	}

	public static Map<String, List<Map<String, String>>> businessLogicFunctions() {
		Map<String, List<Map<String, String>>> returnInput = new LinkedHashMap<String, List<Map<String, String>>>();

		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("BusinessLogic_Functions.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);

			XSSFSheet sheet = wb.getSheetAt(0);

			LinkedHashMap<String, String> li = new LinkedHashMap<String, String>();
			for (int i1 = 1; i1 < sheet.getLastRowNum(); i1++) {

				List<Map<String, String>> s = new java.util.LinkedList<Map<String, String>>();
				String ScenarioName = sheet.getRow(i1).getCell(0).getStringCellValue();
				if (!ScenarioName.isEmpty()) {
					for (int k = 1; k < sheet.getLastRowNum(); k++) {

						String ScenarioName1 = sheet.getRow(k).getCell(1).getStringCellValue();
						String headernameName = sheet.getRow(k).getCell(0).getStringCellValue();

						if (headernameName.equalsIgnoreCase(ScenarioName)) {
							li = new LinkedHashMap<String, String>();

							for (int i = 1; i < sheet.getRow(0).getLastCellNum(); i++) {

								String Key = sheet.getRow(k).getCell(1).getStringCellValue();

								String headerName = sheet.getRow(k).getCell(2).getStringCellValue();
								String Decisin = sheet.getRow(k).getCell(3).getStringCellValue();

								if ((!Key.equalsIgnoreCase(""))) {
									li.put(Key, headerName+"_"+Decisin);

								} else {
									break;
								}

							}
							s.add(li);
						}

					}
					returnInput.put(ScenarioName, s);
				}

			}
			fio.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInput;

	}

	public static Map<String, List<Map<String, String>>> testData1() {
		Map<String, List<Map<String, String>>> returnInput = new LinkedHashMap<String, List<Map<String, String>>>();

		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("testData.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);

			XSSFSheet sheet = wb.getSheetAt(0);

			LinkedHashMap<String, String> li = new LinkedHashMap<String, String>();
			for (int i1 = 1; i1 < sheet.getLastRowNum(); i1++) {

				List<Map<String, String>> s = new java.util.LinkedList<Map<String, String>>();
				String ScenarioName = sheet.getRow(i1).getCell(0).getStringCellValue();
				if (!ScenarioName.isEmpty()) {
					for (int k = 1; k < sheet.getLastRowNum(); k++) {

						String ScenarioName1 = sheet.getRow(k).getCell(1).getStringCellValue();
						String headernameName = sheet.getRow(k).getCell(0).getStringCellValue();

							li = new LinkedHashMap<String, String>();

							for (int i = 1; i < sheet.getRow(0).getLastCellNum(); i++) {

								String Key = sheet.getRow(k).getCell(i).getStringCellValue();

								String headerName = sheet.getRow(0).getCell(i).getStringCellValue();
								if ((!Key.equalsIgnoreCase(""))) {
									li.put(headerName, Key);
								}
								else
								{
									break;
								}
							}
							s.add(li);
						}

					}
					returnInput.put(ScenarioName, s);
				}

			
			fio.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInput;

	}
	
	
	
	
	
	
	public static Map<String,Map<String, String>> testData() {
		Map<String,Map<String, String>> returnInput = new LinkedHashMap<String,Map<String, String>>();

		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("testData.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);

			XSSFSheet sheet = wb.getSheetAt(0);

			for(int i=1;i<=sheet.getLastRowNum();i++) {
				Map<String, String> data = new LinkedHashMap<String, String>();

				String ScenarioName = sheet.getRow(i).getCell(0).getStringCellValue();
				
				for(int j=0;j<sheet.getRow(0).getPhysicalNumberOfCells();j++) {
					String field = sheet.getRow(0).getCell(j).getStringCellValue();
					String value="";
					try {
						value = sheet.getRow(i).getCell(j).getStringCellValue();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						value="";
						}
					data.put(field, value);
				}
				
				returnInput.put(ScenarioName, data);
				
			}
			

			
			fio.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnInput;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static Map<String, LinkedHashMap<String, String>> testCase() {
		Map<String, LinkedHashMap<String, String>> TestData = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("TestCase.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);
           String data="";
			XSSFSheet sheet = wb.getSheetAt(0);
			for (int j = 1; j < sheet.getLastRowNum(); j++) {
				String scenario = "";
				try {
					scenario = sheet.getRow(j).getCell(0).getStringCellValue();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					scenario = "";
				}
				LinkedHashMap<String, String> li = new LinkedHashMap<String, String>();
				if ((!scenario.isEmpty()||data.equalsIgnoreCase(""))&&(!data.equalsIgnoreCase(scenario))) {
					for (int i = 1; i < sheet.getLastRowNum(); i++) {
						String scenarios = sheet.getRow(i).getCell(0).getStringCellValue();
						if (scenarios.equalsIgnoreCase(scenario)) {
							String Key = sheet.getRow(i).getCell(1).getStringCellValue();
							String value = sheet.getRow(i).getCell(2).getStringCellValue();
							if ((!Key.equalsIgnoreCase("") || !Key.isEmpty())
									&& (!value.equalsIgnoreCase("") || !value.isEmpty())) {

								li.put(Key, value);
							}
						}
					}
				}
				if(!scenario.isEmpty()&&!TestData.containsKey(scenario))
				{
				data=scenario;
				TestData.put(scenario, li);
				}
			}
			fio.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TestData;

	}

	public static Map<String, String> scenarios() {
		Map<String, String> scenario = new LinkedHashMap<String, String>();

		try {
			String path = System.getProperty("user.dir");
			path = path.concat("\\").concat("Files").concat("\\").concat("Scenarios.xlsx");
			FileInputStream fio = new FileInputStream(path);
			XSSFWorkbook wb = new XSSFWorkbook(fio);

			for (int sh = 1; sh < wb.getSheetAt(0).getPhysicalNumberOfRows(); sh++) {
				String key = "", value = "";
				try {
					key = wb.getSheetAt(0).getRow(sh).getCell(0).toString();
				} catch (Exception e) {
					key = "";
				}
				try {
					value = wb.getSheetAt(0).getRow(sh).getCell(1).toString();
				} catch (Exception e) {
					value = "";
				}

				scenario.put(key, value);
			}
			fio.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return scenario;

	}

	public static String TestdataValueToget(Map<String, LinkedHashMap<String, String>> Input, String Keys,
			String ValueToget) {
		LinkedHashMap<String, String> Inputs = Input.get(Keys);
		String value = "";
		try {
			
			
			for (Entry<String, String> entry : Inputs.entrySet()) {
				String Key = entry.getKey();
				value = Inputs.get(ValueToget).toString();
				if (ValueToget.equalsIgnoreCase(Key)) {
					value = Inputs.get(ValueToget).toString();
					break;

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return value;

	}

	public static boolean setExtendReport() {
		boolean status = false;

		try {

			SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");

			Date date = new Date(System.currentTimeMillis());

			String dateTime = formatter.format(date);
			// Creating log Directory

			// Creating report Directory

			String reportDirectoryPath = System.getProperty("user.dir") + "/reports/" + dateTime;
			String logDirectoryPath = System.getProperty("user.dir") + "/logDirectory/" + dateTime;
			File repFilPath = new File(reportDirectoryPath);

			if (!repFilPath.exists()) {

				repFilPath.mkdirs();

				System.out.println("Reports Directory created : " + reportDirectoryPath);

			} else {

				System.out.println("Reports Directory Already Exists : " + reportDirectoryPath);

			}

			String logRepName = logDirectoryPath + "/" + "SC-5454545" + ".log";
			String fileSuffix = new SimpleDateFormat("HH-mm-ss").format(new Date());
			String detailedReportName = reportDirectoryPath + "/DetailedReport_" + fileSuffix.toString() + "" + ".html";

			String summaryReportName = reportDirectoryPath + "/SummaryReport" + ".html";

			Path pathlogRepName = Paths.get(logRepName);

			Files.deleteIfExists(pathlogRepName);

			Path pathdetailedReportName = Paths.get(detailedReportName);

			Files.deleteIfExists(pathdetailedReportName);

			Path pathsummaryReportName = Paths.get(summaryReportName);

			Files.deleteIfExists(pathsummaryReportName);

			// Put all the paths in the global variables

			GlobalVariables.exeMap.put("logFile", logRepName);

			GlobalVariables.exeMap.put("detailedReport", detailedReportName);

			GlobalVariables.exeMap.put("summaryReport", summaryReportName);

			status = true;

		} catch (Exception e) {

			e.printStackTrace();

		}

		return status;

	}

//	public static Logger getLogger(String name) throws IOException {
//		Logger logger = Logger.getLogger(name);
//
//		// Create folder with current date
//		String dateFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//		Path logDir = Paths.get("logs", dateFolder);
//		Files.createDirectories(logDir);
//		// Create file with current timestamp
//		String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH-mm-ss"));
//		Path logFiles = logDir.resolve("app-" + timestamp + ".log");
//		
//		 File logFile = new File(logFiles.toString());		 
//		 
//		 
//	        if (logFile.exists()) {
//	            if (logFile.delete()) {
//	                System.out.println("Log file deleted successfully.");
//	            } else {
//	                System.out.println("Failed to delete the log file.");
//	            }
//	        }
//		FileHandler fileHandler = new FileHandler(logFiles.toString(), true);
//		fileHandler.setFormatter(new SimpleFormatter());
//		logger.addHandler(fileHandler);
//		logger.setUseParentHandlers(false); // avoid duplicate console logs
//
//		return logger;
//	}
//
//	public static void deleteDirectory(File dir) {
//		File[] files = dir.listFiles();
//
//		if (files != null) {
//			for (File file : files) {
//				if (file.isDirectory()) {
//					deleteDirectory(file);
//				} else {
//					file.delete();
//				}
//			}
//		}
//
//		dir.delete();
//	}
	public static String duration() {
		String hhmmss = "";
		try {
			long end = System.nanoTime();
			long execution = end - GlobalVariables.ExecutionStartTime;
			long totalSeconds = TimeUnit.NANOSECONDS.toSeconds(Math.abs(execution));
			long hours = totalSeconds / 3600;
			long minutes = (totalSeconds % 3600) / 60;
			long seconds = totalSeconds % 60;
			hhmmss = String.format("%02d:%02d:%02d", hours, minutes, seconds);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return hhmmss;
	}
}
