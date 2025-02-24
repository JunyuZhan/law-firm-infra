package com.lawfirm.common.message.support.sender;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MessageSendResultTest {

    @Test
    void should_create_success_result() {
        // given
        String messageId = "test-message-id";
        String channel = "email";

        // when
        MessageSendResult result = MessageSendResult.success(messageId, channel);

        // then
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getMessageId()).isEqualTo(messageId);
        assertThat(result.getChannel()).isEqualTo(channel);
        assertThat(result.getErrorCode()).isNull();
        assertThat(result.getErrorMessage()).isNull();
    }

    @Test
    void should_create_failure_result() {
        // given
        String channel = "sms";
        String errorCode = "SMS_001";
        String errorMessage = "Invalid phone number";

        // when
        MessageSendResult result = MessageSendResult.failure(channel, errorCode, errorMessage);

        // then
        assertThat(result.isSuccess()).isFalse();
        assertThat(result.getChannel()).isEqualTo(channel);
        assertThat(result.getErrorCode()).isEqualTo(errorCode);
        assertThat(result.getErrorMessage()).isEqualTo(errorMessage);
        assertThat(result.getMessageId()).isNull();
    }

    @Test
    void should_set_raw_response() {
        // given
        MessageSendResult result = new MessageSendResult();
        Object rawResponse = new Object();

        // when
        result.setRawResponse(rawResponse);

        // then
        assertThat(result.getRawResponse()).isSameAs(rawResponse);
    }
} 