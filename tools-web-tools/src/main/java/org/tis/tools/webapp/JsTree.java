package org.tis.tools.webapp;

import java.io.Serializable;
import java.util.List;

public class JsTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9099517515967994365L;
	private String id;
	private String text;
	private String icon;
	private Boolean children;
	
	

	public Boolean getChildren() {
		return children;
	}
	public void setChildren(Boolean children) {
		this.children = children;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
	@Override
	public String toString() {
		return "JsTree [id=" + id + ", text=" + text + "]";
	}
	
}
