/**
 * 
 */
package org.tools.design.test.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * 对 ClassUtil 的所有单元测试
 * 
 * @author megapro
 *
 */

// 使用Suite，把分散在多个工程中、且是对同一个被测对象的单元测试 集成起来
// 也可以用Suite 集成存在逻辑关联性的一组单元测试，
// 通过Suite一次执行所有相关的单元测试程序
@RunWith(Suite.class)
@SuiteClasses({ 
	org.tools.design.test.util.ClassUtilTest.class, 
	org.tis.tools.common.utils.ClassUtilTest.class 
})
public class ClassUtilAllTest {

}
