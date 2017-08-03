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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmGroup;
import org.tis.tools.rservice.om.capable.IEmployeeRService;
import org.tis.tools.rservice.om.capable.IGroupRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 机构管理功能
 * 
 * @author
 */
@Controller
@RequestMapping(value = "/om/workgroup")
public class WorkGroupController extends BaseController {
	@Autowired
	IGroupRService groupRService;
	@Autowired
	IEmployeeRService employeeRService;
	/**
	 * 展示工作组树
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/workgrouptree")
	public String workgroup(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String id = jsonObj.getString("id");
			// 通过id判断需要加载的节点
			if ("#".equals(id)) {
				// 调用远程服务,#:根
				Map<String,String> map = new HashMap<>();
				map.put("groupCode", "00000");
				map.put("groupName", "工作组树");
				List<Map> list = new ArrayList<>();
				list.add(map);
				AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response,list ,"yyyy-MM-dd");
			} else if ("00000".equals(id)) {
				List<OmGroup> list = groupRService.queryRootGroup();
				AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response,list ,"yyyy-MM-dd");
			} else {
				List<OmGroup> list = groupRService.queryChildGroup(id);
				AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response,list ,"yyyy-MM-dd");
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
	 * 新增根工作组
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/add")
	public String add(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			JSONObject jsonObj = JSONObject.parseObject(content);

			String flag = jsonObj.getString("flag");
			jsonObj.remove("flag");
			if (flag.equals("root")) {
				OmGroup og = new OmGroup();
				BeanUtils.populate(og, jsonObj);
				groupRService.createRootGroup(og);
			} else {// 新增子节点
				OmGroup og = new OmGroup();
				BeanUtils.populate(og, jsonObj);
				groupRService.createGroup(og);
			}
			AjaxUtils.ajaxJsonSuccessMessage(response, "新增工作组成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getParams());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "新增工作组失败");
		}
		return null;
	}

	/**
	 * 编辑工作组
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/edit")
	public String edit(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {

			JSONObject jsonObj = JSONObject.parseObject(content);
			OmGroup og = new OmGroup();
			BeanUtils.populate(og, jsonObj);
			groupRService.createRootGroup(og);
			AjaxUtils.ajaxJsonSuccessMessage(response, "新增根工作组成功!");
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "新增根工作组失败!");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除工作组
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public String delete(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupCode = jsonObj.getString("groupCode");
			groupRService.deleteGroup(groupCode);
			AjaxUtils.ajaxJsonSuccessMessage(response, "删除工作组成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}

	/**
	 * 生成工作组代码
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/initGroupCode")
	public String initGroupCode(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupType = jsonObj.getString("groupType");
			String groupCode = groupRService.genGroupCode(groupType);
			AjaxUtils.ajaxJsonSuccessMessage(response,groupCode, "生成工作组编号成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 查询所有工作组列表
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryall")
	public String queryall(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		try {
			// 查询所有工作组
			List<OmGroup> list = groupRService.queryAllGroup();
			List<Map> l = new ArrayList<Map>();
			for (OmGroup o : list) {
				Map m1 = BeanUtils.describe(o);
				l.add(m1);
			}
			AjaxUtils.ajaxJson(response, net.sf.json.JSONArray.fromObject(l).toString());
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}

	/**
	 * 条件查询工作组列表
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryChild")
	public String queryby(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupCode = jsonObj.getString("groupCode");
			List<OmGroup> ogList = groupRService.queryAllchild(groupCode);
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, ogList, "yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 启用---注销工作组
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/enableGroup")
	public String enableGroup(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupCode = jsonObj.getString("groupCode");
			String flag = jsonObj.getString("flag");
			if("running".equals(flag)){
				groupRService.cancelGroup(groupCode);
			}else if("cancel".equals(flag)){
				groupRService.reenableGroup(groupCode, true);
			}
			AjaxUtils.ajaxJsonSuccessMessage(response, "启用成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 更新修改工作组
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateGroup")
	public String updateGroup(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			OmGroup og = JSONObject.parseObject(content, OmGroup.class);
			groupRService.updateGroup(og);
			AjaxUtils.ajaxJsonSuccessMessage(response, "修改成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 生成下级岗位列表
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadPosition")
	public String loadPosition(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupCode = jsonObj.getString("groupCode");
			//TODO
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 生成下级人员列表
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadempin")
	public String loadempin(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupCode = jsonObj.getString("groupCode");
			List<OmEmployee> empList = groupRService.queryEmployee(groupCode);
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, empList, "yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 加载不在此工作组的人员列表(同属同一机构)
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadempNotin")
	public String loadempNotin(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupCode = jsonObj.getString("groupCode");
			String guidOrg = jsonObj.getString("guidOrg");
			List<OmEmployee> empList = groupRService.queryEmpNotInGroup(guidOrg, groupCode);
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, empList, "yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 *新添人员
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addEmpGroup")
	public String addEmpGroup(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupGuid = jsonObj.getString("groupGuid");
			List<Object> empguidList = jsonObj.getJSONArray("empGuidlist");
			transactionTemplate.execute(new TransactionCallback<String>() {
				@Override
				public String doInTransaction(TransactionStatus status) {
					try {
						for(Object o: empguidList){
							employeeRService.insertEmpGroup(groupGuid, o.toString());
						}
						return "success";
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw e;
					}
				}
			});
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 *删除人员-工作组关联
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteEmpGroup")
	public String deleteEmpGroup(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.parseObject(content);
			String groupGuid = jsonObj.getString("guid");
			List<Object> empguidList = jsonObj.getJSONArray("empGuidlist");
			transactionTemplate.execute(new TransactionCallback<String>() {
				@Override
				public String doInTransaction(TransactionStatus status) {
					try {
						for(Object o: empguidList){
							employeeRService.deleteEmpGroup(groupGuid, o.toString());
						}
						return "success";
					} catch (Exception e) {
						status.setRollbackOnly();
						e.printStackTrace();
						throw e;
					}
				}
			});
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
