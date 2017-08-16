package org.tis.tools.dao.ac;

import org.apache.ibatis.annotations.Param;
import org.tis.tools.model.po.ac.AcFunc;

import java.util.List;

public interface AcFuncMapperExt {

    /**
     * 查询应用下的所有功能
     *
     * @param appGuid
     * @return
     */
    List<AcFunc> queryFuncListInApp(@Param("appGuid") String appGuid);
}
