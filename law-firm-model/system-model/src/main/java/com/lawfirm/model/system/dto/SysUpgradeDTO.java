package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import com.lawfirm.common.core.enums.StatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 系统升级DTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUpgradeDTO extends BaseDTO {

    /**
     * 升级包ID
     */
    private Long packageId;

    /**
     * 升级包名称
     */
    private String name;

    /**
     * 升级包版本
     */
    private String version;

    /**
     * 升级包路径
     */
    private String path;

    /**
     * 升级包大小（字节）
     */
    private Long size;

    /**
     * 升级包MD5
     */
    private String md5;

    /**
     * 升级包描述
     */
    private String description;

    /**
     * 升级状态（0-未升级，1-升级中，2-升级成功，3-升级失败）
     */
    private Integer status;

    /**
     * 升级日志
     */
    private String log;

    /**
     * 升级时间
     */
    private Long upgradeTime;

    /**
     * 备注
     */
    private String remark;

    @Override
    public StatusEnum getStatus() {
        return super.getStatus();
    }

    @Override
    public SysUpgradeDTO setStatus(StatusEnum status) {
        super.setStatus(status);
        return this;
    }

    @Override
    public SysUpgradeDTO setRemark(String remark) {
        super.setRemark(remark);
        return this;
    }

    @Override
    public SysUpgradeDTO setId(Long id) {
        super.setId(id);
        return this;
    }

    @Override
    public SysUpgradeDTO setSort(Integer sort) {
        super.setSort(sort);
        return this;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final SysUpgradeDTO dto;

        public Builder() {
            dto = new SysUpgradeDTO();
        }

        public Builder packageId(Long packageId) {
            dto.packageId = packageId;
            return this;
        }

        public Builder name(String name) {
            dto.name = name;
            return this;
        }

        public Builder version(String version) {
            dto.version = version;
            return this;
        }

        public Builder path(String path) {
            dto.path = path;
            return this;
        }

        public Builder size(Long size) {
            dto.size = size;
            return this;
        }

        public Builder md5(String md5) {
            dto.md5 = md5;
            return this;
        }

        public Builder description(String description) {
            dto.description = description;
            return this;
        }

        public Builder status(StatusEnum status) {
            dto.setStatus(status);
            return this;
        }

        public Builder log(String log) {
            dto.log = log;
            return this;
        }

        public Builder upgradeTime(Long upgradeTime) {
            dto.upgradeTime = upgradeTime;
            return this;
        }

        public Builder remark(String remark) {
            dto.setRemark(remark);
            return this;
        }

        public SysUpgradeDTO build() {
            return dto;
        }
    }
} 