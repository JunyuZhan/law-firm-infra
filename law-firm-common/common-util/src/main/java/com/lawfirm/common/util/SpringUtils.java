package com.lawfirm.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component("commonSpringUtils")
public class SpringUtils implements ApplicationContextAware {
    
    private static ApplicationContext applicationContext;
    
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringUtils.applicationContext = context;
    }
    
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }
    
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
    
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
    
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
} 