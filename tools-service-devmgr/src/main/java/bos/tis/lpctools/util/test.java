package bos.tis.lpctools.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import bos.tis.lpctools.handler.PackageHandler;

public class test {
	//private static String url = "http://48.1.1.62/svn/repos/tip/development/branches/Feature/Feature_201605051043";  
		private static String url =  "http://48.1.1.62/svn/repos/tip/development/branches/docs/A_project_development/02_development/05_patch_list/0111_SALM开发流程管理/List";
		
		//private static String url = "http://48.1.1.62/svn/repos/tip/development/branches/Feature/Feature_201605051043/bos.tis.bs/bos.tis.bs.common/src/bos/tis/bs/api/enums";
	    private static SVNRepository repository = null;  
	
	 public static void setupLibrary() {  
	        DAVRepositoryFactory.setup();  
	        SVNRepositoryFactoryImpl.setup();  
	        FSRepositoryFactory.setup();  
	        try {  
	            repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));  
	        }  
	        catch (SVNException e) {  
	            //logger.error(e.getErrorMessage(), e);  
	        }  
	        // 身份验证  
	        //ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("wuhong","1qaz2wsx");
	        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager("caodongqian","12345e");
	        repository.setAuthenticationManager(authManager);  
	    } 
	public static void main(String[] args) throws Exception {
		
		setupLibrary();
		
		 //此变量用来存放要查看的文件的属性名/属性值列表。
       SVNProperties fileProperties = new SVNProperties();
       //此输出流用来存放要查看的文件的内容。
       ByteArrayOutputStream baos = new ByteArrayOutputStream();
		repository.getFile("BOSTIS-[20150526004]-[庄壮成]-[PAD终端建设项目(PA2015069R20150611000001)].xls", -1, fileProperties, baos);
		
		String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
		System.out.println(mimeType);
		ByteArrayInputStream bis=new ByteArrayInputStream(baos.toByteArray());
		DataInputStream  is=new DataInputStream (bis);
//		Object rv=is.readObject();
		System.out.println(baos.toByteArray());
		readFeatureExcel1(is);
		// TODO 自动生成的方法存根
		//Helper.unZip("C:\\Users\\HP\\Desktop\\logs.zip", "F:\\cdq");
		
//		HelperUtil.unZipFiles("C:/Users/HP/Desktop/RTC/BS_uat_0926_pm.zip", "F:\\cdq/BS_uat_0926_pm");
		
//		PackageHandler.instance.dePackageHandle("C:/Users/HP/Desktop/RTC/BS_uat_0926_pm.zip", 
//				ParamsConfig.zipFileDecompassPath+"/BS_uat_0926_pm");
	}
	
	
	public static Map<String,String> readFeatureExcel(DataInputStream  os) throws FileNotFoundException, IOException {

		
		XSSFWorkbook xwb = new XSSFWorkbook(os);
		XSSFSheet sheet = xwb.getSheetAt(0);
		int counter = 0; //记录遍历的有效行数
		
		Map<String,String> rowMap = new HashMap<String,String>();
		for (int i = 1; counter < sheet.getPhysicalNumberOfRows()-1; i++) {
			XSSFRow row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            } else {  
                counter++;  
            } 
            
//            //判断该行是不是属于无用的行(只存了空字符串)
//            XSSFCell firstCell = row.getCell(0);
//            if(firstCell==null || "".equals(firstCell.getNumericCellValue())){
//            	continue;
//            }
            
            rowMap.put(row.getCell(2).getStringCellValue(), row.getCell(1).getStringCellValue());
            
		}
		System.out.println(rowMap);
		return rowMap;
		
	}
	
	public static Map<String,String> readFeatureExcel1(DataInputStream  os) throws FileNotFoundException, IOException {
		HSSFWorkbook hwb = new HSSFWorkbook(os);
		HSSFSheet sheet = hwb.getSheetAt(0);
		
		int counter = 0; //记录遍历的有效行数
		
		Map<String,String> rowMap = new HashMap<String,String>();
		for (int i = 1; counter < sheet.getPhysicalNumberOfRows()-1; i++) {
			HSSFRow row = sheet.getRow(i);  
            if (row == null) {  
                continue;  
            } else {  
                counter++;  
            } 
            
//            //判断该行是不是属于无用的行(只存了空字符串)
//            XSSFCell firstCell = row.getCell(0);
//            if(firstCell==null || "".equals(firstCell.getNumericCellValue())){
//            	continue;
//            }
            
            rowMap.put(row.getCell(2).getStringCellValue(), row.getCell(1).getStringCellValue());
            
		}
		System.out.println(rowMap);
		return rowMap;
	}
}
