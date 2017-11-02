/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import org.tis.tools.model.po.ac.*;
import org.tis.tools.rservice.ac.exception.RoleManagementException;

import java.util.List;
import java.util.Map;

/**
 * 
 * <pre>
 * 
 * 角色（Role）管理.</br>
 * 
 * 关于“角色”：
 * <li>角色是功能的权限集合；
 * <li>权限集定义：指定角色与功能（权限资源）间的关联关系；
 * <li>权限赋值：及授权过程，将角色与被授权者建立关联，把功能权限赋值给组织对象（机构、工作组、职务、岗位、操作员）；
 * 
 * </pre>
 * @author megapro
 *
 */
public interface IRoleRService {

    /**
     * <p>查询所有角色</p>
     *
     * <pre>
     *     用于展示角色列表
     * </pre>
     * @return
     * @throws RoleManagementException
     */
    List<AcRole> queryAllRole() throws RoleManagementException;
    /**
     * <p>查询所有角色</p>
     *
     * <pre>
     *     用于展示角色列表,添加应用名称
     * </pre>
     * @return
     * @throws RoleManagementException
     */
    List<Map> queryAllRoleExt() throws RoleManagementException;

    /**
     * <p>新增角色</p>
     *
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
     * @param acRole
     *           新增的角色对象
     * @return
     *           完成新增的角色对象
     * @throws RoleManagementException
     */
    AcRole createAcRole(AcRole acRole) throws RoleManagementException;

    /**
     * <p>修改角色</p>
     *
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
     * @param acRole
     *           修改的角色对象
     * @return
     *           完成修改的角色对象
     * @throws RoleManagementException
     */
    AcRole eidtAcRole(AcRole acRole) throws RoleManagementException;

    /**
     * <p>删除角色</p>
     *
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
     * @param guid
     *          角色的GUID
     * @throws RoleManagementException
     */
    AcRole deleteAcRole(String guid) throws RoleManagementException;

    /**
     * <p>角色添加功能权限</p>
     *
     * <pre>
     *     业务逻辑
     *     1.验证传入的对象不能为空
     *     2.验证添加角色的功能权限所需的必要信息 XXX 是否需要验证角色、功能是否存在
     *          a.角色GUID
     *          b.功能GUID
     *
     * </pre>
     * @param acRoleFunc
     * @throws RoleManagementException
     */
    void addRoleFunc(AcRoleFunc acRoleFunc) throws RoleManagementException;

    /**
     * 配置角色功能
     * @param appGuid
     * @param roleGuid
     * @param funcList
     * @return
     * @throws RoleManagementException
     */
    List<AcRoleFunc> configRoleFunc(String appGuid, String roleGuid, List<AcFunc> funcList) throws RoleManagementException;

    /**
     * <p>角色移除功能权限</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和功能GUID移除角色功能权限
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param roleGuid 角色GUID
     * @param funcGuid  功能GUID
     * @throws RoleManagementException
     */
    void removeRoleFunc(String roleGuid, String funcGuid) throws RoleManagementException;

    /**
     * <p>角色移除某应用的全部功能权限</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和功能GUID移除角色功能权限
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param roleGuid 角色GUID
     * @param appGuid 应用GUID
     * @throws RoleManagementException
     */
    void removeRoleFuncWithApp(String roleGuid, String appGuid) throws RoleManagementException;

    /**
     * <p>查询角色的功能权限集合</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应角色功能权限集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param roleGuid 角色GUID
     * @throws RoleManagementException
     */
    List<AcRoleFunc> queryAllRoleFunByRoleGuid(String roleGuid) throws RoleManagementException;

    /**
     * <p>角色添加组织对象权限</p>
     *
     * <pre>
     *     业务逻辑
     *     1.验证传入的对象不能为空
     *     2.验证添加角色的组织对象权限所需的必要信息 XXX 是否需要验证角色、组织对象是否存在
     *          a.角色GUID
     *          b.组织对象类型
     *          c.组织对象GUID
     *
     * </pre>
     * @param acPartyRole
     * @throws RoleManagementException
     */
    List<AcPartyRole> addRoleParty(List<AcPartyRole> acPartyRole) throws RoleManagementException;

    /**
     * <p>角色移除组织对象权限</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和组织对象GUID移除角色组织对象权限
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param acPartyRoles
     * @throws RoleManagementException
     */
    List<AcPartyRole> removeRoleParty(List<AcPartyRole> acPartyRoles) throws RoleManagementException;


    /**
     * <p>角色批量移除组织对象权限</p>
     * <p>
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和组织对象GUID移除角色组织对象权限
     *     1.验证传入的对象不能为空
     *
     * </pre>
     *
     * @param roleGuid
     *          角色GUID
     * @param partyGuidList
     *          组织对象GUID集合
     * @throws RoleManagementException
     */
    void removeRolePartyList(String roleGuid, List<String> partyGuidList) throws RoleManagementException;

    /**
     * <p>查询角色的组织对象权限集合</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应角色组织对象权限集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param roleGuid 角色GUID
     * @param partyType 组织对象类型
     * @throws RoleManagementException
     */
    List<AcPartyRole> queryAllRoleParty(String roleGuid, String partyType) throws RoleManagementException;


    /**
     * <p>查询角色的组织对象权限集合</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应角色组织对象权限集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param roleGuid 角色GUID
     * @param partyType 组织对象类型
     * @throws RoleManagementException
     */
    List<Map> queryAllRolePartyExt(String roleGuid, String partyType) throws RoleManagementException;


    /**
     * <p>角色关联操作员</p>
     *
     * <pre>
     *     业务逻辑
     *     1.验证传入的对象不能为空
     *     2.验证添加角色与操作员关联所需的必要信息 XXX 是否需要验证角色、操作员是否存在
     *          a.角色GUID
     *          b.操作员GUID
     *
     * </pre>
     * @param acOperatorRoles
     * @throws RoleManagementException
     */
    List<AcOperatorRole> addOperatorRole(List<AcOperatorRole> acOperatorRoles) throws  RoleManagementException;

    /**
     * <p>查询角色的组织对象权限集合</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID查询对应操作员集合
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param roleGuid 角色GUID
     * @throws RoleManagementException
     */
    List<AcOperatorRole> queryAllOperatorRole(String roleGuid) throws  RoleManagementException;

    /**
     * 查询角色下的操作员集合
     * @param roleGuid
     * @return
     * @throws RoleManagementException
     */
    List<Map> queryAllOperatorRoleExt(String roleGuid) throws  RoleManagementException;

    /**
     * <p>角色移除操作员</p>
     *
     * <pre>
     *     业务逻辑
     *     传入角色的GUID和操作员GUID移除关联
     *     1.验证传入的对象不能为空
     *
     * </pre>
     * @param acOperatorRoles
     * @throws RoleManagementException
     */
    List<AcOperatorRole> removeOperatorRole(List<AcOperatorRole> acOperatorRoles) throws  RoleManagementException;


    /**
     * 查询员工所有的权限集合
     * @param userId
     * @return
     * @throws RoleManagementException
     */
    List<AcRole> queryAllRoleByUserId(String userId)  throws  RoleManagementException;

    /**
     * 查询员工对应组织对象下的权限集合
     * @param empGuid
     * @return
     *          包含角色GUID的集合
     * @throws RoleManagementException
     */
    List<AcRole> queryEmpPartyRole(String partyType, String empGuid) throws RoleManagementException;

    /**
     * 查询应用下的所有角色
     * @param appGuid
     *           应用GUID
     * @return
     *           角色集合
     * @throws RoleManagementException
     */
    List<AcRole> queryRoleListInApp(String appGuid) throws RoleManagementException;


    /**
     * 查询操作员所在工作组和岗位相关应用下所有角色
     *    来源  操作员对应员工的工作组和岗位相关应用下所有的角色
     * @param employeeGuid
     *          员工GUID
     * @return
     *          角色集合
     * @throws RoleManagementException
     */
    List<AcRole> queryEmployeeAllPartyRoleList(String employeeGuid) throws RoleManagementException;

    /**
     * 查询操作员未授权角色
     *    来源  操作员对应员工的工作组和岗位相关应用下非继承的未授权给该操作员的角色
     * @param userId
     *          用户ID
     * @return
     *          角色集合
     * @throws RoleManagementException
     */
    List<AcRole> queryOperatorUnauthorizedRoleList(String userId) throws RoleManagementException;

    /**
     * 查询操作员已授权角色
     *    来源  操作员对应的工作组和岗位的应用下非继承的已授权给该操作员的角色
     * @param userId
     *          用户ID
     * @return
     *          角色集合
     * @throws RoleManagementException
     */
    List<AcRole> queryOperatorAuthorizedRoleList(String userId) throws RoleManagementException;

    /**
     * 查询操作员继承角色
     *    来源  操作员对应员工所属机构、岗位、职务、工作组直接关联的角色、无需授权默认拥有
     * @param userId
     *          用户ID
     * @return
     *          角色集合
     * @throws RoleManagementException
     */
    List<AcRole> queryOperatorInheritRoleList(String userId) throws RoleManagementException;

    /**
     * 查询角色在功能下的行为列表
     * @param roleGuid 需要查询的角色GUID
     * @param funcGuid 查询的功能GUID
     * @return 返回该角色拥有此功能的行为列表 {@link AcRoleBhv}
     * @throws RoleManagementException
     */
    List<Map> queryAcRoleBhvsByFuncGuid(String roleGuid, String funcGuid) throws RoleManagementException;

    /**
     * 批量添加角色在功能下的行为列表
     * @param acRoleBhvs 需要添加的行为列表 {@link AcRoleBhv}
     * @return 返回添加的该行为列表 {@link AcRoleBhv}
     * @throws RoleManagementException
     */
    void addAcRoleBhvs(List<AcRoleBhv> acRoleBhvs) throws RoleManagementException;

    /**
     * 批量移除角色在功能下的行为列表
     * @param acRoleBhvs 需要移除的行为列表 {@link AcRoleBhv}
     * @return 返回移除的该行为列表 {@link AcRoleBhv}
     * @throws RoleManagementException
     */
    void removeAcRoleBhvs(List<AcRoleBhv> acRoleBhvs) throws RoleManagementException;

}
