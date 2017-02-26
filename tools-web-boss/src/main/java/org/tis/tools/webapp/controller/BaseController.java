package org.tis.tools.webapp.controller;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;
import org.tis.tools.base.Page;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.service.base.SequenceService;
import org.tis.tools.webapp.retcode.RetCodeEnum;
import org.tis.tools.webapp.util.JSONPropertyStrategyWrapper;
import org.tis.tools.webapp.util.JsonDateProcessor;
import org.tis.tools.webapp.util.JsonFileProcessor;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertySetStrategy;

/**
 * Controller 的抽象类
 * 
 * @author megapro
 *
 */
abstract public class BaseController {
	
	protected final Logger       logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected TransactionTemplate transactionTemplate;

	@Autowired
	protected SequenceService sequenceBiz ;
	
	protected JsonConfig jsonConfig;
	
	public BaseController() {
		net.sf.json.util.JSONUtils.getMorpherRegistry().registerMorpher(
				new DateMorpher(new String[] { "yyyy-MM-dd HH:mm:ss" ,"yyyy-MM-dd"
				}));
		jsonConfig = new JsonConfig();
		//修复cycle错误
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateProcessor());
		jsonConfig.registerJsonValueProcessor(File.class,
				new JsonFileProcessor());
		jsonConfig.setPropertySetStrategy(new JSONPropertyStrategyWrapper(
				PropertySetStrategy.DEFAULT));
		createResponseCache();
	}
	
	/**
	 * <pre>
	 * 构造处理结果返回结构
	 * 结构示意：{"result":{"retCode":"","retMsg":"",....}}
	 * </pre>
	 * @return
	 */
	private void createResponseCache() {
		Map<String, Object> resp = getResponseMessage() ;
		//默认成功
		returnRetCode(resp,RetCodeEnum._0000_SUCC) ; 
	}

	/**
	 * 返回错误码
	 * @param result
	 * @param retCode
	 */
	protected void returnRetCode(RetCodeEnum retCode){
		returnRetCode(getResponseMessage(),retCode) ; 
	}
	
	/**
	 * 返回处理响应数据
	 * @param key
	 * @param value
	 */
	protected void returnResponseData(String key, Object value){
		getResponseMessage().put(key, value);
	}
	
	private void returnRetCode(Map<String, Object> resp, RetCodeEnum retCode){
		resp.put("retCode", retCode.retCode);
		resp.put("retMsg" , retCode.retMsg);
	}
	
	/**
	 * 要求子类构造自己的响应数据
	 * @return
	 */
	public abstract Map<String, Object> getResponseMessage() ;

	protected void initWanNengChaXun(JSONObject jsonObj, WhereCondition wc) {
		// 查询项
		JSONObject jsonObjsearchItems = jsonObj.getJSONObject("searchItems");
		Set set = jsonObjsearchItems.keySet();
		for (Object o : set) {
			String key = o.toString();
			String value = jsonObjsearchItems.getString(key);
			if (StringUtils.endsWith(key, "_eq")) {
				wc.andEquals(key.substring(0, key.length() - 3), value);
			} else if (StringUtils.endsWith(key, "_lk")) {
				wc.andFullLike(key.substring(0, key.length() - 3), value);
			} else if (StringUtils.endsWith(key, "_gt")) {
				wc.andGreaterThan(key.substring(0, key.length() - 3), value);
			} else if (StringUtils.endsWith(key, "_lt")) {
				wc.andLessThan(key.substring(0, key.length() - 3), value);
			} else if (StringUtils.endsWith(key, "_ge")) {
				wc.andGreaterEquals(key.substring(0, key.length() - 3), value);
			} else if (StringUtils.endsWith(key, "_le")) {
				wc.andLessEquals(key.substring(0, key.length() - 3), value);
			} else if (StringUtils.endsWith(key, "_start")) {
				if (StringUtils.isNotEmpty(value)
						&& !StringUtils.equals(value, "null")) {
					wc.andGreaterEquals(key.substring(0, key.length() - 6),
							value);
				}
			} else if (StringUtils.endsWith(key, "_end")
					&& !StringUtils.equals(value, "null")) {
				if (StringUtils.isNotEmpty(value)) {
					wc.andLessEquals(key.substring(0, key.length() - 4), value);
				}
			} else if (StringUtils.endsWith(key, "_in")) {
				if (StringUtils.isNotEmpty(value)) {
					wc.andIn(key.substring(0, key.length() - 3),
							Arrays.asList(value.split(",")));
				}
			} else if (StringUtils.endsWith(key, "_nin")) {
				if (StringUtils.isNotEmpty(value)) {
					wc.andNotIn(key.substring(0, key.length() - 4),
							Arrays.asList(value.split(",")));
				}
			} else if (StringUtils.endsWith(key, "_neq")) {
				wc.andNotEquals(key.substring(0, key.length() - 4), value);
			}else{
				if(StringUtils.isNotEmpty(key)&&StringUtils.isNotEmpty(value)){
					wc.andEquals(key, value);
				}
			}
		}
	}

	protected String getOrderGuize(String orderGuize) {
		if (orderGuize == null) {
			return orderGuize;
		}
		if (orderGuize.endsWith("asc")) {
			return orderGuize.substring(0, orderGuize.length() - 3) + " asc";
		} else if (orderGuize.endsWith("desc")) {
			return orderGuize.substring(0, orderGuize.length() - 4) + " desc";
		}
		return null;
	}

	// 获取权限查询条件
	protected String getGlobalFilter(String content,
			HttpServletRequest request, HttpServletResponse response) {
		String globalFilter = null;
		return globalFilter;
	}

	protected Page getPage(JSONObject jsonObj) {
		Page page = (Page) JSONObject.toBean(jsonObj.getJSONObject("page"),
				Page.class);
		if (page == null) {
			page = new Page();
		}
		return page;
	}
}
