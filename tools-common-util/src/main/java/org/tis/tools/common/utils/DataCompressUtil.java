/**
 * 
 */
package org.tis.tools.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.tis.tools.common.utils.marshaller.IDataMarshaller;

/**
 * <pre>
 * 数据压缩工具
 * 对数据进行压缩，减小带宽占用，提高数据传输效率
 * </pre>
 * 
 * @author megapro
 *
 */
public class DataCompressUtil {

	/**
	 * 序列化并压缩数据对象(减小数据对象体积) 使用系统默认的序列化方式
	 * 
	 * @param transContext
	 *            需要压缩的数据对象
	 * @param marshallType
	 *            见{@link DataMarshallerFactory}
	 * @return
	 */
	public static byte[] marshalAndCompress(Object transContext, String marshallType) {

		if (null == transContext) {
			// FIXME 日志需要打
			// logger.warn("准备做数据压缩的对象为空！") ;
			return null;
		}

		IDataMarshaller marshaller = getMarshallerByType(marshallType);
		byte[] sendBytes = null;
		try {
			long beg = transContext.toString().getBytes().length;

			sendBytes = marshaller.marshal(transContext);
			sendBytes = compressData(sendBytes);
			// logger.debug("压缩前/后字节数："+beg+"/"+sendBytes.length);
		} catch (Exception e) {

			// logger.error("压缩交易上下文发生错误！",e);
			return null;
		}

		return sendBytes;
	}

	/**
	 * 解压并反序列化
	 * 
	 * @param data
	 *            - 待解压和反序列化的数据
	 * @param marshallType
	 *            - 序列化方式见{@link DataMarshallerFactory}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshalAndUncompress(byte[] data, String marshallType) {

		if (null == data) {
			// logger.warn("准备解压缩的数据为空！") ;
			return null;
		}

		try {

			data = unCompressData(data);
			IDataMarshaller marshaller = getMarshallerByType(marshallType);
			return (T) marshaller.unmarshal(data);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.debug("解压并反序列化错误！",e);
			return null;
		}
	}

	private static IDataMarshaller getMarshallerByType(String marshallType) {
		IDataMarshaller marshaller;
		if (null == marshallType || "".equals(marshallType)) {
			marshaller = DataMarshallerUtil.getDataMarshaller();
		} else {
			marshaller = DataMarshallerUtil.getDataMarshaller(marshallType);
		}
		return marshaller;
	}

	private static byte[] compressData(byte[] sendBytes) {
		long begin = System.currentTimeMillis();
		byte[] bss = sendBytes;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			GZIPOutputStream gzip = new GZIPOutputStream(baos);
			gzip.write(sendBytes);
			gzip.finish();
			gzip.close();
			bss = baos.toByteArray();
		} catch (Throwable ex) {
			// logger.error(ex);
		}
		long end = System.currentTimeMillis();
		// logger.debug("数据压缩耗时："+(end-begin));
		return bss;
	}

	private static byte[] unCompressData(byte[] receiveBytes) throws IOException {
		GZIPInputStream gzip2 = new GZIPInputStream(new ByteArrayInputStream(receiveBytes));
		byte[] buf = new byte[1024];
		int num = -1;
		ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
		while ((num = gzip2.read(buf, 0, buf.length)) != -1) {
			baos2.write(buf, 0, num);
		}
		byte[] b = baos2.toByteArray();
		baos2.flush();
		baos2.close();
		gzip2.close();

		receiveBytes = b;
		return receiveBytes;
	}

	public static void main(String[] args) {

		Date date = new Date();
		byte[] kkk = marshalAndCompress(date, DataMarshallerUtil.MARSHALLER_TYPE_JDK);
	}
}
