package com.lawfirm.model.auth.vo;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门视图对象
 */
@Data
public class DepartmentVO {
    
    /**
     * 部门ID
     */
    private Long id;
    
    /**
     * 部门名称
     */
    private String name;
    
    /**
     * 上级部门ID
     */
    private Long parentId;
    
    /**
     * 上级部门名称
     */
    private String parentName;
    
    /**
     * 祖级列表
     */
    private String ancestors;
    
    /**
     * 显示顺序
     */
    private Integer orderNum;
    
    /**
     * 负责人ID
     */
    private Long leaderId;
    
    /**
     * 负责人名称
     */
    private String leaderName;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 部门状态（0正常 1停用）
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 子部门列表
     */
    private List<DepartmentVO> children;
    
    /**
     * 备注
     */
    private String remark;
} 