package com.lawfirm.common.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BaseTestTest {

    @Test
    void shouldHaveCorrectAnnotations() {
        // 验证BaseTest类上的注解
        assertTrue(BaseTest.class.isAnnotationPresent(SpringBootTest.class),
                "BaseTest should have @SpringBootTest annotation");
        assertTrue(BaseTest.class.isAnnotationPresent(ActiveProfiles.class),
                "BaseTest should have @ActiveProfiles annotation");
        assertTrue(BaseTest.class.isAnnotationPresent(Transactional.class),
                "BaseTest should have @Transactional annotation");
        
        // 验证@ActiveProfiles的value是"test"
        ActiveProfiles activeProfiles = BaseTest.class.getAnnotation(ActiveProfiles.class);
        assertTrue(activeProfiles.value()[0].equals("test"),
                "ActiveProfiles should have value 'test'");
    }

    @Test
    void shouldBeAbstractClass() {
        assertTrue(java.lang.reflect.Modifier.isAbstract(BaseTest.class.getModifiers()),
                "BaseTest should be abstract");
    }

    @Test
    void shouldHaveAbstractMethods() {
        assertTrue(hasAbstractMethod(BaseTest.class, "beforeTest"),
                "BaseTest should have abstract method 'beforeTest'");
        assertTrue(hasAbstractMethod(BaseTest.class, "afterTest"),
                "BaseTest should have abstract method 'afterTest'");
        assertTrue(hasAbstractMethod(BaseTest.class, "clearTestData"),
                "BaseTest should have abstract method 'clearTestData'");
    }

    private boolean hasAbstractMethod(Class<?> clazz, String methodName) {
        return java.util.Arrays.stream(clazz.getDeclaredMethods())
                .anyMatch(method -> method.getName().equals(methodName) &&
                        java.lang.reflect.Modifier.isAbstract(method.getModifiers()));
    }
} 