package org.tis.tools.rservice.ac.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

/**
 * Created by zhaoch on 2017/7/16.
 */
public class MenuManagementException extends ToolsRuntimeException {
    private static final long serialVersionUID = 1L;

    public MenuManagementException() {}

    public MenuManagementException(String code) {
        super(code);
    }

    public MenuManagementException(String code, Object[] placeholders) {
        super(code,placeholders) ;
    }

    public MenuManagementException(String code, Object[] params, String message) {
        super(code,params,message) ;
    }

    public MenuManagementException(String code, String message) {
        super(code,message) ;
    }
}
