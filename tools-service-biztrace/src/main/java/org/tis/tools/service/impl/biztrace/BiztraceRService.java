package org.tis.tools.service.impl.biztrace;

import java.util.ArrayList;
import java.util.List;

import org.tis.tools.service.api.biztrace.AnalyseResult;
import org.tis.tools.service.api.biztrace.BiztraceFileInfo;
import org.tis.tools.service.api.biztrace.IBiztraceRService;
import org.tis.tools.service.api.biztrace.ParseResult;
import org.tis.tools.service.biztrace.BizTraceAnalyManage;
import org.tis.tools.service.biztrace.TISLogFile;
import org.tis.tools.service.exception.biztrace.BiztraceRServiceException;
import org.tis.tools.utils.TimeUtil;


/**
 * 
 * BS服务器的‘业务日志分析代理服务’实现
 * @author megapro
 *
 */
public class BiztraceRService implements IBiztraceRService
{

	@Override
	public List<BiztraceFileInfo> listBiztraces()
			throws BiztraceRServiceException {
		
		//TODO 修改为本地BS的日志路径
		String bsPath = "/Users/megapro/temp" ; 
		String bsLogPath = bsPath + "/logs/" ; 
		
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
	public ParseResult parseBiztraceFixed(List<BiztraceFileInfo> fixedBiztraces)
			throws BiztraceRServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AnalyseResult analyseBiztrace(List<String> fixedDay)
			throws BiztraceRServiceException {
		// TODO Auto-generated method stub
		return null;
	}


	
}
