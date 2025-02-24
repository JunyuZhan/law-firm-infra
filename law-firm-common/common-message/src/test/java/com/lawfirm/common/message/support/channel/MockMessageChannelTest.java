package com.lawfirm.common.message.support.channel;

import com.lawfirm.common.message.support.sender.MessageSendResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MockMessageChannelTest {

    private static class MockMessageChannel implements MessageChannel {
        private boolean available = true;
        private final String type;

        public MockMessageChannel(String type) {
            this.type = type;
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public boolean isAvailable() {
            return available;
        }

        public void setAvailable(boolean available) {
            this.available = available;
        }

        @Override
        public MessageSendResult send(String content, String target) {
            if (!isAvailable()) {
                return MessageSendResult.failure(type, "CHANNEL_001", "Channel not available");
            }
            return MessageSendResult.success("MSG-" + System.currentTimeMillis(), type);
        }

        @Override
        public MessageSendResult batchSend(String content, String... targets) {
            if (!isAvailable()) {
                return MessageSendResult.failure(type, "CHANNEL_001", "Channel not available");
            }
            return MessageSendResult.success("BATCH-" + System.currentTimeMillis(), type);
        }
    }

    private MockMessageChannel channel;

    @BeforeEach
    void setUp() {
        channel = new MockMessageChannel("mock");
    }

    @Test
    void should_get_channel_type() {
        assertThat(channel.getType()).isEqualTo("mock");
    }

    @Test
    void should_check_availability() {
        // given
        channel.setAvailable(true);

        // when & then
        assertThat(channel.isAvailable()).isTrue();

        // when
        channel.setAvailable(false);

        // then
        assertThat(channel.isAvailable()).isFalse();
    }

    @Test
    void should_send_message_when_available() {
        // given
        channel.setAvailable(true);

        // when
        MessageSendResult result = channel.send("test content", "test-target");

        // then
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getChannel()).isEqualTo("mock");
        assertThat(result.getMessageId()).startsWith("MSG-");
    }

    @Test
    void should_fail_to_send_when_not_available() {
        // given
        channel.setAvailable(false);

        // when
        MessageSendResult result = channel.send("test content", "test-target");

        // then
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getChannel()).isEqualTo("mock");
        assertThat(result.getErrorCode()).isEqualTo("CHANNEL_001");
        assertThat(result.getErrorMessage()).isEqualTo("Channel not available");
    }

    @Test
    void should_batch_send_messages_when_available() {
        // given
        channel.setAvailable(true);

        // when
        MessageSendResult result = channel.batchSend(
            "test content", 
            "target1", "target2"
        );

        // then
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getChannel()).isEqualTo("mock");
        assertThat(result.getMessageId()).startsWith("BATCH-");
    }
} 