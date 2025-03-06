package com.lawfirm.common.message.handler.retry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

class SimpleRetryPolicyTest {

    private static class SimpleRetryPolicy implements RetryPolicy {
        private final int maxRetries;
        private final Duration baseDelay;
        private final List<Class<? extends Throwable>> retryableExceptions;

        /**
         * 使用List构造重试策略
         */
        public SimpleRetryPolicy(int maxRetries, Duration baseDelay, List<Class<? extends Throwable>> retryableExceptions) {
            this.maxRetries = maxRetries;
            this.baseDelay = baseDelay;
            this.retryableExceptions = retryableExceptions;
        }

        /**
         * 使用可变参数构造重试策略
         */
        @SafeVarargs
        @SuppressWarnings({"unchecked", "varargs"})
        public static SimpleRetryPolicy of(int maxRetries, Duration baseDelay, Class<? extends Throwable>... retryableExceptions) {
            return new SimpleRetryPolicy(maxRetries, baseDelay, List.of(retryableExceptions));
        }

        @Override
        public boolean shouldRetry(int retryCount, Throwable exception) {
            if (retryCount >= maxRetries) {
                return false;
            }
            
            for (Class<? extends Throwable> retryable : retryableExceptions) {
                if (retryable.isInstance(exception)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Duration getNextRetryDelay(int retryCount) {
            // 使用指数退避策略
            return baseDelay.multipliedBy((long) Math.pow(2, retryCount));
        }

        @Override
        public int getMaxRetryCount() {
            return maxRetries;
        }

        @Override
        public boolean shouldAbort(Throwable exception) {
            // 如果是Error或RuntimeException，则立即中止
            return exception instanceof Error || 
                   (exception instanceof RuntimeException && 
                    !shouldRetry(0, exception));
        }
    }

    private SimpleRetryPolicy retryPolicy;

    @BeforeEach
    void setUp() {
        retryPolicy = SimpleRetryPolicy.of(
            3,  // 最大重试次数
            Duration.ofSeconds(1),  // 基础延迟时间
            IllegalStateException.class,  // 可重试的异常
            IllegalArgumentException.class
        );
    }

    @Test
    void should_retry_when_exception_is_retryable_and_count_not_exceeded() {
        // given
        IllegalStateException exception = new IllegalStateException("Test error");

        // when & then
        assertThat(retryPolicy.shouldRetry(0, exception)).isTrue();
        assertThat(retryPolicy.shouldRetry(1, exception)).isTrue();
        assertThat(retryPolicy.shouldRetry(2, exception)).isTrue();
        assertThat(retryPolicy.shouldRetry(3, exception)).isFalse();
    }

    @Test
    void should_not_retry_when_exception_is_not_retryable() {
        // given
        NullPointerException exception = new NullPointerException("Test error");

        // when & then
        assertThat(retryPolicy.shouldRetry(0, exception)).isFalse();
    }

    @Test
    void should_calculate_exponential_backoff_delay() {
        // when & then
        assertThat(retryPolicy.getNextRetryDelay(0)).isEqualTo(Duration.ofSeconds(1));
        assertThat(retryPolicy.getNextRetryDelay(1)).isEqualTo(Duration.ofSeconds(2));
        assertThat(retryPolicy.getNextRetryDelay(2)).isEqualTo(Duration.ofSeconds(4));
    }

    @Test
    void should_get_max_retry_count() {
        assertThat(retryPolicy.getMaxRetryCount()).isEqualTo(3);
    }

    @Test
    void should_abort_on_error() {
        // given
        Error error = new Error("Fatal error");

        // when & then
        assertThat(retryPolicy.shouldAbort(error)).isTrue();
    }

    @Test
    void should_abort_on_non_retryable_runtime_exception() {
        // given
        RuntimeException exception = new NullPointerException("NPE");

        // when & then
        assertThat(retryPolicy.shouldAbort(exception)).isTrue();
    }

    @Test
    void should_not_abort_on_retryable_exception() {
        // given
        RuntimeException exception = new IllegalStateException("Retryable");

        // when & then
        assertThat(retryPolicy.shouldAbort(exception)).isFalse();
    }
} 