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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * Web MVC配置
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer, ErrorPageRegistrar {

    /**
     * 注册错误页面
     * 将所有错误请求重定向到我们的HomeController
     */
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
    
    /**
     * 配置消息转换器
     * 确保正确处理不同内容类型，特别是JSON和文本内容
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 添加JSON转换器
        MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
        // 添加API文档相关MediaType
        jsonConverter.setSupportedMediaTypes(Arrays.asList(
            MediaType.APPLICATION_JSON,
            new MediaType("application", "*+json"),
            new MediaType("application", "json"),
            new MediaType("application", "vnd.api+json")
        ));
        // 将JSON转换器添加到最前面，确保它优先处理JSON内容
        converters.add(0, jsonConverter);

        // 添加字符串转换器
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setSupportedMediaTypes(Arrays.asList(
            MediaType.TEXT_PLAIN,
            MediaType.TEXT_HTML,
            new MediaType("text", "javascript")
        ));
        // 将字符串转换器添加到JSON转换器之后
        converters.add(1, stringConverter);
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
     * 注意：API文档相关资源处理已移至专门的配置类
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
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

    /**
     * 配置字符编码过滤器
     */
    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding(StandardCharsets.UTF_8.name());
        filter.setForceEncoding(true);
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1);
        return registrationBean;
    }
} 