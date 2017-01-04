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
 * 操作员
 * 描述：操作员
 * 定义来自 ： 
 *  业务域：tuser
 *  模型：AC_OPERATOR 操作员
 *  定义文件：D:\GitSpace\tistools\tools-core\model\model-user.xml
 * </pre>
 * @author mvn bronsp:gendao
 *
 */
public class AcOperator implements Serializable {

 	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private Integer operatorid;//操作员编号
	 private String userid;//登录用户名
	 private String password;//密码
	 private Date invaldate;//密码失效日期
	 private String operatorname;//操作员名称
	 private String authmode;//认证模式
	 private String status;//操作员状态
	 private Date unlocktime;//解锁时间
	 private String menutype;//菜单风格
	 private Date lastlogin;//最近登录时间
	 private Integer errcount;//最新错误登录次数
	 private Date startdate;//有效开始日期
	 private Date enddate;//有效截止日期
	 private String validtime;//有效时间范围
	 private String maccode;//MAC码
	 private String ipaddress;//IP地址
	 private String email;//邮箱
	
	 public void setOperatorid(Integer operatorid) {
          this.operatorid = operatorid ;
    }
	 public Integer getOperatorid(){
        return operatorid;
    }
	public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
	 public String getUserid(){
        return userid;
    }
	public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
	 public String getPassword(){
        return password;
    }
	 public void setInvaldate(Date invaldate) {
          this.invaldate = invaldate ;
    }
	 public Date getInvaldate(){
        return invaldate;
    }
	public void setOperatorname(String operatorname) {
        this.operatorname = operatorname == null ? null : operatorname.trim();
    }
	 public String getOperatorname(){
        return operatorname;
    }
	public void setAuthmode(String authmode) {
        this.authmode = authmode == null ? null : authmode.trim();
    }
	 public String getAuthmode(){
        return authmode;
    }
	public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }
	 public String getStatus(){
        return status;
    }
	 public void setUnlocktime(Date unlocktime) {
          this.unlocktime = unlocktime ;
    }
	 public Date getUnlocktime(){
        return unlocktime;
    }
	public void setMenutype(String menutype) {
        this.menutype = menutype == null ? null : menutype.trim();
    }
	 public String getMenutype(){
        return menutype;
    }
	 public void setLastlogin(Date lastlogin) {
          this.lastlogin = lastlogin ;
    }
	 public Date getLastlogin(){
        return lastlogin;
    }
	 public void setErrcount(Integer errcount) {
          this.errcount = errcount ;
    }
	 public Integer getErrcount(){
        return errcount;
    }
	 public void setStartdate(Date startdate) {
          this.startdate = startdate ;
    }
	 public Date getStartdate(){
        return startdate;
    }
	 public void setEnddate(Date enddate) {
          this.enddate = enddate ;
    }
	 public Date getEnddate(){
        return enddate;
    }
	public void setValidtime(String validtime) {
        this.validtime = validtime == null ? null : validtime.trim();
    }
	 public String getValidtime(){
        return validtime;
    }
	public void setMaccode(String maccode) {
        this.maccode = maccode == null ? null : maccode.trim();
    }
	 public String getMaccode(){
        return maccode;
    }
	public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress == null ? null : ipaddress.trim();
    }
	 public String getIpaddress(){
        return ipaddress;
    }
	public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
	 public String getEmail(){
        return email;
    }
}