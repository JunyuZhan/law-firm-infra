package com.lawfirm.finance.converter;

import com.lawfirm.finance.dto.request.FeeRecordAddRequest;
import com.lawfirm.finance.dto.request.FeeRecordUpdateRequest;
import com.lawfirm.finance.dto.response.FeeRecordResponse;
import com.lawfirm.finance.entity.FeeRecord;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeeRecordConverter {
    
    public FeeRecord toEntity(FeeRecordAddRequest request) {
        if (request == null) {
            return null;
        }
        FeeRecord entity = new FeeRecord();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
    
    public FeeRecord toEntity(FeeRecordUpdateRequest request) {
        if (request == null) {
            return null;
        }
        FeeRecord entity = new FeeRecord();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }
    
    public FeeRecordResponse toResponse(FeeRecord entity) {
        if (entity == null) {
            return null;
        }
        FeeRecordResponse response = new FeeRecordResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }
    
    public List<FeeRecordResponse> toResponseList(List<FeeRecord> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
} 