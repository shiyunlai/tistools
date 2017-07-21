/**
 * 
 */
package org.tis.tools.service.om.exception;

/**
 * <pre>
 * OM模块的异常码定义.</br>
 * 范围： OM-0000 ~ OM-9999
 * </pre>
 * 
 * @author megapro
 *
 */
public class OMExceptionCodes {

	/**
	 * 异常码前缀（分配给core模块）： om
	 */
	private static final String R_EX_PREFIX = "OM";

	/**
	 * 缺少生成机构代码所需的参数
	 */
	public static final String LAKE_PARMS_FOR_GEN_ORGCODE = R_EX_CODE("0001");

	/**
	 * 机构代码对应的机构不存在
	 */
	public static final String ORGANIZATION_NOT_EXIST_BY_ORG_CODE = R_EX_CODE("0002");

	/**
	 * 参数不允许为空
	 */
	public static final String PARMS_NOT_ALLOW_EMPTY = R_EX_CODE("0003");

	/**
	 * 新增根节点机构失败
	 */
	public static final String FAILURE_WHRN_CREATE_ROOT_ORG = R_EX_CODE("0004");

	/**
	 * 拷贝机构失败
	 */
	public static final String FAILURE_WHRN_COPY_ORG = R_EX_CODE("0005");

	/**
	 * 深度拷贝机构失败
	 */
	public static final String FAILURE_WHRN_DEEP_COPY_ORG = R_EX_CODE("0006");

	/**
	 * 机构出于正常状态，无需执行启用处理
	 */
	public static final String ORG_IS_RUNNING_NEEDLESS_ENABLED = R_EX_CODE("0007");

	/**
	 * 机构状态不在停用状态，不能执行启用
	 */
	public static final String ORG_IS_NOT_STOP_WHEN_ENABLED = R_EX_CODE("0008");

	/**
	 * 启用机构时，生效日期和失效日期范围无效
	 */
	public static final String INVALID_DATE_SCOPE_WHEN_ENABLED = R_EX_CODE("0008");

	/**
	 * 机构状态非停用，不能删除
	 */
	public static final String FAILURE_WHEN_DEL_MUST_STOP = R_EX_CODE("0009");

	/**
	 * 非空机构，不能删除
	 */
	public static final String FAILURE_WHEN_DEL_MUST_EMPTY_ORG = R_EX_CODE("0010");

	/**
	 * 新增子节点机构失败
	 */
	public static final String FAILURE_WHEN_CREATE_CHILD_ORG = R_EX_CODE("0011");

	/**
	 * 更新父节点机构失败
	 */
	public static final String FAILURE_WHRN_UPDATE_PARENT_ORG = R_EX_CODE("0012");

	/**
	 * 机构状态不能直接通过修改而更新
	 */
	public static final String FAILURE_WHRN_UPDATE_ORG_STATUS = R_EX_CODE("0013");

	/**
	 * 更新机构信息失败
	 */
	public static final String FAILURE_WHEN_UPDATE_ORG_APP = R_EX_CODE("0014");
	/**
	 * 缺少创建员工的必要信息
	 */
	public static final String LAKE_PARMS_FOR_CREATE_EMPLOYEE = R_EX_CODE("0015");
	/**
	 * 员工代码对应的员工不存在
	 */
	public static final String EMPANIZATION_NOT_EXIST_BY_EMP_CODE = R_EX_CODE("0016");
	/**
	 * 员工状态不能直接通过修改而更新
	 */
	public static final String FAILURE_WHEN_UPDATE_EMP_STATUS = R_EX_CODE("0017");
	/**
	 * 不能删除在职员工
	 */
	public static final String FAILURE_WHEN_DEL_NOT_ONJOB = R_EX_CODE("0018");
	/**
	 * 删除员工失败
	 */
	public static final String FAILURE_WHEN_DEL_EMP= R_EX_CODE("0019");
	/**
	 * 不存在此岗位代码FAILURE_WHEN_UPDATE_POS_STATUS
	 */
	public static final String POSITANIZATION_NOT_EXIST_BY_POSIT_CODE= R_EX_CODE("0020");
	/**
	 * 不能直接修改机构状态
	 */
	public static final String FAILURE_WHEN_UPDATE_POS_STATUS= R_EX_CODE("0021");

	/**
	 * 以烤串方式拼接异常码
	 * 
	 * @param code
	 *            业务域范围内的异常编码
	 * @return
	 */
	private static String R_EX_CODE(String code) {
		return R_EX_PREFIX + "-" + code;
	}
}
