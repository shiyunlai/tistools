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
import org.tis.tools.rservice.om.capable.IEmployeeRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import com.alibaba.fastjson.JSON;

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
	 * @param content
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
			AjaxUtils.ajaxJsonSuccessMessage(response,list2);
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
			employeeRService.createEmployee(oe);
			AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
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
