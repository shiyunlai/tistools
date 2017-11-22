package org.tis.tools.dao.ac;

import org.apache.ibatis.annotations.Param;
import org.tis.tools.model.po.ac.AcFunc;

import java.util.List;
import java.util.Map;

public interface AcFuncMapperExt {

    /**
     * 查询应用下的所有功能
     *
     * @param appGuid
     * @return
     */
    List<AcFunc> queryFuncListInApp(@Param("appGuid") String appGuid);

    /**
     * 查询功能资源信息
     * 用于组装路由
     * @param funcGuids 功能GUID集合
     * @return
     */
    List<Map> queryFuncResourcesWithFuncCode(@Param("funcGuids") String funcGuids);

    /**
     * 根据功能行为查询功能和行为信息
     *
     * @param bhvCode 行为代码
     * @return
     */
    List<Map> queryFuncInfoByBhvCode(@Param("bhvCode") String bhvCode);
}
