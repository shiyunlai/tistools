/**
 * 
 */
package org.tis.tools.service.api.biztrace;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * 解析进度信息
 * 
 * 展示解析的百分比进度
 * 
 * @author megapro
 *
 */
public class ParseProcessInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2247845613131562981L;
	
	private boolean isParsing = false ;
	
	//总共有多少个日志文件需要解析
	private List<String> totalLogFiles ;
	
	//已经解析完的文件
	private List<String> parsedLogFiles ;
	
	//正在解析的日志文件名称
	private List<String> parsingLogFileName ; 
	
	//当前解析进度百分比 最多两位小数，避免小数问题，以long传输，如： 35.55%  则传输 3555，除10000得到实际值
	private long parsedProcess ;

	public boolean isParsing() {
		return isParsing;
	}

	public void setParsing(boolean isParsing) {
		this.isParsing = isParsing;
	}

	/**
	 * 总共有多少个日志文件需要解析
	 * @return
	 */
	public List<String> getTotalLogFiles() {
		return totalLogFiles;
	}

	public void setTotalLogFiles(List<String> totalLogFiles) {
		this.totalLogFiles = totalLogFiles;
	}

	/**
	 * 已经解析完的文件
	 * @return
	 */
	public List<String> getParsedLogFiles() {
		return parsedLogFiles;
	}

	public void setParsedLogFiles(List<String> parsedLogFiles) {
		this.parsedLogFiles = parsedLogFiles;
	}

	/**
	 * 正在解析的日志文件名称
	 * @return
	 */
	public List<String> getParsingLogFileName() {
		return parsingLogFileName;
	}

	public void setParsingLogFileName(List<String> parsingLogFileName) {
		this.parsingLogFileName = parsingLogFileName;
	}

	public long getParsedProcess() {
		return parsedProcess;
	}

	public void setParsedProcess(long parsedProcess) {
		this.parsedProcess = parsedProcess;
	} 
	
}
