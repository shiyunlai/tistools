/**
 * 
 */
package org.tis.tools.common.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 对字符串进行格式化的工具
 * 
 * 如：json字符串 FormattingUtil.formatJsonStr( {"A":"a1","B":"b1","C":{"C1":"123","C2":"456"}} ) 将输出
 * {
 * 	"A":"a1",
 * 	"B":"b1",
 * 	"C":{
 * 		"C1":"123",
 * 		"C2":"456"
 * 	}
 * }
 * 
 * </pre>
 * @author megapro
 *
 */
public class FormattingUtil {

	private static final Map<Class, Class> SimpleTypeMap = new HashMap<Class, Class>(8);
	
	static {
		SimpleTypeMap.put(Boolean.class, boolean.class);
		SimpleTypeMap.put(Byte.class, byte.class);
		SimpleTypeMap.put(Character.class, char.class);
		SimpleTypeMap.put(Double.class, double.class);
		SimpleTypeMap.put(Float.class, float.class);
		SimpleTypeMap.put(Integer.class, int.class);
		SimpleTypeMap.put(Long.class, long.class);
		SimpleTypeMap.put(Short.class, short.class);
		
		SimpleTypeMap.put(String.class, String.class);
		SimpleTypeMap.put(Date.class, Date.class);
		
		SimpleTypeMap.put(BigInteger.class, Long.class);
		SimpleTypeMap.put(BigDecimal.class, double.class);
	}
	
	private FormattingUtil(){
		
	}
	
	private static class FormattingUtilHolder{
		public static final FormattingUtil instance = new FormattingUtil() ; 
	}
	
	/**
	 * 取工具实例
	 * @return
	 */
	public static FormattingUtil instance(){
		return FormattingUtilHolder.instance ; 
	}
	
	/**
	 * 将对象格式化为json样式的字符串
	 * @param obj
	 * @return
	 */
	public String toJsonString(Object obj) {
		return obj2json(obj);
	}	
	
	/**
	 * <pre>
	 * 
	 * 将json字符串进行展示结构化处理
	 * 
	 * 如：
	 * 
	 * FormattingUtil.formatJsonStr( {"A":"a1","B":"b1","C":{"C1":"123","C2":"456"}} ) 
	 * 
	 * 将输出
	 * 
	 * {
	 * 	"A":"a1",
	 * 	"B":"b1",
	 * 	"C":{
	 * 		"C1":"123",
	 * 		"C2":"456"
	 * 	}
	 * }
	 * 
	 * </pre>
	 * 
	 * @param jsonString
	 * @return
	 */
	public String formatJsonString(String jsonString) {
		return formatJson(jsonString, "    ");
	}
	
	
	private static String obj2json(Object obj) {
		StringBuilder json = new StringBuilder();
		if (obj == null) {
			json.append("\"\"");
		} else if (obj instanceof Character || Character.TYPE.isAssignableFrom(obj.getClass())) {
			json.append("'").append(string2json(String.valueOf(obj))).append("'");
		} else if (obj.getClass().isPrimitive() || obj instanceof Boolean || obj instanceof Byte || obj instanceof Short || obj instanceof Integer
				|| obj instanceof Long || obj instanceof Float || obj instanceof Double || obj instanceof BigInteger || obj instanceof BigDecimal) {
			json.append(string2json(String.valueOf(obj)));
		} else if (obj instanceof String) {
			json.append("\"").append(string2json((String) obj)).append("\"");
		} else if (obj instanceof Date) {// 默认时间格式
			json.append("\"").append(data2String("yyyy-MM-dd HH:mm:ss", (Date) obj)).append("\"");
		} else if (obj.getClass().isArray()) {
			json.append(array2json((Object[]) obj));
		} else if (obj instanceof Collection) {
			json.append(collection2json((Collection<?>) obj));
		} else if (obj instanceof Map) {
			json.append(map2json((Map<?, ?>) obj));
		}
		else {
			json.append(bean2json(obj));
		}
		return json.toString();
	}

	// 把Date类型转化为字符串
	private static String data2String(String pattern, Date date) {
		SimpleDateFormat dateFormator = new SimpleDateFormat(pattern);
		return dateFormator.format(date);
	}

	// 把JavaBean转化为JSON字符串
	private static String bean2json(Object bean) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		PropertyDescriptor[] props = null;
		try {
			props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
		} catch (IntrospectionException e) {
		}
		if (props != null) {
			ArrayList<PropertyDescriptor> propSimpleList = new ArrayList<PropertyDescriptor>();
			ArrayList<PropertyDescriptor> propUnknowList = new ArrayList<PropertyDescriptor>();
			ArrayList<PropertyDescriptor> propComplexList = new ArrayList<PropertyDescriptor>();
			for (PropertyDescriptor property : props) {
				if (property.getPropertyType().isPrimitive()) {
					propSimpleList.add(property);
					continue;
				}
				
				if (SimpleTypeMap.containsKey(property.getPropertyType())) {
					propSimpleList.add(property);
					continue;
				}
				
				if (property.getPropertyType().isArray()) {
					propComplexList.add(property);
					continue;
				}
				if (Collection.class.isAssignableFrom(property.getPropertyType())) {
					propComplexList.add(property);
					continue;
				}
				if (Map.class.isAssignableFrom(property.getPropertyType())) {
					propComplexList.add(property);
					continue;
				}
				propUnknowList.add(property);
			}
			for (PropertyDescriptor property : propSimpleList) {
				property2json(bean, property, json);
			}
			for (PropertyDescriptor property : propUnknowList) {
				property2json(bean, property, json);
			}
			for (PropertyDescriptor property : propComplexList) {
				property2json(bean, property, json);
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}
	
	private static void property2json(Object bean, PropertyDescriptor property, StringBuilder json) {
		try {
			String name = obj2json(property.getName());
			String value = "";
			if (property.getReadMethod() != null) {
				value = obj2json(property.getReadMethod().invoke(bean));
			}
			json.append(name);
			json.append(":");
			json.append(value);
			json.append(",");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 把数组转化为JSON字符串
	private static String array2json(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object obj : array) {
				json.append(obj2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	// 把Collection转化为JSON字符串
	private static String collection2json(Collection<?> collection) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (collection != null && collection.size() > 0) {
			for (Object obj : collection) {
				json.append(obj2json(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	// 把Map转化为JSON字符串
	private static String map2json(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		json.append("{");
		if (map != null && map.size() > 0) {
			for (Object key : map.keySet()) {
				json.append(obj2json(key));
				json.append(":");
				json.append(obj2json(map.get(key)));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, '}');
		} else {
			json.append("}");
		}
		return json.toString();
	}
	
//	private static String sdo2json(DataObject sdo) {
//		StringBuilder json = new StringBuilder();
//		json.append("{");
//		if (sdo != null) {
//			List propertyList = sdo.getInstanceProperties();
//			Iterator iterator = propertyList.iterator();
//			while(iterator.hasNext()) {
//				Property porperty = (Property)iterator.next();
//				json.append(obj2json(porperty.getName()));
//				json.append(":");
//				json.append(obj2json(sdo.get(porperty)));
//				
//				if (iterator.hasNext()) {
//					json.append(",");
//				}
//			}
//		}
//		json.append("}");
//		return json.toString();
//	}

	// 把字符串转化为JSON字符串
	private static String string2json(String s) {
		if (s == null)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			switch (ch) {
			case '"':
				sb.append("\\\"");
				break;
			case '\\':
				sb.append("\\\\");
				break;
			case '\b':
				sb.append("\\b");
				break;
			case '\f':
				sb.append("\\f");
				break;
			case '\n':
				sb.append("\\n");
				break;
			case '\r':
				sb.append("\\r");
				break;
			case '\t':
				sb.append("\\t");
				break;
			case '/':
				sb.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					sb.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						sb.append('0');
					}
					sb.append(ss.toUpperCase());
				} else {
					sb.append(ch);
				}
			}
		}
		return sb.toString();
	}

	private static String formatJson(String json, String fillStringUnit) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}
		
		int fixedLenth = 0;
		ArrayList<String> tokenList = new ArrayList<String>();
		{
			String jsonTemp = json;
			//预读取
			while (jsonTemp.length() > 0) {
				String token = getToken(jsonTemp);
				jsonTemp = jsonTemp.substring(token.length());
				token = token.trim();
				tokenList.add(token);
			}			
		}
		
		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			int length = token.getBytes().length;
			if (length > fixedLenth && i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
				fixedLenth = length;
			}
		}
		
		StringBuilder buf = new StringBuilder();
		int count = 0;
		for (int i = 0; i < tokenList.size(); i++) {
			
			String token = tokenList.get(i);
			
			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, count, fillStringUnit);
				continue;
			}
			if (token.equals(":")) {
				buf.append(" ").append(token).append(" ");
				continue;
			}
			if (token.equals("{")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("}")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}
			if (token.equals("[")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			}
			if (token.equals("]")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}
			
			buf.append(token);
			//左对齐
			if (i < tokenList.size() - 1 && tokenList.get(i + 1).equals(":")) {
				int fillLength = fixedLenth - token.getBytes().length;
				if (fillLength > 0) {
					for(int j = 0; j < fillLength; j++) {
						buf.append(" ");
					}
				}
			}
		}
		return buf.toString();
	}
	
	private static String getToken(String json) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (json.length() > 0) {
			String token = json.substring(0, 1);
			json = json.substring(1);
			
			if (!isInYinHao && 
					(token.equals(":") || token.equals("{") || token.equals("}") 
							|| token.equals("[") || token.equals("]")
							|| token.equals(","))) {
				if (buf.toString().trim().length() == 0) {					
					buf.append(token);
				}
				
				break;
			}

			if (token.equals("\\")) {
				buf.append(token);
				buf.append(json.substring(0, 1));
				json = json.substring(1);
				continue;
			}
			if (token.equals("\"")) {
				buf.append(token);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}				
			}
			buf.append(token);
		}
		return buf.toString();
	}

	private static void doFill(StringBuilder buf, int count, String fillStringUnit) {
		buf.append("\n");
		for (int i = 0; i < count; i++) {
			buf.append(fillStringUnit);
		}
	}
}
