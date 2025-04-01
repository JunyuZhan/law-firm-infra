package com.lawfirm.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.Schema;
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
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawfirm.common.security.constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

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
@EnableWebSecurity
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiDocConfiguration implements WebMvcConfigurer {

    private final ObjectMapper apiDocObjectMapper;
    
    @Value("${server.servlet.context-path:/api}")
    private String contextPath;
    
    @Autowired
    public ApiDocConfiguration(@Qualifier("apiDocObjectMapper") ObjectMapper apiDocObjectMapper) {
        this.apiDocObjectMapper = apiDocObjectMapper;
        log.info("ApiDocConfiguration已初始化，使用apiDocObjectMapper");
    }
    
    /**
     * 配置默认内容协商策略
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        log.info("配置API文档内容协商策略");
        
        configurer
            .defaultContentType(org.springframework.http.MediaType.APPLICATION_JSON)
            .favorParameter(false)
            .ignoreAcceptHeader(false)
            .useRegisteredExtensionsOnly(false)
            .mediaType("json", org.springframework.http.MediaType.APPLICATION_JSON);
    }
    
    /**
     * OpenAPI配置
     */
    @Bean(name = "lawFirmOpenAPI")
    public OpenAPI openAPI() {
        log.info("初始化OpenAPI基础信息配置");
        
        try {
            // 规范化上下文路径
            String normalizedContextPath = contextPath;
            if (!normalizedContextPath.startsWith("/")) {
                normalizedContextPath = "/" + normalizedContextPath;
            }
            
            OpenAPI api = new OpenAPI()
                // 修正OpenAPI版本格式为3.0.1而不是字符串"3.0.1"
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
                
            // 添加Swagger兼容配置
            api.addExtension("x-generator", "OpenAPI Generator");
            
            // 测试序列化
            log.info("测试OpenAPI对象序列化");
            String serialized = apiDocObjectMapper.writeValueAsString(api);
            log.info("OpenAPI对象序列化成功: {}", serialized.substring(0, Math.min(100, serialized.length())) + "...");
            
            return api;
        } catch (Exception e) {
            log.error("初始化OpenAPI配置时发生异常", e);
            throw new RuntimeException("初始化OpenAPI配置失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * API分组 - 单一分组配置，避免默认分组冲突
     */
    @Bean(name = "lawFirmAllApiGroup")
    public GroupedOpenApi allApiGroup() {
        try {
            log.info("初始化统一API分组");
            return GroupedOpenApi.builder()
                    .group("all")
                    .displayName("所有接口")
                    .pathsToMatch("/**")
                    .build();
        } catch (Exception e) {
            log.error("初始化API分组时发生异常", e);
            throw new RuntimeException("初始化API分组失败: " + e.getMessage(), e);
        }
    }
    
    /**
     * API文档安全过滤链
     */
    @Bean("apiDocWebSecurityFilterChain")
    public SecurityFilterChain apiDocSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("开始配置API文档安全访问 - 最高优先级");
        
        // 规范化上下文路径
        String pathPrefix = "";
        if (contextPath != null && !contextPath.equals("/")) {
            pathPrefix = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
        }
        
        log.info("API文档上下文路径前缀: {}", pathPrefix);
        
        // 创建路径匹配器
        List<RequestMatcher> matchers = new ArrayList<>();
        
        // 添加标准路径模式
        for (String path : SecurityConstants.API_DOC_PATHS) {
            String fullPath = pathPrefix + path;
            log.debug("添加API文档匹配路径: {}", fullPath);
            matchers.add(new AntPathRequestMatcher(fullPath));
        }
        
        // 添加直接路径 - 不附加上下文路径
        String[] directPaths = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/v3/api-docs/swagger-config",
            "/v3/api-docs/all",
            "/v3/api-docs/business",
            "/v3/api-docs/system",
            "/webjars/**",
            "/doc.html",
            "/doc.html/**",
            "/knife4j/**"
        };
        
        for (String path : directPaths) {
            log.debug("添加直接路径匹配: {}", path);
            matchers.add(new AntPathRequestMatcher(path));
        }
        
        // 添加根路径匹配器，确保首页可访问
        String rootPath = pathPrefix + "/";
        log.debug("添加根路径匹配: {}", rootPath);
        matchers.add(new AntPathRequestMatcher(rootPath));
        
        // 如果 contextPath 不为空，也需要匹配 contextPath 本身
        if (StringUtils.hasText(pathPrefix)) {
            log.debug("添加上下文路径匹配: {}", pathPrefix);
            matchers.add(new AntPathRequestMatcher(pathPrefix));
        }
        
        // 添加缺失的路径匹配
        String[] additionalPaths = {
            "/favicon.ico",
            "/error",
            "/actuator/health"
        };
        
        for (String path : additionalPaths) {
            log.debug("添加额外路径匹配: {}", path);
            matchers.add(new AntPathRequestMatcher(path));
        }
        
        // 输出调试信息
        log.info("最终安全匹配路径列表：");
        matchers.forEach(m -> {
            if (m instanceof AntPathRequestMatcher) {
                log.info(" - {}", ((AntPathRequestMatcher)m).getPattern());
            } else {
                log.info(" - {}", m);
            }
        });
        
        // 创建组合匹配器
        RequestMatcher docMatcher = new OrRequestMatcher(matchers);
        
        log.info("开始构建SecurityFilterChain");
        SecurityFilterChain chain = http
            .securityMatcher(docMatcher)
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .cors(cors -> cors.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .sessionManagement(session -> session.disable())
            .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self' 'unsafe-inline' 'unsafe-eval' data: blob:"))
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            )
            .build();
        
        log.info("SecurityFilterChain构建完成");
        return chain;
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
     * 配置消息转换器
     * 为API文档定制专门的消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("配置API文档消息转换器");
        
        try {
            // 先移除所有现有的Jackson转换器
            List<HttpMessageConverter<?>> jacksonConverters = converters.stream()
                .filter(c -> c instanceof MappingJackson2HttpMessageConverter)
                .collect(Collectors.toList());
            
            if (!jacksonConverters.isEmpty()) {
                log.info("移除{}个现有Jackson转换器", jacksonConverters.size());
                converters.removeAll(jacksonConverters);
            }
            
            // 添加String消息转换器，确保字符集正确
            StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
            stringConverter.setSupportedMediaTypes(List.of(
                org.springframework.http.MediaType.TEXT_PLAIN,
                org.springframework.http.MediaType.TEXT_HTML,
                org.springframework.http.MediaType.APPLICATION_JSON,
                org.springframework.http.MediaType.valueOf("application/json;charset=UTF-8")
            ));
            converters.add(0, stringConverter);
            
            // 使用API文档专用的objectMapper配置JSON转换器
            MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter(apiDocObjectMapper);
            jsonConverter.setSupportedMediaTypes(List.of(
                org.springframework.http.MediaType.APPLICATION_JSON,
                org.springframework.http.MediaType.valueOf("application/json;charset=UTF-8")
            ));
            converters.add(0, jsonConverter);
            
            log.info("API文档消息转换器配置完成，当前转换器数量: {}", converters.size());
        } catch (Exception e) {
            log.error("配置API文档消息转换器时发生错误", e);
        }
    }
} 