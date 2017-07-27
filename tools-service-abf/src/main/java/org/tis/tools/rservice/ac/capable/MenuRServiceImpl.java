package org.tis.tools.rservice.ac.capable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.MenuManagementException;
import org.tis.tools.service.ac.AcAppService;
import org.tis.tools.service.ac.AcFuncService;
import org.tis.tools.service.ac.AcFuncgroupService;
import org.tis.tools.service.ac.AcMenuService;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

import java.util.ArrayList;
import java.util.List;

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
                    BasicUtil.wrap(e.getCause().getMessage()));
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
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_ROOT_MENU,
                    BasicUtil.wrap(e.getCause().getMessage()));
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
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_CHILD_MENU,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 创建根菜单
     *
     * @param acMenu
     * @throws MenuManagementException
     */
    @Override
    public void createRootMenu(AcMenu acMenu) throws MenuManagementException {
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
            if(StringUtil.isEmpty(acMenu.getIsleaf())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("ISLEAF"));
            }
            List<AcMenu> acMenus = queryRootMenu(acMenu.getGuidApp());
            if(acMenus.size() > 0) {
                throw new MenuManagementException(
                        ACExceptionCodes.CURRENT_APP_ALREADY_HAVE_ROOT_MENU);
            }
            acMenu.setGuid(GUID.menu());
            acMenu.setMenuSeq(acMenu.getMenuSeq());
            acMenuService.insert(acMenu);
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_CHILD_MENU,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 创建子菜单
     *
     * @param acMenu
     * @throws MenuManagementException
     */
    @Override
    public void createChildMenu(AcMenu acMenu) throws MenuManagementException {
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
            acMenuService.insert(acMenu);
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_QUERY_CHILD_MENU,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 修改菜单
     *
     * @param acMenu
     * @throws MenuManagementException
     */
    @Override
    public void editMenu(AcMenu acMenu) throws MenuManagementException {
        try {
            if(null == acMenu) {
                throw new MenuManagementException(ACExceptionCodes.OBJECT_IS_NULL, BasicUtil.wrap("acMenu"));
            }
            acMenuService.update(acMenu);
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_UPDATE_AC_MENU, BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 删除菜单
     *
     * @param menuGuid
     * @throws MenuManagementException
     */
    @Override
    public void deleteMenu(String menuGuid) throws MenuManagementException {
        try {
            if(StringUtil.isEmpty(menuGuid)) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_MENU"));
            }
            /*查询子菜单，一并删除*/
            List<AcMenu> menuList = acMenuService.query(new WhereCondition().andEquals("GUID_PARENTS", menuGuid));
            List<String> guidList = new ArrayList<String>(menuList.size());
            for (AcMenu menu : menuList) {
                guidList.add(menu.getGuid());
            }
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        if(guidList.size() > 0) {
                            acMenuService.deleteByCondition(new WhereCondition().andIn("GUID", guidList));
                        }
                        acMenuService.delete(menuGuid);
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new MenuManagementException(
                                ACExceptionCodes.FAILURE_WHEN_DELETE_AC_MENU, BasicUtil.wrap(e.getCause().getMessage()));
                    }
                }
            });
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_DELETE_AC_MENU, BasicUtil.wrap(e.getCause().getMessage()));
        }
    }
}
