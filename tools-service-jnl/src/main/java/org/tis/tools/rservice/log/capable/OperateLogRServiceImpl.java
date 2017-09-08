package org.tis.tools.rservice.log.capable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.tis.tools.common.utils.BasicUtil;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.log.LogAbfHistory;
import org.tis.tools.model.po.log.LogAbfKeyword;
import org.tis.tools.model.po.log.LogAbfOperate;
import org.tis.tools.model.vo.log.LogHistoryDetail;
import org.tis.tools.model.vo.log.LogOperateDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.log.exception.LogManagementException;
import org.tis.tools.service.log.LogAbfHistoryService;
import org.tis.tools.service.log.LogAbfKeywordService;
import org.tis.tools.service.log.LogAbfOperateService;

import java.util.Date;

public class OperateLogRServiceImpl extends BaseRService implements IOperateLogRService {

    @Autowired
    LogAbfOperateService logAbfOperateService;
    @Autowired
    LogAbfHistoryService logAbfHistoryService;
    @Autowired
    LogAbfKeywordService logAbfKeywordService;

    /**
     * 新增操作日志
     *
     * @param log 日志VO类
     * @throws LogManagementException
     */
    @Override
    public void createOperatorLog(LogOperateDetail log) throws LogManagementException {
        if (null == log || null == log.getInstance()) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, BasicUtil.wrap("", "LOG_OPERATE"));
        }
        LogAbfOperate logAbfOperate = log.getInstance();
        // 校验 TODO 不完善
        if (StringUtils.isBlank(logAbfOperate.getOperateFrom())) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                    BasicUtil.wrap(LogAbfOperate.COLUMN_OPERATE_FROM, LogAbfOperate.TABLE_NAME));
        }
        if (StringUtils.isBlank(logAbfOperate.getOperateResult())) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                    BasicUtil.wrap(LogAbfOperate.COLUMN_OPERATE_RESULT, LogAbfOperate.TABLE_NAME));
        }
        try {
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                public void doInTransactionWithoutResult(TransactionStatus status) {
                    try {
                        String logGuid = GUID.logOperate();
                        logAbfOperate.setGuid(logGuid);
                        logAbfOperate.setOperateTime(new Date());
                        logAbfOperateService.insert(logAbfOperate);
                        for (LogHistoryDetail obj : log.getAllObj()) {
                            String objGuid = GUID.logObject();
                            LogAbfHistory logAbfHistory = obj.getInstance();
                            logAbfHistory.setGuidOperate(logGuid);
                            logAbfHistory.setGuid(objGuid);
                            logAbfHistoryService.insert(logAbfHistory);
                            for (LogAbfKeyword logAbfKeyword : obj.getKeywords()) {
                                logAbfKeyword.setGuidHistory(objGuid);
                                logAbfKeywordService.insert(logAbfKeyword);
                            }
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        logger.error("createOperatorLog exception: " + e);
                        throw new LogManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("LOG_OPERATE", e.getMessage()));
                    }
                }
            });
        } catch (LogManagementException ae) {
            logger.error("createOperatorLog exception: " + ae);
            throw ae;
        } catch (Exception e) {
            logger.error("createOperatorLog exception: " + e);
            throw new LogManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("LOG_OPERATE", e.getMessage()));
        }
    }

}
