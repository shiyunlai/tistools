package org.tis.tools.model.dto.shiro;

import com.alibaba.dubbo.common.utils.StringUtils;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 行为权限Permission实例
 * 用于比较验证是否有功能行为的权限
 * 字符格式：
 * "+funcCode+bhvCode1,bhvCode2,..."
 * 其中funcCode只有一个，标识功能代码，bhvCode多个标色功能行为代码
 * 验证规则：
 * 先验证是否有功能权限，也就是funcCode是否一致，然后验证行为权限
 */
public class BehaviorPermission implements Permission, Serializable {

    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 行为权限Permission标识*/
    private static final String BHV_PERMISSION_FLAG = "\\+";
    private static final String SUBPART_DIVIDER_TOKEN = ",";

    /** 功能代码*/
    private String funcCode;
    /** 行为代码*/
    private List<String> bhvCodes;

    /**
     * 解析字符串构造 BehaviorPermission
     * @param permissionString
     */
    public BehaviorPermission(String permissionString) {
        if (permissionString == null || permissionString.trim().length() == 0) {
            throw new IllegalArgumentException("BehaviorPermission string cannot be null or empty. Make sure permission strings are properly formatted.");
        }
        String[] array = permissionString.split(BHV_PERMISSION_FLAG);

        if(array.length > 2) {
            funcCode = array[1];
        }
        String bhvCodeString = array[2];
        if(StringUtils.isNotEmpty(bhvCodeString)) {
            Set<String> subparts = CollectionUtils.asSet(bhvCodeString.split(SUBPART_DIVIDER_TOKEN));
            if (subparts.isEmpty()) {
                throw new IllegalArgumentException("BhvCodes string cannot contain parts with only dividers. Make sure permission strings are properly formatted.");
            }
            this.bhvCodes = new ArrayList<>(subparts);
        }
        if (this.bhvCodes.isEmpty()) {
            throw new IllegalArgumentException("BhvCodes string cannot contain only dividers. Make sure permission strings are properly formatted.");
        }
    }

    /**
     * Returns {@code true} if this current instance <em>implies</em> all the functionality and/or resource access
     * described by the specified {@code Permission} argument, {@code false} otherwise.
     * <p/>
     * <p>That is, this current instance must be exactly equal to or a <em>superset</em> of the functionality
     * and/or resource access described by the given {@code Permission} argument.  Yet another way of saying this
     * would be:
     * <p/>
     * <p>If &quot;permission1 implies permission2&quot;, i.e. <code>permission1.implies(permission2)</code> ,
     * then any Subject granted {@code permission1} would have ability greater than or equal to that defined by
     * {@code permission2}.
     *
     * @param p the permission to check for behavior/functionality comparison.
     * @return {@code true} if this current instance <em>implies</em> all the functionality and/or resource access
     * described by the specified {@code Permission} argument, {@code false} otherwise.
     */
    @Override
    public boolean implies(Permission p) {
        if(!(p instanceof BehaviorPermission)) {
            return false;
        }
        BehaviorPermission other = (BehaviorPermission) p;
        if(!this.funcCode.equals(other.funcCode))
            return false;
        if(!this.bhvCodes.containsAll(other.bhvCodes))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BehaviorPermission{" +
                "funcCode='" + funcCode + '\'' +
                ", bhvCodes=" + bhvCodes +
                '}';
    }
}
