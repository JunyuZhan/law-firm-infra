package com.lawfirm.model.system.dto;

import com.lawfirm.common.data.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 系统升级DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysUpgradeDTO extends BaseDTO {

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

    public static SysUpgradeDTO.Builder builder() {
        return new SysUpgradeDTO.Builder();
    }

    public static class Builder {
        private final SysUpgradeDTO dto;

        public Builder() {
            dto = new SysUpgradeDTO();
        }

        public Builder id(Long id) {
            dto.setId(id);
            return this;
        }

        public Builder name(String name) {
            dto.setName(name);
            return this;
        }

        public Builder version(String version) {
            dto.setVersion(version);
            return this;
        }

        public Builder path(String path) {
            dto.setPath(path);
            return this;
        }

        public Builder size(Long size) {
            dto.setSize(size);
            return this;
        }

        public Builder md5(String md5) {
            dto.setMd5(md5);
            return this;
        }

        public Builder description(String description) {
            dto.setDescription(description);
            return this;
        }

        public Builder status(Integer status) {
            dto.setStatus(status);
            return this;
        }

        public Builder log(String log) {
            dto.setLog(log);
            return this;
        }

        public Builder upgradeTime(Long upgradeTime) {
            dto.setUpgradeTime(upgradeTime);
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