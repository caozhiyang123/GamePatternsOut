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
	private int maxPrizeValue;
	private JsonRootBean jsonRootBean = null;
	private List<Pattern> getMachinePatterns(int machineID,String fileName){
		ParseJsonToVO parseJsonToVO = new ParseJsonToVO();
		Map<String, JsonRootBean> machinePatterns = parseJsonToVO.getMachinePatterns();
		jsonRootBean = machinePatterns.get(fileName);
		cardWidth = jsonRootBean.getCardWitdh();
		cardHeight = jsonRootBean.getCardHeight();
		maxPrizeValue = jsonRootBean.getMaxPrizeValue();
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
	private final int numPerCardOfPachinko = 25;
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

	private void getPossiblePatternsFromNineInN(List<Pattern> patterns) {
		List<Pattern> newPatterns = new ArrayList<>();
		Pattern pattern = null;
		int i,j,k,L,m,n,p,q ,r= 0 ;
		int code1,code2,code3,code4,code5,code6,code7,code8,code9,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
		int size = patterns.size();
		for(i=0;i<size;i++){
			if(i==size-1) {
				System.out.println(i);
			}
			for(j=i+1;j<size;j++){
				for(k = j+1;k<size;k++){
					for(L=k+1;L< size;L++) {
						for(m=L+1;m<size;m++) {
							for(n=m+1;n<size;n++) {
								for(p=n+1;p<size;p++) {
									for(q=p+1;q<size;q++) {
										for(r=q+1;r<size;r++) {
											int value = patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue()+patterns.get(n).getValue()+patterns.get(p).getValue()+patterns.get(q).getValue()+patterns.get(r).getValue();
											if(value >maxPrizeValue) {
												continue;
											}
											pattern = new Pattern();
											pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue()+patterns.get(n).getValue()+patterns.get(p).getValue()+patterns.get(q).getValue()+patterns.get(r).getValue());
											pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName()+"+"+patterns.get(L).getName()+"+"+patterns.get(m).getName()+"+"+patterns.get(n).getName()+"+"+patterns.get(p).getName()+"+"+patterns.get(q).getName()+"+"+patterns.get(r).getName());
											code1 = patterns.get(i).getFormatCode();
											code2 = patterns.get(j).getFormatCode();
											code3 = patterns.get(k).getFormatCode();
											code4 = patterns.get(L).getFormatCode();
											code5 = patterns.get(m).getFormatCode();
											code6 = patterns.get(n).getFormatCode();
											code7 = patterns.get(p).getFormatCode();
											code8 = patterns.get(q).getFormatCode();
											code9 = patterns.get(r).getFormatCode();
											codeNew = code1 | code2 | code3 |code4 |code5 |code6 |code7 |code8 |code9;
											patternNew = Integer.toBinaryString(codeNew);
											if(patternNew.length()< numPerCard){
												patternNew = "00000"+patternNew;
											}
											pattern.setFormat(patternNew);
											pattern.setFormatCode(codeNew);
											pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
											newPatterns.add(pattern);
											//去重 避免Heap内存溢出
											newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
										}
									}
								}
							}
						}
					}
				}
			}
		}
		//去重
		newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		
		patterns.addAll(newPatterns);
		//去重
		patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	private void getPossiblePatternsFromEightInN(List<Pattern> patterns) {
		List<Pattern> newPatterns = new ArrayList<>();
		Pattern pattern = null;
		int i,j,k,L,m,n,p,q = 0 ;
		int code1,code2,code3,code4,code5,code6,code7,code8,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
		int size = patterns.size();
		for(i=0;i<size;i++){
			if(i==size-1) {
				System.out.println(i);
			}
			for(j=i+1;j<size;j++){
				for(k = j+1;k<size;k++){
					for(L=k+1;L< size;L++) {
						for(m=L+1;m<size;m++) {
							for(n=m+1;n<size;n++) {
								for(p=n+1;p<size;p++) {
									for(q=p+1;q<size;q++) {
										int value = patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue()+patterns.get(n).getValue()+patterns.get(p).getValue()+patterns.get(q).getValue();
										if(value >maxPrizeValue) {
											continue;
										}
										pattern = new Pattern();
										pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue()+patterns.get(n).getValue()+patterns.get(p).getValue()+patterns.get(q).getValue());
										pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName()+"+"+patterns.get(L).getName()+"+"+patterns.get(m).getName()+"+"+patterns.get(n).getName()+"+"+patterns.get(p).getName()+"+"+patterns.get(q).getName());
										code1 = patterns.get(i).getFormatCode();
										code2 = patterns.get(j).getFormatCode();
										code3 = patterns.get(k).getFormatCode();
										code4 = patterns.get(L).getFormatCode();
										code5 = patterns.get(m).getFormatCode();
										code6 = patterns.get(n).getFormatCode();
										code7 = patterns.get(p).getFormatCode();
										code8 = patterns.get(q).getFormatCode();
										codeNew = code1 | code2 | code3 |code4 |code5 |code6 |code7 |code8;
										patternNew = Integer.toBinaryString(codeNew);
										if(patternNew.length()< numPerCard){
											patternNew = "00000"+patternNew;
										}
										pattern.setFormat(patternNew);
										pattern.setFormatCode(codeNew);
										pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
										newPatterns.add(pattern);
										//去重 避免Heap内存溢出
										newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
									}
								}
							}
						}
					}
				}
			}
		}
		//去重
		newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		
		patterns.addAll(newPatterns);
		//去重
		patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	
	
	private void getPossiblePatternsFromSevenInN(List<Pattern> patterns) {
		List<Pattern> newPatterns = new ArrayList<>();
		Pattern pattern = null;
		int i,j,k,L,m,n,p = 0 ;
		int code1,code2,code3,code4,code5,code6,code7 ,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
		int size = patterns.size();
		for(i=0;i<size;i++){
			if(i==size-1) {
				System.out.println(i);
			}
			for(j=i+1;j<size;j++){
				for(k =j+1;k<size;k++){
					for(L=k+1;L< size;L++) {
						for( m=L+1;m<size;m++) {
							for( n=m+1;n<size;n++) {
								for( p=n+1;p<size;p++) {
									int value = patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue()+patterns.get(n).getValue()+patterns.get(p).getValue();
									if(value >maxPrizeValue) {
										continue;
									}
									//去重
									pattern = new Pattern();
									pattern.setValue(value);
									pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName()+"+"+patterns.get(L).getName()+"+"+patterns.get(m).getName()+"+"+patterns.get(n).getName()+"+"+patterns.get(p).getName());
									code1 = patterns.get(i).getFormatCode();
									code2 = patterns.get(j).getFormatCode();
									code3 = patterns.get(k).getFormatCode();
									code4 = patterns.get(L).getFormatCode();
									code5 = patterns.get(m).getFormatCode();
									code6 = patterns.get(n).getFormatCode();
									code7 = patterns.get(p).getFormatCode();
									codeNew = code1 | code2 | code3 |code4 |code5 |code6 |code7;
									patternNew = Integer.toBinaryString(codeNew);
									if(patternNew.length()< numPerCard){
										patternNew = "00000"+patternNew;
									}
									pattern.setFormat(patternNew);
									pattern.setFormatCode(codeNew);
									pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
									newPatterns.add(pattern);
									//去重 避免Heap内存溢出
									newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
								}
							}
						}
					}
				}
			}
		}
		//去重
		newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		
		patterns.addAll(newPatterns);
		//去重
		patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	
	private void getPossiblePatternsFromSixInN(List<Pattern> patterns) {
		List<Pattern> newPatterns = new ArrayList<>();
		Pattern pattern = null;
		int i,j,k,L,m,n,p = 0 ;
		int code1,code2,code3,code4,code5,code6,code7 ,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
		int size = patterns.size();
		for(i=0;i<size;i++){
			if(i==size-1) {
				System.out.println(i);
			}
			for(j=i+1;j<size;j++){
				for(k = j+1;k<size;k++){
					for(L=k+1;L<size;L++) {
						for(m=L+1;m<size;m++) {
							for(n=m+1;n<size;n++) {
								int value = patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue()+patterns.get(n).getValue();
								if(value >maxPrizeValue) {
									continue;
								}
								pattern = new Pattern();
								pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue()+patterns.get(n).getValue());
								pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName()+"+"+patterns.get(L).getName()+"+"+patterns.get(m).getName()+"+"+patterns.get(n).getName());
								code1 = patterns.get(i).getFormatCode();
								code2 = patterns.get(j).getFormatCode();
								code3 = patterns.get(k).getFormatCode();
								code4 = patterns.get(L).getFormatCode();
								code5 = patterns.get(m).getFormatCode();
								code6 = patterns.get(n).getFormatCode();
								codeNew = code1 | code2 | code3 |code4 |code5 |code6;
								patternNew = Integer.toBinaryString(codeNew);
								if(patternNew.length()< numPerCard){
									patternNew = "00000"+patternNew;
								}
								pattern.setFormat(patternNew);
								pattern.setFormatCode(codeNew);
								pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
								newPatterns.add(pattern);
								//去重 避免Heap内存溢出
								newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
							}
						}
					}
				}
			}
		}
		//去重
		newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		
		patterns.addAll(newPatterns);
		//去重
		patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	private void getPossiblePatternsFromFiveInN(List<Pattern> patterns) {
		List<Pattern> newPatterns = new ArrayList<>();
		Pattern pattern = null;
		int i,j,k,L,m= 0 ;
		int code1,code2,code3,code4,code5,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
		int size = patterns.size();
		for(i=0;i<size;i++){
			for(j=i+1;j<size;j++){
				for( k = j+1;k<size;k++){
					for(L=k+1;L<size;L++) {
						for(m=L+1;m<size;m++) {
							int value = patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue();
							if(value >maxPrizeValue) {
								continue;
							}
							pattern = new Pattern();
							pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue()+patterns.get(m).getValue());
							pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName()+"+"+patterns.get(L).getName()+"+"+patterns.get(m).getName());
							code1 = patterns.get(i).getFormatCode();
							code2 = patterns.get(j).getFormatCode();
							code3 = patterns.get(k).getFormatCode();
							code4 = patterns.get(L).getFormatCode();
							code5 = patterns.get(m).getFormatCode();
							codeNew = code1 | code2 | code3 |code4 |code5;
							patternNew = Integer.toBinaryString(codeNew);
							if(patternNew.length()< numPerCard){
								patternNew = "00000"+patternNew;
							}
							pattern.setFormat(patternNew);
							pattern.setFormatCode(codeNew);
							pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
							newPatterns.add(pattern);
							//去重 避免Heap内存溢出
							newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
						}
					}
				}
			}
		}
		//去重
		newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		
		patterns.addAll(newPatterns);
		//去重
		patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	private void getPossiblePatternsFromFourInN(List<Pattern> patterns) {
		List<Pattern> newPatterns = new ArrayList<>();
		Pattern pattern = null; 
		int i,j,k,L = 0 ;
		int code1,code2,code3,code4 ,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
		int size = patterns.size();
		for(i=0;i<size;i++){
			   for(j=i+1;j<size;j++){
				   for(k = j+1;k<size;k++){
					   for(L=k+1;L< size;L++) {
						   int value = patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue();
							if(value >maxPrizeValue) {
								continue;
							}
						   pattern = new Pattern();
						   pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue()+patterns.get(L).getValue());
						   pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName()+"+"+patterns.get(L).getName());
						   code1 = patterns.get(i).getFormatCode();
						   code2 = patterns.get(j).getFormatCode();
						   code3 = patterns.get(k).getFormatCode();
						   code4 = patterns.get(L).getFormatCode();
						   codeNew = code1 | code2 | code3 |code4;
						   patternNew = Integer.toBinaryString(codeNew);
						   if(patternNew.length()<numPerCard){
							   patternNew = "00000"+patternNew;
						   }
						   pattern.setFormat(patternNew);
						   pattern.setFormatCode(codeNew);
						   pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
						   newPatterns.add(pattern);
						   //去重 避免Heap内存溢出
							newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
					   }
				   }
			   }
		   }
		//去重
		newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		
		patterns.addAll(newPatterns);
		   //去重
		patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	
	public void getPossiblePatternsFormThreeInN(List<Pattern> patterns){
		List<Pattern> newPatterns = new ArrayList<>();
		Pattern pattern = null;
		int i,j,k= 0 ;
		int code1,code2,code3 ,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
		int size = patterns.size();
		for(i=0;i<size;i++){
			   for(j=i+1;j<size;j++){
				   for(k = j+1;k<size;k++){
					   int value = patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue();
						if(value >maxPrizeValue) {
							continue;
						}
					   pattern = new Pattern();
					   pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue()+patterns.get(k).getValue());
					   pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName()+"+"+patterns.get(k).getName());
					   code1 = patterns.get(i).getFormatCode();
					   code2 = patterns.get(j).getFormatCode();
					   code3 = patterns.get(k).getFormatCode();
					   codeNew = code1 | code2 | code3;
					   patternNew = Integer.toBinaryString(codeNew);
					   if(patternNew.length()< numPerCard){
						   patternNew = "00000"+patternNew;
					   }
					   pattern.setFormat(patternNew);
					   pattern.setFormatCode(codeNew);
					   pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
					   newPatterns.add(pattern);
						 //去重 避免Heap内存溢出
						newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
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
	   Pattern pattern = null;
		int i,j= 0 ;
		int code1,code2,codeNew=0;
		int numPerCard = cardHeight*cardWidth;
		String patternNew = "";
	   for(i=0;i<patterns.size();i++){
		   for(j=i+1;j<patterns.size();j++){
			   int value = patterns.get(i).getValue()+patterns.get(j).getValue();
				if(value >maxPrizeValue) {
					continue;
				}
			   pattern = new Pattern();
			   pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue());
			   pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName());
			   code1 = patterns.get(i).getFormatCode();
			   code2 = patterns.get(j).getFormatCode();
			   codeNew = code1 | code2;
			   patternNew = Integer.toBinaryString(codeNew);
			   if(patternNew.length()< numPerCard){
				   patternNew = "00000"+patternNew;
			   }
			   pattern.setFormat(patternNew);
			   pattern.setFormatCode(codeNew);
			   pattern.setSunNum(PatternUtil.sunNum(codeNew, numPerCard));
			   newPatterns.add(pattern);
			 //去重 避免Heap内存溢出
				newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
		   }
	   }
	   //去重
	   newPatterns = getPossiblePatternsCompareToSelf(newPatterns);
	   
	   patterns.addAll(newPatterns);
	   //去重
	   patterns = getPossiblePatternsCompareToSelf(patterns);
	}
	public void getPossiblePatternsFromMInN(List<Pattern> patterns,int M){//M<N
		List<Pattern> newPatterns = new ArrayList<>();
		for(int i=0;i<patterns.size();i++){
			for(int j=i+1;j<patterns.size();j++){
				int value = patterns.get(i).getValue()+patterns.get(j).getValue();
				if(value >maxPrizeValue) {
					continue;
				}
				Pattern pattern = new Pattern();
				pattern.setValue(patterns.get(i).getValue()+patterns.get(j).getValue());
				pattern.setName(patterns.get(i).getName()+"+"+patterns.get(j).getName());
				int code1 = patterns.get(i).getFormatCode();
				int code2 = patterns.get(j).getFormatCode();
				int codeNew = code1 | code2;
				String patternNew = Integer.toBinaryString(codeNew);
				if(patternNew.length()< cardHeight*cardWidth){
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
//		getAmericanPatternsNew(getAllPatternsUtil,29,"American.json");
		//gameId:41
		getPachinko3PatternsNew(getAllPatternsUtil,41,"pachinko3.json");
	}

	
	private static void getPachinko3PatternsNew(GetAllPatternsUtil getAllPatternsUtil, int gameId, String fileName) {
		JsonRootBean jsonRoot = getAllPossiblePatterns41(getAllPatternsUtil,gameId,fileName);
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

	/*class newMyThread extends Thread{
		
		int a = 0;
		private GetAllPatternsUtil getAllPatternsUtil;
		private List<Pattern> patterns;
		private newMyThread(int a,GetAllPatternsUtil getAllPatternsUtil,List<Pattern> patterns) {
			this.a = a ;
			this.getAllPatternsUtil = getAllPatternsUtil;
			this.patterns = patterns;
		}
		
		@Override
		public void run() {
			if(a==2) {
				getAllPatternsUtil.getPossiblePatternsFromTwoInN(patterns);
			}else if(a==3) {
				getAllPatternsUtil.getPossiblePatternsFormThreeInN(patterns);
			}else if(a==4) {
				getAllPatternsUtil.getPossiblePatternsFromFourInN(patterns);
			}
		}
	}*/
	
	private static JsonRootBean getAllPossiblePatterns41(GetAllPatternsUtil getAllPatternsUtil, int gameId,
			String fileName) {
		//2/19
		List<Pattern> patterns = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns);
		getAllPatternsUtil.getPossiblePatternsFromTwoInN(patterns);
		//3/19
		List<Pattern> patterns2 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns2);
		getAllPatternsUtil.getPossiblePatternsFormThreeInN(patterns2);
		//4/19
		List<Pattern> patterns3 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns3);
		getAllPatternsUtil.getPossiblePatternsFromFourInN(patterns3);
		//5/19
		List<Pattern> patterns4 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns4);
		getAllPatternsUtil.getPossiblePatternsFromFiveInN(patterns4);
		//6/19
		List<Pattern> patterns5 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns5);
		getAllPatternsUtil.getPossiblePatternsFromSixInN(patterns5);
//		7/19
		List<Pattern> patterns6 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns6);
		getAllPatternsUtil.getPossiblePatternsFromSevenInN(patterns6);
//		//8/19
		List<Pattern> patterns7 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns7);
		getAllPatternsUtil.getPossiblePatternsFromEightInN(patterns7);
//		//9/19
//		List<Pattern> patterns8 = getAllPatternsUtil.getPatterns(gameId,fileName);
//		getAllPatternsUtil.initPatterns(patterns8);
//		getAllPatternsUtil.getPossiblePatternsFromNineInN(patterns8);
		//
		patterns.addAll(patterns2);
		patterns.addAll(patterns3);
		patterns.addAll(patterns4);
		patterns.addAll(patterns5);
		patterns.addAll(patterns6);
		patterns.addAll(patterns7);
//		patterns.addAll(patterns8);
		//rounstastics
		getAllPatternsUtil.jsonRootBean.setPattern(patterns);
		return getAllPatternsUtil.jsonRootBean;
	}

	public static void getAmericanPatternsNew(GetAllPatternsUtil getAllPatternsUtil,int gameId,String fileName)
	{
		JsonRootBean jsonRoot = getAllPossiblePatterns39(getAllPatternsUtil,gameId,fileName);
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
	

	public static JsonRootBean getAllPossiblePatterns39(GetAllPatternsUtil getAllPatternsUtil,int gameId,String fileName)
	{
		//2/8
		List<Pattern> patterns = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns);
		getAllPatternsUtil.getPossiblePatternsFromTwoInN(patterns);
		//3/8
		List<Pattern> patterns2 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns2);
		getAllPatternsUtil.getPossiblePatternsFormThreeInN(patterns2);
		//4/8
		List<Pattern> patterns3 = getAllPatternsUtil.getPatterns(gameId,fileName);
		getAllPatternsUtil.initPatterns(patterns3);
		getAllPatternsUtil.getPossiblePatternsFromFourInN(patterns3);
		patterns.addAll(patterns2);
		patterns.addAll(patterns3);
		//rounstastics
		getAllPatternsUtil.jsonRootBean.setPattern(patterns);
		return getAllPatternsUtil.jsonRootBean;
	}
	
	
	
	
	
	
	
	
	
	
}
