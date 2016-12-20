/**
 * 
 */
package org.tis.tools.base.web.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * <p>Description: File文件转化为json字符串的处理</p>
 * <p>Author: meagecho</p>
 * <p>Date: 2016年12月20日</p>
 */
public class JsonFileProcessor implements JsonValueProcessor{

	/**
	 * 返回List<String>形式的文件名称
	 */
	@Override
	public Object processArrayValue(Object arg0, JsonConfig arg1) {
		File[] files = (File[]) arg0 ; 
		List<String> fileNames = new ArrayList<String>() ; 
		for( File f : files ){
			fileNames.add(f.getName()) ;
		}
		return fileNames;
	}

	/**
	 * 返回文件名称 
	 */
	@Override
	public Object processObjectValue(String key, Object value, JsonConfig conf) {
		if( value instanceof File ){
			return ( (File)value).getName() ;
		}
		return "";
	}

}
