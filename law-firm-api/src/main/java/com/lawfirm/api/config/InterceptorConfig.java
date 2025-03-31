package com.lawfirm.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.lawfirm.api.interceptor.RequestInterceptor;
import com.lawfirm.api.util.ApiDocPathHelper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器配置类
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Autowired
    private ApiDocPathHelper apiDocPathHelper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加请求拦截器
        registry.addInterceptor(new RequestInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error")
                // 排除API文档路径
                .excludePathPatterns(getApiDocExcludePaths());
    }
    
    /**
     * 获取需要排除的API文档路径列表
     */
    private String[] getApiDocExcludePaths() {
        // 获取带上下文路径的API文档路径数组
        String[] docPaths = apiDocPathHelper.getApiDocPathsWithContext();
        
        // 创建结果数组，包含错误路径
        String[] result = new String[docPaths.length + 1];
        System.arraycopy(docPaths, 0, result, 0, docPaths.length);
        result[docPaths.length] = "/error";
        
        return result;
    }
} 