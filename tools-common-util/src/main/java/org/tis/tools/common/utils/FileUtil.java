/**
 * 
 */
package org.tis.tools.common.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.ant.DirectoryScanner;

import java.io.*;

/**
 * 文件处理工具类.<BR>
 *
 */

public final class FileUtil {

	/**
	 * 因为不需要实例，所以构造函数为私有<BR>
	 * 参见Singleton模式<BR>
	 *
	 * Only one instance is needed,so the default constructor is private<BR>
	 * Please refer to singleton design pattern.
	 */
	private FileUtil() {
		super();
	}
	/**
	 * 创建一个文件，绝对路径
	 * @param name
	 * @return
	 * @throws IOException
	 */
	public static boolean createNewFile(String name) throws IOException{
		File file = new File(name);
		return createNewFile(file);
	}
	/**
	 * 创建一个文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static boolean createNewFile(File file) throws IOException{
		if(file.exists()){
			return true;
		}
		File parentfile = file.getParentFile();
		if(!parentfile.exists()){
			parentfile.mkdirs();
		}
		file.createNewFile();
		return true;
	}

	/**
	 * 将指定的二进制内容写入到文件中<BR>
	 *
	 * Write the bytes to a file<BR>.
	 *
	 * @param content the content for output.
	 * @param fileName the target file for output.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeFile(final byte[] content, final String fileName) throws IOException {
		File t_Dir = new File(FilenameUtils.getFullPath(fileName));

		if (!t_Dir.exists() && !t_Dir.mkdirs()) {
			throw new IOException("The parent path is not valid.");
		}
		//Make sure the parent directory valid.<BR>
		FileUtils.writeByteArrayToFile(new File(fileName), content);
	}

	/**
	 * Write string to file.
	 *
	 * @param string the string
	 * @param filename the filename
	 *
	 * @throws Exception the exception
	 */
	public static void writeFile(String string, String filename) throws Exception {
		FileUtils.writeStringToFile(new File(filename), string);
	}

	/**
	 * Write string to file in the specified charSet.
	 *
	 * @param string the string
	 * @param filename the filename
	 *
	 * @throws Exception the exception
	 */
	public static void writeFile(String string, String filename, String charSet) throws Exception {
		FileUtils.writeStringToFile(new File(filename), string, charSet);
	}

	/**
	 * 从指定的ClassLoader中得到指定路径的资源，并按照指定的编码格式转换成字符串。<BR>
	 *
	 * @param classLoader the class loader
	 * @param resourcePath the filename
	 * @param charSet the char set
	 *
	 * @return the string
	 *
	 * @throws Exception the exception
	 */
	public static String classResourceToString(ClassLoader classLoader, String resourcePath, String charSet) throws IOException {
		return IOUtils.toString(classLoader.getResourceAsStream(resourcePath), charSet);
	}

	/**
	 * To string from file.
	 *
	 * @param filename the filename
	 *
	 * @return the string
	 *
	 * @throws Exception the exception
	 */
	public static String readFile(String filename) throws Exception {
		return readFile(filename, OsUtil.getDefaultEncoding());
	}

	/**
	 * To string from file.
	 *
	 * @param filename the filename
	 * @param charSet the char set
	 *
	 * @return the string
	 *
	 * @throws Exception the exception
	 */
	public static String readFile(String filename, String charSet) throws Exception {
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(filename);
			return IOUtils.toString(inputStream, charSet);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 将指定目录中的子目录及文件都复制到另外一个指定目录中。<BR>
	 * 所有的目录都将被复制，但文件会根据传入的过滤器进行判断。<BR>
	 *
	 * Copy a directory to another directory.<BR>
	 * All the directory and the children directories will be copied.<BR>
	 * The files will be copied or not depeneds on the file filter.<BR>
	 * If the file filter is null,all the files will be copied.<BR>
	 *
	 * @param sourceDirectory the r_ source directory
	 * @param targetDirectory the r_ target directory
	 * @param filter the r_ filter
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyDirectory(File sourceDirectory, File targetDirectory, FileFilter filter) throws IOException {
		if (null == sourceDirectory || (!sourceDirectory.exists())) {
			return;
		}

		//
		// create the directory if necessary...
		//
		if (!targetDirectory.exists() && !targetDirectory.mkdirs()) {
			throw new IOException("DirCopyFailed");
		}

		File[] t_Files = sourceDirectory.listFiles();
		//
		// and then all the items below the directory...
		//
		for (int i = 0; i < t_Files.length; ++i) {
			File t_File = t_Files[i];
			if (t_File.isDirectory()) {
				File t_NextDirectory = new File(targetDirectory, t_File.getName());
				copyDirectory(t_File, t_NextDirectory, filter);
			}
			else {
				if (filter == null || filter.accept(t_Files[i])) {
					copyFile(t_File, targetDirectory);
				}
			}
		}
	}

	/**
	 * 将一个文件复制成指定的文件或者复制到指定的目录下面。<BR>
	 *
	 * Copy a file to the target file or directory.<BR>
	 *
	 * @param sourceFile the r_ source file
	 * @param targetFile the r_ target file
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		//
		// if the destination is a dir, what we really want to do is create
		// a file with the same name in that dir
		//
		if (targetFile.isDirectory()) {
			targetFile = new File(targetFile, sourceFile.getName());
		}
		if(!targetFile.exists()) {
			targetFile.getParentFile().mkdirs();
			targetFile.createNewFile();
		}

		FileInputStream inputStream = null;
		OutputStream outputStream = null;

		try {
			inputStream = new FileInputStream(sourceFile);
			outputStream = new FileOutputStream(targetFile);
			IOUtils.copy(inputStream, outputStream);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
	}

	/**
	 * 如果是文件，正常删除。<BR>
	 * 如果是目录，删除该目录及该目录下面所有子目录及文件。<BR>
	 *
	 * If the resource is file,just remove it.<BR>
	 * If the resource is directory,remove it and the directories and files in it.<BR>
	 *
	 *
	 * @param file
	 * @throws IOException
	 */
	public static void deleteFile(File file) throws IOException {
		if (file.exists()) {

			if (file.isFile()) {
				file.delete();
			}
			else {
				FileUtils.deleteDirectory(file);
			}
		}
	}

	/**
	 * 创建目录，可以包含下级子目录
	 *
	 * @return　成功返回true，否则返回false
	 */
	public static boolean mkdir(String fileName) {
		return mkdir(new File(fileName));
	}

	/**
	 * 创建目录，可以包含下级子目录
	 *
	 * @return　成功返回true，否则返回false
	 */
	public static boolean mkdir(File file) {

		if (file.exists())
			return true;

		if (!file.isDirectory()) {
			file = file.getParentFile();
		}
		return file.mkdirs();
	}

	/**
	 * 文件搜索
	 *
	 * @param targetDir
	 *            目标目录
	 * @param includePatterns
	 *            include匹配模式
	 * @param excludePatterns
	 *            exclude匹配模式
	 * @return 找到的文件列表(注意：是相对于目标目录的相对路径)
	 */
	public static String[] listFiles(String targetDir, String[] includePatterns, String[] excludePatterns) {
		try {
			DirectoryScanner scan=new DirectoryScanner();
			scan.setBasedir(targetDir);
			scan.setIncludes(includePatterns);
			scan.setExcludes(excludePatterns);
			scan.scan();
			return scan.getIncludedFiles();
		} catch (Exception e) {
			return new String[0];
		}
	}
}


