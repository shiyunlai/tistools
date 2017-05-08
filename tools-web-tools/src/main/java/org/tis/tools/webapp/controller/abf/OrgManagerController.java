
/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package org.tis.tools.webapp.controller.abf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tis.tools.model.po.jnl.JnlPrefill;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.rservice.om.capable.IOrgRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import net.sf.json.JSONObject;

/**
 * 机构管理功能
 * @author 
 */
@Controller
@RequestMapping(value = "/om/org")
public class OrgManagerController extends BaseController {

	@Autowired
	IOrgRService orgRService;
	
	/**
	 * 展示机构树
	 * @param model
	 * @param content
	 * @param age
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/tree")
	public String execute(ModelMap model, @RequestBody String content,
			String age, HttpServletRequest request, HttpServletResponse response) {
		try {
			//收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			JSONObject job = jsonObj.getJSONObject("item");
			JnlPrefill p = new JnlPrefill();
			JSONObject.toBean(job,p,jsonConfig);
			
			//调用远程服务
			List<OmOrg> rootOrgs = orgRService.queryAllRoot() ; 
			
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(rootOrgs).toString());
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "查询根机构树失败!");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 每个controller定义自己的返回信息变量
	 */
	private Map<String, Object> responseMsg ;
	@Override
	public Map<String, Object> getResponseMessage() {
		if( null == responseMsg ){
			responseMsg = new HashMap<String, Object> () ;
		}
		return responseMsg ;
	}
}
