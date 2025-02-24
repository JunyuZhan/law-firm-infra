package com.lawfirm.common.core.context;

import com.lawfirm.common.core.exception.FrameworkException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class BaseContextHandlerTest {

    @AfterEach
    void tearDown() {
        BaseContextHandler.clear();
    }

    @Test
    void shouldStoreAndRetrieveValue() {
        // given
        String key = "testKey";
        String value = "testValue";

        // when
        BaseContextHandler.set(key, value);
        String retrieved = BaseContextHandler.get(key);

        // then
        assertEquals(value, retrieved);
    }

    @Test
    void shouldReturnDefaultValueWhenKeyNotExists() {
        // given
        String key = "nonExistentKey";
        String defaultValue = "default";

        // when
        String value = BaseContextHandler.get(key, defaultValue);

        // then
        assertEquals(defaultValue, value);
    }

    @Test
    void shouldThrowExceptionWhenKeyIsNull() {
        assertThrows(FrameworkException.class, () -> BaseContextHandler.set(null, "value"));
        assertThrows(FrameworkException.class, () -> BaseContextHandler.get(null));
    }

    @Test
    void shouldRemoveValue() {
        // given
        String key = "testKey";
        String value = "testValue";
        BaseContextHandler.set(key, value);

        // when
        BaseContextHandler.remove(key);

        // then
        assertNull(BaseContextHandler.get(key));
    }

    @Test
    void shouldClearAllValues() {
        // given
        BaseContextHandler.set("key1", "value1");
        BaseContextHandler.set("key2", "value2");

        // when
        BaseContextHandler.clear();

        // then
        assertNull(BaseContextHandler.get("key1"));
        assertNull(BaseContextHandler.get("key2"));
    }

    @Test
    void shouldBeThreadSafe() throws InterruptedException {
        // given
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        String key = "threadKey";

        // when
        for (int i = 0; i < threadCount; i++) {
            final String value = "thread" + i;
            executorService.execute(() -> {
                try {
                    BaseContextHandler.set(key, value);
                    assertEquals(value, BaseContextHandler.get(key));
                } finally {
                    latch.countDown();
                }
            });
        }

        // then
        assertTrue(latch.await(5, TimeUnit.SECONDS));
        executorService.shutdown();
    }
} 