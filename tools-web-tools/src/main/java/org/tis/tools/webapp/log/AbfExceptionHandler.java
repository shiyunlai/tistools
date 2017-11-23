package org.tis.tools.webapp.log;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthorizedException;
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
     * 权限异常
     */
    @ResponseBody
    @ExceptionHandler(ShiroException.class)
    public Map<String, Object> handleShiroException(ShiroException ex) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String msg ;
        String status;
        String code;
        if (ex instanceof IncorrectCredentialsException) {
            code = "AUTH-440";
            status = ERROR;
            msg = "密码错误，连续五次错误帐号会被锁定！";
        } else if (ex instanceof ExcessiveAttemptsException) {
            code = "AUTH-445";
            status = ERROR;
            msg = "达到最大错误次数，请联系管理员或稍后再试！";
        } else if (ex instanceof UnauthorizedException) {
            code = "AUTH-403";
            status = FORBID;
            msg = "没有当前功能或行为的权限！";
        } else {
            code = "AUTH-444";
            status = ERROR;
            msg = StringUtils.isBlank(ex.getMessage()) ? ex.getMessage() : ex.getCause().getMessage();
        }
        Map<String, Object> map = new HashMap<>();
        map.put(RETCODE, code);
        map.put(STATUS, status);
        map.put(RETMESSAGE, msg);
        logger.error(request.getPathInfo() + "权限异常-ShiroException :", ex);
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
        map.put(STATUS, FAILED);
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
