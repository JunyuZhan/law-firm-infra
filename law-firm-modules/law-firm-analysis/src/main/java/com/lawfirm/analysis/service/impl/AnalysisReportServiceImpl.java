package com.lawfirm.analysis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lawfirm.analysis.constant.AnalysisTypeEnum;
import com.lawfirm.analysis.mapper.AnalysisReportMapper;
import com.lawfirm.analysis.model.dto.AnalysisReportRequest;
import com.lawfirm.analysis.model.dto.AnalysisReportResponse;
import com.lawfirm.analysis.model.entity.AnalysisReport;
import com.lawfirm.analysis.model.vo.AnalysisReportVO;
import com.lawfirm.analysis.service.AnalysisReportService;
import com.lawfirm.common.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 统计分析报告服务实现类
 */
@Slf4j
@Service
public class AnalysisReportServiceImpl extends ServiceImpl<AnalysisReportMapper, AnalysisReport> implements AnalysisReportService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AnalysisReportResponse generateReport(AnalysisReportRequest request) {
        log.info("生成分析报告, request: {}", request);

        // 创建报告记录
        AnalysisReport report = new AnalysisReport();
        report.setAnalysisType(request.getAnalysisType())
                .setTitle(request.getTitle())
                .setDescription(request.getDescription())
                .setStartTime(request.getStartTime())
                .setEndTime(request.getEndTime());

        // TODO: 根据不同的分析类型生成报告内容
        String contentJson = "{}"; // 临时占位
        report.setContentJson(contentJson);

        // 保存报告
        save(report);

        return convertToResponse(report);
    }

    @Override
    public AnalysisReportResponse getLatestReport(AnalysisTypeEnum type) {
        // 查询最新报告
        LambdaQueryWrapper<AnalysisReport> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(AnalysisReport::getAnalysisType, type)
                .orderByDesc(AnalysisReport::getCreateTime)
                .last("LIMIT 1");

        AnalysisReport report = getOne(wrapper);
        if (report == null) {
            throw new BusinessException("未找到相关报告");
        }

        return convertToResponse(report);
    }

    /**
     * 将实体转换为响应DTO
     */
    private AnalysisReportResponse convertToResponse(AnalysisReport report) {
        AnalysisReportResponse response = new AnalysisReportResponse();
        BeanUtils.copyProperties(report, response);
        return response;
    }
} 
