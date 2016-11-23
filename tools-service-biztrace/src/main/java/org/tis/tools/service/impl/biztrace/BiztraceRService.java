package org.tis.tools.service.impl.biztrace;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.common.utils.TimeUtil;
import org.tis.tools.service.api.biztrace.AnalyseResult;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;
import org.tis.tools.service.api.biztrace.IBiztraceRService;
import org.tis.tools.service.api.biztrace.ParseProcessInfo;
import org.tis.tools.service.api.biztrace.ParseResult;
import org.tis.tools.service.biztrace.BizTraceAnalyManage;
import org.tis.tools.service.biztrace.TISLogFile;
import org.tis.tools.service.biztrace.helper.BiztraceHelper;
import org.tis.tools.service.exception.biztrace.BiztraceRServiceException;


/**
 * 
 * BS服务器的‘业务日志分析代理服务’实现
 * @author megapro
 *
 */
public class BiztraceRService implements IBiztraceRService
{
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass()) ;
	
	@Override
	public List<BiztraceFileInfo> listBiztraces( String providerHost )
			throws BiztraceRServiceException {
		
		logger.debug("haha....<"+providerHost+">干活，列出所有日志文件名！" );
		//默认biztrace与bs同目录部署，于是 BIZTRACE_HOME/../../bs/logs/就是日志目录位置
		String bsLogPath = BiztraceHelper.getBSHome() + "/logs/";
		
		//TODO 按最近修改时间由新到旧排序
		List<BiztraceFileInfo> logFiles = new ArrayList<BiztraceFileInfo>() ; 
		try {
			List<TISLogFile> ll= BizTraceAnalyManage.instance.listLogFiles(bsLogPath) ;
			for( TISLogFile f : ll ){
				BiztraceFileInfo info = new BiztraceFileInfo( ) ;
				info.setFileName(f.logFile.getName());
				info.setFilePath(f.logFile.getPath());
				info.setFileSize(f.logFile.length());//单位KB
				info.setLastModifedTime(TimeUtil.longToDateStr(f.logFile.lastModified(),null));
				logFiles.add(info) ; 
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BiztraceRServiceException("列出日志文件时异常！",e) ;
		}
		
		return logFiles;
	}
	
	@Override
	public ParseResult resolveAndAnalyseBiztraceFixed(List<BiztraceFileInfo> fixedBiztraces)
			throws BiztraceRServiceException {
		
		//TODO 收集各线程中的信息返回 ParseResult？ 或者查询redis获取这些信息
		BizTraceAnalyManage.instance.resolve(fixedBiztraces, 1);
		
		return new ParseResult() ;
	}
	
	@Override
	public ParseProcessInfo getResolveProcess() {
		
		//TODO Auto-generated method stub
		return null ; 
	}

}
