package org.tools.design.test.ac;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.vo.ac.AcMenuDetail;
import org.tis.tools.rservice.ac.capable.IAuthenticationRService;
import org.tools.design.SpringJunitSupport;

import java.util.Map;

public class AuthRServiceTest extends SpringJunitSupport {

    @Autowired
    IAuthenticationRService authenticationRService;

    @Test
    public void getMenuByUserIdTest() throws ToolsRuntimeException {

        try {
            String appGuid = "APP1499956132";
            String userId = "admin";
            AcMenuDetail menuByUserId = authenticationRService.getMenuByUserId(userId, appGuid);
            System.out.println(menuByUserId.toJson());
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());

            e.printStackTrace();
        }

    }

    @Test
    public void LoginTest() throws ToolsRuntimeException {

        try {
            // {"userId":"admin","password":"312312312312","identityGuid":"2 ","appGuid":"APP1499956132"}
            String password = "123456";
            String userId = "admin";
            String identityGuid = "2";
            String appGuid = "APP1499956132";
//            AcOperator acOperator = authenticationRService.loginCheck(userId, password, identityGuid, appGuid);
            Map<String, Object> initInfoByUserIdAndIden = authenticationRService.getInitInfoByUserIdAndIden(userId, identityGuid, appGuid);

            System.out.println(initInfoByUserIdAndIden);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());

            e.printStackTrace();
        }

    }
}
