package org.tis.tools.rservice.log.capable;

import org.tis.tools.model.vo.log.LogOperateDetail;
import org.tis.tools.rservice.log.exception.LogManagementException;

/***
 *  <pre>
 *      操作日志
 *  对用户需要记录的操作实现的管理功能
 *
 *  </pre>
 */
public interface IOperateLogRService {
    /**
     * 新增操作日志
     * @param log 日志VO类
     * @throws LogManagementException
     */
    void createOperatorLog(LogOperateDetail log) throws LogManagementException;
}
