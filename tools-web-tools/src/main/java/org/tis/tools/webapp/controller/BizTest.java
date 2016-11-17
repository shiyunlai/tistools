package org.tis.tools.webapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.tis.tools.base.web.controller.BaseController;
import org.tis.tools.base.web.util.AjaxUtils;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;
import org.tis.tools.service.api.biztrace.IBiztraceRService;
@Controller
@RequestMapping("/testController")
public class BizTest extends BaseController {
	
	@RequestMapping("/test")
	public String test(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			if(logger.isInfoEnabled()){
				logger.info("testController test request : " + content);
			}
			JSONObject jsonObj = JSONObject.fromObject(content);
			String trans_serial = jsonObj.getString("trans_serial");
			System.out.println("trans_serial:"+trans_serial);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("testController test exception : " ,e);
		}
		return null;
	}
	
	@Autowired
	IBiztraceRService biztraceRService ;

	@ResponseBody
	@RequestMapping(value="/biztrace/{providerHost}/list",method=RequestMethod.GET)
	public String getBranchCode(HttpServletRequest request, @PathVariable String providerHost,
			HttpServletResponse response) {
		
		System.out.println("调用<"+providerHost+">biztrace服务，列出日志文件");
		List<BiztraceFileInfo> logFileList = biztraceRService.listBiztraces(providerHost) ;
		for(BiztraceFileInfo f : logFileList ){
			System.out.println( f );
		}
		
		return JSONUtils.valueToString(logFileList) ;
	}
}
