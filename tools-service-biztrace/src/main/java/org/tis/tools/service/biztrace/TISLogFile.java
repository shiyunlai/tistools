/**
 * 
 */
package org.tis.tools.service.biztrace;

import java.io.File;
import java.io.Serializable;

import org.tis.tools.service.biztrace.parser.BizTraceResolver;
import org.tis.tools.service.biztrace.parser.DefaultResolver;
import org.tis.tools.utils.TimeUtil;

/**
 * TIS系统的日志文件
 * @author megapro
 *
 */
public class TISLogFile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3743339445304396266L;

	/** 日志文件 */
	public File logFile = null ; 
	
	/** 日志文件所属日期（文件所在目录） */
	public String dateStr = null ;
	
	/** 日志文件类型 */
	public LogTypeEnum logTypeEnum = null ; 
	
	public String toString () {
		StringBuffer sb = new StringBuffer() ;
		sb.append(dateStr).append("\t").append(logTypeEnum).append("\t\t").append(logFile.getAbsolutePath()) ; 
		return sb.toString() ;
	}
	
	public void setLogFile(File f ) {
		this.logFile = f ; 
		setDateFromPath(f.getAbsolutePath()) ; 
	}
	
	/**
	 * <pre>
	 * 从文件路径中解析出日期信息
	 * 如：
	 * /Users/megapro/temp/biztrace/bs13/20160704/biztrace.log.54
	 * 则
	 * 日期为  ： 20160704
	 * 日志类型 ： biztrace.log -> {@link LogTypeEnum#LOG_BIZTRACE }
	 * </pre>
	 * @param absolutePath 文件全路径名
	 * @return
	 */
	private void setDateFromPath(String absolutePath) {
		
		// 确定日记类型 
		for( LogTypeEnum t : LogTypeEnum.values() ){
			if( absolutePath.indexOf( t.getSuffixTag() ) > 0 ){
				this.logTypeEnum = t ;
			}
		}
		
		// 确定日志发生日期 
		this.dateStr = TimeUtil.getDatePatternFromStr(absolutePath) ; 
	}
	
	/**
	 * <pre>
	 * 日志文件类型定义
	 * 只要增加一个枚举类型，程序会自动收集该后缀类型的日志，并执行对应的解析处理
	 * </pre>
	 * @author megapro
	 *
	 */
	public static enum LogTypeEnum implements Serializable{
		
		LOG_BIZTRACE("biztrace.log",new BizTraceResolver()) ,
		
		//LOG_BTPTRACE("btp-trace.log",new DefaultResolver()) ,

		//LOG_BSTIMER("BSTimer.log",new DefaultResolver()) ,

		//LOG_ENGINE("engine.log",new DefaultResolver()) ,
		
		//LOG_HMONITOR("hmonitor.log",new DefaultResolver()) ,

		//LOG_STDATA("stdata.log",new DefaultResolver()) ,
		
		//LOG_TRACE("trace.log",new DefaultResolver()) ,//FIXME 文件命名有冲突，导致 biztrace和btp-trace 类型映射错误，需要Pattern解决

		LOG_BS("bs.log",new DefaultResolver());
		//LOG_BS("bs.log",new BSResolver());
		
		/** 文件标志缀 */
		String suffixTag = null ;
		
		/** 日志文件拆分解析器 */
		public IBizTraceResolver resolver = null ; 
		
		private LogTypeEnum(String suffixTag,IBizTraceResolver resolver ){
			this.suffixTag = suffixTag ; 
			this.resolver = resolver ; 
		}
		
		public String getSuffixTag() {
			return suffixTag;
		}

		public void setSuffixTag(String suffixTag) {
			this.suffixTag = suffixTag;
		}

		public IBizTraceResolver getResolver() {
			return resolver;
		}

		public void setResolver(IBizTraceResolver resolver) {
			this.resolver = resolver;
		}

		public String toString(){
			return suffixTag; 
		}
	}
}
