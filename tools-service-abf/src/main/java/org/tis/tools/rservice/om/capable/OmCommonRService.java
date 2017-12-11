package org.tis.tools.rservice.om.capable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.ac.capable.IOperatorRService;
import org.tis.tools.rservice.ac.exception.OperatorManagementException;
import org.tis.tools.service.ac.AcOperatorServiceExt;

import static org.tis.tools.common.utils.BasicUtil.wrap;

/**
 * 通用的服务类，不暴露接口只用于内部调用
 *
 **/
public class OmCommonRService extends BaseRService {

    @Autowired
    IOperatorRService operatorRService;
    @Autowired
    AcOperatorServiceExt acOperatorServiceExt;

    /**
     * 用于处理操作员资源的变化对身份的各资源影响
     * 如：操作员的身份下赋予了某机构，当机构与该员工取消指配，身份下的该机构也应该取消
     * @param userId 操作员ID
     * @param resGuid 资源ID
     * @throws OperatorManagementException
     */
    public void deleteOperatorIdentityRes(String userId, String resGuid)
            throws OperatorManagementException {
        if (StringUtils.isBlank(userId)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL,
                    wrap("userId(String)", "doOperatorIdentityResCheck"));
        }
        if (StringUtils.isBlank(resGuid)) {
            throw new OperatorManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_CALL,
                    wrap("resGuid(String)", "doOperatorIdentityResCheck"));
        }
        acOperatorServiceExt.deleteOperatorIdentityRes(userId, resGuid);
    }
}
