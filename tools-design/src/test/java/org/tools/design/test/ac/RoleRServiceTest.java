package org.tools.design.test.ac;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.rservice.ac.capable.IRoleRService;
import org.tools.design.SpringJunitSupport;

import java.util.List;

public class RoleRServiceTest extends SpringJunitSupport {

    @Autowired
    IRoleRService roleRService;


    @Test
    public void queryEmpPartyRoleTest() throws ToolsRuntimeException {

        try {
            String empGuid = "EMPLOYEE1500519610";
            String partyType = ACConstants.PARTY_TYPE_ORGANIZATION;
            List<AcRole> acRoleList = roleRService.queryEmpPartyRole(partyType, empGuid);
            System.out.println(acRoleList);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryAllRoleByUserIdTest() throws ToolsRuntimeException {

        try {
            String userId = "admin";
            List<AcRole> acRoleList = roleRService.queryAllRoleByUserId(userId);
            System.out.println(acRoleList);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

}
