package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.CryptographyUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.basic.AcOperatorIdentityresRServiceImpl;
import org.tis.tools.rservice.ac.exception.OperatorManagementException;
import org.tis.tools.service.ac.*;
import org.tis.tools.service.ac.exception.ACExceptionCodes;
import org.tis.tools.service.om.OmEmployeeService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    AcRoleService acRoleService;

    @Autowired
    OmEmployeeService omEmployeeService;

    @Autowired
    IRoleRService roleRService;

    @Autowired
    AcOperatorServiceExt acOperatorServiceExt;

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
     * @param IdentityGuid
     * @param resGuid
     * @throws OperatorManagementException
     */
    @Override
    public void deleteOperatorIdentityres(String IdentityGuid, String resGuid) throws OperatorManagementException {
        try {
            if (null == IdentityGuid) {
                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, BasicUtil.wrap(AcOperatorIdentityres.COLUMN_GUID_IDENTITY));
            }
            if (null == resGuid) {
                throw new OperatorManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_DELETE, BasicUtil.wrap(AcOperatorIdentityres.COLUMN_GUID_AC_RESOURCE));
            }
            acOperatorIdentityresService.deleteByCondition(new WhereCondition()
                    .andEquals(AcOperatorIdentityres.COLUMN_GUID_IDENTITY, IdentityGuid)
                    .andEquals(AcOperatorIdentityres.COLUMN_GUID_AC_RESOURCE, resGuid));
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
            if(StringUtils.isBlank(operatorGuid)) {
                throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("GUID_OPERATOR", "AC_ROLE"));
            }
            if(StringUtils.isBlank(resType)) {
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
            if(!StringUtils.isEquals(resType, ACConstants.RESOURCE_TYPE_ROLE)) {
                if (CollectionUtils.isEmpty(omEmployees)) {
                    return acRoleList;
                }
                employee = omEmployees.get(0);
            }

            switch (resType) {
                case ACConstants.RESOURCE_TYPE_ROLE :
                    List<AcOperatorRole> acOperatorRoles = acOperatorRoleService.query(new WhereCondition().andEquals("GUID_OPERATOR", operatorGuid));
                    List<String> roleGuids = new ArrayList<>();
                    for(AcOperatorRole acOperatorRole : acOperatorRoles) {
                        roleGuids.add(acOperatorRole.getGuidRole());
                    }
                    if(roleGuids.size() > 0) {
                        acRoleList = acRoleService.query(new WhereCondition().andIn("GUID", roleGuids));
                    }
                    break;
                case ACConstants.RESOURCE_TYPE_FUNCTION :
                    // TODO 功能权限待完成
                    break;
                case ACConstants.RESOURCE_TYPE_ORGANIZATION :
                    acRoleList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_ORGANIZATION, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_POSITION :
                    acRoleList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_POSITION, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_DUTY :
                    acRoleList = roleRService.queryEmpPartyRole(ACConstants.PARTY_TYPE_DUTY, employee.getGuid());
                    break;
                case ACConstants.RESOURCE_TYPE_WORKGROUP :
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

            AcOperator acOperator =  new AcOperator();
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
}
