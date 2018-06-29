package com.smart.global.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ParseJsonUtil
{
	private static final String  MACHINE_CONFIG = "config/machines/";
	private JSONObject jsonObject;
	private Map<String,JSONObject> jsonObjectMap;
	
	public Map<String,JSONObject> getJsonObjectMap(){
		return jsonObjectMap;
	}
	public  ParseJsonUtil(){
		List<String> contentFolder = readContentFolder();
		init(contentFolder);
	}
	
	 public List<String> readContentFolder()
	{
		File folder = new File("config/machines/");
		File[] listOfFiles = folder.listFiles();
		List<String> list = new ArrayList<String>();
		
	    for (int i = 0; i < listOfFiles.length; i++) {
	      if (listOfFiles[i].isFile()&& FileUtil.getExtension(listOfFiles[i].getName()).equals("json")) {
	    	  list.add(listOfFiles[i].getName() ); 
	      }
	    }
	    return list ; 
	}
	 
	public void init(List<String> files)
    {
		jsonObjectMap = new HashMap<String,JSONObject>();
        for (int i = 0; i < files.size(); i++)
        {
            JSONObject jsonObj = parseToJson(files.get(i));
            jsonObjectMap.put(files.get(i), jsonObj);
        }
           
    }
	
	public JSONObject parseToJson(String filename)
	{  
		try {
			FileReader reader = new FileReader(MACHINE_CONFIG+filename ); 
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(reader);
            return jsonObject;
		} catch (FileNotFoundException ex) {
			
		} catch (IOException | org.json.simple.parser.ParseException ex) {
			
		}
		return null;
	}
	
}
