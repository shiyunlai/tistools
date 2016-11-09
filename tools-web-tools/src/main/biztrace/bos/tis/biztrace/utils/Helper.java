/**
 * 
 */
package bos.tis.biztrace.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.junrar.Archive;
import com.github.junrar.rarfile.FileHeader;


/**
 * 工具类,常用方法
 * 
 * @author megapro
 *
 */
public class Helper {

	/**
	 * 格式化 "yyyy-MM-dd HH:mm:ss,SSS" 的字符串为日期
	 */
	private static SimpleDateFormat sdf = new SimpleDateFormat(RunConfig.BIZ_TRACE_DATE_PATTERN);
	private final static Logger logger = LoggerFactory.getLogger(Helper.class);
	static byte[] buffer = new byte[2048];
	
	/**
	 * 返回日期的时间
	 * 
	 * @param dateStr
	 * @return
	 */
	public static long toTime(String dateStr) {
		Date date = null;
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			System.out.println("日期格式化失败！" + e);
			e.printStackTrace();
		}
		return date.getTime();
	}


	/**
	 * 返回path中第一个满足yyyyMMdd格式的日期字符串
	 * @param path
	 * @return 如果path中有日期则返回，否则返回空字符串
	 */
	public static String getDatePatternFromStr(String path) {
		String reg = "[1-9]\\d{3}(((0[13578]|1[02])([0-2]\\d|3[01]))|((0[469]|11)([0-2]\\d|30))|(02([01]\\d|2[0-8])))";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(path);
		while (matcher.find()) {
			return matcher.group();
		}
		return "";
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
	 * @param recusive 是否递归遍历子目录
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
	 * 连接args后输出
	 * @param args
	 * @return 
	 */
	public static String concat( Object... args ) {
		StringBuffer sb = new StringBuffer() ; 
		for( Object o : args ){
			sb.append(o.toString()) ;
		}
		return sb.toString();
	}
	
	
		
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

