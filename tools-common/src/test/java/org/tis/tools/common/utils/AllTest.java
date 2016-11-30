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
	BasicUtilsTest.class,
	DirectoryUtilTest.class,
	PinyinUtilTest.class,
	SimpleSequenceUtilTest.class,
	TimeUtilsTest.class
})
public class AllTest {

}
