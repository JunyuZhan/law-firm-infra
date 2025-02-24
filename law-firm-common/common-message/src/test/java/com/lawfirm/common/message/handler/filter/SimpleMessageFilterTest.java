package com.lawfirm.common.message.handler.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleMessageFilterTest {

    private static class SimpleMessageFilter implements MessageFilter {
        private final String type;
        private final int order;
        private boolean enabled = true;

        public SimpleMessageFilter(String type, int order) {
            this.type = type;
            this.order = order;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        @Override
        public String filter(String content) {
            if (content == null) {
                return null;
            }
            // 简单的过滤实现：替换敏感词
            return content.replaceAll("password|密码", "***");
        }

        @Override
        public int getOrder() {
            return order;
        }

        @Override
        public boolean isEnabled() {
            return enabled;
        }

        @Override
        public String getType() {
            return type;
        }
    }

    private SimpleMessageFilter filter;

    @BeforeEach
    void setUp() {
        filter = new SimpleMessageFilter("sensitive-word", 100);
    }

    @Test
    void should_filter_sensitive_words() {
        // given
        String content = "Your password is 123456, 密码是123456";

        // when
        String filtered = filter.filter(content);

        // then
        assertThat(filtered).isEqualTo("Your *** is 123456, ***是123456");
    }

    @Test
    void should_handle_null_content() {
        // when
        String filtered = filter.filter(null);

        // then
        assertThat(filtered).isNull();
    }

    @Test
    void should_handle_empty_content() {
        // when
        String filtered = filter.filter("");

        // then
        assertThat(filtered).isEmpty();
    }

    @Test
    void should_get_order() {
        assertThat(filter.getOrder()).isEqualTo(100);
    }

    @Test
    void should_check_enabled_status() {
        // given
        filter.setEnabled(true);

        // when & then
        assertThat(filter.isEnabled()).isTrue();

        // when
        filter.setEnabled(false);

        // then
        assertThat(filter.isEnabled()).isFalse();
    }

    @Test
    void should_get_type() {
        assertThat(filter.getType()).isEqualTo("sensitive-word");
    }

    @Test
    void should_not_modify_content_without_sensitive_words() {
        // given
        String content = "Hello, world!";

        // when
        String filtered = filter.filter(content);

        // then
        assertThat(filtered).isEqualTo(content);
    }
} 