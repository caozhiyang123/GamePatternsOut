package com.smart.global.conf;

import org.dom4j.Document;

import org.dom4j.DocumentException;

import org.dom4j.Element;

import org.dom4j.io.SAXReader;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.SQLException;

import java.sql.Statement;

import java.util.ArrayList;

import java.util.List;

public class CreateDB {
    String driverClass="";
    String url="";
    String user="";
    String password="";
    String tableName="";
    public static void main(String[] args) {
        try {
            new CreateDB().doit();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    public String getsql() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document document = reader.read("src/createTable.xml");
        //��ȡ���ڵ�
        Element root = document.getRootElement();
        //��ȡdb�ڵ�
        Element db_element = root.element("db");
        //��ȡdb�ڵ�����Ľڵ㼯��
        List<Element> db_list = db_element.elements();
        for(int i=0;i<db_list.size();i++){
            Element element = db_list.get(i);
            if("driverClass".equals(element.attributeValue("name"))){
                driverClass = element.getText();
            }
            else if("url".equals(element.attributeValue("name"))){
                url = element.getText();
            }
            else if("user".equals(element.attributeValue("name"))){
                user = element.getText();
            }
            else if("password".equals(element.attributeValue("name"))){
                password = element.getText();
            }
        }
        ArrayList<String> sqls = new ArrayList<>();
        //��ȡtableNmae�ڵ�
        Element tb_element = root.element("table");
        //��ȡ������
        tableName =tb_element.attributeValue("tableName");
        //��ȡԪ�ؼ���
        List<Element> tb_list = tb_element.elements();
        for(int i=0;i<tb_list.size();i++){
            Element element = tb_list.get(i);
            String column = element.attributeValue("column");
            if("integer".equals(element.attributeValue("type"))){
                sqls.add(column+" "+"int");//uid int
            }else if("varchar".equals(element.attributeValue("type"))){
                String lenght  = element.attributeValue("length");
                sqls.add(column+" "+" varchar("+lenght+") ");//username varchar(255)  password varchar(255) 
            }
        }
        String sql="create table "+tableName+" (";
        for(int i=0;i<sqls.size();i++){
            //��ֹ�������Ĵ���
            if(i!=sqls.size()-1){
                sql = sql+sqls.get(i)+" , ";
            }else {
                sql = sql+sqls.get(i);
            }
        }
        sql = sql+" );";   //"create table  tbuser (id int,username varchar(255) , password varchar(255) );
        return sql;
    }

    public void doit() throws ClassNotFoundException, SQLException, DocumentException {
        String sql=getsql();
        System.out.println(driverClass+"  "+url+"  "+user+" "+password);
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url,user,password);
        Statement statement = connection.createStatement();
        System.out.println(sql);
//        statement.execute(sql);
    }

}