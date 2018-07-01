/**
  * Copyright 2018 bejson.com 
  */
package com.besjon.pojo;
import java.util.List;

/**
 * Auto-generated: 2018-06-28 17:17:38
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class JsonRootBean {

	private int machineId;
	private String machineName;
	private int cardWidth;
    private int cardHeight;
    private int availableBalls;
    private int maxPrizeValue;
    private List<Pattern> pattern;
    
    
    public int getMaxPrizeValue() {
		return maxPrizeValue;
	}
	public void setMaxPrizeValue(int maxPrizeValue) {
		this.maxPrizeValue = maxPrizeValue;
	}
	
	public int getAvailableBalls() {
		return availableBalls;
	}
	public void setAvailableBalls(int availableBalls) {
		this.availableBalls = availableBalls;
	}
	public int getCardWitdh()
	{
		return cardWidth;
	}
	public void setCardWitdh(int cardWitdh)
	{
		this.cardWidth = cardWitdh;
	}
	public int getCardHeight()
	{
		return cardHeight;
	}
	public void setCardHeight(int cardHeight)
	{
		this.cardHeight = cardHeight;
	}
	public int getMachineId()
	{
		return machineId;
	}
	public void setMachineId(int machineId)
	{
		this.machineId = machineId;
	}
	public String getMachineName()
	{
		return machineName;
	}
	public void setMachineName(String machineName)
	{
		this.machineName = machineName;
	}
	public void setPattern(List<Pattern> pattern) {
         this.pattern = pattern;
     }
     public List<Pattern> getPattern() {
         return pattern;
     }
	@Override
	public String toString()
	{
		return "JsonRootBean [machineId=" + machineId + ", machineName=" + machineName + ", cardWitdh=" + cardWidth
				+ ", cardHeight=" + cardHeight + ", pattern=" + pattern + "]";
	}
	

}