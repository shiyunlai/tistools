/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcOperatorIdentity;
import org.tis.tools.rservice.ac.exception.AuthManagementException;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 
 * 认证服务.
 * 
 * 认证就是核实用户身份的过程，证明用户身份.
 * 当操作员发生操作时，系统执行安全认证控制处理。
 * 
 * 提供的服务包括：
 * <li>登陆
 * <li>注销
 * <li>修改操作员密码
 * <li>检查操作权限
 * <li>.....
 * 
 * </pre>
 * @author megapro
 *
 */
public interface IAuthenticationRService {

    /**
     *   用户状态检查
     * a)	判断用户是否存在；
     * b)	用户状态只能是“退出、正常、挂起”，否则报错提示；
     * c)	检查是否在允许的时间范围内；
     * d)	检查是否在运行MAC范围内；
     * e)	检查是否在运行的IP地址范围内；

     * @param userId
     *          用户名
     * @return 用户检查结果
     *
     * @throws ToolsRuntimeException
     *
     */
    List<AcOperatorIdentity> userStatusCheck(String userId) throws AuthManagementException;

    /**
     *   用户登录
     a)	用户必须存在；
     b)	用户状态只能是“退出、正常、挂起”，否则报错提示；
     c)	验证用户密码（关于密码，新建用户时，加密存储；采用加密传输；加密方法提供为工具类CryptographyUtil）；
     d)	认证错误即锁定解锁的相关处理——密码错误时需要记录“错误登陆次数”；达到“锁定次数限制”时，更新状态变为“锁定”，记录“锁定时间”（由管理员解锁后才能再登陆——用户管理中需要增加解锁功能）；如果密码正确则重置错误登陆次数＝0；
     e)	登陆验证通过，修改用户状态为“正常”，记录“最近登陆时间”；
     f)	如果验证通过，返回以下用户信息，用作主界面展示：
     	用户信息（AC_OPERATOR）；


     * @param userId
     *          用户名
     * @return 用户检查结果
     *
     * @throws ToolsRuntimeException
     *
     */
    AcOperator loginCheck(String userId, String password, String identity, String appGuid) throws AuthManagementException;

    /**
     *  根据用户id和身份查询菜单信息
     *
       根据当前登陆身份的权限滤后的菜单——应用系统菜单（AC_MENU）或重组菜单（AC_OPERATOR_MENU）；
     	用户在该应用系统中的快捷菜单（AC_OPERATOR_SHORTCUT）；
     	用户在该应用系统中的个性化配置（AC_OPERATOR_CONFIG）；

     * @param userId
     *            用户ID
     * @param identity
     *             身份GUID
     * @
     *
     * @throws AuthManagementException
     */
    Map<String, Object> getInitInfoByUserIdAndIden(String userId, String identity, String appGuid) throws AuthManagementException;


    /**
     * 修改密码
     * @param userId
     *          用户ID
     * @param oldPwd
     *          原密码
     * @param newPwd
     *          新密码
     * @throws AuthManagementException
     */
    AcOperator updatePassword(String userId, String oldPwd, String newPwd) throws AuthManagementException;


    /**
     * 检查操作权限
     * @param userId 操作员
     * @param reqInfo 请求代码
     * @param appGuid 应用id
     * @param funcGuid 功能id
     * @return
     * @throws AuthManagementException
     */
    boolean operateAuthCheck(String userId, String reqInfo, String appGuid, String funcGuid) throws AuthManagementException;

    /**
     * 检查功能权限
     * @param userId
     * @param funcGuid
     * @param appGuid
     * @return
     * @throws AuthManagementException
     */
    boolean funcAuthCheck(String userId, String funcGuid, String appGuid) throws AuthManagementException;

    /**
     * 查询操作员在应用下的已授权功能
     * @param userId
     * @param appGuid
     * @return
     * @throws AuthManagementException
     */
    List<AcFunc> queryOperatorAuthFuncsInApp(String userId, String appGuid) throws AuthManagementException;


}
