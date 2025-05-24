package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.dto.LawyerPerformanceRequestDTO;
import com.lawfirm.model.analysis.service.ILawyerPerformanceService;
import com.lawfirm.model.analysis.vo.LawyerPerformanceVO;
import com.lawfirm.model.cases.mapper.base.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.cache.annotation.Cacheable;

@Service("lawyerPerformanceServiceImpl")
public class LawyerPerformanceServiceImpl implements ILawyerPerformanceService {
    @Autowired
    private CaseMapper caseMapper;

    @Override
    @Cacheable(value = "analysisLawyerPerformanceCache", key = "'lawyerPerformance:' + #request.hashCode()", cacheManager = "commonCacheRedisTemplate")
    public List<LawyerPerformanceVO> analyzePerformance(LawyerPerformanceRequestDTO request) {
        List<Map<String, Object>> totalList = caseMapper.countCasesByLawyerAll();
        List<Map<String, Object>> closedList = caseMapper.countClosedCasesByLawyerAll();
        Map<Long, Integer> totalMap = new HashMap<>();
        Map<Long, Integer> closedMap = new HashMap<>();
        Map<Long, String> nameMap = new HashMap<>();
        for (Map<String, Object> row : totalList) {
            Long id = row.get("lawyer_id") == null ? null : ((Number) row.get("lawyer_id")).longValue();
            Integer count = ((Number) row.get("case_count")).intValue();
            String name = (String) row.get("lawyer_name");
            totalMap.put(id, count);
            nameMap.put(id, name);
        }
        for (Map<String, Object> row : closedList) {
            Long id = row.get("lawyer_id") == null ? null : ((Number) row.get("lawyer_id")).longValue();
            Integer count = ((Number) row.get("closed_count")).intValue();
            closedMap.put(id, count);
        }
        List<LawyerPerformanceVO> result = new ArrayList<>();
        for (Long id : totalMap.keySet()) {
            int total = totalMap.getOrDefault(id, 0);
            int closed = closedMap.getOrDefault(id, 0);
            LawyerPerformanceVO vo = new LawyerPerformanceVO();
            vo.setLawyerId(id);
            vo.setLawyerName(nameMap.get(id));
            vo.setCaseCount(total);
            vo.setTotalWorkHours(0.0); // 如有工时字段可补充
            vo.setCloseRate(total == 0 ? 0.0 : closed * 1.0 / total);
            result.add(vo);
        }
        return result;
    }
} 