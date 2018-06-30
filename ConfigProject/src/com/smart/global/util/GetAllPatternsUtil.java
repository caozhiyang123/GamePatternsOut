package com.smart.global.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.besjon.pojo.JsonRootBean;
import com.besjon.pojo.Pattern;

public class GetAllPatternsUtil
{
	private int cardWidth;
	private int cardHeight;
	private JsonRootBean jsonRootBean = null;
	private List<Pattern> getMachinePatterns(int machineID,String fileName){
		ParseJsonToVO parseJsonToVO = new ParseJsonToVO();
		Map<String, JsonRootBean> machinePatterns = parseJsonToVO.getMachinePatterns();
		jsonRootBean = machinePatterns.get(fileName);
		cardWidth = jsonRootBean.getCardWitdh();
		cardHeight = jsonRootBean.getCardHeight();
		if(jsonRootBean!=null && jsonRootBean.getMachineId() == machineID){
			List<Pattern> patterns = jsonRootBean.getPattern();
			return patterns;
		}
		return null;
	}
	
	public  List<Pattern> getPatterns(int gameId,String fileName){
		 List<Pattern> patterns = getMachinePatterns(gameId, fileName);
		 return patterns;
	}
	
	private final int numPerCardOfAmerican = 25;
	private final int numPerCardOfPachinko = 0;
	private final int numPerCardOfShowBall = 0;
	
	private void initPatterns(List<Pattern> patterns)
    {
        double weight = 0;
        for (Pattern pattern : patterns)
        {
            weight += pattern.getWeight();
            if (weight > 1.0)
            {
                System.out.println(("patternVO weight much than 1!"));
            }
            pattern.setWeight(weight);
            int code = PatternUtil.parsePatternCode(pattern.getFormat());
            pattern.setFormatCode(code);
            pattern.setSunNum(PatternUtil.sunNum(code, cardHeight*cardWidth));
        }
    }
	
	//去重
	public List<Pattern> getPossiblePatternsCompareToSelf(List<Pattern> patterns){
		List<Pattern> newPatternsList = new ArrayList<Pattern>();
		Map<Integer, Pattern> patternsMap = new HashMap<Integer,Pattern>();
		for (Pattern pattern : patterns)
		{
			if(patternsMap.containsKey(pattern.getFormatCode()) && (patternsMap.get(pattern.getFormatCode()).getValue()<pattern.getValue())){
				patternsMap.remove(pattern.getFormatCode());
				patternsMap.put(pattern.getFormatCode(), pattern);
			}else if(!patternsMap.containsKey(pattern.getFormatCode())){
				patternsMap.put(pattern.getFormatCode(), pattern);
			}
		}
		Collection<Pattern> patternsColl = patternsMap.values();
		newPatternsList.addAll(patternsColl);
		return newPatternsList;
		
	}
	//去重
	public List<Pattern> getPossiblePatternsCompareToSelf2(List<Pattern> patterns){
		List<Pattern> newPatternsList = new ArrayList<Pattern>();
		Map<Integer, Pattern> patternsMap = new HashMap<Integer,Pattern>();
		for (Pattern pattern : patterns)
		{
			if(patternsMap.containsKey(pattern.getFormatCode())){
				patternsMap.remove(pattern.getFormatCode());
				patternsMap.put(pattern.getFormatCode(), pattern);
			}else if(!patternsMap.containsKey(pattern.getFormatCode())){
				patternsMap.put(pattern.getFormatCode(), pattern);
			}
		}
		Collection<Pattern> patternsColl = patternsMap.values();
		newPatternsList.addAll(patternsColl);
		return newPatternsList;
		
	}
	
	
	public void getPossiblePatternsFormThreeInN(List<Pattern> patterns){
		List<Pattern> newPatterns = new ArrayList<>();
		
		//American C(3/n)
		for(int i=1;i<patterns.size();i++){
			   for(int j=i+1;j<patterns.size();j++){
				   for(int k = j+1;k<patterns.size();k++){
					   Pattern pattern = new Pattern();
					   pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue());
					   pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName());
					   int code1 = patterns.get(i).getFormatCode();
					   int code2 = patterns.get(j).getFormatCode();
					   int code3 = patterns.get(k).getFormatCode();
					   int codeNew = code1 | code2 | code3;
					   String patternNew = Integer.toBinaryString(codeNew);
					   if(patternNew.length()<25){
						   patternNew = "00000"+patternNew;
					   }
					   pattern.setFormat(patternNew);
					   pattern.setFormatCode(codeNew);
					   pattern.setSunNum(PatternUtil.sunNum(codeNew, cardHeight*cardWidth));
					   newPatterns.add(pattern);
				   }
			   }
		   }
		//去重
		newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		
		patterns.addAll(newPatterns);
		   //去重
		patterns = getPossiblePatternsCompareToSelf(patterns);
		
	}
	
	
	public void getPossiblePatternsFromTwoInN(List<Pattern> patterns){
	   List<Pattern> newPatterns = new ArrayList<>();
	   
	   //American C(2/n)
	   for(int i=1;i<patterns.size();i++){
		   for(int j=i+1;j<patterns.size();j++){
			   Pattern pattern = new Pattern();
			   pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue());
			   pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName());
			   int code1 = patterns.get(i).getFormatCode();
			   int code2 = patterns.get(j).getFormatCode();
			   int codeNew = code1 | code2;
			   String patternNew = Integer.toBinaryString(codeNew);
			   if(patternNew.length()<25){
				   patternNew = "00000"+patternNew;
			   }
			   pattern.setFormat(patternNew);
			   pattern.setFormatCode(codeNew);
			   pattern.setSunNum(PatternUtil.sunNum(codeNew, cardHeight*cardWidth));
			   newPatterns.add(pattern);
		   }
	   }
	   //去重
	   newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
	   
	   patterns.addAll(newPatterns);
	   //去重
	   patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	
	public static void main(String[] args)
	{
		GetAllPatternsUtil getAllPatternsUtil = new GetAllPatternsUtil();
		//gameId:29
		getAmericanPatternsNew(getAllPatternsUtil,29,"American.json");
	}

	public static void getAmericanPatternsNew(GetAllPatternsUtil getAllPatternsUtil,int gameId,String fileName)
	{
		JsonRootBean jsonRoot = getAmericanPatterns(getAllPatternsUtil,gameId,fileName);
		//去重
		List<Pattern> possiblePatterns = getAllPatternsUtil.getPossiblePatternsCompareToSelf(jsonRoot.getPattern());
		//add defalut pattern
		List<Pattern> patterns3 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns3);
		possiblePatterns.addAll(patterns3);
		//去重
		possiblePatterns = getAllPatternsUtil.getPossiblePatternsCompareToSelf2(possiblePatterns);
		//按照value从大到小重新排序
		Collections.sort(possiblePatterns);
		//别名重定义
		int a = 0;
		for (Pattern pattern : possiblePatterns)
		{
			pattern.setAlias("P"+a);
			a++;
		}
		jsonRoot.setPattern(possiblePatterns);
		System.out.println(jsonRoot.toString());
	}
	

	public static JsonRootBean getAmericanPatterns(GetAllPatternsUtil getAllPatternsUtil,int gameId,String fileName)
	{
		//2/7
		List<Pattern> patterns = getAllPatternsUtil.getPatterns(gameId,fileName);//2/7,
		getAllPatternsUtil.initPatterns(patterns);
		getAllPatternsUtil.getPossiblePatternsFromTwoInN(patterns);
		//3/7
		List<Pattern> patterns2 = getAllPatternsUtil.getPatterns(gameId,fileName);//3/7
		getAllPatternsUtil.initPatterns(patterns2);
		getAllPatternsUtil.getPossiblePatternsFormThreeInN(patterns2);
		patterns.addAll(patterns2);
		//rounstastics
		getAllPatternsUtil.jsonRootBean.setPattern(patterns);
		return getAllPatternsUtil.jsonRootBean;
	}
	
	
	
	
	
	
	
	
	
	
}
