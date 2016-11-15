/**
 * 
 */
package org.tis.tools.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;

/**
 * 
 * 文件压缩工具类
 * 
 * @author megapro
 *
 */
public class ZipUtil {

	/**
	 * 解压缩.zip
	 * @param decompasspath
	 * @param savepath
	 */
	public static void unZip(String decompasspath,String savepath){
		try {  
	        ZipInputStream Zin=new ZipInputStream(new FileInputStream(decompasspath));//输入源zip路径  
	        BufferedInputStream Bin=new BufferedInputStream(Zin);  
	        File file=null;  
	        ZipEntry entry;  
	        try {  
	            while((entry = Zin.getNextEntry())!=null && !entry.isDirectory()){  
	            	//System.out.println(entry.getName());
	                file=new File(savepath,entry.getName());  
	                if(!file.exists()){  
	                    (new File(file.getParent())).mkdirs();  
	                }  
	                FileOutputStream out=new FileOutputStream(file);  
	                BufferedOutputStream bos=new BufferedOutputStream(out);  
	                int b;  
	                while((b=Bin.read())!=-1){  
	                    bos.write(b);  
	                }  
	                bos.close();  
	                out.close();  
	                //System.out.println(file+"解压成功");      
	            }  
	            Bin.close();  
	            Zin.close();  
	        } catch (IOException e) {   
	            e.printStackTrace();  
	        }  
	    } catch (FileNotFoundException e) {    
	        e.printStackTrace();  
	    }
	}
	
	/**
	 * 解压缩.rar
	 * @param decompasspath
	 * @param savepath
	 */
	public static void unRar(String sourceRar, String destDir){
	     Archive a = null;
	     FileOutputStream fos = null;
	     try {
	         a = new Archive(new File(sourceRar));
	         FileHeader fh = a.nextFileHeader();
	         while (fh != null) {
	             if (!fh.isDirectory()) {
	                  // 1 根据不同的操作系统拿到相应的 destDirName 和 destFileName
	                 String compressFileName = fh.getFileNameString().trim();
	                 System.out.println(compressFileName);
	                 String destFileName = "";
	                 String destDirName = "";
	                  // 非windows系统
	                 if (File.separator.equals("/")) {
	                   destFileName = destDir +"\\"+ compressFileName.replaceAll("\\\\", "/");
	                   destDirName = destFileName.substring(0, destFileName.lastIndexOf("/"));
	                   // windows系统
	                  } else {                   
	                   destFileName = destDir+"\\"+ compressFileName.replaceAll("/", "\\\\");                     
	                   destDirName = destFileName.substring(0,destFileName.lastIndexOf("\\"));
	                  }   
	                  // 2创建文件夹
	                  File dir = new File(destDirName);
	                  if (!dir.exists() || !dir.isDirectory()) {
	                   dir.mkdirs();
	                  }
	                  // 3解压缩文件
	                  fos = new FileOutputStream(new File(destFileName));
	                  a.extractFile(fh, fos);
	                  fos.close();
	                  fos = null;
	             }
	             fh = a.nextFileHeader();
	         }
	         a.close();
	         a = null;
	     } catch (Exception e) {
	         e.printStackTrace();
	     } finally {
	         try {
	             if (fos != null) {
	                 fos.close();
	                 fos = null;
	             }
	             if (a != null) {
	                 a.close();
	                 a = null;
	             }
	         } catch (Exception e) {
	         	e.printStackTrace();
	         }                        
	     }
	}
    	
}
