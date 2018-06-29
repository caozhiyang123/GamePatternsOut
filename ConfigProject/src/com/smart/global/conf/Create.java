package com.smart.global.conf;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Create {
	public static void main(String[] args) {
		Document document = DocumentHelper.createDocument();
		Element rootElement = document.addElement("users");
		Element element = rootElement.addElement("user");
		Element nameElement = element.addElement("name");
		nameElement.setText("уехЩ");
		nameElement.setAttributeValue("id", "33");
		
	}
}
