package org.tis.tools.rservice.ac.capable;

import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.common.utils.StringUtil;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.ac.AcOperatorIdentity;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.exception.AuthManagementException;
import org.tis.tools.service.ac.AcOperatorIdentityService;
import org.tis.tools.service.ac.AcOperatorService;
import org.tis.tools.service.ac.exception.ACExceptionCodes;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
public class AuthenticationRServiceImpl extends BaseRService implements IAuthenticationRService {

    @Autowired
    AcOperatorService acOperatorService;
    @Autowired
    AcOperatorIdentityService acOperatorIdentityService;

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
    public List<AcOperatorIdentity> userStatusCheck(String userId) throws ToolsRuntimeException {

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
}
