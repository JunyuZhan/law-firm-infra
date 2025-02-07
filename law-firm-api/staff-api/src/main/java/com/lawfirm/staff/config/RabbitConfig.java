package com.lawfirm.staff.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ配置
 */
@EnableRabbit
@Configuration
public class RabbitConfig {

    public static final String EXCHANGE_TOPIC = "law.firm.topic";
    public static final String QUEUE_NOTIFICATION = "law.firm.notification";
    public static final String QUEUE_EMAIL = "law.firm.email";
    public static final String QUEUE_SMS = "law.firm.sms";
    public static final String ROUTING_KEY_NOTIFICATION = "notification.#";
    public static final String ROUTING_KEY_EMAIL = "email.#";
    public static final String ROUTING_KEY_SMS = "sms.#";

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(EXCHANGE_TOPIC);
    }

    @Bean
    public Queue notificationQueue() {
        return new Queue(QUEUE_NOTIFICATION);
    }

    @Bean
    public Queue emailQueue() {
        return new Queue(QUEUE_EMAIL);
    }

    @Bean
    public Queue smsQueue() {
        return new Queue(QUEUE_SMS);
    }

    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(notificationQueue())
                .to(topicExchange())
                .with(ROUTING_KEY_NOTIFICATION);
    }

    @Bean
    public Binding emailBinding() {
        return BindingBuilder.bind(emailQueue())
                .to(topicExchange())
                .with(ROUTING_KEY_EMAIL);
    }

    @Bean
    public Binding smsBinding() {
        return BindingBuilder.bind(smsQueue())
                .to(topicExchange())
                .with(ROUTING_KEY_SMS);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
} 