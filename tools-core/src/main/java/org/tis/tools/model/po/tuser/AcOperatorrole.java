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
 * 操作员与权限集（角色）对应关系
 * 描述：操作员与权限集（角色）对应关系
 * 定义来自 ： 
 *  业务域：tuser
 *  模型：AC_OPERATORROLE 操作员与权限集（角色）对应关系
 *  定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class AcOperatorrole implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Integer operatorid;//操作员编号
	 private String roleid;//角色编号
	 private String auth;//是否可分级授权
	
	 public void setOperatorid(Integer operatorid) {
          this.operatorid = operatorid ;
    }
	 public Integer getOperatorid(){
        return operatorid;
    }
	public void setRoleid(String roleid) {
        this.roleid = roleid == null ? null : roleid.trim();
    }
	 public String getRoleid(){
        return roleid;
    }
	public void setAuth(String auth) {
        this.auth = auth == null ? null : auth.trim();
    }
	 public String getAuth(){
        return auth;
    }
}