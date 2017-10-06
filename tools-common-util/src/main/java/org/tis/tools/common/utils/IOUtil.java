/**
 * 
 */
package org.tis.tools.common.utils;


import java.io.File;

import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;


import java.util.LinkedHashMap;
import java.util.Map;

import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipEntry;


/**
 * 
 * 与IO操作相关的内容都放到这个类中<BR>
 * 是对Jarkar Commons-IO中的IOUitls的补充。<BR>
 * 
 * @see org.apache.commons.io#IOUtils
 */

public final class IOUtil {

	private static final String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);

	public static final FileFilter LIST_FILE = new FileFilter() {

		public boolean accept(File file) {
			if (file == null) {
				return false;
			}
			if (!file.isDirectory()) {
				return true;
			}
			return false;
		}
	};

	/**
	 * 因为不需要实例，所以构造函数为私有<BR>
	 * 参见Singleton模式<BR>
	 * 
	 * Only one instance is needed,so the default constructor is private<BR>
	 * Please refer to singleton design pattern.
	 */
	public IOUtil() {
		super();
	}


	@SuppressWarnings("unchecked")
	public static Map listAllFileNames(File base) {
		return listAllFileNames(base, "");
	}

	@SuppressWarnings("unchecked")
	private static Map listAllFileNames(File base, String prefix) {
		if (!base.canRead() || !base.isDirectory()) {
			throw new IllegalArgumentException(base.getAbsolutePath());
		}
		Map map = new LinkedHashMap();
		File[] hits = base.listFiles();
		for (int i = 0; i < hits.length; i++) {
			File hit = hits[i];
			if (hit.canRead()) {
				if (hit.isDirectory()) {
					map.putAll(listAllFileNames(hit, prefix.equals("") ? hit
							.getName() : prefix + "/" + hit.getName()));
				} else {
					map.put(prefix.equals("") ? hit.getName() : prefix + "/"
							+ hit.getName(), hit);
				}
			}
		}
		map.put(prefix, base);
		return map;
	}

	/**
	 * 安静的关闭
	 * 
	 * @param ioObj
	 */
	public  static void closeQuietly(Object ioObj) {
		if (ioObj == null) {
			return;
		}
		
		if (ioObj instanceof Closeable) {
			try {
				((Closeable)ioObj).close();
				return;
			} catch (Throwable ignore) {
			}
		} else {
			try {
				Method method = ioObj.getClass().getMethod("close", new Class[0]);
				if (method != null) {
					method.invoke(ioObj, new Object[0]);
					return;
				}				
			} catch (Throwable ignore) {
			}
			throw new IllegalArgumentException("ioObj'" + ioObj.getClass() + "' is not support type!");
		}
	}

	/**
	 * 是否是绝对路径（根据当前系统判断）
	 * 
	 * @param path 路径
	 * @return true:是
	 */
	public  static boolean isAbsolutePath(String path) {
		if (path == null || path.trim().length() == 0) {
			throw new IllegalArgumentException("path is null!");
		}
		path = normalizeInUnixStyle(path);		
	    if (isWindowsAndDos()) {
	    	int colon = path.indexOf(":/");
	        return Character.isLetter(path.charAt(0)) && colon == 1;
	    } else {
	    	return path.charAt(0) == '/';
	    }
	}
	
	private  static boolean isWindowsAndDos() {
		if (OS_NAME.indexOf("windows") > -1) {
			return true;
		}
		if (OS_NAME.indexOf("dos") > -1) {
			return true;
		}
		if (OS_NAME.indexOf("netware") > -1) {
			return true;
		}
		return false;
	}

	/**
	 * 取得文件名
	 * 
	 * @param file 文件路径
	 * @param isWithFileExtension 是否包含文件扩展名
	 * @return 文件名
	 */
	public  static String getFileName(String file, boolean isWithFileExtension) {
		if (file == null || file.trim().length() == 0) {
			throw new IllegalArgumentException("file is null!");
		}
		file = file.trim();
		int index = file.lastIndexOf('/');
		int index2 = file.lastIndexOf('\\');
		if (index2 > index) {
			index = index2;
		}
		
		String fileName = file;
		if (index != -1) {
			fileName = file.substring(index + 1);
		}
		if (!isWithFileExtension) {
			index = fileName.lastIndexOf('.');
			if (index != -1) {
				fileName = fileName.substring(0, index);
			}
		}
		
		return fileName.trim();
	}

	/**
	 * 取得文件扩展名
	 * 
	 * @param file 文件路径
	 * @return 文件扩展名
	 */
	public  static String getFileExtension(String file) {
		if (file == null || file.trim().length() == 0) {
			return null;
		}
		file = file.trim();
		int index = file.lastIndexOf('.');
		if (index != -1) {
			return file.substring(index + 1).trim();
		}
		return null;
	}

	/**
	 * 路径是否匹配
	 * 
	 * @param patternPath 路径模式
	 * @param path 路径
	 * @param isCaseSensitive 是否大小写敏感
	 * @return true:是
	 */
	public  static boolean isMatch(String patternPath, String path, boolean isCaseSensitive) {
		return MatchUtil.match(patternPath, path, isCaseSensitive);
	}

	/**
	 * 得到相对路径<br>
	 * 
	 * 如c:/abc/de/a.txt相对于c:/abc的相对路径是de/a.txt<BR>
	 * 
	 * @param rootPath
	 * @param resourcePath
	 * @return
	 */
	public  static String getRelativePath(String rootPath, String resourcePath) {
		if (rootPath == null || rootPath.trim().length() == 0) {
			throw new IllegalArgumentException("rootPath is null!");
		}
		if (resourcePath == null || resourcePath.trim().length() == 0) {
			throw new IllegalArgumentException("resourcePath is null!");
		}
		rootPath = normalizeInUnixStyle(rootPath);
		resourcePath = normalizeInUnixStyle(resourcePath);
		if (!resourcePath.startsWith(rootPath)) {
			throw new IllegalArgumentException("rootPath'" + rootPath + "' is not resourcePath'" + resourcePath + "' root!");
		}
		String relativePath = resourcePath.substring(rootPath.length());
		if (relativePath.length() > 0 && relativePath.charAt(0) == '/') {
			relativePath = relativePath.substring(1);
		}
		return relativePath;
	}	

	/**
	 * 转化为Unix风格的标准格式路径<br>
	 * 
	 * 如com//sun\jnlp\<BR>
	 * 就会变成com/sun/jnlp。<BR>
	 * 
	 * @param path 路径
	 * @return
	 */
	public  static String normalizeInUnixStyle(String path) {
		if (path == null || path.trim().length() == 0) {
			throw new IllegalArgumentException("path is null!");
		}
		path = path.trim();
		String[] tokens = toPathTokens(path);
		StringBuilder buf = new StringBuilder();
		for (int i = tokens.length - 1; i >= 0; i--) {
			if (tokens[i].equals(".")) {
				continue;
			}
			if (tokens[i].equals("..")) {
				if (i > 0) {
					i--;
					continue;
				}
			}
			buf.insert(0, "/" + tokens[i]);
		}
		if (path.charAt(0) == '/' || path.charAt(0) == '\\') {
			if (buf.length() > 0) {
				return buf.toString();
			} else {
				return "/";
			}			
		} else {
			if (buf.length() > 0) {
				return buf.substring(1);
			} else {
				return ".";
			}			
		}	
	}
	
	private  static String[] toPathTokens(String path) {
		List<String> tokenList = new ArrayList<String>();
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < path.length(); i++) {
			String ele = path.substring(i, i + 1);
			if (ele.equals("/") || ele.equals("\\")) {
				String token = buf.toString().trim();
				if (token.length() > 0) {
					tokenList.add(token);
				}
				buf = new StringBuilder();
			} else {
				buf.append(ele);
			}
		}
		String token = buf.toString().trim();
		if (token.length() > 0) {
			tokenList.add(token);
		}
		return tokenList.toArray(new String[0]);
	}

	/**
	 * 删除文件或者目录
	 * 
	 * @param path 字符串或者java.io.File类型
	 */
	public  static boolean deleteQuietly(Object path) {
		return deleteQuietly(path, false);
	}
	
	/**
	 * 删除文件或者目录
	 * 
	 * @param path
	 * @param isDeleteEmptyParent 是否删除空的父目录
	 */
	public  static boolean deleteQuietly(Object path, boolean isDeleteEmptyParent) {
		if(path == null) {
			return true;
		}
		File deleteFile = null;
		if (path instanceof String) {
			if (((String)path).trim().length() == 0) {
				return true;
			}
			deleteFile = new File((String)path);
		} else if (path instanceof File) {
			deleteFile = (File)path;
		} else {
			throw new IllegalArgumentException("file'" + path.getClass() + "' is not support type!");
		}
		
		try {
			File parent = deleteFile.getParentFile();
			boolean result = doDeleteQuietly(deleteFile);
			
			if (isDeleteEmptyParent) {
				boolean res = doDeleteEmptyParentQuietly(parent);
				if (result) {
					result = res;
				}
			}
			return result;
		} catch (Throwable ignore) {
			return false;
		}
	}
	
	//向下删除
	private  static boolean doDeleteQuietly(File file) {
		try {
			boolean result = true;
			if (file.isDirectory()) {
				for (File aFile : file.listFiles()) {
					boolean res = doDeleteQuietly(aFile);	
					if (result) {
						result = res;
					}
				}
			}
			file.delete();
			return result;
		} catch (Throwable ignore) {
			return false;
		}
	}
	
	//向上删除
	private  static boolean doDeleteEmptyParentQuietly(File parent) {
		try {
			if (parent == null) {
				return true;
			}
			boolean result = true;
			File parentFile = parent.getParentFile();
			if (parent.list().length == 0) {
				parent.delete();
				boolean res = doDeleteEmptyParentQuietly(parentFile);
				if (result) {
					result = res;
				}
			}
			return result;
		} catch (Throwable ignore) {
			return false;
		}
	}
	
	/**
	 * 列出目录下的所有文件和目录
	 * 
	 * @param dir
	 * @param filter
	 * @return
	 */
	public  static List<File> listFiles(File dir, FileFilter filter) {
		if (dir == null) {
			throw new IllegalArgumentException("dir is null!");
		}
		if (!dir.exists()) {				
			throw new IllegalArgumentException("Path'" + dir.getAbsolutePath() + "' is not existed!");
		}
		if (dir.isFile()) {
			throw new IllegalArgumentException("Path'" + dir.getAbsolutePath() + "' is file, not dir!");
		}
		List<File> fileList = new ArrayList<File>();
		doListFiles(dir, fileList, filter);
		return fileList;
	}
	
	private static void doListFiles(File dir, List<File> fileList, FileFilter filter) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				doListFiles(file, fileList, filter);
			}
			if (filter != null && !filter.accept(file)) {
				continue;
			}
			fileList.add(file);
		}
	}
	
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;	
	
	/**
	 * 输入流和输出流的拷贝
	 * 
	 * @param input 输入
	 * @param output 输出
	 * @param bufferSize 缓冲区大小
	 * @return 拷贝字节数
	 * @throws IOException
	 */
	public  static long copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
		if (input == null) {
			throw new IllegalArgumentException("InputStream is null!");
		}
		if (output == null) {
			throw new IllegalArgumentException("OutputStream is null!");
		}
		if (bufferSize <= 0) {
			bufferSize = DEFAULT_BUFFER_SIZE;
		}
		//使用nio
		ReadableByteChannel readChannel = Channels.newChannel(input);
		WritableByteChannel writeChannel = Channels.newChannel(output);
		ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
		long count = 0;
	    while(readChannel.read(buffer) != -1) {
	      buffer.flip(); // Prepare for writing
	      writeChannel.write(buffer);
	      count += buffer.position();
	      buffer.clear();  // Prepare for reading
	    }
		return count;
	}
	
	/**
	 * 文件拷贝(如果有相同的文件，会覆盖掉)；目标路径如果不存在，会自动创建
	 * 
	 * @param srcPath 源路径
	 * @param destPath 目标路径
	 * @param srcFileFilter 源路径过滤规则
	 * @param preserveFileDate 是否保留文件时间戳
	 * @throws IOException
	 */
	public  static void copy(File srcPath, File destPath, FileFilter srcFileFilter, boolean preserveFileDate) throws IOException {
		if (srcPath == null) {
			throw new IllegalArgumentException("srcPath is null!");
		}
		if (!srcPath.exists()) {
			throw new IllegalArgumentException("srcPath'" + srcPath.getAbsolutePath() + "' is not existed!");
		}
		if (destPath == null) {
			throw new IllegalArgumentException("destPath is null!");
		}
		//如果两个路径相同，则不需要处理
		if (srcPath.equals(destPath)) {
			return;
		}
		//如果目标路径是源路径的子目录
		if (normalizeInUnixStyle(destPath.getAbsolutePath()).startsWith(normalizeInUnixStyle(srcPath.getAbsolutePath()))) {
			throw new IllegalArgumentException("cannot copy '" + srcPath.getAbsolutePath() + "' to '" + destPath.getAbsolutePath() + "', because srcPath is the parent of destPath.");
		}
		if (!destPath.exists()) {
			destPath.mkdirs();
		}
		if (destPath.isFile()) {
			if (srcPath.isFile()) {				
				doCopyFile(srcPath, destPath, srcFileFilter, preserveFileDate);
			} else {				
				throw new IllegalArgumentException("srcPath'" + srcPath.getAbsolutePath() + "' is dir, destPath'" + srcPath.getAbsolutePath() + "' is file!");
			}
		} else {
			if (srcPath.isFile()) {
				if (srcFileFilter != null) {
					if (!srcFileFilter.accept(srcPath)) {
						return;
					}
				}
				doCopyFile(srcPath, new File(destPath, srcPath.getName()), srcFileFilter, preserveFileDate);
			} else {
				List<File> files = listFiles(srcPath, LIST_FILE);
				String basePath = normalizeInUnixStyle(srcPath.getAbsolutePath());
				for (File srcFile : files) {
					String relativePath = getRelativePath(basePath, srcFile.getAbsolutePath());
					File destFile = new File(destPath, relativePath);
					if (!destFile.getParentFile().exists()) {
						destFile.getParentFile().mkdirs();
					}
					doCopyFile(srcFile, destFile, srcFileFilter, preserveFileDate);
				}
			}
		}
	}
	
	private  static void doCopyFile(File srcFile, File destFile, FileFilter srcFileFilter, boolean preserveFileDate) throws IOException {
		if (srcFileFilter != null) {
			if (!srcFileFilter.accept(srcFile)) {
				return;
			}
		}
		FileInputStream input = null;
		FileOutputStream output = null;
		try {					
			input = new FileInputStream(srcFile);
			output = new FileOutputStream(destFile);
			copy(input, output, DEFAULT_BUFFER_SIZE);
			if (preserveFileDate) {
				destFile.setLastModified(srcFile.lastModified());
			}
		} finally {
			closeQuietly(input);
			closeQuietly(output);
		}
	}

	/**
	 * 文件移动(如果有相同的文件，会覆盖掉)；目标路径如果不存在，会自动创建
	 * 
	 * @param srcPath 源路径
	 * @param destPath 目标路径
	 * @param srcFileFilter 源路径过滤规则
	 * @throws IOException
	 */
	public  static void move(File srcPath, File destPath, FileFilter srcFileFilter) throws IOException {
		if (srcPath == null) {
			throw new IllegalArgumentException("srcPath is null!");
		}
		if (!srcPath.exists()) {
			throw new IllegalArgumentException("srcPath'" + srcPath.getAbsolutePath() + "' is not existed!");
		}
		if (destPath == null) {
			throw new IllegalArgumentException("destPath is null!");
		}
		//如果两个路径相同，则不需要处理
		if (srcPath.equals(destPath)) {
			return;
		}
		//如果目标路径是源路径的子目录
		if (normalizeInUnixStyle(destPath.getAbsolutePath()).startsWith(normalizeInUnixStyle(srcPath.getAbsolutePath()))) {
			throw new IllegalArgumentException("cannot copy '" + srcPath.getAbsolutePath() + "' to '" + destPath.getAbsolutePath() + "', because srcPath is the parent of destPath.");
		}
		if (!destPath.exists()) {
			destPath.mkdirs();
		}
		if (destPath.isFile()) {
			if (srcPath.isFile()) {				
				doMoveFile(srcPath, destPath, srcFileFilter);
			} else {				
				throw new IllegalArgumentException("srcPath'" + srcPath.getAbsolutePath() + "' is dir, destPath'" + srcPath.getAbsolutePath() + "' is file!");
			}
		} else {
			if (srcPath.isFile()) {
				if (srcFileFilter != null) {
					if (!srcFileFilter.accept(srcPath)) {
						return;
					}
				}
				doMoveFile(srcPath, new File(destPath, srcPath.getName()), srcFileFilter);
			} else {
				List<File> files = listFiles(srcPath, LIST_FILE);
				String basePath = normalizeInUnixStyle(srcPath.getAbsolutePath());
				for (File srcFile : files) {
					String relativePath = getRelativePath(basePath, srcFile.getAbsolutePath());
					File destFile = new File(destPath, relativePath);
					if (!destFile.getParentFile().exists()) {
						destFile.getParentFile().mkdirs();
					}
					doMoveFile(srcFile, destFile, srcFileFilter);
				}
			}
		}
	}
	
	private  static void doMoveFile(File srcFile, File destFile, FileFilter srcFileFilter) throws IOException {
		if (srcFileFilter != null) {
			if (!srcFileFilter.accept(srcFile)) {
				return;
			}
		}
		if (destFile.exists()) {
			destFile.delete();
		}
		srcFile.renameTo(destFile);
	}

	/**
	 * 压缩文件
	 * 
	 * @param inputPath 文件或者目录
	 * @param zipFile 压缩后的文件
	 * @param inputFileFilter 源路径过滤规则
	 * @param isUpdate 是否更新压缩文件，否则新建压缩文件
	 * @throws IOException
	 */
	public  static void zip(File inputPath, File zipFile ) throws IOException {
		ZipUtil.zip(inputPath, zipFile);
	}
	
/**	
	public  static void zip(File inputPath, File zipFile, FileFilter inputFileFilter, boolean isUpdate) throws IOException {
		if (inputPath == null) {
			throw new IllegalArgumentException("inputPath is null!");
		}
		if (!inputPath.exists()) {
			throw new IllegalArgumentException("inputPath'" + inputPath.getAbsolutePath() + "' is not existed!");
		}
		if (zipFile == null) {
			throw new IllegalArgumentException("zipFile is null!");
		}
		//如果zipFile在inputPath下
		if (normalizeInUnixStyle(zipFile.getAbsolutePath()).startsWith(normalizeInUnixStyle(inputPath.getAbsolutePath()))) {
			throw new IllegalArgumentException("cannot zip '" + inputPath.getAbsolutePath() + "' to '" + zipFile.getAbsolutePath() + "', because inputPath is the parent of zipFile.");
		}
		File bakZipFile = null;
		if (!zipFile.exists()) {
			zipFile.createNewFile();
		} else {
			if (zipFile.isDirectory()) {
				throw new IllegalArgumentException("zipFile'" + zipFile.getAbsolutePath() + "' is dir, not file!");
			}
			if (isUpdate) {
				bakZipFile = new File(zipFile.getParentFile(), zipFile.getName() + ".bak");
				zipFile.renameTo(bakZipFile);
			}
		}
		
		ZipOutputStream output = null;
		HashSet<String> entrys = new HashSet<String>();
		try {
			output = new ZipOutputStream(new FileOutputStream(zipFile));
			if (inputPath.isDirectory()) {
				String basePath = inputPath.getAbsolutePath();
				List<File> files = listFiles(inputPath, LIST_FILE);

				Iterator<?> iterator = files.iterator();
				while (iterator.hasNext()) {
					File file = (File) iterator.next();
					if (inputFileFilter != null) {
						if (!inputFileFilter.accept(file)) {
							continue;
						}
					}
					String relativePath = getRelativePath(basePath, file.getAbsolutePath());
					if (relativePath.charAt(0) == '\\' || relativePath.charAt(0) == '/') {
						relativePath = relativePath.substring(1);
					}
					FileInputStream input = null;
					try {						
						ZipEntry ze = new ZipEntry(relativePath);
						entrys.add(ze.getName());
						ze.setTime(file.lastModified());
						output.putNextEntry(ze);
						input = new FileInputStream(file);
						copy(input, output, DEFAULT_BUFFER_SIZE);
					} finally {
						closeQuietly(input);
					}
				}
			} else {
				if (inputFileFilter != null) {
					if (!inputFileFilter.accept(inputPath)) {
						return;
					}
				}
				FileInputStream input = null;
				try {
					ZipEntry ze = new ZipEntry(inputPath.getName());
					entrys.add(ze.getName());
					ze.setTime(inputPath.lastModified());
					output.putNextEntry(ze);
					
					input = new FileInputStream(inputPath);		
					copy(input, output, DEFAULT_BUFFER_SIZE);					
				} finally {
					closeQuietly(input);
				}
			}
			
			if (bakZipFile != null) {
				ZipFile file = new ZipFile(bakZipFile);
				try {					
					Enumeration<ZipEntry> es = (Enumeration<ZipEntry>)file.entries();
					while (es.hasMoreElements()) {
						ZipEntry ze = (ZipEntry) es.nextElement();
						if (entrys.contains(ze.getName())) {
							continue;
						}
						output.putNextEntry(ze);
						copy(file.getInputStream(ze), output, DEFAULT_BUFFER_SIZE);
					}
				} finally {
					closeQuietly(file);
					deleteQuietly(bakZipFile);
				}				
			}
		} finally {
			closeQuietly(output);
		}
	}
*/
	
	/**
	 * 解压文件
	 * 
	 * @param zipFile 压缩文件
	 * @param outputDir 解压后的目录
	 * @param zipEntryFilter 过滤规则
	 * @throws IOException
	 */
	public  static void unzip(File zipFile, File outputDir ) throws IOException {
		ZipUtil.unzip(zipFile, outputDir);
	}
	
/**	public  static void unzip(File zipFile, File outputDir, IZipEntryFilter zipEntryFilter) throws IOException {
		if (zipFile == null) {
			throw new IllegalArgumentException("zipFile is null!");
		}
		if (!zipFile.exists()) {
			throw new IllegalArgumentException("zipFile'" + zipFile.getAbsolutePath() + "' is not existed!");
		}
		if (outputDir == null) {
			throw new IllegalArgumentException("outputDir is null!");
		}	
		if (!outputDir.exists()) {
			outputDir.mkdirs();
		}
		if (outputDir.isFile()) {
			throw new IllegalArgumentException("outputDir'" + outputDir.getAbsolutePath() + "' is file, not dir!");
		}
		
		ZipFile file = new ZipFile(zipFile);
		try {
			Enumeration<ZipEntry> es = (Enumeration<ZipEntry>)file.entries();
			while (es.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) es.nextElement();
				if (zipEntryFilter != null) {
					if (!zipEntryFilter.accept(entry)) {
						continue;
					}
				}
				File destFile = new File(outputDir, entry.getName());
				if (entry.isDirectory()) {
					if (!destFile.exists()) {
						destFile.mkdirs();
					}
				} else {
					if (!destFile.getParentFile().exists()) {
						destFile.getParentFile().mkdirs();
					}
					if (!destFile.exists()) {
						destFile.createNewFile();
					}
					InputStream input = null;
					OutputStream output = null;
					try {
						input = file.getInputStream(entry);
						output = new FileOutputStream(destFile);
						copy(input, output, DEFAULT_BUFFER_SIZE);					
					} finally {
						closeQuietly(input);
						closeQuietly(output);
					}
				}
			}
		} finally {
			closeQuietly(file);
		}
	}
*/
	
	/**
	 * 列出压缩文件中的所有文件和目录
	 * 
	 * @param zipFile 压缩文件
	 * @param zipEntryFilter 过滤规则
	 * @return
	 */
//	public  static List<ZipEntry> listZipEntrys(File zipFile, IZipEntryFilter zipEntryFilter) throws IOException {
	public  static List<ZipEntry> listZipEntrys(File zipFile ) throws IOException {
		if (zipFile == null) {
			throw new IllegalArgumentException("zipFile is null!");
		}
		if (!zipFile.exists()) {
			throw new IllegalArgumentException("zipFile'" + zipFile.getAbsolutePath() + "' is not existed!");
		}
		if (zipFile.isDirectory()) {
			throw new IllegalArgumentException("zipFile'" + zipFile.getAbsolutePath() + "' is dir, not file!");
		}
		ZipFile file = new ZipFile(zipFile);
		try {
			List<ZipEntry> fileList = new ArrayList<ZipEntry>();
			Enumeration<ZipEntry> es = (Enumeration<ZipEntry>)file.entries();
			while (es.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) es.nextElement();
//				if (zipEntryFilter != null) {
//					if (!zipEntryFilter.accept(entry)) {
//						continue;
//					}
//				}
				fileList.add(entry);
			}
			return fileList;
		} finally {
			closeQuietly(file);
		}
	}

	/**
	 * 取得资源
	 * 
	 * @param clazz 类名
	 * @param resource 资源名
	 * @return
	 */
	public  static URL getResource(Class clazz, String resource) {
		if (resource == null || resource.trim().length() == 0) {
			throw new IllegalArgumentException("resource is null!");
		}
		URL url = null;
		if (clazz != null) {
			url = clazz.getResource(resource);
			if (url == null) {
				url = clazz.getClassLoader().getResource(resource);
			}			
		}
		if (url == null) {
			url = Thread.currentThread().getContextClassLoader().getResource(resource);
		}
		
		return url;
	}
	/**
	 * 取得所有资源
	 * 
	 * @param clazz 类名
	 * @param resource 资源名
	 * @return
	 */

	public  static URL[] getAllResources(Class clazz, String resource) {
		if (resource == null || resource.trim().length() == 0) {
			throw new IllegalArgumentException("resource is null!");
		}
		ArrayList<URL> urlList = new ArrayList<URL>();
		if (clazz != null) {
			URL url = clazz.getResource(resource);
			if (url != null) {
				urlList.add(url);
			}
			try {
				Enumeration<URL> urls = clazz.getClassLoader().getResources(resource);	
				while(urls.hasMoreElements()) {
					urlList.add(urls.nextElement());
				}
			} catch (Throwable ignore) {				
			}			
		}
		try {
			Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(resource);	
			while(urls.hasMoreElements()) {
				urlList.add(urls.nextElement());
			}
		} catch (Throwable ignore) {				
		}	
		return urlList.toArray(new URL[0]);
	}

	/**
	 * 读取文件到内存
	 * 
	 * @param file
	 * @return
	 */
	public  static byte[] read(File file) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("file is null!");
		}
		if (!file.exists()) {
			throw new IllegalArgumentException("file'" + file.getAbsolutePath() + "' is not existed!");
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException("zipFile'" + file.getAbsolutePath() + "' is dir, not file!");
		}
		
		FileInputStream fileIn = null;
		try {
			fileIn = new FileInputStream(file);
			long size = file.length();
			if (size > Integer.MAX_VALUE) {
				throw new IllegalArgumentException("File is too big:" + size);
			}
			return read(fileIn);
		} finally {
			closeQuietly(fileIn);
		}
	}
	
	/**
	 * 读取流到内存
	 * 
	 * @param input
	 * @return
	 */
	public  static byte[] read(InputStream input) throws IOException {
		if (input == null) {
			throw new IllegalArgumentException("InputStream is null!");
		}
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		copy(input, byteOut, 1024 * 4);
		return byteOut.toByteArray();
	}

	/**
	 * 写入文件
	 * 
	 * @param file
	 * @param bytes
	 * @param isAppend 是否写入文件最后，否则覆写文件内容
	 */
	public  static void write(File file, byte[] bytes, boolean isAppend) throws IOException {
		if (file == null) {
			throw new IllegalArgumentException("file is null!");
		}
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
		}
		if (file.isDirectory()) {
			throw new IllegalArgumentException("zipFile'" + file.getAbsolutePath() + "' is dir, not file!");
		}
		if (bytes == null || bytes.length == 0) {
			return;
		}
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(file, isAppend);
			fileOut.write(bytes);
			fileOut.flush();
		} finally {
			closeQuietly(fileOut);
		}
	}
	
	/**
	 * 字符集转换
	 * 
	 * @param bytes 字节数组，不可以为空
	 * @param fromCharsetName bytes的字符集，如果为空，则是系统默认编码
	 * @param toCharsetName 指定字符集，不可以为空
	 * @return 转换后的字节数组
	 * 
	 * @throws UnsupportedEncodingException 不支持的字符集
	 */
	public  static byte[] toCharSet(byte[] bytes, String fromCharsetName, String toCharsetName) throws UnsupportedEncodingException {
		if (bytes == null) {
			throw new IllegalArgumentException("bytes is null!");
		}
		if (toCharsetName == null || toCharsetName.trim().length() == 0) {
			throw new IllegalArgumentException("toCharsetName is null!");
		}
		if (fromCharsetName == null || fromCharsetName.trim().length() == 0) {
			fromCharsetName = System.getProperty("file.encoding");
		}
		CharBuffer charBuf = null;
		if (fromCharsetName == null || fromCharsetName.trim().length() == 0) {
			charBuf = ByteBuffer.wrap(bytes).asCharBuffer();
		} else {
			charBuf = Charset.forName(fromCharsetName).decode(ByteBuffer.wrap(bytes));
		}
		return Charset.forName(toCharsetName).encode(charBuf).array();
	}

	/**
	 * 字符集转换
	 * 
	 * @param str 字符串，不可以为空
	 * @param charsetName 指定字符集，不可以为空
	 * @return 转换后的字符串
	 * @throws UnsupportedEncodingException 不支持的字符集
	 */
	public  static String toCharSet(String str, String charsetName) throws UnsupportedEncodingException {
		if (str == null || str.trim().length() == 0) {
			throw new IllegalArgumentException("str is null!");
		}
		if (charsetName == null || charsetName.trim().length() == 0) {
			throw new IllegalArgumentException("charsetName is null!");
		}
		return new String(Charset.forName(charsetName).encode(CharBuffer.wrap(str)).array(), charsetName);
	}


}
