/**
 * AjaxUtils.java
 * Created at 2013-4-14
 * Created by su.zhang
 * Copyright (C) 2013 su.zhang, All rights reserved.
 */
package org.tis.tools.webapp.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * <p>ClassName: AjaxUtils</p>
 * <p>Description: ajax工具类</p>
 * <p>Author: su.zhang</p>
 * <p>Date: 2013-4-14</p>
 */
public class AjaxUtils {
    /**
     * <p>
     * Field STATUS: 状态
     * </p>
     */
    public static final String STATUS = "status";
    /**
     * <p>
     * Field WARN: 警告
     * </p>
     */
    public static final String WARN = "warning";
    /**
     * <p>
     * Field SUCCESS: 成功
     * </p>
     */
    public static final String SUCCESS = "success";
    
    /**
     * <p>
     * Field SUCCESSCODE: 成功返回码
     * </p>
     */
    public static final String SUCCESSCODE = "0000";
    
    /**
     * <p>
     * Field 返回码: 
     * </p>
     */
    public static final String RETCODE = "retCode";
    
    
    /**
     * <p>
     * Field 返回码: 
     * </p>
     */
    public static final String RETMESSAGE = "retMessage";
    
    /**
     * <p>
     * Field 返回码: 
     * </p>
     */
    public static final String SUCCCODE = "0000";
    
    /**
     * <p>
     * Field ERROR: 错误
     * </p>
     */
    public static final String ERROR = "error";

    /**
     * <p>
     * Field FAILED: 失败
     * </p>
     */
    public static final String FAILED = "failed";

    /**
     * <p>
     * Field UNAUTH: 未验证
     * </p>
     */
    public static final String UNAUTH = "unAuth";
    /**
     * <p>
     * Field FORBID: 拒绝
     * </p>
     */
    public static final String FORBID = "forbid";

    /**
     * <p>
     * Field KEEP_OPEN: 成功并保持window打开状态
     * </p>
     */
    public static final String KEEP_OPEN = "keepOpen";
    /**
     * <p>
     * Field MESSAGE: 消息
     * </p>
     */
    public static final String MESSAGE = "message";
    /**
     * <p>
     * Field EXTRA_MESSAGE: 额外信息（一般在异常时使用）
     * </p>
     */
    public static final String EXTRA_MESSAGE = "extraMessage";
    /**
     * <p>
     * Field ERROR_MESSAGE: 处理异常提示信息
     * </p>
     */
    public static final String ERROR_MESSAGE = "处理时发生异常";
    /**
     * <p>
     * Field CONTENT: 内容
     * </p>
     */
    public static final String CONTENT = "content";
    /**
     * <p>Field logger: 日志对象</p>
     */
    protected static Logger logger = Logger.getLogger(AjaxUtils.class);
    /**
     * <p>
     * Description: AJAX输出，返回null
     * </p>
     *
     * @param response HttpServletResponse对象
     * @param content 内容
     * @param type contentType
     * @return null
     */
    public static String ajax(HttpServletResponse response, String content, String type) {
        try {
//            response.reset();
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
//            String data=response.getHeader("Pragma");
//            System.out.println("data:::::::::::"+data);
//            if(StringUtils.isNotEmpty(data)&&data.length()==8){
//            	return null;
//            }
//            threadLocal.set("tag"); 
//            String datetag=response.getHeader("datetag");
//            System.out.println("datetag:================"+datetag);
//            if(StringUtils.isNotEmpty(datetag)){
////            	return null;
//            }
            response.setHeader("Pragma", "No-cache");
            PrintWriter writer = response.getWriter();
            response.getWriter().write(content);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            logger.error("AJAX响应错误", e);
        }
        return null;
    }

    /**
     * <p>Description: AJAX输出文本，返回null</p>
     * @param response HttpServletResponse对象
     * @param text 文本内容
     * @return null
     */
    public static String ajaxText(HttpServletResponse response, String text) {
        return ajax(response, text, "text/plain");
    }

    /**
     * <p>Description: AJAX输出HTML，返回null</p>
     * @param response HttpServletResponse对象
     * @param html html内容
     * @return null
     */
    public static String ajaxHtml(HttpServletResponse response, String html) {
        return ajax(response, html, "text/html");
    }

    /**
     * <p>Description: AJAX输出XML，返回null</p>
     * @param response HttpServletResponse对象
     * @param xml xml内容
     * @return null
     */
    public static String ajaxXml(HttpServletResponse response, String xml) {
        return ajax(response, xml, "text/xml");
    }

    /**
     * <p>Description: 根据字符串输出JSON，返回null</p>
     * @param response HttpServletResponse对象
     * @param jsonString json串
     * @return null
     */
    public static String ajaxJson(HttpServletResponse response, String jsonString) {
        return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 根据Map输出JSON，返回null</p>
     * @param response HttpServletResponse对象
     * @param jsonMap json map对象
     * @return null
     */
    public static String ajaxJson(HttpServletResponse response, Map<String, String> jsonMap) {
        String jsonObject = JSON.toJSONString(jsonMap);
        return ajax(response, jsonObject, "text/html");
    }

    /**
     * <p>Description: 输出JSON警告消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param message 信息字符串
     * @return null
     */
    public static String ajaxJsonWarnMessage(HttpServletResponse response, String message) {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put(STATUS, WARN);
        jsonMap.put(MESSAGE, message);
        String jsonString = JSON.toJSONString(jsonMap);
        return ajax(response, jsonString, "text/html");
    }



    /**
     * <p>Description: 输出JSON成功消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param message 信息字符串
     * @return null
     */
    public static String ajaxJsonSuccessMessageWithParam(HttpServletResponse response, String message,Map<String,String> param) {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put(SUCCESS, "true");
        jsonMap.put(STATUS, SUCCESS);
        jsonMap.put(MESSAGE, message);
        for(String s:param.keySet()){
            jsonMap.put(s, param.get(s));
        }
        String jsonString = JSON.toJSONString(jsonMap);
        return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 输出JSON成功并保持window打开状态消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param message 信息字符串
     * @return null
     */
    public static String ajaxJsonKeepOpenMessage(HttpServletResponse response, String message) {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put(SUCCESS, "true");
        jsonMap.put(STATUS, KEEP_OPEN);
        jsonMap.put(MESSAGE, message);
        String jsonString = JSON.toJSONString(jsonMap);
        return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 输出JSON错误消息，返回null</p>
     * @param response HttpServletResponse对象
     * @return null
     */
    public static String ajaxJsonErrorMessage(HttpServletResponse response) {
        Map<String, String> jsonMap = new HashMap<String, String>();
        jsonMap.put(STATUS, ERROR);
        jsonMap.put(MESSAGE, ERROR_MESSAGE);
        String jsonString = JSON.toJSONString(jsonMap);
        return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 输出JSON错误消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param extraMessage 信息字符串
     * @return null
     */
    public static String ajaxJsonErrorMessage(HttpServletResponse response, String extraMessage) {
    	Map<String, String> jsonMap = new HashMap<String, String>();
    	jsonMap.put(STATUS, ERROR);
    	jsonMap.put(MESSAGE, extraMessage);
    	jsonMap.put(EXTRA_MESSAGE, extraMessage);
        String jsonString = JSON.toJSONString(jsonMap);
    	return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 设置页面不缓存</p>
     * @param response HttpServletResponse对象
     */
    public static void setResponseNoCache(HttpServletResponse response) {
        response.setHeader("progma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
    }

    /**
     * <p>Description: 输出JSON成功消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param args 信息字符串
     * @return null
     */
    public static String ajaxJsonSuccessMessage(HttpServletResponse response, Object... args) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put(RETCODE, SUCCESSCODE);
        jsonMap.put(STATUS, SUCCESS);
        jsonMap.put(RETMESSAGE, args[0]);
        String jsonString = JSON.toJSONString(jsonMap,SerializerFeature.WriteNullListAsEmpty);
        return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 输出格式化日期的JSON成功消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param retMessage 信息字符串
     * @return null
     */
    public static String ajaxJsonSuccessMessageWithDateFormat(HttpServletResponse response, Object retMessage, String format) {
        Map<String, Object> jsonMap = new HashMap<String, Object>();
        jsonMap.put(RETCODE, SUCCESSCODE);
        jsonMap.put(STATUS, SUCCESS);
        jsonMap.put(RETMESSAGE, retMessage);
        String jsonString = JSON.toJSONStringWithDateFormat(jsonMap, format);
        return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 输出JSON错误消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param args 信息字符串
     * @return null
     */
    public static String ajaxJsonErrorMessage(HttpServletResponse response, String code,Object... args) {
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put(RETCODE, code);
    	jsonMap.put(STATUS, ERROR);
    	jsonMap.put(RETMESSAGE, args[0]);
        String jsonString = JSON.toJSONString(jsonMap);
//        JSON.toJSONStringWithDateFormat(resultMap, "yyyy-MM-dd HH:mm:ss.SSS");
    	return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 输出JSON错误消息，返回null</p>
     * @param response HttpServletResponse对象
     * @param retMessage 信息字符串
     * @return null
     */
    public static String ajaxJsonFailMessage(HttpServletResponse response, String code, String retMessage) {
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put(RETCODE, code);
    	jsonMap.put(STATUS, FAILED);
    	jsonMap.put(RETMESSAGE, retMessage);
        String jsonString = JSON.toJSONString(jsonMap);
    	return ajax(response, jsonString, "text/html");
    }

    /**
     * <p>Description: 登录失效，输出JSON错误消息，返回null</p>
     * @param response HttpServletResponse对象
     * @return null
     */
    public static String ajaxJsonAuthMessage(HttpServletResponse response) {
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put(RETCODE, "AUTH-401");
    	jsonMap.put(STATUS, UNAUTH);
    	jsonMap.put(RETMESSAGE, "会话失效，请重新登录！");
        String jsonString = JSON.toJSONString(jsonMap);
    	return ajax(response, jsonString, "application/json");
    }
    /**
     * <p>Description: 权限不足，输出JSON错误消息，返回null</p>
     * @param response HttpServletResponse对象
     * @return null
     */
    public static String ajaxJsonForbidMessage(HttpServletResponse response) {
    	Map<String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put(RETCODE, "AUTH-403");
    	jsonMap.put(STATUS, FORBID);
    	jsonMap.put(RETMESSAGE, "没有当前功能或行为的权限！");
        String jsonString = JSON.toJSONString(jsonMap);
    	return ajax(response, jsonString, "application/json");
    }
}
