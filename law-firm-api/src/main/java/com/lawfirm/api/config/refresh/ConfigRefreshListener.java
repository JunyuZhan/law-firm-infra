package com.lawfirm.api.config.refresh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfigRefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private final ConfigRefreshProperties properties;

    public ConfigRefreshListener(ConfigRefreshProperties properties) {
        this.properties = properties;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (properties.isRefreshOnStartup()) {
            refreshConfig();
        }
    }

    @Scheduled(fixedDelayString = "${config.refresh.refresh-interval:30000}")
    public void scheduledRefresh() {
        if (properties.isEnabled()) {
            refreshConfig();
        }
    }

    private void refreshConfig() {
        log.info("开始刷新配置...");
        // TODO: 实现配置刷新逻辑
        log.info("配置刷新完成");
    }
} 