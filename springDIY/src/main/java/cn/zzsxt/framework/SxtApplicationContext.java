package cn.zzsxt.framework;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.zzsxt.service.UserService;

public class SxtApplicationContext {
	public SxtApplicationContext(){
		//1.解析配置文件
		parseXML();
		//2.根据配置文件的bean的bean节点信息动态创建对象
		createObject();
		//3.为属性注入值
		injectObject();
	}
	
	//封装applicationContext.xml中所有bean节点信息
	List<BeanDefination> beansList = new ArrayList<BeanDefination>();
	//封装动态创建的对象,以bean的id做key,以根据bean的class所创建对象做value
	Map<String,Object> beansMap = new HashMap<String,Object>();
	/**
	 * 1.解析applicationContext.xml配置文件
	 * 2.将bean节点信息封装成BeanDefination对象
	 * 3.将property节点的信息封装成PropertyDefination对象,并添加到BeanDefination的propList集合中
	 * 4.将BeanDefination对象添加到beansList中
	 */
	public void parseXML(){
		InputStream ips = this.getClass().getResourceAsStream("/applicationContext.xml");
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ips);
			Element beans = document.getRootElement();//获取根节点beans
			Iterator<Element> beanIter = beans.elementIterator();
			while (beanIter.hasNext()) {
				Element bean = beanIter.next();//bean节点
				String beanId = bean.attributeValue("id");
				String beanClass = bean.attributeValue("class");
				BeanDefination bd = new BeanDefination();//将beanId和beanClass封装成BeanDefination对象
				bd.setBeanId(beanId);
				bd.setBeanClass(beanClass);
				Iterator<Element> propertyIter = bean.elementIterator();
				while (propertyIter.hasNext()) {
					Element property = propertyIter.next();//property节点
					String propertyName = property.attributeValue("name");
					String propertyRef = property.attributeValue("ref");
					PropertyDefination pd = new PropertyDefination();//将property属性信息封装成PropertyDefination对象
					pd.setPropertyName(propertyName);
					pd.setPropertyRef(propertyRef);
					//将propertyDefination对象添加到BeanDefination对象的propList中
					bd.getPropList().add(pd);
				}
				//将BeanDefination对象添加到beansList中
				beansList.add(bd);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 1.根据配置文件的bean的class信息动态创建对象
	 * <bean id="userJdbcDao" class="cn.zzsxt.dao.impl.UserJdbcDaoImpl"/>
	 * 2.将创建的对象添加到beansMap中,以bean的id做key,以根据bean的class所创建对象做value
	 */
	public void createObject(){
		//遍历beansList获取bean节点的信息
		try {
			for (BeanDefination beanDefination : beansList) {
				String beanId = beanDefination.getBeanId();
				String beanClass = beanDefination.getBeanClass();
				//利用反射机制动态创建对象
				Object object=Class.forName(beanClass).newInstance();
				//以bean的id做key,以object做value,将动态创建的对象添加到beansMap中
				beansMap.put(beanId, object);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为属性注入指定的对象:回调属性的setter方法
	 * 前提条件:
	 * 1.property的name必须与属性名称一致
	 * 2.property的ref必须与待注入的对象的id一致
	 */
	public void injectObject(){
		try {
			//1.遍历beansList获取bean节点
			for (BeanDefination beanDefination : beansList) {
				//2.获取bean节点id和class
				String beanId = beanDefination.getBeanId();
				//3.根据id从beansMap获取以创建的对象
				Object object = beansMap.get(beanId);
				//4.获取该对象的Class信息
				Class clazz = object.getClass();
				//5.获取所有声明的方法
				Method[] methods = clazz.getDeclaredMethods();
				List<PropertyDefination> propertyList = beanDefination.getPropList();
				for (PropertyDefination propertyDefination : propertyList) {
					//6.获取bean节点的字节信息(property节点信息name和ref)
					String propertyName = propertyDefination.getPropertyName();
					String propertyRef = propertyDefination.getPropertyRef();
					//根据property的name值动态拼接setter方法名称(property的name与属性名称一致)
					String setterMethodName = makeSetter(propertyName);//获取setter方法名
					//根据propertyRef获取待注入的对象(ref与待注入bean的id一致)
					Object value = beansMap.get(propertyRef);
					for(Method method:methods){
						if(method.getName().equals(setterMethodName)){
							method.invoke(object, value);//回调setter方法，第一个参数:对象 第二个参数:方法的参数值
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据属性名称拼接其setter方法:set+属性的首字母大写+其余字母
	 * @param fieldName ---> userDao
	 * @return--->setUserDao
	 */
	public String makeSetter(String fieldName){
		return "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
	}
	
	/**
	 * 根据bean的id从beansMap获取创建的对象
	 * @param beanId
	 * @return
	 */
	public Object getBean(String beanId){
		return beansMap.get(beanId);
	}
	
	public static void main(String[] args) {
		SxtApplicationContext applicationContext = new SxtApplicationContext();
		UserService userService = (UserService)applicationContext.getBean("userService");
		userService.add();
	}
}
