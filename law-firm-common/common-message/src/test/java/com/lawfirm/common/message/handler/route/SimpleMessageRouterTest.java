package com.lawfirm.common.message.handler.route;

import com.lawfirm.common.message.support.channel.MessageChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleMessageRouterTest {

    private static class SimpleMessageRouter implements MessageRouter {
        private final Map<String, MessageChannel> channels = new HashMap<>();

        @Override
        public MessageChannel route(String messageType) {
            // 简单的路由策略：直接使用messageType作为channel类型
            return channels.get(messageType);
        }

        @Override
        public void registerChannel(MessageChannel channel) {
            channels.put(channel.getType(), channel);
        }

        @Override
        public void removeChannel(String channelType) {
            channels.remove(channelType);
        }

        @Override
        public MessageChannel[] getAvailableChannels() {
            return channels.values().stream()
                .filter(MessageChannel::isAvailable)
                .toArray(MessageChannel[]::new);
        }
    }

    private SimpleMessageRouter router;
    private MessageChannel emailChannel;
    private MessageChannel smsChannel;

    @BeforeEach
    void setUp() {
        router = new SimpleMessageRouter();
        
        // 创建模拟的消息通道
        emailChannel = mock(MessageChannel.class);
        when(emailChannel.getType()).thenReturn("email");
        when(emailChannel.isAvailable()).thenReturn(true);
        
        smsChannel = mock(MessageChannel.class);
        when(smsChannel.getType()).thenReturn("sms");
        when(smsChannel.isAvailable()).thenReturn(true);
        
        // 注册通道
        router.registerChannel(emailChannel);
        router.registerChannel(smsChannel);
    }

    @Test
    void should_route_to_correct_channel() {
        // when & then
        assertThat(router.route("email")).isSameAs(emailChannel);
        assertThat(router.route("sms")).isSameAs(smsChannel);
        assertThat(router.route("unknown")).isNull();
    }

    @Test
    void should_register_channel() {
        // given
        MessageChannel wechatChannel = mock(MessageChannel.class);
        when(wechatChannel.getType()).thenReturn("wechat");
        
        // when
        router.registerChannel(wechatChannel);
        
        // then
        assertThat(router.route("wechat")).isSameAs(wechatChannel);
    }

    @Test
    void should_remove_channel() {
        // when
        router.removeChannel("email");
        
        // then
        assertThat(router.route("email")).isNull();
    }

    @Test
    void should_get_available_channels() {
        // given
        when(smsChannel.isAvailable()).thenReturn(false);
        
        // when
        MessageChannel[] availableChannels = router.getAvailableChannels();
        
        // then
        assertThat(availableChannels).hasSize(1);
        assertThat(availableChannels[0]).isSameAs(emailChannel);
    }

    @Test
    void should_handle_duplicate_registration() {
        // given
        MessageChannel newEmailChannel = mock(MessageChannel.class);
        when(newEmailChannel.getType()).thenReturn("email");
        
        // when
        router.registerChannel(newEmailChannel);
        
        // then
        assertThat(router.route("email")).isSameAs(newEmailChannel);
    }
} 