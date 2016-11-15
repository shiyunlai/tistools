package bos.tis.lpctools.handler;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.tis.tools.utils.SvnUtil;
import org.tmatesoft.svn.core.SVNException;

import bos.tis.lpctools.entity.CommitListContent;
import bos.tis.lpctools.util.ParamsConfig;

public class CompareHandler {
	
	public static final CompareHandler instance = new CompareHandler() ;
	public static List<Map<String,String>> results = new ArrayList<Map<String,String>>();
	
	public List<String> getAllList(String userName,String password){
		List<String> lists =null;
		try {
			lists = SvnUtil.getSvnCommitRecord(userName, password, "/development/branches/docs/A_project_development/02_development/05_patch_list/0111_SALM开发流程管理/List");
		} catch (SVNException e) {
			e.printStackTrace();
			throw new IllegalArgumentException("获取清单失败!");
		}
		
		List<String> results = new ArrayList<String>();
		for(String listPath : lists){
			String listName = listPath.substring(listPath.lastIndexOf("/")+1);
			results.add(listName);
		}
		return results;
	}
	
//	public Map<String, String> getAllFeatures(){
//		Map<String, String> featureMap =null;
//		try {
//			featureMap = HelperUtil.readFeatureExcel("E:/fileUpload/TIS项目-开发计划员管理表-工作内容座标V1.xlsx");
//		} catch (FileNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
//		//System.out.println(featureMap);
//		if(featureMap == null || featureMap.isEmpty()){
//			throw new IllegalArgumentException("找不到分支清单!");
//		}
//		return featureMap;		
//	}
	
	/**
	 * 根据提交清单检查打的包中是否缺少
	 * @param listName	清单名称				
	 * @param commitListContents	清单内容
	 * @param selectedPackageFileList	选择检查的报名称的集合
	 * @param svnUserName	svn用户名
	 * @param svnPassword	svn密码
	 * @param featureMap	所有需求对应的分支号
	 * @return
	 */
	public List<Map<String,String>> checkPackageByList(String listName,List<CommitListContent> commitListContents,final List<String> selectedPackageFileList,
			String svnUserName,String svnPassword,Map<String, String> featureMap){
		System.out.println(featureMap);
		String feature = featureMap.get(listName);
		if(feature==null){
			throw new IllegalArgumentException(listName.substring(0, listName.lastIndexOf("."))+"找不到分支!");
		}
		
		String featurePath = "";
		if(feature.startsWith("Feature")){
			featurePath = "Feature/" + feature;
		}else if(feature.startsWith("Hotfix")){
			featurePath = "Hotfix/" + feature;
		}
		
		
		for(CommitListContent contentRow : commitListContents){
			String deploy_position = contentRow.getDeploy_position();
			String code_path = contentRow.getCode_path();
			boolean existFlag = false;
			//System.out.println("code_path:"+code_path);
			List<String> commitHistory = null;
			try {
				commitHistory = SvnUtil.getSvnCommitRecord(svnUserName, svnPassword, "/development/branches/"+featurePath+code_path);
			} catch (SVNException e) {
				//e.printStackTrace();
				//System.out.println(e.getMessage());
				if(e.getMessage().contains("404 Not Found")){
					putResult(existFlag,listName,contentRow);
					continue;
				}
				e.printStackTrace();
				throw new IllegalArgumentException("获取svn提交记录异常!");
			}
			if(commitHistory == null || commitHistory.isEmpty()){//如果目标路径在svn中不存在
				//existFlag = false;
				putResult(existFlag,listName,contentRow);
				continue;
			}
			
			String lastStr = code_path.substring(code_path.lastIndexOf("/"));
			if(lastStr.contains(".") && !lastStr.substring(0, lastStr.lastIndexOf(".")).contains(".")){//如果是具体某个文件路径,直接查找包文件进行对比				
				existFlag = doCompare(deploy_position,contentRow,selectedPackageFileList);		
				
				putResult(existFlag,listName,contentRow);
			}else{//如果是目录路径,取svn提交记录跟包文件对比
//				Map<String, String> featureMap =null;
//				try {
//					featureMap = HelperUtil.readFeatureExcel("E:/fileUpload/TIS项目-开发计划员管理表-工作内容座标V1.xlsx");
//				} catch (FileNotFoundException e1) {
//					// TODO 自动生成的 catch 块
//					e1.printStackTrace();
//				} catch (IOException e1) {
//					// TODO 自动生成的 catch 块
//					e1.printStackTrace();
//				}
//				//System.out.println(featureMap);
//				if(featureMap == null){
//					throw new IllegalArgumentException("找不到分支清单!");
//				}
//				
//				String feature = featureMap.get(listName);
//				if(feature==null){
//					throw new IllegalArgumentException("找不到分支!");
//				}
//				
//				String featurePath = "";
//				if(feature.startsWith("Feature")){
//					featurePath = "Feature/" + feature;
//				}else if(feature.startsWith("Hotfix")){
//					featurePath = "Hotfix/" + feature;
//				}
//				
//				List<String> commitHistory = null;
//				try {
//					commitHistory = SvnUtil.getSvnCommitRecord(svnUserName, svnPassword, "/development/branches/"+featurePath+code_path);
//				} catch (SVNException e) {
//					e.printStackTrace();
//					throw new IllegalArgumentException("获取svn提交记录异常!");
//				}
//				
//				if(commitHistory == null || commitHistory.isEmpty()){//如果目录路径在svn中不存在
//					existFlag = false;
//					putResult(existFlag,listName,contentRow);
//				}else{//如果存在则取该目录下的提交记录跟包文件对比
				for(String filePath : commitHistory){
					if(!filePath.substring(filePath.lastIndexOf("/")).contains(".")){//如果不是文件路径而是目录
						continue;
					}
					contentRow.setCode_path(filePath.substring(filePath.indexOf(featurePath)+featurePath.length()));
					existFlag = doCompare(deploy_position,contentRow,selectedPackageFileList);
						
					putResult(existFlag,listName,contentRow);
//					}
				}
			}
						
		}
		return results;
	}
	
	/**
	 * 根据存在标志将打的包中遗漏的内容添加到结果集中
	 * @param existFlag	包中是否存在清单中的内容的标志
	 * @param listName	清单名称
	 * @param contentRow 清单中某一行的内容
	 */
	private void putResult(boolean existFlag,String listName,CommitListContent contentRow){
		if(!existFlag){				
			Map<String,String> result = new HashMap<String,String>();
			result.put("listName", listName);
			result.put("differFile", contentRow.getCode_path());
			result.put("isExsitInPackage", "无");
			result.put("deployPosition", contentRow.getDeploy_position());
			result.put("submitter", contentRow.getSubmitter());
			results.add(result);
		}
	}
	
	/**
	 * 
	 * @param deploy_position	部署位置
	 * @param contentRow	清单中某一行的内容
	 * @param selectedPackageFileList	要对比的包的集合
	 * @return
	 */
	private boolean doCompare(final String deploy_position,CommitListContent contentRow,final List<String> selectedPackageFileList){
		String deploy_flag = "";
		if("bs.work.user".equals(deploy_position)){			
			deploy_flag = "BS";			
		}else if("tws.work.user".equals(deploy_position)){
			deploy_flag = "TWS";							
		}
		final String compareFlag = deploy_flag;
		
		File f = new File(ParamsConfig.zipFileDecompassPath);
		File[] files = f.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name) {
				if(name.contains(compareFlag) && selectedPackageFileList.contains(name+".zip")){
					return true;
				}
				return false;
			}										
		});

		if(files.length == 0){
			return false;
		}
		//System.out.println("files:"+files);
		
		boolean existFlag = false;
		for(File dir : files){
			String isCheckedPathSrc = dir.getAbsolutePath()+contentRow.getCode_path();
			String isCheckedPath = isCheckedPathSrc.replace("/src", "");
			
			String isCheckedPathFatherFirStr = isCheckedPath.substring(0, isCheckedPath.lastIndexOf("/"));
			File isCheckedPathFatherFir = new File(isCheckedPathFatherFirStr);
			if(!isCheckedPathFatherFir.exists()){
				return false;
			}
			
			String isCheckedFileName = isCheckedPath.substring(isCheckedPath.lastIndexOf("/")+1, isCheckedPath.lastIndexOf("."));
			if("".equals(isCheckedFileName)){//如果检查的文件名如.eos
				isCheckedFileName = isCheckedPath.substring(isCheckedPath.lastIndexOf("/")+1);
			}
			File[] childFiles = isCheckedPathFatherFir.listFiles();
			for(File childFile : childFiles){
				String childFileName = childFile.getName();
//				System.out.println(isCheckedFileName);
//				System.out.println(childFileName);
				
				//文件名会有如.eos这样的文件
				String childFileName1 = "";
				int index = childFileName.lastIndexOf(".");				
				if(index == -1){//为目录
					continue;
				}else if(index == 0){//如:.eos
					childFileName1 = childFileName;
				}else{
					childFileName1 = childFileName.substring(0, index);
				}
				if(isCheckedFileName.equals(childFileName1)){
					existFlag = true;
					break;
				}
			}
			
			if(existFlag){
				break;
			}

		}
		
		return existFlag;
	}
}
