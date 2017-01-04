/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
 package org.tis.tools.model.po.tuser;

import java.util.Date;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * 
 * <pre>
 * 自动生成，不修改。
 * 角色
 * 描述：角色
 * 定义来自 ： 
 *  业务域：tuser
 *  模型：AC_ROLE 角色
 *  定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class AcRole implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String roleid;//角色代号
	 private String rolename;//角色名称
	 private String roletype;//角色类别
	 private String roledesc;//角色描述
	 private Integer appid;//应用编号
	
	public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }
	 public String getRoleid(){
        return roleid;
    }
	public void setRolename(String rolename) {
        this.rolename = rolename == null ? null : rolename.trim();
    }
	 public String getRolename(){
        return rolename;
    }
	public void setRoletype(String roletype) {
        this.roletype = roletype == null ? null : roletype.trim();
    }
	 public String getRoletype(){
        return roletype;
    }
	public void setRoledesc(String roledesc) {
        this.roledesc = roledesc == null ? null : roledesc.trim();
    }
	 public String getRoledesc(){
        return roledesc;
    }
	 public void setAppid(Integer appid) {
          this.appid = appid ;
    }
	 public Integer getAppid(){
        return appid;
    }
}