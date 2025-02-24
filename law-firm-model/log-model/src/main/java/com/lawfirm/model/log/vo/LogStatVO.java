package com.lawfirm.model.log.vo;

import com.lawfirm.model.base.vo.BaseVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 日志统计视图对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogStatVO extends BaseVO {

    /**
     * 总日志数
     */
    private Long totalCount;

    /**
     * 今日日志数
     */
    private Long todayCount;

    /**
     * 异常日志数
     */
    private Long errorCount;

    /**
     * 按日志类型统计
     */
    private Map<String, Long> logTypeStats;

    /**
     * 按业务类型统计
     */
    private Map<String, Long> businessTypeStats;

    /**
     * 按操作类型统计
     */
    private Map<String, Long> operateTypeStats;

    /**
     * 按日志级别统计
     */
    private Map<String, Long> logLevelStats;

    /**
     * 按模块统计
     */
    private Map<String, Long> moduleStats;

    /**
     * 按操作人统计
     */
    private Map<String, Long> operatorStats;

    /**
     * 按IP地址统计
     */
    private Map<String, Long> ipStats;

    /**
     * 按地区统计
     */
    private Map<String, Long> locationStats;

    /**
     * 按浏览器统计
     */
    private Map<String, Long> browserStats;

    /**
     * 按操作系统统计
     */
    private Map<String, Long> osStats;

    /**
     * 每日统计数据
     */
    private List<DailyStats> dailyStats;

    /**
     * 平均响应时间(毫秒)
     */
    private Double avgResponseTime;

    /**
     * 最大响应时间(毫秒)
     */
    private Long maxResponseTime;

    /**
     * 最小响应时间(毫秒)
     */
    private Long minResponseTime;

    /**
     * 日统计数据内部类
     */
    @Data
    @Accessors(chain = true)
    public static class DailyStats {
        /**
         * 日期
         */
        private String date;

        /**
         * 总数
         */
        private Long total;

        /**
         * 成功数
         */
        private Long success;

        /**
         * 失败数
         */
        private Long fail;

        /**
         * 平均响应时间
         */
        private Double avgTime;
    }
} 