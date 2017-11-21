package org.tools.design.test.ac;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.ACConstants;
import org.tis.tools.model.po.ac.*;
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
            String appCode = "ABF" ; // 指定应用系统
            List<AcConfig> maps = operatorRService.queryOperatorConfig(userId,appCode);
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

    @Test
    public void getOperatorFuncInfo() throws ToolsRuntimeException {

        try {
            String userId = "admin";
            String appGuid = "APP1509196635";
            AcOperatorFuncDetail operatorFuncInfo = operatorRService.getOperatorFuncInfo(userId, appGuid);
            System.out.println(operatorFuncInfo);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void changeOperatorStatus() throws ToolsRuntimeException {

        try {
            String userId = "admin";
            String status = ACConstants.OPERATE_STATUS_LOGOUT;
            AcOperator acOperator = operatorRService.changeOperatorStatus(userId, status);
            System.out.println(acOperator);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }


    @Test
    public void getOperatorsNotLinkEmp() throws ToolsRuntimeException {
        try {
            List<AcOperator> operatorsNotLinkEmp = operatorRService.getOperatorsNotLinkEmp();
            System.out.println(operatorsNotLinkEmp);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void setDefaultOperatorIdentity() throws ToolsRuntimeException {
        try {
            String identityGuid= "3";
            AcOperatorIdentity acOperatorIdentity = operatorRService.setDefaultOperatorIdentity(identityGuid);
            System.out.println(acOperatorIdentity);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void getOperatorFuncBhvInfo() throws ToolsRuntimeException {
        try {
            String funcGuid = "FUNC1500601486";
            String userId = "renxy";
            Map<String, List<Map>> operatorFuncBhvInfo = operatorRService.getOperatorFuncBhvInfo(userId, funcGuid);
            System.out.println(operatorFuncBhvInfo);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
    @Test
    public void addAcOperatorBhv() throws ToolsRuntimeException {
        try {
            String data = "[{\"guidFuncBhv\":\"BHVDEF1507626382\",\"guidOperator\":\"OPERATOR1509439057\",\"authType\":0}]";
            List<AcOperatorBhv> acOperatorBhvs = JSON.parseArray(data, AcOperatorBhv.class);
            operatorRService.addAcOperatorBhv(acOperatorBhvs);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }






}
