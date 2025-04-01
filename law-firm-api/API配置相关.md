# 律师事务所系统API文档配置相关文件清单

本文档列出了系统中所有与API文档（Swagger/Knife4j）相关的配置文件和代码文件，并分析了相关配置，用于排查API文档故障。

**最终配置结构总结 (清理后):**

*   **API文档核心配置**: `ApiDocConfiguration` (API层) + `apidoc.yml`
*   **API文档路径定义**: `SecurityConstants` (common-security) - **权威来源**
*   **API文档路径判断**: `ApiDocPathHelper` (API层) - 使用 `SecurityConstants`
*   **API文档安全放行**: `ApiDocConfiguration` (API层, 最高优先级)
*   **主要安全认证**: `SecurityConfig` (auth模块, JWT) / `ProductionSecurityConfig` (API层, 需实现!)
*   **开发环境简化安全**: `DevSecurityFilterConfig` (API层)
*   **默认安全回退**: `BaseSecurityConfig` (common-security)
*   **全局CORS**: `WebConfig` (common-web)
*   **文档静态资源映射**: `WebConfig` (common-web)
*   **应用静态资源映射**: `WebMvcConfig` (API层)
*   **字符编码**: `application.yml` (`server.servlet.encoding`)
*   **消息转换器**: `ApiDocConfiguration` (API层, 高优先级) + `JacksonConfig` (common-data, `@Primary ObjectMapper`)
*   **XSS防护**: `ApiXssFilter` (API层)
*   **拦截器**: `InterceptorConfig` (API层)
*   **生产环境检查**: `ProductionSecurityConfig` (API层) - 包含内部检查逻辑

## 核心配置文件

### 1. API文档专用配置文件
- **law-firm-api/src/main/resources/config/apidoc.yml**
  - API文档主要配置文件，包含Swagger、Knife4j等界面和生成相关的详细配置。

### 2. 应用配置中的API文档设置
- **law-firm-api/src/main/resources/application.yml**
  - 包含 `springdoc` 和 `knife4j` 的部分基础配置 (部分可能已移至 `apidoc.yml`)。
  - `api.doc.enabled`: 控制文档是否启用 (也可能由 `apidoc.yml` 控制)。
  - `server.servlet.encoding`: **负责全局字符编码**。
  - `cors.allowed-origins`: (配合 `WebConfig`) **控制CORS允许的源**。
- **law-firm-api/src/main/resources/application.properties**
  - 可能通过 `spring.config.import` 导入 `apidoc.yml`。
- **law-firm-api/src/main/resources/application-{env}.yml**
  - 不同环境的特定配置，如 `application-prod.yml` 可能通过 `${API_DOCS_ENABLED:false}` 禁用文档。

### 3. 依赖配置
- **law-firm-dependencies/pom.xml**: 管理 `springdoc` 和 `knife4j` 的版本。
- **law-firm-api/pom.xml**: 引入API文档依赖。
- **law-firm-common/common-web/pom.xml**: 引入API文档依赖。

## API文档相关配置类

### 1. 主要API文档配置类
- **law-firm-api/src/main/java/com/lawfirm/api/config/ApiDocConfiguration.java**
  - **核心职责**: API文档的 **OpenAPI Bean定义** (`openAPI`), **安全放行** (`apiDocSecurityFilterChain` - 最高优先级), **路径映射** (`addViewControllers`), **内容协商** (`configureContentNegotiation`), 以及**高优先级消息转换器** (`configureMessageConverters`)。
  - `@Order(Ordered.HIGHEST_PRECEDENCE)` 确保文档路径最先被处理且允许访问。
- **law-firm-common/common-web/src/main/java/com/lawfirm/common/web/config/OpenApiAutoConfiguration.java**
  - 通用OpenAPI自动配置，作为默认配置提供，仅在没有其他OpenAPI Bean（如 `ApiDocConfiguration` 中的）时生效。

### 2. API文档路径定义与判断
- **law-firm-common/common-security/src/main/java/com/lawfirm/common/security/constants/SecurityConstants.java**
  - 定义 **`API_DOC_PATHS`** 常量数组，**权威的**、需要放行的API文档路径列表。
- **law-firm-api/src/main/java/com/lawfirm/api/util/ApiDocPathHelper.java**
  - 辅助类，用于判断请求URI是否属于API文档路径。
  - **重要**: 现在直接使用 `SecurityConstants.API_DOC_PATHS` 进行判断，不再内部定义列表。

### 3. 安全相关配置类 (影响文档访问)
- **law-firm-api/src/main/java/com/lawfirm/api/config/api/ProductionSecurityConfig.java** (`@Profile("prod")`, `@Order(80)`)
  - **关键**: 包含 `productionSecurityFilterChain` Bean，**必须正确实现生产环境的安全认证逻辑** (如JWT/OAuth2)，否则生产环境将不安全！
  - 包含生产环境配置检查逻辑（原 `SecurityChecker`）。
- **law-firm-modules/law-firm-auth/src/main/java/com/lawfirm/auth/config/SecurityConfig.java** (`@Order(90)`)
  - 提供默认的JWT认证过滤器 (`jwtAuthenticationFilter`) 和安全过滤链 (`securityFilterChain`)。该过滤链会排除 `SecurityConstants.API_DOC_PATHS`，然后要求其他请求认证。
- **law-firm-api/src/main/java/com/lawfirm/api/config/dev/DevSecurityFilterConfig.java** (`@Profile("dev")`, `@ConditionalOnProperty`, `@Order(70/50)`)
  - 提供开发环境下的简化安全配置 (`devSecurityFilterChain`)，允许所有非文档请求通过。
- **law-firm-common/common-security/src/main/java/com/lawfirm/common/security/config/BaseSecurityConfig.java** (无 `@Order`, 条件Bean)
  - 提供基础的、默认的安全过滤链 (`baseSecurityFilterChain`)，作为最后的保障，在其他安全链都未生效时要求认证。

### 4. Web MVC 相关配置 (间接影响)
- **law-firm-common/common-web/src/main/java/com/lawfirm/common/web/config/WebConfig.java**
  - **关键**: 配置 **全局CORS** (读取 `cors.allowed-origins` 属性) 和 **API文档静态资源映射** (`addResourceHandlers`)，将 `/webjars/**`, `/doc.html` 等映射到 `classpath:/META-INF/resources/`。
- **law-firm-api/src/main/java/com/lawfirm/api/config/WebMvcConfig.java**
  - 配置应用自身的静态资源 (`/static/**`) 映射。
- **law-firm-common/common-data/src/main/java/com/lawfirm/common/data/config/JacksonConfig.java**
  - 定义 `@Primary` 的 `ObjectMapper` Bean，影响所有JSON序列化。

### 5. 过滤器与拦截器
- **law-firm-api/src/main/java/com/lawfirm/api/filter/ApiXssFilter.java** (`@Order(Ordered.HIGHEST_PRECEDENCE + 10)`)
  - XSS防护过滤器，使用 `ApiDocPathHelper` 明确排除了API文档路径。
- **law-firm-api/src/main/java/com/lawfirm/api/config/InterceptorConfig.java**
  - 配置自定义的 `RequestInterceptor`，使用 `ApiDocPathHelper` 排除了API文档路径。

## 可能的故障原因 (清理后)

1.  **安全配置未放行文档路径**:
    *   确保 `ApiDocConfiguration` 中的 `apiDocSecurityFilterChain` 具有最高优先级 (`@Order(Ordered.HIGHEST_PRECEDENCE)`)。
    *   确保 `SecurityConstants.API_DOC_PATHS` 列表包含了所有需要访问的文档路径 (包括 `/v3/api-docs`, `/swagger-ui.html`, `/doc.html`, `/knife4j/**`, `/webjars/**` 等)。
    *   **检查 `ProductionSecurityConfig`**: 确保其 `productionSecurityFilterChain` **已正确实现**，并且**没有错误地拦截** API文档路径（虽然理论上 `ApiDocConfiguration` 优先级更高，但配置错误仍可能发生）。
2.  **路径定义不一致**:
    *   确认 `ApiDocPathHelper` 正确使用了 `SecurityConstants.API_DOC_PATHS`。
    *   检查 `application.yml` 中 `server.servlet.context-path` 是否配置正确，路径匹配是否考虑了上下文路径。
3.  **静态资源无法加载 (UI问题)**:
    *   确认 `common-web` 中的 `WebConfig` 正确映射了 `/webjars/**` 等资源路径到 `classpath:/META-INF/resources/webjars/`。
    *   确认项目依赖中包含了 `knife4j-openapi3-jakarta-spring-boot-starter` 或 `springdoc-openapi-starter-webmvc-ui` 等包含前端资源的包。
    *   确认构建工具（Maven/Gradle）将这些静态资源正确打包。
4.  **CORS问题**:
    *   确认 `common-web` 的 `WebConfig` 中的 `addCorsMappings` 配置正确，特别是 `allowedOriginPatterns` 读取了正确的配置值。
    *   检查生产环境配置文件 (`application-prod.yml`) 中 `cors.allowed-origins` 是否设置了允许前端访问的域名。
5.  **配置文件启用开关**:
    *   检查对应环境的配置文件中 `api.doc.enabled` 或 `springdoc.api-docs.enabled` / `springdoc.swagger-ui.enabled` / `knife4j.enable` 等相关开关是否设置为 `true`。
    *   注意生产环境可能通过环境变量 `${API_DOCS_ENABLED:false}` 默认禁用。
6.  **依赖版本兼容性**:
    *   参考官方文档或之前添加的兼容性表格，确认 `spring-boot`, `springdoc`, `knife4j` 版本兼容。
    *   **当前项目版本 (Spring Boot 3.2.3, Knife4j 4.1.0) 符合要求**。
7.  **消息转换或编码问题**:
    *   确认 `ApiDocConfiguration` 正确配置了消息转换器。
    *   确认 `JacksonConfig` (common-data) 的 `ObjectMapper` 配置没有意外影响。
    *   确认 `application.yml` 中 `server.servlet.encoding` 设置为 `UTF-8`。

## API文档访问路径

- 主文档访问路径：`${contextPath}/doc.html` (例如: `/api/doc.html`)
- Swagger UI访问路径：`${contextPath}/swagger-ui.html`
- API规范路径：`${contextPath}/v3/api-docs`
- Knife4j路径：`${contextPath}/knife4j/doc.html`
- 其他映射路径（由`ApiDocConfiguration`配置，相对于上下文路径）：
  - `/` 转发到 `doc.html`
  - `/doc` 转发到 `doc.html`
  - `/swagger` 转发到 `swagger-ui.html`
  - `/api-docs` 转发到 `doc.html`
