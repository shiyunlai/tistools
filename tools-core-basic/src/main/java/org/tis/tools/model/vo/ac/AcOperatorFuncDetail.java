package org.tis.tools.model.vo.ac;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AcOperatorFuncDetail implements Serializable {

    /** 节点类型 */
    public static final String NODE_TYPE_ROOT = "root";
    public static final String NODE_TYPE_APP = "app";
    public static final String NODE_TYPE_FUNCGROUP = "funcGroup";
    public static final String NODE_TYPE_FUNC = "func";
    /** 节点图标 */
    public static final String NODE_ICON_ROOT = "fa fa-home icon-state-info icon-lg";
    public static final String NODE_ICON_APP = "fa fa-th-large icon-state-info icon-lg";
    public static final String NODE_ICON_FUNCGROUP = "fa fa-th-list icon-state-info icon-lg";
    public static final String NODE_ICON_FUNC = "fa fa-wrench icon-state-info icon-lg";
    /** 节点状态 */
    public static final String NODE_STATUS_DISABLED = "0";
    public static final String NODE_STATUS_ENABLED = "1";


    /** serialVersionUID */
    private static final long serialVersionUID = 1L;

    /** 功能ID */
    private String id;

    /** 显示名称 */
    private String text;

    /** 图标 */
    private String icon;

    /** 父节点GUID */
    private String parentGuid;

    /** 是否叶子节点 */
    private String isLeaf;

    /** 排序码 */
    private BigDecimal order;

    /** 应用GUID */
    private String appGuid;

    /** 功能组GUID */
    private String funcGroupGuid;

    /** 节点类型 */
    private String nodeType;

    /** 是否拥有 */
    private String status;

    private List<AcOperatorFuncDetail> children = new ArrayList<>();


    public void addChildren(AcOperatorFuncDetail node) {
        this.children.add(node);
    }

    public List<AcOperatorFuncDetail> getChildren() {
        return children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public String getAppGuid() {
        return appGuid;
    }

    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

    public String getFuncGroupGuid() {
        return funcGroupGuid;
    }

    public void setFuncGroupGuid(String funcGroupGuid) {
        this.funcGroupGuid = funcGroupGuid;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String toString() {
        String result = "{"
                + "\"id\" : \"" + this.id + "\""
                + ", \"text\" : \"" + this.text + "\""
                + ", \"icon\" : \"" + this.icon + "\""
                + ", \"isLeaf\" : \"" + this.isLeaf + "\""
                + ", \"order\" : \"" + this.order + "\""
                + ", \"nodeType\" :\"" + this.order + "\""
                + ", \"parentGuid\" : \"" + this.parentGuid + "\""
                + ", \"funcGroupGuid\" : \"" + this.funcGroupGuid + "\""
                + ", \"appGuid\" : \"" + this.appGuid + "\""
                + ", \"state\" : {"
                + "\"disabled\" : " + (StringUtils.equals(this.status, NODE_STATUS_DISABLED) ? "true":"false") + "}"
                + ", \"nodeType\" : \"" + this.nodeType + "\"";

        if (children != null && children.size() != 0) {
            result += ", \"children\" : " + children.toString();
        }
        return result + "}";
    }
}
