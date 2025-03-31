package com.lawfirm.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * API文档统一配置类
 * <p>
 * 集中管理API文档的所有配置：
 * 1. OpenAPI/Swagger配置
 * 2. 安全访问配置
 * 3. 界面路径配置
 * 4. 消息转换配置
 * </p>
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ApiDocConfiguration implements WebMvcConfigurer {

    private final ObjectMapper objectMapper;
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    /**
     * API文档路径常量
     */
    public static final String[] API_DOC_PATHS = {
        "/doc.html", "/doc.html/**", "/doc/**",
        "/swagger-ui.html", "/swagger-ui/**", 
        "/v3/api-docs", "/v3/api-docs/**", "/v3/api-docs-ext/**",
        "/swagger-resources/**", "/swagger-resources",
        "/swagger-config/**", "/swagger-config",
        "/webjars/**", "/webjars",
        "/knife4j/**", "/knife4j",
        "/api-docs/**", "/api-docs",
        "/v2/api-docs/**", "/v2/api-docs",
        "/configuration/ui", "/configuration/security",
        "/favicon.ico", "/markdown/**"
    };
    
    /**
     * OpenAPI配置
     */
    @Bean
    @Primary
    public OpenAPI openAPI() {
        log.info("初始化OpenAPI基础信息配置");
        
        // 规范化上下文路径
        String normalizedContextPath = contextPath;
        if (!normalizedContextPath.startsWith("/")) {
            normalizedContextPath = "/" + normalizedContextPath;
        }
        
        return new OpenAPI()
            .info(new Info()
                .title("律师事务所管理系统API")
                .description("提供律师事务所业务管理所需的各种API接口")
                .version("1.0.0")
                .contact(new Contact()
                    .name("律师事务所开发团队")
                    .email("dev@lawfirm.com")
                    .url("https://lawfirm.com"))
                .license(new License()
                    .name("Apache 2.0")
                    .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
            .servers(List.of(
                new Server()
                    .url(normalizedContextPath)
                    .description("API服务器")
            ))
            .components(new Components()
                .addSecuritySchemes("bearerAuth", 
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT Token认证")))
            .security(List.of(
                new SecurityRequirement().addList("bearerAuth")
            ));
    }
    
    /**
     * 系统API分组
     */
    @Bean
    public GroupedOpenApi systemApiGroup() {
        return GroupedOpenApi.builder()
                .group("system")
                .displayName("系统接口")
                .pathsToMatch("/auth/**", "/login", "/logout", "/refreshToken", 
                             "/user/**", "/system/**", "/menu/**", "/role/**")
                .build();
    }
    
    /**
     * 业务API分组
     */
    @Bean
    public GroupedOpenApi businessApiGroup() {
        return GroupedOpenApi.builder()
                .group("business")
                .displayName("业务接口")
                .pathsToMatch("/case/**", "/client/**", "/contract/**", 
                             "/document/**", "/knowledge/**")
                .build();
    }
    
    /**
     * API文档安全过滤链
     */
    @Bean("apiDocSecurityFilterChain")
    @Primary
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain apiDocSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("配置API文档安全访问 - 最高优先级");
        
        // 规范化上下文路径
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        // 创建路径匹配器
        List<RequestMatcher> matchers = new ArrayList<>();
        for (String path : API_DOC_PATHS) {
            matchers.add(new AntPathRequestMatcher(pathPrefix + path));
        }
        // 添加根路径匹配器，确保首页可访问
        matchers.add(new AntPathRequestMatcher(pathPrefix + "/"));
        
        // 创建组合匹配器
        RequestMatcher docMatcher = new OrRequestMatcher(matchers);
        
        return http
            .securityMatcher(docMatcher)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
            .build();
    }
    
    /**
     * 视图控制器配置
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        log.info("配置API文档路径映射");
        
        // 将根路径重定向到文档页面
        registry.addViewController("/").setViewName("forward:/doc.html");
        registry.addViewController("/doc").setViewName("forward:/doc.html");
        registry.addViewController("/swagger").setViewName("forward:/swagger-ui.html");
        registry.addViewController("/api-docs").setViewName("forward:/doc.html");
    }
    
    /**
     * 配置消息转换器，确保文档正确显示
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("配置API文档消息转换器");
        
        // 创建JSON转换器
        MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
        jacksonConverter.setObjectMapper(objectMapper);
        jacksonConverter.setDefaultCharset(StandardCharsets.UTF_8);
        jacksonConverter.setSupportedMediaTypes(List.of(
                MediaType.APPLICATION_JSON,
                new MediaType("application", "*+json")
        ));
        
        // 创建字符串转换器
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        stringConverter.setSupportedMediaTypes(List.of(
                MediaType.TEXT_PLAIN,
                MediaType.TEXT_HTML,
                MediaType.APPLICATION_JSON
        ));
        
        // 将转换器添加到最前面
        converters.add(0, jacksonConverter);
        converters.add(0, stringConverter);
    }
} 