package bos.tis.biztrace.upload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;

import bos.tis.biztrace.utils.Helper;

import com.caucho.hessian.client.HessianProxyFactory;

public class FileUploaderClient {
	//Hessian服务的url
	//private static final String url = "http://10.242.107.130:8080/tis/FileUploadService";
	private static final String url = "http://localhost:8081/monitor/FileUploadService";
	public static final FileUploaderClient instance = new FileUploaderClient() ;
	
	public void upload(File file) {
		//File zipFile = new File(localZipFilePath);
		//String savePath = zipFile.getParent() + "/" + zipFile.getName()+".zip";
		
		//将文件压缩
		//Helper.compasss(localZipFilePath, savePath);
		
		//创建HessianProxyFactory实例
		HessianProxyFactory factory = new HessianProxyFactory();
		try {
			//获得Hessian服务的远程引用
			FileUploadServiceI uploader = (FileUploadServiceI) factory.create(FileUploadServiceI.class, url);
			//读取需要上传的文件
			InputStream data = new BufferedInputStream(new FileInputStream(file));
			
			uploader.upload(file.getName(), data);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

//		File saveFile = new File(savePath);
//		if(saveFile.exists()){
//			saveFile.delete();
//		}
				     
	}

}
