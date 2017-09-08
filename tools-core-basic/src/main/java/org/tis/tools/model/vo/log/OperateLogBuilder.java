package org.tis.tools.model.vo.log;

/**
 * 日志创建类
 */
public class OperateLogBuilder {

    private LogOperateDetail logOperateDetail;

    public LogOperateDetail start() {
        logOperateDetail = new LogOperateDetail();
        return logOperateDetail;
    }

    public LogOperateDetail getLog() {
        return logOperateDetail;
    }


    public void complete() {
       // FIXME 调用服务持久化？
    }
}
