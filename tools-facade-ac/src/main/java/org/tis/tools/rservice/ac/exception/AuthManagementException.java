package org.tis.tools.rservice.ac.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * Created by Administrator on 2017/7/20.
 */
public class AuthManagementException extends ToolsRuntimeException {
    private static final long serialVersionUID = 1L;

    public AuthManagementException() {}

    public AuthManagementException(String code) {
        super(code);
    }

    public AuthManagementException(String code, Object[] placeholders) {
        super(code,placeholders) ;
    }

    public AuthManagementException(String code, Object[] params, String message) {
        super(code,params,message) ;
    }

    public AuthManagementException(String code, String message) {
        super(code,message) ;
    }
}
