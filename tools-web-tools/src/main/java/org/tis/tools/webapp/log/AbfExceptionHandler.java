package org.tis.tools.webapp.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.tis.tools.base.exception.ToolsRuntimeException;

import java.util.HashMap;
import java.util.Map;

import static org.tis.tools.webapp.util.AjaxUtils.*;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class AbfExceptionHandler {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 服务异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler(ToolsRuntimeException.class)
    @ResponseBody
    public Map<String, Object> handleInvalidRequestError(ToolsRuntimeException ex) {
        Map<String, Object> map = new HashMap<>();
        map.put(RETCODE, ex.getCode());
        map.put(STATUS, ERROR);
        map.put(RETMESSAGE, ex.getMessage());
        logger.error("ToolsRuntimeException : ", ex);
        return map;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, Object> handleUnexpectedServerError(Exception ex) {
        Map<String, Object> map = new HashMap<>();
        map.put(RETCODE, "SYS_0001");
        map.put(STATUS, ERROR);
        map.put(RETMESSAGE, ex.getMessage());
        logger.error("UnexpectedServerError : ", ex);
        return map;
    }
}
