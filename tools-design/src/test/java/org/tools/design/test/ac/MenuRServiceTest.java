package org.tools.design.test.ac;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcOperatorMenu;
import org.tis.tools.model.vo.ac.AcMenuDetail;
import org.tis.tools.rservice.ac.capable.IMenuRService;
import org.tools.design.SpringJunitSupport;

import java.math.BigDecimal;

public class MenuRServiceTest extends SpringJunitSupport {

    @Autowired
    IMenuRService menuRService;

    @Test
    public void createRootOperatorMenuTest() {
        try {
            AcOperatorMenu menu = new AcOperatorMenu();
            menu.setGuidApp("APP1499956132");
            menu.setMenuName("个人重组菜单");
            menu.setMenuLabel("个人重组菜单");
            menu.setMenuLabel("个人重组菜单");
            menu.setGuidOperator("111");
            menuRService.createRootOperatorMenu(menu);
            AcMenuDetail operatorMenuByUserId = menuRService.getOperatorMenuByUserId("admin", "APP1499956132");
            String json = operatorMenuByUserId.toJson();
            System.out.println(json);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }


    @Test
    public void copyMenuToOperatorMenuTest() {
        try {
            String operatorGuid = "111";
            String copyGuid = "MENU1502789025";
            String goalGuid = "OMENU1502786615";
            BigDecimal order = new BigDecimal("1");
            menuRService.copyMenuToOperatorMenu(operatorGuid ,copyGuid, goalGuid, order);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void moveOperatorMenuTest() {
        try {
//            String operatorGuid = "111";
            String targetGuid = "OMENU1502786615";
            String moveGuid = "OMENU1502871974";
            BigDecimal order = new BigDecimal("0");
            menuRService.moveOperatorMenu(targetGuid, moveGuid, order);
            System.out.println("2222");
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }

    @Test
    public void getMenuByUserIdTest() {
        try {
            String userId = "admin";
            String appGuid = "APP1499956132";
            BigDecimal order = new BigDecimal("0");
            AcMenuDetail menuByUserId = menuRService.getMenuByUserId(userId, appGuid);
            String s = menuByUserId.toJson();
            System.out.println(s);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
    @Test
    public void getOperatorMenuByUserIdTest() {
        try {
            String userId = "admin";
            String appGuid = "APP1502700977";
            String  identity = "2";
            AcMenuDetail menuByUserId = menuRService.getOperatorMenuByUserId(userId, appGuid, identity);
            String s = menuByUserId.toJson();
            System.out.println(s);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码："+e.getCode());
            System.out.println("错误信息："+e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
