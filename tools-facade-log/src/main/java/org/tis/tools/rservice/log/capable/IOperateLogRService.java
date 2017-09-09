package org.tis.tools.rservice.log.capable;

import org.tis.tools.model.po.log.LogAbfHistory;
import org.tis.tools.model.po.log.LogAbfKeyword;
import org.tis.tools.model.po.log.LogAbfOperate;
import org.tis.tools.model.vo.log.LogHistoryDetail;
import org.tis.tools.model.vo.log.LogOperateDetail;
import org.tis.tools.rservice.log.exception.LogManagementException;

import java.util.List;

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

    /**
     * 查询操作日志列表
     * @return 操作日志集合
     * @throws LogManagementException
     */
    List<LogAbfOperate> queryOperateLogList() throws LogManagementException;

    /**
     * 查询操作日志明细
     * @param logGuid 操作日志GUID
     * @return 操作对象集合
     * @throws LogManagementException
     */
    LogOperateDetail queryOperateDetail(String logGuid) throws LogManagementException;

    /**
     * 查询对象的操作历史
     * @param objGuid 对象GUID
     * @return 操作日志集合
     * @throws LogManagementException
     */
    List<LogOperateDetail> queryOperateHistoryList(String objGuid) throws LogManagementException;


}
