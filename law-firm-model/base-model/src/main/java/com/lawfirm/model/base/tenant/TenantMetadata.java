package com.lawfirm.model.base.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 租户元数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TenantMetadata implements Cloneable {

    /**
     * 租户ID
     */
    private Long tenantId;

    /**
     * 租户编码
     */
    private String tenantCode;

    /**
     * 租户名称
     */
    private String tenantName;

    /**
     * 租户状态（0-启用，1-禁用）
     */
    private Integer status;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 最大用户数
     */
    private Integer maxUsers;

    /**
     * 最大存储空间(MB)
     */
    private Long maxStorage;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 地址
     */
    private String address;

    /**
     * 描述
     */
    private String description;

    /**
     * 验证租户元数据
     */
    public void validate() {
        if (tenantCode == null || tenantCode.trim().isEmpty()) {
            throw new IllegalArgumentException("租户编码不能为空");
        }
        if (tenantName == null || tenantName.trim().isEmpty()) {
            throw new IllegalArgumentException("租户名称不能为空");
        }
    }

    /**
     * 克隆租户元数据
     */
    @Override
    public TenantMetadata clone() {
        try {
            return (TenantMetadata) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("克隆租户元数据失败", e);
        }
    }
} 