package org.tis.tools.webapp;

import java.io.Serializable;

public class TreeData implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2653702979123211049L;
	private String viewType;
	private String currentId;
	private String parentId;
	private String parentName;
	
	public String getViewType() {
		return viewType;
	}
	public TreeData setViewType(String viewType) {
		this.viewType = viewType;
		return this;
	}
	public String getCurrentId() {
		return currentId;
	}
	public TreeData setCurrentId(String currentId) {
		this.currentId = currentId;
		return this;
	}
	public String getParentId() {
		return parentId;
	}
	public TreeData setParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}
	public String getParentName() {
		return parentName;
	}
	public TreeData setParentName(String parentName) {
		this.parentName = parentName;
		return this;
	}
	

}

