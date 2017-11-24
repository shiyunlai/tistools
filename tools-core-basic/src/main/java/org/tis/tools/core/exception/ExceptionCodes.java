/**
 * 
 */
package org.tis.tools.core.exception;


/**
 * 
 * core模块的异常码定义.</br>
 * 范围： core-0000 ~ core-9999
 * 
 * @author megapro
 *
 */
public class ExceptionCodes {

	/**
	 * 异常码前缀（分配给core模块）
	 */
	private static final String R_EX_PREFIX = "CORE";
	
	/**
	 * 异常：执行成功.<br>
	 */
	public static final int successCode = 200 ;

	/**
	 * 500 - Internal Server Error 服务器遇到了意料不到的情况，不能完成客户的请求。.<br>
	 */
	public static final int errorCode = 500;

	/**
	 * 异常： 应用权限不足
	 */
	public static final String APP_PERMISSION_DENIED = "AUTH-4440";

	/**
	 * 异常： 功能权限不足
	 */
	public static final String FUNC_PERMISSION_DENIED = "AUTH-4441";

	/**
	 * 异常： 操作权限不足
	 */
	public static final String BHV_PERMISSION_DENIED = "AUTH-4442";

	/**
	 * 异常：系统执行失败.<br>
	 */
	public static final String SYSTEM_PROCESS_FAILURE = R_EX_CODE("9999");
	
	/**
	 * 异常：查找类路径下的META-INF资源错误.<br>
	 */
	public static final String FIND_META_INF_RESOURCE_ERROR = R_EX_CODE("0001");

	/**
	 * 异常：新增数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_INSERT = R_EX_CODE("0003");

	/**
	 * 异常：修改数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_DELETE = R_EX_CODE("0004");

	/**
	 * 异常：修改数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_UPDATE = R_EX_CODE("0005");
	/**
	 * 异常：查询数据时，对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_QUERY = R_EX_CODE("0006");

	/**
	 * 异常：新增数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_INSERT = R_EX_CODE("0007");

	/**
	 * 异常：删除数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_DELETE = R_EX_CODE("0008");
	/**
	 * 异常：修改数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_UPDATE = R_EX_CODE("0009");
	/**
	 * 异常：查找数据时，数据不全.<br>
	 */
	public static final String LACK_PARAMETERS_WHEN_QUERY = R_EX_CODE("0010");

	/**
	 * 异常：新增项.<br>
	 */
	public static final String FAILURE_WHEN_INSERT = R_EX_CODE("0011");

	/**
	 * 异常：删除项.<br>
	 */
	public static final String FAILURE_WHEN_DELETE = R_EX_CODE("0012");

	/**
	 * 异常：修改项.<br>
	 */
	public static final String FAILURE_WHEN_UPDATE = R_EX_CODE("0013");

	/**
	 * 异常：查询项.<br>
	 */
	public static final String FAILURE_WHEN_QUERY = R_EX_CODE("0014");

	/**
	 * 异常：.<br>
	 */
	public static final String DUPLICATE_WHEN_INSERT = R_EX_CODE("0015");

	/**
	 * 异常：查询项.<br>
	 */
	public static final String NOT_FOUND_WHEN_UPDATE= R_EX_CODE("0016");
	/**
	 * 异常：查询项.<br>
	 */

	public static final String NOT_FOUND_WHEN_QUERY = R_EX_CODE("0017");

	/**
	 * 用户ID已经存在
	 */
	public static final String OBJECT_IS_ALREADY_EXIST  = R_EX_CODE("0018");

	/**
	 * 异常：对象不能为空.<br>
	 */
	public static final String NOT_ALLOW_NULL_WHEN_CALL = R_EX_CODE("0019");
	/**
	 * 异常：对象不能为空.<br>
	 */
	public static final String FAILURE_WHEN_CALL = R_EX_CODE("0020");

	public static final String NOT_FOUND_WHEN_DELETE = R_EX_CODE("0021");
	
	/**
	 * 以烤串方式拼接异常码
	 * @param code 业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
