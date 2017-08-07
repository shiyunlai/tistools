package org.tis.tools.rservice.om.capable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.OMConstants;
import org.tis.tools.model.po.om.OmBusiorg;
import org.tis.tools.model.vo.om.OmBusiorgDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.om.basic.IOmBusiorgRService;
import org.tis.tools.rservice.om.exception.BusiOrgManagementException;
import org.tis.tools.service.om.OmOrgService;
import org.tis.tools.service.om.exception.OMExceptionCodes;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OmBusiOrgRServiceImpl extends BaseRService implements IBusiOrgRService {
    /** 拷贝新增时，代码前缀  */
    private static final String CODE_HEAD_COPYFROM = "Copyfrom-";
    @Autowired
    IOmBusiorgRService omBusiorgRService;

    @Autowired
    OmOrgService omOrgService ;
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
        // 验证传入参数
        if (StringUtil.isEmpty(newBusiorgCode)) {
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("newBusiorgCode"));
        }
        if (StringUtil.isEmpty(newBusiorgName)) {
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("newBusiorgName"));
        }
        if (StringUtil.isEmpty(orgCode)) {
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("orgCode"));
        }
        if (StringUtil.isEmpty(busiDomain)) {
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("busiDomain"));
        }
        /**
         * 机构码已存在
         */
        if(isExitByBusiorgCode(newBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("busiDomain"));
        }
        OmBusiorg omBusiorg = new OmBusiorg();
        omBusiorg.setGuid(GUID.busiorg());
        omBusiorg.setNodeType(OMConstants.BUSIORG_NODE_TYPE_REALITY);
        //收集入参
        omBusiorg.setBusiorgCode(newBusiorgCode);
        omBusiorg.setBusiDomain(busiDomain);
        omBusiorg.setBusiorgName(newBusiorgName);
        omBusiorg.setOrgCode(orgCode);
        //判断是否是新增根机构
        if(parentsBusiorgCode == null ||parentsBusiorgCode==""){
            omBusiorg.setGuidParents(null);
            omBusiorg.setBusiorgLevel(new BigDecimal("0"));
            omBusiorg.setSeqno(omBusiorg.getGuid());
            omBusiorg.setSortno(new BigDecimal("0"));
            omBusiorg.setIsleaf(CommonConstants.YES);
            omBusiorg.setSubCount(new BigDecimal("0"));
            // 新增机构
            try {
                omBusiorgRService.insert(omBusiorg);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusiOrgManagementException(
                        OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                        BasicUtil.wrap(e.getCause().getMessage()), "新增业务机构（实际机构）！{0}");
            }
        }else{
            OmBusiorg parentOmBusiorg = queryBusiorg(parentsBusiorgCode);
            omBusiorg.setGuidParents(parentOmBusiorg.getGuid());
            omBusiorg.setBusiorgLevel(parentOmBusiorg.getBusiorgLevel().add(new BigDecimal("1")));
            omBusiorg.setSeqno(parentOmBusiorg.getSeqno()+"."+omBusiorg.getGuid());
            omBusiorg.setSortno(new BigDecimal("0"));
            omBusiorg.setIsleaf(CommonConstants.YES);
            omBusiorg.setSubCount(new BigDecimal("0"));
            //更新父机构
            parentOmBusiorg.setIsleaf(CommonConstants.NO);
            parentOmBusiorg.setSubCount(parentOmBusiorg.getSubCount().add(new BigDecimal("1")));
            // 新增机构
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        omBusiorgRService.update(parentOmBusiorg);
                        omBusiorgRService.insert(omBusiorg);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new BusiOrgManagementException(
                                OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                                BasicUtil.wrap(e.getCause().getMessage()), "新增业务机构（实际机构）！{0}");
                    }
                }
            });
        }
        return omBusiorg;
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
        // 验证传入参数
        if (StringUtil.isEmpty(newBusiorgCode)) {
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("newBusiorgCode"));
        }
        if (StringUtil.isEmpty(newBusiorgName)) {
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("newBusiorgName"));
        }
        if (StringUtil.isEmpty(busiDomain)) {
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("busiDomain"));
        }
        /**
         * 机构码已存在
         */
        if(isExitByBusiorgCode(newBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("busiDomain"));
        }
        OmBusiorg omBusiorg = new OmBusiorg();
        omBusiorg.setGuid(GUID.busiorg());
        omBusiorg.setNodeType(OMConstants.BUSIORG_NODE_TYPE_DUMMY);
        //收集入参
        omBusiorg.setBusiorgCode(newBusiorgCode);
        omBusiorg.setBusiDomain(busiDomain);
        omBusiorg.setBusiorgName(newBusiorgName);
        //判断是否是新增根机构
        if(parentsBusiorgCode == null ||parentsBusiorgCode == ""){
            omBusiorg.setGuidParents(null);
            omBusiorg.setBusiorgLevel(new BigDecimal("0"));
            omBusiorg.setSeqno(omBusiorg.getGuid());
            omBusiorg.setSortno(new BigDecimal("0"));
            omBusiorg.setIsleaf(CommonConstants.YES);
            omBusiorg.setSubCount(new BigDecimal("0"));
            // 新增机构
            try {
                omBusiorgRService.insert(omBusiorg);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusiOrgManagementException(
                        OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                        BasicUtil.wrap(e.getCause().getMessage()), "新增业务机构（实际机构）！{0}");
            }
        }else{
            OmBusiorg parentOmBusiorg = queryBusiorg(parentsBusiorgCode);
            omBusiorg.setGuidParents(parentOmBusiorg.getGuid());
            omBusiorg.setBusiorgLevel(parentOmBusiorg.getBusiorgLevel().add(new BigDecimal("1")));
            omBusiorg.setSeqno(parentOmBusiorg.getSeqno()+"."+omBusiorg.getGuid());
            omBusiorg.setSortno(new BigDecimal("0"));
            omBusiorg.setIsleaf(CommonConstants.YES);
            omBusiorg.setSubCount(new BigDecimal("0"));
            //更新父机构
            parentOmBusiorg.setIsleaf(CommonConstants.NO);
            parentOmBusiorg.setSubCount(parentOmBusiorg.getSubCount().add(new BigDecimal("1")));
            // 新增机构
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        omBusiorgRService.update(parentOmBusiorg);
                        omBusiorgRService.insert(omBusiorg);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new BusiOrgManagementException(
                                OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                                BasicUtil.wrap(e.getCause().getMessage()), "新增业务机构（实际机构）！{0}");
                    }
                }
            });
        }
        return omBusiorg;
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
        if(!StringUtil.noEmpty(fromBusiorgCode, newBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
        }
        /**
         * 机构码已存在
         */
        if(isExitByBusiorgCode(newBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("busiDomain"));
        }
        if(!isExitByBusiorgCode(fromBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE,
                    BasicUtil.wrap(fromBusiorgCode), "拷贝业务机构时，找不到参照业务机构{0}！");
        }
        /**
         * 如果新业务机构的父业务机构代码不存在 默认为顶级业务机构
         */
        OmBusiorg newOmBusiorg = queryBusiorg(fromBusiorgCode);
        if(StringUtil.isEmpty(toParentsBusiorgCode)){
            newOmBusiorg.setGuid(GUID.busiorg());
            newOmBusiorg.setBusiorgCode(newBusiorgCode);
            newOmBusiorg.setBusiorgName(CODE_HEAD_COPYFROM+newOmBusiorg.getBusiorgName());
            newOmBusiorg.setIsleaf(CommonConstants.YES);
            newOmBusiorg.setSubCount(new BigDecimal("0"));
            newOmBusiorg.setGuidParents(null);
            newOmBusiorg.setBusiorgLevel(new BigDecimal("0"));
            newOmBusiorg.setSeqno(newOmBusiorg.getGuid());
            newOmBusiorg.setSortno(new BigDecimal("0"));
            newOmBusiorg.setIsleaf(CommonConstants.YES);
            newOmBusiorg.setSubCount(new BigDecimal("0"));
            try {
                omBusiorgRService.insert(newOmBusiorg);
            } catch (Exception e) {
                e.printStackTrace();
                throw new BusiOrgManagementException(
                        OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                        BasicUtil.wrap(e.getCause().getMessage()), "拷贝业务机构（实际机构）！{0}");
            }
        }else{
            if(!isExitByBusiorgCode(toParentsBusiorgCode)){
                throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE,
                        BasicUtil.wrap(fromBusiorgCode), "拷贝业务机构时，找不到目标父业务机构{0}！");
            }
            OmBusiorg parentsOmBusiorg = queryBusiorg(toParentsBusiorgCode);
            newOmBusiorg.setGuid(GUID.busiorg());
            newOmBusiorg.setBusiorgCode(newBusiorgCode);
            newOmBusiorg.setBusiorgName(CODE_HEAD_COPYFROM+newOmBusiorg.getBusiorgName());
            newOmBusiorg.setSubCount(new BigDecimal("0"));
            newOmBusiorg.setGuidParents(toParentsBusiorgCode);
            newOmBusiorg.setBusiorgLevel(parentsOmBusiorg.getBusiorgLevel().add(new BigDecimal("1")));
            newOmBusiorg.setSeqno(parentsOmBusiorg.getSeqno()+newOmBusiorg.getGuid());
            newOmBusiorg.setSortno(new BigDecimal("0"));
            newOmBusiorg.setIsleaf(CommonConstants.YES);
            newOmBusiorg.setSubCount(new BigDecimal("0"));
            //更新父机构
            parentsOmBusiorg.setIsleaf(CommonConstants.NO);
            parentsOmBusiorg.setSubCount(parentsOmBusiorg.getSubCount().add(new BigDecimal("1")));
            // 新增机构
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        omBusiorgRService.update(parentsOmBusiorg);
                        omBusiorgRService.insert(newOmBusiorg);

                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new BusiOrgManagementException(
                                OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                                BasicUtil.wrap(e.getCause().getMessage()), "拷贝业务机构（实际机构）！{0}");
                    }
                }
            });
        }
        return newOmBusiorg;
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
        if(!StringUtil.noEmpty(fromBusiorgCode, newBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
        }
        /**
         * 机构码已存在
         */
        if(isExitByBusiorgCode(newBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("busiDomain"));
        }
        if(!isExitByBusiorgCode(fromBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE,
                    BasicUtil.wrap(fromBusiorgCode), "拷贝业务机构时，找不到参照业务机构{0}！");
        }
        /**
         * 如果新业务机构的父业务机构代码不存在 默认为顶级业务机构
         */
        OmBusiorg newOmBusiorg = queryBusiorg(fromBusiorgCode);
        WhereCondition wc = new WhereCondition();
        wc.andNotEquals("BUSIORG_CODE", fromBusiorgCode);
        wc.andFullLike("seqno",fromBusiorgCode);
        //所有下属对象
        List<OmBusiorg> busiorgs = omBusiorgRService.query(wc);
        BigDecimal mainLevel = newOmBusiorg.getBusiorgLevel();
        if(StringUtil.isEmpty(toParentsBusiorgCode)){
            newOmBusiorg.setGuid(GUID.busiorg());
            newOmBusiorg.setBusiorgCode(newBusiorgCode);
            newOmBusiorg.setBusiorgName(CODE_HEAD_COPYFROM+newOmBusiorg.getBusiorgName());
            newOmBusiorg.setGuidParents(null);
            newOmBusiorg.setBusiorgLevel(new BigDecimal("0"));
            newOmBusiorg.setSeqno(newOmBusiorg.getGuid());
            newOmBusiorg.setSortno(new BigDecimal("0"));
            newOmBusiorg.setIsleaf(CommonConstants.YES);
            newOmBusiorg.setSubCount(new BigDecimal("0"));
            for(OmBusiorg busiorg:busiorgs){
                busiorg.setGuid(GUID.busiorg());
                busiorg.setBusiorgName(CODE_HEAD_COPYFROM+busiorg.getBusiorgName());
                busiorg.setBusiorgLevel(busiorg.getBusiorgLevel().subtract(mainLevel));
                /**
                 * 数据库该表GUID 长度为17位 故此处计算新的面包屑 采用 位数计算开始截取
                 * 如果为原等级为根节点不需要修改
                 */
                if(mainLevel.compareTo(new BigDecimal("0"))!=0){
                    busiorg.setSeqno(busiorg.getSeqno().substring(mainLevel.intValue()*17+1));
                }
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        omBusiorgRService.insert(newOmBusiorg);
                        for(OmBusiorg busiorg:busiorgs){
                            omBusiorgRService.insert(busiorg);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new BusiOrgManagementException(
                                OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                                BasicUtil.wrap(e.getCause().getMessage()), "深度拷贝业务机构（实际机构）！{0}");
                    }
                }
            });
        }else{
            if(!isExitByBusiorgCode(toParentsBusiorgCode)){
                throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE,
                        BasicUtil.wrap(fromBusiorgCode), "拷贝业务机构时，找不到目标父业务机构{0}！");
            }
            OmBusiorg parentsOmBusiorg = queryBusiorg(toParentsBusiorgCode);
            newOmBusiorg.setGuid(GUID.busiorg());
            newOmBusiorg.setBusiorgCode(newBusiorgCode);
            newOmBusiorg.setBusiorgName(CODE_HEAD_COPYFROM+newOmBusiorg.getBusiorgName());
            newOmBusiorg.setSubCount(new BigDecimal("0"));
            newOmBusiorg.setGuidParents(toParentsBusiorgCode);
            newOmBusiorg.setBusiorgLevel(parentsOmBusiorg.getBusiorgLevel().add(new BigDecimal("1")));
            newOmBusiorg.setSeqno(parentsOmBusiorg.getSeqno()+newOmBusiorg.getGuid());
            newOmBusiorg.setSortno(new BigDecimal("0"));
            for(OmBusiorg busiorg:busiorgs){
                busiorg.setGuid(GUID.busiorg());
                busiorg.setBusiorgName(CODE_HEAD_COPYFROM+busiorg.getBusiorgName());
                busiorg.setBusiorgLevel(busiorg.getBusiorgLevel().subtract(mainLevel).add(parentsOmBusiorg.getBusiorgLevel()));
                /**
                 * 数据库该表GUID 长度为17位 故此处计算新的面包屑 采用 位数计算开始截取
                 * 如果为原等级为根节点直接加上新的父节点
                 */
                if(mainLevel.compareTo(new BigDecimal("0"))==0){
                    busiorg.setSeqno(parentsOmBusiorg.getSeqno()+busiorg.getSeqno());
                }else{
                    busiorg.setSeqno(parentsOmBusiorg.getSeqno()+busiorg.getSeqno().substring(mainLevel.intValue()*17+1));
                }
            }
            //更新父机构
            parentsOmBusiorg.setIsleaf(CommonConstants.NO);
            // 新增机构
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        omBusiorgRService.update(parentsOmBusiorg);
                        omBusiorgRService.insert(newOmBusiorg);
                        for(OmBusiorg busiorg:busiorgs){
                            omBusiorgRService.insert(busiorg);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new BusiOrgManagementException(
                                OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                                BasicUtil.wrap(e.getCause().getMessage()), "拷贝业务机构（实际机构）！{0}");
                    }
                }
            });
        }
        return newOmBusiorg;
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
        if(!StringUtil.noEmpty(fromParentsBusiorgCode, busiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY);
        }
        if(isExitByBusiorgCode(busiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE,
                    BasicUtil.wrap(busiorgCode), "移动业务机构时，新业务机构{0}已经存在！");
        }
        if(!isExitByBusiorgCode(fromParentsBusiorgCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE,
                    BasicUtil.wrap(fromParentsBusiorgCode), "移动业务机构时，找不到参照业务机构{0}！");
        }
        OmBusiorg omBusiorg = queryBusiorg(busiorgCode);
        WhereCondition wc = new WhereCondition();
        wc.andNotEquals("BUSIORG_CODE", busiorgCode);
        wc.andFullLike("seqno",busiorgCode);
        //所有下属对象
        List<OmBusiorg> busiorgs = omBusiorgRService.query(wc);
        BigDecimal mainLevel = omBusiorg.getBusiorgLevel();
        /**
         * 如果要业务机构的父业务机构代码不存在 默认为顶级业务机构
         */
        if(StringUtil.isEmpty(toParentsBusiorgCode)){
            omBusiorg.setGuidParents(null);
            omBusiorg.setBusiorgLevel(new BigDecimal("0"));
            omBusiorg.setSeqno(omBusiorg.getGuid());
            omBusiorg.setSortno(new BigDecimal("0"));
            for(OmBusiorg busiorg:busiorgs){
                busiorg.setBusiorgLevel(busiorg.getBusiorgLevel().subtract(mainLevel));
                /**
                 * 数据库该表GUID 长度为17位 故此处计算新的面包屑 采用 位数计算开始截取
                 * 如果为原等级为根节点不需要修改
                 */
                if(mainLevel.compareTo(new BigDecimal("0"))!=0){
                    busiorg.setSeqno(busiorg.getSeqno().substring(mainLevel.intValue()*17+1));
                }
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        omBusiorgRService.insert(omBusiorg);
                        for(OmBusiorg busiorg:busiorgs){
                            omBusiorgRService.insert(busiorg);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new BusiOrgManagementException(
                                OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                                BasicUtil.wrap(e.getCause().getMessage()), "移动业务机构（实际机构）！{0}");
                    }
                }
            });
        }else{
            if(!isExitByBusiorgCode(toParentsBusiorgCode)){
                throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE,
                        BasicUtil.wrap(toParentsBusiorgCode), "移动业务机构时，找不到目标父业务机构{0}！");
            }
            OmBusiorg parentsOmBusiorg = queryBusiorg(toParentsBusiorgCode);
            omBusiorg.setGuidParents(toParentsBusiorgCode);
            omBusiorg.setBusiorgLevel(parentsOmBusiorg.getBusiorgLevel().add(new BigDecimal("1")));
            omBusiorg.setSeqno(parentsOmBusiorg.getSeqno()+omBusiorg.getGuid());
            for(OmBusiorg busiorg:busiorgs){
                busiorg.setBusiorgLevel(busiorg.getBusiorgLevel().subtract(mainLevel).add(parentsOmBusiorg.getBusiorgLevel()));
                /**
                 * 数据库该表GUID 长度为17位 故此处计算新的面包屑 采用 位数计算开始截取
                 * 如果为原等级为根节点直接加上新的父节点
                 */
                if(mainLevel.compareTo(new BigDecimal("0"))==0){
                    busiorg.setSeqno(parentsOmBusiorg.getSeqno()+busiorg.getSeqno());
                }else{
                    busiorg.setSeqno(parentsOmBusiorg.getSeqno()+busiorg.getSeqno().substring(mainLevel.intValue()*17+1));
                }
            }
            //更新父机构
            parentsOmBusiorg.setIsleaf(CommonConstants.NO);
            // 新增机构
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        omBusiorgRService.update(parentsOmBusiorg);
                        omBusiorgRService.insert(omBusiorg);
                        for(OmBusiorg busiorg:busiorgs){
                            omBusiorgRService.insert(busiorg);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new BusiOrgManagementException(
                                OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                                BasicUtil.wrap(e.getCause().getMessage()), "拷贝业务机构（实际机构）！{0}");
                    }
                }
            });
        }
        return omBusiorg;
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
        OmBusiorg oldOmBusiorg = queryBusiorg(newOmBusiorg.getBusiorgCode());
        /**
         * TODO 修改字段 不可更新的需要重新赋值 可以修改的 必填字段要做判断空字符串
         * 		以下修改字段 随便定义的
         * @author kexian.li
         */
        if(StringUtil.noEmpty(newOmBusiorg.getNodeType())){
            oldOmBusiorg.setNodeType(newOmBusiorg.getNodeType());
        }
        if(StringUtil.noEmpty(newOmBusiorg.getBusiorgName())){
            oldOmBusiorg.setBusiorgName(newOmBusiorg.getBusiorgName());
        }
        if(StringUtil.noEmpty(newOmBusiorg.getGuidOrg())){
            oldOmBusiorg.setGuidOrg(newOmBusiorg.getGuidOrg());
        }
        if(StringUtil.noEmpty(newOmBusiorg.getGuidPosition())){
            oldOmBusiorg.setGuidPosition(newOmBusiorg.getGuidPosition());
        }
        if(StringUtil.noEmpty(newOmBusiorg.getBusiDomain())){
            oldOmBusiorg.setBusiDomain(newOmBusiorg.getBusiDomain());
        }
        try {
            omBusiorgRService.update(oldOmBusiorg);
            return oldOmBusiorg;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusiOrgManagementException(
                    OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                    BasicUtil.wrap(e.getCause().getMessage()), "修改业务机构失败！{0}");
        }
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
        OmBusiorg omBusiorg = queryBusiorg(busiorgCode);
        if(null == omBusiorg){
            throw new BusiOrgManagementException(OMExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("newBusiorgCode"));
        }
        WhereCondition wc = new WhereCondition();
        wc.andFullLike("seqno",busiorgCode);
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            public void doInTransactionWithoutResult(TransactionStatus status) {
                try {
                    omBusiorgRService.deleteByCondition(wc);
                } catch (Exception e) {
                    status.setRollbackOnly();
                    e.printStackTrace();
                    throw new BusiOrgManagementException(
                            OMExceptionCodes.FAILURE_WHRN_CREAT_BUSIORG,
                            BasicUtil.wrap(e.getCause().getMessage()), "删除业务机构（实际机构）！{0}");
                }
            }
        });
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
        WhereCondition wc = new WhereCondition();
        wc.andEquals("BUSIORG_CODE",busiorgCode);
        List<OmBusiorg> busiorgList = omBusiorgRService.query(wc);
        if(busiorgList.size() != 1){
            throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE, BasicUtil.wrap(busiorgCode));
        }
        return busiorgList.get(0);
    }

    /**
     * <pre>
     * 查询指定业务条线下的业务机构树
     * </pre>
     *
     * @param busiDomainCode 业务条线（值来自业务字典： DICT_OM_BUSIDOMAIN）
     * @return 某条线下业务机构列表
     */
    //@Override
    public List<OmBusiorgDetail> queryBusiorgByDomain(String busiDomainCode,String level) {
        if(StringUtil.isEmpty(busiDomainCode)){
            throw new BusiOrgManagementException(OMExceptionCodes.NOT_EXIST_BY_BUSIORG_CODE, BasicUtil.wrap(""));
        }
        WhereCondition wc = new WhereCondition();
        wc.andEquals("BUSI_DOMAIN",busiDomainCode);
        if(StringUtil.isEmpty(level)){
            wc.andEquals("busiorg_level",level);
        }
        List<OmBusiorg> omBusiorgs = omBusiorgRService.query(wc);
        List<OmBusiorgDetail> omBusiorgDetails = new ArrayList<>();
        OmBusiorgDetail omBusiorgDetail;
        Set<OmBusiorg> omBusiorgSets;
        for(OmBusiorg oBusiorg:omBusiorgs){
            wc.clear();
            omBusiorgDetail = new OmBusiorgDetail();
    		/*
    		 * OmBusiorgDetail 是 OmBusiorg 它的子类 但是 组装这个类型的时候 是怎么做呢
    		 */
//            omBusiorgDetail.setparentsBusiorg(oBusiorg);
            wc.andEquals("guid_parents", oBusiorg.getGuid());
            omBusiorgSets = new HashSet<>();
            omBusiorgSets.addAll(omBusiorgRService.query(wc));
            omBusiorgDetail.setChildBusiorgSet(omBusiorgSets);
            omBusiorgDetail.setOrg(omOrgService.loadByGuid(oBusiorg.getGuidOrg()));
            omBusiorgDetails.add(omBusiorgDetail);
        }
        return omBusiorgDetails;
    }

    @Override
    public List<OmBusiorgDetail> queryBusiorgByDomain(String busiDomainCode) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param busiorgCode
     * @return 根据机构码查询 机构是否存在
     * @author kexian.li
     */
    private boolean isExitByBusiorgCode(String busiorgCode){
        WhereCondition wc = new WhereCondition();
        wc.andEquals("BUSIORG_CODE",busiorgCode);
        List<OmBusiorg> busiorgList = omBusiorgRService.query(wc);
        if(busiorgList.size() ==0){
            return false;
        }
        return true;
    }
}
