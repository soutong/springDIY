package cn.zzsxt.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于封装bean节点的信息(实体类)
 * @author Think
 *
 */
public class BeanDefination {
	private String beanId;//封装bean节点的id属性值
	private String beanClass;//封装bean节点的class属性值
	//封装bean节点下所有property子节点信息
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
