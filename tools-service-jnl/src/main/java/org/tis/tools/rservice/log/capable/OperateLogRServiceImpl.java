package org.tis.tools.rservice.log.capable;

import com.sun.corba.se.impl.orbutil.LogKeywords;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.CollectionUtils;
import org.tis.tools.base.WhereCondition;
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

import java.util.*;

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
                        e.printStackTrace();
                        throw new LogManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("LOG_OPERATE", e.getMessage()));
                    }
                }
            });
        } catch (LogManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LogManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, BasicUtil.wrap("LOG_OPERATE", e.getMessage()));
        }
    }

    /**
     * 查询操作日志列表
     *
     * @return 操作日志集合
     * @throws LogManagementException
     */
    @Override
    public List<LogAbfOperate> queryOperateLogList() throws LogManagementException {
        try {
            return logAbfOperateService.query(new WhereCondition());
        } catch (Exception e) {
            e.printStackTrace();
            throw new LogManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap(LogAbfOperate.TABLE_NAME, e.getMessage()));
        }
    }

    /**
     * 查询操作日志明细
     *
     * @param logGuid 操作日志GUID
     * @return 操作对象集合
     * @throws LogManagementException
     */
    @Override
    public LogOperateDetail queryOperateDetail(String logGuid) throws LogManagementException {
        if(StringUtils.isBlank(logGuid)) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap(LogAbfOperate.COLUMN_GUID, "OPERATE_LOG"));
        }
        try {
            LogAbfOperate logAbfOperate = logAbfOperateService.loadByGuid(logGuid);
            if(logAbfOperate == null) {
                throw new LogManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap(logGuid, LogAbfOperate.TABLE_NAME));
            }
            LogOperateDetail detail = new LogOperateDetail();
            detail.setLog(logAbfOperate);
            List<LogAbfHistory> objs = logAbfHistoryService.query(new WhereCondition().andEquals(LogAbfHistory.COLUMN_GUID_OPERATE, logGuid));
            if (objs.size() > 0) {
                Map<String, LogHistoryDetail> map = new HashMap<>();
                List<String> objGuids = new ArrayList<>();
                for(LogAbfHistory obj : objs) {
                    LogHistoryDetail objDetail = new LogHistoryDetail();
                    objDetail.setObj(obj);
                    objGuids.add(obj.getGuid());
                    map.put(obj.getGuid(), objDetail);
                    detail.getAllObj().add(objDetail);
                }
                if(objGuids.size() > 0) {
                    List<LogAbfKeyword> keywords = logAbfKeywordService.query(new WhereCondition().andIn(LogAbfKeyword.COLUMN_GUID_HISTORY, objGuids));
                    for(LogAbfKeyword keyword : keywords) {
                        map.get(keyword.getGuidHistory()).getKeywords().add(keyword);
                    }
                }
            }
            return detail;
        } catch (Exception e) {
            throw new LogManagementException(ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap("OPERATE_LOG", e.getMessage()));
        }
    }

    /**
     * 查询对象的操作历史
     *
     * @param objGuid 对象GUID
     * @return 操作日志集合
     * @throws LogManagementException
     */
    @Override
    public List<LogOperateDetail> queryOperateHistoryList(String objGuid) throws LogManagementException {
        if(StringUtils.isBlank(objGuid)) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, BasicUtil.wrap(LogAbfHistory.COLUMN_OBJ_GUID, "OPERATE_LOG"));
        }
        try {
            List<LogAbfHistory> objs = logAbfHistoryService.query(new WhereCondition().andEquals(LogAbfHistory.COLUMN_OBJ_GUID, objGuid));
            if(CollectionUtils.isEmpty(objs)) {
                throw new LogManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, BasicUtil.wrap(objGuid, LogAbfHistory.TABLE_NAME));
            }
            List<LogOperateDetail> logDetails = new ArrayList<>();
            if (objs.size() > 0) {
                Map<String, LogHistoryDetail> logGuidMap = new HashMap<>();
                Map<String, LogHistoryDetail> objGuidMap = new HashMap<>();
                List<String> logGuids = new ArrayList<>();
                List<String> objGUids = new ArrayList<>();
                for (LogAbfHistory obj : objs) {
                    logGuids.add(obj.getGuidOperate());
                    objGUids.add(obj.getGuid());
                    LogHistoryDetail objDetail = new LogHistoryDetail();
                    objDetail.setObj(obj);
                    objGuidMap.put(obj.getGuid(), objDetail);
                    logGuidMap.put(obj.getGuidOperate(), objDetail);
                }
                List<LogAbfOperate> logs = logAbfOperateService.query(new WhereCondition().andIn(LogAbfOperate.COLUMN_GUID, logGuids));
                List<LogAbfKeyword> keywords = logAbfKeywordService.query(new WhereCondition().andIn(LogAbfKeyword.COLUMN_GUID_HISTORY, objGUids));
                for(LogAbfOperate log : logs) {
                    LogOperateDetail detail = new LogOperateDetail();
                    detail.setLog(log);
                    detail.getAllObj().add(logGuidMap.get(log.getGuid()));
                    logDetails.add(detail);
                }
                for(LogAbfKeyword keyword : keywords) {
                    objGuidMap.get(keyword.getGuidHistory()).getKeywords().add(keyword);
                }
            }
            return logDetails;
        } catch (Exception e) {
            throw new LogManagementException(ExceptionCodes.FAILURE_WHEN_QUERY, BasicUtil.wrap("OPERATE_LOG", e.getMessage()));
        }
    }
}
