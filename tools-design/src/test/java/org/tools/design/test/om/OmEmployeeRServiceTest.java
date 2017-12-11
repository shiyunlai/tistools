package org.tools.design.test.om;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.rservice.om.capable.IEmployeeRService;
import org.tools.design.SpringJunitSupport;

import java.util.List;
import java.util.Map;

public class OmEmployeeRServiceTest extends SpringJunitSupport {

    @Autowired
    IEmployeeRService employeeRService;

    @Test
    public void queryPosDutybyEmpCode() throws ToolsRuntimeException {
        try {
            String empCode = "EMP000178";
            List<Map> maps = employeeRService.queryPosDutybyEmpCode(empCode);
            System.out.println(maps);
        } catch (ToolsRuntimeException e) {
            System.out.println("错误码：" + e.getCode());
            System.out.println("错误信息：" + e.getMessage());
            // TODO 自动生成的 catch 块
            e.printStackTrace();
        }
    }
}
