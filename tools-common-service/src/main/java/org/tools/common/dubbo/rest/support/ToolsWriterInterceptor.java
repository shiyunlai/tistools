/**
 * 
 */
package org.tools.common.dubbo.rest.support;

import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.jboss.resteasy.util.Base64.OutputStream;

/**
 * 
 * WriterInterceptor  拦截 MessageBodyWriter.writeTo 可以用来实现数据压缩
 * 
 * @author megapro
 *
 */
public class ToolsWriterInterceptor implements WriterInterceptor {

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.WriterInterceptor#aroundWriteTo(javax.ws.rs.ext.WriterInterceptorContext)
	 */
	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		
		System.out.println("here in ToolsWriterInterceptor.aroundWriteTo() ");
		
//		OutputStream outputStream = (OutputStream) context.getOutputStream();
//		context.setOutputStream(new GZIPOutputStream(outputStream));
//		context.proceed();
	}

}
