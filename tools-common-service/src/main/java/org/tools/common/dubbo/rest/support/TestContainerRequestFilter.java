/**
 * 
 */
package org.tools.common.dubbo.rest.support;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

/**
 * 服务处理前过滤器
 * @author megapro
 *
 */
public class TestContainerRequestFilter implements ContainerRequestFilter {

	/* (non-Javadoc)
	 * @see javax.ws.rs.container.ContainerRequestFilter#filter(javax.ws.rs.container.ContainerRequestContext)
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("here is TestContainerRequestFilter.filter() ");
	}

}
