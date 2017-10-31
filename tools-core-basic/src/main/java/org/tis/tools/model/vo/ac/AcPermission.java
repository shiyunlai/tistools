package org.tis.tools.model.vo.ac;

import java.io.Serializable;
import java.util.Set;

public class AcPermission implements Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    private String userId;

    private String appCode;

    private Set<String> funCodes;

    private Set<String> bhvCodes;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public Set<String> getFunCodes() {
        return funCodes;
    }

    public void setFunCodes(Set<String> funCodes) {
        this.funCodes = funCodes;
    }

    public Set<String> getBhvCodes() {
        return bhvCodes;
    }

    public void setBhvCodes(Set<String> bhvCodes) {
        this.bhvCodes = bhvCodes;
    }
}
