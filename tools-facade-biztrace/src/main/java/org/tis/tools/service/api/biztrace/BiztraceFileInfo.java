/**
 * 
 */
package org.tis.tools.service.api.biztrace;

import java.io.Serializable;

/**
 * 
 * Biztrace日志文件信息对象
 * 
 * @author megapro
 *
 */
public class BiztraceFileInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6996719204574284215L;

	private String fileName  ;
	
	private String filePath  ; 
	
	private String lastModifedTime; 
	
	private long fileSize ; //以KB为单位

	/**
	 * 日志文件名称（无路径信息）
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * 返回日志文件所在路径
	 * @return
	 */
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 日志文件最后修改时间
	 * @return
	 */
	public String getLastModifedTime() {
		return lastModifedTime;
	}
	
	public void setLastModifedTime(String lastModifedTime) {
		this.lastModifedTime = lastModifedTime;
	}
	
	/**
	 * 文件大小(KB为单位)
	 * @return
	 */
	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
}
