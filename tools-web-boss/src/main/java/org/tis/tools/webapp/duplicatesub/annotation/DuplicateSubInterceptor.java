/**
 * 
 */
package org.tis.tools.webapp.duplicatesub.annotation;

import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * <pre>
 * 防止重复提交过滤器
 * 
 * 使用说明：
 * 
 * 在spring.xml 中增加拦截器配置
 * 
 * &ltbean class="org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping">
 * 	&ltproperty name="interceptors">
 * 		&ltlist>
 * 			&ltbean class="org.tis.tools.webapp.duplicatesub.annotation.DuplicateSubInterceptor"/>
 * 			&ltbean class="org.tis.tools.....***Interceptor"/>
 * 		&lt/list>
 * 	&lt/property>
 * &lt/bean>
 * 
 * 或
 * 
 * &lt!-- 拦截器配置 -->
 * &lt mvc:interceptors >
 * 	&lt!-- 配置Token拦截器，防止用户重复提交数据 -->
 * 	&lt mvc:interceptor >
 * 		&lt mvc:mapping path = "/**" />
 * 		&lt bean class = "org.tis.tools.webapp.duplicatesub.annotation.DuplicateSubInterceptor" />
 * 	&lt/ mvc:interceptor >
 * &lt/ mvc:interceptors >
 * 
 * </pre>
 * 
 * @author megapro
 *
 */
public class DuplicateSubInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(DuplicateSubInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (handler instanceof HandlerMethod) {

			HandlerMethod handlerMethod = (HandlerMethod) handler;
			Method method = handlerMethod.getMethod();
			DuplicateSub annotation = method.getAnnotation(DuplicateSub.class);

			if (annotation != null) {

				boolean needSaveSession = annotation.needSaveToken();
				if (needSaveSession) {
					request.getSession(false).setAttribute("token", UUID.randomUUID().toString());
				}

				boolean needRemoveSession = annotation.needRemoveToken();
				if (needRemoveSession) {
					if (isRepeatSubmit(request)) {
						return false;
					}
					request.getSession(false).removeAttribute("token");
				}
			}
			return true;
		} else {
			return super.preHandle(request, response, handler);
		}
	}

	private boolean isRepeatSubmit(HttpServletRequest request) {
		//如果当前用户的Session中不存在Token(令牌)，则用户是重复提交了表单
		String serverToken = (String) request.getSession(false).getAttribute("token");
		if (serverToken == null) {
			return true;
		}
		
		//如果用户提交的表单数据中没有token，则用户是重复提交了表单
		String clinetToken = request.getParameter("token");
		if (clinetToken == null) {
			return true;
		}
		
		//存储在Session中的Token(令牌)与表单提交的Token(令牌)不同，则用户是重复提交了表单
		if (!serverToken.equals(clinetToken)) {
			return true;
		}
		return false;
	}
}
