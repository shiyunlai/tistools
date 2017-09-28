package org.tis.tools.service.ac;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.dao.ac.AcOperatorMapperExt;

import java.util.List;
import java.util.Map;

@Service
public class AcOperatorServiceExt {

    @Autowired
    AcOperatorMapperExt acOperatorMapperExt;

    /**
     * 查询操作员身份资源 翻译角色名
     * @param identityGuid
     * @return
     */
    public List<Map> queryOperatorIdentityreses(String identityGuid) {
        return acOperatorMapperExt.queryOperatorIdentityreses(identityGuid);
    }

    /**
     * 查询操作员特殊权限详情-翻译APP_GUID和FUNC_GUID
     * @param operatorGuid
     * @return
     */
    public List<Map> queryOperatorFuncDetail(String operatorGuid) {
        return acOperatorMapperExt.queryOperatorFuncDetail(operatorGuid);
    }


}
