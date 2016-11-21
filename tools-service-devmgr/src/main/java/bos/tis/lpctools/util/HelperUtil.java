package bos.tis.lpctools.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.tis.tools.common.utils.DirectoryUtil;



public class HelperUtil {
	
	/**
	 * 解析excel文件
	 * @param filePath 文件路径
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String,String>> readExcel(String filePath) throws IOException {  
		File file = new File(filePath);
		
        String fileName = file.getName();  
        String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName  
                .substring(fileName.lastIndexOf(".") + 1);  
        if ("xls".equals(extension)) {  
           return read2003Excel(file);  
        }else{       	
            throw new IOException(fileName+"为不支持的文件类型");  
        }		 
    }
	
	/**
	 * 读取excel2003类型的文件
	 * @param file
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static List<Map<String,String>> read2003Excel(File file) throws FileNotFoundException, IOException {
		//System.out.println(file.getName());
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheetAt(0);
		int counter = 0; //记录遍历的有效行数
		
		List<Map<String,String>> sheetList = new ArrayList<Map<String,String>>();
		for (int i = 2; counter < sheet.getPhysicalNumberOfRows()-2; i++) {
			HSSFRow row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            } else {  
                counter++;  
            } 
            
            //判断该行是不是属于无用的行(只存了空字符串)
            HSSFCell firstCell = row.getCell(0);
            if(firstCell==null || "".equals(firstCell.getStringCellValue().trim())){
            	continue;
            }
            
            Map<String,String> rowMap = new HashMap<String,String>();
            String[] keys = {"project_name","import_type","import_name","deploy_position","code_path","version","per_version","submitter"};
            //System.out.println(row.getLastCellNum());
            for (int j = 0; j < 8; j++) {            	
            	HSSFCell cell = row.getCell(j);              	
                if (cell == null) {  
                    rowMap.put(keys[j], "");  
                }else{                	
                	rowMap.put(keys[j], cell.getStringCellValue().trim());
                }
            }
            sheetList.add(rowMap);
		}

		return sheetList;
		
	}
	
	public static List<Map<String,String>> readListContent(ByteArrayOutputStream  baos) throws IOException{
		ByteArrayInputStream bis=new ByteArrayInputStream(baos.toByteArray());
		DataInputStream dis = new DataInputStream (bis);
		
		HSSFWorkbook hwb = new HSSFWorkbook(dis);
		HSSFSheet sheet = hwb.getSheetAt(0);
		
		int counter = 0; //记录遍历的有效行数
		
		List<Map<String,String>> sheetList = new ArrayList<Map<String,String>>();
		for (int i = 2; counter < sheet.getPhysicalNumberOfRows()-2; i++) {
			HSSFRow row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            } else {  
                counter++;  
            } 
            
            //判断该行是不是属于无用的行(只存了空字符串)
            HSSFCell firstCell = row.getCell(0);
            if(firstCell==null || "".equals(firstCell.getStringCellValue().trim())){
            	continue;
            }
            
            Map<String,String> rowMap = new HashMap<String,String>();
            String[] keys = {"project_name","import_type","import_name","deploy_position","code_path","version","per_version","submitter"};
            //System.out.println(row.getLastCellNum());
            for (int j = 0; j < 8; j++) {            	
            	HSSFCell cell = row.getCell(j);              	
                if (cell == null) {  
                    rowMap.put(keys[j], "");  
                }else{                	
                	rowMap.put(keys[j], cell.getStringCellValue().trim());
                }
            }
            sheetList.add(rowMap);
		}
		return sheetList;
	}
//	public static Map<String,String> readFeatureExcel(String filePath) throws FileNotFoundException, IOException {
//		File file = new File(filePath);
//		
//		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
//		XSSFSheet sheet = xwb.getSheetAt(0);
//		int counter = 0; //记录遍历的有效行数
//		
//		Map<String,String> rowMap = new HashMap<String,String>();
//		for (int i = 1; counter < sheet.getPhysicalNumberOfRows()-1; i++) {
//			XSSFRow row = sheet.getRow(i);  
//            if (row == null) {  
//                continue;  
//            } else {  
//                counter++;  
//            } 
//            
////            //判断该行是不是属于无用的行(只存了空字符串)
////            XSSFCell firstCell = row.getCell(0);
////            if(firstCell==null || "".equals(firstCell.getNumericCellValue())){
////            	continue;
////            }
//            
//            rowMap.put(row.getCell(2).getStringCellValue().trim(), row.getCell(1).getStringCellValue().trim());
//            
//		}
//		return rowMap;
//		
//	}
	
	/**
	 * 序列化
	 * @param value	被序列化的对象
	 * @return
	 */
	public static byte[] serialize(Object value) {  
        if (value == null) {  
            throw new NullPointerException("Can't serialize null");  
        }  
        byte[] rv=null;  
        ByteArrayOutputStream bos = null;  
        ObjectOutputStream os = null;  
        try {  
            bos = new ByteArrayOutputStream();  
            os = new ObjectOutputStream(bos);  
            os.writeObject(value);  
            os.close();  
            bos.close();  
            rv = bos.toByteArray();  
        } catch (IOException e) {  
            throw new IllegalArgumentException("Non-serializable object", e);  
        } finally {  
        	try {
				os.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}    
        }  
        return rv;  
    }  
	
	/**
	 * 反序列化
	 * @param in  字节数组
	 * @return
	 */
    public static Object deserialize(byte[] in) {  
        Object rv=null;  
        ByteArrayInputStream bis = null;  
        ObjectInputStream is = null;  
        try {  
            if(in != null) {  
                bis=new ByteArrayInputStream(in);  
                is=new ObjectInputStream(bis);  
                rv=is.readObject();  
                is.close();  
                bis.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();
        } finally {  
        	try {
				is.close();
				bis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
        }  
        return rv;  
    }
    
    /**
     * 解压(.zip)
     * @param zipFileName 被解压的文件路径
     * @param descFileName	解压后的文件目录
     * @return
     */
    public static boolean unZipFiles(String zipFileName, String descFileName) {
        String descFileNames = descFileName;
        if (!descFileNames.endsWith(File.separator)) {
            descFileNames = descFileNames + File.separator;
        }        
        try {
            //System.out.println("zipFileName:"+zipFileName);
            // 根据ZIP文件创建ZipFile对象
            ZipFile zipFile = new ZipFile(new File(zipFileName)); //   解压乱码  
            ZipEntry entry = null;
            String entryName = null;
            String descFileDir = null;
            byte[] buf = new byte[4096];
            int readByte = 0;
            // 获取ZIP文件里所有的entry
            @SuppressWarnings("rawtypes")
            Enumeration enums = zipFile.entries();
            // 遍历所有entry
            while (enums.hasMoreElements()) {
                entry = (ZipEntry) enums.nextElement();
                // 获得entry的名字
                entryName = entry.getName();
                descFileDir = descFileNames + entryName;
                if (entry.isDirectory()) {
                    // 如果entry是一个目录，则创建目录
                    new File(descFileDir).mkdirs();
                    continue;
                } else {
                    // 如果entry是一个文件，则创建父目录
                    new File(descFileDir).getParentFile().mkdirs();
                }
                File file = new File(descFileDir);
                // 打开文件输出流
                OutputStream os = new FileOutputStream(file);
                // 从ZipFile对象中打开entry的输入流
                InputStream is = zipFile.getInputStream(entry);
                while ((readByte = is.read(buf)) != -1) {
                    os.write(buf, 0, readByte);
                }
                os.close();
                is.close();
            }
            zipFile.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    	
    
}
