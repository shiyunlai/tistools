package org.tis.tools.webapp.controller.ac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcApp;
import org.tis.tools.model.po.ac.AcBhvDef;
import org.tis.tools.model.po.ac.AcBhvtypeDef;
import org.tis.tools.model.po.ac.AcFuncResource;
import org.tis.tools.model.po.ac.AcFuncgroup;
import org.tis.tools.model.po.ac.AcFunc;
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.model.vo.ac.AcAppVo;
import org.tis.tools.model.vo.ac.AcFuncVo;
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
	 * appAdd新增应用服务
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
			JSONObject jsonObj = JSONObject.parseObject(content);	
			String appCode = jsonObj.getString("appCode");
			String appName = jsonObj.getString("appName");
			String appType = jsonObj.getString("appType");
			String appDesc = jsonObj.getString("appDesc");
			String isOpen = jsonObj.getString("isopen");
			String openDateStr = jsonObj.getString("openDateStr");
			SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd");
			Date date = times.parse(openDateStr);
			String url = jsonObj.getString("url");
			String empMaintenance = jsonObj.getString("guidEmpMaintenance");
			String roleMaintenance = jsonObj.getString("guidRoleMaintenance");
			String ipAddr = jsonObj.getString("ipAddr");
			String ipPort = jsonObj.getString("ipPort");
			AcApp ac = new AcApp();
			ac.setAppCode(appCode);
			ac.setAppName(appName);
			ac.setAppType(appType);
			ac.setAppDesc(appDesc);
			ac.setIsopen(isOpen);
			ac.setGuidEmpMaintenance(empMaintenance);
			ac.setGuidRoleMaintenance(roleMaintenance);
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
			JSONObject jsonObj = JSONObject.parseObject(content);	
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
			JSONObject jsonObj = JSONObject.parseObject(content);
			String id = jsonObj.getString("id");
			AcApp acApp = applicationRService.queryAcApp(id);
			acApp.setAppCode(jsonObj.getString("appCode"));
			acApp.setAppName(jsonObj.getString("appName"));
			acApp.setAppType(jsonObj.getString("appType"));
			acApp.setAppDesc(jsonObj.getString("appDesc"));
			acApp.setIsopen(jsonObj.getString("isopen"));
			String openDateStr = jsonObj.getString("openDateStr");
			SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd");
			Date date = times.parse(openDateStr);
			acApp.setOpenDate(date);
			acApp.setUrl(jsonObj.getString("url"));
			acApp.setIpAddr(jsonObj.getString("ipAddr"));
			acApp.setGuidEmpMaintenance(jsonObj.getString("guidEmpMaintenance"));
			acApp.setGuidRoleMaintenance(jsonObj.getString("guidRoleMaintenance"));
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
	public String appQuery(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			if (logger.isInfoEnabled()) {
				logger.info("appQuery request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);
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
				List<AcFuncVo> funcList = applicationRService.queryAcFuncVo(id);
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
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
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
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
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
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
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
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcCode = jsonObj.getString("funcCode");
			String funcName = jsonObj.getString("funcName");
			String funcAction = jsonObj.getString("funcAction");
			String paraInfo = jsonObj.getString("paraInfo");
			String funcType = jsonObj.getString("funcType");
			String isCheck = jsonObj.getString("isCheck");
			String isMenu = jsonObj.getString("ismenu");
			String funcDesc = jsonObj.getString("funcDesc");
			String guidFuncgroup = jsonObj.getString("guidFuncgroup");
			//设置功能对应资源
			AcFuncResource acFuncResource = new AcFuncResource();
			String resType = jsonObj.getString("resType");
			String compackName = jsonObj.getString("compackName");
			String resshowName = jsonObj.getString("resShowName");
			String resPath = jsonObj.getString("resPath");
			acFuncResource.setResType(resType);
			acFuncResource.setCompackName(compackName);
			acFuncResource.setResShowName(resshowName);
			acFuncResource.setResPath(resPath);
		
			AcFunc acFunc = new AcFunc();
			acFunc.setFuncCode(funcCode);
			acFunc.setFuncName(funcName);
			acFunc.setFuncAction(funcAction);
			acFunc.setFuncDesc(funcDesc);
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
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
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
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			AcFunc acFunc = applicationRService.queryFunc(guid);
			String funcCode = jsonObj.getString("funcCode");
			String funcName = jsonObj.getString("funcName");
			String funcAction = jsonObj.getString("funcAction");
			String paraInfo = jsonObj.getString("paraInfo");
			String funcType = jsonObj.getString("funcType");
			//String isCheck = jsonObj.getString("isCheck");
			String isMenu = jsonObj.getString("ismenu");
			String funcDesc = jsonObj.getString("funcDesc");
						
			acFunc.setFuncCode(funcCode);
			acFunc.setFuncDesc(funcDesc);
			acFunc.setFuncName(funcName);
			acFunc.setFuncAction(funcAction);
			acFunc.setParaInfo(paraInfo);
			acFunc.setFuncType(funcType);
			//acFunc.setIscheck(isCheck);
			acFunc.setIsmenu(isMenu);
			
			applicationRService.updateAcFunc(acFunc);
			AjaxUtils.ajaxJsonSuccessMessage(response,"");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("acFuncEdit exception : ", e);
		}
		return null;
	}
	
	/**
	 * acFuncResource更新功能对应资源
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/acFuncResourceEdit" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String acFuncResourceEdit(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("acFuncEdit request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
	
			//设置功能对应资源
			AcFuncResource acFuncResource = new AcFuncResource();

			String guid = jsonObj.getString("id");		
			String resType = jsonObj.getString("resType");
			String compackName = jsonObj.getString("compackName");
			String resshowName = jsonObj.getString("resShowName");
			String resPath = jsonObj.getString("resPath");
			acFuncResource.setGuidFunc(guid);
			acFuncResource.setResType(resType);
			acFuncResource.setCompackName(compackName);
			acFuncResource.setResShowName(resshowName);
			acFuncResource.setResPath(resPath);

			applicationRService.updateAcFuncResource(acFuncResource);
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
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			AcFuncResource funcResouce = applicationRService.queryFuncResource(guid);
			AjaxUtils.ajaxJsonSuccessMessage(response, funcResouce);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("acFuncResouceQuery exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("acFuncResouceQuery exception : ", e);
		}
		return null;
	}
	
	
	/**
	 * queryAllFunc查询所有应用
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryAllFunc" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String queryAllFunc(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("queryAllFunc request : " + content);
			}
			List <AcFunc> acfunc= applicationRService.queryAllFunc();
			AjaxUtils.ajaxJsonSuccessMessage(response, acfunc);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("queryAllFunc exception : ", e);
		}
		return null;
	}
	
	/**
	 * importFunc导入应用
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/importFunc" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String importFunc(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("importFunc request : " + content);
			}
			
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			JSONArray jsonArray = jsonObj.getJSONArray("list");
			List list = JSONObject.parseArray(jsonArray.toJSONString(), String.class);
			applicationRService.importFunc(guid,list);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("importFunc exception : ", e);
		}
		return null;
	}
	
	/**
	 * functypeAdd新增行为类型
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/functypeAdd" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String functypeAdd(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("functypeAdd request : " + content);
			}
			
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String bhvtypeCode = jsonObj.getString("bhvtypeCode");
			String bhvtypeName = jsonObj.getString("bhvtypeName");
			AcBhvtypeDef acBhvtypeDef=new AcBhvtypeDef();
			acBhvtypeDef.setBhvtypeCode(bhvtypeCode);
			acBhvtypeDef.setBhvtypeName(bhvtypeName);
			
			applicationRService.functypeAdd(acBhvtypeDef);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("functypeAdd exception : ", e);
		}
		return null;
	}
	
	/**
	 * functypeDel删除行为类型
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/functypeDel" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String functypeDel(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("functypeDel request : " + content);
			}
			
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String guid = jsonObj.getString("id");			
			applicationRService.functypeDel(guid);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("functypeDel exception : ", e);
		}
		return null;
	}
	
	/**
	 * functypeEdit 修改行为类型
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/functypeEdit" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String functypeEdit(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("functypeEdit request : " + content);
			}
			
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String guid = jsonObj.getString("id");		
			String bhvtypeCode = jsonObj.getString("bhvtypeCode");	
			String bhvtypeName = jsonObj.getString("bhvtypeName");	
			AcBhvtypeDef acBhvtypeDef=new AcBhvtypeDef();
			acBhvtypeDef.setGuid(guid);
			acBhvtypeDef.setBhvtypeCode(bhvtypeCode);
			acBhvtypeDef.setBhvtypeName(bhvtypeName);
			applicationRService.functypeEdit(acBhvtypeDef);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("functypeEdit exception : ", e);
		}
		return null;
	}
	
	/**
	 * functypequery 查询行为类型
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/functypequery" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String functypequery(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("functypequery request : " + content);
			}
			List<AcBhvtypeDef> list = applicationRService.functypequery();
			AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("functypequery exception : ", e);
		}
		return null;
	}
	
	
	/**
	 * funactAdd 新增功能操作行为
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/funactAdd" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String funactAdd(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("funactAdd request : " + content);
			}
			
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String guidBehtype = jsonObj.getString("guid");
			String bhvCode = jsonObj.getString("bhvCode");
			String bhvName = jsonObj.getString("bhvName");
			AcBhvDef acBhvDef=new AcBhvDef();
			acBhvDef.setBhvCode(bhvCode);
			acBhvDef.setBhvName(bhvName);
			acBhvDef.setGuidBehtype(guidBehtype);
			applicationRService.funactAdd(acBhvDef);
			
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("funactAdd exception : ", e);
		}
		return null;
	}
	
	
	/**
	 * funactDel 删除功能操作行为
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/funactDel" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String funactDel(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("funactDel request : " + content);
			}
			
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String idstr = jsonObj.getJSONArray("ids").toJSONString();
			List<String> ids = JSONObject.parseArray(idstr,String.class);
			applicationRService.funactDel(ids);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("funactDel exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("funactDel exception : ", e);
		}
		return null;
	}
	
	/**
	 * funactEdit 修改功能操作行为
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/funactEdit" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String funactEdit(@RequestBody String content, HttpServletRequest request,
			HttpServletResponse response) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("funactEdit request : " + content);
			}
			
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String guid = jsonObj.getString("id");
			String bhvCode = jsonObj.getString("bhvCode");
			String bhvName = jsonObj.getString("bhvName");
			AcBhvDef acBhvDef=new AcBhvDef();
			acBhvDef.setBhvCode(bhvCode);
			acBhvDef.setBhvName(bhvName);
			acBhvDef.setGuid(guid);
			applicationRService.funactEdit(acBhvDef);
			
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("funactEdit exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("funactEdit exception : ", e);
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

	/**
	 * queryBhvtypeDefByFunc 根据功能的GUID查询行为类型定义
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryBhvtypeDefByFunc" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String queryBhvtypeDefByFunc(@RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("queryBhvtypeDefByFunc request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcGuid = jsonObj.getString("id");
			List<AcBhvtypeDef> list = applicationRService.queryBhvtypeDefByFunc(funcGuid);

			AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("queryBhvtypeDefByFunc exception : ", e);
		}catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("queryBhvtypeDefByFunc exception : ", e);
		}
		return null;

	}

	/**
	 * queryBhvDefByBhvType 根据行为类型的GUID查询所有的操作行为
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryBhvDefByBhvType" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String queryBhvDefByBhvType(@RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("queryBhvDefByBhvType request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String bhvtypeGuid = jsonObj.getString("id");
			List<AcBhvDef> list = applicationRService.queryBhvDefByBhvType(bhvtypeGuid);

			AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("queryBhvDefByBhvType exception : ", e);
		}
		return null;

	}
	
	/**
	 * addBhvtypeForFunc 功能添加行为类型
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addBhvtypeForFunc" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String addBhctypeForFunc(@RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("addBhvtypeForFunc request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcGuid = jsonObj.getString("id");
			List bhvDefGuids = JSONObject.parseArray(jsonObj.getJSONArray("bhvDefGuids").toJSONString(), String.class);
			applicationRService.addBhvtypeForFunc(funcGuid, bhvDefGuids);
			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("addBhvtypeForFunc exception : ", e);
		}
		return null;
	}

	/**
	 * addBhvDefForFunc 功能添加行为定义
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/addBhvDefForFunc" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String addBhcDefForFunc(@RequestBody String content, HttpServletRequest request,
							 HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("addBhvDefForFunc request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcGuid = jsonObj.getString("id");
			List bhvtypeGuids = JSONObject.parseArray(jsonObj.getJSONArray("typeGuidList").toJSONString(), String.class);
			applicationRService.addBhvDefForFunc(funcGuid, bhvtypeGuids);

			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("addBhvDefForFunc exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("addBhvDefForFunc exception : ", e);
		}
		return null;
	}




	/**
	 *  查询功能下某个行为类型的操作行为
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryBhvDefInTypeForFunc" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String queryBhcDefForFunc(@RequestBody String content, HttpServletRequest request,
									HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("queryBhvDefInTypeForFunc request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcGuid = jsonObj.getString("funcGuid"); // 功能GUID
			String bhvtypeGuid = jsonObj.getString("bhvtypeGuid"); //类型GUID

			List list = applicationRService.queryBhvDefInTypeForFunc(funcGuid, bhvtypeGuid);

			AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("queryBhvDefInTypeForFunc exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("queryBhvDefInTypeForFunc exception : ", e);
		}
		return null;

	}

	/**
	 *  查询功能下全部的操作行为
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/queryAllBhvDefForFunc" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String queryAllBhcDefForFunc(@RequestBody String content, HttpServletRequest request,
									HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("queryAllBhvDefForFunc request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcGuid = jsonObj.getString("funcGuid"); // 功能GUID

			List list = applicationRService.queryAllBhvDefForFunc(funcGuid);

			AjaxUtils.ajaxJsonSuccessMessage(response, list);//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("queryAllBhvDefForFunc exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("queryAllBhvDefForFunc exception : ", e);
		}
		return null;

	}
	
	
	/**
	 * 删除功能对应的行为类型
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delFuncBhvType" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String delFuncBhvType(@RequestBody String content, HttpServletRequest request,
										HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("delFuncBhcType request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcGuid = jsonObj.getString("funcGuid"); // 功能GUID
			String idstr = jsonObj.getJSONArray("bhvtypeGuids").toJSONString();
			List<String> bhvtypeGuids = JSONObject.parseArray(idstr, String.class); // 行为类型GUID

			applicationRService.delFuncBhvType(funcGuid, bhvtypeGuids);

			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("delFuncBhvType exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("delFuncBhvType exception : ", e);
		}
		return null;
	}

	/**
	 * 删除功能对应的行为定义
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/delFuncBhvDef" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public String delFuncBhvDef(@RequestBody String content, HttpServletRequest request,
										HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("delFuncBhvDef request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数
			String funcGuid = jsonObj.getString("funcGuid"); // 功能GUID
			String idstr = jsonObj.getJSONArray("bhvDefGuids").toJSONString();
			List<String> bhvDefGuids = JSONObject.parseArray(idstr, String.class); // 行为定义GUID

			applicationRService.delFuncBhvDef(funcGuid, bhvDefGuids);

			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("delFuncBhvDef exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("delFuncBhvDef exception : ", e);
		}
		return null;
	}
	
	/**
	 * 开启应用
	 * @param content
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="/enableApp" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public void enableApp(@RequestBody String content, HttpServletRequest request,
										HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("enableApp request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数

			String appGuid = jsonObj.getString("appGuid"); // 应用GUID
			Date openDate = jsonObj.getDate("openStr");//开通时间

			applicationRService.enableApp(appGuid, openDate);

			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("enableApp exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("enableApp exception : ", e);
		}
	}

	/**
	 * 关闭应用
	 * @param content
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="/disableApp" ,produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public void disableApp(@RequestBody String content, HttpServletRequest request,
										HttpServletResponse response) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("disableApp request : " + content);
			}
			JSONObject jsonObj = JSONObject.parseObject(content);	//传入的参数

			String appGuid = jsonObj.getString("appGuid"); // 应用GUID

			applicationRService.disableApp(appGuid);

			AjaxUtils.ajaxJsonSuccessMessage(response, "");//返回给前台的结
		} catch (ToolsRuntimeException e) {
			AjaxUtils.ajaxJsonErrorMessage(response,e.getCode(), e.getMessage());
			logger.error("disableApp exception : ", e);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response,"SYS_0001", e.getMessage());
			logger.error("disableApp exception : ", e);
		}
	}

}