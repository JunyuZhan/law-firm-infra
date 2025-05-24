package com.lawfirm.schedule.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.extern.slf4j.Slf4j;

import com.lawfirm.model.ai.service.AiService;
import com.lawfirm.model.ai.dto.AIRequestDTO;
import com.lawfirm.model.ai.vo.AIResponseVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日程AI管理器
 * 负责与core-ai模块集成，实现日程相关的AI辅助功能。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleAIManager {
    @Qualifier("coreAIServiceImpl")
    private final AiService aiService;

    /** 日程智能摘要 */
    public String summarize(String content, Integer maxLength) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("SCHEDULE_SUMMARIZE");
        req.setModelName("schedule-summary");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        params.put("max_length", maxLength != null ? maxLength : 200);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (String) resp.getData().get("summary") : null;
    }
    /** 日程冲突检测与优化建议 */
    public Map<String, Object> detectConflict(List<Map<String, Object>> schedules) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("SCHEDULE_CONFLICT");
        req.setModelName("schedule-conflict");
        Map<String, Object> params = new HashMap<>();
        params.put("schedules", schedules);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (Map<String, Object>) resp.getData().get("conflictResult") : Map.of();
    }
    /** 日程智能分类 */
    public Map<String, Double> classify(String content) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("SCHEDULE_CLASSIFY");
        req.setModelName("schedule-classify");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (Map<String, Double>) resp.getData().get("classification") : Map.of();
    }
    /** 日程标签推荐 */
    public List<String> recommendTags(String content, Integer limit) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("SCHEDULE_TAG_RECOMMEND");
        req.setModelName("schedule-tag");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        params.put("limit", limit != null ? limit : 5);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (List<String>) resp.getData().get("tags") : List.of();
    }
    /** 日程推荐 */
    public List<Map<String, Object>> recommendSchedules(String content, Integer limit) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("SCHEDULE_RECOMMEND");
        req.setModelName("schedule-recommend");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        params.put("limit", limit != null ? limit : 5);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (List<Map<String, Object>>) resp.getData().get("schedules") : List.of();
    }
    /** 日程智能问答 */
    public String qa(String question, String context) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("SCHEDULE_QA");
        req.setModelName("schedule-qa");
        Map<String, Object> params = new HashMap<>();
        params.put("question", question);
        params.put("context", context);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (String) resp.getData().get("answer") : null;
    }
    /** 日程自动生成 */
    public String generate(Map<String, Object> elements) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("SCHEDULE_GENERATE");
        req.setModelName("schedule-generate");
        req.setParams(elements);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (String) resp.getData().get("schedule") : null;
    }
} 