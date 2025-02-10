package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.finance.entity.ExpenseRecord;
import com.lawfirm.finance.mapper.ExpenseRecordMapper;
import com.lawfirm.finance.service.IExpenseRecordService;
import com.lawfirm.finance.dto.request.ExpenseRecordAddRequest;
import com.lawfirm.finance.dto.request.ExpenseRecordUpdateRequest;
import com.lawfirm.finance.dto.request.ExpenseRecordQueryRequest;
import com.lawfirm.finance.dto.response.ExpenseRecordResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 支出记录Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExpenseRecordServiceImpl extends ServiceImpl<ExpenseRecordMapper, ExpenseRecord> implements IExpenseRecordService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addExpenseRecord(ExpenseRecordAddRequest request) {
        // 参数校验
        checkAddRequest(request);
        
        // 构建实体
        ExpenseRecord expenseRecord = new ExpenseRecord();
        BeanUtils.copyProperties(request, expenseRecord);
        expenseRecord.setExpenseStatus("PENDING");
        
        // 保存记录
        save(expenseRecord);
        return expenseRecord.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateExpenseRecord(ExpenseRecordUpdateRequest request) {
        // 参数校验
        checkUpdateRequest(request);
        
        // 获取原记录
        ExpenseRecord expenseRecord = getById(request.getId());
        if (expenseRecord == null) {
            throw new BusinessException("支出记录不存在");
        }
        
        // 检查状态
        if ("PAID".equals(expenseRecord.getExpenseStatus())) {
            throw new BusinessException("已支付的支出记录不能修改");
        }
        
        // 更新记录
        BeanUtils.copyProperties(request, expenseRecord);
        updateById(expenseRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteExpenseRecord(Long id) {
        // 检查记录是否存在
        ExpenseRecord expenseRecord = getById(id);
        if (expenseRecord == null) {
            throw new BusinessException("支出记录不存在");
        }
        
        // 检查是否可以删除
        if (!"PENDING".equals(expenseRecord.getExpenseStatus())) {
            throw new BusinessException("非待处理状态的支出记录不能删除");
        }
        
        // 删除记录
        removeById(id);
    }

    @Override
    public IPage<ExpenseRecordResponse> pageExpenseRecords(ExpenseRecordQueryRequest request) {
        // 构建查询条件
        LambdaQueryWrapper<ExpenseRecord> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件
        
        // 执行分页查询
        Page<ExpenseRecord> page = new Page<>(request.getPageNum(), request.getPageSize());
        page = page(page, wrapper);
        
        // 转换结果
        return page.convert(this::convertToResponse);
    }

    @Override
    public ExpenseRecordResponse getExpenseRecordById(Long id) {
        ExpenseRecord expenseRecord = getById(id);
        if (expenseRecord == null) {
            throw new BusinessException("支出记录不存在");
        }
        return convertToResponse(expenseRecord);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveExpenseRecord(Long id, String status, String opinion) {
        // 获取记录
        ExpenseRecord expenseRecord = getById(id);
        if (expenseRecord == null) {
            throw new BusinessException("支出记录不存在");
        }
        
        // 检查状态
        if (!"PENDING".equals(expenseRecord.getExpenseStatus())) {
            throw new BusinessException("只能审批待处理状态的支出记录");
        }
        
        // 更新状态
        expenseRecord.setExpenseStatus(status);
        expenseRecord.setRemark(opinion);
        updateById(expenseRecord);
    }

    @Override
    public List<ExpenseRecordResponse> getExpenseStatistics(Long departmentId, String startTime, String endTime) {
        // TODO: 实现支出统计逻辑
        return null;
    }

    private void checkAddRequest(ExpenseRecordAddRequest request) {
        // TODO: 添加请求参数校验逻辑
    }

    private void checkUpdateRequest(ExpenseRecordUpdateRequest request) {
        // TODO: 更新请求参数校验逻辑
    }

    private ExpenseRecordResponse convertToResponse(ExpenseRecord entity) {
        if (entity == null) {
            return null;
        }
        ExpenseRecordResponse response = new ExpenseRecordResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
} 