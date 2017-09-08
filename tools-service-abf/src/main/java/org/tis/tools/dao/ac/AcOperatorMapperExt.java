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


}
