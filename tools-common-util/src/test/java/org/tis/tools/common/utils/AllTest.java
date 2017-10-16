/**
 * 
 */
package org.tis.tools.common.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author megapro
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
		BasicUtilTest.class,
		BeanFieldValidateUtilTest.class,
		DirectoryUtilTest.class,
		CryptographyUtilTest.class,
		PinyinUtilTest.class,
		SimpleSequenceUtilTest.class,
		TimeUtilsTest.class
})
public class AllTest {

}
