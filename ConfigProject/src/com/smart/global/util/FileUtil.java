package com.smart.global.util;

public class FileUtil {

 
    public static String getExtension(String filename) {
        if (filename == null) {
            return null;
        }
        int extensionPos = filename.lastIndexOf('.');  
 
        if (extensionPos == -1) {
            return "";
        } else {
            return filename.substring(extensionPos+1);
        }
    }
    
    public static void main(String[] args)
	{
		System.out.println(Double.parseDouble("6.5"));
	}
}