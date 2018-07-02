package cn.zzsxt.framework;
/**
 * 用于封装property节点的信息
 * @author Think
 *
 */
public class PropertyDefination {
	private String propertyName;//封装property节点的name属性值
	private String propertyRef;//封装property节点的ref属性值
	
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
