package com.lawfirm.api.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

import java.beans.Introspector;

/**
 * 自定义Bean名称生成器
 * 根据包路径为Bean添加模块前缀，避免Bean名称冲突
 */
public class CustomBeanNameGenerator extends AnnotationBeanNameGenerator {
    
    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String beanClassName = definition.getBeanClassName();
        if (beanClassName == null) {
            return null;
        }
        
        String shortClassName = beanClassName.substring(beanClassName.lastIndexOf('.') + 1);
        
        // 添加模块前缀
        if (beanClassName.contains(".core.")) {
            return "core" + shortClassName;
        } else if (beanClassName.contains(".common.")) {
            return "common" + shortClassName;
        } else if (beanClassName.contains(".client.")) {
            return "client" + shortClassName;
        } else if (beanClassName.contains(".auth.")) {
            return "auth" + shortClassName;
        } else if (beanClassName.contains(".system.")) {
            return "system" + shortClassName;
        } else if (beanClassName.contains(".cases.")) {
            return "case" + shortClassName;
        } else if (beanClassName.contains(".contract.")) {
            return "contract" + shortClassName;
        } else if (beanClassName.contains(".document.")) {
            return "doc" + shortClassName;
        } else if (beanClassName.contains(".knowledge.")) {
            return "knowledge" + shortClassName;
        } else if (beanClassName.contains(".api.")) {
            return "api" + shortClassName;
        }
        
        // 默认情况下使用类名首字母小写作为Bean名称
        return Introspector.decapitalize(shortClassName);
    }
} 