package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.vo.ac.AcMenuDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.MenuManagementException;
import org.tis.tools.rservice.ac.exception.MenuManagementException;
import org.tis.tools.service.ac.*;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

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
    AcOperatorIdentityresService acOperatorIdentityresService;

    @Autowired
    AcOperatorMenuService acOperatorMenuService;

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
            acMenu.setMenuSeq(acMenu.getGuid());
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
            if(StringUtil.isEmpty(acMenu.getGuidFunc())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_FUNC"));
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
            if(StringUtil.isEmpty(acMenu.getGuid())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID"));
            }
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
            if(StringUtil.isEmpty(acMenu.getGuidFunc())) {
                throw new MenuManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("GUID_FUNC"));
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
                List<AcRoleFunc> acRoleFuncs = acRoleFuncService.query(new WhereCondition().andIn("GUID_ROLE", new ArrayList<>(roleGuidList)));
                for (AcRoleFunc roleFunc : acRoleFuncs) {
                    funcGuidList.add(roleFunc.getGuidFunc());
                }
            }
            if (funcGuidList.size() > 0) {
                // 获取功能下的菜单列表
                List<AcMenu> menus = acMenuService.query(new WhereCondition().andIn("GUID_FUNC", new ArrayList<>(funcGuidList)));
                menuList.addAll(menus);
            }
            return createMenuTree(menuList);

        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_MENU", e.getCause().getMessage()));
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

        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_MENU", e.getCause().getMessage()));
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
            if (acOperatorMenuService.count(new WhereCondition().andEquals("GUID_OPERATOR", operator.getGuid())) > 0) {
                // 当前操作员的所有重组菜单
                List<AcOperatorMenu> menuList = acOperatorMenuService.query(new WhereCondition()
                        .andEquals("GUID_APP", appGuid)
                        .andEquals("GUID_OPERATOR", operator.getGuid()));

                return createOperatorMenuTree(menuList);
            }
            return new AcMenuDetail();
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_OPERATOR_MENU", e.getCause().getMessage()));
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
            if (acOperatorMenuService.count(new WhereCondition().andEquals("GUID_OPERATOR", operator.getGuid())) > 0) {

                // 获取当前身份的功能菜单
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
                    List<AcMenu> menus = acMenuService.query(new WhereCondition().andIn("GUID_FUNC", new ArrayList<>(funcGuidList)));
                    for (AcMenu menu : menus) {
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
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    BasicUtil.wrap("AC_OPERATOR_MENU", e.getCause().getMessage()));
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
            if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(node.getParentGuid())) {
                root = node;
            } else { // 不是父节点，则获取它的父节点将当前节点添加到子节点中
                nodeList.get(node.getParentGuid()).addChild(node);
            }
        }
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
            if (com.alibaba.dubbo.common.utils.StringUtils.isBlank(node.getParentGuid())) {
                root = node;
            } else { // 不是父节点，则获取它的父节点将当前节点添加到子节点中
                nodeList.get(node.getParentGuid()).addChild(node);
            }
        }
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
        } catch (MenuManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MenuManagementException(
                    ACExceptionCodes.FAILURE_WHEN_LOGIN,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }
}
