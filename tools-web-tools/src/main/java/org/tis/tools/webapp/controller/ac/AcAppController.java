package org.tis.tools.webapp.controller.ac;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.rservice.ac.basic.IAcAppRService;
import org.tis.tools.rservice.ac.capable.IApplicationRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

import java.math.BigDecimal; //转换

@Controller
@RequestMapping("/AcAppController")
public class AcAppController extends BaseController {
	@Autowired
	IApplicationRService applicationRService;
	
	/**
	 * appAdd新增应用服务员
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/appAdd" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String testPostController(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("appAdd request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	
			String appCode = jsonObj.getString("appCode");
			String appName = jsonObj.getString("appName");
			String appType = jsonObj.getString("appType");
			String appDesc = jsonObj.getString("appDesc");
			String isOpen = jsonObj.getString("isOpen");
			String openDate = jsonObj.getString("openDate");
			SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd");
			Date date = times.parse(openDate);
			String url = jsonObj.getString("url");
			String ipAddr = jsonObj.getString("ipAddr");
			String ipPort = jsonObj.getString("ipPort");
			AcApp ac = new AcApp();
			ac.setAppCode(appCode);
			ac.setAppName(appName);
			ac.setAppType(appType);
			ac.setAppDesc(appDesc);
			ac.setIsopen(isOpen);
			ac.setOpenDate(date);
			ac.setUrl(url);
			ac.setIpPort(ipPort);
			ac.setIpAddr(ipAddr);
			applicationRService.createAcApp(ac);//把参数全部填写上
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, JSONArray.fromObject(result).toString());
			logger.error("appAdd exception : ", e);
		}
		return null;
	}
	
	/**
	 * appDel删除方法
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value="/appDel",method=RequestMethod.POST)
	public String appDel(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("appAdd request : " + content);
			}	
			JSONObject jsonObj = JSONObject.fromObject(content);	
			String guid = jsonObj.getString("id");
			applicationRService.deleteAcApp(guid);
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, JSONArray.fromObject(result).toString());
			logger.error("appDel exception : ", e);
		}
		return null;
	}
	
	/**
	 * appEdit修改方法
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appEdit",method=RequestMethod.POST)
	public String appEdit(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("appEdit request : " + content);
			} 
			JSONObject jsonObj = JSONObject.fromObject(content);
			String id = jsonObj.getString("id");
			AcApp acApp = applicationRService.queryAcApp(id);
			acApp.setAppCode(jsonObj.getString("appCode"));
			acApp.setAppName(jsonObj.getString("appName"));
			acApp.setAppType(jsonObj.getString("appType"));
			acApp.setAppDesc(jsonObj.getString("appDesc"));
			acApp.setIsopen(jsonObj.getString("isOpen"));
			String openDate = jsonObj.getString("openDate");
			SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd");
			Date date = times.parse(openDate);
			acApp.setOpenDate(date);
			acApp.setUrl(jsonObj.getString("url"));
			acApp.setIpAddr(jsonObj.getString("ipAddr"));
			acApp.setIpPort(jsonObj.getString("ipPort"));
			applicationRService.updateAcApp(acApp);
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, JSONArray.fromObject(result).toString());
			logger.error("appEdit exception : ", e);
		}
		return null;
	}
	
	/**
	 * appSearch查询应用
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/appQuery" ,method=RequestMethod.POST)
	public String appSearch(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("appQuery request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);
			String id = jsonObj.getString("id");
			//通过id判断需要加载的节点
			WhereCondition wc;
			if("#".equals(id)){
				Map map=new HashMap();
				map.put("ROOT_NAME", "应用功能管理");
				map.put("ROOT_CODE", "AC0000");
				result.put("data", map);//返回给前台的数据
			}else if("AC0000".equals(id)){
				//调用远程服务,#:根
				 List<AcApp> ac = applicationRService.queryAcRootList();
				 result.put("data", ac);//返回给前台的数据
			}else if(id.length()>3&&"APP".equals(id.substring(0, 3))){	
				List<AcFuncgroup> group = applicationRService.queryAcRootFuncgroup(id);
				result.put("data", group);//返回给前台的数据
			}else if(id.length()>9&&"FUNCGROUP".equals(id.substring(0, 9))){
				Map map=new HashMap();
				List<AcFuncgroup> groupList = applicationRService.queryAcChildFuncgroup(id);
				if(groupList.size()>0){
					map.put("groupList", "groupList");
				}
				List<AcFunc> funcList = applicationRService.queryAcGroupFunc(id);
				if(funcList.size()>0){
					map.put("funcList", funcList);
				}
				result.put("data", map);//返回给前台的数据
			}
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJson(response, net.sf.json.JSONArray.fromObject(result).toString());
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, net.sf.json.JSONArray.fromObject(result).toString());
			logger.error("appQuery exception : ", e);
		}
		return null;
	}
	
	
	/**
	 * groupAdd新增功能组
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/groupAdd" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String groupAdd(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("groupAdd request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			
			String FUNCGROUP_NAME = jsonObj.getString("FUNCGROUP_NAME");
			String GROUP_LEVEL = jsonObj.getString("GROUP_LEVEL");
			String GUID_APP = jsonObj.getString("GUID_APP");
			String GUID_PARENTS = jsonObj.getString("GUID_PARENTS");
			//转换成BigDecimal类型
			BigDecimal groupLevel=new BigDecimal(GROUP_LEVEL);
			groupLevel=groupLevel.setScale(2, BigDecimal.ROUND_HALF_UP); //小数位2位，四舍五入
			AcFuncgroup acFuncgroup = new AcFuncgroup();//new 一个新对象
			acFuncgroup.setFuncgroupName(FUNCGROUP_NAME);
			acFuncgroup.setGroupLevel(groupLevel);
			acFuncgroup.setGuidApp(GUID_APP);
			acFuncgroup.setGuidParents(GUID_PARENTS);
			AcFuncgroup acgrop = applicationRService.createAcFuncGroup(acFuncgroup );//把new的并且填入参数的对象，传入，返回
			result.put("data", acgrop);//返回给前台的数据
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());//返回给前台的结
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, net.sf.json.JSONArray.fromObject(result).toString());
			logger.error("groupAdd exception : ", e);
		}
		return null;
	}
	
	/*
	 * groupDel删除功能组
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/groupDel" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String groupDel(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("groupDel request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			applicationRService.deleteAcFuncGroup(guid);//把new的并且填入参数的对象，传入，返回
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());//返回给前台的结
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, net.sf.json.JSONArray.fromObject(result).toString());
			logger.error("groupDel exception : ", e);
		}
		return null;
	}
	
	/**
	 * groupEdit修改功能组
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/groupEdit" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String groupEdit(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("groupEdit request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			AcFuncgroup acFuncgroup = applicationRService.queryFuncgroup(guid);
			
			String FUNCGROUP_NAME = jsonObj.getString("FUNCGROUP_NAME");
			String GROUP_LEVEL = jsonObj.getString("GROUP_LEVEL");
			String GUID_PARENTS = jsonObj.getString("GUID_PARENTS");
			acFuncgroup.setFuncgroupName(FUNCGROUP_NAME);
			acFuncgroup.setGroupLevel(new BigDecimal(GROUP_LEVEL));
			acFuncgroup.setGuidParents(GUID_PARENTS);
			applicationRService.updateAcFuncgroup(acFuncgroup);//把new的并且填入参数的对象，传入，返回
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());//返回给前台的结
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, net.sf.json.JSONArray.fromObject(result).toString());
			logger.error("groupEdit exception : ", e);
		}
		return null;
	}
	
	/**
	 * acFuncAdd新增功能
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/acFuncAdd" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String acFuncAdd(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("acFuncAdd request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String funcCode = jsonObj.getString("funcCode");
			String funcName = jsonObj.getString("funcName");
			String funcAction = jsonObj.getString("funcAction");
			String paraInfo = jsonObj.getString("paraInfo");
			String funcType = jsonObj.getString("funcType");
			String isCheck = jsonObj.getString("isCheck");
			String isMenu = jsonObj.getString("isMenu");
			String guidFuncgroup = jsonObj.getString("guidFuncgroup");
			AcFunc acFunc = new AcFunc();
			acFunc.setFuncCode(funcCode);
			acFunc.setFuncName(funcName);
			acFunc.setFuncAction(funcAction);
			acFunc.setParaInfo(paraInfo);
			acFunc.setFuncType(funcType);
			acFunc.setIscheck(isCheck);
			acFunc.setIsmenu(isMenu);
			acFunc.setGuidFuncgroup(guidFuncgroup);
			applicationRService.createAcFunc(acFunc);//把new的并且填入参数的对象，传入，返回
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());//返回给前台的结
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, net.sf.json.JSONArray.fromObject(result).toString());
			logger.error("acFuncAdd exception : ", e);
		}
		return null;
	}
	
	/**
	 * acFuncDel删除功能
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/acFuncDel" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String acFuncDel(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("acFuncDel request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			applicationRService.deleteAcFunc(guid);
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());//返回给前台的结
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, net.sf.json.JSONArray.fromObject(result).toString());
			logger.error("acFuncDel exception : ", e);
		}
		return null;
	}
	
	/**
	 * acFuncEdit更新功能
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/acFuncEdit" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String acFuncEdit(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("acFuncEdit request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			AcFunc acFunc = applicationRService.queryFunc(guid);
//			funcCode,funcName,funcAction,paraInfo,funcType,isCheck,isMenu
			String funcCode = jsonObj.getString("funcCode");
			String funcName = jsonObj.getString("funcName");
			String funcAction = jsonObj.getString("funcAction");
			String paraInfo = jsonObj.getString("paraInfo");
			String funcType = jsonObj.getString("funcType");
			String isCheck = jsonObj.getString("isCheck");
			String isMenu = jsonObj.getString("isMenu");
			acFunc.setFuncCode(funcCode);
			acFunc.setFuncName(funcName);
			acFunc.setFuncAction(funcAction);
			acFunc.setParaInfo(paraInfo);
			acFunc.setFuncType(funcType);
			acFunc.setIscheck(isCheck);
			acFunc.setIsmenu(isMenu);
			applicationRService.updateAcFunc(acFunc);
			result.put("status", "0");//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());//返回给前台的结
		} catch (Exception e) {
			result.put("status", "1");//返回给前台的数据
			result.put("error_message", e.getMessage());//返回给前台的数据
			AjaxUtils.ajaxJsonErrorMessage(response, net.sf.json.JSONArray.fromObject(result).toString());
			logger.error("acFuncEdit exception : ", e);
		}
		return null;
	}
	
	
	private Map<String, Object> responseMsg ;
	@Override
	public Map<String, Object> getResponseMessage() {
		if( null == responseMsg ){
			responseMsg = new HashMap<String, Object> () ;
		}
		return responseMsg;
	}
	

}