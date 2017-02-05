package org.tis.tools.maven.plugin.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.tis.tools.maven.plugin.exception.GenDaoMojoException;
import org.tis.tools.maven.plugin.freemarker.ext.HumpClassName;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.utility.StringUtil;

public class FreeMarkerUtil {
	
	private static Configuration cfg;
	
	//指定模版加载，配置加载时都使用 UTF-8 编码
	private static final String ENCODING ="UTF-8";
	
	/**
	 * 自定义处理能力
	 */
	private static Map<String, Object> templateMethodModels = new HashMap<String, Object>() ; 
	
	static {
		//注册FreeMarker模版的自定义方法
		templateMethodModels.put("humpClassName",new HumpClassName()) ; //${humpClassName("abc")}、${humpClassName(table.id)}
	}
	
	public Configuration getCfg() {
		return cfg;
	}

	/**
	 * </br>初始化FreeMarker模版
	 * @param defaultTemplatesPath 插件jar中的 META-INF/templates 位置下的模版
	 */
	public static void initDefTemplate(String defaultTemplatesPath) {
		cfg = new Configuration();
		cfg.setDefaultEncoding(ENCODING);
		System.out.println("使用默认FreeMarker模版:"+defaultTemplatesPath);
		cfg.setClassForTemplateLoading(FreeMarkerUtil.class, defaultTemplatesPath);
	}

	
	/**
	 * </br>初始化FreeMarker模版
	 * @param fixedTemplatesPath 指定模版文件位置，如： D:\gendao\templates 
	 * @throws GenDaoMojoException 如果模版文件位置不存在抛出异常
	 */
	public static void initFixedTemplate(String fixedTemplatesPath) throws GenDaoMojoException {
		cfg = new Configuration();
		cfg.setDefaultEncoding(ENCODING);
		try {
			System.out.println("使用指定FreeMarker模版:"+fixedTemplatesPath);
			cfg.setDirectoryForTemplateLoading(new File(fixedTemplatesPath));
		} catch (IOException e) {
			e.printStackTrace();
			throw new GenDaoMojoException("初始化源码模版失败！",e) ; 
		}
	}
	
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void process(String tmplate, Map root, String targetFile) throws Exception {
		
		if (new File(targetFile).exists()) {
			String s = FileUtils.readFileToString(new File(targetFile));
			if (s.indexOf("bak the file by lanmosoft.com") >= 0) {
				System.out.println("已锁定" + targetFile);
				return;
			}
		} else {
			FileUtils.writeStringToFile(new File(targetFile), "");
		}

		//加入自定义方法
		root.putAll(templateMethodModels);
		
		Template t = cfg.getTemplate(tmplate);
		t.setEncoding(ENCODING);
		Writer out = new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8");
		t.process(root, out);
		out.flush();
		out.close();
	}
	
	public static String capFirst(String a) {
		try {
			return StringUtil.capitalize(a.substring(0, 1)) + a.substring(1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return a;
	}

}
