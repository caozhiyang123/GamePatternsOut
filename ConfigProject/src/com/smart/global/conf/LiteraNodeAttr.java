package com.smart.global.conf;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LiteraNodeAttr {
	public static void main(String[] args) {
		SAXReader reader = null;
		try {
			reader = new SAXReader();
			Document document = reader.read(new File("src/demo.xml"));
			Element root = document.getRootElement();
			for (Iterator it = root.attributeIterator(); it.hasNext();) {
				Attribute attribute = (Attribute) it.next();
				String text = attribute.getText();				
				System.out.println(text);
			}
			System.out.println("=========================================");
			updateAttr(root);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}

	private static void updateAttr(Element root) {
		Element userEle = root.element("user");
		userEle.addAttribute("id", "2018");
		userEle.attribute("id").setText("008");
		for (Iterator it = userEle.attributeIterator(); it.hasNext();) {
			Attribute attribute = (Attribute) it.next();
			String text = attribute.getText();				
			System.out.println(text);
		}
	}
}
