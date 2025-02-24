package com.lawfirm.common.test.config;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestConfigTest {

    @Test
    void shouldHaveCorrectAnnotations() {
        // 验证TestConfig接口上的注解
        assertTrue(TestConfig.class.isAnnotationPresent(Configuration.class),
                "TestConfig should have @Configuration annotation");
    }

    @Test
    void shouldBeInterface() {
        assertTrue(TestConfig.class.isInterface(),
                "TestConfig should be an interface");
    }

    @Test
    void shouldHaveRequiredMethods() {
        assertTrue(hasMethod(TestConfig.class, "configureTestDataSource"),
                "TestConfig should have method 'configureTestDataSource'");
        assertTrue(hasMethod(TestConfig.class, "configureTestTransactionManager"),
                "TestConfig should have method 'configureTestTransactionManager'");
        assertTrue(hasMethod(TestConfig.class, "configureTestEnvironment"),
                "TestConfig should have method 'configureTestEnvironment'");
    }

    private boolean hasMethod(Class<?> clazz, String methodName) {
        return java.util.Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method -> method.getName().equals(methodName));
    }
} 