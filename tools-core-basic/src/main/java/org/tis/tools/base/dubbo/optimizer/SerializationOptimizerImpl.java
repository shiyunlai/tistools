/**
 * 
 */
package org.tis.tools.base.dubbo.optimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.tis.tools.AcRole;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.po.ac.AcOperator;

import com.alibaba.dubbo.common.serialize.support.SerializationOptimizer;

/**
 * <pre>
 * kryo序列号能力时，注册可序列化的类
 * 
 * 要让Kryo和FST完全发挥出高性能，最好将那些需要被序列化的类注册到dubbo系统中
 * 如果被序列化的类中不包含无参的构造函数，则在Kryo的序列化中，性能将会大打折扣
 * 
 * 参考： https://dangdangdotcom.github.io/dubbox/serialization.html
 * </pre>
 * 
 * @author megapro
 *
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.alibaba.dubbo.common.serialize.support.SerializationOptimizer#
	 * getSerializableClasses()
	 */
	@Override
	public Collection<Class> getSerializableClasses() {

		List<Class> classes = new LinkedList<Class>();
		classes.add(AcOperator.class);
		classes.add(AcRole.class);
		classes.add(ToolsRuntimeException.class);
		return classes;
	}

}
