/**
 * 
 */
package org.tis.tools.rservice.om.capable;

import org.tis.tools.model.po.om.OmBusiorg;
import org.tis.tools.model.vo.om.OmBusiorgDetail;
import org.tis.tools.rservice.om.exception.BusiOrgManagementException;

import java.util.List;

/**
 * <pre>
 * 业务机构（BusiOrg）
 * 
 * 业务机构是以业务视角来对机构进行分类分组，每个业务视角称为“业务套别”或者“业务条线”（业务字典DICT_OM_BUSIDOMAIN），
 * 作为业务处理的机构线或者是业务统计的口径。
 * 
 * 业务条线没有层次关系。
 * 
 * 如：某公司在全国有33个分公司，为统计各个区域如华东、华北、华南、华西的销售情况，
 * 但公司内部没有华东、华北、华南、华西这样的机构，这时可以建立一个“区域”业务机构套别，
 * 在这个套别下建立华东、华北、华南、华西四个虚节点，然后将各个分公司分到不同的区域下，
 * 这样就可以按照区域进行统计了。
 * 
 * 本接口定义了对OM组织模块中业务机构（BusiOrg）概念对象的管理服务功能；
 * 
 * 满足《4.4.3 业务机构管理用例》描述中的功能需求，如：新增业务机构、查询业务机构信息等；
 * 
 * 这些功能主要供Branch Manager这样的管理型系统使用；
 * </pre>
 * 
 * @author megapro
 *
 */
public interface IBusiOrgRService {

	/*
	 * ========================================== 
	 * 
	 * 业务条线／套别管理相关的服务
	 * 
	 * 查询业务条线／套别信息
	 * 新增业务条线／套别
	 * 删除整条业务条线／套别
	 * 
	 * ==========================================
	 */
	/**
	 * <pre>
	 * 新增业务条线／套别
	 * 
	 * 说明：
	 * 实际上是新增一个业务字典DICT_OM_BUSIDOMAIN的字典项；
	 *
	 * 
	 * </pre>
	 * 
	 * @param busiDomainCode
	 *            业务条线／套别代码
	 * @param busiDomainName
	 *            业务条线名称
	 * @return 业务字典DICT_OM_BUSIDOMAIN对象
	 * @throws BusiOrgManagementException
	 */
//	void createBusiDomain(String busiDomainCode, String busiDomainName) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 删除业务条线／套别
	 * 
	 * 说明
	 * 清理条线下所有机构、虚拟机构关联关系；
	 * 删除对应业务字典：DICT_OM_BUSIDOMAIN的字典项；
	 * </pre>
	 * 
	 * @param busiDomainCode
	 *            业务条线／套别
	 * @throws BusiOrgManagementException
	 */
//	void deleteBusiDomain(String busiDomainCode) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 查询所有业务条线/套别（业务机构树的一级节点为业务条线/套别，因此需要一个这样的查询功能）
	 * 
	 * 说明：
	 * 实际上是查询业务字典： DICT_OM_BUSIDOMAIN的字典项；
	 * 
	 * </pre>
	 * 
	 * @return
	 */
	//TODO 业务字典完成后，修改返回类型为业务字典对象
//	List<Object> queryBusiDomain() ;
	
	
	/*
	 * ========================================== 
	 * 
	 * 业务机构管理相关的服务
	 * 
	 * 生成业务机构代码 
	 * 新增实际业务机构
	 * 新增虚拟业务机构
	 * 删除业务机构
	 * 拷贝业务机构
	 * 移动业务机构
	 * 修改业务机构
	 * 
	 * ==========================================
	 */
	/**
	 * <pre>
	 * 生成业务机构代码
	 *
	 * 调用 BOSHGenOrgCode.XX方法
	 * </pre>
	 * @param nodeType 节点类型（值来自业务字典 DICT_OM_NODETYPE）
	 * @param busiDomain 业务条线（值来自业务字典 DICT_OM_BUSIDOMAIN）
	 * @return 业务机构代码
	 * @exception BusiOrgManagementException
	 */
	String genBusiorgCode( String nodeType, String busiDomain ) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 新增业务机构（实际机构）
	 * 
	 * 说明：
	 * 系统将orgCode转为对应的guid存储；
	 * 
	 * </pre>
	 *
	 * @param newBusiorgName
	 *            新业务机构名称
	 * @param orgCode
	 *            对应实体机构代码（来自OmOrg）
	 * @param busiDomain
	 *            业务条线／套别（值来自业务字典DICT_OM_BUSIDOMAIN）
	 * @param parentsBusiorgCode
	 *            父业务机构代码
	 * @return 新业务机构对象
	 * @exception BusiOrgManagementException
	 */
	OmBusiorg createRealityBusiorg(String newBusiorgName, String orgCode, String busiDomain,
			String parentsBusiorgCode) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 新增业务机构（虚拟节点）
	 * 
	 * </pre>
	 *
	 *            新业务机构代码
	 * @param newBusiorgName
	 *            新业务机构名称
	 * @param busiDomain
	 *            业务条线／套别（值来自业务字典DICT_OM_BUSIDOMAIN）
	 * @param parentsBusiorgCode
	 *            父业务机构代码
	 * @return 新业务机构对象
	 * @throws BusiOrgManagementException
	 */
	OmBusiorg createDummyBusiorg(String newBusiorgName, String busiDomain,
			String parentsBusiorgCode) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 浅拷贝业务机构
	 * 
	 * 说明：
	 * 只复制业务机构记录；
	 * </pre>
	 * 
	 * @param fromBusiorgCode
	 *            参考业务机构代码
	 * @param toParentsBusiorgCode
	 *            新业务机构的父业务机构
	 * @return 新增的业务机构对象
	 * @throws BusiOrgManagementException
	 */
	OmBusiorg copyBusiorg(String fromBusiorgCode,  String toParentsBusiorgCode) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 深度拷贝业务机构
	 * 
	 * 说明：
	 * 除复制业务机构记录，还复制业务机构的所有子业务机构结构；
	 * 复制过程中，系统自动生成补充业务机构代码；
	 * 
	 * </pre>
	 * 
	 * @param fromBusiorgCode
	 *            新业务机构代码
	 * @param toParentsBusiorgCode
	 *            新业务机构的父业务机构
	 * @return 新增的业务机构对象
	 * @throws BusiOrgManagementException
	 */
	OmBusiorg copyBusiorgDeep(String fromBusiorgCode,  String toParentsBusiorgCode) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 移动业务机构
	 * </pre>
	 * 
	 * @param busiorgCode
	 *            被移动的业务机构代码
	 * @param fromParentsBusiorgCode
	 *            原父业务机构
	 * @param toParentsBusiorgCode
	 *            新父业务机构
	 * @return 移动后的业务机构信息对象
	 * @throws BusiOrgManagementException
	 */
	OmBusiorg moveBusiorg(String busiorgCode, String fromParentsBusiorgCode, String toParentsBusiorgCode) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 修改业务机构
	 * 
	 * 说明：
	 * 只修改传入对象（newOmBusiorg）有值的字段；
	 * 避免对（逻辑上）不可直接修改字段的更新，如：子节点数不能直接通过修改而更新；
	 * </pre>
	 * 
	 * @param newOmBusiorg
	 *            新业务机构
	 * @return 修改后的业务机构信息对象
	 * @throws BusiOrgManagementException
	 */
	OmBusiorg updateBusiorg(OmBusiorg newOmBusiorg) throws BusiOrgManagementException;
	
	/**
	 * <pre>
	 * 删除业务机构
	 * 
	 * 说明：
	 * 从该业务机构为根节点的整棵业务机构树；
	 * 
	 * </pre>
	 * 
	 * @param busiorgCode
	 *            待删除的业务机构代码
	 * @throws BusiOrgManagementException
	 */
	OmBusiorg deleteBusiorg(String busiorgCode) throws BusiOrgManagementException ;
	
	/*
	 * ========================================== 
	 * 
	 * 业务机构相关信息查询服务
	 * 
	 * 查询业务机构摘要信息
	 * 查询业务条线/套别下业务机构树
	 * 
	 * ==========================================
	 */
	
	/**
	 * <pre>
	 * 查询业务机构摘要信息
	 * </pre>
	 * 
	 * @param busiorgCode 业务机构代码
	 * @return 业务机构信息对象
	 */
	OmBusiorg queryBusiorg(String busiorgCode) ;
	
	/**
	 * <pre>
	 * 查询指定业务条线下的业务机构树
	 * </pre>
	 * 
	 * @param busiDomainCode
	 *            业务条线（值来自业务字典： DICT_OM_BUSIDOMAIN）
	 * @return 某条线下业务机构列表
	 */
	List<OmBusiorgDetail> queryBusiorgByDomain(String busiDomainCode);

	/**
	 * 查询指定业务条线下的根业务机构
	 * @param busiDomainCode
	 * @return
	 */
	List<OmBusiorg> queryRootBusiorgByDomain(String busiDomainCode);

	/**
	 * 查询此业务条线下所有的业务机构
	 * @param busiDomainCode
	 * @return
	 */
	List<OmBusiorg> queryAllBusiorgByDomain(String busiDomainCode);

	/**
	 *  查询子业务机构
	 * @param busiorgCode
	 * @return
	 */
	List<OmBusiorg> queryChildBusiorgByCode(String busiorgCode);

	/**
	 * 通过业务机构名称检索业务机构
	 * @param busiorgName
	 * @return
	 */
	List<OmBusiorg> queryBusiorgByName(String busiorgName);
}
