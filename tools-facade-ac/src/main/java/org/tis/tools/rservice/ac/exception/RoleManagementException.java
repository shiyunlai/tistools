package org.tis.tools.rservice.ac.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

public class RoleManagementException extends ToolsRuntimeException {
    private static final long serialVersionUID = 1L;

    public RoleManagementException() {}

    public RoleManagementException(String code) {
        super(code);
    }

    public RoleManagementException(String code, Object[] placeholders) {
        super(code,placeholders) ;
    }

    public RoleManagementException(String code, Object[] params, String message) {
        super(code,params,message) ;
    }

    public RoleManagementException(String code, String message) {
        super(code,message) ;
    }
}
