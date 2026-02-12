package com.frameWork;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Demo {
	public static void main(String[] args) {
		test();
	}
	public static void test() {
		 Map<String, Map<String, String>> pm = new LinkedHashMap<String, Map<String, String>>();
	        
	        Map<String, String> cm = new LinkedHashMap<String, String>();
	        cm.put("ssaas","asF");
	        cm.put("KFJD","dhsj");
	        cm.put("ska","ert");
	        cm.put("mckdl","akka");
	        cm.put("qjqkq","jddks");
	        
	        pm.put("parent", cm);
	        
	        for(Entry<String, Map<String, String>> entry1 : pm.entrySet() ){
	            String key=entry1.getKey();
	          Map<String, String> cm1=  pm.get(key);
	            for(Entry<String, String>entry : cm1.entrySet() ){
	                           String insideKey = entry.getKey() ;
	                           System.out.println(insideKey);
	                           System.out.println(pm.get(insideKey));
	                           

	            }
	        }
	}

}
