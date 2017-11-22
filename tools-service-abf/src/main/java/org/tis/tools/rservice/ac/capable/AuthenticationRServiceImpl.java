package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.dto.shiro.AbfPermission;
import org.tis.tools.model.dto.shiro.PasswordHelper;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.AuthManagementException;
import org.tis.tools.service.ac.*;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

import java.util.*;
import java.util.stream.Collectors;

import static org.tis.tools.common.utils.BasicUtil.surroundBracketsWithLFStr;
import static org.tis.tools.common.utils.BasicUtil.wrap;

/**
 * Created by zhaoch on 2017/7/20.
 */
public class AuthenticationRServiceImpl extends BaseRService implements IAuthenticationRService {

    @Autowired
    AcOperatorService acOperatorService;

    @Autowired
    AcOperatorIdentityService acOperatorIdentityService;

    @Autowired
    AcOperatorMenuService acOperatorMenuService;

    @Autowired
    IMenuRService menuRService;

    @Autowired
    IOperatorRService operatorRService;

    @Autowired
    AcOperatorBhvService acOperatorBhvService;

    @Autowired
    IRoleRService roleRService;

    @Autowired
    AcRoleFuncService acRoleFuncService;

    @Autowired
    AcOperatorFuncService acOperatorFuncService;

    @Autowired
    AcFuncService acFuncService;

    @Autowired
    AcFuncBhvService acFuncBhvService;

    @Autowired
    AcBhvDefService acBhvDefService;

    @Autowired
    AcFuncResourceService acFuncResourceService;

    @Autowired
    AcAppService acAppService;

    @Autowired
    IApplicationRService applicationRService;

    @Autowired
    AcOperatorServiceExt acOperatorServiceExt;

    @Autowired
    AcFuncServiceExt acFuncServiceExt;

    /**
     *   用户状态检查
     * a)	判断用户是否存在；
     * b)	用户状态只能是“退出、正常、挂起”，否则报错提示；
     * c)	检查是否在允许的时间范围内；
     * d)	检查是否在运行MAC范围内；
     * e)	检查是否在运行的IP地址范围内；
     *   ABF只验证ａ、ｂ
     * @param userId
     *          用户名
     *          IP地址
     * @return 操作员身份集合
     *
     * @throws ToolsRuntimeException
     *
     */
    @Override
    public List<AcOperatorIdentity> userStatusCheck(String userId) throws AuthManagementException {

        try {
            // 校验传入参数
            if (StringUtils.isBlank(userId)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("userId"));
            }
            //判断用户是否存在
            //判断用户状态，只能是“退出、正常、挂起”，否则报错提示
            AcOperator acOperator = operatorRService.queryOperatorByUserId(userId);
            String operatorStatus = acOperator.getOperatorStatus();
            if(!StringUtil.isEqualsIn(operatorStatus,
                    ACConstants.OPERATE_STATUS_LOGOUT,
                    ACConstants.OPERATE_STATUS_LOGIN,
                    ACConstants.OPERATE_STATUS_PAUSE)) {
                throw new AuthManagementException(ACExceptionCodes.USER_STATUS_NOT_ALLOW_LOGIN, wrap(operatorStatus));
            }
            return acOperatorIdentityService.query(new WhereCondition().andEquals("GUID_OPERATOR", acOperator.getGuid()));

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ACExceptionCodes.CHECK_USER_STATUS_ERROR,
                    wrap(e));
        }

    }

//    /**
//     * 用户登录
//     * a)	用户必须存在；
//     * b)	用户状态只能是“退出、正常、挂起”，否则报错提示；
//     * c)	验证用户密码（关于密码，新建用户时，加密存储；采用加密传输；加密方法提供为工具类 CryptographyUtil）；
//     * d)	认证错误即锁定解锁的相关处理——密码错误时需要记录“错误登陆次数”；达到“锁定次数限制”时，更新状态变为“锁定”，记录“锁定时间”（由管理员解锁后才能再登陆——用户管理中需要增加解锁功能）；
//     *      如果密码正确则重置错误登陆次数＝0；
//     * e)	登陆验证通过，修改用户状态为“正常”，记录“最近登陆时间”；
//     * f)	如果验证通过，返回以下用户信息，用作主界面展示：
//     * 	用户信息（AC_OPERATOR）；
//     * 	根据当前登陆身份的权限滤后的菜单——应用系统菜单（AC_MENU）或重组菜单（AC_OPERATOR_MENU）；
//     * 	用户在该应用系统中的快捷菜单（AC_OPERATOR_SHORTCUT）；
//     * 	用户在该应用系统中的个性化配置（AC_OPERATOR_CONFIG）；
//     * XXX 密码失效日期逻辑？
//     * @param userId   用户名
//     * @param password
//     * @param identityGuid
//     * @param appGuid
//     *
//     * @return 用户检查结果
//     * @throws ToolsRuntimeException
//     */
    /*@Override
    public AcOperator loginCheck(String userId, String password, String identityGuid, String appGuid) throws AuthManagementException {
        try {
            // 校验传入参数
            if (StringUtils.isBlank(userId)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("userId"));
            }
            if (StringUtils.isBlank(password)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("password"));
            }
            if (StringUtils.isBlank(identityGuid)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("identityGuid"));
            }
            // 判断用户是否存在
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if (acOperators.size() != 1) {
                throw new AuthManagementException(ACExceptionCodes.USER_ID_NOT_EXIST,
                        wrap(surroundBracketsWithLFStr(AcOperator.COLUMN_USER_ID, userId)));
            }
            // 判断用户状态，只能是“退出、正常、挂起”，否则报错提示
            AcOperator acOperator = acOperators.get(0);
            String operatorStatus = acOperator.getOperatorStatus();
            if(!StringUtil.isEqualsIn(operatorStatus,
                    ACConstants.OPERATE_STATUS_LOGOUT,
                    ACConstants.OPERATE_STATUS_LOGIN,
                    ACConstants.OPERATE_STATUS_PAUSE)) {
                throw new AuthManagementException(ACExceptionCodes.USER_STATUS_NOT_ALLOW_LOGIN, wrap(operatorStatus));
            }
            // 验证用户密码
            // 如果密码错误
            if (! StringUtils.isEquals(acOperator.getPassword(),CryptographyUtil.md5(password))) {
                // 当前错误次数
                BigDecimal curErrCount = acOperator.getErrCount().add(new BigDecimal("1"));
                // 如果当前错误次数大于允许的错误次数，
                if(acOperator.getLockLimit().compareTo(curErrCount) < 0) {
                    // 状态设置为锁定，更新锁定时间
                    acOperator.setOperatorStatus(ACConstants.OPERATE_STATUS_LOCK);
                    acOperator.setLockTime(new Date());
                } else {// 否则更新数据库错误次数
                    acOperator.setErrCount(curErrCount);
                }
                //保存数据库
                acOperatorService.update(acOperator);
                throw new AuthManagementException(ACExceptionCodes.PASSWORD_IS_WRONG, wrap(userId));
            } else { // 如果验证密码成功
                // 验证应用权限
                if (applicationRService.queryOperatorAllApp(userId)
                        .stream()
                        .filter(app -> app.getGuid().equals(appGuid))
                        .count() < 1) {
                    throw new AuthManagementException(ACExceptionCodes.PERMISSION_DENIED);
                }
                // 设置登录状态
                acOperator.setOperatorStatus(ACConstants.OPERATE_STATUS_LOGIN);
                // 数据库错误次数置0
                acOperator.setErrCount(new BigDecimal("0"));
                // 记录登陆时间
                acOperator.setLastLogin(new Date());
                // 保存数据库
                acOperatorService.update(acOperator);
            }
            // 清空密码返回
            acOperator.setPassword(null);
            return acOperator;

        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ACExceptionCodes.FAILURE_WHEN_LOGIN,
                    wrap(e));
        }
    }*/

    @Override
    public AcOperator loginCheck(String userId, String identity, String appCode) throws AuthManagementException {
        // 校验传入参数
        if (StringUtils.isBlank(userId)) {
            throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("userId"));
        }
        if (StringUtils.isBlank(identity)) {
            throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("identity"));
        }
        if (StringUtils.isBlank(appCode)) {
            throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("appCode"));
        }
        // 判断用户是否存在
        List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
        if (acOperators.size() != 1) {
            throw new AuthManagementException(ACExceptionCodes.USER_ID_NOT_EXIST,
                    wrap(surroundBracketsWithLFStr(AcOperator.COLUMN_USER_ID, userId)));
        }
        // 验证用户是否有该应用权限
        List<AcApp> acApps = applicationRService.queryOperatorAllApp(userId);
        long count = acApps.stream().filter(acApp -> StringUtils.isEquals(acApp.getAppCode(), appCode)).count();
        if(count < 1) {
            throw new AuthManagementException(ACExceptionCodes.PERMISSION_DENIED);
        }
        return acOperators.get(0);
        //
    }

    /**
     * 根据用户id和身份查询菜单信息等初始化信息
     * <p>
     *   根据当前登陆身份的权限滤后的菜单——应用系统菜单（AC_MENU）或重组菜单（ ）；
     * 	用户在该应用系统中的快捷菜单（AC_OPERATOR_SHORTCUT）； TODO 快捷菜单
     * 	用户在该应用系统中的个性化配置（AC_OPERATOR_CONFIG）； TODO 个性化配置
     *
     * @param userId
     * @param identityGuid
     * @param appCode
     * @throws AuthManagementException
     */
    @Override
    public Map<String, Object> getInitInfoByUserIdAndIden(String userId, String identityGuid, String appCode) throws AuthManagementException {
        // 校验传入参数
        if (StringUtils.isBlank(userId)) {
            throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("userId"));
        }
        if (StringUtils.isBlank(identityGuid)) {
            throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("identityGuid"));
        }
        if (StringUtils.isBlank(appCode)) {
            throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("appCode"));
        }
        try {
            AcOperator operator = operatorRService.queryOperatorByUserId(userId);
            AcApp acApp = applicationRService.queryAcAppByCode(appCode);
            List<AcConfig> acConfigs = operatorRService.queryOperatorConfig(userId, acApp.getGuid());
            // 查询用户配置是否启用重组菜单
            final boolean[] enableRecombine = {false};
            acConfigs.stream()
                    .filter(cfg -> StringUtils.isEquals(cfg.getGuidApp(), acApp.getGuid()) &&
                            StringUtils.isEquals(cfg.getConfigType(), ACConstants.CONFIG_TYPE_MENUREORG) &&
                            StringUtils.isEquals(cfg.getConfigValue(), CommonConstants.YES))
                    .findFirst()
                    .ifPresent(a -> enableRecombine[0] = true);
            Map<String, Object> resultInfo = new HashMap<>();
            operator.setPassword(null);
            resultInfo.put("configs", acConfigs);
            resultInfo.put("user", operator);
            // 查询重组菜单 如果有重组菜单并且用户启用了重组菜单
            if (acOperatorMenuService.count(new WhereCondition()
                    .andEquals(AcOperatorMenu.COLUMN_GUID_APP, acApp.getGuid())
                    .andEquals("GUID_OPERATOR", operator.getGuid())) > 0 && enableRecombine[0]) {
                resultInfo.put("menu", menuRService.getOperatorMenuByUserId(userId, acApp.getGuid(), identityGuid).toJson());
            } else {
                // 没有重组菜单或者用户配置没有启动重组带单，查询权限菜单
                resultInfo.put("menu", menuRService.getMenuByUserId(userId, acApp.getGuid(), identityGuid).toJson());
            }
            // 查询资源信息
            List<String> funcGuids = queryOperatorAuthFuncsInApp(userId, acApp.getGuid()).stream().map(AcFunc::getGuid).collect(Collectors.toList());
            List<Map> resources = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(funcGuids))
                acFuncServiceExt.queryFuncResourcesWithFuncCode(funcGuids);
            resultInfo.put("resources", resources);
            return resultInfo;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ACExceptionCodes.FAILURE_WHEN_LOGIN,
                    wrap(e));
        }
    }



    /**
     * 修改密码
     *
     * @param userId
     * @param oldPwd
     * @param newPwd
     * @throws AuthManagementException
     */
    @Override
    public AcOperator updatePassword(String userId, String oldPwd, String newPwd) throws AuthManagementException {
        try {
            // 校验传入参数
            if (StringUtils.isBlank(userId)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("userId"));
            }
            if (StringUtils.isBlank(oldPwd)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("old_password"));
            }
            if (StringUtils.isBlank(newPwd)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, wrap("new_password"));
            }
            // 判断用户是否存在
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if (acOperators.size() != 1) {
                throw new AuthManagementException(ACExceptionCodes.USER_ID_NOT_EXIST, wrap(userId));
            }
            AcOperator acOperator = acOperators.get(0);
            // 验证用户密码
            // 如果密码错误
            if (! StringUtils.isEquals(acOperator.getPassword(), PasswordHelper.generate(oldPwd, acOperator.getGuid()))) {
                throw new AuthManagementException(ACExceptionCodes.PASSWORD_IS_WRONG, wrap(userId));
            } else {
                acOperator.setPassword(PasswordHelper.generate(newPwd, acOperator.getGuid()));
                acOperator.setGuid(acOperator.getGuid());
                // 保存数据库
                acOperatorService.update(acOperator);
            }
            acOperator.setPassword(null);
            return acOperator;
        } catch (ToolsRuntimeException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE,
                    wrap("AC_OPERATOR", e));
        }
    }

    /**
     * 检查操作权限
     *      1.判断请求的功能是否在操作员权限之中
     *      2.判断请求的操作是否在操作员的允许列表中
     *
     * @param userId 操作员
     * @param reqInfo 请求代码
     * @param appGuid 应用id
     * @param funcGuid 功能id
     * @return
     * @throws AuthManagementException
     */
    @Override
    public boolean operateAuthCheck(String userId, String reqInfo, String appGuid, String funcGuid) throws AuthManagementException {
        // 校验传入参数
        if (StringUtils.isBlank(userId)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("userId", "user authentication"));
        }
        if (StringUtils.isBlank(reqInfo)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("reqInfo", "user authentication"));
        }
        if (StringUtils.isBlank(appGuid)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("appGuid", "user authentication"));
        }
        if (StringUtils.isBlank(funcGuid)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("funcGuid", "user authentication"));
        }
        try {
            if (funcAuthCheck(userId, funcGuid, appGuid)) {
                boolean flag = false;
                AcOperator operator = operatorRService.queryOperatorByUserId(userId);
                // 根据请求代码查询行为GUID
                List<String> bhvDefGuids = acBhvDefService.query(new WhereCondition().andEquals(AcBhvDef.COLUMN_BHV_CODE, reqInfo))
                        .stream().map(AcBhvDef::getGuid).collect(Collectors.toList());
                if (bhvDefGuids.size() > 0) {
                    // 根据行为GUID查询功能
                    List<String> funcBhvGuids = acFuncBhvService.query(new WhereCondition()
                            .andIn(AcFuncBhv.COLUMN_GUID_BHV, bhvDefGuids))
                            .stream()
                            .filter(acFuncBhv -> StringUtils.isEquals(acFuncBhv.getGuidFunc(), funcGuid))
                            .map(AcFuncBhv::getGuid).collect(Collectors.toList());
                    if (CollectionUtils.isNotEmpty(funcBhvGuids)) {
                        int count = acOperatorBhvService.count(new WhereCondition()
                                .andEquals(AcOperatorBhv.COLUMN_GUID_OPERATOR, operator.getGuid())
                                .andEquals(AcOperatorBhv.COLUMN_GUID_FUNC_BHV, funcBhvGuids.get(0)));
                        if (count > 0)
                            flag = true;
                    }
                }
                return flag;
            } else {
                throw new AuthManagementException(ExceptionCodes.FUNC_PERMISSION_DENIED);
            }
        } catch (ToolsRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    wrap("operateAuthCheck", e));
        }

    }

    /**
     * 检查功能权限
     *
     * @param userId
     * @param funcGuid
     * @param appGuid
     * @return
     * @throws AuthManagementException
     */
    @Override
    public boolean funcAuthCheck(String userId, String funcGuid, String appGuid) throws AuthManagementException {
        if (StringUtils.isBlank(userId)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("userId", "funcAuthCheck"));
        }
        if (StringUtils.isBlank(funcGuid)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("funcGuid", "funcAuthCheck"));
        }
        if (StringUtils.isBlank(appGuid)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("appGuid", "funcAuthCheck"));
        }
        try {
            long count = queryOperatorAuthFuncsInApp(userId, appGuid).stream().filter(acFunc ->
                    StringUtils.isEquals(acFunc.getGuid(), funcGuid)
            ).count();
            if(count > 0) {
                return true;
            } else {
                return false;
            }
        } catch (ToolsRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY,
                    wrap("funcAuthCheck", e));
        }
    }

    /**
     * 查询操作员在应用下的已授权功能
     *
     * @param userId
     * @param appGuid
     * @return
     * @throws AuthManagementException
     */
    @Override
    public List<AcFunc> queryOperatorAuthFuncsInApp(String userId, String appGuid) throws AuthManagementException {
        if (StringUtils.isBlank(userId)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("userId", "queryOperatorAuthFuncsInApp"));
        }
        if (StringUtils.isBlank(appGuid)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("appGuid", "queryOperatorAuthFuncsInApp"));
        }
        try {
            AcOperator operator = operatorRService.queryOperatorByUserId(userId);
            //查询当前用户拥有该应用的功能
            List<AcRole> acRoleList = roleRService.queryAllRoleByUserId(userId);
            Set<String> funcGuidList = new HashSet<>(); // 功能GUID
            List<String> roleGuidList = acRoleList.stream().map(AcRole::getGuid).collect(Collectors.toList());// 角色GUID
            if (roleGuidList.size() > 0) {
                // 获取角色拥有该应用下功能列表
                funcGuidList.addAll(acRoleFuncService.query(new WhereCondition()
                        .andEquals(AcRoleFunc.COLUMN_GUID_APP, appGuid)
                        .andIn(AcRoleFunc.COLUMN_GUID_ROLE, new ArrayList<>(roleGuidList)))
                        .stream()
                        .map(AcRoleFunc::getGuidFunc)
                        .collect(Collectors.toList()));
                // 获取角色下的特殊功能列表
                funcGuidList.addAll(acOperatorFuncService.query(new WhereCondition()
                        .andEquals(AcOperatorFunc.COLUMN_GUID_APP, appGuid)
                        .andEquals(AcOperatorFunc.COLUMN_GUID_OPERATOR, operator.getGuid()))
                        .stream()
                        .map(AcOperatorFunc::getGuidFunc)
                        .collect(Collectors.toList()));
            }
            List<AcFunc> funcList = new ArrayList<>();
            if(funcGuidList.size() > 0)
                funcList = acFuncService.query(new WhereCondition().andIn(AcFunc.COLUMN_GUID, new ArrayList<>(funcGuidList)));
            return funcList;
        } catch (ToolsRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new AuthManagementException(
                    ExceptionCodes.FAILURE_WHEN_CALL,
                    wrap("queryOperatorAuthFuncsInApp", e));
        }
    }


    /**
     * 获取操作员的权限信息
     *
     * @param userId  用户名
     * @param appCode 应用code
     * @return
     * @throws AuthManagementException
     */
    @Override
    public AbfPermission getPermissions(String userId, String appCode) throws AuthManagementException {
        if (StringUtils.isBlank(userId)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("userId", "getViewPermissions"));
        }
        if (StringUtils.isBlank(appCode)) {
            throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap("appGuid", "getViewPermissions"));
        }
        List<AcApp> acApps = acAppService.query(new WhereCondition().andEquals(AcApp.COLUMN_APP_CODE, appCode));
        if (CollectionUtils.isEmpty(acApps)) {
            throw new AuthManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, wrap(surroundBracketsWithLFStr(AcApp.COLUMN_APP_CODE, appCode)
                    , AcApp.TABLE_NAME));
        }
        AcApp acApp = acApps.get(0);
        AcOperator acOperator = operatorRService.queryOperatorByUserId(userId);
        AbfPermission abfPermission = new AbfPermission();
        // 获取所有角色信息
        List<String> roles = roleRService.queryAllRoleByUserId(userId)
                .stream()
                .filter(acRole -> StringUtils.isEquals(acRole.getGuidApp(), acApp.getGuid()))
                .map(AcRole::getGuid)
                .collect(Collectors.toList());
        // 获取所有角色授予的所有行为类型
        Map<String, List<Map>> funcBhvMap = acOperatorServiceExt
                .getAllOperatorFuncPmtBhv(acOperator.getGuid(), roles)
                .stream()
                .collect(Collectors.groupingBy(map -> (String) map.get("funcCode")));
        Set<String> bhvPermission = new HashSet<>();
        List<AcFunc> acFuncs = queryOperatorAuthFuncsInApp(userId, acApp.getGuid());
        for(AcFunc acFunc : acFuncs) {
            String funcCode = acFunc.getFuncCode();
            StringBuilder sb = new StringBuilder("+" + funcCode + "+");
            sb.append("view,");// 添加视图权限
            for (Map map : funcBhvMap.get(funcCode)) {
                sb.append((String) map.get("bhvCode")).append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            bhvPermission.add(String.valueOf(sb));
        }
        /*for (Map.Entry<String, List<Map>> entry : funcBhvMap.entrySet()) {
            StringBuilder sb = new StringBuilder("+" + entry.getKey() + "+");
            for (Map map : entry.getValue()) {
                sb.append((String) map.get("bhvCode")).append(",");
            }
            sb.delete(sb.length() - 1, sb.length());
            bhvPermission.add(String.valueOf(sb));
        }*/
        abfPermission.setBhvPermissions(bhvPermission);
        // TODO 添加数据权限
        return abfPermission;
    }

    /**
     * 根据行为code获取行为权限字符串
     *
     * @param bhvCode
     * @return
     * @throws AuthManagementException
     */
    @Override
    public List<String> getPermStrByBhvCode(String bhvCode) throws AuthManagementException {
        if (StringUtils.isBlank(bhvCode)) {
           throw new AuthManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL, wrap("bhvCode(String)", "getPermStrByBhvCode"));
        }
        try {
            List<Map> maps = acFuncServiceExt.queryFuncInfoByBhvCode(bhvCode);
            List<String> perms = new ArrayList<>();
            for (Map m : maps) {
                perms.add("+" + m.get("funcCode") + "+" + m.get("bhvCode"));
            }
            return perms;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(ExceptionCodes.FAILURE_WHEN_CALL, wrap("getPermStrByBhvCode", e));
        }
    }
}
