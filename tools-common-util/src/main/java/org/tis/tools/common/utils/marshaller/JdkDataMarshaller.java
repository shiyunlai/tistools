/**
 * 
 */
package org.tis.tools.common.utils.marshaller;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 使用JDK的序列化，反序列化机制实现的数据序列化
 * 
 * @author megapro
 *
 */
public class JdkDataMarshaller extends AbstractDataMarshaller {

	@Override
	protected void doMarshal(Object data, OutputStream out, Object additional) throws Exception {
		ObjectOutputStream outObj = out instanceof ObjectOutputStream ? (ObjectOutputStream) out
				: new ObjectOutputStream(out);
		outObj.writeObject(data);
		outObj.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T doUnmarshal(InputStream in, Object additional) throws Exception {
		ObjectInputStream objIn = in instanceof ObjectInputStream ? (ObjectInputStream) in : new ObjectInputStream(in);
		return (T) objIn.readObject();
	}
}
