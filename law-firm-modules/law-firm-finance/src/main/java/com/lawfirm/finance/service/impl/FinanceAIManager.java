package com.lawfirm.finance.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.extern.slf4j.Slf4j;

import com.lawfirm.model.ai.service.AiService;
import com.lawfirm.model.ai.dto.AIRequestDTO;
import com.lawfirm.model.ai.vo.AIResponseVO;

import java.util.HashMap;
import java.util.Map;

/**
 * 财务AI管理器
 * 负责与core-ai模块集成，实现财务相关的AI辅助功能。
 * 包括财务报表智能摘要、财务异常检测等。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FinanceAIManager {
    @Qualifier("coreAIServiceImpl")
    private final AiService aiService;

    /**
     * 财务报表智能摘要
     * @param reportContent 报表内容
     * @param maxLength 最大摘要长度
     * @return 摘要内容
     */
    public String generateReportSummary(String reportContent, Integer maxLength) {
        log.info("生成财务报表摘要，最大长度: {}", maxLength);
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("FINANCE_SUMMARIZE");
            requestDTO.setModelName("finance-summary");
            Map<String, Object> params = new HashMap<>();
            params.put("text", reportContent);
            params.put("max_length", maxLength != null ? maxLength : 200);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return (String) response.getData().get("summary");
            } else {
                log.error("生成财务报表摘要失败: {}", response != null ? response.getMessage() : "无响应");
                return null;
            }
        } catch (Exception e) {
            log.error("生成财务报表摘要时发生错误", e);
            return null;
        }
    }

    /**
     * 财务异常检测
     * @param reportContent 报表内容
     * @return 异常检测结果
     */
    public Map<String, Object> detectFinancialAnomalies(String reportContent) {
        log.info("检测财务报表异常");
        try {
            AIRequestDTO requestDTO = new AIRequestDTO();
            requestDTO.setAction("FINANCE_ANOMALY_DETECT");
            requestDTO.setModelName("finance-anomaly");
            Map<String, Object> params = new HashMap<>();
            params.put("text", reportContent);
            requestDTO.setParams(params);
            AIResponseVO response = aiService.process(requestDTO);
            if (response != null && response.isSuccess()) {
                return response.getData();
            } else {
                log.error("财务异常检测失败: {}", response != null ? response.getMessage() : "无响应");
                return Map.of();
            }
        } catch (Exception e) {
            log.error("财务异常检测时发生错误", e);
            return Map.of();
        }
    }
} 