package org.tis.tools.model.vo.devmgr;

import org.tis.tools.base.BaseModel;

public class FeatureReg extends BaseModel{
	
	private static final long serialVersionUID = 8015922733058988081L;
	private String branchId;	//分支号
	private String workId;	//工作编号
	private String workDesc;	//工作描述
	private String artfId;	//任务编号
	private String branchType;	//分支类型
	private String branchMgr;	//分支管理员
	private String openDate;	//分支申请日期
	private String status;	//分支处理状态
	private String listFileName;	//清单文件名
	private String developer;	//开发者
	private String releaser;	//投产人
	private String releaseDate;	//投产日期
	public String getBranchId() {
		return branchId;
	}
	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getWorkDesc() {
		return workDesc;
	}
	public void setWorkDesc(String workDesc) {
		this.workDesc = workDesc;
	}
	public String getArtfId() {
		return artfId;
	}
	public void setArtfId(String artfId) {
		this.artfId = artfId;
	}
	public String getBranchType() {
		return branchType;
	}
	public void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public String getBranchMgr() {
		return branchMgr;
	}
	public void setBranchMgr(String branchMgr) {
		this.branchMgr = branchMgr;
	}
	public String getOpenDate() {
		return openDate;
	}
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getListFileName() {
		return listFileName;
	}
	public void setListFileName(String listFileName) {
		this.listFileName = listFileName;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getReleaser() {
		return releaser;
	}
	public void setReleaser(String releaser) {
		this.releaser = releaser;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	
}
