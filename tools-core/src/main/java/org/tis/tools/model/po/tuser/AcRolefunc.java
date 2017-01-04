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
 * 权限集(角色)功能对应关系
 * 描述：权限集(角色)功能对应关系
 * 定义来自 ： 
 *  业务域：tuser
 *  模型：AC_ROLEFUNC 权限集(角色)功能对应关系
 *  定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class AcRolefunc implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String roleid;//角色编号
	 private String funccode;//功能编号
	 private Integer appid;//应用编号
	 private Integer funcgroupid;//功能组编号
	
	public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }
	 public String getRoleid(){
        return roleid;
    }
	public void setFunccode(String funccode) {
        this.funccode = funccode == null ? null : funccode.trim();
    }
	 public String getFunccode(){
        return funccode;
    }
	 public void setAppid(Integer appid) {
          this.appid = appid ;
    }
	 public Integer getAppid(){
        return appid;
    }
	 public void setFuncgroupid(Integer funcgroupid) {
          this.funcgroupid = funcgroupid ;
    }
	 public Integer getFuncgroupid(){
        return funcgroupid;
    }
}