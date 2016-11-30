package org.tis.tools.service.api.devmgr;

import java.util.List;

import org.tis.tools.base.Page;
import org.tis.tools.model.vo.devmgr.DevelopBranchInfo;

/**
 * 
 * 开发管理相关服务接口定义
 * 
 * @author megapro
 *
 */
public interface DevMgrRemoteService {

	/**
	 * 获取一个工作编号
	 * 
	 * @param workDesc
	 *            工作描述信息
	 * @return 工作编号
	 */
	public String genWorkId(String workDesc);

	/**
	 * 获取一个分支代码
	 * 
	 * @param branchType
	 *            分支类型，可选值见 {@link BranchType}
	 * @return 分支代码
	 */
	public String genBranchCode(String branchType);
	
	/**
	 * 新增一条开发分支信息
	 * 
	 * @param branchInfo
	 *            开发分支
	 */
	public void add(DevelopBranchInfo branchInfo);
	
	
	/**
	 * 删除一条开发分支信息
	 * 
	 * @param branchCode
	 *            开发分支
	 * @return 被删除的开发分支记录
	 */
	public DevelopBranchInfo delete(String branchCode);

	
	/**
	 * 更新开发分支信息
	 * 
	 * @param branchInfo
	 * @return 更新后的开发分支信息
	 */
	public DevelopBranchInfo update(DevelopBranchInfo branchInfo);

	
	/**
	 * 查询指定页的开发分支记录
	 * 
	 * @param page
	 *            分页信息
	 * @return 该页的开发分支记录
	 */
	public List<DevelopBranchInfo> queryList(Page page);

	
	/**
	 * 查询开发分支详情信息
	 * 
	 * @param branchCode
	 *            开发分支
	 * @return 开发分支信息
	 */
	public DevelopBranchInfo queryDtl(String branchCode);
}
