package com.lawfirm.model.system.entity.monitor;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lawfirm.model.base.entity.ModelBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 数据库监控实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_db_monitor")
public class SysDbMonitor extends ModelBaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 数据库名称
     */
    private String dbName;

    /**
     * 数据库连接
     */
    private String dbUrl;

    /**
     * 活动连接数
     */
    private Integer activeConnections;

    /**
     * 最大连接数
     */
    private Integer maxConnections;

    /**
     * 每秒查询数
     */
    private BigDecimal qps;

    /**
     * 每秒事务数
     */
    private BigDecimal tps;

    /**
     * 慢查询数
     */
    private Integer slowQueries;

    /**
     * 数据表大小(bytes)
     */
    private Long tableSize;

    /**
     * 索引大小(bytes)
     */
    private Long indexSize;

    /**
     * 状态(NORMAL,WARNING,ERROR)
     */
    private String dbStatus;

    /**
     * 监控时间
     */
    private LocalDateTime monitorTime;
}