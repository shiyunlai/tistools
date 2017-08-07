/**
 * 
 */
package org.tis.tools.rservice.ac.capable;


import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcOperatorIdentity;
import org.tis.tools.model.po.ac.AcOperatorIdentityres;
import org.tis.tools.rservice.ac.exception.OperatorManagementException;

import java.util.List;

/**
 * 
 * <pre>
 * 
 * 操作员（Operator）管理.</br>
 * 
 * 操作员是功能的操作主体，也是权限控制的针对对象.</br>
 * 
 * 关于“操作员”：
 * <li>每个员工有一个唯一的操作员；
 * <li>操作员身份：每个操作员可有多个角色，有时这些角色不能全部生效，</br>根据需要为操作员设定多个身份，身份代表了部分权限集合，在登陆应用系统时只生效其中某个身份；
 * <li>特殊权限：可直接将功能，或多个功能的操作功能特殊指派给操作员；
 * <li>自定义菜单：操作员根据自身需要，对应用系统默认菜单进行重组，自定义菜单只有操作员登陆时生效；
 * <li>快捷键：将常用功能指定为快捷键启动，目前提供的快捷键设置范围为 ctrl + 0 ~ 9，可按应用系统区分；
 * <li>个性化配置：有些应用系统提供给操作员的首选项配置，允许操作员作出适合使用系统的配置；
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public interface IOperatorRService {

    /**
     * 新增操作员
     * @param acOperator
     * @throws OperatorManagementException
     */
    void createOperator(AcOperator acOperator) throws OperatorManagementException;

    /**
     * 修改操作员
     * @param acOperator
     * @throws OperatorManagementException
     */
    void editOperator(AcOperator acOperator) throws OperatorManagementException;

    /**
     * 删除操作员
     * @param operatorGuid
     *              操作员GUID
     * @throws OperatorManagementException
     */
    void deleteOperator(String operatorGuid) throws OperatorManagementException;

    /**
     * 查询操作员列表
     * @return 操作员对象集合
     * @throws OperatorManagementException
     */
    List<AcOperator> queryOperators() throws OperatorManagementException;

    /**
     * 查询操作员对应的身份集合
     * @param operatorGuid
     * @return
     * @throws OperatorManagementException
     */
    List<AcOperatorIdentity> queryOperatorIdentities(String operatorGuid) throws OperatorManagementException;

    /**
     * 新增操作员身份
     * @param operatorIdentity
     * @throws OperatorManagementException
     */
    void createOperatorIdentity(AcOperatorIdentity operatorIdentity) throws OperatorManagementException;

    /**
     * 修改操作员身份
     * @param operatorIdentity
     * @throws OperatorManagementException
     */
    void editOperatorIdentity(AcOperatorIdentity operatorIdentity) throws OperatorManagementException;

    /**
     * 删除操作员身份
     * @param operatorIdenGuid
     * @throws OperatorManagementException
     */
    void deleteOperatorIdentity(String operatorIdenGuid) throws OperatorManagementException;

    /**
     * 设置默认操作员身份
     * @param operatorIdenGuid
     * @throws OperatorManagementException
     */
    void setDefaultOperatorIdentity(String operatorIdenGuid) throws OperatorManagementException;


    /**
     * 新增操作员身份权限
     * @param operatorIdentityres
     * @throws OperatorManagementException
     */
    void createOperatorIdentityres(AcOperatorIdentityres operatorIdentityres) throws OperatorManagementException;

    /**
     * 修改操作员身份权限
     * @param operatorIdentityres
     * @throws OperatorManagementException
     */
    void editOperatorIdentityres(AcOperatorIdentityres operatorIdentityres) throws OperatorManagementException;

    /**
     * 删除操作员身份权限
     * @param operatorIdentityresGuid
     * @throws OperatorManagementException
     */
    void deleteOperatorIdentityres(String operatorIdentityresGuid) throws OperatorManagementException;

    /**
     * 查询操作员身份对应的权限集合
     * @param operatorIdentityGuid
     * @return
     * @throws OperatorManagementException
     */
    List<AcOperatorIdentityres> queryOperatorIdentityreses(String operatorIdentityGuid) throws OperatorManagementException;


    /**
     * 通过USER_ID 和 OPERATOR_NAME 查询 操作员身份列表
     * @param userId 操作员登录名
     *
     * @param operatorName 操作员姓名
     *
     * @return
     * @throws OperatorManagementException
     */
    List<AcOperatorIdentity> queryOperatorIdentitiesByUserIdAndName(String userId, String operatorName) throws OperatorManagementException;
}
