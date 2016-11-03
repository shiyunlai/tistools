/***
 * 
 */
package org.tis.tools.model.vo.devmgr;

import java.io.Serializable;

/***
 * 
 * 开发分支信息（值对象）
 * 
 * @author megapro
 *
 */
public class DevelopBranchInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6400883340053350436L;
	
	/** 工作编号 */
	String workId ; 
	/** 开发分支 */
	String branchCode ; 
	/** 分支类型 */
	String branchType ; 
	/** 分支管理员 */
	String branchMgr ; 
	/** 分支申请日期 */
	String branchOpenDate ; 
	/** 清单文件名称 */
	String releseFileName ; 
	/** 分支处理状态 */
	String branchStatus ; 
	/** 任务编号 */
	String artfId ; 
	/** 开发负责人 */
	String developer ; 
	/** 投产负责人 */
	String releaser ; 
	/** 投产日期 */
	String releaseDate ;
	public String getWorkId() {
		return workId;
	}
	public void setWorkId(String workId) {
		this.workId = workId;
	}
	public String getBranchCode() {
		return branchCode;
	}
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
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
	public String getBranchOpenDate() {
		return branchOpenDate;
	}
	public void setBranchOpenDate(String branchOpenDate) {
		this.branchOpenDate = branchOpenDate;
	}
	public String getReleseFileName() {
		return releseFileName;
	}
	public void setReleseFileName(String releseFileName) {
		this.releseFileName = releseFileName;
	}
	public String getBranchStatus() {
		return branchStatus;
	}
	public void setBranchStatus(String branchStatus) {
		this.branchStatus = branchStatus;
	}
	public String getArtfId() {
		return artfId;
	}
	public void setArtfId(String artfId) {
		this.artfId = artfId;
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
	
	public String toString(){
		StringBuffer sb = new StringBuffer() ; 
		sb.append(this.workId).append(this.branchCode) ; 
		return sb.toString() ; 
	}
}
