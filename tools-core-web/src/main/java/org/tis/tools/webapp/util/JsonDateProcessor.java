/**
 * JsonDateProcessor.java
 * Created at 2013年11月6日
 * Created by Jackie
 * Copyright (C) 2013 lanmosoft.com, All rights reserved.
 */
package org.tis.tools.webapp.util;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>ClassName: JsonDateProcessor</p>
 * <p>Description: json日期（默认格式：yyyy-MM-dd）转换器,将java.util.Date类型按照指定格式转换</p>
 * <p>Author: Jackie</p>
 * <p>Date: 2013年11月6日</p>
 */
public class JsonDateProcessor implements JsonValueProcessor {
    
    private String format;
    
    public JsonDateProcessor() {
        this.format = "yyyy-MM-dd HH:mm:ss";
    }
    
    public JsonDateProcessor(String formate) {
        this.format = formate;
    }

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

    /* (non-Javadoc)
     * <p>Title: processArrayValue</p>
     * <p>Description: </p>
     * @param arg0
     * @param arg1
     * @return
     * @see net.sf.json.processors.JsonValueProcessor#processArrayValue(java.lang.Object, net.sf.json.JsonConfig)
     */
	@Override
	public Object processArrayValue(Object value, JsonConfig config) {
		String[] obj = {};
		if (value instanceof Date[]) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			Date[] dates = (Date[]) value;
			obj = new String[dates.length];
			for (int i = 0; i < dates.length; i++) {
				obj[i] = sf.format(dates[i]);
			}
		}
		return obj;
	}

    /* (non-Javadoc)
     * <p>Title: processObjectValue</p>
     * <p>Description: </p>
     * @param arg0
     * @param arg1
     * @param arg2
     * @return
     * @see net.sf.json.processors.JsonValueProcessor#processObjectValue(java.lang.String, java.lang.Object, net.sf.json.JsonConfig)
     */
    @Override
    public Object processObjectValue(String key, Object value, JsonConfig config) {
        if (value instanceof Date) {
           String a=  new SimpleDateFormat(format).format((Date)value);
           if(a.endsWith("00:00:00")){
        	   return a.split(" ")[0];
           }
           return a;
        }
        return null;
    }

}
