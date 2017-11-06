package org.tis.tools.webapp.log;

import org.tis.tools.model.vo.log.OperateLogBuilder;

public class LogThreadLocal {

    private static ThreadLocal<OperateLogBuilder> LOG_BUILDER_LOCAL = new ThreadLocal<>();

    //对外提供set和get方法
    public static void setLogBuilderLocal(OperateLogBuilder logBuilder){
        LOG_BUILDER_LOCAL.set(logBuilder);
    }

    public static OperateLogBuilder getLogBuilderLocal(){
        return LOG_BUILDER_LOCAL.get();
    }

}
