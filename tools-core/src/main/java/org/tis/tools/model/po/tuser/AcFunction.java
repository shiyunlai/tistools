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
 * 功能
 * 描述：功能
 * 定义来自 ： 
 *  业务域：tuser
 *  模型：AC_FUNCTION 功能
 *  定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class AcFunction implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String funccode;//功能编号
	 private Integer funcgroupid;//功能组编号
	 private String funcname;//功能名称
	 private String funcdesc;//功能描述
	 private String funcaction;//功能调用入口
	 private String parainfo;//输入参数
	 private String ischeck;//是否验证权限
	 private String functype;//功能类型
	 private String ismenu;//可否定义为菜单
	
	public void setFunccode(String funccode) {
        this.funccode = funccode == null ? null : funccode.trim();
    }
	 public String getFunccode(){
        return funccode;
    }
	 public void setFuncgroupid(Integer funcgroupid) {
          this.funcgroupid = funcgroupid ;
    }
	 public Integer getFuncgroupid(){
        return funcgroupid;
    }
	public void setFuncname(String funcname) {
        this.funcname = funcname == null ? null : funcname.trim();
    }
	 public String getFuncname(){
        return funcname;
    }
	public void setFuncdesc(String funcdesc) {
        this.funcdesc = funcdesc == null ? null : funcdesc.trim();
    }
	 public String getFuncdesc(){
        return funcdesc;
    }
	public void setFuncaction(String funcaction) {
        this.funcaction = funcaction == null ? null : funcaction.trim();
    }
	 public String getFuncaction(){
        return funcaction;
    }
	public void setParainfo(String parainfo) {
        this.parainfo = parainfo == null ? null : parainfo.trim();
    }
	 public String getParainfo(){
        return parainfo;
    }
	public void setIscheck(String ischeck) {
        this.ischeck = ischeck == null ? null : ischeck.trim();
    }
	 public String getIscheck(){
        return ischeck;
    }
	public void setFunctype(String functype) {
        this.functype = functype == null ? null : functype.trim();
    }
	 public String getFunctype(){
        return functype;
    }
	public void setIsmenu(String ismenu) {
        this.ismenu = ismenu == null ? null : ismenu.trim();
    }
	 public String getIsmenu(){
        return ismenu;
    }
}