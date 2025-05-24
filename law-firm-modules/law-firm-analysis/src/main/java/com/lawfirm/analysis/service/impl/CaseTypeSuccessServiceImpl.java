package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.dto.CaseTypeSuccessRequestDTO;
import com.lawfirm.model.analysis.service.ICaseTypeSuccessService;
import com.lawfirm.model.analysis.vo.CaseTypeSuccessVO;
import com.lawfirm.model.cases.mapper.base.CaseMapper;
import com.lawfirm.model.cases.enums.base.CaseTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.cache.annotation.Cacheable;

@Service("caseTypeSuccessServiceImpl")
public class CaseTypeSuccessServiceImpl implements ICaseTypeSuccessService {
    @Autowired
    private CaseMapper caseMapper;

    @Override
    @Cacheable(value = "analysisCaseTypeSuccessCache", key = "'caseTypeSuccess:' + #request.hashCode()", cacheManager = "commonCacheRedisTemplate")
    public List<CaseTypeSuccessVO> analyze(CaseTypeSuccessRequestDTO request) {
        // 查询各类型案件总数、胜诉数、和解数、败诉数
        List<Map<String, Object>> totalList = caseMapper.countCasesByType();
        List<Map<String, Object>> winList = caseMapper.countWonCasesByType();
        List<Map<String, Object>> settleList = caseMapper.countSettledCasesByType();
        List<Map<String, Object>> loseList = caseMapper.countLostCasesByType();

        // 组装类型->数量映射
        Map<Integer, Integer> totalMap = new HashMap<>();
        Map<Integer, Integer> winMap = new HashMap<>();
        Map<Integer, Integer> settleMap = new HashMap<>();
        Map<Integer, Integer> loseMap = new HashMap<>();
        for (Map<String, Object> row : totalList) {
            Integer type = (Integer) row.get("case_type");
            Integer count = ((Number) row.get("case_count")).intValue();
            totalMap.put(type, count);
        }
        for (Map<String, Object> row : winList) {
            Integer type = (Integer) row.get("case_type");
            Integer count = ((Number) row.get("win_count")).intValue();
            winMap.put(type, count);
        }
        for (Map<String, Object> row : settleList) {
            Integer type = (Integer) row.get("case_type");
            Integer count = ((Number) row.get("settle_count")).intValue();
            settleMap.put(type, count);
        }
        for (Map<String, Object> row : loseList) {
            Integer type = (Integer) row.get("case_type");
            Integer count = ((Number) row.get("lose_count")).intValue();
            loseMap.put(type, count);
        }
        List<CaseTypeSuccessVO> result = new ArrayList<>();
        for (CaseTypeEnum typeEnum : CaseTypeEnum.values()) {
            Integer type = typeEnum.getValue();
            int total = totalMap.getOrDefault(type, 0);
            int win = winMap.getOrDefault(type, 0);
            int settle = settleMap.getOrDefault(type, 0);
            int lose = loseMap.getOrDefault(type, 0);
            CaseTypeSuccessVO vo = new CaseTypeSuccessVO();
            vo.setCaseType(typeEnum.getDescription());
            vo.setTotalCount(total);
            vo.setWinCount(win);
            vo.setWinRate(total == 0 ? 0.0 : win * 1.0 / total);
            vo.setSettleCount(settle);
            vo.setLoseCount(lose);
            result.add(vo);
        }
        return result;
    }
} 