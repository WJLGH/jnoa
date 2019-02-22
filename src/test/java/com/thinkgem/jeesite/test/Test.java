package com.thinkgem.jeesite.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		//测试文件
		Scanner in =new Scanner(System.in);
//		String dateStr = null;
//		while((dateStr =in.nextLine())!=null) {
//			System.out.println(dateStr+":"+dateStr.matches("^\\d{4}(|-\\d{2}(|-\\d{2}))$"));
//		}3
		//搜索CLASSPATH路径，以classpath路径下的bean.xml、service.xml文件创建applicationContext
		       ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"bean.xml","service.xml"});

		       //以指定路径下的bean.xml、service.xml文件创建applicationContext
		       ApplicationContext ctx1 = new FileSystemXmlApplicationContext(new String[]{"bean.xml","service.xml"});

	}
}
