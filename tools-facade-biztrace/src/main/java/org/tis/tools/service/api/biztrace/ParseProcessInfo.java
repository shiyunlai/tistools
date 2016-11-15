/**
 * 
 */
package org.tis.tools.service.api.biztrace;

import java.io.Serializable;

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
	private int totalLogFiles ;
	
	//已经解析完的文件
	private int parsedLogFiles ;
	
	//正在解析的日志文件名称
	private String parsingLogFileName ; 
	
	//TODO 其他进度信息

	/**
	 * 是否正在执行日志解析处理 
	 * @return true - 执行中， false - 未执行(无需显示进度条)
	 */
	public boolean isParsing() {
		return isParsing;
	}

	public void setParsing(boolean isParsing) {
		this.isParsing = isParsing;
	}

	public int getTotalLogFiles() {
		return totalLogFiles;
	}

	public void setTotalLogFiles(int totalLogFiles) {
		this.totalLogFiles = totalLogFiles;
	}

	public int getParsedLogFiles() {
		return parsedLogFiles;
	}

	public void setParsedLogFiles(int parsedLogFiles) {
		this.parsedLogFiles = parsedLogFiles;
	}

	public String getParsingLogFileName() {
		return parsingLogFileName;
	}

	public void setParsingLogFileName(String parsingLogFileName) {
		this.parsingLogFileName = parsingLogFileName;
	}
	
}
