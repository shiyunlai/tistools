package com.bos.tis.tools.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author  zhangsu
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

	
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
		for(String s:bypassList){
			if(StringUtils.startsWith(pathInfo,s)){
				return true;
			}
		}
		return false;
	}
}
