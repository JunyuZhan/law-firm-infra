package com.lawfirm.common.data.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
     * 配置枚举类型处理器
     * 在MyBatis-Plus 3.5.3.1+版本中，全局配置方式已变更
     * 现在应该在application.yml中通过mybatis-plus.configuration.default-enum-type-handler进行配置
     *
     * @param typeEnumsPackage 枚举类所在的包路径
     * @return 返回枚举类信息配置对象
     */
    @Bean
    public EnumTypeConfig configureEnumTypeHandlers(@Value("${mybatis-plus.typeEnumsPackage:com.lawfirm.model}") String typeEnumsPackage) {
        EnumTypeConfig config = new EnumTypeConfig(typeEnumsPackage);
        
        try {
            Set<Class<?>> enumClasses = scanEnumClasses(typeEnumsPackage);
            if (!enumClasses.isEmpty()) {
                log.info("发现{}个枚举类，请确保在application.yml中配置了'mybatis-plus.configuration.default-enum-type-handler'", enumClasses.size());
                for (Class<?> enumClass : enumClasses) {
                    if (enumClass.isEnum()) {
                        log.debug("检测到枚举类: {}", enumClass.getName());
                        config.addEnumClass(enumClass);
                    }
                }
            }
        } catch (Exception e) {
            log.warn("枚举类型扫描失败", e);
        }
        
        return config;
    }
    
    /**
     * 扫描并注册所有枚举类
     */
    private Set<Class<?>> scanEnumClasses(String basePackage) throws IOException, ClassNotFoundException {
        Set<Class<?>> enumClasses = new HashSet<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
        
        String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + 
                                 ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/*.class";
        
        Resource[] resources = resolver.getResources(packageSearchPath);
        for (Resource resource : resources) {
            if (resource.isReadable()) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                String className = metadataReader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(className);
                if (clazz.isEnum()) {
                    enumClasses.add(clazz);
                }
            }
        }
        
        return enumClasses;
    }
    
    /**
     * 枚举类型配置类，用于保存扫描到的枚举类信息
     */
    public static class EnumTypeConfig {
        private final String packageName;
        private final Set<Class<?>> enumClasses = new HashSet<>();
        
        public EnumTypeConfig(String packageName) {
            this.packageName = packageName;
        }
        
        public void addEnumClass(Class<?> enumClass) {
            enumClasses.add(enumClass);
        }
        
        public Set<Class<?>> getEnumClasses() {
            return enumClasses;
        }
        
        public String getPackageName() {
            return packageName;
        }
    }
} 