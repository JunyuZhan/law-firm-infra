package com.lawfirm.staff.client.clerk.fallback;

import com.lawfirm.staff.client.clerk.TaskClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TaskClientFallbackFactory implements FallbackFactory<TaskClient> {

    private final TaskClientFallback fallback;

    public TaskClientFallbackFactory(TaskClientFallback fallback) {
        this.fallback = fallback;
    }

    @Override
    public TaskClient create(Throwable cause) {
        log.error("任务服务调用失败", cause);
        return fallback;
    }
} 