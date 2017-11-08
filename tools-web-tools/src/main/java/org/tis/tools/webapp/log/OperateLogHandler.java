package org.tis.tools.webapp.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.vo.log.LogOperateDetail;
import org.tis.tools.model.vo.log.OperateLogBuilder;
import org.tis.tools.rservice.log.capable.IOperateLogRService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import static org.tis.tools.webapp.util.AjaxUtils.RETMESSAGE;

@Component
@Aspect
public class OperateLogHandler {

    @Autowired
    IOperateLogRService logOperatorRService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private JoinPoint point;

    @Pointcut("@annotation(org.tis.tools.webapp.log.OperateLog)")
    public void methodCachePointcut() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void requestPointcut() {}

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestBody)")
    public void requestBodyPointcut() {}

    /**
     * 统一处理 LOG4J
     * 进入RequestMapping注解的controller方法前
     */
    @Before("requestPointcut()")
    public void enterController(JoinPoint point) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String reqInfo = "{}";
        for(Object arg : point.getArgs()){
            if (arg != null && arg.getClass() == String.class) {
                reqInfo = String.valueOf(arg);
            }
        }
        logger.info(" [请求] Request URL:{}; Request Method:{}; Request Body:{}", BasicUtil.wrap(request.getPathInfo(), request.getMethod(), reqInfo)) ;
    }

    @AfterReturning(value = "requestPointcut()", returning = "ret")
    public void exitController(JoinPoint point, Map<String, Object> ret) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String objStr = JSON.toJSONString(ret.get(RETMESSAGE));
        logger.info(" [响应] Request URL:{}; Response Body:{}", BasicUtil.wrap(request.getPathInfo(), objStr)) ;
    }

    /**
     * 进入OperateLog注解的controller方法前
     * @param point
     * @throws Throwable
     */
    @Before("methodCachePointcut() && @annotation(log)")
    public void enterOperateController(JoinPoint point, OperateLog log) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        OperateLogBuilder logBuilder = new OperateLogBuilder();
        logBuilder.start()
                .setOperateFrom("ABF") // FIXME 从哪设置该值
                .setUserId(StringUtils.equals(log.operateType(),"login") ? "" : session.getAttribute("userId").toString())
                .setOperatorName(StringUtils.equals(log.operateType(), "login") ? "" : session.getAttribute("operatorName").toString())
                .setOperateType(log.operateType())
                .setProcessDesc(log.operateDesc())
                .setRestfulUrl(request.getPathInfo());

        LogThreadLocal.setLogBuilderLocal(logBuilder);
    }

    /**
     * controller执行没有异常完毕后
     *
     * @param point
     */
    @AfterReturning(value = "@annotation(logAnt)", returning = "ret")
    public void logAfterExecution(JoinPoint point, Map<String, Object> ret, OperateLog logAnt) throws Throwable {
        LogOperateDetail log = LogThreadLocal.getLogBuilderLocal().getLog();
        log.setOperateResult(JNLConstants.OPERATE_STATUS_SUCCESS);

        // 添加数据变化项（LogAbfChange）到操作日志
        JSONObject reqData = new JSONObject();

        for(Object arg : point.getArgs()){
            if (arg != null &&  String.class.equals(arg.getClass())) {
                try{
                    reqData = JSONObject.parseObject(String.valueOf(arg));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        String objStr = JSON.toJSONString(ret.get(RETMESSAGE));
        if(logAnt.retType() == ReturnType.Object) {
            JSONObject jsonObject = JSONObject.parseObject(objStr);
            JSONObject changeData = reqData.getJSONObject("changeData");
            if(StringUtils.isNotBlank(logAnt.id())) {
                log.addObj()
                        .setObjGuid(jsonObject.getString(logAnt.id()))
                        .setObjName(StringUtils.isBlank(logAnt.name()) ? null : jsonObject.getString(logAnt.name()))
                        .setObjValue(objStr);
                for (String key : logAnt.keys()) {
                    log.getObj(0).addKey(key, jsonObject.getString(key));
                }
                if(changeData != null)
                    changeData.keySet().forEach(key -> log.getObj(0).addChangeItem(key, changeData.getString(key)));
            }

        } else if(logAnt.retType() == ReturnType.List) {
            JSONArray array = JSONObject.parseArray(objStr);
            JSONArray changeData = reqData.getJSONArray("changeData");
            if(StringUtils.isNotBlank(logAnt.id())) {
                for (int i = 0; i < array.size(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    log.addObj().setObjGuid(jsonObject.getString(logAnt.id())).setObjValue(jsonObject.toJSONString());
                    for (String key : logAnt.keys()) {
                        log.getObj(i).addKey(key, jsonObject.getString(key));
                    }
                    int finalI = i;
                    if(changeData != null)
                        changeData.getJSONObject(i).keySet()
                            .forEach(key ->
                                    log.getObj(finalI).addChangeItem(key, changeData.getJSONObject(finalI).getString(key)));
                }
            }
        }
        saveLogInfo();
    }

    /**
     * controller执行抛出异常完毕后
     *
     * @param point
     */
    @AfterThrowing(value = "methodCachePointcut()", throwing = "e")
    public void logAfterExecutionThrowException(JoinPoint point, Exception e) throws Throwable {
        OperateLogBuilder logBuilder = LogThreadLocal.getLogBuilderLocal();

        if(e instanceof ToolsRuntimeException) {
            logBuilder.getLog().setOperateResult(JNLConstants.OPERATE_STATUS_FAIL);
        } else {
            logBuilder.getLog().setOperateResult(JNLConstants.OPERATE_STATUS_EXCEPTION);
        }

        // 获取堆栈String
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        logBuilder.getLog().setStackTrace(sw.toString().toUpperCase());
        saveLogInfo();
        pw.close();
        sw.close();

    }

    private void saveLogInfo() {
        logOperatorRService.createOperatorLog(LogThreadLocal.getLogBuilderLocal().getLog());
    }


}
