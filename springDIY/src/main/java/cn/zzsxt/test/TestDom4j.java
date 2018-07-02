package cn.zzsxt.test;

import java.io.InputStream;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestDom4j {
	public void parseXML(){
		InputStream ips = this.getClass().getResourceAsStream("/applicationContext.xml");//������·��(src|src/main/resources)����Դ�ļ�
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ips);
			//��ȡ���ڵ�:beans�ڵ�
			Element beans = document.getRootElement();
			//��ȡ���ڵ���ӽڵ�:bean�ڵ�
			Iterator<Element> beanIter = beans.elementIterator();
			while(beanIter.hasNext()){
				Element bean = beanIter.next();//bean�ڵ�
				//��ȡbean������ֵ(id��class)
				String beanId = bean.attributeValue("id");
				String beanClass = bean.attributeValue("class");
				System.out.println("id="+beanId+"---class="+beanClass);
				//��ȡproperty�ڵ����Ϣ(bean�ڵ���ӽڵ�)
				Iterator<Element> propertyIter = bean.elementIterator();
				while(propertyIter.hasNext()){
					Element property = propertyIter.next();//property�ڵ�
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
