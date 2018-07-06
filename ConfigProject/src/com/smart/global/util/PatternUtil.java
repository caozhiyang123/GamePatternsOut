package com.smart.global.util;


import java.util.BitSet;

public class PatternUtil
{
    private int length;

    public PatternUtil(int length)
    {
        if (length > Integer.SIZE)
        {
        }
        this.length = length;
    }

    public int differentBitCount(int src, int dst)
    {
        int result = src & dst ^ dst;
        int count = 0;
        for (int i = 0; i < length; i++)
        {
            count += (result >> i & 1);
        }

        return count;
    }

    //get miss one bit index, if miss 0 bit or more 1 bit return -1.
    //from left to right, 0, 1, 2,....
    public int missOneBit(int src, int dst)
    {
        int diffBit = src & dst ^ dst;
        int result = -1;
        int count = 0;
        for (int i = 0; i < length; i++)
        {
            count += (diffBit >> (length - 1 - i) & 1);
            if (count == 1 && result < 0)
            {
                result = i;
            }
            else if (count > 1)
            {
                return -1;
            }
        }

        return result;
    }
    
    public static int sunNum(int src,int numberPerCard){
    	int count = 0 ;
    	for(int i = 0;i<numberPerCard;i++){
    		count += (src >> i & 1);
    	}
    	return count;
    }

    public static int isMatch(int src, int dst)
    {
    	if((src & dst) != dst && (src & dst) != src){
    		return 0;
    	}else if((src & dst) != dst && (src & dst) == src){
    		return 1;
    	}else if((src & dst) == dst && (src & dst) == src){
    		return 2;
    	}
    	return -1;
    }
    
    public static boolean isMatch2(int src,int dst){
    	if(src != dst){
    		return true;
    	}
		return false;
    }
    public static boolean isMatch3(int src,int dst){
    	return (src & dst) == dst;
    }

    //parse string flags to binary number
    //for example "1111100000" -> 0b1111100000
    public static int parsePatternCode(String src)
    {
        if (src.length() > Integer.SIZE)
        {
        	System.out.println("src length over than Integer SIZE:"+src.length());
        }
        return Integer.parseInt(src, 2);
    }

    public int[] parseFormatCodes(String flags)
    {
        int count = flags.length() / length;
        int[] formatCodes = new int[count];
        for (int i = 0; i < formatCodes.length; i++)
        {
            formatCodes[i] = parsePatternCode(flags.substring(i * length, (i + 1) * length));
        }

        return formatCodes;
    }

    public int[] toBinaryCodes(BitSet bitSet, int count)
    {
        return toBinaryCodes(bitSet, count, length);
    }

    public static int[] toBinaryCodes(BitSet bitSet, int count, int numPerCard)
    {
        int[] binaryCodes = new int[count];
        for (int i = 0; i < count; i++)
        {
            int value = 0;
            int max = (i + 1) * numPerCard;
            for (int j = i * numPerCard; j < max; ++j)
            {
                value |= (bitSet.get(j) ? 1 : 0) << (j % numPerCard);
            }
            binaryCodes[i] = value;
        }

        return binaryCodes;
    }

    public int toBinaryCode(BitSet bitSet)
    {
        int value = 0;
        for (int i = 0; i < length; i++)
        {
            value |= (bitSet.get(i) ? 1 : 0) << i;
        }
        return value;
    }
}
