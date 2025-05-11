package com.lawfirm.common.data.config;

import java.io.IOException;

import org.apache.ibatis.type.TypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import org.apache.ibatis.type.EnumTypeHandler;

/**
 * MyBatis-Plus配置类
 * <p>
 * 配置MyBatis-Plus的各种功能和行为
 * </p>
 */
@Configuration
public class MybatisPlusConfig {

    private static final Logger log = LoggerFactory.getLogger(MybatisPlusConfig.class);
    
    /**
     * 创建MyBatis-Plus拦截器
     * 配置分页、乐观锁、防止全表更新与删除等功能
     */
    @Bean(name = "commonMybatisPlusInterceptor")
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        
        log.info("MyBatis-Plus拦截器配置完成，已启用乐观锁");
        return interceptor;
    }
    
    /**
     * 初始化MyBatis-Plus相关配置
     * 注意：
     * 1. 避免Set<Class<?>>类型的使用，解决FactoryBean类型推断问题
     * 2. 返回一个对象，符合Spring的@Bean方法要求
     */
    @Bean(name = "mybatisPlusConfigInit")
    public Object initMybatisPlusConfig(@Value("${mybatis-plus.typeEnumsPackage:}") String typeEnumsPackage) {
        if (!StringUtils.hasText(typeEnumsPackage)) {
            log.info("未配置枚举类包路径，跳过枚举类扫描");
            return new Object(); // 返回一个简单对象
        }
        
        try {
            // 处理多个包路径，以逗号分隔
            int totalCount = 0;
            for (String packageName : typeEnumsPackage.split(",")) {
                if (StringUtils.hasText(packageName)) {
                    int count = scanEnumClasses(packageName.trim());
                    totalCount += count;
                    if (count > 0) {
                        log.info("在{}包中发现{}个枚举类", packageName.trim(), count);
                    }
                }
            }
            log.info("共扫描到{}个枚举类", totalCount);
        } catch (Exception e) {
            log.warn("枚举类型扫描失败", e);
        }
        
        return new Object(); // 返回一个简单对象
    }
    
    /**
     * 扫描并加载所有枚举类
     * 返回整数而不是Set<Class<?>>类型，避免类型推断问题
     */
    private int scanEnumClasses(String basePackage) throws IOException {
        int count = 0;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + 
                                 ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/*.class";
        
        Resource[] resources = resolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                try {
                    MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                    String className = metadataReader.getClassMetadata().getClassName();
                    
                    // 检查是否为枚举类，但不保存Class对象的引用
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isEnum()) {
                        count++;
                        log.debug("检测到枚举类: {}", className);
                    }
                } catch (ClassNotFoundException e) {
                    log.warn("加载类失败: {}", e.getMessage());
                }
            }
        }
        
        return count;
    }
} 