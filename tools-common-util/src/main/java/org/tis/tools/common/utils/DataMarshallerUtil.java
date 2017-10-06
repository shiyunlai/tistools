/**
 * 
 */
package org.tis.tools.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tis.tools.common.utils.marshaller.IDataMarshaller;
import org.tis.tools.common.utils.marshaller.JdkDataMarshaller;
import org.tis.tools.common.utils.marshaller.XStreamDataMarshaller;

/**
 * <pre>
 * 数据序列化工具
 * 
 * 可通过启动脚本中增加 -DIBSMarshallerType=xStreamMarshaller 
 * 来指定使用哪种序列化处理器,目前系统支持以下三种序列化：
 * xStreamMarshaller - 使用XStream的序列化实现{@link XStreamDataMarshaller}
 * jdkMarshaller     - 使用JDK的序列化，反序列化机制实现的数据序列化{@link JdkDataMarshaller}
 * eosMarshaller     - 使用EOS序列化实现{@link EosDataMarshaller}
 * 
 * </pre>
 */
public class DataMarshallerUtil {

	/**
	 * <pre>
	 * 设置序列化类型的系统启动参数名称
	 * 如：
	 * -DMARSHALLER="jdk"
	 * -DMARSHALLER="xstream"
	 * </pre>
	 */
	public static final String IBSMarshallerType = "MARSHALLER";

	/**
	 * 数据序列化方式：jdk实现
	 */
	public static final String MARSHALLER_TYPE_JDK = "jdk";
	
	/**
	 * 数据序列化方式：xstream实现
	 */
	public static final String MARSHALLER_TYPE_XSTREAM = "xstream";

	private static Logger logger = LoggerFactory.getLogger(DataMarshallerUtil.class);
	private static String marshallerType = MARSHALLER_TYPE_JDK;// 默认采用JDK
	private static IDataMarshaller marshaller = null;// 默认使用
	private static Map<String, IDataMarshaller> marshallerMaps = new HashMap<String, IDataMarshaller>(); // 指定使用

	public static IDataMarshaller getDataMarshaller() {

		if (marshaller == null) {

			String fixedType = System.getProperty(IBSMarshallerType, marshallerType);

			logger.info("Use the["+fixedType+"]data marshaller for system by -DMARSHALLER=?！") ;

			if (MARSHALLER_TYPE_XSTREAM.equals(fixedType)) {

				marshaller = new XStreamDataMarshaller();
			} else if (MARSHALLER_TYPE_JDK.equals(fixedType)) {

				marshaller = new JdkDataMarshaller();
			} else {

				marshaller = new XStreamDataMarshaller();
			}
		}

		return marshaller;
	}

	/**
	 * 指定Marshaller
	 * 
	 * @param marshallerType
	 *            null/DataMarshallerFactory.EOS/DataMarshallerFactory.JDK/
	 *            DataMarshallerFactory.XSTREAM，如果不指定，则统一使用 -DIBSMarshallerType
	 *            所指定的方式
	 * @return
	 */
	public static IDataMarshaller getDataMarshaller(String marshallerType) {

		if (null == marshallerType || "".equals(marshallerType)) {

			return getDataMarshaller();
		}

		else if (MARSHALLER_TYPE_JDK.equals(marshallerType)) {

			if (!marshallerMaps.containsKey(MARSHALLER_TYPE_JDK)) {
				marshallerMaps.put(MARSHALLER_TYPE_JDK, new JdkDataMarshaller());
			}

			return marshallerMaps.get(MARSHALLER_TYPE_JDK);
		}

		else if (MARSHALLER_TYPE_XSTREAM.equals(marshallerType)) {

			if (!marshallerMaps.containsKey(MARSHALLER_TYPE_XSTREAM)) {
				marshallerMaps.put(MARSHALLER_TYPE_XSTREAM, new XStreamDataMarshaller());
			}

			return marshallerMaps.get(MARSHALLER_TYPE_XSTREAM);
		}

		else {

			return getDataMarshaller();
		}
	}

	public static void setDataMarshaller(IDataMarshaller _marshaller) {
		if (_marshaller != null) {
			marshaller = _marshaller;
		}
	}
}
