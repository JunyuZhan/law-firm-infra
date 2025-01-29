package com.lawfirm.model.base.entity;

import com.lawfirm.common.data.entity.BaseEntity;
import com.lawfirm.model.base.enums.StatusEnum;
import com.lawfirm.model.base.status.StatusAware;
import com.lawfirm.model.base.tenant.TenantAware;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 模型基础实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE #{entityName} SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public abstract class ModelBaseEntity extends BaseEntity implements Serializable, TenantAware, StatusAware {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Integer version;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createTime;

    @CreatedBy
    @Column(updatable = false, length = 64)
    private String createBy;

    @LastModifiedDate
    private LocalDateTime updateTime;

    @LastModifiedBy
    @Column(length = 64)
    private String updateBy;

    @Column(nullable = false)
    private Boolean deleted = false;

    @Column(nullable = false)
    private Long tenantId;  // 租户ID

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status = StatusEnum.ENABLED;  // 状态

    @Min(0)
    @Column(nullable = false)
    private Integer sort = 0;  // 排序号

    @Column(length = 512)
    private String remark;  // 备注
} 