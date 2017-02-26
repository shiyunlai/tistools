/**
 * 
 */
package org.tis.tools.service.biztrace.helper;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 
 * Spring 上下文工具类
 * 
 * @author megapro
 *
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext; // Spring应用上下文环境
	
	private SpringContextUtil(){
		
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		SpringContextUtil.applicationContext = applicationContext ; 
	}

	public static ApplicationContext getApplicationContext() {
        return applicationContext;
	}


	/**
	 * 根据id从Spring上下文中取Bean
	 * @param beanName
	 * @return
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String beanName) throws BeansException {
		
		return (T) applicationContext.getBean(beanName);
	}
	
	/**
	 * 根据id从Spring上下文中取Bean
	 * @param beanName
	 * @param requiredType
	 * @return
	 * @throws BeansException
	 */
	public static <T> T getBean(String beanName, Class<T> requiredType ) throws BeansException {

		return applicationContext.getBean(beanName, requiredType) ; 
	}
	
}
