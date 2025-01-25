package com.lawfirm.organization.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

/**
 * 律所分所实体
 */
@Data
@Entity
@Table(name = "org_branch")
public class Branch {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * 分所名称
     */
    @NotBlank(message = "分所名称不能为空")
    @Size(max = 50, message = "分所名称长度不能超过50个字符")
    @Column(length = 50, nullable = false)
    private String name;
    
    /**
     * 分所编码
     */
    @NotBlank(message = "分所编码不能为空")
    @Size(max = 20, message = "分所编码长度不能超过20个字符")
    @Column(length = 20, nullable = false, unique = true)
    private String code;
    
    /**
     * 分所地址
     */
    @Size(max = 200, message = "分所地址长度不能超过200个字符")
    @Column(length = 200)
    private String address;
    
    /**
     * 联系电话
     */
    @Size(max = 20, message = "联系电话长度不能超过20个字符")
    @Column(length = 20)
    private String phone;
    
    /**
     * 负责人ID
     */
    private Long managerId;
    
    /**
     * 分所状态（0-正常，1-停用）
     */
    private Integer status;
    
    /**
     * 排序
     */
    private Integer sort;
    
    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    @Column(length = 500)
    private String remark;
    
    /**
     * 创建时间
     */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updateTime;
    
    /**
     * 创建人
     */
    @CreatedBy
    @Column(updatable = false)
    private Long createBy;
    
    /**
     * 更新人
     */
    @LastModifiedBy
    private Long updateBy;
    
    /**
     * 是否删除
     */
    private Boolean deleted = false;
} 