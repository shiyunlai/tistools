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
import org.tis.tools.model.po.om.OmOrg;
import org.tis.tools.rservice.ac.basic.IAcAppRService;
import org.tis.tools.rservice.ac.capable.IApplicationRService;
import org.tis.tools.webapp.controller.BaseController;
import org.tis.tools.webapp.util.AjaxUtils;

@Controller
@RequestMapping("/AcAppController")
public class AcAppController extends BaseController {
	@Autowired
	IApplicationRService applicationRService;
	@Autowired
	IAcAppRService acAppRService;
	
	/**
	 * appAdd新增应用
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
			
			JSONObject jsonObj1 = JSONObject.fromObject(content);	
			String jsonItem = jsonObj1.getString("item");
			JSONObject jsonObj = JSONObject.fromObject(jsonItem);
			String appCode = jsonObj.getString("appCode");
			String appName = jsonObj.getString("appName");
			String appType = jsonObj.getString("appType");
			String appDesc = jsonObj.getString("appDesc");
			String isOpen = jsonObj.getString("isOpen");
			String openDate = jsonObj.getString("openDate");
			SimpleDateFormat times = new SimpleDateFormat("yyyy-MM-dd");
			Date A = times.parse(openDate);
			String url = jsonObj.getString("url");
			String ipAddr = jsonObj.getString("ipAddr");
			String ipPort = jsonObj.getString("ipPort");
			/*System.out.print(jsonObj);
			System.out.print(A);*/
			AcApp acApp = applicationRService.createAcApp(appCode, appName, appType, appDesc, isOpen, A, url, ipAddr,ipPort);//把参数全部填写上
			result.put("acApp", acApp);//返回给前台的数据
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());
			
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "新增应用失败！");
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
		
//			JSONObject jsonObj = JSONObject.fromObject(content);
//			WhereCondition wc = new  WhereCondition();
//			String appCode = jsonObj.getString("appCode");
//			if(appCode!=null)
//				wc.andEquals("app_code", appCode);
//			String appName = jsonObj.getString("appName");
//			if(appName!=null)
//				wc.andEquals("app_name", appName);//传入sql查询 条件		
//			String GUID = jsonObj.getString("GUID");
		
//				wc.andEquals("GUID", content);
//			acAppRService.deleteByCondition(wc);//调用删除方法
			
			acAppRService.delete(content);
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());
			
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "删除应用失败！");
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
				logger.info("appAdd request : " + content);
			}
		
			JSONObject jsonObj = JSONObject.fromObject(content);
			AcApp acapp = new AcApp();
			acapp.setGuid(jsonObj.getString("ID"));
			acapp.setAppName(jsonObj.getString("appName"));
			acapp.setAppName(jsonObj.getString("appCode"));//每个字段都写，要知道修改那个	
			acAppRService.update(acapp);
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(result).toString());
			
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "删除应用失败！");
			logger.error("appDel exception : ", e);
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
				logger.info("appAdd request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);
			String id = jsonObj.getString("id");
			String guid = jsonObj.getString("guid");
			List<AcApp> acApp = new ArrayList<AcApp>();
			//通过id判断需要加载的节点
			if("#".equals(id)){
				//调用远程服务,#:根
				AcApp ac = new AcApp();
				ac.setAppName("应用功能管理");
				ac.setAppCode("TX1001");
				acApp.add(ac);
			}else if("TX1001".equals(id)){
				WhereCondition wc = new  WhereCondition();//定义wc封装方法，里面定义条件
				acApp = acAppRService.query(wc);
			}
			else{
				WhereCondition wc = new  WhereCondition();//定义wc封装方法，里面定义条件
				wc.andEquals("guid",guid);
				acApp = acAppRService.query(wc);
			}
			
			AjaxUtils.ajaxJson(response, net.sf.json.JSONArray.fromObject(acApp).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "查询应用失败！");
			logger.error("appsearch exception : ", e);
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