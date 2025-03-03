package com.lawfirm.model.cases.vo.business;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 案件分类视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class CaseCategoryVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 分类编码
     */
    private String categoryCode;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String categoryDescription;

    /**
     * 父分类ID
     */
    private Long parentId;

    /**
     * 父分类名称
     */
    private String parentName;

    /**
     * 分类层级
     */
    private Integer level;

    /**
     * 分类路径
     */
    private String path;

    /**
     * 分类完整路径名称
     */
    private String fullPath;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 状态（0-禁用 1-启用）
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String statusName;

    /**
     * 图标
     */
    private String icon;

    /**
     * 颜色
     */
    private String color;

    /**
     * 是否允许删除
     */
    private Boolean allowDelete;

    /**
     * 是否系统内置
     */
    private Boolean isSystem;

    /**
     * 备注
     */
    private String remark;

    /**
     * 子分类列表
     */
    private transient List<CaseCategoryVO> children;

    /**
     * 案件数量
     */
    private Integer caseCount;

    /**
     * 获取状态名称
     */
    public String getStatusName() {
        if (this.status == null) {
            return "";
        }
        return this.status == 1 ? "启用" : "禁用";
    }

    /**
     * 是否为根节点
     */
    public boolean isRoot() {
        return this.parentId == null || this.parentId == 0;
    }

    /**
     * 是否为叶子节点
     */
    public boolean isLeaf() {
        return this.children == null || this.children.isEmpty();
    }

    /**
     * 是否有子节点
     */
    public boolean hasChildren() {
        return this.children != null && !this.children.isEmpty();
    }

    /**
     * 获取子节点数量
     */
    public int getChildCount() {
        return this.children == null ? 0 : this.children.size();
    }
} 