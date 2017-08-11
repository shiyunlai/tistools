package org.tis.tools.rservice.ac.capable;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.CryptographyUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.def.CommonConstants;
import org.tis.tools.model.po.ac.*;
import org.tis.tools.model.vo.ac.AcMenuDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.AuthManagementException;
import org.tis.tools.service.ac.*;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

import java.math.BigDecimal;
import java.util.*;

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
            if (StringUtil.isEmpty(userId)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("userId"));
            }
//            if (StringUtil.isEmpty(macCode)) {
//                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("macCode"));
//            }
//            if (StringUtil.isEmpty(ipAddress)) {
//                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("ipAddress"));
//            }
            //判断用户是否存在
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if (acOperators.size() != 1) {
                throw new AuthManagementException(ACExceptionCodes.USER_ID_NOT_EXIST, BasicUtil.wrap(userId));
            }
            //判断用户状态，只能是“退出、正常、挂起”，否则报错提示
            AcOperator acOperator = acOperators.get(0);
            String operatorStatus = acOperator.getOperatorStatus();
            if(!StringUtil.isEqualsIn(operatorStatus,
                    ACConstants.OPERATE_STATUS_LOGOUT,
                    ACConstants.OPERATE_STATUS_LOGIN,
                    ACConstants.OPERATE_STATUS_PAUSE)) {
                throw new AuthManagementException(ACExceptionCodes.USER_STATUS_NOT_ALLOW_LOGIN, BasicUtil.wrap(operatorStatus));
            }
            return acOperatorIdentityService.query(new WhereCondition().andEquals("GUID_OPERATOR", acOperator.getGuid()));

        } catch (AuthManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ACExceptionCodes.CHECK_USER_STATUS_ERROR,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }

    }

    /**
     * 用户登录
     * a)	用户必须存在；
     * b)	用户状态只能是“退出、正常、挂起”，否则报错提示；
     * c)	验证用户密码（关于密码，新建用户时，加密存储；采用加密传输；加密方法提供为工具类 CryptographyUtil）；
     * d)	认证错误即锁定解锁的相关处理——密码错误时需要记录“错误登陆次数”；达到“锁定次数限制”时，更新状态变为“锁定”，记录“锁定时间”（由管理员解锁后才能再登陆——用户管理中需要增加解锁功能）；
     *      如果密码正确则重置错误登陆次数＝0；
     * e)	登陆验证通过，修改用户状态为“正常”，记录“最近登陆时间”；
     * f)	如果验证通过，返回以下用户信息，用作主界面展示：
     * 	用户信息（AC_OPERATOR）；
     * 	根据当前登陆身份的权限滤后的菜单——应用系统菜单（AC_MENU）或重组菜单（AC_OPERATOR_MENU）；
     * 	用户在该应用系统中的快捷菜单（AC_OPERATOR_SHORTCUT）；
     * 	用户在该应用系统中的个性化配置（AC_OPERATOR_CONFIG）；
     * XXX 密码失效日期逻辑？
     * @param userId   用户名
     * @param password
     * @param identityGuid
     * @param appGuid
     *
     * @return 用户检查结果
     * @throws ToolsRuntimeException
     */
    @Override
    public AcOperator loginCheck(String userId, String password, String identityGuid, String appGuid) throws AuthManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("userId"));
            }
            if (StringUtil.isEmpty(password)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("password"));
            }
            if (StringUtil.isEmpty(identityGuid)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("identityGuid"));
            }
            // 判断用户是否存在
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if (acOperators.size() != 1) {
                throw new AuthManagementException(ACExceptionCodes.USER_ID_NOT_EXIST, BasicUtil.wrap(userId));
            }
            // 判断用户状态，只能是“退出、正常、挂起”，否则报错提示
            AcOperator acOperator = acOperators.get(0);
            String operatorStatus = acOperator.getOperatorStatus();
            if(!StringUtil.isEqualsIn(operatorStatus,
                    ACConstants.OPERATE_STATUS_LOGOUT,
                    ACConstants.OPERATE_STATUS_LOGIN,
                    ACConstants.OPERATE_STATUS_PAUSE)) {
                throw new AuthManagementException(ACExceptionCodes.USER_STATUS_NOT_ALLOW_LOGIN, BasicUtil.wrap(operatorStatus));
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
                throw new AuthManagementException(ACExceptionCodes.PASSWORD_IS_WRONG, BasicUtil.wrap(userId));
            } else { // 如果登录成功
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

        } catch (AuthManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ACExceptionCodes.FAILURE_WHEN_LOGIN,
                    BasicUtil.wrap(e.getCause().getMessage()));
        }
    }

    /**
     * 根据用户id和身份查询菜单信息等初始化信息
     * <p>
     *   根据当前登陆身份的权限滤后的菜单——应用系统菜单（AC_MENU）或重组菜单（AC_OPERATOR_MENU）；
     * 	用户在该应用系统中的快捷菜单（AC_OPERATOR_SHORTCUT）； TODO 快捷菜单
     * 	用户在该应用系统中的个性化配置（AC_OPERATOR_CONFIG）； TODO 个性化配置
     *
     * @param userId
     * @param identityGuid
     * @param appGuid
     * @throws AuthManagementException
     */
    @Override
    public Map<String, Object> getInitInfoByUserIdAndIden(String userId, String identityGuid, String appGuid) throws AuthManagementException {
        try {
            Map<String, Object> resultInfo = new HashMap<>();
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("userId"));
            }
            if (StringUtil.isEmpty(identityGuid)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("identityGuid"));
            }
            if (StringUtil.isEmpty(appGuid)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("appGuid"));
            }
            //判断是否有重组菜单
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if(CollectionUtils.isEmpty(acOperators)) {
                throw new AuthManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap("USER_ID "+ userId, "AC_OPERATOR"));
            }
            AcOperator operator = acOperators.get(0);
            operator.setPassword(null);
            resultInfo.put("user", operator);
            // 查询重组菜单
            if (acOperatorMenuService.count(new WhereCondition().andEquals("GUID_OPERATOR", operator.getGuid())) > 0) {
                resultInfo.put("menu", menuRService.getOperatorMenuByUserId(userId, appGuid, identityGuid).toJson());
            } else {
                resultInfo.put("menu", menuRService.getMenuByUserId(userId, appGuid, identityGuid).toJson());
            }
            return resultInfo;
        } catch (AuthManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ACExceptionCodes.FAILURE_WHEN_LOGIN,
                    BasicUtil.wrap(e.getCause().getMessage()));
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
    public void updatePassword(String userId, String oldPwd, String newPwd) throws AuthManagementException {
        try {
            // 校验传入参数
            if (StringUtil.isEmpty(userId)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("userId"));
            }
            if (StringUtil.isEmpty(oldPwd)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("old_password"));
            }
            if (StringUtil.isEmpty(newPwd)) {
                throw new AuthManagementException(ACExceptionCodes.PARMS_NOT_ALLOW_EMPTY, BasicUtil.wrap("new_password"));
            }
            // 判断用户是否存在
            List<AcOperator> acOperators = acOperatorService.query(new WhereCondition().andEquals("USER_ID", userId));
            if (acOperators.size() != 1) {
                throw new AuthManagementException(ACExceptionCodes.USER_ID_NOT_EXIST, BasicUtil.wrap(userId));
            }
            AcOperator acOperator = acOperators.get(0);
            // 验证用户密码
            // 如果密码错误
            if (! StringUtils.isEquals(acOperator.getPassword(),CryptographyUtil.md5(oldPwd))) {
                throw new AuthManagementException(ACExceptionCodes.PASSWORD_IS_WRONG, BasicUtil.wrap(userId));
            } else {
                acOperator.setPassword(CryptographyUtil.md5(newPwd));
                acOperator.setGuid(acOperator.getGuid());
                // 保存数据库
                acOperatorService.update(acOperator);
            }
        } catch (AuthManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new AuthManagementException(
                    ExceptionCodes.FAILURE_WHEN_UPDATE,
                    BasicUtil.wrap("AC_OPERATOR", e.getCause().getMessage()));
        }
    }

}
