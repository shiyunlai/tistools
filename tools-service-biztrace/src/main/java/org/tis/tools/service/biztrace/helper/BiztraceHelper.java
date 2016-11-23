/**
 * 
 */
package org.tis.tools.service.biztrace.helper;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.tis.tools.common.utils.DirectoryUtil;

/**
 * 
 * 业务日志分析功能帮助类
 * 
 * @author megapro
 *
 */
public class BiztraceHelper {

	/**
	 * 获取BS路径
	 * @return
	 */
	public static String getBSHome() {
		String root = DirectoryUtil.getAppMainDirectory() ; 
		String biztracePros = root + "/conf/biztrace.properties" ; 
		Properties p = new Properties();   
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(biztracePros));   
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//返回配置文件中BS_HOME，如果没有或者读取.properties文件失败,则默认返回本应用同目录的bs路径
		String temp = p.getProperty("BS_HOME",root+"/../bs");
		return temp ; 
	}
}
