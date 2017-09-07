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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcOperator;
import org.tis.tools.model.po.om.OmDuty;
import org.tis.tools.model.po.om.OmEmployee;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.po.sys.SysDict;
import org.tis.tools.model.po.sys.SysDictItem;
import org.tis.tools.rservice.om.capable.IDutyRService;
import org.tis.tools.rservice.sys.capable.IDictRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import com.alibaba.dubbo.common.json.ParseException;
import com.alibaba.fastjson.JSONObject;

/**
 * 职务定义功能
 * 
 * @author
 */
@Controller
@RequestMapping(value = "/om/duty")
public class DutyController extends BaseController{
	@Autowired
	IDutyRService dutyRService;
	@Autowired
	IDictRService dictRService;
	
	/**
	 * 展示职务树
	 * 
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dutytree")
	public String execute(ModelMap model, @RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String id = jsonObj.getString("id");
			String dutyType = jsonObj.getString("dutyType");
			String dutyCode = jsonObj.getString("dutyCode");
			if("#".equals(id)){
				Map map = new HashMap<>();
				map.put("text", "职务树");
				map.put("id", "00000");
				List<Map> list = new ArrayList<>();
				list.add(map);
				AjaxUtils.ajaxJsonSuccessMessage(response, list);
			}else if ("00000".equals(id)) {
				SysDict queryDict = dictRService.queryDict("DICT_OM_DUTYTYPE");
				List<SysDictItem> querySysDictItems = dictRService.querySysDictItems(queryDict.getGuid());
				AjaxUtils.ajaxJsonSuccessMessage(response, querySysDictItems);
			}else if(id.length() == 2){
				List<OmDuty> list = dutyRService.queryDutyByDutyTypeOnlyF(id);
				AjaxUtils.ajaxJsonSuccessMessage(response, list);
			}else{
				List<OmDuty> list = dutyRService.queryChildByDutyCode(id);
				AjaxUtils.ajaxJsonSuccessMessage(response, list);
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
	 * 展示筛选树
	 * @param model
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/searchtree")
	public String searchtree(ModelMap model, @RequestBody String content, HttpServletRequest request,
						  HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String dutyName = jsonObj.getString("searchitem");
			String id = jsonObj.getString("id");
			if ("#".equals(id)) {
				List<OmDuty> dutyList = dutyRService.queryBydutyName(dutyName);
				AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, dutyList, "yyyy-MM-dd");
			}else{
				List<OmDuty> list = dutyRService.queryChildByDutyCode(id);
				AjaxUtils.ajaxJsonSuccessMessage(response, list);
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
	 * 生成职务列表
	 * 
	 * @param model
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/loadallduty")
	public String loadallduty(ModelMap model, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			List<OmDuty> list = dutyRService.queryAllDuty();
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response, list, "yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 新增职务
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addduty")
	public String addduty(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String dutyCode = jsonObj.getString("dutyCode");
			String dutyName = jsonObj.getString("dutyName");
			String dutyType = jsonObj.getString("dutyType");
			String remark = jsonObj.getString("remark");
			String parentsCode = jsonObj.getString("parentsCode");
			dutyRService.createDuty(dutyCode, dutyName, dutyType, parentsCode, remark);
			AjaxUtils.ajaxJsonSuccessMessage(response, "新增成功!");
		}catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	
	/**
	 * 生成职务代码
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/initdutyCode")
	public String initdutyCode(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String dutyType = jsonObj.getString("dutyType");
			String dutyCode = dutyRService.genDutyCode(dutyType);
			AjaxUtils.ajaxJsonSuccessMessage(response, dutyCode);
		}catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 通过职务套别查询职务
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/querydutybyType")
	public String querydutybyType(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String dutyType = jsonObj.getString("dutyType");
			List<OmDuty> list = dutyRService.queryDutyByDutyType(dutyType);
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
	 * 查询下级职务
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/querychild")
	public String querychild(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String dutyCode = jsonObj.getString("dutyCode");
			List<OmDuty> list = dutyRService.queryChildByDutyCode(dutyCode);
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
	 * 查询职务下人员,用过岗位查询
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/queryempbudutyCode")
	public String queryempbudutyCode(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String dutyCode = jsonObj.getString("dutyCode");
			List<OmEmployee> list = dutyRService.quereyEmployeeByDutyCode(dutyCode);
			AjaxUtils.ajaxJsonSuccessMessageWithDateFormat(response,list,"yyyy-MM-dd");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
	/**
	 * 删除职务
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deletedutyByCode")
	public String deletedutyByCode(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			String dutyCode = jsonObj.getString("dutyCode");
			dutyRService.deleteDuty(dutyCode);
			AjaxUtils.ajaxJsonSuccessMessage(response,"删除成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	/**
	 * 更新职务
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updateDuty")
	public String updateDuty(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 收到请求
			JSONObject jsonObj = JSONObject.parseObject(content);
			OmDuty od = new OmDuty();
			BeanUtils.populate(od, jsonObj);
			dutyRService.updateDuty(od);
			AjaxUtils.ajaxJsonSuccessMessage(response,"更新成功!");
		} catch (ToolsRuntimeException e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, e.getCode(), e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "SYS_0001", e.getMessage());
		}
		return null;
	}
	
    /**
     * 查询所有职务
     *
     */
    @ResponseBody
    @RequestMapping(value="/queryDutyList" ,produces = "text/plain;charset=UTF-8",method= RequestMethod.POST)
    public String queryDutyList(@RequestBody String content, HttpServletRequest request,
                           HttpServletResponse response) throws ToolsRuntimeException, ParseException {
        try {
            if (logger.isInfoEnabled()) {
                logger.info("queryDutyList request : " + content);
            }
            List<OmDuty> dutyList = dutyRService.queryAllDuty();
            AjaxUtils.ajaxJsonSuccessMessage(response,dutyList);
        } catch (ToolsRuntimeException e) {
            AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
            logger.error("queryDutyList exception : ", e);
        }catch (Exception e) {
            AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
            logger.error("queryDutyList exception : ", e);
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
