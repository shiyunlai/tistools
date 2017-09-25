package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.BeanFieldValidateUtil;
import org.tis.tools.common.utils.CryptographyUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.vo.ac.AcOperatorFuncDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.OperatorManagementException;
import org.tis.tools.service.ac.*;
import org.tis.tools.service.ac.exception.ACExceptionCodes;
import org.tis.tools.service.om.OmEmployeeService;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    AcRoleService acRoleService;

    @Autowired
    OmEmployeeService omEmployeeService;

    @Autowired
    IRoleRService roleRService;

    @Autowired
    AcOperatorServiceExt acOperatorServiceExt;

    @Autowired
    IApplicationRService applicationRService;

    @Autowired
    AcFuncgroupService acFuncgroupService;

    @Autowired
    AcRoleFuncService acRoleFuncService;

    @Autowired
    AcFuncServiceExt acFuncServiceExt;

    @Autowired
    AcConfigService acConfigService;

    @Autowired
    AcOperatorConfigService acOperatorConfigService;

    @Autowired
    AcFuncBhvService acFuncBhvService;

    @Autowired
    AcBhvDefService acBhvDefService;


    /**
     * 新增操作员
     *
     * @param acOperator
     * @throws OperatorManagementException
     */
    @Override
    public void createOperator(AcOperator acOperator) throws OperatorManagementException {
        try {
            if (null == acOperator) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("acOperator"));
            }
            // 校验传入参数
            // 登陆用户名:userId’;   密码:’passWord’;   操作员姓名:’operatorName’;
            // 操作员状态: ‘operatorStatus;  密码失效日:’invalDate’ ;  认证模式:’authMode’ ;
            // 锁定次数限制:lockLimit;  当前错误登录次数:’errCount’ ;  锁定时间:’lockTime’ ;
            // 解锁时间:’unlockTime’ ;  菜单风格:’menuType’; 最近登录时间:’lastLogin’; 有效开始日期:’startDate’;
            // 有效截止日期:’endDate’; 允许时间范围:’validTime’; 允许MAC码:’macCode’ ; 允许IP地址:’ipAddRess’

            //USER_ID 必填
            if (StringUtil.isEmpty(acOperator.getUserId())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            //判断userId的唯一性
            if (acOperatorService.count(new WhereCondition().andEquals("USER_ID", acOperator.getUserId())) > 0) {
                throw new OperatorManagementException(ACExceptionCodes.USER_ID_IS_ALREADY_EXIST, BasicUtil.wrap("USER_ID"));
            }
            //PASSWORD 必填
            if (StringUtil.isEmpty(acOperator.getPassword())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("PASSWORD"));
            }
            //操作员姓名: OperatorName 必填;
            if (StringUtil.isEmpty(acOperator.getOperatorName())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("OPERATOR_NAME"));
            }
            //操作员状态: operatorStatus 必填
            if (StringUtil.isEmpty(acOperator.getOperatorStatus())) {
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
            if (null == acOperator) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("acOperator"));
            }
            //USER_ID 必填
            if (StringUtil.isEmpty(acOperator.getUserId())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            if (StringUtil.isEmpty(acOperator.getGuid())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID"));
            }
            //判断userId的唯一性
            if (acOperatorService.count(new WhereCondition()
                    .andNotEquals(AcOperator.COLUMN_GUID, acOperator.getGuid())
                    .andEquals("USER_ID", acOperator.getUserId())) > 0) {
                throw new OperatorManagementException(ACExceptionCodes.USER_ID_IS_ALREADY_EXIST, BasicUtil.wrap("USER_ID"));
            }
            //PASSWORD 必填
            if (StringUtil.isEmpty(acOperator.getPassword())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("PASSWORD"));
            }
            //操作员姓名: OperatorName 必填;
            if (StringUtil.isEmpty(acOperator.getOperatorName())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("OPERATOR_NAME"));
            }
            //操作员状态: operatorStatus 必填
            if (StringUtil.isEmpty(acOperator.getOperatorStatus())) {
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
            if (StringUtil.isEmpty(operatorGuid)) {
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
                        if (operIdentList.size() > 0) {
                            List<String> operIdenGuids = BasicUtil.getValueListByKey(operIdentList, AcOperatorIdentity.class, "guid");
                            if (operIdenGuids.size() > 0) {
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
                        acConfigService.deleteByCondition(commonWc);
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
        if (StringUtil.isEmpty(operatorGuid)) {
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
            if (null == operatorIdentity) {
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
            if (StringUtil.isEmpty(operatorIdenGuid)) {
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
            if (StringUtil.isEmpty(operatorIdenGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_OPERATORIDENTITY"));
            }

            List<AcOperatorIdentity> list = acOperatorIdentityService.query(new WhereCondition().andEquals("GUID", operatorIdenGuid));
            if (list.size() != 1) {
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
     * @param operatorIdentityresList
     * @throws OperatorManagementException
     */
    @Override
    public void createOperatorIdentityres(List<AcOperatorIdentityres> operatorIdentityresList) throws OperatorManagementException {
        try {

            if (CollectionUtils.isEmpty(operatorIdentityresList)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("operatorIdentityresList", "AC_OPERATOR_IDENTITYRES"));
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for (AcOperatorIdentityres operatorIdentityres : operatorIdentityresList) {
                            if (StringUtil.isEmpty(operatorIdentityres.getGuidIdentity())) {
                                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_IDENTITY"));
                            }
                            if (StringUtil.isEmpty(operatorIdentityres.getAcResourcetype())) {
                                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("AC_RESOURCETYPE"));
                            }
                            if (StringUtil.isEmpty(operatorIdentityres.getGuidAcResource())) {
                                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_AC_RESOURCE"));
                            }
                            if (acOperatorIdentityresService.count(new WhereCondition()
                                    .andEquals("GUID_IDENTITY", operatorIdentityres.getGuidIdentity())
                                    .andEquals("AC_RESOURCETYPE", operatorIdentityres.getAcResourcetype())
                                    .andEquals("GUID_AC_RESOURCE", operatorIdentityres.getGuidAcResource())) > 0) {
                                throw new OperatorManagementException(ExceptionCodes.OBJECT_IS_ALREADY_EXIST, BasicUtil.wrap("GUID_AC_RESOURCE"));
                            }
                            acOperatorIdentityresService.insert(operatorIdentityres);
                        }

                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("AC_OPERATOR_IDENTITYRES", e.getCause().getMessage()));
                    }
                }
            });
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("AC_OPERATOR_IDENTITYRES", e.getCause().getMessage()));
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
     * @param identityresList 身份权限资源集合，每个资源包含资源类型GUID和资源GUID
     * @throws OperatorManagementException
     */
    @Override
    public void deleteOperatorIdentityres(List<AcOperatorIdentityres> identityresList) throws OperatorManagementException {
        try {
            if(CollectionUtils.isEmpty(identityresList)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("Object", "AC_OPERATOR_IDENTITYRES"));
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for(AcOperatorIdentityres identityres : identityresList) {
                            if (StringUtils.isBlank(identityres.getGuidIdentity())) {
                                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, BasicUtil.wrap(AcOperatorIdentityres.COLUMN_GUID_IDENTITY, "AC_OPERATOR_IDENTITYRES"));
                            }
                            if (StringUtils.isBlank(identityres.getGuidAcResource())) {
                                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, BasicUtil.wrap(AcOperatorIdentityres.COLUMN_GUID_AC_RESOURCE, "AC_OPERATOR_IDENTITYRES"));
                            }
                            acOperatorIdentityresService.deleteByCondition(new WhereCondition()
                                    .andEquals(AcOperatorIdentityres.COLUMN_GUID_IDENTITY, identityres.getGuidIdentity())
                                    .andEquals(AcOperatorIdentityres.COLUMN_GUID_AC_RESOURCE, identityres.getGuidAcResource()));
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_OPERATOR_IDENTITYRES", e.getMessage()));
                    }
                }
            });
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITYRES,
                    BasicUtil.wrap(e.getMessage()));
        }
    }

    /**
     * 查询操作员身份对应的权限集合
     *
     * @param operatorIdentityGuid
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<Map> queryOperatorIdentityreses(String operatorIdentityGuid) throws OperatorManagementException {
        try {
            if (StringUtil.isEmpty(operatorIdentityGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_IDENTITY"));
            }
            return acOperatorServiceExt.queryOperatorIdentityreses(operatorIdentityGuid);
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITY,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 通过USER_ID 查询操作员身份列表
     *
     * @param userId 操作员登录名
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperatorIdentity> queryOperatorIdentitiesByUserId(String userId) throws OperatorManagementException {
        try {
            if (StringUtil.isEmpty(userId)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_IDENTITY"));
            }
            return queryOperatorIdentities(queryOperatorByUserId(userId).getGuid());
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITY,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }


    /**
     * 查询操作员不同资源类型下的所有角色
     *
     * @param operatorGuid
     * @param resType
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcRole> queryOperatorRoleByResType(String operatorGuid, String resType) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(operatorGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("GUID_OPERATOR", "AC_ROLE"));
            }
            if (StringUtils.isBlank(resType)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("RES_TYPE", "AC_ROLE"));
            }
            List<AcRole> acRoleList = new ArrayList<>();
            AcOperator operator = acOperatorService.loadByGuid(operatorGuid);
            if (null == operator) {
                return acRoleList;
            }
            // 来自角色的权限资源和员工不相关
            List<OmEmployee> omEmployees = omEmployeeService.query(new WhereCondition().andEquals("USER_ID", operator.getUserId()));
            OmEmployee employee = new OmEmployee();
            if (!StringUtils.isEquals(resType, ACConstants.RESOURCE_TYPE_ROLE)) {
                if (CollectionUtils.isEmpty(omEmployees)) {
                    return acRoleList;
                }
                employee = omEmployees.get(0);
            }

            switch (resType) {
                case ACConstants.RESOURCE_TYPE_ROLE:
                    List<AcOperatorRole> acOperatorRoles = acOperatorRoleService.query(new WhereCondition().andEquals("GUID_OPERATOR", operatorGuid));
                    List<String> roleGuids = new ArrayList<>();
                    for (AcOperatorRole acOperatorRole : acOperatorRoles) {
                        roleGuids.add(acOperatorRole.getGuidRole());
                    }
                    if (roleGuids.size() > 0) {
                        acRoleList = acRoleService.query(new WhereCondition().andIn("GUID", roleGuids));
                    }
                    break;
                case ACConstants.RESOURCE_TYPE_FUNCTION:
                    // TODO 功能权限待完成
                    break;
                case ACConstants.RESOURCE_TYPE_ORGANIZATION:
                    acRoleList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_ORGANIZATION, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_POSITION:
                    acRoleList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_POSITION, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_DUTY:
                    acRoleList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_DUTY, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_WORKGROUP:
                    acRoleList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_WORKGROUP, employee.getGuid());
                    break;
                default:
                    throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap("AC_RESOURCE_TYPE " + resType, "AC_ROLE"));
            }
            return acRoleList;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_OPERATOR_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * 用户状态修改
     *
     * @param userId
     * @param status
     * @throws OperatorManagementException
     */
    @Override
    public void updateUserStatus(String userId, String status) throws OperatorManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            if (StringUtil.isEmpty(status)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("STATUS"));
            }
            // 判断用户是否存在
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if (acOperators.size() != 1) {
                throw new OperatorManagementException(ACExceptionCodes.USER_ID_NOT_EXIST, BasicUtil.wrap(userId));
            }
            AcOperator acOperator = acOperators.get(0);

            AcOperator newOperator = new AcOperator();
            newOperator.setGuid(acOperator.getGuid());
            // TODO 更改状态附加业务逻辑

            // 如果当前状态为锁定
            if (StringUtils.isEquals(acOperator.getOperatorStatus(), ACConstants.OPERATE_STATUS_LOCK)) {
                newOperator.setLockTime(null); // 清楚锁定时间
                newOperator.setErrCount(new BigDecimal("0")); // 错误次数置0
            } else {
                newOperator.setOperatorStatus(status); // 更改状态
            }
            acOperatorService.update(newOperator);
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE,
                    BasicUtil.wrap("AC_OPERATOR", e.getCause().getMessage()));
        }
    }


    /**
     * 根据用户名查询操作员信息
     *
     * @param userId
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public AcOperator queryOperatorByUserId(String userId) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(userId)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("USER_ID", "AC_ROLE"));
            }
            List<AcOperator> operatorList = acOperatorService.query(new WhereCondition().andEquals(AcOperator.COLUMN_USER_ID, userId));// 查询用户对应的操作员信息
            if (CollectionUtils.isEmpty(operatorList)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap("USER_ID " + userId, "AC_OPERATOR"));
            }

            AcOperator acOperator = new AcOperator();
            acOperator.setUserId(userId);
            acOperator.setGuid(operatorList.get(0).getGuid());
            return acOperator;

        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap("AC_OPERATOR", e.getCause().getMessage()));
        }
    }


    /**
     * 查询操作员特殊功能权限集
     * 来源  操作员对应员工的工作组和岗位相关应用下未授权给该操作员的功能
     * <p>
     * 业务逻辑  查询操作员已拥有应用的所有功能集合  操作员已授权和继承的所有角色拥有功能的并集 的差集
     * <p>
     * 场景  1.用于操作员分配特殊功能权限时展示功能列表
     *
     * @param userId 用户ID
     * @return 功能集合
     * @throws OperatorManagementException
     */
    @Override
    public AcOperatorFuncDetail queryOperatorFuncInfoInApp(String userId) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(userId)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("USER_ID", "queryOperatorFuncInfoInApp"));
            }
            /** 查询对应操作员*/
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals(AcOperator.COLUMN_USER_ID, userId));
            if (acOperators.size() != 1) {
                throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap(userId, "AC_OPERATOR"));
            }
            AcOperator operator = acOperators.get(0);
            // 创建根节点
            AcOperatorFuncDetail rootNode = new AcOperatorFuncDetail();
            rootNode.setId("root");
            rootNode.setText("特殊功能权限");
            rootNode.setIsLeaf(CommonConstants.NO);
            rootNode.setIcon(AcOperatorFuncDetail.NODE_ICON_ROOT);
            rootNode.setNodeType(AcOperatorFuncDetail.NODE_TYPE_ROOT);
            rootNode.setStatus(AcOperatorFuncDetail.NODE_STATUS_ENABLED);

            /** 查询用户下所有应用 */
            List<AcApp> acApps = applicationRService.queryOperatorAllApp(userId);
            /** 查询应用的所有功能 */
            if (acApps.size() > 0) {
                for (AcApp app : acApps) {
                    // 构造应用节点
                    AcOperatorFuncDetail appNode = new AcOperatorFuncDetail();
                    appNode.setId(app.getGuid());
                    appNode.setText(app.getAppName());
                    appNode.setParentGuid(rootNode.getId());
                    appNode.setIcon(AcOperatorFuncDetail.NODE_ICON_APP);
                    appNode.setNodeType(AcOperatorFuncDetail.NODE_TYPE_APP);
                    appNode.setStatus(AcOperatorFuncDetail.NODE_STATUS_ENABLED);
                    appNode.setAppGuid(app.getGuid());
                    appNode.setIsLeaf(CommonConstants.NO);
                    rootNode.addChildren(appNode);

                    List<AcOperatorFuncDetail> nodeList = new ArrayList<>();
                    // 节点列表（散列表，用于临时存储节点对象）
                    HashMap<String, AcOperatorFuncDetail> nodeMap = new HashMap<>();
                    // 查询所有的功能组
                    List<AcFuncgroup> funcgroups = acFuncgroupService.query(new WhereCondition().andEquals(AcFuncgroup.COLUMN_GUID_APP, app.getGuid()));
                    for (AcFuncgroup acFuncgroup : funcgroups) {
                        AcOperatorFuncDetail groupNode = new AcOperatorFuncDetail();
                        groupNode.setId(acFuncgroup.getGuid());
                        groupNode.setText(acFuncgroup.getFuncgroupName());
                        groupNode.setParentGuid(acFuncgroup.getGuidParents());
                        groupNode.setIcon(AcOperatorFuncDetail.NODE_ICON_FUNCGROUP);
                        groupNode.setNodeType(AcOperatorFuncDetail.NODE_TYPE_FUNCGROUP);
                        groupNode.setStatus(AcOperatorFuncDetail.NODE_STATUS_ENABLED);
                        groupNode.setIsLeaf(CommonConstants.NO);
                        groupNode.setAppGuid(acFuncgroup.getGuidApp());
                        groupNode.setFuncGroupGuid(acFuncgroup.getGuidParents());
                        nodeList.add(groupNode);
                        nodeMap.put(groupNode.getId(), groupNode);
                    }
                    //查询当前用户拥有该应用的功能对应菜单
                    List<AcRole> acRoleList = roleRService.queryAllRoleByUserId(userId);
                    Set<String> funcGuidList = new HashSet<>(); // 功能GUID
                    Set<String> roleGuidList = new HashSet<>(); // 角色GUID
                    for (AcRole acRole : acRoleList) {
                        roleGuidList.add(acRole.getGuid());
                    }
                    if (roleGuidList.size() > 0) {
                        // 获取角色下的功能列表
                        List<AcRoleFunc> acRoleFuncs = acRoleFuncService.query(new WhereCondition()
                                .andEquals(AcRoleFunc.COLUMN_GUID_APP, app.getGuid())
                                .andIn(AcRoleFunc.COLUMN_GUID_ROLE, new ArrayList<>(roleGuidList)));
                        for (AcRoleFunc roleFunc : acRoleFuncs) {
                            funcGuidList.add(roleFunc.getGuidFunc());
                        }
                        // 获取角色下的特殊功能列表
                        List<AcOperatorFunc> acOperatorFuncs = acOperatorFuncService.query(new WhereCondition()
                                .andEquals(AcOperatorFunc.COLUMN_GUID_APP, app.getGuid())
                                .andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, operator.getGuid()));
                        for (AcOperatorFunc operatorFunc : acOperatorFuncs) {
                            funcGuidList.add(operatorFunc.getGuidFunc());
                        }
                    }
                    // 查询所有功能
                    List<AcFunc> acFuncs = acFuncServiceExt.queryFuncListInApp(app.getGuid());
                    for (AcFunc acFunc : acFuncs) {
                        AcOperatorFuncDetail funcNode = new AcOperatorFuncDetail();
                        funcNode.setId(acFunc.getGuid());
                        funcNode.setText(acFunc.getFuncName());
                        funcNode.setParentGuid(acFunc.getGuidFuncgroup());
                        funcNode.setIcon(AcOperatorFuncDetail.NODE_ICON_FUNC);
                        funcNode.setNodeType(AcOperatorFuncDetail.NODE_TYPE_FUNC);
                        funcNode.setStatus(AcOperatorFuncDetail.NODE_STATUS_DISABLED);
                        funcNode.setIsLeaf(CommonConstants.YES);
                        funcNode.setFuncGroupGuid(acFunc.getGuidFuncgroup());
                        funcNode.setAppGuid(nodeMap.get(acFunc.getGuidFuncgroup()).getAppGuid());
                        //如果角色已经有该功能权限
                        if(!funcGuidList.contains(acFunc.getGuid())) {
                            funcNode.setStatus(AcOperatorFuncDetail.NODE_STATUS_ENABLED);
                        }
                        nodeList.add(funcNode);
                        nodeMap.put(funcNode.getId(), funcNode);
                    }

                    // 构造功能树节点
                    for (AcOperatorFuncDetail node : nodeList) {
                        // 如果节点的父节点为空，则为应用下的子节点
                        if (StringUtils.isBlank(node.getParentGuid())) {
                            appNode.addChildren(node);
                        } else { // 否则获取父节点添加
                            nodeMap.get(node.getParentGuid()).addChildren(node);
                        }
                    }

                    // 查询已拥有功能


                }
            }
            return rootNode;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_APP", e.getCause().getMessage()));
        }
    }

    /**
     * 查询用户的特殊权限列表
     *
     * @param userId 用户名
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<Map> queryAcOperatorFunListByUserId(String userId) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(userId)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("USER_ID", "AC_OPERATOR_FUNC"));
            }
            AcOperator acOperator = queryOperatorByUserId(userId);
            return acOperatorServiceExt.queryOperatorFuncDetail(acOperator.getGuid());

        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap("AC_OPERATOR_FUNC", e.getCause().getMessage()));
        }
    }

    /**
     * 添加特殊权限
     *
     * @param acOperatorFunc
     * @throws OperatorManagementException
     */
    @Override
    public void addAcOperatorFun(AcOperatorFunc acOperatorFunc) throws OperatorManagementException {
        try {
            if (null == acOperatorFunc) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("acOperatorFunc", "AC_OPERATOR_FUNC"));
            }
            if (StringUtils.isBlank(acOperatorFunc.getGuidOperator())) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap(AcOperatorFunc.COLUMN_GUID_OPERATOR, "AC_OPERATOR_FUNC"));
            }
            if (StringUtils.isBlank(acOperatorFunc.getGuidFunc())) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap(AcOperatorFunc.COLUMN_GUID_FUNC, "AC_OPERATOR_FUNC"));
            }
            if (StringUtils.isBlank(acOperatorFunc.getGuidFuncgroup())) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap(AcOperatorFunc.COLUMN_GUID_FUNCGROUP, "AC_OPERATOR_FUNC"));
            }
            if (StringUtils.isBlank(acOperatorFunc.getGuidApp())) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap(AcOperatorFunc.COLUMN_GUID_APP, "AC_OPERATOR_FUNC"));
            }
            if (StringUtils.isBlank(acOperatorFunc.getAuthType())) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap(AcOperatorFunc.COLUMN_AUTH_TYPE, "AC_OPERATOR_FUNC"));
            }

            acOperatorFuncService.insert(acOperatorFunc);

        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap("AC_OPERATOR_FUNC", e.getCause().getMessage()));
        }

    }

    /**
     * 移除特殊权限
     *
     * @param operatorGuid 操作员GUID
     * @param funcGuid     功能GUID
     * @throws OperatorManagementException
     */
    @Override
    public void removeAcOperatorFun(String operatorGuid, String funcGuid) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(operatorGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap(AcOperatorFunc.COLUMN_GUID_OPERATOR, "AC_OPERATOR_FUNC"));
            }
            if (StringUtils.isBlank(funcGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap(AcOperatorFunc.COLUMN_GUID_FUNC, "AC_OPERATOR_FUNC"));
            }

            acOperatorFuncService.deleteByCondition(new WhereCondition().andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, operatorGuid)
                .andEquals(AcOperatorFunc.COLUMN_GUID_FUNC, funcGuid));

        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_OPERATOR_FUNC", e.getCause().getMessage()));
        }
    }

    /**
     * 查询所有个性化配置
     *
     * @return 配置集合
     * @throws OperatorManagementException
     */
    @Override
    public List<AcConfig> queryConfigList() throws OperatorManagementException {
        try {
            return acConfigService.query(new WhereCondition());
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap(AcConfig.TABLE_NAME, e.getCause().getMessage()));
        }
    }

    /**
     * 新增个性化配置
     *
     * @param config
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public AcConfig addConfig(AcConfig config) throws OperatorManagementException {
        if(config == null) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("config", AcConfig.TABLE_NAME));
        }
        try {
            config.setGuid(GUID.operatorConfig());
            String validate = BeanFieldValidateUtil.checkObjFieldNotRequired(config, new String[]{"configDesc, displayOrder"});
            if(StringUtils.isNotEmpty(validate)) {
                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap(validate, AcConfig.TABLE_NAME));
            }
            if(acConfigService.count(new WhereCondition()
                    .andEquals(AcConfig.COLUMN_GUID_APP, config.getGuidApp())
                    .andEquals(AcConfig.COLUMN_CONFIG_TYPE, config.getConfigType())
                    .andEquals(AcConfig.COLUMN_CONFIG_NAME, config.getConfigName())) > 0) {
                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, BasicUtil.wrap(config.getConfigName(), AcConfig.TABLE_NAME));
            }
            config.setDisplayOrder(new BigDecimal(acConfigService.count(new WhereCondition()
                    .andEquals(AcConfig.COLUMN_GUID_APP, config.getGuidApp())
                    .andEquals(AcConfig.COLUMN_CONFIG_TYPE, config.getConfigType()))));
            acConfigService.insert(config);
            return config;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap(AcConfig.TABLE_NAME, e.getMessage()));
        }
    }

    /**
     * 批量删除个性化配置
     *
     * @param cfgList
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcConfig> deleteConfig(List<AcConfig> cfgList) throws OperatorManagementException {
        if(CollectionUtils.isEmpty(cfgList)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("configList", AcConfig.TABLE_NAME));
        }
        try {
            List<String> guids = new ArrayList<>();
            cfgList.stream().forEach(cfg -> {
                if(cfg == null) {
                    throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("acOperatorConfig", AcConfig.TABLE_NAME));
                }
                if(StringUtils.isBlank(cfg.getGuid())) {
                    throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, BasicUtil.wrap(AcConfig.COLUMN_GUID, AcConfig.TABLE_NAME));
                }
                guids.add(cfg.getGuid());
            });
            if(guids.size() > 0) {
                acConfigService.deleteByCondition(new WhereCondition().andIn(AcConfig.COLUMN_GUID, guids));
            }
            return cfgList;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap(AcConfig.TABLE_NAME, e));
        }
    }

    /**
     * 修改个性化配置
     *
     * @param config
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public AcConfig updateConfig(AcConfig config) throws OperatorManagementException {
        if(config == null) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("acOperatorConfig", AcConfig.TABLE_NAME));
        }
        try {
            String validate = BeanFieldValidateUtil.checkObjFieldNotRequired(config, new String[]{"configDesc", "displayOrder"});
            if(StringUtils.isNotEmpty(validate)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap(validate, AcConfig.TABLE_NAME));
            }
            if(acConfigService.count(new WhereCondition()
                    .andEquals(AcConfig.COLUMN_GUID_APP, config.getGuidApp())
                    .andEquals(AcConfig.COLUMN_CONFIG_TYPE, config.getConfigType())
                    .andEquals(AcConfig.COLUMN_CONFIG_NAME, config.getConfigName())
                    .andNotEquals(AcConfig.COLUMN_GUID, config.getGuid())) > 0) {
                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, BasicUtil.wrap(config.getConfigName(), AcConfig.TABLE_NAME));
            }
            acConfigService.update(config);
            return config;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE, BasicUtil.wrap(AcConfig.TABLE_NAME, e.getCause().getMessage()));
        }
    }

    /**
     * 保存操作员配置
     *
     * @param acOperatorConfig
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public AcOperatorConfig saveOperatorLog(AcOperatorConfig acOperatorConfig) throws OperatorManagementException {
        if(acOperatorConfig == null) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("acOperatorConfig", AcOperatorConfig.TABLE_NAME));
        }
        try {
            String validate = BeanFieldValidateUtil.checkObjFieldAllRequired(acOperatorConfig);
            if(StringUtils.isNotEmpty(validate)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap(validate, AcOperatorConfig.TABLE_NAME));
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        WhereCondition wc = new WhereCondition();
                        wc.andEquals(AcOperatorConfig.COLUMN_GUID_CONFIG, acOperatorConfig.getGuidConfig())
                                .andEquals(AcOperatorConfig.COLUMN_GUID_OPERATOR, acOperatorConfig.getGuidOperator());
                        acOperatorConfigService.deleteByCondition(wc);
                        acOperatorConfigService.insert(acOperatorConfig);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ExceptionCodes.FAILURE_WHEN_UPDATE, BasicUtil.wrap(AcOperatorConfig.TABLE_NAME, e.getMessage()));
                    }
                }
            });
            return acOperatorConfig;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE, BasicUtil.wrap(AcOperatorConfig.TABLE_NAME, e.getCause().getMessage()));
        }
    }

    /**
     * 查询操作员的个性化配置
     *
     * @param userId
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcConfig> queryOperatorConfig(String userId) throws OperatorManagementException {
        if(StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("UserId", AcOperatorConfig.TABLE_NAME)
            );
        }
        AcOperator operator = queryOperatorByUserId(userId);
        try {
            /**
             * 查询全部配置，与操作员修改的配置，替换默认配置中的已修改项
             */
            List<AcOperatorConfig> privateConfigs = acOperatorConfigService.query(
                    new WhereCondition().andEquals(AcOperatorConfig.COLUMN_GUID_OPERATOR, operator.getGuid()));
            List<AcConfig> defaultConfigs = queryConfigList();
            if(privateConfigs.size() > 0) {
                Map<String, String> matchVal = new HashMap<>();
                privateConfigs.stream().forEach(cfg ->
                    matchVal.put(cfg.getGuidConfig(), cfg.getConfigValue())
                );

                List<AcConfig> afterHandleConfigs = defaultConfigs.stream().map(cfg -> {
                    if (matchVal.containsKey(cfg.getGuid())) {
                        cfg.setConfigValue(matchVal.get(cfg.getGuid()));
                    }
                    return cfg;
                }).collect(Collectors.toList());

                return afterHandleConfigs;
            } else {
                return defaultConfigs;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap(AcOperatorConfig.TABLE_NAME, e.getCause().getMessage()));
        }
    }

    /**
     * 查询操作员在某功能的行为白名单和黑名单
     *
     * @param funGuid
     * @param userId
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public Map<String, Object> queryOperatorBhvListInFunc(String funGuid, String userId) throws OperatorManagementException {
        if(StringUtils.isBlank(funGuid)) {
            throw new OperatorManagementException(
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("funGuid", AcOperatorBhv.TABLE_NAME)
            );
        }
        if(StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("UserId", AcOperatorBhv.TABLE_NAME)
            );
        }
        String operatorGuid = queryOperatorByUserId(userId).getGuid();
        try {
            Map<String, Object> bhvMap = new HashMap<>();
            List<Map> whiteList = new ArrayList<>();
            List<Map> blackList = new ArrayList<>();

            // 查询功能下的所有行为定义
            List<Map> allBhvDef = applicationRService.queryAllBhvDefForFunc(funGuid);
            // 取出GUID
            List<String> guidList = allBhvDef.stream().map(map -> map.get("guid").toString()).collect(Collectors.toList());
            // 查询功能下的所有黑名单与所有功能定义对比
            if(guidList != null && guidList.size() > 0) {
                List<AcOperatorBhv> black = acOperatorBhvService.query(new WhereCondition()
                        .andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, operatorGuid)
                        .andIn(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, guidList));
                List<String> blackGuids = black.stream().map(AcOperatorBhv::getGuidFuncBhv).collect(Collectors.toList());
                whiteList = allBhvDef.stream().filter(m -> ! blackGuids.contains(m.get("guid"))).collect(Collectors.toList());
                blackList = allBhvDef.stream().filter(m -> blackGuids.contains(m.get("guid"))).collect(Collectors.toList());
            }
            bhvMap.put("whiteList", whiteList);
            bhvMap.put("blackList", blackList);
            return bhvMap;

        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap(AcOperatorBhv.TABLE_NAME, e));
        }
    }

    /**
     * 操作员功能行为添加黑名单
     *
     * @param operatorBhvList
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperatorBhv> addOperatorBhvBlackList(List<AcOperatorBhv> operatorBhvList) throws OperatorManagementException {
        if(CollectionUtils.isEmpty(operatorBhvList)) {
            throw new OperatorManagementException(
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
            );
        }
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for (AcOperatorBhv operatorBhv : operatorBhvList) {
                            if (operatorBhv == null) {
                                throw new OperatorManagementException(
                                        ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
                                );
                            }
                            String validate = BeanFieldValidateUtil.checkObjFieldNotRequired(operatorBhv, new String[]{"authType"});
                            if (StringUtils.isNotEmpty(validate)) {
                                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap(validate, AcOperatorBhv.TABLE_NAME));
                            }
                            if (acOperatorBhvService.count(new WhereCondition()
                                    .andEquals(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, operatorBhv.getGuidFuncBhv())
                                    .andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, operatorBhv.getGuidOperator())) > 0) {
                                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, BasicUtil.wrap(operatorBhv.getGuidFuncBhv(), AcOperatorBhv.TABLE_NAME));
                            }
                            acOperatorBhvService.insert(operatorBhv);

                        }
                    } catch (OperatorManagementException ae) {
                        status.setRollbackOnly();
                        throw ae;
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap(AcOperatorBhv.TABLE_NAME, e));
                    }
                }
            });
            return  operatorBhvList;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap(AcOperatorBhv.TABLE_NAME, e));
        }
    }

    /**
     * 操作员功能行为移除黑名单
     *
     * @param operatorBhvList
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperatorBhv> deleteOperatorBhvBlackList(List<AcOperatorBhv> operatorBhvList) throws OperatorManagementException {
        if(CollectionUtils.isEmpty(operatorBhvList)) {
            throw new OperatorManagementException(
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
            );
        }
        try {
            List<String> funcBhvGuids = operatorBhvList.stream().map(AcOperatorBhv::getGuidFuncBhv).distinct().collect(Collectors.toList());
            String operatorGuid = "";
            Optional<String> first = operatorBhvList.stream().map(AcOperatorBhv::getGuidOperator).findFirst();
            if (first.isPresent()) {
                operatorGuid = first.get();
            }
            if(StringUtils.isNotEmpty(operatorGuid) && funcBhvGuids.size() > 0) {
                acOperatorBhvService.deleteByCondition(new WhereCondition().andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, operatorGuid)
                .andIn(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, funcBhvGuids));
            } else {
                throw new OperatorManagementException(
                        ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, BasicUtil.wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
                );
            }
            return  operatorBhvList;
        } catch (OperatorManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap(AcOperatorBhv.TABLE_NAME, e));
        }
    }

}
