package org.tis.tools.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.web.controller.BaseController;
import org.tis.tools.base.web.util.AjaxUtils;
import org.tis.tools.base.web.util.JSONUtils;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;
import org.tis.tools.service.api.biztrace.IBiztraceRService;
import org.tis.tools.service.api.biztrace.ParseProcessInfo;
import org.tis.tools.webapp.impl.dubboinfo.BiztraceManager;
import org.tis.tools.webapp.spi.dubboinfo.DubboServiceInfo;

/**
 * 业务日志Controller
 * 
 * GET	http://.../log/agents	查询日志代理服务列表
 * GET	http://.../log/list/{providerHost}	查询指定服务器的业务日志文件列表
 * POST	http://.../log/analyse/{providerHost}	分析业务日志
 * GET	http://.../log/analyse/process/{providerHost}	
 * 
 * @author megapro
 *
 */
@Controller
@RequestMapping("/log")
public class BizlogController extends BaseController {
	
	@Autowired
	IBiztraceRService biztraceRService ; 
	
	/**
	 * 查询当前所有biztraced日志代理服务提供者列表
	 * 界面端展示为列表菜单供选择
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/agents",method=RequestMethod.GET)
	public String listAgents(HttpServletRequest request,HttpServletResponse response){
		
		try {
			logger.info("list agents : " );
			
			List<DubboServiceInfo> agents = BiztraceManager.instance.getBiztraceProviderList()  ;
			
			//返回代理节点信息
			returnResponseData("agents", agents);
			
			String back = JSONArray.fromObject(responseMsg).toString() ; 
			logger.debug("agents："+back) ;
			AjaxUtils.ajaxJsonSuccessMessage(response, back );
			
			logger.info("list agents : ok" );
		}
		catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response);
			logger.error("list agents exception : " , e );
		}
		
		return null ; 
	}
	
	/**
	 * 查询当前所有的业务日志文件列表
	 * @param agentHost url中指定代理服务所在的主机服务名称 ip:port
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/list/{agentHost}",method=RequestMethod.GET)
	public String listLogFiles(@PathVariable String agentHost,HttpServletRequest request,HttpServletResponse response){
		try {
			logger.info("list logfile : " + agentHost);
			
			List<BiztraceFileInfo> logList = biztraceRService.listBiztraces(agentHost) ;
			
			returnResponseData("logFiles", logList);
			
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(responseMsg).toString());
			
			logger.info("list logfile : ok" );
		}
		catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response);
			logger.error("list logfile exception : " , e );
		}
		
		return null;		
	}
	
	/**
	 * 分析业务日志
	 * @param agentHost 日志代理服务
	 * @param logFiles 指定分析文件范围
	 * <br/>指定日志文件范围 {"type":"part","logs":["biztrace.log.1","biztrace.log.2",....]}
	 * <br/>指定全部日志文件 {"type":"all"}
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/analyse/{agentHost}",method=RequestMethod.POST)
	public String analyseLog(@PathVariable String agentHost,@RequestBody String logFiles,
			HttpServletRequest request,HttpServletResponse response){
		
		try {
			logger.info("analyse agentHost : " + agentHost);
			logger.info("analyse logFiles : " + logFiles);
			
			//解析文件范围
			JSONObject jsonLogFiles = JSONObject.fromObject(logFiles);
			String type = JSONUtils.getStr(jsonLogFiles, "type") ;
			if( StringUtils.isEmpty(type) ){
				AjaxUtils.ajaxJsonErrorMessage(response, "请指定日志文件范围！");
				return null ; 
			}
			
			List<BiztraceFileInfo> logList = biztraceRService.listBiztraces(agentHost) ;//取出当前所有日志文件
			
			if( StringUtils.equals(type, "part") ){
				JSONArray logsList = jsonLogFiles.getJSONArray("logs") ; 
				List<String> fixedLogFile = (List<String>) JSONArray.toArray(logsList) ; 
				//如果只指定了部分，泽剔除未指定的内容
				for( BiztraceFileInfo i : logList ){
					if( fixedLogFile.contains(i.getFileName()) ){
						continue ; 
					}else{
						logList.remove(i) ; 
					}
				}
			}
			
			biztraceRService.resolveAndAnalyseBiztraceFixed(logList);//解析指定的日志文件
			
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(responseMsg).toString());
			
			logger.info("analyse biztrace : ok" );
		}
		catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response);
			logger.error("analyse biztrace exception : " , e );
		}
		
		return null;
	}
	
	/**
	 * 查询分析进度,解析日志文件期间，前端循环调用本接口，实现解析进度条展示
	 * @param agentHost 日志代理服务
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/analyse/process/{agentHost}",method=RequestMethod.GET)
	public String analyseProcess(@PathVariable String agentHost,
			HttpServletRequest request,HttpServletResponse response){
		
		try {
			logger.info("analyse process : "+ agentHost);

			// TODO 调用对应日志代理服务，获取当前分析进度
			ParseProcessInfo process = biztraceRService.getResolveProcess();

			returnResponseData("process", process);// 返回进度信息

			AjaxUtils.ajaxJsonSuccessMessage(response,
					JSONArray.fromObject(responseMsg).toString());

			logger.info("analyse process : ok");
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response);
			logger.error("analyse process exception : ", e);
		}

		return null ; 
	}
}
