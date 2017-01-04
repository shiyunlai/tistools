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
 * 应用系统
 * 描述：应用系统
 * 定义来自 ： 
 *  业务域：tuser
 *  模型：AC_APPLICATION 应用系统
 *  定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class AcApplication implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String appcode;//应用代码
	 private String appid;//应用编号
	 private String appname;//应用名称
	 private String apptype;//应用类型
	 private String isopen;//是否开通
	 private Date opendate;//开通日期
	 private String url;//访问地址
	 private String appdesc;//应用描述
	 private String maintenance;//管理维护人员
	 private String manarole;//应用管理角色
	 private String demo;//备注
	 private String iniwp;//开是否接入集中工作平台
	 private String intaskcenter;//是否接入集中任务中心
	 private String ipaddr;//IP
	 private String ipport;//端口
	
	public void setAppcode(String appcode) {
        this.appcode = appcode == null ? null : appcode.trim();
    }
	 public String getAppcode(){
        return appcode;
    }
	public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }
	 public String getAppid(){
        return appid;
    }
	public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }
	 public String getAppname(){
        return appname;
    }
	public void setApptype(String apptype) {
        this.apptype = apptype == null ? null : apptype.trim();
    }
	 public String getApptype(){
        return apptype;
    }
	public void setIsopen(String isopen) {
        this.isopen = isopen == null ? null : isopen.trim();
    }
	 public String getIsopen(){
        return isopen;
    }
	 public void setOpendate(Date opendate) {
          this.opendate = opendate ;
    }
	 public Date getOpendate(){
        return opendate;
    }
	public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }
	 public String getUrl(){
        return url;
    }
	public void setAppdesc(String appdesc) {
        this.appdesc = appdesc == null ? null : appdesc.trim();
    }
	 public String getAppdesc(){
        return appdesc;
    }
	public void setMaintenance(String maintenance) {
        this.maintenance = maintenance == null ? null : maintenance.trim();
    }
	 public String getMaintenance(){
        return maintenance;
    }
	public void setManarole(String manarole) {
        this.manarole = manarole == null ? null : manarole.trim();
    }
	 public String getManarole(){
        return manarole;
    }
	public void setDemo(String demo) {
        this.demo = demo == null ? null : demo.trim();
    }
	 public String getDemo(){
        return demo;
    }
	public void setIniwp(String iniwp) {
        this.iniwp = iniwp == null ? null : iniwp.trim();
    }
	 public String getIniwp(){
        return iniwp;
    }
	public void setIntaskcenter(String intaskcenter) {
        this.intaskcenter = intaskcenter == null ? null : intaskcenter.trim();
    }
	 public String getIntaskcenter(){
        return intaskcenter;
    }
	public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr == null ? null : ipaddr.trim();
    }
	 public String getIpaddr(){
        return ipaddr;
    }
	public void setIpport(String ipport) {
        this.ipport = ipport == null ? null : ipport.trim();
    }
	 public String getIpport(){
        return ipport;
    }
}