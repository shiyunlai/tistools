/**
 * 
 */
package org.tis.tools.model.def;

/**
 * 
 * OM业务域的常量定义
 * 
 * @author megapro
 *
 */
public interface OMConstants {
	
	/////////////////////////////////////////////////
	
	/** 机构状态：正常 */
	public static final String ORG_STATUS_RUNNING = "running";
	/** 机构状态：注销 */
	public static final String ORG_STATUS_CANCEL = "cancel";
	/** 机构状态：停用 */
	public static final String ORG_STATUS_STOP = "stop";
	

	/////////////////////////////////////////////////
	
	/** 岗位状态：正常 */
	public static final String POSITION_STATUS_RUNNING = "running";
	/** 岗位状态：注销 */
	public static final String POSITION_STATUS_CANCEL = "cancel";

	
	/////////////////////////////////////////////////
	
	/** 工作组状态：正常 */
	public static final String GROUP_STATUS_RUNNING = "running";
	/** 工作组状态：注销 */
	public static final String GROUP_STATUS_CANCEL = "cancel";
	
	/////////////////////////////////////////////////
	
	/** 人员状态 :  在招*/
	public static final String EMPLOYEE_STATUS_OFFER = "offer";
	/** 人员状态 :  在职*/
	public static final String EMPLOYEE_STATUS_ONJOB = "onjob";
	/** 人员状态 :  离职*/
	public static final String EMPLOYEE_STATUS_OFFJOB = "offjob";

	/** 节点类型 : 虚拟节点*/
	public static final String BUSIORG_NODE_TYPE_DUMMY = "dummy";
	/** 节点类型 : 实体节点*/
	public static final String BUSIORG_NODE_TYPE_REALITY = "reality";

}
