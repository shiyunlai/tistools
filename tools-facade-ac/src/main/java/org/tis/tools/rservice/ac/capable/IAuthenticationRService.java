/**
 * 
 */
package org.tis.tools.rservice.ac.capable;

import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcOperatorIdentity;

import java.util.List;

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
    List<AcOperatorIdentity> userStatusCheck(String userId) throws ToolsRuntimeException;

}
