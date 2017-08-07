package org.tis.tools.rservice.ac.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

public class OperatorManagementException extends ToolsRuntimeException {
    private static final long serialVersionUID = 1L;

    public OperatorManagementException() {}

    public OperatorManagementException(String code) {
        super(code);
    }

    public OperatorManagementException(String code, Object[] placeholders) {
        super(code,placeholders) ;
    }

    public OperatorManagementException(String code, Object[] params, String message) {
        super(code,params,message) ;
    }

    public OperatorManagementException(String code, String message) {
        super(code,message) ;
    }
}
