package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.finance.entity.FeeRecord;
import com.lawfirm.finance.mapper.FeeRecordMapper;
import com.lawfirm.finance.repository.FeeRecordRepository;
import com.lawfirm.finance.service.FeeRecordService;
import com.lawfirm.finance.dto.request.FeeRecordAddRequest;
import com.lawfirm.finance.dto.request.FeeRecordUpdateRequest;
import com.lawfirm.finance.dto.request.FeeRecordQueryRequest;
import com.lawfirm.finance.dto.response.FeeRecordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 收费记录Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FeeRecordServiceImpl extends ServiceImpl<FeeRecordMapper, FeeRecord> implements FeeRecordService {

    private final FeeRecordRepository feeRecordRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addFeeRecord(FeeRecordAddRequest request) {
        // 参数校验
        checkAddRequest(request);
        
        // 构建实体
        FeeRecord feeRecord = new FeeRecord();
        BeanUtils.copyProperties(request, feeRecord);
        feeRecord.setFeeStatus("UNPAID");
        feeRecord.setPaidAmount(BigDecimal.ZERO);
        
        // 保存记录
        save(feeRecord);
        return feeRecord.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFeeRecord(FeeRecordUpdateRequest request) {
        // 参数校验
        checkUpdateRequest(request);
        
        // 获取原记录
        FeeRecord feeRecord = getById(request.getId());
        if (feeRecord == null) {
            throw new BusinessException("收费记录不存在");
        }
        
        // 更新记录
        BeanUtils.copyProperties(request, feeRecord);
        updateById(feeRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteFeeRecord(Long id) {
        // 检查记录是否存在
        FeeRecord feeRecord = getById(id);
        if (feeRecord == null) {
            throw new BusinessException("收费记录不存在");
        }
        
        // 检查是否可以删除
        if (!"UNPAID".equals(feeRecord.getFeeStatus())) {
            throw new BusinessException("已支付的收费记录不能删除");
        }
        
        // 删除记录
        removeById(id);
    }

    @Override
    public IPage<FeeRecordResponse> pageFeeRecords(FeeRecordQueryRequest request) {
        // 构建查询条件
        LambdaQueryWrapper<FeeRecord> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (request.getFeeType() != null) {
            wrapper.eq(FeeRecord::getFeeType, request.getFeeType());
        }
        if (request.getFeeStatus() != null) {
            wrapper.eq(FeeRecord::getFeeStatus, request.getFeeStatus());
        }
        if (request.getCaseId() != null) {
            wrapper.eq(FeeRecord::getCaseId, request.getCaseId());
        }
        if (request.getClientId() != null) {
            wrapper.eq(FeeRecord::getClientId, request.getClientId());
        }
        if (request.getLawFirmId() != null) {
            wrapper.eq(FeeRecord::getLawFirmId, request.getLawFirmId());
        }
        if (request.getMinAmount() != null) {
            wrapper.ge(FeeRecord::getAmount, request.getMinAmount());
        }
        if (request.getMaxAmount() != null) {
            wrapper.le(FeeRecord::getAmount, request.getMaxAmount());
        }
        if (request.getStartTime() != null) {
            wrapper.ge(FeeRecord::getCreateTime, request.getStartTime());
        }
        if (request.getEndTime() != null) {
            wrapper.le(FeeRecord::getCreateTime, request.getEndTime());
        }
        
        // 执行分页查询
        Page<FeeRecord> page = new Page<>(request.getPageNum(), request.getPageSize());
        page = page(page, wrapper);
        
        // 转换结果
        return page.convert(this::convertToResponse);
    }

    @Override
    public FeeRecordResponse getFeeRecordById(Long id) {
        FeeRecord feeRecord = getById(id);
        if (feeRecord == null) {
            throw new BusinessException("收费记录不存在");
        }
        return convertToResponse(feeRecord);
    }

    @Override
    public BigDecimal getUnpaidAmount(Long clientId) {
        // 查询未支付金额
        LambdaQueryWrapper<FeeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FeeRecord::getClientId, clientId)
               .eq(FeeRecord::getFeeStatus, "UNPAID");
        
        List<FeeRecord> records = list(wrapper);
        return records.stream()
                     .map(FeeRecord::getAmount)
                     .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<FeeRecordResponse> getFeeStatistics(Long lawFirmId, String startTime, String endTime) {
        // 实现收费统计逻辑
        LambdaQueryWrapper<FeeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(FeeRecord::getLawFirmId, lawFirmId);
        
        if (startTime != null && !startTime.trim().isEmpty()) {
            wrapper.ge(FeeRecord::getCreateTime, LocalDateTime.parse(startTime));
        }
        if (endTime != null && !endTime.trim().isEmpty()) {
            wrapper.le(FeeRecord::getCreateTime, LocalDateTime.parse(endTime));
        }
        
        List<FeeRecord> records = list(wrapper);
        return records.stream()
                     .map(this::convertToResponse)
                     .collect(Collectors.toList());
    }

    private void checkAddRequest(FeeRecordAddRequest request) {
        // 添加请求参数校验逻辑
        if (request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("收费金额必须大于0");
        }
        if (request.getFeeType() == null || request.getFeeType().trim().isEmpty()) {
            throw new BusinessException("收费类型不能为空");
        }
        if (request.getClientId() == null) {
            throw new BusinessException("客户ID不能为空");
        }
        if (request.getLawFirmId() == null) {
            throw new BusinessException("律所ID不能为空");
        }
    }

    private void checkUpdateRequest(FeeRecordUpdateRequest request) {
        // 更新请求参数校验逻辑
        if (request.getId() == null) {
            throw new BusinessException("记录ID不能为空");
        }
        if (request.getAmount() != null && request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException("收费金额必须大于0");
        }
    }

    private FeeRecordResponse convertToResponse(FeeRecord entity) {
        if (entity == null) {
            return null;
        }
        FeeRecordResponse response = new FeeRecordResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    @Override
    @Transactional
    public FeeRecord updatePaymentStatus(Long id, String status, BigDecimal paidAmount) {
        FeeRecord feeRecord = getFeeRecord(id);
        
        // 更新支付状态
        feeRecord.setFeeStatus(status);
        feeRecord.setPaidAmount(paidAmount);
        
        if (paidAmount.compareTo(feeRecord.getAmount()) >= 0) {
            feeRecord.setFeeStatus("PAID");
            feeRecord.setPaymentTime(LocalDateTime.now());
        } else if (paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            feeRecord.setFeeStatus("PARTIAL");
        }
        
        return feeRecordRepository.save(feeRecord);
    }

    @Override
    public BigDecimal calculateTotalAmount(Long lawFirmId) {
        return feeRecordRepository.findByLawFirmId(lawFirmId).stream()
                .map(FeeRecord::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal calculateTotalPaidAmount(Long lawFirmId) {
        return feeRecordRepository.findByLawFirmId(lawFirmId).stream()
                .map(FeeRecord::getPaidAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public void payFee(Long feeId, BigDecimal amount) {
        FeeRecord feeRecord = getFeeRecord(feeId);
        BigDecimal newPaidAmount = feeRecord.getPaidAmount().add(amount);
        
        // 更新支付状态和已支付金额
        if (newPaidAmount.compareTo(feeRecord.getAmount()) >= 0) {
            updatePaymentStatus(feeId, "PAID", newPaidAmount);
        } else {
            updatePaymentStatus(feeId, "PARTIAL", newPaidAmount);
        }
    }

    @Override
    public List<FeeRecord> getLawFirmFeeRecords(Long lawFirmId) {
        return feeRecordRepository.findByLawFirmId(lawFirmId);
    }

    @Override
    public List<FeeRecord> getLawFirmFeeRecordsByTimeRange(Long lawFirmId, LocalDateTime startTime, LocalDateTime endTime) {
        return feeRecordRepository.findByLawFirmIdAndCreateTimeBetween(lawFirmId, startTime, endTime);
    }

    @Override
    public BigDecimal getTotalIncome(Long lawFirmId) {
        return calculateTotalAmount(lawFirmId);
    }

    @Override
    public BigDecimal getTotalPaidAmount(Long lawFirmId) {
        return calculateTotalPaidAmount(lawFirmId);
    }
} 