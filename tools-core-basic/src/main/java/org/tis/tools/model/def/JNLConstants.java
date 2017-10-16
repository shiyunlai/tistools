package org.tis.tools.model.def;

public interface JNLConstants {
    String OPERATE_STATUS_SUCCESS = "succ";
    String OPERATE_STATUS_FAIL = "fail";
    String OPERATE_STATUS_EXCEPTION = "exception";

    /**
     * 操作类型
     * 用于 记录操作日志功能- OperateLog注解中的操作类型 operateType
     */
    String OPEARTE_TYPE_LOGIN = "login";
    String OPEARTE_TYPE_UPDATE = "update";
    String OPEARTE_TYPE_ADD = "add";
    String OPEARTE_TYPE_DELETE = "delete";
}
