package org.tis.tools.dao.ac;


import org.apache.ibatis.annotations.Param;
import org.tis.tools.model.po.ac.AcRole;

import java.util.List;
import java.util.Map;

public interface AcRoleMapperExt {

    /**
     * 查询角色表和应用表得到角色详细信息，包含了应用信息
     *
     * @return
     */
    List<Map> queryAllRoleExt();

    /**
     * 查询组织类型对应下的权限集合
     * @param roleGuid
     * @param partyType
     * @return
     */
    List<Map> queryAllRolePartyExt(@Param("roleGuid") String roleGuid, @Param("partyType") String partyType);


    /**
     * 查询角色已经授权的角色列表
     * @param roleGuid
     * @return
     */
    List<Map> queryAllOperatorRoleExt(@Param("roleGuid") String roleGuid);

    /**
     * 查询员工在所在工作组和岗位下的所有角色
     * @param empGuid
     *          员工
     * @return
     */
    List<AcRole> queryEmployeeAllPartyRoleList(@Param("empGuid") String empGuid);
}
