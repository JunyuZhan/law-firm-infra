package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.dto.ClientAnalysisRequestDTO;
import com.lawfirm.model.analysis.service.IClientAnalysisService;
import com.lawfirm.model.analysis.vo.ClientAnalysisVO;
import com.lawfirm.model.cases.mapper.base.CaseMapper;
import com.lawfirm.model.client.mapper.ClientMapper;
import com.lawfirm.model.client.entity.base.Client;
import com.lawfirm.model.client.vo.ClientVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.springframework.cache.annotation.Cacheable;

@Service("clientAnalysisServiceImpl")
public class ClientAnalysisServiceImpl implements IClientAnalysisService {
    @Autowired
    private CaseMapper caseMapper;
    @Autowired
    private ClientMapper clientMapper;

    @Override
    @Cacheable(value = "analysisClientCache", key = "'client:' + #request.hashCode()", cacheManager = "commonCacheRedisTemplate")
    public List<ClientAnalysisVO> analyze(ClientAnalysisRequestDTO request) {
        List<Map<String, Object>> caseList = caseMapper.countCasesByClient();
        List<Map<String, Object>> amountList = caseMapper.sumCaseAmountByClient();
        Map<Long, Integer> caseMap = new HashMap<>();
        Map<Long, Double> amountMap = new HashMap<>();
        Map<Long, String> nameMap = new HashMap<>();
        for (Map<String, Object> row : caseList) {
            Long id = row.get("client_id") == null ? null : ((Number) row.get("client_id")).longValue();
            Integer count = ((Number) row.get("case_count")).intValue();
            String name = (String) row.get("client_name");
            caseMap.put(id, count);
            nameMap.put(id, name);
        }
        for (Map<String, Object> row : amountList) {
            Long id = row.get("client_id") == null ? null : ((Number) row.get("client_id")).longValue();
            Double amount = row.get("total_amount") == null ? 0.0 : ((Number) row.get("total_amount")).doubleValue();
            amountMap.put(id, amount);
        }
        List<ClientAnalysisVO> result = new ArrayList<>();
        for (Long id : caseMap.keySet()) {
            ClientAnalysisVO vo = new ClientAnalysisVO();
            ClientVO clientVO = new ClientVO();
            clientVO.setClientName(nameMap.get(id));
            vo.setClient(clientVO);
            vo.setCaseCount(caseMap.get(id));
            vo.setContribution(amountMap.getOrDefault(id, 0.0));
            result.add(vo);
        }
        return result;
    }
} 