/**
 * 
 */
package org.tis.tools.utils;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 文件目录工具类
 * 
 * @author megapro
 *
 */
public class DirectoryUtil {

	/**
	 * 根据文件后缀,列出目录下指定的文件
	 * @param path	路径
	 * @param recusive 递归方式
	 * @param subfix 文件后缀
	 * @return
	 */
	public static List<File> listFile(String path,boolean recusive , final String subfix){
		List<File> files = null;
		
		try {
			files = listFile(path,recusive,new FileFilter(){
				@Override
				public boolean accept(File pathname) {
					if( pathname.getAbsolutePath().endsWith(subfix) ){
						return true ; 
					}
					return false;
				}
			}) ;
		} catch (Exception e) {
			e.printStackTrace();	
			throw new IllegalArgumentException(path+"路径不存在!");
		}
		
		return files;
	}

	/**
	 * 列出目录下指定的文件
	 * @param file 目录
	 * @param recusive 递归方式
	 * @param filter 过滤条件
	 * @return
	 * @throws Exception 
	 */
	public static List<File> listFile(String path, boolean recusive,
			FileFilter filter) throws Exception {
		return listFile(new File(path),recusive,filter) ; 
	}
	
	/**
	 * 遍历dir目录下所有文件
	 * @param dir 遍历目录
	 * @param recusive 是否递归遍历子目录 true递归 false只遍历一级目录
	 * @param filter 过滤文件条件
	 * @return
	 * @throws Exception
	 */
	public static List<File> listFile(File dir,boolean recusive,
			FileFilter filter) throws Exception {
		
		File[] fs =dir.listFiles() ;
		List<File> allFiles = new ArrayList<File>() ; 
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				if( recusive ){
					try {
						allFiles.addAll(listFile(fs[i],recusive,filter));
					} catch (Exception e) {
					}
				}
			}else{
				if( filter.accept(fs[i]) ){
					allFiles.add(fs[i]) ;
				}
			}
		}
		
		return allFiles ; 
	}
}
