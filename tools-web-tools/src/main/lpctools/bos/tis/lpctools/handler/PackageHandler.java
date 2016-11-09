package bos.tis.lpctools.handler;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

import bos.tis.lpctools.util.HelperUtil;
import bos.tis.lpctools.util.ParamsConfig;

public class PackageHandler {
	
	public static final PackageHandler instance = new PackageHandler() ;
	
	/**
	 * 将打好的包文件解压出来用于比较
	 * @param packagePath	包文件存放路径
	 * @param dePackageFir	解压后的目录
	 */	 	 
	public void dePackageHandle(String packagePath,String dePackageFir){
				
		HelperUtil.unZipFiles(packagePath, dePackageFir);
		
		File file = new File(dePackageFir);
		File[] childFiles = file.listFiles(new FilenameFilter(){			
			@Override
			public boolean accept(File dir, String name) {
				if( name.endsWith(".jar") || name.endsWith(".epd") || name.endsWith(".ecd")){
					return true ; 
				}
				return false;
			}
		});
		
		if(childFiles.length == 0){
			return;
		}
		
		for(File childFile : childFiles){
			String fileName = childFile.getName();
			dePackageHandle(childFile.getAbsolutePath(),dePackageFir+File.separator+fileName.substring(0, fileName.lastIndexOf(".")));
			childFile.delete();
		}
		
	}
}
