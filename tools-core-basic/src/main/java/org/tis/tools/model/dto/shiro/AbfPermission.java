package org.tis.tools.model.dto.shiro;

import java.io.Serializable;
import java.util.Set;

/**
 * ABF权限
 */
public class AbfPermission implements Serializable {

    /** 行为权限集合*/
    private Set<String> bhvPermissions;
    /** 数据权限集合*/
    private Set<String> dataPermissions;

    public AbfPermission() {
    }

    public Set<String> getBhvPermissions() {
        return bhvPermissions;
    }

    public void setBhvPermissions(Set<String> bhvPermissions) {
        this.bhvPermissions = bhvPermissions;
    }

    public Set<String> getDataPermissions() {
        return dataPermissions;
    }

    public void setDataPermissions(Set<String> dataPermissions) {
        this.dataPermissions = dataPermissions;
    }
}
