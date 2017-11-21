package org.tis.tools.model.vo.ac;

import org.apache.commons.lang.StringUtils;
import org.tis.tools.model.def.GUID;
import org.tis.tools.model.po.ac.AcMenu;
import org.tis.tools.model.po.ac.AcOperatorMenu;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
     * 下级菜单详情集合
     */
    private List<AcMenuDetail> children = new ArrayList<>();

    /**
     * 当前菜单
     */
    private AcMenu acMenu;

    /**
     * 当前重组菜单
     */
    private AcOperatorMenu acOperatorMenu;


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
        this.children.add(node);
    }

    // 传入当前菜单节点的上级菜单信息
    // 该方法实现对所有下级的菜单重构GUID、PARENT_GUID和MENU_SEQ
    public void restructureGuidForOperatorMenu(AcMenu acMenu) {
        // 生成重组菜单GUID
        String newGuid = GUID.operatorMenu();
        // 重构当前菜单节点信息
        this.acMenu.setGuid(newGuid);
        this.acMenu.setMenuSeq(acMenu.getMenuSeq() + "." + newGuid);
        this.acMenu.setGuidParents(acMenu.getGuid());
        this.acMenu.setGuidRoot(acMenu.getGuidRoot());

        // 如果有子节点，重构子节点信息
        if (this.children.size() > 0) {
            for (AcMenuDetail menuDetail : this.children) {
                menuDetail.restructureGuidForOperatorMenu(this.acMenu);
            }
        }
    }

    public void sortChildren() {
        // 对当前子菜单排序
        if (this.children.size() > 0) {
//            ArrayList<AcMenuDetail> list = new ArrayList<>(this.children);
            Collections.sort(this.children, new Comparator<AcMenuDetail>() {
                @Override
                public int compare(AcMenuDetail o1, AcMenuDetail o2) {
                    return o1.getOrder().compareTo(o2.getOrder());
                }
            });
            for (AcMenuDetail menuDetail : this.children) {
                menuDetail.sortChildren();
            }
        }
    }

    public List<AcMenuDetail> getChildren() {
        return children;
    }

    public AcMenu getAcMenu() {
        return acMenu;
    }

    public void setAcMenu(AcMenu acMenu) {
        this.acMenu = acMenu;
    }

    public AcOperatorMenu getAcOperatorMenu() {
        return acOperatorMenu;
    }

    public void setAcOperatorMenu(AcOperatorMenu acOperatorMenu) {
        this.acOperatorMenu = acOperatorMenu;
    }

    public String toString() {
        String result = "{"
                + "\"guid\" : \"" + this.guid + "\""
                + ", \"label\" : \"" + this.label + "\""
                + ", \"icon\" : \"" + this.icon + "\""
                + ", \"isLeaf\" : \"" + this.isLeaf + "\""
                + ", \"order\" : \"" + this.order + "\""
                + ", \"href\" : \"" + this.href + "\""
                + ", \"parentGuid\" : \"" + this.parentGuid + "\"";

        if (children != null && children.size() != 0) {
            result += ", \"children\" : " + children.toString();
        }
        return result + "}";
    }


    public String toJson() {
        String result = "{";
        if (StringUtils.isNotBlank(this.guid)) {
            result +=  "\"guid\" : \"" + this.guid + "\""
                    + ", \"label\" : \"" + this.label + "\""
                    + ", \"icon\" : \"" + this.icon + "\""
                    + ", \"isLeaf\" : \"" + this.isLeaf + "\""
                    + ", \"href\" : \"" + this.href + "\""
                    + ", \"order\" : \"" + this.order + "\"";
            if (children != null && children.size() != 0) {
                result += ", \"children\" : " + children.toString();
            }
        }
        return result + "}";
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
