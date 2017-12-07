package org.tis.tools.rservice.log.capable;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.util.CollectionUtils;
import org.tis.tools.base.WhereCondition;
import org.tis.tools.core.exception.ExceptionCodes;
import org.tis.tools.model.def.AbfEntityHelper;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.def.JNLConstants;
import org.tis.tools.model.po.log.LogAbfChange;
import org.tis.tools.model.po.log.LogAbfHistory;
import org.tis.tools.model.po.log.LogAbfKeyword;
import org.tis.tools.model.po.log.LogAbfOperate;
import org.tis.tools.model.vo.log.LogHistoryDetail;
import org.tis.tools.model.vo.log.LogOperateDetail;
import org.tis.tools.rservice.BaseRService;
import org.tis.tools.rservice.log.exception.LogManagementException;
import org.tis.tools.service.log.*;
import org.tis.tools.service.log.exception.LOGExceptionCodes;

import java.util.*;

import static org.tis.tools.common.utils.BasicUtil.wrap;

public class OperateLogRServiceImpl extends BaseRService implements IOperateLogRService {

    @Autowired
    LogAbfOperateService logAbfOperateService;
    @Autowired
    LogAbfHistoryService logAbfHistoryService;
    @Autowired
    LogAbfKeywordService logAbfKeywordService;
    @Autowired
    LogAbfChangeService logAbfChangeService;

    @Autowired
    LogServiceExt logServiceExt;

    /**
     * 新增操作日志
     *
     * @param log 日志VO类
     * @throws LogManagementException
     */
    @Override
    public void createOperatorLog(LogOperateDetail log) throws LogManagementException {
        if (null == log || null == log.getInstance()) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT, wrap("", "LOG_OPERATE"));
        }
        LogAbfOperate logAbfOperate = log.getInstance();
        // 校验 TODO 不完善
        if (StringUtils.isBlank(logAbfOperate.getOperateFrom())) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                    wrap(LogAbfOperate.COLUMN_OPERATE_FROM, LogAbfOperate.TABLE_NAME));
        }
        if (StringUtils.isBlank(logAbfOperate.getOperateResult())) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_INSERT,
                    wrap(LogAbfOperate.COLUMN_OPERATE_RESULT, LogAbfOperate.TABLE_NAME));
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
                            for (LogAbfChange logAbfChange: obj.getChanges()) {
                                logAbfChange.setGuidHistory(objGuid);
                                logAbfChangeService.insert(logAbfChange);
                            }
                        }
                    } catch (Exception e) {
                        status.setRollbackOnly();
                        e.printStackTrace();
                        throw new LogManagementException(
                                ExceptionCodes.FAILURE_WHEN_INSERT, wrap("LOG_OPERATE", e.getMessage()));
                    }
                }
            });
        } catch (LogManagementException ae) {
            throw ae;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LogManagementException(
                    ExceptionCodes.FAILURE_WHEN_INSERT, wrap("LOG_OPERATE", e.getMessage()));
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
            WhereCondition whereCondition = new WhereCondition();
            whereCondition.setOrderBy(LogAbfOperate.COLUMN_OPERATE_TIME + " DESC");
            return logAbfOperateService.query(whereCondition);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LogManagementException(
                    ExceptionCodes.FAILURE_WHEN_QUERY, wrap(LogAbfOperate.TABLE_NAME, e.getMessage()));
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
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap(LogAbfOperate.COLUMN_GUID, "OPERATE_LOG"));
        }
        try {
            LogAbfOperate logAbfOperate = logAbfOperateService.loadByGuid(logGuid);
            if(logAbfOperate == null) {
                throw new LogManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, wrap(logGuid, LogAbfOperate.TABLE_NAME));
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
                    List<LogAbfChange> changes = logAbfChangeService.query(new WhereCondition().andIn(LogAbfChange.COLUMN_GUID_HISTORY, objGuids));
                    for (LogAbfChange change : changes) {
                        map.get(change.getGuidHistory()).getChanges().add(change);
                    }
                }
            }
            return detail;
        } catch (LogManagementException e) {
            throw e;
        } catch (Exception e) {
            throw new LogManagementException(ExceptionCodes.FAILURE_WHEN_QUERY, wrap("OPERATE_LOG", e.getMessage()));
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
    public Map<String, Object> queryOperateHistoryList(String objGuid) throws LogManagementException {
        if(StringUtils.isBlank(objGuid)) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap(LogAbfHistory.COLUMN_OBJ_GUID, "OPERATE_LOG"));
        }
        Map<String, Object> resultMap = new HashMap<>();
        String tableName = AbfEntityHelper.getTableName(objGuid);
        if(StringUtils.isBlank(tableName)) {
            throw new LogManagementException(LOGExceptionCodes.NOT_FOUND_CORRESPONDING_ENTITY, wrap(objGuid));
        }
        try {
            Map<String, Object> entityInfo = AbfEntityHelper.transEntity(logServiceExt.getEntityInfo(tableName, objGuid), objGuid);
            if (entityInfo == null) {
                throw new LogManagementException(ExceptionCodes.NOT_FOUND_WHEN_QUERY, wrap(objGuid, LogAbfHistory.TABLE_NAME));
            }
            resultMap.put("subject", entityInfo);
            List<LogAbfHistory> objs = logAbfHistoryService.query(new WhereCondition().andEquals(LogAbfHistory.COLUMN_OBJ_GUID, objGuid));
            if(CollectionUtils.isEmpty(objs)) {
                resultMap.put("list", new ArrayList<>());
            }
            List<LogOperateDetail> logDetails = new ArrayList<>();

            Map<String, LogHistoryDetail> logGuidMap = new HashMap<>();
            Map<String, LogHistoryDetail> objGuidMap = new HashMap<>();
            List<String> logGuids = new ArrayList<>();
            List<String> objGuids = new ArrayList<>();
            for (LogAbfHistory obj : objs) {
                logGuids.add(obj.getGuidOperate());
                objGuids.add(obj.getGuid());
                LogHistoryDetail objDetail = new LogHistoryDetail();
                objDetail.setObj(obj);
                objGuidMap.put(obj.getGuid(), objDetail);
                logGuidMap.put(obj.getGuidOperate(), objDetail);
            }
            if (logGuids.size() > 0) {
                WhereCondition wc = new WhereCondition();
                wc.andIn(LogAbfOperate.COLUMN_GUID, logGuids);
                wc.setOrderBy(LogAbfOperate.COLUMN_OPERATE_TIME + " DESC");
                List<LogAbfOperate> logs = logAbfOperateService.query(wc);
                List<LogAbfKeyword> keywords = logAbfKeywordService.query(new WhereCondition().andIn(LogAbfKeyword.COLUMN_GUID_HISTORY, objGuids));
                List<LogAbfChange> changes = logAbfChangeService.query(new WhereCondition().andIn(LogAbfChange.COLUMN_GUID_HISTORY, objGuids));

                for (LogAbfOperate log : logs) {
                    LogOperateDetail detail = new LogOperateDetail();
                    detail.setLog(log);
                    detail.getAllObj().add(logGuidMap.get(log.getGuid()));
                    logDetails.add(detail);
                }
                for (LogAbfKeyword keyword : keywords) {
                    objGuidMap.get(keyword.getGuidHistory()).getKeywords().add(keyword);
                }
                for (LogAbfChange change : changes) {
                    objGuidMap.get(change.getGuidHistory()).getChanges().add(change);
                }
            }
            resultMap.put("list", logDetails);
            return resultMap;
        } catch (LogManagementException e) {
            throw e;
        } catch (Exception e) {
            logger.error("queryOperateHistoryList exception : ", e);
            throw new LogManagementException(ExceptionCodes.FAILURE_WHEN_QUERY, wrap("OPERATE_LOG", e.getMessage()));
        }
    }

    /**
     * 查询操作员的登录历史
     *
     * @param userId
     * @return 操作日志集合
     * @throws LogManagementException
     */
    @Override
    public List<LogAbfOperate> queryLoginHistory(String userId) throws LogManagementException {
        if(StringUtils.isBlank(userId)) {
            throw new LogManagementException(ExceptionCodes.NOT_ALLOW_NULL_WHEN_QUERY, wrap(LogAbfOperate.COLUMN_USER_ID, "OPERATE_LOG"));
        }
        try {
            return logAbfOperateService.query(new WhereCondition()
                    .andEquals(LogAbfOperate.COLUMN_OPERATE_TYPE, JNLConstants.OPEARTE_TYPE_LOGIN)
                    .andEquals(LogAbfOperate.COLUMN_USER_ID, userId));
        } catch (Exception e) {
            throw new LogManagementException(ExceptionCodes.FAILURE_WHEN_QUERY, wrap("OPERATE_LOG", e.getMessage()));
        }
    }
}
