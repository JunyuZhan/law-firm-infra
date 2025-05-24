package com.lawfirm.analysis.service.impl;

import com.lawfirm.model.analysis.dto.FinanceAnalysisRequestDTO;
import com.lawfirm.model.analysis.service.IFinanceAnalysisService;
import com.lawfirm.model.analysis.vo.FinanceAnalysisVO;
import com.lawfirm.model.cases.mapper.business.CaseFeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Date;
import org.springframework.cache.annotation.Cacheable;

@Service("financeAnalysisServiceImpl")
public class FinanceAnalysisServiceImpl implements IFinanceAnalysisService {
    @Autowired
    private CaseFeeMapper caseFeeMapper;

    @Override
    @Cacheable(value = "analysisFinanceCache", key = "'finance:' + #request.hashCode()", cacheManager = "commonCacheRedisTemplate")
    public List<FinanceAnalysisVO> analyze(FinanceAnalysisRequestDTO request) {
        List<FinanceAnalysisVO> list = new ArrayList<>();
        BigDecimal totalIncome = caseFeeMapper.sumAllFeeAmount();
        BigDecimal totalPaid = caseFeeMapper.sumAllPaidAmount();
        BigDecimal totalUnpaid = caseFeeMapper.sumAllUnpaidAmount();
        FinanceAnalysisVO incomeVO = new FinanceAnalysisVO();
        incomeVO.setType("收入");
        incomeVO.setAmount(totalIncome == null ? 0.0 : totalIncome.doubleValue());
        list.add(incomeVO);
        FinanceAnalysisVO paidVO = new FinanceAnalysisVO();
        paidVO.setType("已回款");
        paidVO.setAmount(totalPaid == null ? 0.0 : totalPaid.doubleValue());
        list.add(paidVO);
        FinanceAnalysisVO unpaidVO = new FinanceAnalysisVO();
        unpaidVO.setType("未回款");
        unpaidVO.setAmount(totalUnpaid == null ? 0.0 : totalUnpaid.doubleValue());
        list.add(unpaidVO);

        // 按月趋势分析
        Date start = request.getStartDate();
        Date end = request.getEndDate();
        // 收入趋势
        for (Map<String, Object> row : caseFeeMapper.sumFeeAmountByMonth(start, end)) {
            FinanceAnalysisVO vo = new FinanceAnalysisVO();
            vo.setType("收入-" + row.get("month"));
            vo.setAmount(row.get("amount") == null ? 0.0 : ((Number) row.get("amount")).doubleValue());
            list.add(vo);
        }
        // 回款趋势
        for (Map<String, Object> row : caseFeeMapper.sumPaidAmountByMonth(start, end)) {
            FinanceAnalysisVO vo = new FinanceAnalysisVO();
            vo.setType("已回款-" + row.get("month"));
            vo.setAmount(row.get("amount") == null ? 0.0 : ((Number) row.get("amount")).doubleValue());
            list.add(vo);
        }
        return list;
    }
} 