package com.lawfirm.staff.client.lawyer.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.lawyer.TimesheetClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.lawyer.timesheet.*;
import com.lawfirm.staff.model.response.lawyer.timesheet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
public class TimesheetClientFallback extends FeignFallbackConfig implements TimesheetClient {

    private static final String SERVICE_NAME = "工时服务";

    @Override
    @SuppressWarnings("unchecked")
    public Result<PageResult<TimesheetResponse>> page(Integer pageNum, Integer pageSize, String keyword) {
        Type type = createParameterizedType(Result.class, createParameterizedType(PageResult.class, TimesheetResponse.class));
        return handleFallback(SERVICE_NAME, "page", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> create(TimesheetCreateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "create", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> update(TimesheetUpdateRequest request) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "update", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<Void> delete(Long id) {
        Type type = createParameterizedType(Result.class, Void.class);
        return handleFallback(SERVICE_NAME, "delete", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<TimesheetResponse> get(Long id) {
        Type type = createParameterizedType(Result.class, TimesheetResponse.class);
        return handleFallback(SERVICE_NAME, "get", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<TimesheetStatisticsResponse> getStatistics() {
        Type type = createParameterizedType(Result.class, TimesheetStatisticsResponse.class);
        return handleFallback(SERVICE_NAME, "getStatistics", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<TimesheetResponse>> getMyTimesheets() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, TimesheetResponse.class));
        return handleFallback(SERVICE_NAME, "getMyTimesheets", type);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Result<List<TimesheetResponse>> getDepartmentTimesheets() {
        Type type = createParameterizedType(Result.class, createParameterizedType(List.class, TimesheetResponse.class));
        return handleFallback(SERVICE_NAME, "getDepartmentTimesheets", type);
    }
} 