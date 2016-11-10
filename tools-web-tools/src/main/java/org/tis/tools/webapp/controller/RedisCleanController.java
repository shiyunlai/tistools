package org.tis.tools.webapp.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.tis.tools.web.base.controller.BaseController;
import org.tis.tools.web.base.util.AjaxUtils;

import bos.tis.biztrace.handler.BizLogDateHandler;
import bos.tis.biztrace.handler.RedisHandler;

@Controller
@RequestMapping("/RedisCleanController")
public class RedisCleanController extends BaseController{
	@RequestMapping("/getRedisSpaceUsage")
	public String getRedisSpaceUsage(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		try {
			logger.info("RedisCleanController getRedisSpaceUsage request : received!" );
			
			String redisSpace = RedisHandler.instance.getRedisSpaceUsage();
			
			logger.info("RedisCleanController getRedisSpaceUsage response : " + redisSpace);
			AjaxUtils.ajaxJson(response, JSONObject.fromObject("{redisSpace:"+redisSpace+"}", jsonConfig).toString());
			
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("RedisCleanController getRedisSpaceUsage exception : " ,e);
		}
		return null;		
	}
	
	@RequestMapping("/getResolveredLogDate")
	public String getResolveredLogDate(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		try {
			logger.info("RedisCleanController getResolveredLogDate request : received!" );
			
			Set<String> resolvedDates = BizLogDateHandler.instance.getResolvedBizLogDate();
			
			logger.info("RedisCleanController getResolveredLogDate response : " + resolvedDates);
			AjaxUtils.ajaxJson(response, JSONArray.fromObject(resolvedDates, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("RedisCleanController getResolveredLogDate exception : " ,e);
		}
		return null;		
	}
	
	@RequestMapping("/cleanRedisSpace")
	public String cleanRedisSpace(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		try {
			logger.info("RedisCleanController cleanRedisSpace request :"+content );
			
			JSONArray jsonArry = JSONArray.fromObject(content);			
			Object[] dateArry = jsonArry.toArray();
			
			for(int i=0;i<dateArry.length;i++){
				RedisHandler.instance.cleanRedisSpace(dateArry[i].toString());
			}
			
			AjaxUtils.ajaxJsonSuccessMessage(response, "success");
			logger.info("RedisCleanController cleanRedisSpace response : success");
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("RedisCleanController cleanRedisSpace exception : " ,e);
		}
		return null;		
	}
}
