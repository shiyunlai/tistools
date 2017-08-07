package org.tis.tools.rservice.ac.capable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.CryptographyUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcOperatorIdentity;
import org.tis.tools.model.po.ac.AcOperatorIdentityres;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.OperatorManagementException;
import org.tis.tools.service.ac.*;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OperatorRServiceImpl extends BaseRService implements IOperatorRService {

    @Autowired
    AcOperatorService acOperatorService;

    @Autowired
    AcOperatorIdentityService acOperatorIdentityService;

    @Autowired
    AcOperatorIdentityresService acOperatorIdentityresService;

    @Autowired
    AcOperatorMenuService acOperatorMenuService;

    @Autowired
    AcOperatorShortcutService acOperatorShortcutService;

    @Autowired
    AcOperatorBhvService acOperatorBhvService;

    @Autowired
    AcOperatorFuncService acOperatorFuncService;

    @Autowired
    AcOperatorRoleService acOperatorRoleService;

    @Autowired
    AcOperatorConfigService acOperatorConfigService;

    /**
     * 新增操作员
     *
     * @param acOperator
     * @throws OperatorManagementException
     */
    @Override
    public void createOperator(AcOperator acOperator) throws OperatorManagementException {
        try {
            if(null == acOperator) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("acOperator"));
            }
            // 校验传入参数
            // 登陆用户名:userId’;   密码:’passWord’;   操作员姓名:’operatorName’;
            // 操作员状态: ‘operatorStatus;  密码失效日:’invalDate’ ;  认证模式:’authMode’ ;
            // 锁定次数限制:lockLimit;  当前错误登录次数:’errCount’ ;  锁定时间:’lockTime’ ;
            // 解锁时间:’unlockTime’ ;  菜单风格:’menuType’; 最近登录时间:’lastLogin’; 有效开始日期:’startDate’;
            // 有效截止日期:’endDate’; 允许时间范围:’validTime’; 允许MAC码:’macCode’ ; 允许IP地址:’ipAddRess’

            //USER_ID 必填
            if(StringUtil.isEmpty(acOperator.getUserId())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            //判断userId的唯一性
            if(acOperatorService.count(new WhereCondition().andEquals("USER_ID", acOperator.getUserId())) > 0) {
                throw new OperatorManagementException(ACExceptionCodes.USER_ID_IS_ALREADY_EXIST, BasicUtil.wrap("USER_ID"));
            }
            //PASSWORD 必填
            if(StringUtil.isEmpty(acOperator.getPassword())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("PASSWORD"));
            }
            //操作员姓名: OperatorName 必填;
            if(StringUtil.isEmpty(acOperator.getOperatorName())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("OPERATOR_NAME"));
            }
            //操作员状态: operatorStatus 必填
            if(StringUtil.isEmpty(acOperator.getOperatorStatus())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("OPERATOR_STATUS"));
            }
            // 新建 操作员 后台处理
            // 1.设置GUID
            // 2.设置当前错误登录次数为0
            // 3.密码加密

            acOperator.setGuid(GUID.operaor());
            acOperator.setErrCount(new BigDecimal("0"));
            acOperator.setPassword(CryptographyUtil.md5(acOperator.getPassword()));
            acOperatorService.insert(acOperator);
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_CREATE_AC_OPERATOR,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 修改操作员
     *
     * @param acOperator
     * @throws OperatorManagementException
     */
    @Override
    public void editOperator(AcOperator acOperator) throws OperatorManagementException {
        try {
            if(null == acOperator) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("acOperator"));
            }
            //USER_ID 必填
            if(StringUtil.isEmpty(acOperator.getUserId())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            //判断userId的唯一性
            if(acOperatorService.count(new WhereCondition().andEquals("USER_ID", acOperator.getUserId())) > 0) {
                throw new OperatorManagementException(ACExceptionCodes.USER_ID_IS_ALREADY_EXIST, BasicUtil.wrap("USER_ID"));
            }
            //PASSWORD 必填
            if(StringUtil.isEmpty(acOperator.getPassword())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("PASSWORD"));
            }
            //操作员姓名: OperatorName 必填;
            if(StringUtil.isEmpty(acOperator.getOperatorName())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("OPERATOR_NAME"));
            }
            //操作员状态: operatorStatus 必填
            if(StringUtil.isEmpty(acOperator.getOperatorStatus())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("OPERATOR_STATUS"));
            }
            acOperatorService.update(acOperator);
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_OPERATOR, BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 删除操作员
     *
     * @param operatorGuid 操作员GUID
     * @throws OperatorManagementException
     */
    @Override
    public void deleteOperator(String operatorGuid) throws OperatorManagementException {
        try {
            if(StringUtil.isEmpty(operatorGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_OPERATOR"));
            }
            /* 查询操作员相关，一并删除
            * 1.操作员身份和操作员身份权限集
            * 2.操作员重组菜单
            * 3.操作员快捷菜单
            * 4.操作员特殊功能行为配置
            * 5.操作员特殊权限配置
            * 6.操作员与权限集对应关系
            * 7.操作员个性配置
            *
            * 删除操作员
            */
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        WhereCondition commonWc = new WhereCondition().andEquals("GUID_OPERATOR", operatorGuid);
                        // 1.操作员身份和操作员身份权限集
                        List<AcOperatorIdentity> operIdentList = acOperatorIdentityService.query(commonWc);
                        if(operIdentList.size() > 0) {
                            List<String> operIdenGuids = BasicUtil.getValueListByKey(operIdentList, AcOperatorIdentity.class, "guid");
                            if(operIdenGuids.size() > 0) {
                                acOperatorIdentityresService.deleteByCondition(new WhereCondition().andIn("GUID_IDENTITY", operIdenGuids));
                                acOperatorIdentityService.deleteByCondition(new WhereCondition().andIn("GUID", operIdenGuids));
                            }
                        }
                        // 2.操作员重组菜单
                        acOperatorMenuService.deleteByCondition(commonWc);
                        // 3.操作员快捷菜单
                        acOperatorShortcutService.deleteByCondition(commonWc);
                        // 4.操作员特殊功能行为配置
                        acOperatorBhvService.deleteByCondition(commonWc);
                        // 5.操作员特殊权限配置
                        acOperatorFuncService.deleteByCondition(commonWc);
                        // 6.操作员与权限集对应关系
                        acOperatorRoleService.deleteByCondition(commonWc);
                        // 7.操作员个性配置
                        acOperatorConfigService.deleteByCondition(commonWc);
                        // 删除操作员
                        acOperatorService.delete(operatorGuid);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR, BasicUtil.wrap(e.getCause().getMessage()));
                    }
                }
            });
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR, BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 查询操作员列表
     *
     * @return 操作员对象集合
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperator> queryOperators() throws OperatorManagementException {
        List<AcOperator> acOperators = new ArrayList<>();
        try {
            acOperators = acOperatorService.query(new WhereCondition());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_AC_OPERATOR,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
        return acOperators;
    }

    /**
     * 查询操作员对应的身份集合
     *
     * @param operatorGuid
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperatorIdentity> queryOperatorIdentities(String operatorGuid) throws OperatorManagementException {
        if(StringUtil.isEmpty(operatorGuid)) {
            throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_OPERATOR"));
        }
        List<AcOperatorIdentity> acOperatorIdentityList = new ArrayList<>();
        try {
            WhereCondition wc = new WhereCondition();
            wc.andEquals("GUID_OPERATOR", operatorGuid);
            wc.setOrderBy("SEQ_NO");
            acOperatorIdentityList = acOperatorIdentityService.query(wc);
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITY,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
        return acOperatorIdentityList;
    }

    /**
     * 新增操作员身份
     *
     * @param operatorIdentity
     * @throws OperatorManagementException
     */
    @Override
    public void createOperatorIdentity(AcOperatorIdentity operatorIdentity) throws OperatorManagementException {
        try {
            if (null == operatorIdentity) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("AC_OPERATOR_IDENTITY"));
            }
            if (StringUtil.isEmpty(operatorIdentity.getGuidOperator())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_OPERATOR"));
            }
            if (StringUtil.isEmpty(operatorIdentity.getIdentityName())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("IDENTITY_NAME"));
            }
            // TODO 序列必填？
            operatorIdentity.setGuid(GUID.identity());
            acOperatorIdentityService.insert(operatorIdentity);
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                ACExceptionCodes.FAILURE_WHEN_CREATE_AC_OPERATOR_IDENTITY,
                BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 修改操作员身份
     *
     * @param operatorIdentity
     * @throws OperatorManagementException
     */
    @Override
    public void editOperatorIdentity(AcOperatorIdentity operatorIdentity) throws OperatorManagementException {
        try {
            if(null == operatorIdentity) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("AcOperatorIdentity"));
            }
            acOperatorIdentityService.update(operatorIdentity);
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_OPERATOR_IDENTITY, BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 删除操作员身份
     *
     * @param operatorIdenGuid
     * @throws OperatorManagementException
     */
    @Override
    public void deleteOperatorIdentity(String operatorIdenGuid) throws OperatorManagementException {
        try {
            if(StringUtil.isEmpty(operatorIdenGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_OPERATORIDENTITY"));
            }
            /* 查询操作员身份相关，一并删除操作员身份和操作员身份权限集
            */
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        acOperatorIdentityresService.deleteByCondition(new WhereCondition().andEquals("GUID_IDENTITY", operatorIdenGuid));
                        acOperatorIdentityService.delete(operatorIdenGuid);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITY, BasicUtil.wrap(e.getCause().getMessage()));
                    }
                }
            });
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITY, BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 设置默认操作员身份
     *
     * @param operatorIdenGuid
     * @throws OperatorManagementException
     */
    @Override
    public void setDefaultOperatorIdentity(String operatorIdenGuid) throws OperatorManagementException {
        try {
            if(StringUtil.isEmpty(operatorIdenGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_OPERATORIDENTITY"));
            }

            List<AcOperatorIdentity> list = acOperatorIdentityService.query(new WhereCondition().andEquals("GUID", operatorIdenGuid));
            if(list.size() != 1) {
                throw new OperatorManagementException(ACExceptionCodes.AC_OPERATOR_IDENTITY_IS_NOT_EXIST, BasicUtil.wrap(operatorIdenGuid));
            }

            AcOperatorIdentity acOperatorIdentity = list.get(0);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        AcOperatorIdentity identity = new AcOperatorIdentity();
                        identity.setIdentityFlag(CommonConstants.NO);
                        //设置当前操作员所有身份的flag为NO
                        acOperatorIdentityService.updateByCondition(new WhereCondition().andEquals("GUID_OPERATOR", acOperatorIdentity.getGuidOperator()), identity);
                        //设置当前操作员需要设定默认身份的flag为YES
                        identity.setIdentityFlag(CommonConstants.YES);
                        acOperatorIdentityService.updateByCondition(new WhereCondition().andEquals("GUID", operatorIdenGuid), identity);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ACExceptionCodes.FAILURE_WHEN_SET_DEFAULT_AC_OPERATOR_IDENTITY, BasicUtil.wrap(e.getCause().getMessage()));
                    }
                }
            });
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_SET_DEFAULT_AC_OPERATOR_IDENTITY, BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 新增操作员身份权限
     *
     * @param operatorIdentityres
     * @throws OperatorManagementException
     */
    @Override
    public void createOperatorIdentityres(AcOperatorIdentityres operatorIdentityres) throws OperatorManagementException {
        try {
            if (null == operatorIdentityres) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("AC_OPERATOR_IDENTITYRES"));
            }
            if (StringUtil.isEmpty(operatorIdentityres.getGuidIdentity())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_IDENTITY"));
            }
            if (StringUtil.isEmpty(operatorIdentityres.getAcResourcetype())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("AC_RESOURCE_TYPE"));
            }
            if (StringUtil.isEmpty(operatorIdentityres.getGuidAcResource())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_AC_RESOURCE"));
            }
            acOperatorIdentityresService.insert(operatorIdentityres);
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_CREATE_AC_OPERATOR_IDENTITYRES,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 修改操作员身份权限
     *
     * @param operatorIdentityres
     * @throws OperatorManagementException
     */
    @Override
    public void editOperatorIdentityres(AcOperatorIdentityres operatorIdentityres) throws OperatorManagementException {

    }

    /**
     * 删除操作员身份权限
     *
     * @param operatorIdentityresGuid
     * @throws OperatorManagementException
     */
    @Override
    public void deleteOperatorIdentityres(String operatorIdentityresGuid) throws OperatorManagementException {

    }

    /**
     * 查询操作员身份对应的权限集合
     *
     * @param operatorIdentityGuid
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperatorIdentityres> queryOperatorIdentityreses(String operatorIdentityGuid) throws OperatorManagementException {
        return null;
    }

    /**
     * 通过USER_ID 和 OPERATOR_NAME 查询 操作员身份列表
     *
     * @param userId       操作员登录名
     * @param operatorName 操作员姓名
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperatorIdentity> queryOperatorIdentitiesByUserIdAndName(String userId, String operatorName) throws OperatorManagementException {
        try {
            if (StringUtil.isEmpty(userId)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_IDENTITY"));
            }
            if (StringUtil.isEmpty(operatorName)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("AC_RESOURCE_TYPE"));
            }
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId).andEquals("OPERATOR_NAME", operatorName));
            if(acOperators == null || acOperators.size() != 1) {
                throw new OperatorManagementException(ACExceptionCodes.AC_OPERATOR_IS_NOT_FOUND, BasicUtil.wrap(userId, operatorName));
            }
            AcOperator acOperator = acOperators.get(0);
            return queryOperatorIdentities(acOperator.getGuid());
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITY,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }
}
