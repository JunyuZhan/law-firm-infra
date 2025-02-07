package com.lawfirm.staff.client.clerk.fallback;

import com.lawfirm.common.core.model.Result;
import com.lawfirm.common.core.model.page.PageResult;
import com.lawfirm.staff.client.clerk.TaskClient;
import com.lawfirm.staff.config.FeignFallbackConfig;
import com.lawfirm.staff.model.request.clerk.task.TaskCreateRequest;
import com.lawfirm.staff.model.request.clerk.task.TaskPageRequest;
import com.lawfirm.staff.model.request.clerk.task.TaskUpdateRequest;
import com.lawfirm.staff.model.response.clerk.task.TaskResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskClientFallback extends FeignFallbackConfig implements TaskClient {

    private static final String SERVICE_NAME = "任务服务";

    @Override
    public Result<PageResult<TaskResponse>> page(TaskPageRequest request) {
        return handlePageFallback(SERVICE_NAME, "page", TaskResponse.class);
    }

    @Override
    public Result<Void> create(TaskCreateRequest request) {
        return handleFallback(SERVICE_NAME, "create", Void.class);
    }

    @Override
    public Result<Void> update(Long id, TaskUpdateRequest request) {
        return handleFallback(SERVICE_NAME, "update", Void.class);
    }

    @Override
    public Result<Void> delete(Long id) {
        return handleFallback(SERVICE_NAME, "delete", Void.class);
    }

    @Override
    public Result<TaskResponse> get(Long id) {
        return handleFallback(SERVICE_NAME, "get", TaskResponse.class);
    }

    @Override
    public Result<PageResult<TaskResponse>> getMyTasks(TaskPageRequest request) {
        return handlePageFallback(SERVICE_NAME, "getMyTasks", TaskResponse.class);
    }

    @Override
    public Result<PageResult<TaskResponse>> getDepartmentTasks(TaskPageRequest request) {
        return handlePageFallback(SERVICE_NAME, "getDepartmentTasks", TaskResponse.class);
    }

    @Override
    public Result<Void> updateStatus(Long id, Integer status) {
        return handleFallback(SERVICE_NAME, "updateStatus", Void.class);
    }
} 