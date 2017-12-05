package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BeanFieldValidateUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.dto.shiro.PasswordHelper;
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
import java.util.stream.Stream;

import static org.tis.tools.common.utils.BasicUtil.surroundBracketsWithLFStr;
import static org.tis.tools.common.utils.BasicUtil.wrap;

@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = Exception.class)
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

    @Autowired
    AcFuncService acFuncService;

    @Autowired
    IAuthenticationRService authenticationRService;


    /**
     * 新增操作员 同时添加默认操作员
     *
     * @param acOperator
     * @throws OperatorManagementException
     */
    @Override
    public AcOperator createOperator(AcOperator acOperator) throws OperatorManagementException {
        try {
            if (null == acOperator) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, wrap("acOperator"));
            }
            // 校验传入参数
            // 登陆用户名:userId’;   密码:’passWord’;   操作员姓名:’operatorName’;
            // 操作员状态: ‘operatorStatus;  密码失效日:’invalDate’ ;  认证模式:’authMode’ ;
            // 锁定次数限制:lockLimit;  当前错误登录次数:’errCount’ ;  锁定时间:’lockTime’ ;
            // 解锁时间:’unlockTime’ ;  菜单风格:’menuType’; 最近登录时间:’lastLogin’; 有效开始日期:’startDate’;
            // 有效截止日期:’endDate’; 允许时间范围:’validTime’; 允许MAC码:’macCode’ ; 允许IP地址:’ipAddRess’

            //USER_ID 必填
            if (StringUtil.isEmpty(acOperator.getUserId())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("USER_ID"));
            }
            //判断userId的唯一性
            if (acOperatorService.count(new WhereCondition().andEquals("USER_ID", acOperator.getUserId())) > 0) {
                throw new OperatorManagementException(ACExceptionCodes.USER_ID_IS_ALREADY_EXIST, wrap("USER_ID"));
            }
            //PASSWORD 必填
            if (StringUtil.isEmpty(acOperator.getPassword())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("PASSWORD"));
            }
            //操作员姓名: OperatorName 必填;
            if (StringUtil.isEmpty(acOperator.getOperatorName())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("OPERATOR_NAME"));
            }
            //操作员状态: 新增默认为停用
            // 1.设置GUID
            // 2.设置当前错误登录次数为0
            // 3.密码加密
            acOperator.setOperatorStatus(ACConstants.OPERATE_STATUS_STOP);
            acOperator.setGuid(GUID.operaor());
            acOperator.setErrCount(new BigDecimal("0"));
            acOperator.setPassword(PasswordHelper.generate(acOperator.getPassword(), acOperator.getGuid()));
            acOperatorService.insert(acOperator);
            // 新增默认身份 该身份不允许被修改，被删除
            AcOperatorIdentity acOperatorIdentity = new AcOperatorIdentity();
            acOperatorIdentity.setGuid(GUID.identity());
            acOperatorIdentity.setIdentityName("系统默认身份"); // 固定名称
            acOperatorIdentity.setIdentityFlag(CommonConstants.YES);
            acOperatorIdentity.setGuidOperator(acOperator.getGuid());
            acOperatorIdentityService.insert(acOperatorIdentity);
            return sensitiveInfoProcess(acOperator);
        } catch (OperatorManagementException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_CREATE_AC_OPERATOR,
                    wrap(e));
        }
    }

    /**
     * 修改操作员
     *
     * @param acOperator
     * @throws OperatorManagementException
     */
    @Override
    public AcOperator editOperator(AcOperator acOperator) throws OperatorManagementException {
        try {
            if (acOperator == null) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, wrap("acOperator"));
            }
            // TODO 验证不全
            String[] validateFields = {
                    "guid", "operatorName", "authMode"
            };
            String result = BeanFieldValidateUtil.checkObjFieldRequired(acOperator, validateFields);
            //USER_ID 必填
            if (!StringUtils.isBlank(result)) {
                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, wrap(result, AcOperator.TABLE_NAME));
            }
            if (acOperatorService.count(new WhereCondition()
                    .andNotEquals(AcOperator.COLUMN_GUID, acOperator.getGuid())
                    .andEquals("USER_ID", acOperator.getUserId())) > 0) {
                throw new OperatorManagementException(ACExceptionCodes.USER_ID_IS_ALREADY_EXIST, wrap("USER_ID"));
            }
            //PASSWORD不通过修改操作员接口更改
            acOperator.setPassword(null);
            //操作员状态不通过修改操作员接口更改
            acOperator.setOperatorStatus(null);
            acOperatorService.update(acOperator);
            return sensitiveInfoProcess(acOperator);
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_OPERATOR, wrap(e));
        }
    }

    /**
     * 删除操作员
     *
     *
     * @param operatorGuid 操作员GUID
     * @throws OperatorManagementException
     */
    @Override
    public AcOperator deleteOperator(String operatorGuid) throws OperatorManagementException {
        try {
            if (StringUtil.isEmpty(operatorGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_OPERATOR"));
            }
            AcOperator acOperator = acOperatorService.loadByGuid(operatorGuid);
            if(acOperator == null) {
                throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        wrap(surroundBracketsWithLFStr(AcOperator.COLUMN_GUID, operatorGuid), AcOperator.TABLE_NAME));
            }
            if(!StringUtils.isEquals(acOperator.getOperatorStatus(), ACConstants.OPERATE_STATUS_STOP)) {
                throw new OperatorManagementException(ACExceptionCodes.CURRENT_STATUS_IS_NOT_ALLOWED_DELETE,
                        wrap(acOperator.getOperatorStatus(), AcOperator.TABLE_NAME));
            }
            acOperatorService.delete(operatorGuid);
            // 删除身份
            acOperatorIdentityService.deleteByCondition(new WhereCondition()
                    .andEquals(AcOperatorIdentity.COLUMN_GUID_OPERATOR, operatorGuid));
            return sensitiveInfoProcess(acOperator);
            /****************************************************************************************
             ****************************************************************************************
             ****************************************************************************************
             ***********************************删除操作员以及其他相关信息*******************************
             ****************************************************************************************
             ****************************************************************************************
             ****************************************************************************************
             ****************************************************************************************/
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
            /*transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        WhereCondition commonWc = new WhereCondition().andEquals("GUID_OPERATOR", operatorGuid);
                        // 1.操作员身份和操作员身份权限集
                        List<AcOperatorIdentity> operIdentList = acOperatorIdentityService.query(commonWc);
                        if (operIdentList.size() > 0) {
                            List<String> operIdenGuids = getValueListByKey(operIdentList, AcOperatorIdentity.class, "guid");
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
                                ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR, wrap(e));
                    }
                }
            });*/
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR, wrap(e));
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
                    wrap(e));
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
            throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_OPERATOR"));
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
                    wrap(e));
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
    public AcOperatorIdentity createOperatorIdentity(AcOperatorIdentity operatorIdentity) throws OperatorManagementException {
        try {
            if (null == operatorIdentity) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, wrap("AC_OPERATOR_IDENTITY"));
            }
            if (StringUtil.isEmpty(operatorIdentity.getGuidOperator())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_OPERATOR"));
            }
            if (StringUtil.isEmpty(operatorIdentity.getIdentityName())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("IDENTITY_NAME"));
            }
            if (StringUtils.isEquals(operatorIdentity.getIdentityName(), "系统默认身份")) {
                throw new OperatorManagementException(ACExceptionCodes.DEFAULT_IDENTITY_NOT_ALLOW);
            }
            // TODO 序列必填？
            if(acOperatorIdentityService.count(new WhereCondition()
                    .andEquals(AcOperatorIdentity.COLUMN_GUID_OPERATOR, operatorIdentity.getGuidOperator())
                    .andEquals(AcOperatorIdentity.COLUMN_IDENTITY_NAME, operatorIdentity.getIdentityName())) > 0) {
                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT,
                        wrap(surroundBracketsWithLFStr(AcOperatorIdentity.COLUMN_IDENTITY_NAME, operatorIdentity.getIdentityName()),
                                AcOperatorIdentity.TABLE_NAME));
            }
            operatorIdentity.setGuid(GUID.identity());
            acOperatorIdentityService.insert(operatorIdentity);
            return operatorIdentity;
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_CREATE_AC_OPERATOR_IDENTITY,
                    wrap(e));
        }
    }

    /**
     * 修改操作员身份
     *
     * @param operatorIdentity
     * @throws OperatorManagementException
     */
    @Override
    public AcOperatorIdentity editOperatorIdentity(AcOperatorIdentity operatorIdentity) throws OperatorManagementException {
        try {
            if (null == operatorIdentity) {
                throw new OperatorManagementException(ACExceptionCodes.OBJECT_IS_NULL, wrap("AcOperatorIdentity"));
            }
            if (StringUtil.isEmpty(operatorIdentity.getGuidOperator())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_OPERATOR"));
            }
            if (StringUtil.isEmpty(operatorIdentity.getGuid())) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID"));
            }
            if (StringUtils.isEquals(operatorIdentity.getIdentityName(), "系统默认身份")) {
                throw new OperatorManagementException(ACExceptionCodes.DEFAULT_IDENTITY_NOT_ALLOW);
            }
            if(acOperatorIdentityService.count(new WhereCondition()
                    .andEquals(AcOperatorIdentity.COLUMN_GUID_OPERATOR, operatorIdentity.getGuidOperator())
                    .andEquals(AcOperatorIdentity.COLUMN_IDENTITY_NAME, operatorIdentity.getIdentityName())
                    .andNotEquals(AcOperatorIdentity.COLUMN_GUID, operatorIdentity.getGuid())) > 0) {
                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_UPDATE,
                        wrap(surroundBracketsWithLFStr(AcOperatorIdentity.COLUMN_IDENTITY_NAME, operatorIdentity.getIdentityName()),
                                AcOperatorIdentity.TABLE_NAME));
            }
            acOperatorIdentityService.update(operatorIdentity);
            return  operatorIdentity;
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_OPERATOR_IDENTITY, wrap(e));
        }
    }

    /**
     * 删除操作员身份
     *
     * @param operatorIdenGuid
     * @throws OperatorManagementException
     */
    @Override
    public AcOperatorIdentity deleteOperatorIdentity(String operatorIdenGuid) throws OperatorManagementException {
        try {
            if (StringUtil.isEmpty(operatorIdenGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_OPERATORIDENTITY"));
            }
            /* 查询操作员身份相关，一并删除操作员身份和操作员身份权限集
            */
            AcOperatorIdentity acOperatorIdentity = acOperatorIdentityService.loadByGuid(operatorIdenGuid);
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
                                ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITY, wrap(e));
                    }
                }
            });
            return acOperatorIdentity;
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITY, wrap(e));
        }
    }

    /**
     * 设置默认操作员身份
     *
     * @param operatorIdenGuid
     * @throws OperatorManagementException
     */
    @Override
    public AcOperatorIdentity setDefaultOperatorIdentity(String operatorIdenGuid) throws OperatorManagementException {
        try {
            if (StringUtil.isEmpty(operatorIdenGuid)) {
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_OPERATORIDENTITY"));
            }

            List<AcOperatorIdentity> list = acOperatorIdentityService.query(new WhereCondition().andEquals("GUID", operatorIdenGuid));
            if (list.size() != 1) {
                throw new OperatorManagementException(ACExceptionCodes.AC_OPERATOR_IDENTITY_IS_NOT_EXIST, wrap(operatorIdenGuid));
            }

            AcOperatorIdentity acOperatorIdentity = list.get(0);
            acOperatorIdentity.setIdentityFlag(CommonConstants.YES);
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
                                ACExceptionCodes.FAILURE_WHEN_SET_DEFAULT_AC_OPERATOR_IDENTITY, wrap(e));
                    }
                }
            });
            return acOperatorIdentity;
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_SET_DEFAULT_AC_OPERATOR_IDENTITY, wrap(e));
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
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("operatorIdentityresList", "AC_OPERATOR_IDENTITYRES"));
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for (AcOperatorIdentityres operatorIdentityres : operatorIdentityresList) {
                            if (StringUtil.isEmpty(operatorIdentityres.getGuidIdentity())) {
                                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_IDENTITY"));
                            }
                            if (StringUtil.isEmpty(operatorIdentityres.getAcResourcetype())) {
                                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("AC_RESOURCETYPE"));
                            }
                            if (StringUtil.isEmpty(operatorIdentityres.getGuidAcResource())) {
                                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_AC_RESOURCE"));
                            }
                            if (acOperatorIdentityresService.count(new WhereCondition()
                                    .andEquals("GUID_IDENTITY", operatorIdentityres.getGuidIdentity())
                                    .andEquals("AC_RESOURCETYPE", operatorIdentityres.getAcResourcetype())
                                    .andEquals("GUID_AC_RESOURCE", operatorIdentityres.getGuidAcResource())) > 0) {
                                throw new OperatorManagementException(ExceptionCodes.OBJECT_IS_ALREADY_EXIST, wrap("GUID_AC_RESOURCE"));
                            }
                            acOperatorIdentityresService.insert(operatorIdentityres);
                        }

                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, wrap("AC_OPERATOR_IDENTITYRES", e));
                    }
                }
            });
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, wrap("AC_OPERATOR_IDENTITYRES", e));
        }
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
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("Object", "AC_OPERATOR_IDENTITYRES"));
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        for(AcOperatorIdentityres identityres : identityresList) {
                            if (StringUtils.isBlank(identityres.getGuidIdentity())) {
                                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, wrap(AcOperatorIdentityres.COLUMN_GUID_IDENTITY, "AC_OPERATOR_IDENTITYRES"));
                            }
                            if (StringUtils.isBlank(identityres.getGuidAcResource())) {
                                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, wrap(AcOperatorIdentityres.COLUMN_GUID_AC_RESOURCE, "AC_OPERATOR_IDENTITYRES"));
                            }
                            acOperatorIdentityresService.deleteByCondition(new WhereCondition()
                                    .andEquals(AcOperatorIdentityres.COLUMN_GUID_IDENTITY, identityres.getGuidIdentity())
                                    .andEquals(AcOperatorIdentityres.COLUMN_GUID_AC_RESOURCE, identityres.getGuidAcResource()));
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ExceptionCodes.FAILURE_WHEN_DELETE, wrap("AC_OPERATOR_IDENTITYRES", e.getMessage()));
                    }
                }
            });
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_OPERATOR_IDENTITYRES,
                    wrap(e.getMessage()));
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
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_IDENTITY"));
            }
            return acOperatorServiceExt.queryOperatorIdentityreses(operatorIdentityGuid);
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITY,
                    wrap(e));
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
                throw new OperatorManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("GUID_IDENTITY"));
            }
            return queryOperatorIdentities(queryOperatorByUserId(userId).getGuid());
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_AC_OPERATOR_IDENTITY,
                    wrap(e));
        }
    }


    /**
     * 查询操作员不同资源类型下的资源
     *
     * @param operatorGuid
     * @param resType
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List queryOperatorRoleByResType(String operatorGuid, String resType) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(operatorGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("GUID_OPERATOR", "AC_ROLE"));
            }
            if (StringUtils.isBlank(resType)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("RES_TYPE", "AC_ROLE"));
            }
            List resList = new ArrayList();
            AcOperator operator = acOperatorService.loadByGuid(operatorGuid);
            if (operator == null) {
                throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        wrap(surroundBracketsWithLFStr(AcOperator.COLUMN_GUID, operatorGuid), AcOperator.TABLE_NAME));
            }
            // 查询相关员工
            OmEmployee employee = queryEmployeeByUserId(operator.getUserId());

            switch (resType) {
                case ACConstants.RESOURCE_TYPE_ROLE:
                    List<AcOperatorRole> acOperatorRoles = acOperatorRoleService.query(new WhereCondition()
                            .andEquals(AcOperatorRole.COLUMN_GUID_OPERATOR, operatorGuid));
                    List<String> roleGuids = new ArrayList<>();
                    for (AcOperatorRole acOperatorRole : acOperatorRoles) {
                        roleGuids.add(acOperatorRole.getGuidRole());
                    }
                    if (roleGuids.size() > 0) {
                        return acRoleService.query(new WhereCondition().andIn("GUID", roleGuids));
                    }
                    break;
                case ACConstants.RESOURCE_TYPE_FUNCTION:
                    Set<String> funcGuids = acOperatorFuncService.query(new WhereCondition()
                            .andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, operatorGuid)
                            .andEquals(AcOperatorFunc.COLUMN_AUTH_TYPE, ACConstants.AUTH_TYPE_PERMIT))
                            .stream().map(AcOperatorFunc::getGuidFunc).collect(Collectors.toSet());
                    if (CollectionUtils.isNotEmpty(funcGuids)) {
                        return acFuncService.query(new WhereCondition().andIn(AcFunc.COLUMN_GUID, funcGuids));
                    }
                    break;
                case ACConstants.RESOURCE_TYPE_ORGANIZATION:
                    resList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_ORGANIZATION, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_POSITION:
                    resList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_POSITION, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_DUTY:
                    resList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_DUTY, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_WORKGROUP:
                    resList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_WORKGROUP, employee.getGuid());
                    break;
                default:
                    throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, wrap("AC_RESOURCE_TYPE " + resType, "AC_ROLE"));
            }
            return resList;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, wrap("AC_OPERATOR_ROLE", e));
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
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("USER_ID", "AC_ROLE"));
            }
            List<AcOperator> operatorList = acOperatorService.query(new WhereCondition().andEquals(AcOperator.COLUMN_USER_ID, userId));// 查询用户对应的操作员信息
            if (CollectionUtils.isEmpty(operatorList)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        wrap(surroundBracketsWithLFStr(AcOperator.COLUMN_USER_ID, userId), AcOperator.TABLE_NAME));
            }
            AcOperator acOperator = operatorList.get(0);
            acOperator.setPassword(null);
            return acOperator;

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, wrap("AC_OPERATOR", e));
        }
    }

    /**
     * 根据用户名查询员工信息
     *
     * @param userId
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public OmEmployee queryEmployeeByUserId(String userId) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(userId)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap(OmEmployee.COLUMN_USER_ID, OmEmployee.TABLE_NAME));
            }
            List<OmEmployee> employees = omEmployeeService.query(new WhereCondition().andEquals(OmEmployee.COLUMN_USER_ID, userId));// 查询用户对应的操作员信息
            if (CollectionUtils.isEmpty(employees)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        wrap(surroundBracketsWithLFStr(OmEmployee.COLUMN_USER_ID, userId), OmEmployee.TABLE_NAME));
            }
            OmEmployee omEmployee = employees.get(0);
            return omEmployee;

        } catch (ToolsRuntimeException ae) {
            logger.error("queryEmployeeByUserId exception: ", ae);
            throw ae;
        } catch (Exception e) {
            logger.error("queryEmployeeByUserId exception: ", e);
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, wrap(OmEmployee.TABLE_NAME, e));
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
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("USER_ID", "queryOperatorFuncInfoInApp"));
            }
            /** 查询对应操作员*/
            AcOperator operator = queryOperatorByUserId(userId);
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
                }
            }
            return rootNode;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    wrap("AC_APP", e));
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
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("USER_ID", "AC_OPERATOR_FUNC"));
            }
            AcOperator acOperator = queryOperatorByUserId(userId);
            return acOperatorServiceExt.queryOperatorFuncDetail(acOperator.getGuid());

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, wrap("AC_OPERATOR_FUNC", e));
        }
    }

    /**
     * 添加特殊权限
     *
     * @param acOperatorFunc
     * @throws OperatorManagementException
     */
    @Override
    public AcOperatorFunc addAcOperatorFunc(AcOperatorFunc acOperatorFunc) throws OperatorManagementException {
        try {
            if (null == acOperatorFunc) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("acOperatorFunc", "AC_OPERATOR_FUNC"));
            }
            String[] validateFields = {"startDate", "endDate"};
            String s = BeanFieldValidateUtil.checkObjFieldNotRequired(acOperatorFunc, validateFields);
            if (! StringUtils.isBlank(s))
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        wrap(s, "AC_OPERATOR_FUNC"));
            // 查重
            if (acOperatorFuncService.count(new WhereCondition()
                    .andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, acOperatorFunc.getGuidOperator())
                    .andEquals(AcOperatorFunc.COLUMN_GUID_FUNC, acOperatorFunc.getGuidFunc())) > 0) {
                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, wrap("", AcOperatorFunc.TABLE_NAME));
            }
            acOperatorFuncService.insert(acOperatorFunc);
            return acOperatorFunc;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, wrap("AC_OPERATOR_FUNC", e));
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
    public AcOperatorFunc removeAcOperatorFunc(String operatorGuid, String funcGuid) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(operatorGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap(AcOperatorFunc.COLUMN_GUID_OPERATOR, AcOperatorFunc.TABLE_NAME));
            }
            if (StringUtils.isBlank(funcGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap(AcOperatorFunc.COLUMN_GUID_FUNC, AcOperatorFunc.TABLE_NAME));
            }
            WhereCondition wc = new WhereCondition();
            wc.andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, operatorGuid)
                    .andEquals(AcOperatorFunc.COLUMN_GUID_FUNC, funcGuid);
            List<AcOperatorFunc> acOperatorFuncs = acOperatorFuncService.query(wc);
            if(CollectionUtils.isEmpty(acOperatorFuncs)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        wrap(surroundBracketsWithLFStr(AcOperatorFunc.COLUMN_GUID_OPERATOR, operatorGuid) + ","
                                + surroundBracketsWithLFStr(AcOperatorFunc.COLUMN_GUID_FUNC, funcGuid), AcOperatorFunc.TABLE_NAME));
            }
            acOperatorFuncService.deleteByCondition(wc);
            return acOperatorFuncs.get(0);
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, wrap(AcOperatorFunc.TABLE_NAME, e));
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
                    ExceptionCodes.FAILURE_WHEN_QUERY, wrap(AcConfig.TABLE_NAME, e));
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
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("config", AcConfig.TABLE_NAME));
        }
        try {
            config.setGuid(GUID.operatorConfig());
            String validate = BeanFieldValidateUtil.checkObjFieldNotRequired(config, new String[]{"configDesc", "displayOrder"});
            if(StringUtils.isNotEmpty(validate)) {
                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, wrap(validate, AcConfig.TABLE_NAME));
            }
            if(acConfigService.count(new WhereCondition()
                    .andEquals(AcConfig.COLUMN_GUID_APP, config.getGuidApp())
                    .andEquals(AcConfig.COLUMN_CONFIG_TYPE, config.getConfigType())
                    .andEquals(AcConfig.COLUMN_CONFIG_NAME, config.getConfigName())) > 0) {
                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, wrap(config.getConfigName(), AcConfig.TABLE_NAME));
            }
            config.setDisplayOrder(new BigDecimal(acConfigService.count(new WhereCondition()
                    .andEquals(AcConfig.COLUMN_GUID_APP, config.getGuidApp())
                    .andEquals(AcConfig.COLUMN_CONFIG_TYPE, config.getConfigType()))));
            acConfigService.insert(config);
            return config;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, wrap(AcConfig.TABLE_NAME, e.getMessage()));
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
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("configList", AcConfig.TABLE_NAME));
        }
        try {
            List<String> guids = new ArrayList<>();
            cfgList.stream().forEach(cfg -> {
                if(cfg == null) {
                    throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("acOperatorConfig", AcConfig.TABLE_NAME));
                }
                if(StringUtils.isBlank(cfg.getGuid())) {
                    throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, wrap(AcConfig.COLUMN_GUID, AcConfig.TABLE_NAME));
                }
                guids.add(cfg.getGuid());
            });
            if(guids.size() > 0) {
                acConfigService.deleteByCondition(new WhereCondition().andIn(AcConfig.COLUMN_GUID, guids));
            }
            return cfgList;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, wrap(AcConfig.TABLE_NAME, e));
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
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("acOperatorConfig", AcConfig.TABLE_NAME));
        }
        try {
            String validate = BeanFieldValidateUtil.checkObjFieldNotRequired(config, new String[]{"configDesc", "displayOrder"});
            if(StringUtils.isNotEmpty(validate)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap(validate, AcConfig.TABLE_NAME));
            }
            if(acConfigService.count(new WhereCondition()
                    .andEquals(AcConfig.COLUMN_GUID_APP, config.getGuidApp())
                    .andEquals(AcConfig.COLUMN_CONFIG_TYPE, config.getConfigType())
                    .andEquals(AcConfig.COLUMN_CONFIG_NAME, config.getConfigName())
                    .andNotEquals(AcConfig.COLUMN_GUID, config.getGuid())) > 0) {
                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, wrap(config.getConfigName(), AcConfig.TABLE_NAME));
            }
            acConfigService.update(config);
            return config;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE, wrap(AcConfig.TABLE_NAME, e));
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
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap("acOperatorConfig", AcOperatorConfig.TABLE_NAME));
        }
        try {
            String validate = BeanFieldValidateUtil.checkObjFieldAllRequired(acOperatorConfig);
            if(StringUtils.isNotEmpty(validate)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, wrap(validate, AcOperatorConfig.TABLE_NAME));
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
                                ExceptionCodes.FAILURE_WHEN_UPDATE, wrap(AcOperatorConfig.TABLE_NAME, e.getMessage()));
                    }
                }
            });
            return acOperatorConfig;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE, wrap(AcOperatorConfig.TABLE_NAME, e));
        }
    }

    /**
     * 查询操作员的个性化配置
     *
     * @param userId
     * @param appGuid
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcConfig> queryOperatorConfig(String userId, String appGuid) throws OperatorManagementException {
        if(StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("UserId", AcOperatorConfig.TABLE_NAME));
        }
        if(StringUtils.isBlank(appGuid)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("UserId", AcOperatorConfig.TABLE_NAME));
        }
        AcOperator operator = queryOperatorByUserId(userId);
        try {
            /**
             * 查询全部配置，与操作员修改的配置，替换默认配置中的已修改项
             */
            List<AcOperatorConfig> privateConfigs = acOperatorConfigService.query(
                    new WhereCondition().andEquals(AcOperatorConfig.COLUMN_GUID_OPERATOR, operator.getGuid()));
            List<AcConfig> defaultConfigs = acConfigService.query(
                    new WhereCondition().andEquals(AcConfig.COLUMN_GUID_APP, appGuid));
            if(privateConfigs.size() > 0) {
                Map<String, String> matchVal = new HashMap<>();
                privateConfigs.forEach(cfg ->
                    matchVal.put(cfg.getGuidConfig(), cfg.getConfigValue())
                );

                return defaultConfigs.stream().map(cfg -> {
                    if (matchVal.containsKey(cfg.getGuid())) {
                        cfg.setConfigValue(matchVal.get(cfg.getGuid()));
                    }
                    return cfg;
                }).collect(Collectors.toList());
            } else {
                return defaultConfigs;
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, wrap(AcOperatorConfig.TABLE_NAME, e));
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
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("funGuid", AcOperatorBhv.TABLE_NAME)
            );
        }
        if(StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("UserId", AcOperatorBhv.TABLE_NAME)
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

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, wrap(AcOperatorBhv.TABLE_NAME, e));
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
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
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
                                        ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
                                );
                            }
                            String validate = BeanFieldValidateUtil.checkObjFieldNotRequired(operatorBhv, new String[]{"authType"});
                            if (StringUtils.isNotEmpty(validate)) {
                                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, wrap(validate, AcOperatorBhv.TABLE_NAME));
                            }
                            if (acOperatorBhvService.count(new WhereCondition()
                                    .andEquals(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, operatorBhv.getGuidFuncBhv())
                                    .andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, operatorBhv.getGuidOperator())) > 0) {
                                throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, wrap(operatorBhv.getGuidFuncBhv(), AcOperatorBhv.TABLE_NAME));
                            }
                            acOperatorBhvService.insert(operatorBhv);

                        }
                    } catch (ToolsRuntimeException ae) {
                        status.setRollbackOnly();
                        throw ae;
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new OperatorManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, wrap(AcOperatorBhv.TABLE_NAME, e));
                    }
                }
            });
            return  operatorBhvList;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, wrap(AcOperatorBhv.TABLE_NAME, e));
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
                    ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
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
                        ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, wrap("operatorBhv", AcOperatorBhv.TABLE_NAME)
                );
            }
            return  operatorBhvList;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, wrap(AcOperatorBhv.TABLE_NAME, e));
        }
    }

    /**
     * 查询操作员在应用下已授权功能
     *
     * @param userId  操作员
     * @param appGuid 应用
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public AcOperatorFuncDetail getOperatorFuncInfo(String userId, String appGuid) throws OperatorManagementException {
        try {
            if (StringUtils.isBlank(userId)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("USER_ID", "getOperatorFuncInfo"));
            }
            if (StringUtils.isBlank(appGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("GUID_APP", "getOperatorFuncInfo"));
            }
            // 构造应用节点
            AcOperatorFuncDetail appNode = new AcOperatorFuncDetail();
            /** 查询用户下所有应用 */
            List<AcApp> acApps = applicationRService.queryOperatorAllApp(userId);
            Optional<AcApp> appOptional = acApps.stream()
                    .filter(app -> StringUtils.isEquals(app.getGuid(), appGuid))
                    .findFirst();
            if(appOptional.isPresent()) {
                appOptional.ifPresent(acApp ->
                    {
                        appNode.setId(acApp.getGuid());
                        appNode.setText(acApp.getAppName());
//                        appNode.setParentGuid(rootNode.getId());
                        appNode.setIcon(AcOperatorFuncDetail.NODE_ICON_APP);
                        appNode.setNodeType(AcOperatorFuncDetail.NODE_TYPE_APP);
                        appNode.setAppGuid(acApp.getGuid());
                        appNode.setIsLeaf(CommonConstants.NO);
//                        rootNode.addChildren(appNode);

                        List<AcOperatorFuncDetail> nodeList = new ArrayList<>();
                        // 节点列表（散列表，用于临时存储节点对象）
                        HashMap<String, AcOperatorFuncDetail> nodeMap = new HashMap<>();
                        // 查询所有的功能组
                        acFuncgroupService
                                .query(new WhereCondition().andEquals(AcFuncgroup.COLUMN_GUID_APP, acApp.getGuid()))
                                .stream()
                                .forEach(acFuncgroup -> {
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
                                });
                        //查询当前用户拥有该应用的功能
                        authenticationRService.queryOperatorAuthFuncsInApp(userId, appGuid)
                                .stream()
                                .forEach(acFunc -> {
                                    AcOperatorFuncDetail funcNode = new AcOperatorFuncDetail();
                                    funcNode.setId(acFunc.getGuid());
                                    funcNode.setText(acFunc.getFuncName());
                                    funcNode.setParentGuid(acFunc.getGuidFuncgroup());
                                    funcNode.setIcon(AcOperatorFuncDetail.NODE_ICON_FUNC);
                                    funcNode.setNodeType(AcOperatorFuncDetail.NODE_TYPE_FUNC);
                                    funcNode.setStatus(AcOperatorFuncDetail.NODE_STATUS_ENABLED);
                                    funcNode.setIsLeaf(CommonConstants.YES);
                                    funcNode.setFuncGroupGuid(acFunc.getGuidFuncgroup());
                                    funcNode.setAppGuid(nodeMap.get(acFunc.getGuidFuncgroup()).getAppGuid());
                                    nodeList.add(funcNode);
                                    nodeMap.put(funcNode.getId(), funcNode);
                                });
                        // 构造功能树节点
                        for (AcOperatorFuncDetail node : nodeList) {
                            // 如果节点的父节点为空，则为应用下的子节点
                            if (StringUtils.isBlank(node.getParentGuid())) {
                                appNode.addChildren(node);
                            } else { // 否则获取父节点添加
                                nodeMap.get(node.getParentGuid()).addChildren(node);
                            }
                        }

                    });
            } else {
                throw new OperatorManagementException(
                        ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        wrap(surroundBracketsWithLFStr(AcApp.COLUMN_GUID, appGuid), AcApp.TABLE_NAME));
            }
            return appNode;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    wrap("getOperatorFuncInfo", e));
        }
    }

    /**
     * 改变操作员状态
     *
     * @param userId 用户名
     * @param status 用户状态
     * @return
     * @throws OperatorManagementException
     * @see ACConstants#OPERATE_STATUS_CLEAR 注销
     * @see ACConstants#OPERATE_STATUS_LOCK 锁定
     * @see ACConstants#OPERATE_STATUS_LOGIN 正常
     * @see ACConstants#OPERATE_STATUS_LOGOUT 退出
     * @see ACConstants#OPERATE_STATUS_PAUSE 挂起
     * @see ACConstants#OPERATE_STATUS_STOP 停用
     */
    @Override
    public AcOperator changeOperatorStatus(String userId, String status) throws OperatorManagementException {
        if(StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap(AcOperator.COLUMN_USER_ID, "changeOperatorStatus"));
        }
        if(StringUtils.isBlank(status)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap(AcOperator.COLUMN_OPERATOR_STATUS, "changeOperatorStatus"));
        }
        try {
            AcOperator acOperator = queryOperatorByUserId(userId);
            String old_status = acOperator.getOperatorStatus();
            switch (status) {
                /*改变状态为 注销
                * 限制当前状态为： 退出、锁定
                * */
                case ACConstants.OPERATE_STATUS_CLEAR :
                    if (!StringUtil.isEqualsIn(old_status, ACConstants.OPERATE_STATUS_LOGOUT, ACConstants.OPERATE_STATUS_LOCK)) {
                        throw new OperatorManagementException(ACExceptionCodes.CURRENT_STATUS_IS_NOT_ALLOWED_CHANGE, wrap(old_status, ACConstants.OPERATE_STATUS_CLEAR));
                    }
                    acOperator.setOperatorStatus(status);
                    break;
                /*改变状态为 正常
                * 限制当前状态为： 退出、挂起
                * */
                case ACConstants.OPERATE_STATUS_LOGIN :
                    if (!StringUtil.isEqualsIn(old_status, ACConstants.OPERATE_STATUS_LOGOUT, ACConstants.OPERATE_STATUS_PAUSE)) {
                        throw new OperatorManagementException(ACExceptionCodes.CURRENT_STATUS_IS_NOT_ALLOWED_CHANGE, wrap(old_status, ACConstants.OPERATE_STATUS_LOGIN));
                    }
                    acOperator.setErrCount(new BigDecimal(0));
                    acOperator.setOperatorStatus(status);
                    break;
                /*改变状态为 退出
                * 不限制当前状态
                * */
                case ACConstants.OPERATE_STATUS_LOGOUT :
                    acOperator.setOperatorStatus(status);
                    break;
                /*改变状态为 挂起
                * 限制当前状态 为正常
                * */
                case ACConstants.OPERATE_STATUS_LOCK :
                    if (!StringUtils.isEquals(old_status, ACConstants.OPERATE_STATUS_PAUSE)) {
                        throw new OperatorManagementException(ACExceptionCodes.CURRENT_STATUS_IS_NOT_ALLOWED_CHANGE, wrap(old_status, ACConstants.OPERATE_STATUS_PAUSE));
                    }
                    acOperator.setLockTime(new Date());
                    acOperator.setOperatorStatus(status);
                    break;
                /*改变状态为 锁定
                * 限制当前状态 为正常
                * */
                case ACConstants.OPERATE_STATUS_PAUSE :
                    if (!StringUtils.isEquals(old_status, ACConstants.OPERATE_STATUS_PAUSE)) {
                        throw new OperatorManagementException(ACExceptionCodes.CURRENT_STATUS_IS_NOT_ALLOWED_CHANGE, wrap(old_status, ACConstants.OPERATE_STATUS_PAUSE));
                    }
                    acOperator.setOperatorStatus(status);
                    break;
                default:
                    throw new OperatorManagementException(ACExceptionCodes.OPERATOR_STATUS_ERROR, old_status);
            }
            acOperatorService.update(acOperator);
            return sensitiveInfoProcess(acOperator);
        } catch (ToolsRuntimeException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(ExceptionCodes.FAILURE_WHEN_CALL, wrap("changeOperatorStatus", e));
        }
    }

    /**
     * 获取没有关联员工的操作员
     *
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<AcOperator> getOperatorsNotLinkEmp() throws OperatorManagementException {
        try {
            Set<String> linkUserIds = omEmployeeService.query(new WhereCondition()).stream()
                    .filter(omEmployee -> StringUtils.isNotEmpty(omEmployee.getUserId()))
                    .map(OmEmployee::getUserId).collect(Collectors.toSet());
            List<AcOperator> operatorList = new ArrayList<>();
            acOperatorService.query(new WhereCondition().andEquals(AcOperator.COLUMN_OPERATOR_STATUS, ACConstants.OPERATE_STATUS_STOP)
                    .andNotIn(AcOperator.COLUMN_USER_ID, new ArrayList<String>(linkUserIds))).forEach(a -> {
                AcOperator acOperator = new AcOperator();
                acOperator.setUserId(a.getUserId());
                acOperator.setOperatorName(a.getOperatorName());
                operatorList.add(acOperator);
            });
            return operatorList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(ExceptionCodes.FAILURE_WHEN_CALL, wrap("getOperatorsNotLinkEmp", e));
        }
    }


    /**
     * 获取操作员功能权限信息
     * 包含 已授权（从角色授权） 特别禁止
     * 未授权（从功能所有行为筛选掉角色授权） 和 特别允许 列表
     *
     * @param userId
     * @param appGuid
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public Map<String, List<AcFunc>> getOperatorFuncAuthInfo(String userId, String appGuid) throws OperatorManagementException {
        if(StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("userId(String)", "getOperatorFuncAuthInfo"));
        }
        if(StringUtils.isBlank(appGuid)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("appGuid(String)", "getOperatorFuncAuthInfo"));
        }
        try {
            // 操作员的所有角色
            List<String> roleGuids = roleRService.queryAllRoleByUserId(userId).stream().map(AcRole::getGuid).collect(Collectors.toList());
            String operatorGuid = queryOperatorByUserId(userId).getGuid();
            // 查询角色所有功能
            Set<String> roleFuncGuids = acRoleFuncService
                    .query(new WhereCondition()
                            .andEquals(AcRoleFunc.COLUMN_GUID_APP, appGuid)
                            .andIn(AcRoleFunc.COLUMN_GUID_ROLE, roleGuids))
                    .stream()
                    .map(AcRoleFunc::getGuidFunc)
                    .collect(Collectors.toSet());
            // 查询应用下所有功能
            List<AcFunc> all = applicationRService.queryFuncListInApp(appGuid)
                    .stream()
                    .filter(acFunc -> StringUtils.isEquals(acFunc.getIscheck(), CommonConstants.YES))
                    .collect(Collectors.toList());

            // 查询操作员功能
            Map<String, List<AcOperatorFunc>> acOperatorFuncs = acOperatorFuncService.query(new WhereCondition().andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, operatorGuid)
                    .andEquals(AcOperatorFunc.COLUMN_GUID_APP, appGuid))
                    .stream()
                    .collect(Collectors.groupingBy(AcOperatorFunc::getAuthType));

            List<AcFunc> forbidList = new ArrayList<>();
            List<AcFunc> permitList = new ArrayList<>();
            List<AcFunc> authList = new ArrayList<>();
            List<AcFunc> unauthList = new ArrayList<>();
            // 特别禁止
            List<String> forbidGuids = acOperatorFuncs.get(ACConstants.AUTH_TYPE_FORBID)
                    .stream().map(AcOperatorFunc::getGuidFunc).collect(Collectors.toList());
            // 特别允许
            List<String> permitGuids = acOperatorFuncs.get(ACConstants.AUTH_TYPE_PERMIT)
                    .stream().map(AcOperatorFunc::getGuidFunc).collect(Collectors.toList());
            // 已授权
            List<String> authGuids = roleFuncGuids.stream().filter(s -> !forbidGuids.contains(s)).collect(Collectors.toList());

            for (AcFunc acFunc : all) {
                String guid = acFunc.getGuid();
                if (forbidGuids.contains(guid)) {
                    forbidList.add(acFunc);
                } else if (permitGuids.contains(guid)) {
                    permitList.add(acFunc);
                } else if (authGuids.contains(guid)) {
                    authList.add(acFunc);
                } else {
                    unauthList.add(acFunc);
                }
            }
            Map<String, List<AcFunc>> map = new HashMap<>();
            map.put("auth", authList);
            map.put("forbid", forbidList);
            map.put("unauth", unauthList);
            map.put("permit", permitList);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(ExceptionCodes.FAILURE_WHEN_CALL, wrap("getOperatorFuncAuthInfo", e));
        }
    }

    /**
     * 获取操作员功能行为信息
     * 包含 已授权（从角色授权） 特别禁止
     * 未授权（从功能所有行为筛选掉角色授权） 和 特别允许 列表
     *
     * @param userId
     * @param funcGuid
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public Map<String, List<Map>> getOperatorFuncBhvInfo(String userId, String funcGuid) throws OperatorManagementException {
        if(StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap(AcOperator.COLUMN_USER_ID, "getAuthOperatorFuncBhv"));
        }
        if(StringUtils.isBlank(funcGuid)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("guid_func", "getAuthOperatorFuncBhv"));
        }
        try {
            // 操作员的所有角色
            List<String> roleGuids = roleRService.queryAllRoleByUserId(userId).stream().map(AcRole::getGuid).collect(Collectors.toList());
            String operatorGuid = queryOperatorByUserId(userId).getGuid();
            // 查询角色对应功能行为
            List<Map> allBhvs = applicationRService.queryAllBhvDefForFunc(funcGuid);
            // 已授权
            List<Map> authOperatorFuncBhv = acOperatorServiceExt.getAuthOperatorFuncBhv(roleGuids, operatorGuid, funcGuid);
            // 特别禁止
            List<Map> authOperatorFuncFbdBhv = acOperatorServiceExt.getAuthOperatorFuncFbdBhv(operatorGuid, funcGuid);
            // 特别允许
            List<Map> unauthOperatorFuncPmtBhv = acOperatorServiceExt.getUnauthOperatorFuncPmtBhv(operatorGuid, funcGuid);
            // 从以上三个集合中获取到的GUID 从所有功能行为集合中筛选掉这些就是未授权行为集合
            Set<String> filterGuids = Stream.concat(Stream.concat(authOperatorFuncBhv.stream(),
                    authOperatorFuncFbdBhv.stream()), unauthOperatorFuncPmtBhv.stream())
                    .map(map -> String.valueOf(map.get("guid"))).collect(Collectors.toSet());
            // 未授权 筛选出有效行为 和 上面需要过滤的行为
            List<Map> unauthOperatorFuncBhv = allBhvs.stream().filter(map -> StringUtils.isEquals(CommonConstants.YES, (String) map.get("iseffective")) &&
                    !filterGuids.contains(map.get("guid").toString())).collect(Collectors.toList());
            Map<String, List<Map>> map = new HashMap<>();
            map.put("auth", authOperatorFuncBhv);
            map.put("forbid", authOperatorFuncFbdBhv);
            map.put("unauth", unauthOperatorFuncBhv);
            map.put("permit", unauthOperatorFuncPmtBhv);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(ExceptionCodes.FAILURE_WHEN_CALL, wrap("getAuthOperatorFuncBhv", e));
        }
    }

    /**
     * 获取操作员拥有的功能下功能行为代码
     *
     * @param userId
     * @param funcCode
     * @return
     * @throws OperatorManagementException
     */
    @Override
    public List<String> getPmtFuncBhvByCode(String userId, String funcCode) throws OperatorManagementException {
        if(StringUtils.isBlank(funcCode))
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("funcCode(String)", "getPmtFuncBhvByCode"));
        List<AcFunc> funcList = acFuncService.query(new WhereCondition().andEquals(AcFunc.COLUMN_FUNC_CODE, funcCode));
        if(CollectionUtils.isEmpty(funcList)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                    wrap(surroundBracketsWithLFStr(AcFunc.COLUMN_FUNC_CODE, funcCode), AcFunc.TABLE_NAME));
        }
        Map<String, List<Map>> operatorFuncBhvInfo = getOperatorFuncBhvInfo(userId, funcList.get(0).getGuid());
        List<String> bhvCodes = new ArrayList<>();
        operatorFuncBhvInfo.get("auth").forEach(map -> bhvCodes.add((String)map.get("bhvCode")));
        operatorFuncBhvInfo.get("permit").forEach(map -> bhvCodes.add((String)map.get("bhvCode")));
        return bhvCodes;
    }

    /**
     * 添加操作员特殊功能行为
     *
     * @param acOperatorBhvs
     * @throws OperatorManagementException
     */
    @Override
    public void addAcOperatorBhv(List<AcOperatorBhv> acOperatorBhvs) throws OperatorManagementException {
        if (CollectionUtils.isEmpty(acOperatorBhvs)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("acOperatorBhvs", "addAcOperatorBhv"));
        }
        try {
            for(AcOperatorBhv acOperatorBhv : acOperatorBhvs) {
                String result = BeanFieldValidateUtil.checkObjFieldAllRequired(acOperatorBhv);
                if (!StringUtils.isBlank(result)) {
                    throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, wrap(result, AcOperatorBhv.TABLE_NAME));
                }
                if(acOperatorBhvService.count(new WhereCondition().andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, acOperatorBhv.getGuidOperator())
                    .andEquals(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, acOperatorBhv.getGuidFuncBhv())) > 0) {
                    throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, wrap(acOperatorBhv.getGuidFuncBhv(), AcOperatorBhv.TABLE_NAME));
                }
                acOperatorBhvService.insert(acOperatorBhv);
            }
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_CALL, wrap("addAcOperatorBhv", e));
        }
    }

    /**
     * 移除操作员特殊功能行为
     *
     * @param acOperatorBhvs
     * @throws OperatorManagementException
     */
    @Override
    public void removeAcOperatorBhv(List<AcOperatorBhv> acOperatorBhvs) throws OperatorManagementException {
        if (CollectionUtils.isEmpty(acOperatorBhvs)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("acOperatorBhvs", "removeAcOperatorBhv"));
        }
        try {
            WhereCondition wc = new WhereCondition();
            for(int i = 0; i < acOperatorBhvs.size(); i ++) {
                String result = BeanFieldValidateUtil.checkObjFieldNotRequired(acOperatorBhvs.get(i), new String[] {"iseffective"});
                if (!StringUtils.isBlank(result)) {
                    throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, wrap(result, AcOperatorBhv.TABLE_NAME));
                }
                wc.andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, acOperatorBhvs.get(i).getGuidOperator())
                        .andEquals(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, acOperatorBhvs.get(i).getGuidFuncBhv());
                if( i != acOperatorBhvs.size() - 1 ) {
                    wc.or();
                }
            }
            acOperatorBhvService.deleteByCondition(wc);
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_CALL, wrap("removeAcOperatorBhv", e));
        }
    }

    /**
     * 添加操作员特殊功能
     *
     * @param acOperatorFuncs
     * @throws OperatorManagementException
     */
    @Override
    public void addAcOperatorFunc(List<AcOperatorFunc> acOperatorFuncs) throws OperatorManagementException {
        if (CollectionUtils.isEmpty(acOperatorFuncs)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("acOperatorFuncs", "addAcOperatorFunc"));
        }
        try {
            for(AcOperatorFunc acOperatorFunc : acOperatorFuncs) {
                String[] notReq = new String[] {"startDate", "endDate"};
                String result = BeanFieldValidateUtil.checkObjFieldNotRequired(acOperatorFunc, notReq);
                if (!StringUtils.isBlank(result)) {
                    throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, wrap(result, AcOperatorFunc.TABLE_NAME));
                }
                if(acOperatorBhvService.count(new WhereCondition().andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, acOperatorFunc.getGuidOperator())
                        .andEquals(AcOperatorFunc.COLUMN_GUID_FUNC, acOperatorFunc.getGuidFunc())) > 0) {
                    throw new OperatorManagementException(ExceptionCodes.DUPLICATE_WHEN_INSERT, wrap(acOperatorFunc.getGuidFunc(), AcOperatorFunc.TABLE_NAME));
                }
                // 如果功能有行为权限，全部添加
                List<AcFuncBhv> acFuncBhvs = acFuncBhvService.query(new WhereCondition().andEquals(AcFuncBhv.COLUMN_GUID_FUNC, acOperatorFunc.getGuidFunc()));
                for(AcFuncBhv acFuncBhv : acFuncBhvs) {
                    AcOperatorBhv operatorBhv = new AcOperatorBhv();
                    operatorBhv.setGuidOperator(acOperatorFunc.getGuidOperator());
                    operatorBhv.setGuidFuncBhv(acFuncBhv.getGuid());
                    operatorBhv.setAuthType(ACConstants.AUTH_TYPE_PERMIT);
                    acOperatorBhvService.insert(operatorBhv);
                }
                acOperatorFuncService.insert(acOperatorFunc);
            }
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_CALL, wrap("addAcOperatorFunc", e));
        }
    }

    /**
     * 移除操作员特殊功能
     *
     * @param acOperatorFuncs
     * @throws OperatorManagementException
     */
    @Override
    public void removeAcOperatorFunc(List<AcOperatorFunc> acOperatorFuncs) throws OperatorManagementException {
        if (CollectionUtils.isEmpty(acOperatorFuncs)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("acOperatorFuncs", "removeAcOperatorFunc"));
        }
        try {
            List<String> guids = new ArrayList<>();
            Set<String> funcBhvGuids = new HashSet<>();
            for(AcOperatorFunc acOperatorFunc : acOperatorFuncs) {
                String[] notReq = new String[] {"startDate", "endDate"};
                String result = BeanFieldValidateUtil.checkObjFieldNotRequired(acOperatorFunc, notReq);
                if (!StringUtils.isBlank(result)) {
                    throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, wrap(result, AcOperatorFunc.TABLE_NAME));
                }
                // 如果功能有行为权限，一并删除
                funcBhvGuids.addAll(acFuncBhvService.query(new WhereCondition()
                        .andEquals(AcFuncBhv.COLUMN_GUID_FUNC, acOperatorFunc.getGuidFunc()))
                        .stream()
                        .map(AcFuncBhv::getGuid).collect(Collectors.toSet()));
                guids.add(acOperatorFunc.getGuidOperator() + acOperatorFunc.getGuidFunc());
            }
            if (CollectionUtils.isNotEmpty(funcBhvGuids)) {
                acOperatorBhvService.deleteByCondition(new WhereCondition()
                        .andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, acOperatorFuncs.get(0).getGuidOperator())
                        .andIn(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, new ArrayList<>(funcBhvGuids)));
            }
            acOperatorFuncService.deleteByCondition(new WhereCondition()
                    .andIn("concat(" + AcOperatorFunc.COLUMN_GUID_OPERATOR + "," + AcOperatorFunc.COLUMN_GUID_FUNC + ")", guids));
        } catch (ToolsRuntimeException oe) {
            throw oe;
        } catch (Exception e) {
            e.printStackTrace();
            throw new OperatorManagementException(
                    ExceptionCodes.FAILURE_WHEN_CALL, wrap("removeAcOperatorFunc", e));
        }
    }

    /**
     * 操作员敏感信息处理  密码等
     * @param acOperator
     * @return
     */
    private AcOperator sensitiveInfoProcess(AcOperator acOperator) {
        try {
            String[] fields = {"password", "authMode"};
            return BeanFieldValidateUtil.processObjSensitiveFields(acOperator, fields);
        } catch (Exception e) {
            throw new OperatorManagementException(ExceptionCodes.FAILURE_WHEN_CALL, wrap("sensitiveInfoProcess", e));
        }
    }



}
