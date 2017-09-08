package org.tis.tools.model.vo.log;

import org.tis.tools.model.po.log.LogAbfHistory;
import org.tis.tools.model.po.log.LogAbfKeyword;

import java.util.ArrayList;
import java.util.List;

public class LogHistoryDetail {
    private LogAbfHistory obj = new LogAbfHistory();

    private List<LogAbfKeyword> keywords = new ArrayList<>();

    public LogHistoryDetail addKey(String param, String value) {
        LogAbfKeyword logAbfKeyword = new LogAbfKeyword();
        logAbfKeyword.setParam(param == null ? null : param.trim());
        logAbfKeyword.setValue(value == null ? null : value.trim());
        this.keywords.add(logAbfKeyword);
        return this;
    }

    public LogAbfHistory getInstance() {
        return obj;
    }

    public List<LogAbfKeyword> getKeywords() {
        return keywords;
    }

    /**
     * Set the 对象来源.
     *
     * @param objFrom 对象来源
     */
    public LogHistoryDetail setObjFrom(String objFrom) {
        this.obj.setObjFrom(objFrom == null ? null : objFrom.trim());
        return this;
    }


    /**
     * Set the 对象类型.
     *
     * @param objType 对象类型
     */
    public LogHistoryDetail setObjType(String objType) {
        this.obj.setObjType(objType == null ? null : objType.trim());
        return this;
    }


    /**
     * Set the 对象GUID.
     *
     * @param objGuid 对象GUID
     */
    public LogHistoryDetail setObjGuid(String objGuid) {
        this.obj.setObjGuid(objGuid == null ? null : objGuid.trim());
        return this;
    }


    /**
     * Set the 对象名.
     *
     * @param objName 对象名
     */
    public LogHistoryDetail setObjName(String objName) {
        this.obj.setObjName(objName == null ? null : objName.trim());
        return this;
    }


    /**
     * Set the 对象值.
     *
     * @param objValue 对象值
     */
    public LogHistoryDetail setObjValue(String objValue) {
        this.obj.setObjValue(objValue == null ? null : objValue.trim());
        return this;
    }


}
