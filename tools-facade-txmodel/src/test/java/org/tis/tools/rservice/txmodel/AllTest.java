/**
 * 
 */
package org.tis.tools.rservice.txmodel;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.tis.tools.rservice.txmodel.message.impl.TxHeaderImplTest;

/**
 * tools-facade-txmodel模块的单元测试总集成类
 * 
 * @author megapro
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	TxModelConstantsTest.class, 
	TxHeaderImplTest.class 
})
public class AllTest {

}
