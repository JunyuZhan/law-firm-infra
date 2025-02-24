package com.lawfirm.common.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

class TestApplicationTest {

    @Test
    void shouldHaveCorrectAnnotations() {
        // 验证TestApplication类上的注解
        assertTrue(TestApplication.class.isAnnotationPresent(SpringBootApplication.class),
                "TestApplication should have @SpringBootApplication annotation");
    }

    @Test
    void shouldBeAbstractClass() {
        assertTrue(java.lang.reflect.Modifier.isAbstract(TestApplication.class.getModifiers()),
                "TestApplication should be abstract");
    }

    @Test
    void shouldHaveAbstractMethods() {
        assertTrue(hasAbstractMethod(TestApplication.class, "configureTestApplication"),
                "TestApplication should have abstract method 'configureTestApplication'");
        assertTrue(hasAbstractMethod(TestApplication.class, "initializeTestEnvironment"),
                "TestApplication should have abstract method 'initializeTestEnvironment'");
    }

    private boolean hasAbstractMethod(Class<?> clazz, String methodName) {
        return java.util.Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method -> method.getName().equals(methodName) &&
                        java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
    }
} 