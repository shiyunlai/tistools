/**
 * 
 */
package org.tis.tools.service.biztrace.helper;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.common.utils.DirectoryUtil;

import com.baidu.disconf.client.usertools.DisconfDataGetter;

/**
 * 
 * 业务日志分析功能帮助类
 * 
 * @author megapro
 *
 */
public class BiztraceHelper {

	public final static BiztraceHelper instance = new BiztraceHelper() ; 
	private String root = ""; 
	
	private BiztraceHelper(){
		init() ; 
	}
	
	private void init() {
		root = DirectoryUtil.getAppMainDirectory() ; 
	}

	/**
	 * 获取BS路径
	 * @return 如果从biztrace.properties中取不到，默认返回当前目录/../bs
	 */
	public String getBSHome() {
		//返回配置文件中BS_HOME，如果没有或者读取.properties文件失败,则默认返回本应用同目录的bs路径
		String temp = /**DisconfDataGetter.getByFileItem("biztrace.properties", "BS_HOME").toString()**/"" ;
		if( StringUtils.isEmpty(temp) ){
			temp= root+"/../bs" ; //默认
		}
		return temp ; 
	}
	
	/**
	 * 获取工作线程数
	 * @return  如果从biztrace.properties中取不到，默认返回5个
	 */
	public int getWorkerThreads(){
		String temp = /**DisconfDataGetter.getByFileItem("biztrace.properties", "WORKER_THREADS").toString()**/"" ;
		if( StringUtils.isEmpty(temp) ){
			temp = "1" ; //取不到就默认5个
		}
		return Integer.valueOf(temp).intValue() ;
	}
}
