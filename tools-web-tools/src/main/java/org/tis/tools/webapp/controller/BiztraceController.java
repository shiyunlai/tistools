/**
 * 
 */
package org.tis.tools.webapp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.tis.tools.base.web.controller.BaseController;
import org.tis.tools.base.web.util.AjaxUtils;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;
import org.tis.tools.service.api.biztrace.IBiztraceRService;
import org.tis.tools.webapp.impl.biztracemgr.BiztraceManager;
import org.tis.tools.webapp.spi.biztracemgr.BiztraceAgentInfo;

import net.sf.json.JSONArray;

/**
 * <pre>
 * 业务日志管理Controller
 * 
 * GET	http://.../biztrce/agents	查询日志代理服务列表
 * GET	http://.../biztrace/list/{providerHost}	查询指定服务器的业务日志文件列表
 * POST	http://.../analyse/{providerHost}	分析业务日志
 * GET	http://.../analyse/{providerHost}/process	
 * </pre>
 * @author megapro
 *
 */
@Controller
@RequestMapping("/biztrace")
public class BiztraceController extends BaseController{

	@Autowired
	IBiztraceRService biztraceRService ; 
	
	/**
	 * 查询当前所有biztraced日志代理服务提供者列表
	 * 界面端展示为列表菜单供选择
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list/agents",method=RequestMethod.GET)
	public String listAgents(HttpServletRequest request,HttpServletResponse response){
		
		try {
			logger.info("list agents : " );
			
			List<BiztraceAgentInfo> agents = BiztraceManager.instance.getBiztraceAgentList()  ;
			
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(agents).toString());
			
			logger.info("list agents : ok" );
		}
		catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("list agents exception : " , e );
		}
		
		return null ; 
	}
	
	/**
	 * 查询当前所有的业务日志文件列表
	 * @param providerHost url中指定日志嗲里服务所在的主机服务名称 ip:port
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list/{providerHost}",method=RequestMethod.GET)
	public String listLogFiles(@PathVariable String providerHost,HttpServletRequest request,HttpServletResponse response){
		try {
			logger.info("list logfile : " + providerHost);
			
			List<BiztraceFileInfo> logList = biztraceRService.listBiztraces(providerHost) ;
			
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(logList).toString());
			
			logger.info("list logfile : ok" );
		}
		catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("list logfile exception : " , e );
		}
		
		return null;		
	}
	
	/**
	 * 分析业务日志
	 * @param providerHost 日志代理服务
	 * @param logFiles 分析文件范围，为空，则指全部；POST传输
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/analyse/{providerHost}",method=RequestMethod.POST)
	public String analyseLog(@PathVariable String providerHost,@RequestBody String logFiles,
			HttpServletRequest request,HttpServletResponse response){
		try {
			logger.info("analyse biztrace : " + providerHost);
			
			//TODO 调用分析过程....
			List<BiztraceFileInfo> logList = biztraceRService.listBiztraces(providerHost) ;
			
			AjaxUtils.ajaxJsonSuccessMessage(response, JSONArray.fromObject(logList).toString());
			
			logger.info("analyse biztrace : ok" );
		}
		catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("analyse biztrace exception : " , e );
		}
		
		return null;
	}
	
	/**
	 * 查询分析进度
	 * @param providerHost 日志代理服务
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/analyse/{providerHost}/process",method=RequestMethod.GET)
	public String analyseProcess(@PathVariable String providerHost,
			HttpServletRequest request,HttpServletResponse response){
		
		//TODO 调用对应日志代理服务，获取当前分析进度
		
		return null ; 
	}
	
	
	//TODO 增加其他分析结果查询功能
	
}
