package cn.zzsxt.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ڷ�װbean�ڵ����Ϣ(ʵ����)
 * @author Think
 *
 */
public class BeanDefination {
	private String beanId;//��װbean�ڵ��id����ֵ
	private String beanClass;//��װbean�ڵ��class����ֵ
	//��װbean�ڵ�������property�ӽڵ���Ϣ
	private List<PropertyDefination> propList = new ArrayList<PropertyDefination>();
	
	public String getBeanId() {
		return beanId;
	}
	public void setBeanId(String beanId) {
		this.beanId = beanId;
	}
	public String getBeanClass() {
		return beanClass;
	}
	public void setBeanClass(String beanClass) {
		this.beanClass = beanClass;
	}
	public List<PropertyDefination> getPropList() {
		return propList;
	}
	public void setPropList(List<PropertyDefination> propList) {
		this.propList = propList;
	}
	@Override
	public String toString() {
		return "BeanDefination [beanId=" + beanId + ", beanClass=" + beanClass + ", propList=" + propList + "]";
	}
	
}
