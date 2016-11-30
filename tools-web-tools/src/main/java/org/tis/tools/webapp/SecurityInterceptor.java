package org.tis.tools.webapp;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author  zhangsu
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	protected final Logger       logger = LoggerFactory.getLogger(this.getClass());
	
	private static List<String> bypassList = new ArrayList<String>();
	static{
//		业务 
		bypassList.add("/anios");
		
	}
	
	public boolean preHandle(HttpServletRequest request,  
			HttpServletResponse response,Object handler)throws Exception{
		String pathInfo = request.getPathInfo();

		String version = request.getHeader("version");
		String cancel = request.getHeader("cancel");

		return true;  
	}
	
	//例外的访问路径。登入、登出、短信、注册用户、刷新参数
	private boolean bypass(String pathInfo){
		logger.info("请求地址： " + pathInfo);
		for(String s:bypassList){
			if(StringUtils.startsWith(pathInfo,s)){
				return true;
			}
		}
		
		logger.warn("不允许访问请求地址：" + pathInfo);
		return false;
	}
}
