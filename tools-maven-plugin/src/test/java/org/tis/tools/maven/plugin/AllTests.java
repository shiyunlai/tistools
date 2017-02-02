package org.tis.tools.maven.plugin;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.tis.tools.maven.plugin.freemarker.ext.HumpClassNameTest;
import org.tis.tools.maven.plugin.gendao.ermaster.ERMasterModelByDom4jTest;
import org.tis.tools.maven.plugin.gendao.ermaster.ERMasterModelTest;
import org.tis.tools.maven.plugin.utils.CommonUtilTest;
import org.tis.tools.maven.plugin.utils.FreeMarkerUtilTest;
import org.tis.tools.maven.plugin.utils.KeyWordUtilTest;
import org.tis.tools.maven.plugin.utils.Xml22BeanUtilTest;

/**
 * bronsp-maven-plugin 工程的测试套件
 * @author megapro
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
		HumpClassNameTest.class,
		CommonUtilTest.class,
		FreeMarkerUtilTest.class,
		KeyWordUtilTest.class,
		ERMasterModelTest.class,
		ERMasterModelByDom4jTest.class,
		Xml22BeanUtilTest.class
	})
public class AllTests {

}
