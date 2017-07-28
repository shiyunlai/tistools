package org.tis.tools.rservice.om.capable;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.model.po.om.OmBusiorg;
import org.tis.tools.model.vo.om.OmBusiorgDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.basic.IOmBusiorgRService;
import org.tis.tools.rservice.om.exception.BusiOrgManagementException;

import java.util.List;

public class OmBusiOrgRServiceImpl extends BaseRService implements IBusiOrgRService {
    
    @Autowired
    IOmBusiorgRService omBusiorgRService;
    /**
     * <pre>
     * 生成业务机构代码
     *
     * 调用 BOSHGenOrgCode.XX方法
     * </pre>
     *
     * @param nodeType   节点类型（值来自业务字典 DICT_OM_NODETYPE）
     * @param busiDomain 业务条线（值来自业务字典 DICT_OM_BUSIDOMAIN）
     * @return 业务机构代码
     * @throws BusiOrgManagementException
     */
    @Override
    public String genBusiorgCode(String nodeType, String busiDomain) throws BusiOrgManagementException {
        return null;
    }

    /**
     * <pre>
     * 新增业务机构（实际机构）
     *
     * 说明：
     * 系统将orgCode转为对应的guid存储；
     *
     * </pre>
     *
     * @param newBusiorgCode     新业务机构代码
     * @param newBusiorgName     新业务机构名称
     * @param orgCode            对应实体机构代码（来自OmOrg）
     * @param busiDomain         业务条线／套别（值来自业务字典DICT_OM_BUSIDOMAIN）
     * @param parentsBusiorgCode 父业务机构代码
     * @return 新业务机构对象
     * @throws BusiOrgManagementException
     */
    @Override
    public OmBusiorg createRealityBusiorg(String newBusiorgCode, String newBusiorgName, String orgCode, String busiDomain, String parentsBusiorgCode) throws BusiOrgManagementException {
        return null;
    }

    /**
     * <pre>
     * 新增业务机构（虚拟节点）
     *
     * </pre>
     *
     * @param newBusiorgCode     新业务机构代码
     * @param newBusiorgName     新业务机构名称
     * @param busiDomain         业务条线／套别（值来自业务字典DICT_OM_BUSIDOMAIN）
     * @param parentsBusiorgCode 父业务机构代码
     * @return 新业务机构对象
     * @throws BusiOrgManagementException
     */
    @Override
    public OmBusiorg createDummyBusiorg(String newBusiorgCode, String newBusiorgName, String busiDomain, String parentsBusiorgCode) throws BusiOrgManagementException {
        return null;
    }

    /**
     * <pre>
     * 浅拷贝业务机构
     *
     * 说明：
     * 只复制业务机构记录；
     * </pre>
     *
     * @param fromBusiorgCode      参考业务机构代码
     * @param newBusiorgCode       新业务机构代码
     * @param toParentsBusiorgCode 新业务机构的父业务机构
     * @return 新增的业务机构对象
     * @throws BusiOrgManagementException
     */
    @Override
    public OmBusiorg copyBusiorg(String fromBusiorgCode, String newBusiorgCode, String toParentsBusiorgCode) throws BusiOrgManagementException {
        return null;
    }

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
     * @param fromBusiorgCode      参考业务机构代码
     * @param newBusiorgCode       新业务机构代码
     * @param toParentsBusiorgCode 新业务机构的父业务机构
     * @return 新增的业务机构对象
     * @throws BusiOrgManagementException
     */
    @Override
    public OmBusiorg copyBusiorgDeep(String fromBusiorgCode, String newBusiorgCode, String toParentsBusiorgCode) throws BusiOrgManagementException {
        return null;
    }

    /**
     * <pre>
     * 移动业务机构
     * </pre>
     *
     * @param busiorgCode            被移动的业务机构代码
     * @param fromParentsBusiorgCode 原父业务机构
     * @param toParentsBusiorgCode   新父业务机构
     * @return 移动后的业务机构信息对象
     * @throws BusiOrgManagementException
     */
    @Override
    public OmBusiorg moveBusiorg(String busiorgCode, String fromParentsBusiorgCode, String toParentsBusiorgCode) throws BusiOrgManagementException {
        return null;
    }

    /**
     * <pre>
     * 修改业务机构
     *
     * 说明：
     * 只修改传入对象（newOmBusiorg）有值的字段；
     * 避免对（逻辑上）不可直接修改字段的更新，如：子节点数不能直接通过修改而更新；
     * </pre>
     *
     * @param newOmBusiorg 新业务机构
     * @return 修改后的业务机构信息对象
     * @throws BusiOrgManagementException
     */
    @Override
    public OmBusiorg updateBusiorg(OmBusiorg newOmBusiorg) throws BusiOrgManagementException {
        return null;
    }

    /**
     * <pre>
     * 删除业务机构
     *
     * 说明：
     * 从该业务机构为根节点的整棵业务机构树；
     *
     * </pre>
     *
     * @param busiorgCode 待删除的业务机构代码
     * @throws BusiOrgManagementException
     */
    @Override
    public void deleteBusiorg(String busiorgCode) throws BusiOrgManagementException {

    }

    /**
     * <pre>
     * 查询业务机构摘要信息
     * </pre>
     *
     * @param busiorgCode 业务机构代码
     * @return 业务机构信息对象
     */
    @Override
    public OmBusiorg queryBusiorg(String busiorgCode) {
        return null;
    }

    /**
     * <pre>
     * 查询指定业务条线下的业务机构树
     * </pre>
     *
     * @param busiDomainCode 业务条线（值来自业务字典： DICT_OM_BUSIDOMAIN）
     * @return 某条线下业务机构列表
     */
    @Override
    public List<OmBusiorgDetail> queryBusiorgByDomain(String busiDomainCode) {
        return null;
    }
}
