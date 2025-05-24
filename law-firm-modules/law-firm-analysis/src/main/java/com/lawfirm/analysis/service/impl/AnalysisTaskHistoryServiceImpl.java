package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.entity.AnalysisTaskHistory;
import com.lawfirm.model.analysis.mapper.AnalysisTaskHistoryMapper;
import com.lawfirm.model.analysis.service.IAnalysisTaskHistoryService;
import com.lawfirm.model.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("analysisTaskHistoryServiceImpl")
public class AnalysisTaskHistoryServiceImpl extends BaseServiceImpl<AnalysisTaskHistoryMapper, AnalysisTaskHistory> implements IAnalysisTaskHistoryService {
    // 可扩展自定义方法
} 