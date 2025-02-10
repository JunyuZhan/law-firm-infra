package com.lawfirm.common.message.handler.impl;

import com.lawfirm.common.message.handler.MessageRateLimiter;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

/**
 * Bucket4j实现的消息限流器
 */
@RequiredArgsConstructor
public class Bucket4jMessageRateLimiter implements MessageRateLimiter {
    private final Bucket bucket;

    public Bucket4jMessageRateLimiter(long capacity, long rate) {
        Bandwidth limit = Bandwidth.classic(capacity, Refill.greedy(rate, Duration.ofSeconds(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @Override
    public boolean tryConsume() {
        return bucket.tryConsume(1);
    }

    @Override
    public boolean tryConsume(int tokens) {
        return bucket.tryConsume(tokens);
    }

    @Override
    public void consume() {
        if (!bucket.tryConsume(1)) {
            throw new RuntimeException("No tokens available");
        }
    }

    @Override
    public void consume(int tokens) {
        if (!bucket.tryConsume(tokens)) {
            throw new RuntimeException("No tokens available");
        }
    }

    @Override
    public long getAvailableTokens() {
        return bucket.getAvailableTokens();
    }
} 