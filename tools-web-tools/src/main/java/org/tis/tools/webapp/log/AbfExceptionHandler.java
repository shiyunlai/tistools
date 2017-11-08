package org.tis.tools.webapp.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.webapp.exception.WebAppException;

import javax.servlet.http.HttpServletRequest;
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
    @ResponseBody
    @ExceptionHandler(ToolsRuntimeException.class)
    public Map<String, Object> handleServiceRequestError(ToolsRuntimeException ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, Object> map = new HashMap<>();
        map.put(RETCODE, ex.getCode());
        map.put(STATUS, ERROR);
        map.put(RETMESSAGE, ex.getMessage());
        logger.error(request.getPathInfo() + "服务异常-ToolsRuntimeException :", ex);
        return map;
    }

    /**
     * WebApp层异常
     */
    @ResponseBody
    @ExceptionHandler(WebAppException.class)
    public Map<String, Object> handleWebAppException(WebAppException ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, Object> map = new HashMap<>();
        map.put(RETCODE, ex.getCode());
        map.put(STATUS, ERROR);
        map.put(RETMESSAGE, ex.getMessage());
        logger.error(request.getPathInfo() + "应用异常-WebAppException :", ex);
        return map;
    }

    /**
     * 请求异常处理
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Map<String, Object> handleUnexpectedServerError(Exception ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, Object> map = new HashMap<>();
        map.put(RETCODE, "SYS_0001");
        map.put(STATUS, ERROR);
        map.put(RETMESSAGE, ex.getMessage());
        logger.error(request.getPathInfo() + "请求异常-UnexpectedServerError :", ex);
        return map;
    }
}
