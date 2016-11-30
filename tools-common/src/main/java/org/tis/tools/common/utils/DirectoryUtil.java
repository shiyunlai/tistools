/**
 * 
 */
package org.tis.tools.common.utils;

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
	
	
	/**
	 * <pre>
	 * 获取应用主路径
	 * 如：
	 * 应用biztrace 部署于 tis用户目录app下， /tis/app/biztrace
	 * 本方法返回 /tis/app
	 * 应用biztrace 部署于 tis用户目录app下， C:/tis/app/biztrace
	 * 本方法返回 C:/tis/app
	 * </pre>
	 * @return
	 */
	public static String getAppMainDirectory(){
		String str = System.getProperty("user.dir") ; 
		return str ; 
	}
	
	
	/**
	 *  <pre>
	 * 获取类clazz的根路径
	 * 如：Abc.class 位于工程 C:/tis/app/biztrace/lib/abc.jar中，则返回: C:/tis/app/biztrace
	 * </pre>
	 * @param clazz
	 * @return
	 */
	public static String getClassRootDirectory(Class<?> clazz){
		
		File f = new File(clazz.getClass().getResource("/").getPath());
        //System.out.println(f);
		return f.getAbsolutePath() ;
	}
	
}
