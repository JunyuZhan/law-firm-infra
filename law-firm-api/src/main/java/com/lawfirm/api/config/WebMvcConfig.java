package com.lawfirm.api.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Web MVC配置
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 注册错误页面
     * 将所有错误请求重定向到我们的HomeController
     */
    @Bean
    public ErrorPageRegistrar errorPageRegistrar() {
        return new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry registry) {
                // 注册400错误页面
                registry.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/error"));
                // 注册404错误页面
                registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error"));
                // 注册500错误页面
                registry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error"));
                // 注册所有其他错误
                registry.addErrorPages(new ErrorPage(Throwable.class, "/error"));
            }
        };
    }
    
    /**
     * 配置消息转换器
     * 确保字符串转换器使用UTF-8编码，解决中文乱码问题
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加UTF-8编码的StringHttpMessageConverter，处理文本内容
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        
        // 清除默认支持的媒体类型，只保留需要的类型，避免干扰其他转换器
        stringConverter.setSupportedMediaTypes(List.of(
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_HTML,
            new MediaType("text", "javascript", StandardCharsets.UTF_8)
        ));
        
        // 将字符串转换器添加到最前面
        converters.add(0, stringConverter);
    }
    
    /**
     * 配置内容协商
     * 确保支持多种媒体类型，尤其是HTML和JSON
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            .favorParameter(true)
            .parameterName("mediaType")
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("html", MediaType.TEXT_HTML);
    }
    
    /**
     * 配置静态资源处理程序
     * 确保Swagger UI可以正常访问
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 访问Swagger UI
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/swagger-ui/")
                .resourceChain(false);
                
        // 访问Swagger UI HTML
        registry.addResourceHandler("/swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/")
                .resourceChain(false);
                
        // 访问webjars资源
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/")
                .resourceChain(false);
                
        // 访问Swagger配置资源
        registry.addResourceHandler("/swagger-resources/**")
                .addResourceLocations("classpath:/META-INF/resources/swagger-resources/")
                .resourceChain(false);
                
        // 访问静态资源
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/")
                .resourceChain(false);
    }
    
    /**
     * 配置CORS跨域支持
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
} 