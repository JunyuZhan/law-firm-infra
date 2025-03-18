package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lawfirm.common.security.context.SecurityContext;
import com.lawfirm.model.finance.entity.Receivable;
import com.lawfirm.model.finance.enums.ReceivableStatusEnum;
import com.lawfirm.model.finance.mapper.ReceivableMapper;
import com.lawfirm.model.finance.service.ReceivableService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReceivableServiceImpl implements ReceivableService {

    private final ReceivableMapper receivableMapper;
    private final SecurityContext securityContext;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createReceivable(Receivable receivable) {
        receivable.setStatusEnum(ReceivableStatusEnum.PENDING);
        receivable.setCreateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivableMapper.insert(receivable);
        return receivable.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateReceivable(Receivable receivable) {
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        return receivableMapper.updateById(receivable) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteReceivable(Long receivableId) {
        return receivableMapper.deleteById(receivableId) > 0;
    }

    @Override
    public Receivable getReceivableById(Long receivableId) {
        return receivableMapper.selectById(receivableId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateReceivableStatus(Long receivableId, ReceivableStatusEnum status, String remark) {
        Receivable receivable = receivableMapper.selectById(receivableId);
        if (receivable == null) {
            throw new IllegalArgumentException("应收账款不存在");
        }
        receivable.setStatusEnum(status);
        receivable.setRemark(remark);
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        return receivableMapper.updateById(receivable) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long recordReceipt(Long receivableId, BigDecimal amount, Long accountId, 
                            LocalDateTime receiveDate, String remark) {
        Receivable receivable = receivableMapper.selectById(receivableId);
        if (receivable == null) {
            throw new IllegalArgumentException("应收账款不存在");
        }
        
        // 更新已收金额
        BigDecimal newReceivedAmount = receivable.getReceivedAmount().add(amount);
        receivable.setReceivedAmount(newReceivedAmount);
        receivable.setUnreceivedAmount(receivable.getTotalAmount().subtract(newReceivedAmount));
        receivable.setLastReceiptDate(receiveDate);
        
        // 更新状态
        if (newReceivedAmount.compareTo(receivable.getTotalAmount()) >= 0) {
            receivable.setStatusEnum(ReceivableStatusEnum.COMPLETED);
        } else if (newReceivedAmount.compareTo(BigDecimal.ZERO) > 0) {
            receivable.setStatusEnum(ReceivableStatusEnum.PARTIAL);
        }
        
        receivable.setRemark(remark);
        receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
        receivableMapper.updateById(receivable);
        
        return receivableId;
    }

    @Override
    public List<Receivable> listReceivables(Long contractId, Long clientId, 
                                          ReceivableStatusEnum status, Integer overdueDays) {
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contractId != null, Receivable::getContractId, contractId)
                .eq(clientId != null, Receivable::getClientId, clientId)
                .eq(status != null, Receivable::getStatusCode, status.getCode())
                .ge(overdueDays != null, Receivable::getOverdueDays, overdueDays)
                .orderByDesc(Receivable::getCreateTime);
        return receivableMapper.selectList(wrapper);
    }

    @Override
    public IPage<Receivable> pageReceivables(IPage<Receivable> page, Long contractId, Long clientId, 
                                           ReceivableStatusEnum status, Integer overdueDays) {
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contractId != null, Receivable::getContractId, contractId)
                .eq(clientId != null, Receivable::getClientId, clientId)
                .eq(status != null, Receivable::getStatusCode, status.getCode())
                .ge(overdueDays != null, Receivable::getOverdueDays, overdueDays)
                .orderByDesc(Receivable::getCreateTime);
        return receivableMapper.selectPage(page, wrapper);
    }

    @Override
    public List<Receivable> listReceivablesByContract(Long contractId) {
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Receivable::getContractId, contractId)
                .orderByDesc(Receivable::getCreateTime);
        return receivableMapper.selectList(wrapper);
    }

    @Override
    public List<Receivable> listReceivablesByClient(Long clientId) {
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Receivable::getClientId, clientId)
                .orderByDesc(Receivable::getCreateTime);
        return receivableMapper.selectList(wrapper);
    }

    @Override
    public BigDecimal sumReceivableAmount(Long contractId, Long clientId, ReceivableStatusEnum status) {
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(contractId != null, Receivable::getContractId, contractId)
                .eq(clientId != null, Receivable::getClientId, clientId)
                .eq(status != null, Receivable::getStatusCode, status.getCode());
        List<Receivable> receivables = receivableMapper.selectList(wrapper);
        return receivables.stream()
                .map(Receivable::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal sumReceivedAmount(Long contractId, Long clientId) {
        return sumReceivableAmount(contractId, clientId, ReceivableStatusEnum.COMPLETED);
    }

    @Override
    public BigDecimal sumUnreceivedAmount(Long contractId, Long clientId) {
        return sumReceivableAmount(contractId, clientId, ReceivableStatusEnum.PENDING);
    }

    @Override
    public Map<String, BigDecimal> agingAnalysis(Long clientId) {
        Map<String, BigDecimal> result = new HashMap<>();
        result.put("0-30天", BigDecimal.ZERO);
        result.put("31-60天", BigDecimal.ZERO);
        result.put("61-90天", BigDecimal.ZERO);
        result.put("90天以上", BigDecimal.ZERO);
        
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(clientId != null, Receivable::getClientId, clientId)
                .eq(Receivable::getStatusCode, ReceivableStatusEnum.PENDING.getCode());
        List<Receivable> receivables = receivableMapper.selectList(wrapper);
        
        LocalDateTime now = LocalDateTime.now();
        for (Receivable receivable : receivables) {
            long days = ChronoUnit.DAYS.between(receivable.getExpectedReceiptDate(), now);
            if (days <= 30) {
                result.put("0-30天", result.get("0-30天").add(receivable.getUnreceivedAmount()));
            } else if (days <= 60) {
                result.put("31-60天", result.get("31-60天").add(receivable.getUnreceivedAmount()));
            } else if (days <= 90) {
                result.put("61-90天", result.get("61-90天").add(receivable.getUnreceivedAmount()));
            } else {
                result.put("90天以上", result.get("90天以上").add(receivable.getUnreceivedAmount()));
            }
        }
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOverdueStatus() {
        LocalDateTime now = LocalDateTime.now();
        LambdaQueryWrapper<Receivable> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Receivable::getStatusCode, ReceivableStatusEnum.PENDING.getCode())
                .lt(Receivable::getExpectedReceiptDate, now);
        List<Receivable> receivables = receivableMapper.selectList(wrapper);
        
        int count = 0;
        for (Receivable receivable : receivables) {
            long days = ChronoUnit.DAYS.between(receivable.getExpectedReceiptDate(), now);
            receivable.setOverdueDays((int) days);
            receivable.setStatusEnum(ReceivableStatusEnum.OVERDUE);
            receivable.setUpdateBy(String.valueOf(securityContext.getCurrentUserId()));
            if (receivableMapper.updateById(receivable) > 0) {
                count++;
            }
        }
        
        return count;
    }
}