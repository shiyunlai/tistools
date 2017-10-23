package org.tools.design.test.ac;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.po.ac.AcConfig;
import org.tis.tools.model.po.ac.AcRole;
import org.tis.tools.model.vo.ac.AcOperatorFuncDetail;
import org.tis.tools.rservice.ac.capable.IOperatorRService;
import org.tools.design.SpringJunitSupport;

import java.util.List;
import java.util.Map;

public class OperatorRServiceTest extends SpringJunitSupport {

    @Autowired
    IOperatorRService operatorRService;


    @Test
    public void queryOperatorRoleByResTypeTest() throws ToolsRuntimeException {

        try {
            String empGuid = "111";
            String partyType = ACConstants.RESOURCE_TYPE_ROLE;
            List<AcRole> acRoleList = operatorRService.queryOperatorRoleByResType(empGuid,partyType);
            System.out.println(acRoleList);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryOperatorIdentityresesTest() throws ToolsRuntimeException {

        try {
            String identityGuid = "IDENTITY1502346125";
            List<Map> maps = operatorRService.queryOperatorIdentityreses(identityGuid);
            System.out.println(maps);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryOperatorFuncInfoInAppTest() throws ToolsRuntimeException{

        try {

            String userId = "test123";
            AcOperatorFuncDetail funcDetail = operatorRService.queryOperatorFuncInfoInApp(userId);
            System.out.println(funcDetail);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }

    }

    @Test
    public void queryAcOperatorFunListByUserIdTest() throws ToolsRuntimeException {

        try {
            String userId = "admin";
            List<Map> maps = operatorRService.queryAcOperatorFunListByUserId(userId);
            System.out.println(maps);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryOperatorConfigTest() throws ToolsRuntimeException {

        try {
            String userId = "admin";
            String appGuid = "APP";
            List<AcConfig> maps = operatorRService.queryOperatorConfig(userId, appGuid);
            System.out.println(maps);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void queryOperatorBhvListInFuncTest() throws ToolsRuntimeException {

        try {
            String operatorGuid = "1111";
            String funcGuid = "FUNC1500601486";
            Map<String, Object> map = operatorRService.queryOperatorBhvListInFunc(funcGuid, operatorGuid);
            System.out.println(map);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }


}
