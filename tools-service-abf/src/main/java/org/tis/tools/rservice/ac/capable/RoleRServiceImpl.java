package org.tis.tools.rservice.ac.capable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.CollectionUtils;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcOperatorRole;
import org.tis.tools.model.po.ac.AcPartyRole;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.ac.AcRoleFunc;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.service.ac.*;
import org.tis.tools.rservice.ac.exception.RoleManagementException;
import org.tis.tools.service.ac.exception.ACExceptionCodes;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;

public class RoleRServiceImpl extends BaseRService implements IRoleRService{

    @Autowired
    AcRoleEntityService acRoleEntityService;

    @Autowired
    AcRoleEntityfieldService acRoleEntityfieldService;

    @Autowired
    AcRoleDatascopeService acRoleDatascopeService;

    @Autowired
    AcRoleFuncService acRoleFuncService;

    @Autowired
    AcOperatorRoleService acOperatorRoleService;

    @Autowired
    AcPartyRoleService acPartyRoleService;

    @Autowired
    AcRoleService acRoleService;

    @Autowired
    AcRoleServiceExt acRoleServiceExt;

    /**
     * <p>查询所有角色</p>
     * <p>
     * <pre>
     *     用于展示角色列表
     * </pre>
     *
     * @return
     * @throws RoleManagementException
     */
    @Override
    public List<AcRole> queryAllRole() throws RoleManagementException {
        try {
            return acRoleService.query(new WhereCondition());
        } catch (Exception e) {
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>查询所有角色</p>
     * <p>
     * <pre>
     *     用于展示角色列表,添加应用名称
     * </pre>
     *
     * @return
     * @throws RoleManagementException
     */
    @Override
    public List<Map> queryAllRoleExt() throws RoleManagementException {
        try {
            return acRoleServiceExt.queryAllRoleExt();
        } catch (Exception e) {
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>新增角色</p>
     * <p>
     * <pre>
     *    1.验证传入的对象不能为空
     *    2.验证新增角色所需的必要信息
     *          a.隶属应用:’guidApp’;
     *          b.角色代码:’roleCode’ ;
     *          c.角色名称:’roleName’;  XXX 是否需要唯一
     *          d.角色类别:‘roleType’;
     *    3.添加新增角色所需的其他信息
     *          a.GUID(调用方法)
     *
     *
     * </pre>
     *
     * @param acRole 新增的角色对象
     * @return 完成新增的角色对象
     * @throws RoleManagementException
     */
    @Override
    public AcRole createAcRole(AcRole acRole) throws RoleManagementException {
        try {
            if(null == acRole) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("AC_ROLE", "AcRole"));
            }
            // 校验必要参数
            if(StringUtils.isBlank(acRole.getGuidApp())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("GUID_APP", "AC_ROLE"));
            }
            if(StringUtils.isBlank(acRole.getRoleCode())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("ROLE_CODE", "AC_ROLE"));
            }
            if(StringUtils.isBlank(acRole.getRoleName())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("ROLE_NAME", "AC_ROLE"));
            }
            if(StringUtils.isBlank(acRole.getRoleType())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("ROLE_TYPE", "AC_ROLE"));
            }

            acRole.setGuid(GUID.role());
            acRoleService.insert(acRole);
            return  acRole;
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>修改角色</p>
     * <p>
     * <pre>
     *     业务逻辑
     *    1.验证传入的对象不能为空
     *    2.验证修改角色所需的必要信息
     *          a.隶属应用:’guidApp’;
     *          b.角色代码:’roleCode’ ;
     *          c.角色名称:’roleName’;  XXX 是否需要唯一
     *          d.角色类别:‘roleType’;
     *          e.角色GUID
     *    3.添加修改角色所需的其他信息
     *          无
     *
     *
     * </pre>
     *
     * @param acRole 修改的角色对象
     * @return 完成修改的角色对象
     * @throws RoleManagementException
     */
    @Override
    public AcRole eidtAcRole(AcRole acRole) throws RoleManagementException {
        try {
            if(null == acRole) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_UPDATE, BasicUtil.wrap("AcRole", "AcRole"));
            }
            // 校验必要参数
            if(StringUtils.isBlank(acRole.getGuid())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("GUID", "AC_ROLE"));
            }
            if(StringUtils.isBlank(acRole.getGuidApp())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("GUID_APP", "AC_ROLE"));
            }
            if(StringUtils.isBlank(acRole.getRoleCode())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("ROLE_CODE", "AC_ROLE"));
            }
            if(StringUtils.isBlank(acRole.getRoleName())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("ROLE_NAME", "AC_ROLE"));
            }
            if(StringUtils.isBlank(acRole.getRoleType())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("ROLE_TYPE", "AC_ROLE"));
            }
            acRoleService.update(acRole);
            return  acRole;
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE,
                    BasicUtil.wrap("AC_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>删除角色</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     1.验证传入GUID是否为空
     *     2.验证传入GUID对应的角色是否存在
     *     3.删除与待删角色相关
     *          a.角色与实体属性关系 AC_ROLE_ENTITYFIELD
     *          b.角色与实体关系 AC_ROLE_ENTITY
     *          c.角色与数据范围权限关系 AC_ROLE_DATASCOPE
     *          d.角色与功能对应关系 AC_ROLE_FUNC
     *          e.操作员与权限集（角色）对应关系 AC_OPERATOR_ROLE
     *          f.组织对象与角色对应关系 AAC_PARTY_ROLE
     *     4.删除角色
     *     XXX 删除角色逻辑是否完整
     * </pre>
     *
     * @param guid 角色的GUID
     * @throws RoleManagementException
     */
    @Override
    public void deleteAcRole(String guid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(guid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID", "AC_ROLE"));
            }
            //验证传入GUID对应的角色是否存在
            if(acRoleService.count(new WhereCondition().andEquals("GUID", guid)) != 1) {
                throw new RoleManagementException(ACExceptionCodes.AC_ROLE_IS_NOT_FOUND, BasicUtil.wrap(guid));
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        WhereCondition wc = new WhereCondition();
                        wc.andEquals("GUID_ROLE", guid);
                        // 角色与实体关系 AC_ROLE_ENTITY
                        acRoleEntityService.deleteByCondition(wc);
                        // 角色与实体属性关系 AC_ROLE_ENTITYFIELD
                        acRoleEntityfieldService.deleteByCondition(wc);
                        // 角色与数据范围权限关系 AC_ROLE_DATASCOPE
                        acRoleDatascopeService.deleteByCondition(wc);
                        // 角色与功能对应关系 AC_ROLE_FUNC
                        acRoleFuncService.deleteByCondition(wc);
                        // 操作员与权限集（角色）对应关系 AC_OPERATOR_ROLE
                        acOperatorRoleService.deleteByCondition(wc);
                        // 组织对象与角色对应关系 AC_PARTY_ROLE
                        acPartyRoleService.deleteByCondition(wc);

                        acRoleService.delete(guid);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new RoleManagementException(
                                ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_ROLE", e.getCause().getMessage()));
                    }
                }
            });
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>角色添加功能权限</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     1.验证传入的对象不能为空
     *     2.验证添加角色的功能权限所需的必要信息 XXX 是否需要验证角色、功能是否存在
     *          a.角色GUID
     *          b.功能GUID
     *
     * </pre>
     *
     * @param acRoleFunc
     * @throws RoleManagementException
     */
    @Override
    public void addRoleFunc(AcRoleFunc acRoleFunc) throws RoleManagementException {
        try {
            if(null == acRoleFunc) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("AC_ROLE_FUNC", "AC_ROLE_FUNC"));
            }
            // 校验必要参数
            if(StringUtils.isBlank(acRoleFunc.getGuidFunc())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("GUID_FUNC", "AC_ROLE_FUNC"));
            }
            if(StringUtils.isBlank(acRoleFunc.getGuidRole())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("GUID_ROLE", "AC_ROLE_FUNC"));
            }
            acRoleFuncService.insert(acRoleFunc);
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_ROLE_FUNC", e.getCause().getMessage()));
        }
    }

    /**
     * <p>角色移除功能权限</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和功能GUID移除角色功能权限
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid 角色GUID
     * @param funcGuid 功能GUID
     * @throws RoleManagementException
     */
    @Override
    public void removeRoleFunc(String roleGuid, String funcGuid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_ROLE", "AC_ROLE_FUNC"));
            }
            if(StringUtils.isBlank(funcGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_FUNC", "AC_ROLE_FUNC"));
            }
            acRoleFuncService.deleteByCondition(new WhereCondition().andEquals("GUID_ROLE", roleGuid).andEquals("GUID_FUNC", funcGuid));
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_ROLE_FUNC", e.getCause().getMessage()));
        }
    }

    /**
     * <p>角色移除某应用的全部功能权限</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和功能GUID移除角色功能权限
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid 角色GUID
     * @param appGuid  应用GUID
     * @throws RoleManagementException
     */
    @Override
    public void removeRoleFuncWithApp(String roleGuid, String appGuid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_ROLE", "AC_ROLE_FUNC"));
            }
            if(StringUtils.isBlank(appGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_APP", "AC_ROLE_FUNC"));
            }
            acRoleFuncService.deleteByCondition(new WhereCondition().andEquals("GUID_ROLE", roleGuid).andEquals("GUID_APP", appGuid));
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_ROLE_FUNC", e.getCause().getMessage()));
        }
    }

    /**
     * <p>查询角色的功能权限集合</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应角色功能权限集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid 角色GUID
     * @throws RoleManagementException
     */
    @Override
    public List<AcRoleFunc> queryAllRoleFunByRoleGuid(String roleGuid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("GUID_ROLE", "AC_ROLE_FUNC"));
            }
            return acRoleFuncService.query(new WhereCondition().andEquals("GUID_ROLE", roleGuid));
        } catch (Exception e) {
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_ROLE_FUNC", e.getCause().getMessage()));
        }
    }

    /**
     * <p>角色添加组织对象权限</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     1.验证传入的对象不能为空
     *     2.验证添加角色的组织对象权限所需的必要信息 XXX 是否需要验证角色、组织对象是否存在
     *          a.角色GUID
     *          b.组织对象类型
     *          c.组织对象GUID
     *
     * </pre>
     *
     * @param acPartyRole
     * @throws RoleManagementException
     */
    @Override
    public void addRoleParty(AcPartyRole acPartyRole) throws RoleManagementException {
        try {
            if(null == acPartyRole) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("acPartyRole", "AC_PARTY_ROLE"));
            }
            // 校验必要参数
            if(StringUtils.isBlank(acPartyRole.getGuidParty())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("GUID_PARTY", "AC_PARTY_ROLE"));
            }
            if(StringUtils.isBlank(acPartyRole.getGuidRole())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("GUID_ROLE", "AC_PARTY_ROLE"));
            }
            if(StringUtils.isBlank(acPartyRole.getPartyType())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("PARTY_TYPE", "AC_PARTY_ROLE"));
            }
            acPartyRoleService.insert(acPartyRole);
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_PARTY_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>角色移除组织对象权限</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和组织对象GUID移除角色组织对象权限
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid  角色GUID
     * @param partyGuid 组织对象GUID
     * @throws RoleManagementException
     */
    @Override
    public void removeRoleParty(String roleGuid, String partyGuid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_ROLE", "AC_PARTY_ROLE"));
            }
            if(StringUtils.isBlank(partyGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_PARTY", "AC_PARTY_ROLE"));
            }
            acPartyRoleService.deleteByCondition(new WhereCondition().andEquals("GUID_ROLE", roleGuid).andEquals("GUID_PARTY", partyGuid));
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_PARTY_ROLE", e.getCause().getMessage()));
        }
    }

    @Override
    public void removeRolePartyList(String roleGuid, List<String> partyGuidList) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_ROLE", "AC_PARTY_ROLE"));
            }
            if(CollectionUtils.isEmpty(partyGuidList)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_PARTY LIST", "AC_PARTY_ROLE"));
            }
            acPartyRoleService.deleteByCondition(new WhereCondition().andEquals("GUID_ROLE", roleGuid).andIn("GUID_PARTY", partyGuidList));
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_PARTY_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>查询角色的组织对象权限集合</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应角色组织对象权限集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid  角色GUID
     * @param partyType 组织对象类型
     * @throws RoleManagementException
     */
    @Override
    public List<AcPartyRole> queryAllRoleParty(String roleGuid, String partyType) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("GUID_ROLE", "AC_PARTY_ROLE"));
            }
            if(StringUtils.isBlank(partyType)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("PARTY_TYPE", "AC_PARTY_ROLE"));
            }
            return acPartyRoleService.query(new WhereCondition().andEquals("GUID_ROLE", roleGuid).andEquals("PARTY_TYPE", partyType));
        } catch (Exception e) {
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_PARTY_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>查询角色的组织对象权限集合</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应角色组织对象权限集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid  角色GUID
     * @param partyType 组织对象类型
     * @throws RoleManagementException
     */
    @Override
    public List<Map> queryAllRolePartyExt(String roleGuid, String partyType) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("GUID_ROLE", "AC_PARTY_ROLE"));
            }
            if(StringUtils.isBlank(partyType)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("PARTY_TYPE", "AC_PARTY_ROLE"));
            }
            return acRoleServiceExt.queryAllRolePartyExt(roleGuid, partyType);
        } catch (Exception e) {
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_PARTY_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>角色关联操作员</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     1.验证传入的对象不能为空
     *     2.验证添加角色与操作员关联所需的必要信息 XXX 是否需要验证角色、操作员是否存在
     *          a.角色GUID
     *          b.操作员GUID
     *
     * </pre>
     *
     * @param acOperatorRole
     * @throws RoleManagementException
     */
    @Override
    public void addOperatorRole(AcOperatorRole acOperatorRole) throws RoleManagementException {
        try {
            if(null == acOperatorRole) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("acOperatorRole", "AC_OPERATOR_ROLE"));
            }
            // 校验必要参数
            if(StringUtils.isBlank(acOperatorRole.getGuidOperator())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("GUID_OPERATOR", "AC_OPERATOR_ROLE"));
            }
            if(StringUtils.isBlank(acOperatorRole.getGuidRole())) {
                throw new RoleManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_INSERT, BasicUtil.wrap("GUID_ROLE", "AC_OPERATOR_ROLE"));
            }
            acOperatorRoleService.insert(acOperatorRole);
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>查询角色的组织对象权限集合</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应操作员集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid     角色GUID
     * @throws RoleManagementException
     */
    @Override
    public List<AcOperatorRole> queryAllOperatorRole(String roleGuid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("GUID_ROLE", "AC_OPERATOR_ROLE"));
            }
            return acOperatorRoleService.query(new WhereCondition().andEquals("GUID_ROLE", roleGuid));
        } catch (Exception e) {
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_OPERATOR_ROLE", e.getCause().getMessage()));
        }
    }

    @Override
    public List<Map> queryAllOperatorRoleExt(String roleGuid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap("GUID_ROLE", "AC_OPERATOR_ROLE"));
            }
            return acRoleServiceExt.queryAllOperatorRoleExt(roleGuid);
        } catch (Exception e) {
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_OPERATOR_ROLE", e.getCause().getMessage()));
        }
    }

    /**
     * <p>角色移除操作员</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和操作员GUID移除关联
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid     角色GUID
     * @param operatorGuid 操作员GUID
     * @throws RoleManagementException
     */
    @Override
    public void removeOperatorRole(String roleGuid, String operatorGuid) throws RoleManagementException {
        try {
            if(StringUtils.isBlank(roleGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_ROLE", "AC_OPERATOR_ROLE"));
            }
            if(StringUtils.isBlank(operatorGuid)) {
                throw new RoleManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_DELETE, BasicUtil.wrap("GUID_OPERATOR", "AC_OPERATOR_ROLE"));
            }
            acOperatorRoleService.deleteByCondition(new WhereCondition().andEquals("GUID_ROLE", roleGuid).andEquals("GUID_OPERATOR", operatorGuid));
        } catch (RoleManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RoleManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_OPERATOR_ROLE", e.getCause().getMessage()));
        }
    }
}
