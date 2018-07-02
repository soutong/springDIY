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
		//1.���������ļ�
		parseXML();
		//2.���������ļ���bean��bean�ڵ���Ϣ��̬��������
		createObject();
		//3.Ϊ����ע��ֵ
		injectObject();
	}
	
	//��װapplicationContext.xml������bean�ڵ���Ϣ
	List<BeanDefination> beansList = new ArrayList<BeanDefination>();
	//��װ��̬�����Ķ���,��bean��id��key,�Ը���bean��class������������value
	Map<String,Object> beansMap = new HashMap<String,Object>();
	/**
	 * 1.����applicationContext.xml�����ļ�
	 * 2.��bean�ڵ���Ϣ��װ��BeanDefination����
	 * 3.��property�ڵ����Ϣ��װ��PropertyDefination����,����ӵ�BeanDefination��propList������
	 * 4.��BeanDefination������ӵ�beansList��
	 */
	public void parseXML(){
		InputStream ips = this.getClass().getResourceAsStream("/applicationContext.xml");
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(ips);
			Element beans = document.getRootElement();//��ȡ���ڵ�beans
			Iterator<Element> beanIter = beans.elementIterator();
			while (beanIter.hasNext()) {
				Element bean = beanIter.next();//bean�ڵ�
				String beanId = bean.attributeValue("id");
				String beanClass = bean.attributeValue("class");
				BeanDefination bd = new BeanDefination();//��beanId��beanClass��װ��BeanDefination����
				bd.setBeanId(beanId);
				bd.setBeanClass(beanClass);
				Iterator<Element> propertyIter = bean.elementIterator();
				while (propertyIter.hasNext()) {
					Element property = propertyIter.next();//property�ڵ�
					String propertyName = property.attributeValue("name");
					String propertyRef = property.attributeValue("ref");
					PropertyDefination pd = new PropertyDefination();//��property������Ϣ��װ��PropertyDefination����
					pd.setPropertyName(propertyName);
					pd.setPropertyRef(propertyRef);
					//��propertyDefination������ӵ�BeanDefination�����propList��
					bd.getPropList().add(pd);
				}
				//��BeanDefination������ӵ�beansList��
				beansList.add(bd);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	/** 
	 * 1.���������ļ���bean��class��Ϣ��̬��������
	 * <bean id="userJdbcDao" class="cn.zzsxt.dao.impl.UserJdbcDaoImpl"/>
	 * 2.�������Ķ�����ӵ�beansMap��,��bean��id��key,�Ը���bean��class������������value
	 */
	public void createObject(){
		//����beansList��ȡbean�ڵ����Ϣ
		try {
			for (BeanDefination beanDefination : beansList) {
				String beanId = beanDefination.getBeanId();
				String beanClass = beanDefination.getBeanClass();
				//���÷�����ƶ�̬��������
				Object object=Class.forName(beanClass).newInstance();
				//��bean��id��key,��object��value,����̬�����Ķ�����ӵ�beansMap��
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
	 * Ϊ����ע��ָ���Ķ���:�ص����Ե�setter����
	 * ǰ������:
	 * 1.property��name��������������һ��
	 * 2.property��ref�������ע��Ķ����idһ��
	 */
	public void injectObject(){
		try {
			//1.����beansList��ȡbean�ڵ�
			for (BeanDefination beanDefination : beansList) {
				//2.��ȡbean�ڵ�id��class
				String beanId = beanDefination.getBeanId();
				//3.����id��beansMap��ȡ�Դ����Ķ���
				Object object = beansMap.get(beanId);
				//4.��ȡ�ö����Class��Ϣ
				Class clazz = object.getClass();
				//5.��ȡ���������ķ���
				Method[] methods = clazz.getDeclaredMethods();
				List<PropertyDefination> propertyList = beanDefination.getPropList();
				for (PropertyDefination propertyDefination : propertyList) {
					//6.��ȡbean�ڵ���ֽ���Ϣ(property�ڵ���Ϣname��ref)
					String propertyName = propertyDefination.getPropertyName();
					String propertyRef = propertyDefination.getPropertyRef();
					//����property��nameֵ��̬ƴ��setter��������(property��name����������һ��)
					String setterMethodName = makeSetter(propertyName);//��ȡsetter������
					//����propertyRef��ȡ��ע��Ķ���(ref���ע��bean��idһ��)
					Object value = beansMap.get(propertyRef);
					for(Method method:methods){
						if(method.getName().equals(setterMethodName)){
							method.invoke(object, value);//�ص�setter��������һ������:���� �ڶ�������:�����Ĳ���ֵ
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ������������ƴ����setter����:set+���Ե�����ĸ��д+������ĸ
	 * @param fieldName ---> userDao
	 * @return--->setUserDao
	 */
	public String makeSetter(String fieldName){
		return "set"+fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
	}
	
	/**
	 * ����bean��id��beansMap��ȡ�����Ķ���
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
