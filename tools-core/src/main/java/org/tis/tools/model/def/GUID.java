/**
 * 
 */
package org.tis.tools.model.def;

import org.tis.tools.common.utils.SequenceSimpleUtil;

/**
 * 
 * 各表中数据主键（GUID）的标识定义及基础能力实现
 * 
 * @author megapro
 *
 */
public class GUID {

	/**
	 * 取一个机构表（OM_ORG）的数据主键值
	 * 
	 * @return
	 */
	public static String org() {
		return SequenceSimpleUtil.instance.GUID("ORG");
	}
	
	/**
	 * 取一个工作组表（OM_GROUP）的数据主键值
	 * 
	 * @return
	 */
	public static String group() {
		return SequenceSimpleUtil.instance.GUID("GROUP");
	}
	
	/**
	 * 取一个业务机构表（OM_BUSIORG）的数据主键值
	 * 
	 * @return
	 */
	public static String busiorg() {
		return SequenceSimpleUtil.instance.GUID("BUSIORG");
	}
	
	/**
	 * 取一个员工表（OM_EMPLOYEE）的数据主键值
	 * 
	 * @return
	 */
	public static String employee() {

		return SequenceSimpleUtil.instance.GUID("EMPLOYEE");
	}
	
	/**
	 * 取一个岗位表（OM_POSITION）的数据主键值
	 * @return
	 */
	public static String position(){
		
		return SequenceSimpleUtil.instance.GUID("POSITION");
	}
	
	/**
	 * 取一个职务定义表（OM_DUTY）的数据主键值
	 * @return
	 */
	public static String duty(){

		return SequenceSimpleUtil.instance.GUID("DUTY");
	}

	
	///////////////////////////////////////////////////////
	
	/**
	 * 取一个角色表（AC_ROLE）的数据主键值
	 * 
	 * @return
	 */
	public static String role() {

		return SequenceSimpleUtil.instance.GUID("ROLE");
	}
	
	/**
	 * 取一个应用表（AC_APP）的数据主键值
	 * 
	 * @return
	 */
	public static String app() {
		
		return SequenceSimpleUtil.instance.GUID("APP");
	}
	
	/**
	 * 取一个实体表（AC_ENTITY）的数据主键值
	 * 
	 * @return
	 */
	public static String entity() {

		return SequenceSimpleUtil.instance.GUID("ENTITY");
	}

	/**
	 * 取一个实体属性表（AC_ENTITYFIELD）的数据主键值
	 * 
	 * @return
	 */
	public static String entityField() {

		return SequenceSimpleUtil.instance.GUID("ENTITYFIELD");
	}
	
	/**
	 * 取一个数据范围权限表（AC_DATASCOPE）的数据主键值
	 * 
	 * @return
	 */
	public static String dataScope() {

		return SequenceSimpleUtil.instance.GUID("DATASCOPE");
	}
	
	/**
	 * 取一个功能组表（AC_FUNCGROUP）的数据主键值
	 * 
	 * @return
	 */
	public static String funcGroup() {

		return SequenceSimpleUtil.instance.GUID("FUNCGROUP");
	}
	
	/**
	 * 取一个功能表（AC_FUNC）的数据主键值
	 * 
	 * @return
	 */
	public static String func() {
		
		return SequenceSimpleUtil.instance.GUID("FUNC");
	}
	
	/**
	 * 取一个功能操作行为表（AC_FUNC_BEHAVIOR）的数据主键值
	 * 
	 * @return
	 */
	public static String funcBehavior() {
		
		return SequenceSimpleUtil.instance.GUID("FUNCBHV");
	}
	
	/**
	 * 取一个操作员表（AC_OPERATOR）的数据主键值
	 * 
	 * @return
	 */
	public static String operaor() {
		
		return SequenceSimpleUtil.instance.GUID("OPERATOR");
	}
	
	/**
	 * 取一个操作员身份表（AC_OPERATOR_IDENTITY）的数据主键值
	 * 
	 * @return
	 */
	public static String identity() {
		
		return SequenceSimpleUtil.instance.GUID("IDENTITY");
	}

	/**
	 * 取一个操作员重组菜单表（AC_OPERATOR_MENU）的数据主键值
	 * 
	 * @return
	 */
	public static String operatorMenu() {
		
		return SequenceSimpleUtil.instance.GUID("OMENU");
	}
	
	/**
	 * 取一个菜单表（AC_MENU）的数据主键值
	 * 
	 * @return
	 */
	public static String menu() {
		
		return SequenceSimpleUtil.instance.GUID("MENU");
	}
	
	
	///////////////////////////////////////////////////////
	
	//其他模块...
}
