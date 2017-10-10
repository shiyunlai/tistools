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

@RunWith(Suite.class)
@SuiteClasses({ 
	org.tools.design.test.util.ClassUtilTest.class, 
	org.tis.tools.common.utils.ClassUtilTest.class 
})
public class ClassUtilAllTest {

}
