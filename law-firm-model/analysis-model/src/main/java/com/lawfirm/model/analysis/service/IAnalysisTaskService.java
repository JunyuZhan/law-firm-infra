package com.lawfirm.model.analysis.service;

import com.lawfirm.model.analysis.entity.AnalysisTask;
import com.lawfirm.model.base.service.BaseService;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 分析任务服务接口
 */
@Schema(description = "分析任务服务接口")
public interface IAnalysisTaskService extends BaseService<AnalysisTask> {
    // 可扩展自定义方法
} 