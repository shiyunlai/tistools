package org.tis.tools.service.ac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tis.tools.common.utils.StringUtil;
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


    /**
     * 查询操作员已授权功能行为
     * @param roleGuid 角色GUID
     * @param operatorGuid 操作员GUID
     * @param funcGuid 功能GUID
     * @return
     */
    public List<Map> getAuthOperatorFuncBhv(List<String> roleGuid, String operatorGuid, String funcGuid) {
        return acOperatorMapperExt.getAuthOperatorFuncBhv(StringUtil.list2String(roleGuid), operatorGuid, funcGuid);
    }

    /**
     * 查询操作员已授权功能中特别禁止行为
     * @param operatorGuid 操作员GUID
     * @param funcGuid 功能GUID
     * @return
     */
    public List<Map> getAuthOperatorFuncFbdBhv(String operatorGuid, String funcGuid) {
        return acOperatorMapperExt.getAuthOperatorFuncFbdBhv(operatorGuid, funcGuid);
    }

    /**
     * 查询操作员未授权功能中特别允许行为
     * @param operatorGuid 操作员GUID
     * @param funcGuid 功能GUID
     * @return
     */
    public List<Map> getUnauthOperatorFuncPmtBhv(String operatorGuid, String funcGuid) {
        return acOperatorMapperExt.getUnauthOperatorFuncPmtBhv(operatorGuid, funcGuid);
    }

    /**
     * 查询操作员所有功能行为
     * @param operatorGuid 操作员GUID
     * @param roleGuid 角色GUID
     * @return
     */
    public List<Map> getAllOperatorFuncPmtBhv(String operatorGuid, List<String> roleGuid) {
        return acOperatorMapperExt.getAllOperatorFuncPmtBhv(operatorGuid, StringUtil.list2String(roleGuid));
    }

    /**
     * 查询操作员所有功能行为
     * @param roleGuids 角色GUID
     * @param partGuids 组织对象GUID
     * @return
     */
    public List<String> getFuncListByIdentity(List<String> partGuids, List<String> roleGuids) {
        return acOperatorMapperExt.getFuncListByIdentity(StringUtil.list2String(partGuids), StringUtil.list2String(roleGuids));
    }



}
