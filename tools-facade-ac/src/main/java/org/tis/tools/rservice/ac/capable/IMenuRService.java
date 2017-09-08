/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.model.po.ac.AcOperatorMenu;
import org.tis.tools.model.vo.ac.AcMenuDetail;
import org.tis.tools.rservice.ac.exception.MenuManagementException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 
 * 菜单（Menu）管理.</br>
 * 
 * 关于“菜单”：
 * <li>每个“应用（Application）”有自己的菜单树；
 * <li>菜单树可多层嵌套；
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public interface IMenuRService {

    /**
     * 查询所有应用系统
     * @return 应用系统集合
     */
    public List<AcApp> queryAllAcApp() throws MenuManagementException;

    /**
     * 查询应用下的根菜单
     * @param GUID_FUNC
     * @return
     */
    List<AcMenu> queryRootMenu(String GUID_FUNC) throws MenuManagementException;

    /**
     * 查询菜单下的子菜单
     * @param GUID_MENU
     * @return
     */
    List<AcMenu> queryChildMenu(String GUID_MENU) throws MenuManagementException;

    /**
     * 创建根菜单
     * @param acMenu
     * @throws MenuManagementException
     */
    AcMenu createRootMenu(AcMenu acMenu) throws MenuManagementException;
    /**
     * 创建重组根菜单
     * @param acOperatorMenu
     * @throws MenuManagementException
     */
    AcOperatorMenu createRootOperatorMenu(AcOperatorMenu acOperatorMenu) throws MenuManagementException;

    /**
     * 创建子菜单
     * @param acMenu
     * @return 创建的菜单
     * @throws MenuManagementException
     */
    AcMenu createChildMenu(AcMenu acMenu) throws MenuManagementException;
    /**
     * 创建子重组菜单
     * @param acOperatorMenu
     * @throws MenuManagementException
     */
    AcOperatorMenu createChildOperatorMenu(AcOperatorMenu acOperatorMenu) throws MenuManagementException;

    /**
     * 修改菜单
     * @param acMenu
     * @return 修改后的菜单
     * @throws MenuManagementException
     */
    AcMenu editMenu(AcMenu acMenu) throws MenuManagementException;
    /**
     * 修改菜单
     * @param acOperatorMenu
     * @throws MenuManagementException
     */
    void editOperatorMenu(AcOperatorMenu acOperatorMenu) throws MenuManagementException;

    /**
     * 删除菜单
     * @param menuGuid
     * @return 删除的菜单
     * @throws MenuManagementException
     */
    AcMenu deleteMenu(String menuGuid) throws MenuManagementException;
    /**
     * 删除重组菜单
     * @param menuGuid
     * @throws MenuManagementException
     */
    void deleteOperatorMenu(String menuGuid) throws MenuManagementException;

    /**
     *  根据用户id和身份查询菜单信息
     *
     * @param userId
     * @param identity
     * @return
     * @throws MenuManagementException
     */
    Map<String, Object> getMenuByUserIdAndIden(String userId, String identity) throws MenuManagementException;

    /**
     *  根据用户id查询该用户拥有的该应用的菜单信息
     *
     * @param userId
     *
     * @return
     * @throws MenuManagementException
     */
    AcMenuDetail getMenuByUserId(String userId, String appGuid) throws MenuManagementException;

    /**
     * 根据用户id和身份查询该用户拥有的该身份下的应用菜单信息
     *
     * @param userId
     * @param appGuid
     * @param identityGuid
     * @return
     * @throws MenuManagementException
     */
    AcMenuDetail getMenuByUserId(String userId, String appGuid, String identityGuid) throws MenuManagementException;

    /**
     *  根据用户id查询应用重组菜单信息
     *
     * @param userId
     * @param appGuid
     * @return
     * @throws MenuManagementException
     */
    AcMenuDetail getOperatorMenuByUserId(String userId, String appGuid) throws MenuManagementException;
    /**
     *  根据用户id和身份查询应用重组菜单信息
     *
     * @param userId
     * @param appGuid
     * @return
     * @throws MenuManagementException
     */
    AcMenuDetail getOperatorMenuByUserId(String userId, String appGuid, String identityGuid) throws MenuManagementException;

    /**
     * 复制菜单到重组菜单
     *
     * @param operatorGuid 操作员GUID
     * @param copyGuid 复制的菜单GUID
     * @param goalGuid 目标的菜单GUID
     * @param order 排序
     *
     * @throws MenuManagementException
     */
    void copyMenuToOperatorMenu(String operatorGuid, String copyGuid, String goalGuid, BigDecimal order) throws MenuManagementException;

    /**
     * 重组菜单移动
     *
     * @param targetGuid       目标菜单GUID
     * @param moveGuid     移动的菜单GUID
     * @param order 排序
     * @throws MenuManagementException
     */
    void moveOperatorMenu(String targetGuid, String moveGuid, BigDecimal order) throws MenuManagementException;

    /**
     * 菜单移动
     *
     * @param targetGuid       目标菜单GUID
     * @param moveGuid     移动的菜单GUID
     * @param order 排序
     * @throws MenuManagementException
     */
    void moveMenu(String targetGuid, String moveGuid, BigDecimal order) throws MenuManagementException;

}
