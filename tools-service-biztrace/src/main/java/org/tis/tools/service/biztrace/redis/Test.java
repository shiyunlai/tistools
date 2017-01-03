/**
 * 
 */
package org.tis.tools.service.biztrace.redis;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author megapro
 *
 */
public class Test {

	public static void main(String[] args) {
        ApplicationContext ac =  new ClassPathXmlApplicationContext("classpat:main/resources/META-INF/spring/spring-context.xml");
        RedisClientTemplate redisClient = (RedisClientTemplate)ac.getBean("redisClientTemplate");
        redisClient.set("a", "abc");
        System.out.println(redisClient.get("a"));
    }
}
