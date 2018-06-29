package com.smart.global.conf;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

public class Testdom4jXpath {
	public void testDom4jXpath(String url){
		SAXReader reader = null;
		try {
			reader = new SAXReader();
			Document document = reader.read(new File(url));
			List<Node> nodes = document.selectNodes("users/user/name"); //获取指定节点的所有集合
			for (int i = 0,lenth = nodes.size(); i < lenth ; i++) {
				String nodeName = nodes.get(i).getName();//获取指定节点的名称
				String name = nodes.get(i).getText();//获取指定节点的内容
				String id = nodes.get(i).valueOf("@id");//获取指定节点的指定属性
				System.out.println("nodename:"+nodeName+"===id:"+id+"===name:"+name);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Testdom4jXpath xpath = new Testdom4jXpath();
		xpath.testDom4jXpath("src/demo.xml");
	}
	
}
