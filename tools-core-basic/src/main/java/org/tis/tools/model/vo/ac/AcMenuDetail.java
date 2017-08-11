package org.tis.tools.model.vo.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

public class AcMenuDetail implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 菜单GUID */
    private String guid;

    /** 菜单显示名称 */
    private String label;

    /** 菜单图标 */
    private String icon;

    /** 父菜单GUID */
    private String parentGuid;

    /** 是否叶子菜单 */
    private String isLeaf;

    /** 排序码 */
    private BigDecimal order;

    /** ui地址 */
    private String href;


    /**
     * 下级菜单集合
     */
    private Set<AcMenuDetail> children = new HashSet<>();


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getParentGuid() {
        return parentGuid;
    }

    public void setParentGuid(String parentGuid) {
        this.parentGuid = parentGuid;
    }

    public String getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(String isLeaf) {
        this.isLeaf = isLeaf;
    }

    public BigDecimal getOrder() {
        return order;
    }

    public void setOrder(BigDecimal order) {
        this.order = order;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    // 添加孩子节点
    public void addChild(AcMenuDetail node) {
//        this.children.addChild(node);
        this.children.add(node);
    }

    public String toString() {
        String result = "{"
                + "\"guid\" : \"" + this.guid + "\""
                + ", \"label\" : \"" + this.label + "\""
                + ", \"icon\" : \"" + this.icon + "\""
                + ", \"isLeaf\" : \"" + this.isLeaf + "\""
                + ", \"order\" : \"" + this.order + "\""
                + ", \"href\" : \"" + this.href + "\"";

        if (children != null && children.size() != 0) {
            result += ", \"children\" : " + children.toString();
        }
        return result + "}";
    }


    public String toJson() {
        String result = "";
        if (children != null && children.size() != 0) {
            result= "{"
                    + "\"guid\" : \"" + this.guid + "\""
                    + ", \"label\" : \"" + this.label + "\""
                    + ", \"icon\" : \"" + this.icon + "\""
                    + ", \"isLeaf\" : \"" + this.isLeaf + "\""
                    + ", \"href\" : \"" + this.href + "\""
                    + ", \"order\" : \"" + this.order + "\""
                    + ", \"children\" : " + children.toString()
                    + "}";
        }
        return result ;
    }

    public String toTree() {
        String result = "";
        if (children != null && children.size() != 0) {
            result= "{"
                    + "\"id\" : \"" + this.guid + "\""
                    + ", \"text\" : \"" + this.label + "\""
                    + ", \"icon\" : \"" + this.icon + "\""
                    + ", \"isLeaf\" : \"" + this.isLeaf + "\""
                    + ", \"href\" : \"" + this.href + "\""
                    + ", \"order\" : \"" + this.order + "\""
                    + ", \"children\" : " + children.toString()
                    + "}";
        }
        return result ;
    }
}
