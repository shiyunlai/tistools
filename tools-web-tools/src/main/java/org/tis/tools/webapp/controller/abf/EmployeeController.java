package org.tis.tools.webapp.controller.abf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.rservice.om.capable.IEmployeeRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.log.OperateLog;
import org.tis.tools.webapp.log.ReturnType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 员工管理功能
 *
 * @author
 */
@Controller
@RequestMapping(value = "/om/emp")
public class EmployeeController extends BaseController {
    @Autowired
    IEmployeeRService employeeRService;


    /**
     * 查询所有人员信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/queryemployee", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> queryemployee(@RequestBody String content) {
        List<OmEmployee> list = employeeRService.queryAllEmployyee();
        return getReturnMap(list);

    }


    /**
     * 新增人员信息
     *
     * @param content
     * @return
     */
    /**
     * 新增根菜单
     * @param content
     * @return
     */
    @OperateLog(
            operateType = "add",  // 操作类型
            operateDesc = "新增员工", // 操作描述
            retType = ReturnType.Object, // 返回类型，对象或数组
            id = "guid", // 操作对象标识
            name = "employeeName", // 操作对象名
            keys = {"guidPosition", "empCode", "guidOrg"}) // 操作对象的关键值的键值名
    @ResponseBody
    @RequestMapping(value = "/addemployee", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> addemployee( @RequestBody String content) {
        OmEmployee oe = JSONObject.parseObject(content, OmEmployee.class);
        if (oe.getGuid() == null || oe.getGuid() == "") {
            OmEmployee emp = employeeRService.createEmployee(oe);
            return getReturnMap(emp);
        } else {
            employeeRService.updateEmployee(oe);
            return getReturnMap("修改成功!");
        }

    }

    /**
     * 删除人员信息
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/deletemp",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> deletemp(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        employeeRService.deleteEmployee(empCode);
        return getReturnMap("删除成功!");
    }

    /**
     * 拉取人员-机构表
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadEmpOrg",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> loadEmpOrg( @RequestBody String content) {
        // 收到请求
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        List<OmOrg> list = employeeRService.queryOrgbyEmpCode(empCode);
        return getReturnMap(list);
    }

    /**
     * 拉取人员-岗位表
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadEmpPos",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> loadEmpPos(@RequestBody String content) {
        // 收到请求
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        List<OmPosition> list = employeeRService.queryPosbyEmpCode(empCode);
        return getReturnMap(list);
    }

    /**
     * 拉取可指派机构列表
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadOrgNotInbyEmp",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> loadOrgNotInbyEmp(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        List<OmOrg> list = employeeRService.queryCanAddOrgbyEmpCode(empCode);
        return getReturnMap(list);
    }

    /**
     * 拉取可指派岗位列表
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/loadPosNotInbyEmp",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> loadPosNotInbyEmp(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        List<OmPosition> list = employeeRService.queryCanAddPosbyEmpCode(empCode);
        return getReturnMap(list);

    }

    /**
     * 指派机构
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assignOrg",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> assignOrg( @RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        String orgCode = jsonObj.getString("orgCode");
        String isMain = jsonObj.getString("isMain");
        if ("true".equals(isMain)) {
            employeeRService.assignOrg(empCode, orgCode, true);
        } else {
            employeeRService.assignOrg(empCode, orgCode, false);
        }
        return getReturnMap("指派成功!");
    }

    /**
     * 取消指派机构
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/disassignOrg",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> disassignOrg( @RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empGuid = jsonObj.getString("empGuid");
        String orgGuid = jsonObj.getString("orgGuid");
        employeeRService.deleteEmpOrg(orgGuid, empGuid);
        return getReturnMap("取消指派成功!");
    }

    /**
     * 指派
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/assignPos",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> assignPos( @RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        String posCode = jsonObj.getString("posCode");
        String isMain = jsonObj.getString("isMain");
        if ("true".equals(isMain)) {
            employeeRService.assignPosition(empCode, posCode, true);
        } else {
            employeeRService.assignPosition(empCode, posCode, false);
        }
        return getReturnMap("指派成功!");
    }

    /**
     * 取消指派机构
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/disassignPos",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> disassignPos(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empGuid = jsonObj.getString("empGuid");
        String posGuid = jsonObj.getString("posGuid");
        employeeRService.deleteEmpPosition(posGuid, empGuid);
        return getReturnMap("取消指派成功!");
    }

    /**
     * 生成员工代码
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/initEmpCode",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> initEmpCode( @RequestBody String content) {
        // 收到请求
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = employeeRService.genEmpCode(null, null);
        return getReturnMap(empCode);
    }

    /**
     * 指定新的主机构
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fixmainOrg",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> fixmainOrg(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        String orgCode = jsonObj.getString("orgCode");
        employeeRService.fixMainOrg(empCode, orgCode);
        return getReturnMap("指定成功!");
    }

    /**
     * 指定新的主岗位
     *
     * @param content
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/fixmainPos",produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public Map<String, Object> fixmainPos(@RequestBody String content) {
        JSONObject jsonObj = JSONObject.parseObject(content);
        String empCode = jsonObj.getString("empCode");
        String posCode = jsonObj.getString("posCode");
        employeeRService.fixMainPosition(empCode, posCode);
       return getReturnMap("指定成功!");
    }


    /**
     * 每个controller定义自己的返回信息变量
     */
    private Map<String, Object> responseMsg;


    @Override
    public Map<String, Object> getResponseMessage() {
        if (null == responseMsg) {
            responseMsg = new HashMap<String, Object>();
        }
        return responseMsg;
    }

}
