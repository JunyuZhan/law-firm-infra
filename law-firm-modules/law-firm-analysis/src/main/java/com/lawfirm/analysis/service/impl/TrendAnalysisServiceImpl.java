package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.dto.TrendAnalysisRequestDTO;
import com.lawfirm.model.analysis.service.ITrendAnalysisService;
import com.lawfirm.model.analysis.vo.TrendAnalysisVO;
import com.lawfirm.model.cases.mapper.base.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.cache.annotation.Cacheable;

@Service("trendAnalysisServiceImpl")
public class TrendAnalysisServiceImpl implements ITrendAnalysisService {
    @Autowired
    private CaseMapper caseMapper;

    @Override
    @Cacheable(value = "analysisTrendCache", key = "'trend:' + #request.hashCode()", cacheManager = "commonCacheRedisTemplate")
    public List<TrendAnalysisVO> analyze(TrendAnalysisRequestDTO request) {
        // 仅实现案件量和结案量趋势，按月统计
        LocalDate start = request.getStartDate() == null ? null : request.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = request.getEndDate() == null ? null : request.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        List<Map<String, Object>> caseList = caseMapper.countCasesByMonth(start, end);
        List<Map<String, Object>> closedList = caseMapper.countClosedCasesByMonth(start, end);
        // 组装时间点->数值映射
        Map<String, Integer> caseMap = new java.util.HashMap<>();
        Map<String, Integer> closedMap = new java.util.HashMap<>();
        for (Map<String, Object> row : caseList) {
            String month = (String) row.get("month");
            Integer count = ((Number) row.get("case_count")).intValue();
            caseMap.put(month, count);
        }
        for (Map<String, Object> row : closedList) {
            String month = (String) row.get("month");
            Integer count = ((Number) row.get("closed_count")).intValue();
            closedMap.put(month, count);
        }
        // 合并所有月份
        java.util.Set<String> allMonths = new java.util.TreeSet<>();
        allMonths.addAll(caseMap.keySet());
        allMonths.addAll(closedMap.keySet());
        List<TrendAnalysisVO> result = new ArrayList<>();
        for (String month : allMonths) {
            TrendAnalysisVO vo = new TrendAnalysisVO();
            vo.setTimePoint(month);
            vo.setValue(caseMap.getOrDefault(month, 0).doubleValue());
            // 可扩展：vo.setYoyRate/vo.setMomRate
            result.add(vo);
            TrendAnalysisVO closedVo = new TrendAnalysisVO();
            closedVo.setTimePoint(month + "(结案)");
            closedVo.setValue(closedMap.getOrDefault(month, 0).doubleValue());
            result.add(closedVo);
        }
        return result;
    }
} 