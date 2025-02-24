package com.lawfirm.core.message.config;

import com.lawfirm.core.message.constant.MessageConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消息队列配置
 */
@Configuration
public class MessageQueueConfig {

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory messageListenerContainerFactory(
            ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        factory.setConcurrentConsumers(3);
        factory.setMaxConcurrentConsumers(10);
        return factory;
    }

    @Bean
    public DirectExchange messageExchange() {
        return new DirectExchange(MessageConstants.EXCHANGE_MESSAGE);
    }

    @Bean
    public Queue messageQueue() {
        return QueueBuilder.durable(MessageConstants.QUEUE_MESSAGE)
                .withArgument("x-dead-letter-exchange", MessageConstants.EXCHANGE_MESSAGE + ".dlx")
                .withArgument("x-dead-letter-routing-key", MessageConstants.ROUTING_KEY_MESSAGE + ".dlq")
                .build();
    }

    @Bean
    public Binding messageBinding() {
        return BindingBuilder.bind(messageQueue())
                .to(messageExchange())
                .with(MessageConstants.ROUTING_KEY_MESSAGE);
    }

    @Bean
    public DirectExchange delayedMessageExchange() {
        return ExchangeBuilder.directExchange(MessageConstants.EXCHANGE_MESSAGE + ".delayed")
                .delayed()
                .build();
    }

    @Bean
    public Queue delayedMessageQueue() {
        return QueueBuilder.durable(MessageConstants.QUEUE_DELAYED_MESSAGE)
                .withArgument("x-dead-letter-exchange", MessageConstants.EXCHANGE_MESSAGE)
                .withArgument("x-dead-letter-routing-key", MessageConstants.ROUTING_KEY_MESSAGE)
                .build();
    }

    @Bean
    public Binding delayedMessageBinding() {
        return BindingBuilder.bind(delayedMessageQueue())
                .to(delayedMessageExchange())
                .with(MessageConstants.ROUTING_KEY_DELAYED_MESSAGE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(MessageConstants.EXCHANGE_MESSAGE + ".dlx");
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(MessageConstants.QUEUE_MESSAGE + ".dlq");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(MessageConstants.ROUTING_KEY_MESSAGE + ".dlq");
    }
} 