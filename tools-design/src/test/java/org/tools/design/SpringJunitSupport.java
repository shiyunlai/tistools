/**
 * 
 */
package org.tools.design;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * 
 * @author megapro
 *
 */
// 让单元测试运行于spring环境，保证拥有spring框架相关支持
@RunWith(SpringJUnit4ClassRunner.class)
// 加载spring容器
//多个文件时，可用{}
//@ContextConfiguration(locations = { "classpath*:/spring1.xml", "classpath*:/spring2.xml" })  
@ContextConfiguration("classpath:/META-INF/spring/dubbo-consumer.xml")
public class SpringJunitSupport {

}
