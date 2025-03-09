package com.lawfirm.model.system.vo.monitor;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 磁盘信息视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DiskInfoVO extends BaseVO {

    private static final long serialVersionUID = 1L;

    /**
     * 总磁盘空间(GB)
     */
    private Double totalDiskSpace;

    /**
     * 已用磁盘空间(GB)
     */
    private Double usedDiskSpace;

    /**
     * 空闲磁盘空间(GB)
     */
    private Double freeDiskSpace;

    /**
     * 磁盘使用率
     */
    private Double diskUsage;

    /**
     * 磁盘读取速率(KB/s)
     */
    private Double diskReadRate;

    /**
     * 磁盘写入速率(KB/s)
     */
    private Double diskWriteRate;

    /**
     * 磁盘分区列表
     */
    private transient List<DiskPartitionVO> partitions;

    /**
     * 磁盘分区信息
     */
    @Data
    @Accessors(chain = true)
    public static class DiskPartitionVO implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 分区名称
         */
        private String name;

        /**
         * 分区挂载点
         */
        private String mountPoint;

        /**
         * 分区类型
         */
        private String type;

        /**
         * 总空间(GB)
         */
        private Double totalSpace;

        /**
         * 已用空间(GB)
         */
        private Double usedSpace;

        /**
         * 空闲空间(GB)
         */
        private Double freeSpace;

        /**
         * 使用率
         */
        private Double usage;
    }
} 