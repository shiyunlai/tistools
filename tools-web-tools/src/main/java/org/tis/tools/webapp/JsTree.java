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
	private String data;
	public static final String EMP_PLUS_ICON = "fa fa-user-plus icon-state-success";// 新增人员图标
	public static final String EMP_ICON = "fa fa-user icon-state-danger";// 人员图标
	public static final String ROOT_TREE_ICON = "fa fa-sitemap icon-state-warning";// 树根节点图标
	public static final String ORG_ICON = "fa fa-building-o icon-state-info";// 机构图标
	public static final String POSITION_ICON = "fa fa-users icon-state-success";// 岗位图标
	public static final String TYPE_ROOT = "root";
	public static final String TYPE_ORG = "org";
	public static final String TYPE_EMP = "emp";
	public static final String TYPE_POSI = "posi";
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	
	@Override
	public String toString() {
		return "JsTree [id=" + id + ", text=" + text + "]";
	}

}
