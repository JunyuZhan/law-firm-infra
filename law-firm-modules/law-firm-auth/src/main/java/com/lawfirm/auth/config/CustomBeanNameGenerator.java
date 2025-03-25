package com.lawfirm.auth.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * 自定义Bean名称生成器，用于解决多模块扫描时的Bean名称冲突
 * 为Mapper接口Bean名称添加前缀，确保唯一性
 */
public class CustomBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanClassName = definition.getBeanClassName();
        if (beanClassName != null && beanClassName.contains("mapper")) {
            // 为Mapper接口Bean名称添加auth前缀
            return "auth" + beanClassName.substring(beanClassName.lastIndexOf('.') + 1);
        }
        return beanClassName;
    }
} 