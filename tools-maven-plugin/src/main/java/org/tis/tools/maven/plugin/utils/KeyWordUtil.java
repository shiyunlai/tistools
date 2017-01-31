/**
 * 
 */
package org.tis.tools.maven.plugin.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 关键字转换工具
 * 
 * 默认转换对照关系见 /META-INF/keywords.properties
 * 
 * 也可通过调用addKeyWord(***)接口增加更多自定义关键字
 * </pre>
 * 
 * @author megapro
 *
 */
public class KeyWordUtil {

	private static Map<String/* keyword */, String/* newword */> keywordMap = new HashMap<String, String>();
	private static String defKeywordPropertiesFile = "/META-INF/keywords.properties";

	public static final KeyWordUtil instance = new KeyWordUtil();
	
	private KeyWordUtil() {
		init();
	}

	/**
	 * 初始化关键字转换对照关系 默认加载本jar包中 /META-INF/keywords.properties
	 * 
	 * @throws IOException
	 */
	private void init() {
		URL def = KeyWordUtil.class.getResource(defKeywordPropertiesFile);
		try {
			loadAdd(def.openStream());
		} catch (IOException e) {
			System.out.println("加载默认keyword配置文件失败！" + def.getFile());
			e.printStackTrace();
		}
	}

	private static void loadAdd(InputStream propertiesInputStream)
			throws IOException {

		InputStreamReader read = new InputStreamReader(propertiesInputStream,
				"UTF-8");// 考虑到编码格式
		BufferedReader bufferedReader = new BufferedReader(read);
		String line = null;
		while ((line = bufferedReader.readLine()) != null) {
			
			if( line.length() == 0 ){
				continue ; //跳过空行
			}
			
			if (line.startsWith("#") || line.startsWith("!")) {
				continue; // 跳过注释行
			}
			
			//开始解析keyword配置
			String[] cons = line.trim().split("=");
			String keyword = cons[0];
			String normword = null;
			if (cons.length > 1) {
				normword = cons[1];
			} else {
				normword = cons[0].charAt(0) + cons[0]; // java ==> jjava
			}

			keywordMap.put(keyword, normword);// 把properties文件中的key-value存放到一个map中
		}
	}

	/**
	 * 加入moreKeyWordFile属性文件中的关键字
	 * 
	 * @param moreKeyWordFile
	 */
	public void addKeyWord(String moreKeyWordFile) {
		File file = new File(moreKeyWordFile);
		InputStream in = null;
		try {

			in = new FileInputStream(file);
			loadAdd(in);

		} catch (FileNotFoundException e) {
			System.out.println("加载指定的keyword配置文件不存在！" + moreKeyWordFile);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("加载keyword配置文件失败！" + moreKeyWordFile);
			e.printStackTrace();
		}
	}

	/**
	 * 加入keyword关键字
	 * 
	 * @param keyword
	 *            关键字
	 * @param normword
	 *            替换为正常字
	 */
	public void addKeyWord(String keyword, String normword) {

		if (keywordMap.containsKey(keyword)) {
			// todo warning keyword is exist
		}

		keywordMap.put(keyword, normword);
	}

	/**
	 * 对关键字进行替换
	 * 
	 * @param keyword
	 *            关键字
	 * @return 代替单词；非关键字则返回自身
	 */
	public String replace(String keyword) {

		if (keywordMap.containsKey(keyword)) {
			return keywordMap.get(keyword);
		} else {
			return keyword;
		}
	}

}
