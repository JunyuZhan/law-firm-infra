package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.entity.AnalysisTask;
import com.lawfirm.model.analysis.mapper.AnalysisTaskMapper;
import com.lawfirm.model.analysis.service.IAnalysisTaskService;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("analysisTaskServiceImpl")
public class AnalysisTaskServiceImpl extends BaseServiceImpl<AnalysisTaskMapper, AnalysisTask> implements IAnalysisTaskService {
    // 可扩展自定义方法
} 