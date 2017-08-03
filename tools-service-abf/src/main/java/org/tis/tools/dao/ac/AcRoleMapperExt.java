package org.tis.tools.dao.ac;


import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AcRoleMapperExt {

    /**
     * 查询角色表和应用表得到角色详细信息，包含了应用信息
     *
     * @return
     */
    List<Map> queryAllRoleExt();

    List<Map> queryAllRolePartyExt(@Param("roleGuid") String roleGuid, @Param("partyType") String partyType);

    List<Map> queryAllOperatorRoleExt(@Param("roleGuid") String roleGuid);
}
