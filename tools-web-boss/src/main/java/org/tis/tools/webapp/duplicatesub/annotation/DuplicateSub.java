/**
 * 
 */
package org.tis.tools.webapp.duplicatesub.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 防重注解<br/>
 * 防止webapp中control方法被重复调用，用于方法上<br/>
 * 用法：<br/>
 * 1.在新建页面方法上，设置needSaveToken()为true，此时拦截器会在Session中保存一个token.<br/>,
 * 	如：<br/>
 * 	&#64DuplicateSub(needRemoveToken = true)<br/>
 * 	public synchronized ModelAndView save(ExecutionUnit unit, HttpServletRequest request, HttpServletResponse response) ...
 * 	<br/>
 * 	&#64DuplicateSub(needSaveToken = true)<br/>
 * 	public ModelAndView edit(Integer id, HttpServletRequest request) ...<br/>
 * 2.同时需要在新建的页面中添加以下内容：<br/>
 * &lt input type="hidden" name="token" value="${token}">
 * <br/>
 * 3.保存方法需要验证重复提交的，设置needRemoveToken为true
 * 此时会在拦截器中验证是否重复提交
 * </p>
 * 
 * @author megapro
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicateSub {
	
	/**
	 * （默认项）true － 在当前session中设置Token ；<br/>false － 不设置Token
	 * @return
	 */
	boolean needSaveToken() default true ; 
	
	/**
	 * （默认项）true － 检查是否重复提交，并清理Token ；<br/>false － 不检查
	 * @return
	 */
	boolean needRemoveToken() default true ; 
	
}
