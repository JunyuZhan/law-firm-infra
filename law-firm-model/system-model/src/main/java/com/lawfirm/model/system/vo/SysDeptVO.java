package com.lawfirm.model.system.vo;

import com.lawfirm.common.data.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 部门VO
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SysDeptVO extends BaseVO {

    /**
     * 部门名称
     */
    private String name;

    /**
     * 父部门ID
     */
    private Long parentId;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 负责人
     */
    private String leader;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态（0-正常 1-停用）
     */
    private Integer status;

    /**
     * 子部门列表
     */
    private List<SysDeptVO> children;
} 