package com.bos.tis.tools.webapp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;























import com.bos.tis.tools.util.AjaxUtils;

import bos.tis.biztrace.BizTraceAnalyManage;
import bos.tis.biztrace.handler.BizLogDateHandler;
import bos.tis.biztrace.parser.LogFileParser;
import bos.tis.biztrace.upload.FileUploadServiceI;
import bos.tis.biztrace.upload.FileUploadServiceImpl;
import bos.tis.biztrace.upload.FileUploaderClient;
import bos.tis.biztrace.utils.RunConfig;
import bos.tis.lpctools.util.HelperUtil;


@Controller
@RequestMapping("/BizLogHandleController")
public class BizLogHandleController extends BaseController{
	boolean resolverOverFlag = false;
	
	@RequestMapping("/upload")
	public String upload(ModelMap model,@RequestParam MultipartFile file,
			HttpServletRequest request,HttpServletResponse response){
		try {			
			logger.info("BizLogHandleController upload request : " + file);

//			InputStream is = file.getInputStream();
//			InputStreamReader isr = new InputStreamReader(is);
//			BufferedReader reader = new BufferedReader(isr);
//			
//			StringBuilder s= new StringBuilder();
//			String tempString = null;
//			while ((tempString = reader.readLine()) != null){  
//				s.append(tempString);
//			}
//			System.out.println(s.toString());
//			
//			reader.close();
			InputStream data = file.getInputStream();			
			FileUploadServiceI uploader = new FileUploadServiceImpl();
			uploader.upload("Biztrace/"+file.getOriginalFilename(), data);
			
			String remotepath = "E:/fileUpload/Biztrace"+file.getOriginalFilename();
			//解压文件
			HelperUtil.unZipFiles(remotepath, "E:/fileUpload/Biztrace");
			//删除压缩文件
			File zipFile = new File(remotepath);
			if(zipFile.exists()){
				zipFile.delete();
			}
			
//			File newfile = new File("C:/Users/HP/Desktop/"+file.getName());	
//			
//			FileOutputStream fos = new FileOutputStream(newfile);
//			OutputStreamWriter writer = new OutputStreamWriter(fos,"UTF-8");
//			writer.write(s.toString());
//			writer.flush();
//			writer.close();
//			boolean flag = file.createNewFile();
			//FileUploaderClient.instance.upload(file);
			logger.info("BizLogHandleController upload response : 文件上传成功!");
			AjaxUtils.ajaxJsonSuccessMessage(response, "success");
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("BizLogHandleController upload exception : " ,e);
		}
		return null;		
	}
	
	@RequestMapping("/reslover")
	public String reslover(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		try {			
			logger.info("BizLogHandleController reslover request : " + content);
			
			LogFileParser.fileParsedNum = 0;
			LogFileParser.fileReadNum = 0;
			LogFileParser.fileParseRecord.clear();
			resolverOverFlag = false;
			
			//String logFilesPath = "E:/fileUpload";
			//String logFilesPath = "C:/Users/HP/Desktop/M2/logtest";
			String logFilesPath = content;
			if(logFilesPath==null || "".equals(logFilesPath)){
				return null;
			}
					
			BizTraceAnalyManage.instance.resolve(logFilesPath, RunConfig.threadNum);
			
			AjaxUtils.ajaxJsonSuccessMessage(response, "success");
			logger.info("BizLogHandleController reslover response : " + response);
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("BizLogHandleController reslover exception : " ,e);
		}
		return null;		
	}
	
	@RequestMapping("/getResloverProcess")
	public String getResloverProcess(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		try {			
			logger.info("BizLogHandleController getResloverProcess request : " + content);
			
			int fileTotalNum = BizTraceAnalyManage.instance.getFileTotalNum();		
			if(fileTotalNum == 0){
				return null;
			}
			if(LogFileParser.fileReadNum == fileTotalNum){
				resolverOverFlag = true;
			}
			Map<String,Object> result = new HashMap<String,Object>();
			result.put("resolverOverFlag", resolverOverFlag);
			result.put("fileTotalNum", fileTotalNum);
			result.put("fileReadNum", LogFileParser.fileReadNum);
			result.put("fileParsedNum", LogFileParser.fileParsedNum);
			result.put("fileParseRecord", LogFileParser.fileParseRecord);

			AjaxUtils.ajaxJson(response, JSONObject.fromObject(result, jsonConfig).toString());			
			//AjaxUtils.ajaxJsonSuccessMessage(response, "success");
			logger.info("BizLogHandleController getResloverProcess response : " + result.toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("BizLogHandleController getResloverProcess exception : " ,e);
		}
		return null;		
	}
	
		
	@RequestMapping("/analyzer")
	public String analyzer(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		try {
			logger.info("BizLogHandleController analyzer request : " + content);
			
			JSONArray jsonArry = JSONArray.fromObject(content);			
			Object[] dateArry = jsonArry.toArray();
			
			for(int i=0;i<dateArry.length;i++){
				BizTraceAnalyManage.instance.analyze(dateArry[i].toString());
			}
			
			BizLogDateHandler.instance.deleteAnalyzedBizLogDate(dateArry);
			
			AjaxUtils.ajaxJsonSuccessMessage(response, "success");
			logger.info("BizLogHandleController analyzer response : ok" );
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("BizLogHandleController analyzer exception : " ,e);
		}
		return null;		
	}
	
	@RequestMapping("/getUnanalyedDate")
	public String getUnanalyedDate(ModelMap model,@RequestBody String content,
			HttpServletRequest request,HttpServletResponse response){
		try {
			if(logger.isInfoEnabled()){
				logger.info("BizLogHandleController getUnanalyedDate request : " + content);
			}
			
			Set<String> unanalyedDates = BizLogDateHandler.instance.getUnanalyzedBizLogDate();
				
			AjaxUtils.ajaxJson(response, JSONArray.fromObject(unanalyedDates, jsonConfig).toString());
		} catch (Exception e) {
			AjaxUtils.ajaxJsonErrorMessage(response, "异常");
			logger.error("BizLogHandleController getUnanalyedDate exception : " ,e);
		}
		return null;		
	}
}
