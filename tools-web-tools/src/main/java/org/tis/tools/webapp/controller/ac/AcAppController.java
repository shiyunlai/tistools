package org.tis.tools.webapp.controller.ac;

import java.text.ParseException;
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
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcFuncResource;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.vo.ac.AcAppVo;
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
	 * @throws ParseException 
	 */
	@ResponseBody
	@RequestMapping(value="/appAdd" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String testPostController(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) throws ToolsRuntimeException, ParseException{
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
			AjaxUtils.ajaxJsonSuccessMessage(response,"");
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("appAdd exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
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
		try {
			if (logger.isInfoEnabled()) {
				logger.info("appAdd request : " + content);
			}	
			JSONObject jsonObj = JSONObject.fromObject(content);	
			String guid = jsonObj.getString("id");
			applicationRService.deleteAcApp(guid);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("appDel exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
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
	 * @throws ParseException 
	 */
	@RequestMapping(value="/appEdit",method=RequestMethod.POST)
	public String appEdit(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
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
			String openDateStr = jsonObj.getString("openDateStr");
			SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd");
			Date date = times.parse(openDateStr);
			acApp.setOpenDate(date);
			acApp.setUrl(jsonObj.getString("url"));
			acApp.setIpAddr(jsonObj.getString("ipAddr"));
			acApp.setIpPort(jsonObj.getString("ipPort"));
			applicationRService.updateAcApp(acApp);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("appEdit exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("appEdit exception : ", e);
		}
		return null;
	}
	
	/**
	 * appQuery查询应用
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
				map.put("rootName", "应用功能管理");
				map.put("rootCode", "AC0000");
				result.put("data", map);//返回给前台的数据
			}else if("AC0000".equals(id)){
				//调用远程服务,#:根
				 List<AcAppVo> ac = applicationRService.queryAcRootList();
				 result.put("data", ac);//返回给前台的数据
			}else if(id.length()>3&&"APP".equals(id.substring(0, 3))){	
				List<AcFuncgroup> group = applicationRService.queryAcRootFuncgroup(id);
				result.put("data", group);//返回给前台的数据
			}else if(id.length()>9&&"FUNCGROUP".equals(id.substring(0, 9))){
				Map map=new HashMap();
				List<AcFuncgroup> groupList = applicationRService.queryAcChildFuncgroup(id);
				if(groupList.size()>0){
					map.put("groupList", groupList);
				}
				List<AcFunc> funcList = applicationRService.queryAcGroupFunc(id);
				if(funcList.size()>0){
					map.put("funcList", funcList);
				}
				result.put("data", map);//返回给前台的数据
			}
			AjaxUtils.ajaxJsonSuccessMessage(response, result.get("data"));
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("appQuery exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
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
		try {
			if (logger.isInfoEnabled()) {
				logger.info("groupAdd request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			
			String funcgroupName = jsonObj.getString("funcgroupName");
			String groupLevel = jsonObj.getString("groupLevel");
			String guidApp = jsonObj.getString("guidApp");
			String guidParents = jsonObj.getString("guidParents");
			//转换成BigDecimal类型
			BigDecimal groupLevelBd=new BigDecimal(groupLevel);
			groupLevelBd=groupLevelBd.setScale(2, BigDecimal.ROUND_HALF_UP); //小数位2位，四舍五入
			AcFuncgroup acFuncgroup = new AcFuncgroup();//new 一个新对象
			acFuncgroup.setFuncgroupName(funcgroupName);
			acFuncgroup.setGroupLevel(groupLevelBd);
			acFuncgroup.setGuidApp(guidApp);
			acFuncgroup.setGuidParents(guidParents);
			AcFuncgroup acgrop = applicationRService.createAcFuncGroup(acFuncgroup );//把new的并且填入参数的对象，传入，返回
			AjaxUtils.ajaxJsonSuccessMessage(response, acgrop);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("groupAdd exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("groupAdd exception : ", e);
		}
		return null;
	}
	
	/**
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
		try {
			if (logger.isInfoEnabled()) {
				logger.info("groupDel request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			applicationRService.deleteAcFuncGroup(guid);//把new的并且填入参数的对象，传入，返回
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("groupDel exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
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
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("groupEdit request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			AcFuncgroup acFuncgroup = applicationRService.queryFuncgroup(guid);
			
			String funcgroupName = jsonObj.getString("funcgroupName");
			String groupLevel = jsonObj.getString("groupLevel");
			String guidParents = jsonObj.getString("guidParents");
			acFuncgroup.setFuncgroupName(funcgroupName);
			acFuncgroup.setGroupLevel(new BigDecimal(groupLevel));
			acFuncgroup.setGuidParents(guidParents);
			applicationRService.updateAcFuncgroup(acFuncgroup);//把new的并且填入参数的对象，传入，返回
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("groupEdit exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
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
			
			//设置功能对应资源
			AcFuncResource acFuncResource = new AcFuncResource();
			String resType = jsonObj.getString("resType");
			String compackName = jsonObj.getString("compackName");
			String resshowName = jsonObj.getString("resshowName");
			String resPath = jsonObj.getString("resPath");
			acFuncResource.setResType(resType);
			acFuncResource.setCompackName(compackName);
			acFuncResource.setResShowName(resshowName);
			acFuncResource.setResPath(resPath);
			
			AcFunc acFunc = new AcFunc();
			acFunc.setFuncCode(funcCode);
			acFunc.setFuncName(funcName);
			acFunc.setFuncAction(funcAction);
			acFunc.setParaInfo(paraInfo);
			acFunc.setFuncType(funcType);
			acFunc.setIscheck(isCheck);
			acFunc.setIsmenu(isMenu);
			acFunc.setGuidFuncgroup(guidFuncgroup);
			applicationRService.createAcFunc(acFunc,acFuncResource);//把new的并且填入参数的对象，传入，返回
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("acFuncAdd exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
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
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("acFuncDel request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			applicationRService.deleteAcFunc(guid);
			AjaxUtils.ajaxJsonSuccessMessage(response,"");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("acFuncDel exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
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
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("acFuncEdit request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			AcFunc acFunc = applicationRService.queryFunc(guid);
			String funcCode = jsonObj.getString("funcCode");
			String funcName = jsonObj.getString("funcName");
			String funcAction = jsonObj.getString("funcAction");
			String paraInfo = jsonObj.getString("paraInfo");
			String funcType = jsonObj.getString("funcType");
			//String isCheck = jsonObj.getString("isCheck");
			String isMenu = jsonObj.getString("isMenu");
		
						
			acFunc.setFuncCode(funcCode);
			acFunc.setFuncName(funcName);
			acFunc.setFuncAction(funcAction);
			acFunc.setParaInfo(paraInfo);
			acFunc.setFuncType(funcType);
			//acFunc.setIscheck(isCheck);
			acFunc.setIsmenu(isMenu);
			
			//设置功能对应资源
			AcFuncResource acFuncResource = new AcFuncResource();
			/*String resType = jsonObj.getString("resType");
			String compackName = jsonObj.getString("compackName");
			String resshowName = jsonObj.getString("resshowName");
			String resPath = jsonObj.getString("resPath");
			acFuncResource.setGuidFunc(guid);
			acFuncResource.setResType(resType);
			acFuncResource.setCompackName(compackName);
			acFuncResource.setResShowName(resshowName);
			acFuncResource.setResPath(resPath);*/

			applicationRService.updateAcFunc(acFunc,acFuncResource);
			AjaxUtils.ajaxJsonSuccessMessage(response,"");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("acFuncEdit exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("acFuncEdit exception : ", e);
		}
		return null;
	}
	
	/**
	 * acFuncResouceQuery功能对应资源查询
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/acFuncResouceQuery" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String acFuncResouceQuery(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("acFuncResouceQuery request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			AcFuncResource funcResouce = applicationRService.queryFuncResource(guid);
			AjaxUtils.ajaxJsonSuccessMessage(response, funcResouce);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("acFuncResouceQuery exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("acFuncResouceQuery exception : ", e);
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