package org.tools.design.test.ac;

import com.alibaba.fastjson.JSON;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.po.ac.AcRoleFunc;
import org.tis.tools.rservice.ac.capable.IRoleRService;
import org.tools.design.SpringJunitSupport;

import java.util.List;
import java.util.Map;

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


    /**
     * 测试通过应用GUID查询关联的角色列表
     *
     * @throws ToolsRuntimeException
     */
    @Test
    public void queryRoleListInAppTest() throws ToolsRuntimeException {
        try {
            String appGuid = "APP1499956132";
            List<AcRole> acRoleList = roleRService.queryRoleListInApp(appGuid);
            Assert.assertNotNull(acRoleList);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryOperatorInheritRoleListTest() throws ToolsRuntimeException {
        try {
            String userId = "test123";
            List<AcRole> acRoleList = roleRService.queryOperatorInheritRoleList(userId);
            Assert.assertNotNull(acRoleList);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryOperatorAuthorizedRoleListTest() throws ToolsRuntimeException {

        try {
            String userId = "test123";
            List<AcRole> acRoleList = roleRService.queryOperatorAuthorizedRoleList(userId);
            Assert.assertNotNull(acRoleList);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryAcRoleBhvsByFuncGuid() throws ToolsRuntimeException {
        try {
            String roleGuid = "ROLE1509610867";
            String funcGuid = "FUNC1500601486";
            List<Map> acBhvDefs = roleRService.queryAcRoleBhvsByFuncGuid(roleGuid, funcGuid);
            System.out.println(acBhvDefs);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void configRoleFunc() throws ToolsRuntimeException {
        try {
            String roleGuid = "ROLE1509610867";

            String data = "[{guidFunc: \"FUNC1500601486\", guidGroup: \"FUNCGROUP1500544715\", guidApp: \"APP1499956132\"}\n" +
                    "{guidFunc: \"FUNC1500601487\", guidFuncgroup: \"FUNCGROUP1500544715\", guidApp: \"APP1499956132\"}\n" +
                    "{guidFunc: \"FUNC1500601488\", guidFuncgroup: \"FUNCGROUP1500544715\", guidApp: \"APP1499956132\"}\n" +
                    "{guidFunc: \"FUNC1500601505\", guidFuncgroup: \"FUNCGROUP1500544715\", guidApp: \"APP1499956132\"}\n" +
                    "{guidFunc: \"FUNC1500601528\", guidFuncgroup: \"FUNCGROUP1500544715\", guidApp: \"APP1499956132\"}\n" +
                    "{guidFunc: \"FUNC1509605822\", guidFuncgroup: \"FUNCGROUP1500544715\", guidApp: \"APP1499956132\"}\n" +
                    "{guidFunc: \"FUNC1500601529\", guidFuncgroup: \"FUNCGROUP1500544716\", guidApp: \"APP1499956132\"}]";
            List<AcRoleFunc> acRoleFuncs = JSON.parseArray(data, AcRoleFunc.class);
            List<AcRoleFunc> acRoleFuncs1 = roleRService.configRoleFunc(roleGuid, acRoleFuncs);
            System.out.println(acRoleFuncs1);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    /*{roleGuid: "ROLE1509610867", funcList: Array(7)}
    funcList:Array(7)
0:

    roleGuid"ROLE1509610867"*/

}
