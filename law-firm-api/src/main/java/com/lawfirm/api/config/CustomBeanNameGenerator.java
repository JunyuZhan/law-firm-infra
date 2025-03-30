package com.lawfirm.api.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * 自定义Bean名称生成器，用于解决多模块扫描时的Bean名称冲突
 * 为Mapper接口Bean名称添加模块前缀，确保唯一性
 */
public class CustomBeanNameGenerator implements BeanNameGenerator {

    private final String modulePrefix;
    
    /**
     * 构造方法
     * @param modulePrefix 模块前缀，例如"auth"、"system"等
     */
    public CustomBeanNameGenerator(String modulePrefix) {
        this.modulePrefix = modulePrefix;
    }
    
    /**
     * 默认构造方法，不添加前缀
     */
    public CustomBeanNameGenerator() {
        this.modulePrefix = "";
    }

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanClassName = definition.getBeanClassName();
        if (beanClassName != null && beanClassName.contains("mapper")) {
            if (modulePrefix != null && !modulePrefix.isEmpty()) {
                // 为Mapper接口Bean名称添加模块前缀
                return modulePrefix + beanClassName.substring(beanClassName.lastIndexOf('.') + 1);
            } else {
                // 使用完整的类名作为Bean名称
                return beanClassName;
            }
        }
        return beanClassName;
    }
} 