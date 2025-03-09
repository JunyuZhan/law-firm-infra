package com.lawfirm.model.system.entity.upgrade;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.io.Serializable;

/**
 * 系统升级实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_upgrade")
public class Upgrade extends ModelBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 升级版本号
     */
    @TableField("upgrade_version")
    private String upgradeVersion;

    /**
     * 升级标题
     */
    @TableField("title")
    private String title;

    /**
     * 升级描述
     */
    @TableField("description")
    private String description;

    /**
     * 升级类型：PATCH-补丁升级，MINOR-小版本升级，MAJOR-大版本升级
     */
    @TableField("upgrade_type")
    private String upgradeType;

    /**
     * 升级状态：PENDING-待升级，UPGRADING-升级中，SUCCESS-升级成功，FAILED-升级失败，ROLLBACK-已回滚
     */
    @TableField("upgrade_status")
    private String upgradeStatus;

    /**
     * 升级时间
     */
    @TableField("upgrade_time")
    private Long upgradeTime;

    /**
     * 回滚时间
     */
    @TableField("rollback_time")
    private Long rollbackTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
} 