package com.smart.global.conf;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class DomDemo {

    public Document parse(String url) throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(url));
        return document;
    }
    public static void main(String[] args) {
		DomDemo domDemo = new DomDemo();
		try {
			Document document = domDemo.parse("src/demo.xml");
			Element rootElement = document.getRootElement();//��ȡ��Ԫ��
			Element element = rootElement.element("user");//��ȡָ�����Ƶ���Ԫ��
			String no = element.attributeValue("no");//��ȡָ��ԭ�ص�����
			System.out.println(no);
			String name = element.element("name").getText();
			System.out.println(name);
			String age = element.element("age").getText();
			System.out.println(age);
			System.out.println("==================================");
			List<Element> elements = rootElement.elements();//��ȡ������Ԫ�ؼ���
			for (int i = 0,length = elements.size(); i <length ; i++) {
				String no2 = elements.get(i).attributeValue("no");
				String name2 = elements.get(i).element("name").getText();
				String age2 = elements.get(i).element("age").getText();
				System.out.println(no2+"="+name+"="+age);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}