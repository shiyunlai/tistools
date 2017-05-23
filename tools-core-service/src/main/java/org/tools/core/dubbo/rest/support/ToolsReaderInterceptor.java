/**
 * 
 */
package org.tools.core.dubbo.rest.support;

import java.io.IOException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

/**
 * 
 * ReaderInterceptor 拦截 MessageBodyReader.readFrom 可以用来实现校验
 * 
 * @author megapro
 *
 */
public class ToolsReaderInterceptor implements WriterInterceptor {

	/* (non-Javadoc)
	 * @see javax.ws.rs.ext.WriterInterceptor#aroundWriteTo(javax.ws.rs.ext.WriterInterceptorContext)
	 */
	@Override
	public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
		
		System.out.println("here is ToolsReaderInterceptor.aroundWriteTo() ");
	}

}
