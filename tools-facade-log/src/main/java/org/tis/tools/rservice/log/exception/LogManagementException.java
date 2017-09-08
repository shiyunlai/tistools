package org.tis.tools.rservice.log.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

public class LogManagementException extends ToolsRuntimeException {
    private static final long serialVersionUID = 1L;

    public LogManagementException() {super();}

    public LogManagementException(String code) {
        super(code);
    }

    public LogManagementException(String code, Object[] placeholders) {
        super(code,placeholders) ;
    }

    public LogManagementException(String code, Object[] params, String message) {
        super(code,params,message) ;
    }

    public LogManagementException(String code, String message) {
        super(code,message) ;
    }
}

