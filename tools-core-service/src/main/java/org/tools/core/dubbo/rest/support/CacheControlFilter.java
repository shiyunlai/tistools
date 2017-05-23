/**
 * 
 */
package org.tools.core.dubbo.rest.support;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

/**
 * 
 * ContainerResponseFilter 请求处理完之后调用，通常用作装入公共信息到 response
 * 
 * @author megapro
 *
 */
public class CacheControlFilter implements ContainerResponseFilter {

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerResponseFilter#filter(javax.ws.rs.container.ContainerRequestContext, javax.ws.rs.container.ContainerResponseContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {

		System.out.println("here is ContainerResponseFilter.filter()");
		
		if (requestContext.getMethod().equals("GET")) {
			responseContext.getHeaders().add("Tx-Control", "someValueGET");
        }
		
		if (requestContext.getMethod().equals("POST")) {
			responseContext.getHeaders().add("Tx-Control", "someValuePOST");
        }
		
	}

}
