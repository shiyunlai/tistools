package org.tools.design.test.log;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.model.po.log.LogAbfOperate;
import org.tis.tools.model.vo.log.LogOperateDetail;
import org.tis.tools.model.vo.log.OperateLogBuilder;
import org.tis.tools.rservice.log.capable.IOperateLogRService;
import org.tools.design.SpringJunitSupport;

import com.alibaba.fastjson.JSON;

public class OperateLogRServiceTest extends SpringJunitSupport {

    @Autowired
    IOperateLogRService operateLogRService;


    @Test
    public void createLogTest() {

       /* logAbfOperate.setOperatrForm("ABF"); // FIXME 从哪设置该值
        logAbfOperate.setUserId(session.getAttribute("userId").toString());
        logAbfOperate.setOperatorName(session.getAttribute("operatorName").toString());
        logAbfOperate.setOperateType(log.operateType());
        logAbfOperate.setProcessDesc(log.operateDesc());
        logAbfOperate.setOperateTime(new Date());
        logAbfOperate.setRestfulUrl(request.getPathInfo());*/


        OperateLogBuilder logBuilder = new OperateLogBuilder();
        logBuilder.start()
                .setOperateFrom("ABF")
                .setUserId("admin")
                .setOperatorName("GM")
                .setOperateType("新增")
                .setProcessDesc("添加根菜单2")
                .setOperateTime(new java.util.Date())
                .setRestfulUrl("menu/create")
                .setOperateResult("success");

        logBuilder.getLog().addObj()
                .setObjFrom("AC_MENU")
                .setObjGuid("MENU121231231231")
                .setObjName("测试菜单")
                .setObjType("菜单类")
                .setObjValue("{id:1231231231231, name:dawdawdawd}")
                .addKey("guid", "测试1")
                .addKey("name", "测试2");

        operateLogRService.createOperatorLog(logBuilder.getLog());
        System.out.println(logBuilder.getLog());

    }

    @Test
    public void queryOperateLogListTest() {
        List<LogAbfOperate> logAbfOperates = operateLogRService.queryOperateLogList();
        String ret = JSON.toJSONString(logAbfOperates);
        System.out.println(ret);
    }
    @Test
    public void queryOperateDetailTest() {
        String logGuid = "OPERATELOG1505787576";
        LogOperateDetail detail = operateLogRService.queryOperateDetail(logGuid);
        String ret = JSON.toJSONString(detail);
        System.out.println(ret);
    }
    @Test
    public void queryOperateHistoryListTest() {
        String objGuid = "MENU1504855790";
        List<LogOperateDetail> logOperateDetails = operateLogRService.queryOperateHistoryList(objGuid);
        String ret = JSON.toJSONString(logOperateDetails);
        System.out.println(ret);

    }

    @Test
    public void queryLoginHistoryTest() {
        String userId = "admin";
        List<LogAbfOperate> logOperateDetails = operateLogRService.queryLoginHistory(userId);
        Assert.assertNotNull(logOperateDetails);

    }


}
