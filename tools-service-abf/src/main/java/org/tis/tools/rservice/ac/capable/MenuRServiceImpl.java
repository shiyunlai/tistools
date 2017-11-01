package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.vo.ac.AcMenuDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.MenuManagementException;
import org.tis.tools.service.ac.*;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhaoch on 2017/7/16.
 */

public class MenuRServiceImpl extends BaseRService implements IMenuRService{

    @Autowired
    AcAppService acAppService;

    @Autowired
    AcFuncgroupService acFuncgroupService;

    @Autowired
    AcFuncService acFuncService;

    @Autowired
    AcMenuService acMenuService;

    @Autowired
    AcOperatorService acOperatorService;

    @Autowired
    AcOperatorRoleService acOperatorRoleService;

    @Autowired
    AcRoleService acRoleService;

    @Autowired
    AcRoleFuncService acRoleFuncService;

    @Autowired
    AcMenuServiceExt acMenuServiceExt;

    @Autowired
    IRoleRService roleRService;

    @Autowired
    AcOperatorIdentityService acOperatorIdentityService;

    @Autowired
    AcOperatorIdentityresService acOperatorIdentityresService;

    @Autowired
    AcOperatorMenuService acOperatorMenuService;

    @Autowired
    AcOperatorFuncService acOperatorFuncService;

    /**
     * 查询所有应用系统
     *
     * @return 应用系统集合
     */
    @Override
    public List<AcApp> queryAllAcApp() throws MenuManagementException {
        List<AcApp> acAppList = new ArrayList<>();
        try {
            acAppList = acAppService.query(new WhereCondition());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHRN_QUERY_AC_APP,
                    BasicUtil.wrap(e));
        }
        return acAppList;
    }

    /**
     * 查询应用下的根菜单
     *
     * @param GUID_APP
     * @return
     */
    @Override
    public List<AcMenu> queryRootMenu(String GUID_APP) throws MenuManagementException{
        try {
            // 校验传入参数
            if(StringUtil.isEmpty(GUID_APP)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            //查询应用下父节点字段为空的菜单，即为根菜单
            return acMenuService.query(new WhereCondition()
                    .andEquals("GUID_APP", GUID_APP)
                    .andIsNull("GUID_PARENTS"));
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_ROOT_MENU,
                    BasicUtil.wrap(e));
        }

    }

    /**
     * 查询菜单下的子菜单
     *
     * @param GUID_MENU
     * @return
     */
    @Override
    public List<AcMenu> queryChildMenu(String GUID_MENU) throws MenuManagementException {
        try {
            // 校验传入参数
            if(StringUtil.isEmpty(GUID_MENU)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_MENU"));
            }
            return acMenuService.query(new WhereCondition().andEquals("GUID_PARENTS", GUID_MENU));
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_CHILD_MENU,
                    BasicUtil.wrap(e));
        }
    }

    /**
     * 创建根菜单
     *
     * @param acMenu
     * @throws MenuManagementException
     */
    @Override
    public AcMenu createRootMenu(AcMenu acMenu) throws MenuManagementException {
        try {
            if(null == acMenu) {
                throw new MenuManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("AcMenu"));
            }
            // 校验传入参数 菜单名称 菜单显示名称 菜单代码  是否叶子菜单  应用GUID
            if(StringUtil.isEmpty(acMenu.getGuidApp())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuName())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_NAME"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuLabel())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_LABLE"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuCode())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_CODE"));
            }
            List<AcMenu> acMenus = queryRootMenu(acMenu.getGuidApp());
            if(acMenus.size() > 0) {
                throw new MenuManagementException(
                        ACExceptionCodes.CURRENT_APP_ALREADY_HAVE_ROOT_MENU);
            }
            acMenu.setGuid(GUID.menu());
            acMenu.setMenuSeq(acMenu.getGuid());
            acMenu.setGuidParents(null);
            acMenu.setIsleaf(CommonConstants.NO);
            acMenu.setGuidRoot(acMenu.getGuid());
            acMenu.setDisplayOrder(new BigDecimal("0"));
            acMenuService.insert(acMenu);
            return acMenu;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_MENU", e));
        }
    }

    /**
     * 创建重组根菜单
     *
     * @param acOperatorMenu
     * @throws MenuManagementException
     */
    @Override
    public AcOperatorMenu createRootOperatorMenu(AcOperatorMenu acOperatorMenu) throws MenuManagementException {
        try {
            if(null == acOperatorMenu) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("AcOperatorMenu"));
            }
            // 校验传入参数 菜单名称 菜单显示名称 菜单代码  是否叶子菜单  应用GUID
            if(StringUtil.isEmpty(acOperatorMenu.getGuidApp())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getMenuName())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_NAME"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getMenuLabel())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_LABLE"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getGuidOperator())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("ISLEAF"));
            }
            // 判断是否存在根菜单
            if(acOperatorMenuService.count(new WhereCondition()
                    .andEquals(AcOperatorMenu.COLUMN_GUID_OPERATOR, acOperatorMenu.getGuidOperator())
                    .andEquals(AcOperatorMenu.COLUMN_GUID_APP, acOperatorMenu.getGuidApp())
                    .andIsNull(AcOperatorMenu.COLUMN_GUID_PARENTS)) > 0) {
                throw new MenuManagementException(
                        ACExceptionCodes.CURRENT_APP_ALREADY_HAVE_ROOT_MENU);
            }
            acOperatorMenu.setGuid(GUID.operatorMenu());
            acOperatorMenu.setMenuSeq(acOperatorMenu.getGuid());
            acOperatorMenu.setGuidRoot(acOperatorMenu.getGuid());
            acOperatorMenu.setGuidParents(null);
            acOperatorMenu.setIsleaf(CommonConstants.NO);
            acOperatorMenu.setDisplayOrder(new BigDecimal("0"));
            acOperatorMenuService.insert(acOperatorMenu);
            return acOperatorMenu;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_OPERATOR_MENU", e));
        }
    }

    /**
     * 创建子菜单
     *
     * @param acMenu
     * @throws MenuManagementException
     */
    @Override
    public AcMenu createChildMenu(AcMenu acMenu) throws MenuManagementException {
        try {
            if(null == acMenu) {
                throw new MenuManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("AcMenu"));
            }
            // 校验传入参数 菜单名称 菜单显示名称 菜单代码  是否叶子菜单  应用GUID 父菜单GUID
            if(StringUtil.isEmpty(acMenu.getGuidApp())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuName())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_NAME"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuLabel())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_LABLE"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuCode())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_CODE"));
            }
            if(StringUtil.isEmpty(acMenu.getIsleaf())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("ISLEAF"));
            }
            if(StringUtil.isEmpty(acMenu.getGuidParents())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_PARENTS"));
            }
            // 如果是叶子菜单，功能GUID不能为空
            if(StringUtils.equals(acMenu.getIsleaf(), CommonConstants.YES)) {
                if(StringUtil.isEmpty(acMenu.getGuidFunc())) {
                    throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_FUNC"));
                }
            }
            List<AcMenu> list = acMenuService.query(new WhereCondition().andEquals("GUID", acMenu.getGuidParents()));
            /** 查询是否存在父菜单 */
            if (list.size() != 1) {
                throw new MenuManagementException(ACExceptionCodes.MENU_NOT_EXIST_BY_GUID, BasicUtil.wrap(acMenu.getGuidParents()));
            }
            /** 添加菜单GUID和序列*/
            String guid = GUID.menu();
            AcMenu parMenu = list.get(0);
            acMenu.setGuid(guid);
            acMenu.setMenuSeq(parMenu.getMenuSeq() + "." + guid);
            acMenu.setGuidRoot(parMenu.getGuidRoot());
            acMenu.setDisplayOrder(new BigDecimal(acMenuService.count(new WhereCondition().andEquals(AcMenu.COLUMN_GUID_PARENTS, acMenu.getGuidParents()))));
            acMenuService.insert(acMenu);
            return acMenu;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_MENU", e));
        }
    }

    /**
     * 创建子重组菜单
     *
     * @param acOperatorMenu
     * @throws MenuManagementException
     */
    @Override
    public AcOperatorMenu createChildOperatorMenu(AcOperatorMenu acOperatorMenu) throws MenuManagementException {
        try {
            if(null == acOperatorMenu) {
                throw new MenuManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("AcMenu"));
            }
            // 校验传入参数 菜单名称 菜单显示名  是否叶子菜单  应用GUID 父菜单GUID 操作员GUID
            if(StringUtil.isEmpty(acOperatorMenu.getGuidApp())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getGuidOperator())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_OPERATOR"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getMenuName())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_NAME"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getMenuLabel())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_LABLE"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getIsleaf())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("ISLEAF"));
            }
            if(StringUtil.isEmpty(acOperatorMenu.getGuidParents())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_PARENTS"));
            }
            // 如果是叶子菜单，功能GUID不能围攻
            if(StringUtils.equals(acOperatorMenu.getIsleaf(), CommonConstants.YES)) {
                if(StringUtil.isEmpty(acOperatorMenu.getGuidFunc())) {
                    throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_FUNC"));
                }
            }
            List<AcOperatorMenu> list = acOperatorMenuService.query(new WhereCondition().andEquals("GUID", acOperatorMenu.getGuidParents()));
            /** 查询是否存在父菜单 */
            if (list.size() != 1) {
                throw new MenuManagementException(ACExceptionCodes.MENU_NOT_EXIST_BY_GUID, BasicUtil.wrap(acOperatorMenu.getGuidParents()));
            }
            /** 添加菜单GUID和序列*/
            String guid = GUID.operatorMenu();
            AcOperatorMenu parMenu = list.get(0);
            acOperatorMenu.setGuid(guid);
            acOperatorMenu.setMenuSeq(parMenu.getMenuSeq() + "." + guid);
            acOperatorMenu.setGuidRoot(parMenu.getGuidRoot());
            acOperatorMenu.setDisplayOrder(new BigDecimal(acOperatorMenuService.count(new WhereCondition().andEquals(AcMenu.COLUMN_GUID_PARENTS, acOperatorMenu.getGuidParents()))));

            acOperatorMenuService.insert(acOperatorMenu);
            return  acOperatorMenu;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT,
                    BasicUtil.wrap("AC_OPERATOR_MENU", e));
        }
    }

    /**
     * 修改菜单
     *
     * @param acMenu
     * @throws MenuManagementException
     */
    @Override
    public AcMenu editMenu(AcMenu acMenu) throws MenuManagementException {
        try {
            if(null == acMenu) {
                throw new MenuManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("acMenu"));
            }
            if(StringUtil.isEmpty(acMenu.getGuid())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID"));
            }
            AcMenu menu = acMenuService.loadByGuid(acMenu.getGuid());
            if (menu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap(acMenu.getGuid(), "AC_MENU"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuName())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_NAME"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuLabel())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_LABLE"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuCode())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("MENU_CODE"));
            }
            if(StringUtil.isEmpty(acMenu.getIsleaf())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("ISLEAF"));
            }
            // 如果是叶子菜单，功能GUID不能为空
            if(StringUtils.equals(acMenu.getIsleaf(), CommonConstants.YES)) {
                if(StringUtil.isEmpty(acMenu.getGuidFunc())) {
                    throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_FUNC"));
                }
            }
            // 如果为根菜单
            if(StringUtil.isEquals(acMenu.getGuid(), menu.getGuidRoot())) {
                acMenu.setGuidParents(null);
            } else {
                acMenu.setGuidParents(menu.getGuidParents());
            }
            acMenu.setGuidApp(menu.getGuidApp());
            acMenu.setGuidRoot(menu.getGuidRoot());
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        acMenuService.update(acMenu);
                        /** 如果修改了菜单对应的功能GUID和UI入口，对应的重组菜单的功能GUID也需同步 **/
                        if(StringUtils.equals(acMenu.getGuidFunc(), menu.getGuidFunc())) {
                            AcOperatorMenu operatorMenu = new AcOperatorMenu();
                            operatorMenu.setGuidFunc(acMenu.getGuidFunc());
                            acOperatorMenuService.updateByCondition(new WhereCondition().andEquals(AcOperatorMenu.COLUMN_GUID_FUNC, menu.getGuidFunc()), operatorMenu);
                        }
                        if(StringUtils.equals(acMenu.getUiEntry(), menu.getUiEntry())) {
                            AcOperatorMenu operatorMenu = new AcOperatorMenu();
                            operatorMenu.setUiEntry(acMenu.getUiEntry());
                            acOperatorMenuService.updateByCondition(new WhereCondition().andEquals(AcOperatorMenu.COLUMN_UI_ENTRY, menu.getUiEntry()), operatorMenu);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new MenuManagementException(
                                ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_MENU, BasicUtil.wrap(e));
                    }
                }
            });
            return acMenu;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_MENU, BasicUtil.wrap(e));
        }
    }

    /**
     * 修改重组菜单
     *
     * @param acMenu
     * @throws MenuManagementException
     */
    @Override
    public void editOperatorMenu(AcOperatorMenu acMenu) throws MenuManagementException {
        try {
            if(null == acMenu) {
                throw new MenuManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("acMenu"));
            }
            if(StringUtil.isEmpty(acMenu.getGuid())) {
                throw new MenuManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("GUID", "AC_OPERATOR_MENU"));
            }
            AcOperatorMenu operatorMenu = acOperatorMenuService.loadByGuid(acMenu.getGuid());
            if (operatorMenu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap(acMenu.getGuid(), "AC_OPERATOR_MENU"));
            }
            if(StringUtil.isEmpty(acMenu.getMenuName())) {
                throw new MenuManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("MENU_NAME", "AC_OPERATOR_MENU"));
            }
            // FIXME 重组菜单不允许添加功能菜单
//            if(StringUtil.isEmpty(acMenu.getIsleaf())) {
//                throw new MenuManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("ISLEAF", "AC_OPERATOR_MENU"));
//            }
            // 如果是叶子菜单，功能GUID不能为空
            if(StringUtils.equals(acMenu.getIsleaf(), CommonConstants.YES)) {
                if(StringUtil.isEmpty(acMenu.getGuidFunc())) {
                    throw new MenuManagementException(ExceptionCodes.LACK_PARAMETERS_WHEN_UPDATE, BasicUtil.wrap("GUID_FUNC", "AC_OPERATOR_MENU"));
                }
            }
            // 如果为根菜单
            if(StringUtil.isEquals(acMenu.getGuid(), operatorMenu.getGuidRoot())) {
                acMenu.setGuidParents(null);
            } else {
                acMenu.setGuidParents(operatorMenu.getGuidParents());
            }

            acMenu.setGuidOperator(operatorMenu.getGuidOperator());
            acMenu.setGuidApp(operatorMenu.getGuidApp());
            acMenu.setGuidParents(operatorMenu.getGuidParents());
            acMenu.setGuidRoot(operatorMenu.getGuidRoot());
            acMenu.setIsleaf(CommonConstants.NO);
            acOperatorMenuService.update(acMenu);
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_MENU, BasicUtil.wrap(e));
        }
    }

    /**
     * 删除菜单
     *
     * @param menuGuid
     * @throws MenuManagementException
     */
    @Override
    public AcMenu deleteMenu(String menuGuid) throws MenuManagementException {
        try {
            if(StringUtil.isEmpty(menuGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_MENU"));
            }
            /*查询对应菜单是否存在*/
            AcMenu acMenu = acMenuService.loadByGuid(menuGuid);
            if (acMenu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap(menuGuid, AcMenu.TABLE_NAME));
            }
            /*查询子菜单，一并删除*/
            List<AcMenu> menuList = acMenuService.query(new WhereCondition().andFullLike(AcMenu.COLUMN_MENU_SEQ, menuGuid));

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        String parentGuid = "";
                        BigDecimal index = new BigDecimal("0");
                        List<String> guidList = new ArrayList<String>(menuList.size());
                        for (AcMenu menu : menuList) {
                            if(StringUtils.equals(menu.getGuid(), menuGuid)) {
                                parentGuid = menu.getGuidParents();
                                index = menu.getDisplayOrder();
                            }
                            guidList.add(menu.getGuid());
                        }
                        if(guidList.size() > 0) {
                            acMenuService.deleteByCondition(new WhereCondition().andIn("GUID", guidList));
                            // 其余兄弟菜单重新排序
                            acMenuServiceExt.reorderMenu(parentGuid, index, ACConstants.RECORD_AUTO_MINUS);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new MenuManagementException(
                                ACExceptionCodes.FAILURE_WHEN_DELETE_AC_MENU, BasicUtil.wrap(e));
                    }
                }
            });
            return acMenu;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_MENU, BasicUtil.wrap(e));
        }
    }

    /**
     * 删除重组菜单
     *
     * @param menuGuid
     * @throws MenuManagementException
     */
    @Override
    public void deleteOperatorMenu(String menuGuid) throws MenuManagementException {
        try {
            if(StringUtil.isEmpty(menuGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_MENU"));
            }
            /** 查询子菜单，一并删除*/
            List<AcOperatorMenu> menuList = acOperatorMenuService.query(new WhereCondition().andFullLike(AcOperatorMenu.COLUMN_MENU_SEQ, menuGuid));

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        String parentGuid = "";
                        BigDecimal index = new BigDecimal("0");
                        List<String> guidList = new ArrayList<String>(menuList.size());
                        for (AcOperatorMenu menu : menuList) {
                            if(StringUtils.equals(menu.getGuid(), menuGuid)) {
                                parentGuid = menu.getGuidParents();
                                index = menu.getDisplayOrder();
                            }
                            guidList.add(menu.getGuid());
                        }
                        if(guidList.size() > 0) {
                            acOperatorMenuService.deleteByCondition(new WhereCondition().andIn(AcOperatorMenu.COLUMN_GUID, guidList));
                            // 其余兄弟菜单重新排序
                            acMenuServiceExt.reorderOperatorMenu(parentGuid, index, ACConstants.RECORD_AUTO_MINUS);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new MenuManagementException(
                                ACExceptionCodes.FAILURE_WHEN_DELETE_AC_MENU, BasicUtil.wrap(e));
                    }
                }
            });
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_DELETE, BasicUtil.wrap("AC_OPERATOR_MENU", e));
        }
    }

    /**
     * 根据用户id查询菜单信息
     *
     * @param userId
     * @param appGuid
     * @return
     * @throws MenuManagementException
     */
    @Override
    public AcMenuDetail getMenuByUserId(String userId, String appGuid) throws MenuManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            /** 查询对应操作员*/
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals(AcOperator.COLUMN_USER_ID, userId));
            if (acOperators.size() != 1) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap(userId, "AC_OPERATOR"));
            }
            AcOperator operator = acOperators.get(0);
            if (StringUtil.isEmpty(appGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            //查询当前应用的所有父菜单
            List<AcMenu> menuList = acMenuService.query(new WhereCondition()
                    .andEquals("GUID_APP", appGuid)
                    .andEquals("ISLEAF", CommonConstants.NO));

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
                        .andEquals(AcRoleFunc.COLUMN_GUID_APP, appGuid)
                        .andIn(AcRoleFunc.COLUMN_GUID_ROLE, new ArrayList<>(roleGuidList)));
                for (AcRoleFunc roleFunc : acRoleFuncs) {
                    funcGuidList.add(roleFunc.getGuidFunc());
                }
                // 获取角色下的特殊功能列表
                List<AcOperatorFunc> acOperatorFuncs = acOperatorFuncService.query(new WhereCondition()
                        .andEquals(AcOperatorFunc.COLUMN_GUID_APP, appGuid)
                        .andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, operator.getGuid()));
                for (AcOperatorFunc operatorFunc : acOperatorFuncs) {
                    funcGuidList.add(operatorFunc.getGuidFunc());
                }
            }
            if (funcGuidList.size() > 0) {
                // 获取功能下的菜单列表
                List<AcMenu> menus = acMenuService.query(new WhereCondition().andIn("GUID_FUNC", new ArrayList<>(funcGuidList)));
                menuList.addAll(menus);
            }
            return createMenuTree(menuList);

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_MENU", e));
        }
    }

    /**
     * 根据用户id和身份查询该用户拥有的该身份下的应用菜单信息
     *
     * @param userId
     * @param appGuid
     * @param identityGuid
     * @return
     * @throws MenuManagementException
     */
    @Override
    public AcMenuDetail getMenuByUserId(String userId, String appGuid, String identityGuid) throws MenuManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            if (StringUtil.isEmpty(appGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            if (StringUtil.isEmpty(identityGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_IDENTITY"));
            }
            //查询当前应用的所有父菜单
            List<AcMenu> menuList = acMenuService.query(new WhereCondition()
                    .andEquals("GUID_APP", appGuid)
                    .andEquals("ISLEAF", CommonConstants.NO));


            List<AcMenu> menuByUserIdentity = acMenuServiceExt.getMenuByUserIdentity(identityGuid, appGuid);
            menuList.addAll(menuByUserIdentity);
            /*// 查询当前身份拥有的角色菜单
            List<AcOperatorIdentityres> acOperatorIdentityres = acOperatorIdentityresService
                    .query(new WhereCondition().andEquals(AcOperatorIdentityres.COLUMN_GUID_IDENTITY, identityGuid));
            Set<String> roleGuidList = new HashSet<>();
            Set<String> funcGuidList = new HashSet<>();
            for (AcOperatorIdentityres res : acOperatorIdentityres) {
                roleGuidList.add(res.getGuidAcResource());
            }
            if (roleGuidList.size() > 0) {
                // 获取角色下的功能列表
                List<AcRoleFunc> acRoleFuncs = acRoleFuncService.query(new WhereCondition().andIn("GUID_ROLE", new ArrayList<>(roleGuidList)));
                for (AcRoleFunc roleFunc : acRoleFuncs) {
                    funcGuidList.add(roleFunc.getGuidFunc());
                }
            }
            if (funcGuidList.size() > 0) {
                // 获取功能下的菜单列表
                List<AcMenu> menus = acMenuService.query(new WhereCondition().andIn("GUID_FUNC", new ArrayList<>(funcGuidList)));
                menuList.addAll(menus);
            }*/
            return createMenuTree(menuList);

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_MENU", e));
        }
    }

    /**
     * 根据用户id查询重组菜单信息
     *
     * @param userId
     * @param appGuid
     * @return
     * @throws MenuManagementException
     */
    @Override
    public AcMenuDetail getOperatorMenuByUserId(String userId, String appGuid) throws MenuManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            if (StringUtil.isEmpty(appGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if(CollectionUtils.isEmpty(acOperators)) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap("USER_ID "+ userId, "AC_OPERATOR"));
            }
            AcOperator operator = acOperators.get(0);

            // 查询重组菜单
            if (acOperatorMenuService.count(new WhereCondition()
                    .andEquals(AcOperatorMenu.COLUMN_GUID_APP, appGuid)
                    .andEquals("GUID_OPERATOR", operator.getGuid())) > 0) {
                // 当前操作员的所有重组菜单
                List<AcOperatorMenu> menuList = acOperatorMenuService.query(new WhereCondition()
                        .andEquals("GUID_APP", appGuid)
                        .andEquals("GUID_OPERATOR", operator.getGuid()));

                return createOperatorMenuTree(menuList);
            } else {
                return new AcMenuDetail();
            }
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_OPERATOR_MENU", e));
        }
    }

    /**
     * 根据用户id和身份查询应用重组菜单信息
     *
     * @param userId
     * @param appGuid
     * @param identityGuid
     * @return
     * @throws MenuManagementException
     */
    @Override
    public AcMenuDetail getOperatorMenuByUserId(String userId, String appGuid, String identityGuid) throws MenuManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("USER_ID"));
            }
            if (StringUtil.isEmpty(appGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_APP"));
            }
            if (StringUtil.isEmpty(identityGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_IDENTITY"));
            }
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if(CollectionUtils.isEmpty(acOperators)) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap("USER_ID "+ userId, "AC_OPERATOR"));
            }
            AcOperator operator = acOperators.get(0);

            // 查询重组菜单
            if (acOperatorMenuService.count(new WhereCondition()
                    .andEquals(AcOperatorMenu.COLUMN_GUID_APP, appGuid)
                    .andEquals("GUID_OPERATOR", operator.getGuid())) > 0) {

                // 获取当前身份的功能菜单
                // 判断当前操作员是否有该身份
                if (acOperatorIdentityService.count(new WhereCondition()
                        .andEquals(AcOperatorIdentity.COLUMN_GUID_OPERATOR, operator.getGuid())
                        .andEquals(AcOperatorIdentity.COLUMN_GUID, identityGuid)) < 1) {
                    throw new MenuManagementException(ACExceptionCodes.IDENTITY_NOT_CORRESPONDING_TO_USER, BasicUtil.wrap(identityGuid, userId));
                }
                // 查询当前身份拥有的角色菜单
                List<AcOperatorIdentityres> acOperatorIdentityres = acOperatorIdentityresService
                        .query(new WhereCondition().andEquals(AcOperatorIdentityres.COLUMN_GUID_IDENTITY, identityGuid));
                Set<String> roleGuids = new HashSet<>();
                for (AcOperatorIdentityres res : acOperatorIdentityres) {
                    roleGuids.add(res.getGuidAcResource());
                }
                Set<String> funcGuidList = new HashSet<>(); // 功能GUID
                if (roleGuids.size() > 0) {
                    // 获取角色下的功能列表
                    List<AcRoleFunc> acRoleFuncs = acRoleFuncService.query(new WhereCondition().andIn("GUID_ROLE", new ArrayList<>(roleGuids)));
                    for (AcRoleFunc roleFunc : acRoleFuncs) {
                        funcGuidList.add(roleFunc.getGuidFunc());
                    }
                }
                Set<String> menuGuids = new HashSet<>();
                if (funcGuidList.size() > 0) {
                    // 获取功能下的菜单列表
                    List<AcOperatorMenu> menus = acOperatorMenuService.query(new WhereCondition()
                            .andEquals("GUID_APP", appGuid)
                            .andEquals("GUID_OPERATOR", operator.getGuid())
                            .andIn("GUID_FUNC", new ArrayList<>(funcGuidList)));
                    for (AcOperatorMenu menu : menus) {
                        menuGuids.add(menu.getGuid());
                    }
                }
                WhereCondition wc = new WhereCondition();
                wc.andEquals("GUID_APP", appGuid)
                        .andEquals("GUID_OPERATOR", operator.getGuid())
                        .andEquals("ISLEAF", CommonConstants.NO);
                if (menuGuids.size() > 0) {
                    wc.or().andIn("GUID", new ArrayList<>(menuGuids));
                }
                // 用于保存菜单列表
                List<AcOperatorMenu> menuList = acOperatorMenuService.query(wc);
                return createOperatorMenuTree(menuList);
            }
            return new AcMenuDetail();
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_OPERATOR_MENU", e));
        }
    }

    /**
     * 构造菜单树
     *
     * @param menuList
     * @return
     */
    public AcMenuDetail createMenuTree(List<AcMenu> menuList) {
        // 节点列表（散列表，用于临时存储节点对象）
        HashMap<String, AcMenuDetail> nodeList = new HashMap<>();
        // 根节点
        AcMenuDetail root = null;
        // 根据结果集构造节点列表（存入散列表）
        for (Iterator it = menuList.iterator(); it.hasNext();) {
            AcMenu menu = (AcMenu) it.next();
            AcMenuDetail node = new AcMenuDetail();
            node.setGuid(menu.getGuid());
            node.setLabel(menu.getMenuLabel());
            node.setParentGuid(menu.getGuidParents());
            node.setIsLeaf(menu.getIsleaf());
            node.setIcon(menu.getExpandPath());
            node.setOrder(menu.getDisplayOrder());
            node.setHref(menu.getUiEntry());
            nodeList.put(node.getGuid(), node);
        }
        // 构造多叉树
        Set entrySet = nodeList.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext();) {
            // 获取当前节点
            AcMenuDetail node = (AcMenuDetail) ((Map.Entry) it.next()).getValue();
            // 如果当前节点的父节点为空，则为根节点
            if (StringUtils.isBlank(node.getParentGuid())) {
                root = node;
            } else { // 不是父节点，则获取它的父节点将当前节点添加到子节点中
                nodeList.get(node.getParentGuid()).addChild(node);
            }
        }
        root.sortChildren();
        return root;
    }

    /**
     * 构造菜单树
     *
     * @param menuList
     * @return
     */
    public AcMenuDetail createOperatorMenuTree(List<AcOperatorMenu> menuList) {
        // 节点列表（散列表，用于临时存储节点对象）
        HashMap<String, AcMenuDetail> nodeList = new HashMap<>();
        // 根节点
        AcMenuDetail root = null;
        // 根据结果集构造节点列表（存入散列表）
        for (Iterator it = menuList.iterator(); it.hasNext();) {
            AcOperatorMenu menu = (AcOperatorMenu) it.next();
            AcMenuDetail node = new AcMenuDetail();
            node.setGuid(menu.getGuid());
            node.setLabel(menu.getMenuLabel());
            node.setParentGuid(menu.getGuidParents());
            node.setIsLeaf(menu.getIsleaf());
            node.setIcon(menu.getExpandPath());
            node.setOrder(menu.getDisplayOrder());
            node.setHref(menu.getUiEntry());
            nodeList.put(node.getGuid(), node);
        }
        // 构造多叉树
        Set entrySet = nodeList.entrySet();
        for (Iterator it = entrySet.iterator(); it.hasNext();) {
            // 获取当前节点
            AcMenuDetail node = (AcMenuDetail) ((Map.Entry) it.next()).getValue();
            // 如果当前节点的父节点为空，则为根节点
            if (StringUtils.isBlank(node.getParentGuid())) {
                root = node;
            } else { // 不是父节点，则获取它的父节点将当前节点添加到子节点中
                nodeList.get(node.getParentGuid()).addChild(node);
            }
        }

        root.sortChildren();
        return root;
    }

    /**
     * 根据用户id和身份查询菜单信息
     *
     * @param userId
     * @param identityGuid
     * @return
     * @throws MenuManagementException
     */
    @Override
    public Map<String, Object> getMenuByUserIdAndIden(String userId, String identityGuid) throws MenuManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("userId"));
            }
            if (StringUtil.isEmpty(identityGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("identityGuid"));
            }
            //获取当前用户的角色列表
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if (acOperators.size() != 1) {
                throw new MenuManagementException(ACExceptionCodes.USER_ID_NOT_EXIST, BasicUtil.wrap(userId));
            }

//            AcOperator acOperator = acOperators.get(0);
//            List<AcMenu> menusByUserId = acMenuServiceExt.getMenuByUserId(acOperator.getUserId());
//            List<AcOperatorRole> acOperatorRoles = acOperatorRoleService.query(new WhereCondition().andEquals("OPERATOR_GUID", acOperator.getGuid()));
//            for(AcOperatorRole acOperatorRole : acOperatorRoles) {
//                AcRole acRole = acRoleService.query(new WhereCondition().andEquals("GUID", acOperatorRole.getGuidRole())).get(0);
//            }

            return null;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_LOGIN,
                    BasicUtil.wrap(e));
        }
    }

    /**
     * 复制菜单到重组菜单
     *
     * @param operatorGuid 操作员GUID
     * @param copyGuid     复制的菜单GUID
     * @param goalGuid     目标的菜单GUID
     * @param order 排序
     *
     * @throws MenuManagementException
     */
    @Override
    public void copyMenuToOperatorMenu(String operatorGuid, String copyGuid, String goalGuid, BigDecimal order) throws MenuManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(operatorGuid)) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap(AcOperatorMenu.COLUMN_GUID_OPERATOR, "AC_OPERATOR_MENU"));
            }
            if (StringUtil.isEmpty(copyGuid)) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("GUID_COPY_MENU", "AC_OPERATOR_MENU"));
            }
            if (StringUtil.isEmpty(goalGuid)) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("GUID_GOAL_MENU", "AC_OPERATOR_MENU"));
            }
            if (null == order) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("ORDER", "AC_OPERATOR_MENU"));
            }
            // 查询复制的菜单信息及其下属的所有菜单信息
            AcMenu copyMenu = acMenuService.loadByGuid(copyGuid);
            String copySeq = copyMenu.getMenuSeq();
            AcOperatorMenu goalMenu = acOperatorMenuService.loadByGuid(goalGuid);
            if(goalMenu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        BasicUtil.wrap("GUID '" + goalGuid + "' ", "AC_OPERATOR_MENU"));
            }


            // 查询复制菜单下的所有子菜单
            List<AcMenu> copyMenus = acMenuService.query(new WhereCondition().andFullLike(AcMenu.COLUMN_MENU_SEQ, copyGuid));
            // 节点列表（散列表，用于临时存储节点对象）
            HashMap<String, AcMenuDetail> nodeList = new HashMap<>();
            // 根节点
            AcMenuDetail root = null;
            // 根据结果集构造节点列表（存入散列表）
            for (Iterator it = copyMenus.iterator(); it.hasNext();) {
                AcMenu menu = (AcMenu) it.next();
                AcMenuDetail node = new AcMenuDetail();
                node.setGuid(menu.getGuid());
                node.setParentGuid(menu.getGuidParents());
                node.setIsLeaf(menu.getIsleaf());
                node.setAcMenu(menu);
                nodeList.put(node.getGuid(), node);
            }
            // 构造多叉树
            Set entrySet = nodeList.entrySet();
            for (Iterator it = entrySet.iterator(); it.hasNext();) {
                // 获取当前节点
                AcMenuDetail node = (AcMenuDetail) ((Map.Entry) it.next()).getValue();
                // 如果当前节点的父节点为空，则为根节点
                if (StringUtils.equals(node.getParentGuid(), copyMenu.getGuidParents())) {
                    root = node;
                } else { // 不是父节点，则获取它的父节点将当前节点添加到子节点中
                    nodeList.get(node.getParentGuid()).addChild(node);
                }
            }
            // 创建一个新菜单信息，保存目标
            AcMenu temp = new AcMenu();
            temp.setGuid(goalMenu.getGuid());
            temp.setMenuSeq(goalMenu.getMenuSeq());
            temp.setGuidRoot(goalMenu.getGuidRoot());
            // 重构GUID
            root.restructureGuidForOperatorMenu(temp);

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        // 重新排序当前父菜单下的子菜单自增
                        acMenuServiceExt.reorderOperatorMenu(goalGuid, order, ACConstants.RECORD_AUTO_PLUS);
                        // 插入复制的菜单
                        for (AcMenu acMenu : copyMenus) {
                            AcOperatorMenu childMenu = new AcOperatorMenu();
                            BeanUtils.copyProperties(acMenu, childMenu);
                            childMenu.setGuidOperator(operatorGuid);
                            // 如果是根节点
                            if(StringUtils.equals(acMenu.getGuidParents(), goalGuid)) {
                                childMenu.setDisplayOrder(order);
                            }
                            acOperatorMenuService.insert(childMenu);
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new MenuManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("AC_OPERATOR_MENU", e));
                    }
                }
            });

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("AC_OPERATOR_MENU", e));
        }
    }

    /**
     * 重组菜单移动
     *
     * @param targetGuid       目标菜单GUID
     * @param moveGuid     移动的菜单GUID
     * @param order 排序
     * @throws MenuManagementException
     */
    @Override
    public void moveOperatorMenu(String targetGuid, String moveGuid, BigDecimal order) throws MenuManagementException {
        try {
            if (StringUtil.isEmpty(targetGuid)) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("GUID_GOAL_MENU", "AC_OPERATOR_MENU"));
            }
            if (StringUtil.isEmpty(moveGuid)) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("GUID_MOVE_MENU", "AC_OPERATOR_MENU"));
            }
            if (null == order) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("ORDER", "AC_OPERATOR_MENU"));
            }
            // 查询移动的菜单信息及其下属的所有菜单信息
            AcOperatorMenu moveMenu = acOperatorMenuService.loadByGuid(moveGuid);
            if(moveMenu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        BasicUtil.wrap("GUID '" + moveGuid + "' ", "AC_OPERATOR_MENU"));
            }

            List<AcOperatorMenu> childMenus = acOperatorMenuService.query(new WhereCondition().andFullLike(AcOperatorMenu.COLUMN_MENU_SEQ, moveGuid));
            // 目标菜单节点
            AcOperatorMenu goalMenu = acOperatorMenuService.loadByGuid(targetGuid);
            if(goalMenu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        BasicUtil.wrap("GUID '" + targetGuid + "' ", "AC_OPERATOR_MENU"));
            }
            // 源菜单节点
            String sourceGuid = moveMenu.getGuidParents(); // 源菜单GUID
            BigDecimal sourceOrder = moveMenu.getDisplayOrder(); // 源菜单显示顺序
            String sourceSeq = moveMenu.getMenuSeq();


            // 处理移动菜单信息
            moveMenu.setGuidParents(targetGuid); // 改变父菜单信息
            moveMenu.setMenuSeq(goalMenu.getMenuSeq() + "." + moveGuid); // 改变序列
            moveMenu.setDisplayOrder(order); // 改变显示顺序
            

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        // 重新排序源菜单下的子菜单自减
                        acMenuServiceExt.reorderOperatorMenu(sourceGuid, sourceOrder, ACConstants.RECORD_AUTO_MINUS);
                        // 重新排序目标菜单下的子菜单自增
                        acMenuServiceExt.reorderOperatorMenu(targetGuid, order, ACConstants.RECORD_AUTO_PLUS);
                        // 更改移动的重组菜单信息
                        acOperatorMenuService.update(moveMenu);
                        // 如果改变了父节点需要同步改变子节点
                        if (!StringUtils.equals(moveMenu.getGuidParents(), targetGuid)) {
                            // 更改移动菜单下的子菜单
                            for (AcOperatorMenu childMenu : childMenus) {
                                // 排除当前移动菜单
                                if (!StringUtils.equals(childMenu.getGuid(), moveGuid)) {
                                    // 更新菜单序列
                                    // update 表名 set 字段名=REPLACE (字段名,'原来的值','要修改的值')
                                    String seq = childMenu.getMenuSeq();
                                    AcOperatorMenu operatorMenu = new AcOperatorMenu();
                                    operatorMenu.setGuid(childMenu.getGuid());
                                    operatorMenu.setMenuSeq(seq.replace(sourceSeq, moveMenu.getMenuSeq()));
                                    acOperatorMenuService.update(operatorMenu);
                                }
                            }
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new MenuManagementException(
                                ExceptionCodes.FAILURE_WHEN_UPDATE, BasicUtil.wrap("AC_OPERATOR_MENU", e));
                    }
                }
            });

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE, BasicUtil.wrap("AC_OPERATOR_MENU", e));
        }
    }
    /**
     * 菜单移动
     *
     * @param targetGuid       目标菜单GUID
     * @param moveGuid     移动的菜单GUID
     * @param order 排序
     * @throws MenuManagementException
     */
    @Override
    public void moveMenu(String targetGuid, String moveGuid, BigDecimal order) throws MenuManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(targetGuid)) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("GUID_GOAL_MENU", "AC_OPERATOR_MENU"));
            }
            if (StringUtil.isEmpty(moveGuid)) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("GUID_MOVE_MENU", "AC_OPERATOR_MENU"));
            }
            if (null == order) {
                throw new MenuManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                        BasicUtil.wrap("ORDER", "AC_OPERATOR_MENU"));
            }
            // 查询移动的菜单信息及其下属的所有菜单信息
            AcMenu moveMenu = acMenuService.loadByGuid(moveGuid);
            if(moveMenu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        BasicUtil.wrap("GUID '" + moveGuid + "' ", "AC_MENU"));
            }

            List<AcMenu> childMenus = acMenuService.query(new WhereCondition().andFullLike(AcMenu.COLUMN_MENU_SEQ, moveGuid));
            // 目标菜单节点
            AcMenu goalMenu = acMenuService.loadByGuid(targetGuid);
            if(goalMenu == null) {
                throw new MenuManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY,
                        BasicUtil.wrap("GUID '" + targetGuid + "' ", "AC_MENU"));
            }
            // 源菜单节点
            String sourceGuid = moveMenu.getGuidParents(); // 源菜单GUID
            BigDecimal sourceOrder = moveMenu.getDisplayOrder(); // 源菜单显示顺序
            String sourceSeq = moveMenu.getMenuSeq();


            // 处理移动菜单信息
            moveMenu.setGuidParents(goalMenu.getGuid()); // 改变父菜单信息
            moveMenu.setMenuSeq(goalMenu.getMenuSeq() + "." + moveGuid); // 改变序列
            moveMenu.setDisplayOrder(order); // 改变显示顺序


            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        // 重新排序源菜单下的子菜单自减
                        acMenuServiceExt.reorderMenu(sourceGuid, sourceOrder, ACConstants.RECORD_AUTO_MINUS);
                        // 重新排序目标菜单下的子菜单自增
                        acMenuServiceExt.reorderMenu(targetGuid, order, ACConstants.RECORD_AUTO_PLUS);
                        // 更改移动的重组菜单信息
                        acMenuService.update(moveMenu);

                        // 如果改变了父节点需要同步改变子节点
                        if (!StringUtils.equals(moveMenu.getGuidParents(), targetGuid)) {
                            // 更改移动菜单下的子菜单
                            for (AcMenu childMenu : childMenus) {
                                // 排除当前移动菜单
                                if (!StringUtils.equals(childMenu.getGuid(), moveGuid)) {
                                    // 更新菜单序列
                                    // update 表名 set 字段名=REPLACE (字段名,'原来的值','要修改的值')
                                    String seq = childMenu.getMenuSeq();
                                    AcMenu acMenu = new AcMenu();
                                    acMenu.setGuid(childMenu.getGuid());
                                    acMenu.setMenuSeq(seq.replace(sourceSeq, moveMenu.getMenuSeq()));
                                    acMenuService.update(acMenu);
                                }
                            }
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new MenuManagementException(
                                ExceptionCodes.FAILURE_WHEN_UPDATE, BasicUtil.wrap("AC_MENU", e));
                    }
                }
            });

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE, BasicUtil.wrap("AC_MENU", e));
        }
    }
}
