/**
 * 
 */
package org.tis.tools.maven.plugin.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author megapro
 *
 */
public class FileUtil {
	
	
	/**
	 * 返回指定目录下，指定后缀的文件（包括子目录）
	 * @param dir
	 * @return 文件名list，全路径文件名
	 */
	public static List<String> listFilesNameBySuffix(File dir,String suffix){
		
		List<String> filesNameList = new ArrayList<String> ()  ;
		
		File[] files = dir.listFiles( new ModelDefFileFilter(suffix) );

		if (files == null) {
			//todo warning 目录下无模型定义文件
			
		}else{
			for (File f : files) {
				if (f.isDirectory()) {
					listFilesNameBySuffix(f,suffix);
				} 
				else {
					filesNameList.add(f.getAbsolutePath()); 
				}
			}
		}
		
		return filesNameList ; 
	}
	
	
	/**
	 * 返回指定目录下，指定后缀的文件
	 * @param dir
	 * @return 文件对象list
	 */
	public static List<File> listFilesBySuffix(File dir,final String suffix){
		
		List<File> filesNameList = new ArrayList<File> ()  ;
		
		File[] files = dir.listFiles(new ModelDefFileFilter(suffix));

		if (files == null) {
			//todo warning 目录下无模型定义文件
			
		}else{
			for (File f : files) {
				if (f.isDirectory()) {
					listFilesNameBySuffix(f,suffix);
				} 
				else {
					filesNameList.add(f); 
				}
			}
		}
		
		return filesNameList ; 
	}
	
	/**
	 * 
	 * 过滤模型定义文件
	 * @author megapro
	 *
	 */
	public static class ModelDefFileFilter implements FilenameFilter{

		//文件后缀，默认 .xml 结尾
		private String suffix = ".xml" ; 
		
		public ModelDefFileFilter(){
			
		}
		
		public ModelDefFileFilter(String suffix){
			this.suffix = suffix ; 
		}
		
		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(suffix);
		}
		
	}

	/**
	 * 判断给定的文件路径不存在
	 * @param directoryStr 文件路径
	 * @return true 路径不存在 false 路径存在 
	 */
	public static boolean isNotExistPath(String directoryStr) {
		
		File f = new File(directoryStr) ;
		return !f.exists();
	}

}
