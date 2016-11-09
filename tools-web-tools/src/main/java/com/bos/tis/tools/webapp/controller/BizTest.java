package com.bos.tis.tools.webapp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



import com.bos.tis.tools.util.AjaxUtils;
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
}
