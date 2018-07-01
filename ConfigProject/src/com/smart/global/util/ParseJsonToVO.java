package com.smart.global.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.besjon.pojo.JsonRootBean;
import com.besjon.pojo.Pattern;

public class ParseJsonToVO
{
   private JSONObject jsonObject;
   private Map<String, JsonRootBean> machineVOMaps;
   private JsonRootBean jsonRootBean;
   private List<Pattern> patterns;
   public Map<String, JsonRootBean> getMachinePatterns(){
	   machineVOMaps = new HashMap<String, JsonRootBean>();
	   ParseJsonUtil parseJsonUtil = new ParseJsonUtil();
	   Map<String, JSONObject> jsonObjectMap = parseJsonUtil.getJsonObjectMap();
	   Set<Entry<String, JSONObject>> entrySet = jsonObjectMap.entrySet();
	   for (Entry<String, JSONObject> entry : entrySet)
       {
           String key = entry.getKey();
           if (!"main.json".equals(key))
           {
               jsonObject = entry.getValue();
               jsonRootBean = new JsonRootBean();
               jsonRootBean.setMachineId(Integer.parseInt(jsonObject.get("id").toString()));
               jsonRootBean.setMachineName(jsonObject.get("name").toString());
               jsonRootBean.setCardWitdh(Integer.parseInt(jsonObject.get("cardWidth").toString()));
               jsonRootBean.setCardHeight(Integer.parseInt(jsonObject.get("cardHeight").toString()));
               jsonRootBean.setAvailableBalls(Integer.parseInt(jsonObject.get("availableBalls").toString()));
               jsonRootBean.setMaxPrizeValue(Integer.parseInt(jsonObject.get("maxPrizeValue").toString()));
               this.setMachinePattern();
               jsonRootBean.setPattern(getMachinePattern());
               machineVOMaps.put(key, jsonRootBean);
           }
       }
	   return  machineVOMaps;
   }
	private List<Pattern> getMachinePattern()
	{
		return patterns;
	}
	private void setMachinePattern()
	{
		patterns = new ArrayList<Pattern>();
		JSONArray patternList = (JSONArray) jsonObject.get("pattern");
		Iterator patternI = patternList.iterator();
        while (patternI.hasNext())
        {
        	Pattern patternVO = new Pattern();
            JSONObject objPattern = (JSONObject) patternI.next();
            patternVO.setName(objPattern.get("name").toString());
            patternVO.setAlias(objPattern.get("alias").toString());
            patternVO.setFormat((String) objPattern.get("format"));
            patternVO.setValue((Integer.parseInt(objPattern.get("value").toString())));
            patternVO.setR(Integer.parseInt(objPattern.get("r").toString()));
            patternVO.setWeight(Double.parseDouble((objPattern.get("weight").toString())));
            patternVO.setIs_eb("false".equals(objPattern.get("is_eb").toString()) ? false : true);
            patterns.add(patternVO);
        }
        jsonRootBean.setPattern(patterns);
		
	}
}
