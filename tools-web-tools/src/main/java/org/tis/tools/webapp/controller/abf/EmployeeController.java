package org.tis.tools.webapp.controller.abf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.om.OmPosition;
import org.tis.tools.rservice.om.capable.IEmployeeRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import net.sf.json.JSONObject;

/**
 * 员工管理功能
 * 
 * @author
 */
@Controller
@RequestMapping(value = "/om/emp")
public class EmployeeController extends BaseController{
	@Autowired
	IEmployeeRService employeeRService;
	
	
	/**
	 * 查询所有人员信息
	 * 
	 * @param model
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryemployee")
	public String queryemployee(ModelMap model,  String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			List<OmEmployee> list = employeeRService.queryAllEmployyee();
			List list2 = new ArrayList();
			for(OmEmployee oe:list){
				Map map = BeanUtils.describe(oe);
				list2.add(map);
			}
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list2, "yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 新增人员信息
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addemployee")
	public String addemployee(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			OmEmployee oe = new OmEmployee();
			BeanUtils.populate(oe, jsonObj);
			if(oe.getGuid() == null || oe.getGuid() == ""){
				employeeRService.createEmployee(oe);
				AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
			}else{
				employeeRService.updateEmployee(oe);
				AjaxUtils.ajaxJsonSuccessMessage(response, "修改成功!");
			}
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 删除人员信息
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deletemp")
	public String deletemp(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			employeeRService.deleteEmployee(empCode);
			AjaxUtils.ajaxJsonSuccessMessage(response, "删除成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}

	/**
	 * 拉取人员-机构表
	 *
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadEmpOrg")
	public String loadEmpOrg(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
						   HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			List<OmOrg> list = employeeRService.queryOrgbyEmpCode(empCode);
			AjaxUtils.ajaxJsonSuccessMessage(response,list);
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 拉取人员-岗位表
	 *
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadEmpPos")
	public String loadEmpPos(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			List<OmPosition> list = employeeRService.queryPosbyEmpCode(empCode);
			AjaxUtils.ajaxJsonSuccessMessage(response,list);
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 拉取可指派机构列表
	 *
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadOrgNotInbyEmp")
	public String loadOrgNotInbyEmp(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			List<OmOrg> list = employeeRService.queryCanAddOrgbyEmpCode(empCode);
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 拉取可指派岗位列表
	 *
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadPosNotInbyEmp")
	public String loadPosNotInbyEmp(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			List<OmPosition> list = employeeRService.queryCanAddPosbyEmpCode(empCode);
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 指派机构
	 *
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/assignOrg")
	public String assignOrg(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			String orgCode = jsonObj.getString("orgCode");
			String isMain = jsonObj.getString("isMain");
			if("true".equals(isMain)){
				employeeRService.assignOrg(empCode, orgCode,true);
			}else {
				employeeRService.assignOrg(empCode, orgCode,false);
			}

			AjaxUtils.ajaxJsonSuccessMessage(response, "指派成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 取消指派机构
	 *
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/disassignOrg")
	public String disassignOrg(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empGuid = jsonObj.getString("empGuid");
			String orgGuid = jsonObj.getString("orgGuid");
			employeeRService.deleteEmpOrg(orgGuid, empGuid);
			AjaxUtils.ajaxJsonSuccessMessage(response, "取消指派成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 指派
	 *
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/assignPos")
	public String assignPos(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			String posCode = jsonObj.getString("posCode");
			String isMain = jsonObj.getString("isMain");
			if("true".equals(isMain)){
				employeeRService.assignPosition(empCode, posCode,true);
			}else {
				employeeRService.assignPosition(empCode, posCode,false);
			}
			AjaxUtils.ajaxJsonSuccessMessage(response, "指派成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 取消指派机构
	 *
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/disassignPos")
	public String disassignPos(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empGuid = jsonObj.getString("empGuid");
			String posGuid = jsonObj.getString("posGuid");
			employeeRService.deleteEmpPosition(posGuid, empGuid);
			AjaxUtils.ajaxJsonSuccessMessage(response, "取消指派成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 生成员工代码
	 *
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/initEmpCode")
	public String initEmpCode(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			//toDO
			String empCode = employeeRService.genEmpCode(null, null);
			AjaxUtils.ajaxJsonSuccessMessage(response, empCode);
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}

	/**
	 * 指定新的主机构
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fixmainOrg")
	public String fixmainOrg(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			String orgCode = jsonObj.getString("orgCode");
			employeeRService.fixMainOrg(empCode, orgCode);
			AjaxUtils.ajaxJsonSuccessMessage(response, "指定成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 指定新的主岗位
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/fixmainPos")
	public String fixmainPos(ModelMap model, @RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String empCode = jsonObj.getString("empCode");
			String posCode = jsonObj.getString("posCode");
			employeeRService.fixMainPosition(empCode, posCode);
			AjaxUtils.ajaxJsonSuccessMessage(response, "指定成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
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
