package cn.zzsxt.framework;
/**
 * ���ڷ�װproperty�ڵ����Ϣ
 * @author Think
 *
 */
public class PropertyDefination {
	private String propertyName;//��װproperty�ڵ��name����ֵ
	private String propertyRef;//��װproperty�ڵ��ref����ֵ
	
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyRef() {
		return propertyRef;
	}
	public void setPropertyRef(String propertyRef) {
		this.propertyRef = propertyRef;
	}
	
	@Override
	public String toString() {
		return "PropertyDefination [propertyName=" + propertyName + ", propertyRef=" + propertyRef + "]";
	}
	
	
}
