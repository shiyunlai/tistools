package bos.tis.biztrace.upload;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;



import bos.tis.lpctools.util.HelperUtil;

public class FileUploadServiceImpl implements FileUploadServiceI{

	@Override
	public void upload(String filename, InputStream data) {
		
		String remotepath = "E:/fileUpload/" + filename;
				
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			//获取客户端传递的InputStream
			bis = new BufferedInputStream(data);
			//创建文件输出流
			bos = new BufferedOutputStream(new FileOutputStream(remotepath));
			byte[] buffer = new byte[8192];
			int r = bis.read(buffer, 0, buffer.length);
			while (r > 0) {
				bos.write(buffer, 0, r);
			    r = bis.read(buffer, 0, buffer.length);
			}
			//System.out.println("-------文件上传成功！-------------");
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (bos != null) {
				try {
					bos.close();
			    } catch (IOException e) {
			        throw new RuntimeException(e);
			    }
			 }
			 if (bis != null) {
				 try {
					 bis.close();
			     } catch (IOException e) {
			         throw new RuntimeException(e);
			     }
			 }
		}
		
	}
	
}
