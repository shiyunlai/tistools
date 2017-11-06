package org.tis.tools.dao.ac;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AcOperatorMapperExt {

    /**
     * 查询操作员身份资源 翻译角色名
     * @param identityGuid
     * @return
     */
    List<Map> queryOperatorIdentityreses(@Param("identityGuid") String identityGuid);

    /**
     * 查询操作员特殊权限详情-翻译APP_GUID和FUNC_GUID
     * @param operatorGuid
     * @return
     */
    List<Map> queryOperatorFuncDetail(@Param("operatorGuid") String operatorGuid);

    /**
     * 查询操作员已授权功能行为
     * @param roleGuid 角色GUID
     * @param operatorGuid 操作员GUID
     * @param funcGuid 功能GUID
     * @return
     */
    List<Map> getAuthOperatorFuncBhv(
            @Param("roleGuid")String roleGuid,
            @Param("operatorGuid")String operatorGuid,
            @Param("funcGuid")String funcGuid);

    /**
     * 查询操作员已授权功能中特别禁止行为
     * @param operatorGuid 操作员GUID
     * @param funcGuid 功能GUID
     * @return
     */
    List<Map> getAuthOperatorFuncFbdBhv(
            @Param("operatorGuid")String operatorGuid,
            @Param("funcGuid")String funcGuid);

    /**
     * 查询操作员未授权功能中特别允许行为
     * @param operatorGuid 操作员GUID
     * @param funcGuid 功能GUID
     * @return
     */
    List<Map> getUnauthOperatorFuncPmtBhv(
            @Param("operatorGuid")String operatorGuid,
            @Param("funcGuid")String funcGuid);

}
