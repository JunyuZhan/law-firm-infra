package com.lawfirm.model.contract.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 合同统计响应DTO
 */
@Data
public class ContractStatisticsResponse {

    // 合同数量统计
    private Long totalCount;           // 合同总数
    private Long activeCount;          // 生效合同数
    private Long draftCount;           // 草稿数量
    private Long pendingApprovalCount; // 待审批数量
    private Long expiredCount;         // 已过期数量
    private Long terminatedCount;      // 已终止数量
    private Long archivedCount;        // 已归档数量

    // 合同金额统计
    private BigDecimal totalAmount;     // 合同总金额
    private BigDecimal paidAmount;      // 已付总金额
    private BigDecimal unpaidAmount;    // 未付总金额
    private BigDecimal overdueAmount;   // 逾期金额

    // 按类型统计
    private Map<String, Long> typeDistribution;        // 合同类型分布
    private Map<String, Long> statusDistribution;      // 合同状态分布
    private Map<String, Long> priorityDistribution;    // 优先级分布
    private Map<String, BigDecimal> monthlyAmount;     // 月度金额统计

    // 审批统计
    private Long approvalPendingCount;  // 待审批数量
    private Long approvalPassedCount;   // 已通过数量
    private Long approvalRejectedCount; // 已驳回数量
    private Double averageApprovalTime; // 平均审批时间（小时）

    // 时间统计
    private Double averageProcessTime;  // 平均处理时间（天）
    private Long expiringCount;         // 即将到期数量
    private Long overdueCount;          // 逾期数量

    // 趋势统计
    private List<TimeSeriesData> contractTrend;     // 合同数量趋势
    private List<TimeSeriesData> amountTrend;       // 合同金额趋势
    private List<TimeSeriesData> approvalTimeTrend; // 审批时间趋势

    /**
     * 时间序列数据
     */
    @Data
    public static class TimeSeriesData {
        private String time;     // 时间点
        private String type;     // 数据类型
        private BigDecimal value;// 数值
    }

    // 排名统计
    private List<RankingData> clientRanking;    // 客户排名
    private List<RankingData> lawFirmRanking;   // 律所排名
    private List<RankingData> employeeRanking;  // 员工排名

    /**
     * 排名数据
     */
    @Data
    public static class RankingData {
        private Long id;           // ID
        private String name;       // 名称
        private Long count;        // 数量
        private BigDecimal amount; // 金额
        private Integer ranking;   // 排名
    }
}

