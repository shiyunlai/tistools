package org.tis.tools.webapp.log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.tis.tools.base.exception.ToolsRuntimeException;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.rservice.log.capable.IOperateLogRService;
import org.tis.tools.model.vo.log.OperateLogBuilder;
import org.tis.tools.model.vo.log.LogOperateDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.Map;

import static org.tis.tools.webapp.util.AjaxUtils.RETMESSAGE;

@Component
@Aspect
public class OperateLogHandler {

    @Autowired
    IOperateLogRService logOperatorRService;

    @Pointcut("@annotation(org.tis.tools.webapp.log.OperatorLog)")
    public void methodCachePointcut() {}

    /**
     * 进入Controller前
     * @param point
     * @throws Throwable
     */
    @Before("methodCachePointcut() && @annotation(log)")
    public void enterController(JoinPoint point, OperatorLog log) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        OperateLogBuilder logBuilder = new OperateLogBuilder();
        logBuilder.start()
                .setOperateFrom("ABF") // FIXME 从哪设置该值
                .setUserId(session.getAttribute("userId").toString())
                .setOperatorName(session.getAttribute("operatorName").toString())
                .setOperateType(log.operateType())
                .setProcessDesc(log.operateDesc())
                .setOperateTime(new Date())
                .setRestfulUrl(request.getPathInfo());
        LogThreadLocal.setLogBuilderLocal(logBuilder);
    }

    /**
     * controller执行没有异常完毕后
     *
     * @param point
     */
    @AfterReturning(value = "@annotation(logAnt)", returning = "ret")
    public void logAfterExecution(JoinPoint point, Map<String, Object> ret, OperatorLog logAnt) throws Throwable {
        LogOperateDetail log = LogThreadLocal.getLogBuilderLocal().getLog();
        log.setOperateResult(JNLConstants.OPERATE_STATUS_SUCCESS);

        if(logAnt.retType() == ReturnType.Object) {
            JSONObject jsonObject = JSONObject.parseObject((String)ret.get(RETMESSAGE));
            log.addObj().setObjGuid(jsonObject.getString(logAnt.id())).setObjValue((String)ret.get(RETMESSAGE));
            for(String key : logAnt.keys()) {
                log.getObj(0).addKey(key, jsonObject.getString(key));
            }

        } else if(logAnt.retType() == ReturnType.List) {
            JSONArray array = JSONObject.parseArray((String)ret.get(RETMESSAGE));
            for(int i=0; i< array.size(); i ++) {
                JSONObject jsonObject = array.getJSONObject(i);
                log.addObj().setObjGuid(jsonObject.getString(logAnt.id())).setObjValue(jsonObject.toJSONString());
                for(String key : logAnt.keys()) {
                    log.getObj(i).addKey(key, jsonObject.getString(key));
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
        System.out.println("**************************controller抛出异常后*************************************");
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
