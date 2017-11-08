/**
 * 
 */
package org.tis.tools.common.utils.marshaller;

import java.io.InputStream;
import java.io.OutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 
 * 使用XStream的序列化实现
 * 
 * @author megapro
 *
 */
public class XStreamDataMarshaller extends AbstractDataMarshaller {

	private XStream xstream = new XStream(new DomDriver("UTF-8"));

	@Override
	protected void doMarshal(Object data, OutputStream out, Object additional) throws Exception {
		xstream.toXML(data, out);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T> T doUnmarshal(InputStream in, Object additional) throws Exception {
		return (T) xstream.fromXML(in);
	}

}
