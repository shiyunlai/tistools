package org.tis.tools.model.vo.log;

import org.tis.tools.model.po.log.LogAbfOperate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LogOperateDetail implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;


    private LogAbfOperate log = new LogAbfOperate();

    private List<LogHistoryDetail> logAbfHistorie = new ArrayList<>();

    public void setLogAbfHistorie(List<LogHistoryDetail> logAbfHistorie) {
        this.logAbfHistorie = logAbfHistorie;
    }

    public void setLog(LogAbfOperate log) {
        this.log = log;
    }

    public LogOperateDetail(){}

    public LogHistoryDetail addObj() {
        LogHistoryDetail obj = new LogHistoryDetail();
        this.logAbfHistorie.add(obj);
        return obj;
    }

    public LogHistoryDetail getObj(int index) {
        return this.logAbfHistorie.get(index);
    }

    public List<LogHistoryDetail> getAllObj() {
        return this.logAbfHistorie;
    }


    public LogAbfOperate getInstance() {
        return log;
    }
    /**
     * Set the 操作渠道.
     */
    public LogOperateDetail setOperateFrom(String operateFrom) {
        this.log.setOperateFrom(operateFrom == null ? null : operateFrom.trim());
        return this;
    }

    /**
     * Set the 操作类型.
     *
     * @param operateType
     *            操作类型
     */
    public LogOperateDetail setOperateType(String operateType) {
        this.log.setOperateType(operateType == null ? null : operateType.trim());
        return this;
    }


    /**
     * Set the 操作时间.
     *
     * @param operateTime
     *            操作时间
     */
    public LogOperateDetail setOperateTime(Date operateTime) {
        this.log.setOperateTime(operateTime);
        return this;
    }


    /**
     * Set the 操作描述.
     *
     * @param operateDesc
     *            操作描述
     */
    public LogOperateDetail setOperateDesc(String operateDesc) {
        this.log.setOperateDesc(operateDesc == null ? null : operateDesc.trim());
        return this;
    }


    /**
     * Set the 操作结果.
     *
     * @param operateResult
     *            操作结果
     */
    public LogOperateDetail setOperateResult(String operateResult) {
        this.log.setOperateResult(operateResult == null ? null : operateResult.trim());
        return this;
    }


    /**
     * Set the 操作员姓名.
     *
     * @param operatorName
     *            操作员姓名
     */
    public LogOperateDetail setOperatorName(String operatorName) {
        this.log.setOperatorName(operatorName == null ? null : operatorName.trim());
        return this;
    }


    /**
     * Set the 操作员.
     *
     * @param userId
     *            操作员
     */
    public LogOperateDetail setUserId(String userId) {
        this.log.setUserId(userId == null ? null : userId.trim());
        return this;
    }


    /**
     * Set the 应用代码.
     *
     * @param appCode
     *            应用代码
     */
    public LogOperateDetail setAppCode(String appCode) {
        this.log.setAppCode(appCode == null ? null : appCode.trim());
        return this;
    }


    /**
     * Set the 应用名称.
     *
     * @param appName
     *            应用名称
     */
    public LogOperateDetail setAppName(String appName) {
        this.log.setAppName(appName == null ? null : appName.trim());
        return this;
    }


    /**
     * Set the 功能编号.
     *
     * @param funcCode
     *            功能编号
     */
    public LogOperateDetail setFuncCode(String funcCode) {
        this.log.setFuncCode(funcCode == null ? null : funcCode.trim());
        return this;
    }


    /**
     * Set the 功能名称.
     *
     * @param funcName
     *            功能名称
     */
    public LogOperateDetail setFuncName(String funcName) {
        this.log.setFuncName(funcName == null ? null : funcName.trim());
        return this;
    }


    /**
     * Set the 服务地址.
     *
     * @param restfulUrl
     *            服务地址
     */
    public LogOperateDetail setRestfulUrl(String restfulUrl) {
        this.log.setRestfulUrl(restfulUrl == null ? null : restfulUrl.trim());
        return this;
    }


    /**
     * Set the 异常堆栈.
     *
     * @param stackTrace
     *            异常堆栈
     */
    public LogOperateDetail setStackTrace(String stackTrace) {
        this.log.setStackTrace(stackTrace == null ? null : stackTrace.trim());
        return this;
    }


    /**
     * Set the 处理描述.
     *
     * @param processDesc
     *            处理描述
     */
    public LogOperateDetail setProcessDesc(String processDesc) {
        this.log.setProcessDesc(processDesc == null ? null : processDesc.trim());
        return this;
    }


}
