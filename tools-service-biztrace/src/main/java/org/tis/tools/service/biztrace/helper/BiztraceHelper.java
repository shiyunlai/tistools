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

	public final static BiztraceHelper instance = new BiztraceHelper() ; 
	private Properties p = new Properties();   
	private String root = ""; 
	
	private BiztraceHelper(){
		init() ; 
	}
	
	private void init() {
		root = DirectoryUtil.getAppMainDirectory() ; 
		String biztracePros = root + "/conf/biztrace.properties" ; 
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(biztracePros));   
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取BS路径
	 * @return 如果从biztrace.properties中取不到，默认返回当前目录/../bs
	 */
	public String getBSHome() {
		//返回配置文件中BS_HOME，如果没有或者读取.properties文件失败,则默认返回本应用同目录的bs路径
		String temp = p.getProperty("BS_HOME",root+"/../bs");
		return temp ; 
	}
	
	/**
	 * 获取工作线程数
	 * @return  如果从biztrace.properties中取不到，默认返回5个
	 */
	public int getWorkerThreads(){
		String temp = p.getProperty("WORKER_THREADS","5") ; //取不到就默认5个
		return Integer.valueOf(temp).intValue() ;
	}
}
