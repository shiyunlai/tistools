package org.tis.tools.rservice.ac.exception;

import org.tis.tools.base.exception.ToolsRuntimeException;

public class EntityManagementException extends ToolsRuntimeException {
    private static final long serialVersionUID = 1L;

    public EntityManagementException() {}

    public EntityManagementException(String code) {
        super(code);
    }

    public EntityManagementException(String code, Object[] placeholders) {
        super(code,placeholders) ;
    }

    public EntityManagementException(String code, Object[] params, String message) {
        super(code,params,message) ;
    }

    public EntityManagementException(String code, String message) {
        super(code, message);
    }
}
