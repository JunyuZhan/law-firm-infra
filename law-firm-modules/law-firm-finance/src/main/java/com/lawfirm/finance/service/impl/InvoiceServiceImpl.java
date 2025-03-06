package com.lawfirm.finance.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.finance.entity.Invoice;
import com.lawfirm.finance.mapper.InvoiceMapper;
import com.lawfirm.finance.service.IInvoiceService;
import com.lawfirm.finance.dto.request.InvoiceAddRequest;
import com.lawfirm.finance.dto.request.InvoiceUpdateRequest;
import com.lawfirm.finance.dto.request.InvoiceQueryRequest;
import com.lawfirm.finance.dto.response.InvoiceResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发票Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements IInvoiceService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addInvoice(InvoiceAddRequest request) {
        // 参数校验
        checkAddRequest(request);
        
        // 检查发票号是否重复
        checkInvoiceNoExists(request.getInvoiceNo());
        
        // 构建实体
        Invoice invoice = new Invoice();
        BeanUtils.copyProperties(request, invoice);
        invoice.setStatus(1);
        
        // 保存记录
        save(invoice);
        return invoice.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInvoice(InvoiceUpdateRequest request) {
        // 参数校验
        checkUpdateRequest(request);
        
        // 获取原记录
        Invoice invoice = getById(request.getId());
        if (invoice == null) {
            throw new BusinessException("发票不存在");
        }
        
        // 检查状态
        if (invoice.getStatus() == 2) {
            throw new BusinessException("已作废的发票不能修改");
        }
        
        // 更新记录
        BeanUtils.copyProperties(request, invoice);
        updateById(invoice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteInvoice(Long id) {
        // 检查记录是否存在
        Invoice invoice = getById(id);
        if (invoice == null) {
            throw new BusinessException("发票不存在");
        }
        
        // 检查是否可以删除
        if (invoice.getStatus() == 1) {
            throw new BusinessException("正常状态的发票不能删除");
        }
        
        // 删除记录
        removeById(id);
    }

    @Override
    public IPage<InvoiceResponse> pageInvoices(InvoiceQueryRequest request) {
        // 构建查询条件
        LambdaQueryWrapper<Invoice> wrapper = new LambdaQueryWrapper<>();
        // TODO: 添加查询条件
        
        // 执行分页查询
        Page<Invoice> page = new Page<>(request.getPageNum(), request.getPageSize());
        page = page(page, wrapper);
        
        // 转换结果
        return page.convert(this::convertToResponse);
    }

    @Override
    public InvoiceResponse getInvoiceById(Long id) {
        Invoice invoice = getById(id);
        if (invoice == null) {
            throw new BusinessException("发票不存在");
        }
        return convertToResponse(invoice);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invalidateInvoice(Long id, String reason) {
        // 获取记录
        Invoice invoice = getById(id);
        if (invoice == null) {
            throw new BusinessException("发票不存在");
        }
        
        // 检查状态
        if (invoice.getStatus() == 2) {
            throw new BusinessException("发票已作废");
        }
        
        // 更新状态
        invoice.setStatus(2);
        invoice.setRemark(reason);
        updateById(invoice);
    }

    @Override
    public List<InvoiceResponse> getInvoiceStatistics(String startTime, String endTime) {
        // TODO: 实现发票统计逻辑
        return null;
    }

    private void checkAddRequest(InvoiceAddRequest request) {
        // TODO: 添加请求参数校验逻辑
    }

    private void checkUpdateRequest(InvoiceUpdateRequest request) {
        // TODO: 更新请求参数校验逻辑
    }

    private void checkInvoiceNoExists(String invoiceNo) {
        LambdaQueryWrapper<Invoice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Invoice::getInvoiceNo, invoiceNo);
        if (count(wrapper) > 0) {
            throw new BusinessException("发票号已存在");
        }
    }

    private InvoiceResponse convertToResponse(Invoice entity) {
        if (entity == null) {
            return null;
        }
        InvoiceResponse response = new InvoiceResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
} 