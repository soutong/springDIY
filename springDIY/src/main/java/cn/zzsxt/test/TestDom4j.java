package cn.zzsxt.test;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestDom4j {
	public void parseXML(){
		InputStream ips = this.getClass().getResourceAsStream("/applicationContext.xml");//加载类路径(src|src/main/resources)的资源文件
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ips);
			//获取根节点:beans节点
			Element beans = document.getRootElement();
			//获取根节点的子节点:bean节点
			Iterator<Element> beanIter = beans.elementIterator();
			while(beanIter.hasNext()){
				Element bean = beanIter.next();//bean节点
				//获取bean的属性值(id和class)
				String beanId = bean.attributeValue("id");
				String beanClass = bean.attributeValue("class");
				System.out.println("id="+beanId+"---class="+beanClass);
				//获取property节点的信息(bean节点的子节点)
				Iterator<Element> propertyIter = bean.elementIterator();
				while(propertyIter.hasNext()){
					Element property = propertyIter.next();//property节点
					String propertyName = property.attributeValue("name");
					String propertyRef = property.attributeValue("ref");
					System.out.println("   name="+propertyName+"***ref="+propertyRef);
				}
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TestDom4j testDom4j = new TestDom4j();
		testDom4j.parseXML();
	}
}
