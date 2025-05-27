package com.lawfirm.task.service;

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
 * 任务AI管理器
 * 负责与core-ai模块集成，实现任务相关的AI辅助功能。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskAIManager {
    @Qualifier("coreAIServiceImpl")
    private final AiService aiService;

    /** 任务智能摘要 */
    public String summarize(String content, Integer maxLength) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("TASK_SUMMARIZE");
        req.setModelName("task-summary");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        params.put("max_length", maxLength != null ? maxLength : 200);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (String) resp.getData().get("summary") : null;
    }
    /** 任务智能分类 */
    public Map<String, Double> classify(String content) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("TASK_CLASSIFY");
        req.setModelName("task-classify");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        @SuppressWarnings("unchecked")
        Map<String, Double> result = resp != null && resp.isSuccess() ? (Map<String, Double>) resp.getData().get("classification") : Map.of();
        return result;
    }
    /** 任务标签推荐 */
    public List<String> recommendTags(String content, Integer limit) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("TASK_TAG_RECOMMEND");
        req.setModelName("task-tag");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        params.put("limit", limit != null ? limit : 5);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        @SuppressWarnings("unchecked")
        List<String> result = resp != null && resp.isSuccess() ? (List<String>) resp.getData().get("tags") : List.of();
        return result;
    }
    /** 任务推荐 */
    public List<Map<String, Object>> recommendTasks(String content, Integer limit) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("TASK_RECOMMEND");
        req.setModelName("task-recommend");
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        params.put("limit", limit != null ? limit : 5);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> result = resp != null && resp.isSuccess() ? (List<Map<String, Object>>) resp.getData().get("tasks") : List.of();
        return result;
    }
    /** 任务智能问答 */
    public String qa(String question, String context) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("TASK_QA");
        req.setModelName("task-qa");
        Map<String, Object> params = new HashMap<>();
        params.put("question", question);
        params.put("context", context);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (String) resp.getData().get("answer") : null;
    }
    /** 任务自动生成 */
    public String generate(Map<String, Object> elements) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("TASK_GENERATE");
        req.setModelName("task-generate");
        req.setParams(elements);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (String) resp.getData().get("task") : null;
    }
    /**
     * 指定模型的任务智能摘要（用于多模型并发调用）
     */
    public String summarizeWithModel(String content, String modelName, Integer maxLength) {
        AIRequestDTO req = new AIRequestDTO();
        req.setAction("TASK_SUMMARIZE");
        req.setModelName(modelName);
        Map<String, Object> params = new HashMap<>();
        params.put("text", content);
        params.put("max_length", maxLength != null ? maxLength : 200);
        req.setParams(params);
        AIResponseVO resp = aiService.process(req);
        return resp != null && resp.isSuccess() ? (String) resp.getData().get("summary") : null;
    }
} 