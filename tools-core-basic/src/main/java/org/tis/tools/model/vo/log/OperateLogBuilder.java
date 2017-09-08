package org.tis.tools.model.vo.log;

import java.io.Serializable;

/**
 * 日志创建类
 */
public class OperateLogBuilder implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;


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
