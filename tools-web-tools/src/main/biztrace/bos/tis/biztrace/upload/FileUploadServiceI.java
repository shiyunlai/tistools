package bos.tis.biztrace.upload;

import java.io.InputStream;

public interface FileUploadServiceI {
	public void upload(String filename, InputStream data);
}
