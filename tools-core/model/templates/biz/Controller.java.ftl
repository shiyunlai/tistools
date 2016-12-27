<#assign wcClassPackageVar="${mainPackage}.base.WhereCondition">
<#assign ajaxUtilsClassPackageVar="${mainPackage}.webapp.util.AjaxUtils">
<#assign jsonUtilsClassPackageVar="${mainPackage}.webapp.util.JSONUtils">
<#assign pageClassPackageVar="${mainPackage}.base.Page">
<#assign baseControllerPackageVar="${mainPackage}.webapp.controller.BaseController">
<#assign isDisPrjVar="${isDisPrj?string}">

/**
 * auto generated
 * Copyright (C) 2016 bronsp.com, All rights reserved.
 */
package ${packageName};

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import ${mainPackage}.model.po.${bizmodelId}.${poClassName};
import ${wcClassPackageVar};
import ${baseControllerPackageVar};
<#if isDisPrjVar == "true">
import com.alibaba.dubbo.config.annotation.Reference;
import ${mainPackage}.rservice.${bizmodelId}.I${poClassName}RService;
<#assign serviceClassVar ="I${poClassName}RService">
<#assign serviceAttrVar="${poClassName?uncap_first}RService">
<#else>
import org.springframework.beans.factory.annotation.Autowired;
import ${mainPackage}.service.${bizmodelId}.${poClassName}Service;
<#assign serviceClassVar ="${poClassName}Service">
<#assign serviceAttrVar="${poClassName?uncap_first}Service">
</#if>  
import ${ajaxUtilsClassPackageVar};
import ${jsonUtilsClassPackageVar};
import ${pageClassPackageVar};

/**
 * <p>
 * auto genegrated
 * </p>
 */
@Controller
@RequestMapping(value = "/${bizmodelId}")
public class ${poClassName}Controller extends BaseController {

<#if isDisPrjVar == "true" >
	@Reference(group="${bizmodelId}",version="1.0",interfaceClass=${serviceClassVar}.class)
	${serviceClassVar} ${serviceAttrVar};
<#else>
	@Autowired
	${serviceClassVar} ${serviceAttrVar};
</#if>
	
	@RequestMapping(value = "/${poClassName?uncap_first}/edit")
	public String execute(ModelMap model, @RequestBody String content,
			String age, HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			JSONObject job = jsonObj.getJSONObject("item");
			${poClassName} p = new ${poClassName}();
			JSONObject.toBean(job,p,jsonConfig);
			String id = sequenceBiz.generateId("${poClassName}");
			if (StringUtils.isNotEmpty(p.getId())) {
				${serviceAttrVar}.update(p);
			} else {
				p.setId(id);
				//initCreate(p, request);
				${serviceAttrVar}.insert(p);
			}
			AjaxUtils.ajaxJson(response, JSONObject.fromObject(p).toString());
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "添加失败!");
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/${poClassName?uncap_first}/delete")
	public String execute3(ModelMap model, @RequestBody String content,
			String age, HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONArray jsonArray = JSONArray.fromObject(content);
			List<${poClassName}> list = new ArrayList<${poClassName}>();
			for (int i = 0; i < jsonArray.size(); i++) {
				${poClassName} p = new ${poClassName}();
				JSONObject.toBean(JSONObject.fromObject(jsonArray.get(i)),p,jsonConfig);
				list.add(p);
			}
			List<String> ids = new ArrayList<String>();
			for (${poClassName} p : list) {
				ids.add(p.getId());
			}
			WhereCondition wc = new WhereCondition();
			wc.andIn("id", ids);
			${serviceAttrVar}.deleteByCondition(wc);
			AjaxUtils.ajaxJson(response, "");
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "删除失败!");
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/${poClassName?uncap_first}/list")
	public String execute1(ModelMap model, @RequestBody String content,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			// 分页对象
			Page page = getPage(jsonObj);
			// 服务端排序规则
			String orderGuize = getOrderGuize(JSONUtils.getStr(jsonObj, "orderGuize"));
			// 组装查询条件
			WhereCondition wc = new WhereCondition();
			wc.setLength(page.getItemsperpage());
			wc.setOffset((page.getCurrentPage() - 1) * page.getItemsperpage());
			wc.setOrderBy(orderGuize);
			initWanNengChaXun(jsonObj, wc);// 万能查询
			List list = ${serviceAttrVar}.query(wc);
			JSONArray ja = JSONArray.fromObject(list,jsonConfig);
			page.setTotalItems(${serviceAttrVar}.count(wc));
			Map map = new HashMap();
			map.put("page", page);
			map.put("list", ja);
			String s=JSONObject.fromObject(map,jsonConfig).toString();
			AjaxUtils.ajaxJson(response,s );
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/${poClassName?uncap_first}/list/id")
	public String executesd1(ModelMap model, @RequestBody String content,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			JSONObject jsonObj = JSONObject.fromObject(content);
			${poClassName} k =  ${serviceAttrVar}.loadById(JSONUtils.getStr(jsonObj, "id"));
			JSONObject jo = JSONObject.fromObject(k,jsonConfig);
			AjaxUtils.ajaxJson(response, jo.toString());
		} catch (Exception e) {// TODO
			AjaxUtils.ajaxJsonErrorMessage(response, "查询失败!");
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
