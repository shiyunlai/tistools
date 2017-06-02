
/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
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
import org.tis.tools.model.po.jnl.JnlPrefill;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.rservice.om.capable.IOrgRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import com.alibaba.dubbo.common.json.JSONArray;

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
	public String execute(ModelMap model,  @RequestBody String content,
			String age, HttpServletRequest request, HttpServletResponse response) {
		try {
			//收到请求
			JSONObject jsonObj = JSONObject.fromObject(content);
			String id = jsonObj.getString("id");
			List<OmOrg> rootOrgs;
			//通过id判断需要加载的节点
			if("#".equals(id)){
				//调用远程服务,#:根
				rootOrgs = orgRService.queryAllRoot() ;
			}else if(id.startsWith("@")){
				//TODO
				//返回机构下岗位信息.根据id查询岗位信息并返回生成树节点.
				rootOrgs = new ArrayList<OmOrg>();
			}else{
				rootOrgs = orgRService.queryChilds(id);
				OmOrg og = new OmOrg();
				//为每一个节点增加岗位信息节点
				og.setOrgName("岗位信息");
				og.setOrgCode("@"+id);
				rootOrgs.add(og);
			}
			
			AjaxUtils.ajaxJson(response, net.sf.json.JSONArray.fromObject(rootOrgs).toString());
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
