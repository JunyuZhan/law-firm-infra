package com.lawfirm.core.message.config;

import com.lawfirm.core.message.facade.MessageFacade;
import com.lawfirm.core.message.service.MessageSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import lombok.extern.slf4j.Slf4j;

/**
 * 消息门面配置类
 * 确保即使在消息服务被禁用的情况下，也能创建MessageFacade
 */
@Slf4j
@Configuration
public class MessageFacadeConfig {

    /**
     * 创建MessageFacade
     * 依赖于MessageSender接口，而不是具体实现
     * NoopMessageSender和MessageSenderImpl都实现了该接口
     */
    @Bean("messageFacade")
    @ConditionalOnMissingBean(MessageFacade.class)
    public MessageFacade messageFacade(MessageSender messageSender) {
        log.info("创建消息门面服务 MessageFacade");
        return new MessageFacade(messageSender);
    }
} 