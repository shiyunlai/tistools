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
 * 功能组
 * 描述：功能组
 * 定义来自 ： 
 *  业务域：tuser
 *  模型：AC_FUNCGROUP 功能组
 *  定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class AcFuncgroup implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Integer funcgroupid;//功能组编号
	 private Integer appid;//应用编号
	 private String funcgroupname;//功能组名称
	 private Integer parentgroup;//上级功能组
	 private Integer grouplevel;//节点层次
	 private String funcgroupseq;//功能组路径序列
	 private String isleaf;//是否叶子节点
	 private Integer subcount;//子节点数
	
	 public void setFuncgroupid(Integer funcgroupid) {
          this.funcgroupid = funcgroupid ;
    }
	 public Integer getFuncgroupid(){
        return funcgroupid;
    }
	 public void setAppid(Integer appid) {
          this.appid = appid ;
    }
	 public Integer getAppid(){
        return appid;
    }
	public void setFuncgroupname(String funcgroupname) {
        this.funcgroupname = funcgroupname == null ? null : funcgroupname.trim();
    }
	 public String getFuncgroupname(){
        return funcgroupname;
    }
	 public void setParentgroup(Integer parentgroup) {
          this.parentgroup = parentgroup ;
    }
	 public Integer getParentgroup(){
        return parentgroup;
    }
	 public void setGrouplevel(Integer grouplevel) {
          this.grouplevel = grouplevel ;
    }
	 public Integer getGrouplevel(){
        return grouplevel;
    }
	public void setFuncgroupseq(String funcgroupseq) {
        this.funcgroupseq = funcgroupseq == null ? null : funcgroupseq.trim();
    }
	 public String getFuncgroupseq(){
        return funcgroupseq;
    }
	public void setIsleaf(String isleaf) {
        this.isleaf = isleaf == null ? null : isleaf.trim();
    }
	 public String getIsleaf(){
        return isleaf;
    }
	 public void setSubcount(Integer subcount) {
          this.subcount = subcount ;
    }
	 public Integer getSubcount(){
        return subcount;
    }
}