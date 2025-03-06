package com.lawfirm.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.analysis.mapper.AnalysisRecordMapper;
import com.lawfirm.analysis.model.dto.AnalysisRequest;
import com.lawfirm.analysis.model.dto.AnalysisResponse;
import com.lawfirm.analysis.model.entity.AnalysisRecord;
import com.lawfirm.analysis.model.vo.AnalysisRecordVO;
import com.lawfirm.analysis.service.AnalysisService;
import com.lawfirm.common.core.exception.BusinessException;
import com.lawfirm.common.core.model.page.PageResult;
import lombok.extern.slf4j.Slf4j;
import com.lawfirm.common.util.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 统计分析服务实现类
 */
@Slf4j
@Service
public class AnalysisServiceImpl extends ServiceImpl<AnalysisRecordMapper, AnalysisRecord> implements AnalysisService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnalysisResponse createAnalysis(AnalysisRequest request) {
        log.info("创建分析任务, request: {}", request);
        
        // 创建分析记录
        AnalysisRecord record = new AnalysisRecord();
        record.setAnalysisType(request.getAnalysisType())
                .setStartTime(request.getStartTime())
                .setEndTime(request.getEndTime());

        // TODO: 根据不同的分析类型执行具体的分析逻辑
        String resultJson = "{}"; // 临时占位
        record.setResultJson(resultJson);

        // 保存记录
        save(record);

        return convertToResponse(record);
    }

    @Override
    public AnalysisResponse getAnalysis(Long id) {
        AnalysisRecord record = getById(id);
        if (record == null) {
            throw new BusinessException("分析记录不存在");
        }
        return convertToResponse(record);
    }

    @Override
    public PageResult<AnalysisResponse> pageAnalysis(Integer pageNum, Integer pageSize) {
        // 分页查询
        Page<AnalysisRecord> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<AnalysisRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(AnalysisRecord::getCreateTime);
        
        Page<AnalysisRecord> recordPage = page(page, wrapper);
        
        // 转换结果
        List<AnalysisResponse> records = recordPage.getRecords().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());

        return new PageResult<>(records, recordPage.getTotal());
    }

    /**
     * 将实体转换为响应DTO
     */
    private AnalysisResponse convertToResponse(AnalysisRecord record) {
        AnalysisResponse response = new AnalysisResponse();
        BeanUtils.copyProperties(record, response);
        return response;
    }
} 