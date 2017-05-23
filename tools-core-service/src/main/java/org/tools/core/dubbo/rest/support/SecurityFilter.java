/**
 * 
 */
package org.tools.core.dubbo.rest.support;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * <pre>
 * ContainerRequestFilter 服务器端请求处理之前，一般用于取请求参数做一些处理，比如记录access log,流量控制，权限校验 等。
 * 常用的几个点：
 * 使用 @Context 获取 HttpServletRequest 等servlet内置对象。
 * 和标准的web filter一样,Spring @Autowired 无法使用，必须通过 WebApplicationContext 获取Spring管理的bean。
 * 数据传递使用 SecurityContext (本人能力有限，没找到更好的方式)。
 * 直接返回结果 requestContext.abortWith(response)；特别注意，调用此方法后，如果继续有其他code，下边的code一样会执行的。(java语言本身的限制)。
 * </pre>
 * 
 * @author megapro
 *
 */
//@Component
//public class SecurityFilter implements ContainerRequestFilter {
//
//	@Context
//    private transient HttpServletRequest servletRequest;
//    
//    private SellerSecurityService sellerSecurityService;
//    
//    private AccessLogService accessLogService;
//    
//    private SellerPvService  sellerPvService;
//    
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//
//		Date now = new Date();
//
//		String appKey = StringUtils.isBlank(servletRequest.getParameter("appkey")) ? "" : servletRequest.getParameter("appkey"); 
//		
//		if (StringUtils.isEmpty(appKey)) {
//			Response response = bulidUnauthResponse(Constant.missAppKeyResponse);
//			requestContext.abortWith(response);
//			return;
//		}
//
//		getService();
//
//		AccessLog accessLog = new AccessLog();
//
//		accessLogService.log(accessLog);
//
//		SecurityContext securityContext = bulidSecurityContext("test");
//
//		requestContext.setSecurityContext(securityContext);
//	}
//    
//	public static SecurityContext bulidSecurityContext(final String value) {
//		return new SecurityContext() {
//			@Override
//			public boolean isUserInRole(String role) {
//				return false;
//			}
//
//			@Override
//			public boolean isSecure() {
//				return false;
//			}
//
//			@Override
//			public Principal getUserPrincipal() {
//				return null;
//			}
//
//			@Override
//			public String getAuthenticationScheme() {
//				return value;
//			}
//		};
//	}
//
//	private Response bulidUnauthResponse(String context) {
//		return Response.ok().status(Constant.unAuthCode).entity(context).build();
//	}
//
//	public void getService() {
//
//		if (sellerSecurityService != null) {
//			return;
//		}
//
//		WebApplicationContext wac = WebApplicationContextUtils
//				.getWebApplicationContext(servletRequest.getServletContext());
//		sellerSecurityService = wac.getBean(SellerSecurityService.class);
//		accessLogService = wac.getBean(AccessLogService.class);
//		sellerPvService = wac.getBean(SellerPvService.class);
//	}
//}
