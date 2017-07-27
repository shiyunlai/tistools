package org.tis.tools.webapp.controller.abf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tis.tools.model.po.om.OmDuty;
import org.tis.tools.rservice.om.capable.IDutyRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

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
	
	/**
	 * 展示职务树
	 * 
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/dutytree")
	public String execute(ModelMap model, @RequestBody String content, String age, HttpServletRequest request,
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
				List<Map> list = new ArrayList<>();
				for(int i = 1;i<6;i++){
					Map map = new HashMap<>();
					map.put("text", "套别"+i);
					map.put("id", "0"+i);
					list.add(map);
				}
				AjaxUtils.ajaxJsonSuccessMessage(response, list);
			}else if(id.length() == 2){
				List<OmDuty> list = dutyRService.queryDutyByDutyType(id);
				AjaxUtils.ajaxJsonSuccessMessage(response, list);
			}
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "查询树失败!");
			e.printStackTrace();
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
