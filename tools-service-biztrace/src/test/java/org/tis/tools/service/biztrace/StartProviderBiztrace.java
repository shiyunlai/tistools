/**
 * 
 */
package org.tis.tools.service.biztrace;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.registry.Registry;
import com.alibaba.dubbo.registry.RegistryFactory;

/**
 * 
 * 测试启动类
 * 
 * 在eclipse中启动本模块时使用
 * 
 * @author megapro
 *
 */
public class StartProviderBiztrace {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		extRouter() ;
		com.alibaba.dubbo.container.Main.main(args);
	}

	private static void extRouter() {
		RegistryFactory registryFactory = ExtensionLoader.getExtensionLoader(RegistryFactory.class).getAdaptiveExtension();
        Registry registry = registryFactory.getRegistry(URL.valueOf("zookeeper://127.0.0.1:2181"));
        registry.register(URL.valueOf(
                "routers://0.0.0.0/com.alibaba.dubbo.examples.merge.api.MergeService?name=test&category=routers&router=custom&dynamic=false"));
	}

}
