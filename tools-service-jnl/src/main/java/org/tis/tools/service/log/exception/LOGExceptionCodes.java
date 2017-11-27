package org.tis.tools.service.log.exception;

public class LOGExceptionCodes {
    /**
     * 异常码前缀（分配给sys模块）： SYS
     */
    private static final String R_EX_PREFIX = "LOG";
    /**
     * 以烤串方式拼接异常码
     * @param code 业务域范围内的异常编码
     * @return
     */
    private static String R_EX_CODE(String code) {
        return R_EX_PREFIX + "-" + code;
    }

    /**
     * 异常：找不到对应的对象.<br>
     */
    public static final String NOT_FOUND_CORRESPONDING_ENTITY = R_EX_CODE("0001");

}
