/**
  * Copyright 2018 bejson.com 
  */
package com.besjon.pojo;

/**
 * Auto-generated: 2018-06-28 17:17:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Pattern implements Comparable<Pattern>{

    private String name;
    private String alias;
    private String format;
    private int value;
    private int r;
    private double weight;
    private boolean is_eb;
    private int formatCode;
    private int sunNum;
    
    public int getSunNum()
	{
		return sunNum;
	}
	public void setSunNum(int sunNum)
	{
		this.sunNum = sunNum;
	}
	public int getFormatCode()
	{
		return formatCode;
	}
	public void setFormatCode(int formatCode)
	{
		this.formatCode = formatCode;
	}
	public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setAlias(String alias) {
         this.alias = alias;
     }
     public String getAlias() {
         return alias;
     }

    public void setFormat(String format) {
         this.format = format;
     }
     public String getFormat() {
         return format;
     }

    public void setValue(int value) {
         this.value = value;
     }
     public int getValue() {
         return value;
     }

    public void setR(int r) {
         this.r = r;
     }
     public int getR() {
         return r;
     }

    public void setWeight(double weight) {
         this.weight = weight;
     }
     public double getWeight() {
         return weight;
     }

    public void setIs_eb(boolean is_eb) {
         this.is_eb = is_eb;
     }
     public boolean getIs_eb() {
         return is_eb;
     }
     
     @Override
 	public String toString() {
 		return "Pattern [name=" + name + ", alias=" + alias + ", format=" + format + ", value=" + value + ", r=" + r
 				+ ", weight=" + weight + ", is_eb=" + is_eb + ", formatCode=" + formatCode + ", sunNum=" + sunNum + "]";
 	}
	
	@Override
	public int compareTo(Pattern o)
	{
		if(this.value < o.value){
			return 1;
		}else if(this.value > o.value){
			return -1;
		}
		return 0;
	}
	
	
     

}