package bos.tis.lpctools.entity;

import java.io.Serializable;

public class CommitListContent implements Serializable{
	
	private static final long serialVersionUID = -6047371776920684905L;
	private String project_name;	//工程名称
	private String import_type;		//导出类型
	private String import_name;		//导出名称
	private String deploy_position;	//部署位置
	private String code_path;		//代码提交路径
	private String version;			//生效版本
	private String per_version;		//修改前版本
	private String submitter;		//提交人
	
	public CommitListContent(String project_name,String import_type,String import_name,String deploy_position,
			String code_path,String version,String per_version,String submitter){
		this.project_name = project_name;
		this.import_type = import_type;
		this.import_name = import_name;
		this.deploy_position = deploy_position;
		this.code_path = code_path;
		this.version = version;
		this.per_version = per_version;
		this.submitter = submitter;
	}
	
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getImport_type() {
		return import_type;
	}
	public void setImport_type(String import_type) {
		this.import_type = import_type;
	}
	public String getImport_name() {
		return import_name;
	}
	public void setImport_name(String import_name) {
		this.import_name = import_name;
	}
	public String getDeploy_position() {
		return deploy_position;
	}
	public void setDeploy_position(String deploy_position) {
		this.deploy_position = deploy_position;
	}
	public String getCode_path() {
		return code_path;
	}
	public void setCode_path(String code_path) {
		this.code_path = code_path;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getPer_version() {
		return per_version;
	}
	public void setPer_version(String per_version) {
		this.per_version = per_version;
	}
	public String getSubmitter() {
		return submitter;
	}
	public void setSubmitter(String submitter) {
		this.submitter = submitter;
	}

	@Override
	public String toString() {
		return "CommitListContent [project_name=" + project_name
				+ ", import_type=" + import_type + ", import_name="
				+ import_name + ", deploy_position=" + deploy_position
				+ ", code_path=" + code_path + ", version=" + version
				+ ", per_version=" + per_version + ", submitter=" + submitter
				+ "]";
	}
		
}
