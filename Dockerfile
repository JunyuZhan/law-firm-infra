# 多阶段构建
# 第一阶段：编译项目
FROM maven:3.9.5-eclipse-temurin-21 AS build

# 设置工作目录
WORKDIR /build

# 复制整个项目
COPY . .

# 设置构建参数
ENV BUILD_OPTS="-DskipTests -Dflowable.enabled=false -Dlawfirm.workflow.enabled=false"

# 清理并构建项目 - 忽略子模块检查问题
RUN mvn clean install ${BUILD_OPTS} -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dfindbugs.skip=true -Dcheckstyle.skip=true -Dpmd.skip=true -Dspotbugs.skip=true -Denforcer.skip=true -pl law-firm-api -am

# 第二阶段：运行环境
FROM eclipse-temurin:21-jre

# 设置工作目录
WORKDIR /app

# 设置时区为中国时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置运行参数
ENV JAVA_OPTS="-Xms1024m -Xmx2048m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/app/logs/java_heapdump.hprof -Xlog:gc*=info:file=/app/logs/gc.log:time,uptime,level,tags:filecount=10,filesize=10M"
ENV CONFIG_OPTS="-Dspring.main.lazy-initialization=true -Dspring.main.allow-bean-definition-overriding=true -Dspring.main.banner-mode=off"
ENV EXCLUDE_OPTS="-Dspring.autoconfigure.exclude=org.flowable.spring.boot.FlowableAutoConfiguration"
ENV FEATURE_OPTS="-Dflowable.enabled=false -Dlawfirm.workflow.enabled=false"
ENV SCAN_OPTS="-Dspring.main.allow-circular-references=true"
ENV MONITOR_OPTS="-Dmanagement.endpoints.web.exposure.include=health,info,metrics,prometheus -Dmanagement.endpoint.health.show-details=always -Dmanagement.endpoint.health.show-components=always -Dmanagement.endpoint.metrics.enabled=true -Dmanagement.endpoint.prometheus.enabled=true"

# 创建目录
RUN mkdir -p /app/logs /app/storage /app/temp

# 复制构建好的JAR
COPY --from=build /build/law-firm-api/target/law-firm-api-1.0.0.jar /app/app.jar

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} ${CONFIG_OPTS} ${EXCLUDE_OPTS} ${FEATURE_OPTS} ${SCAN_OPTS} ${MONITOR_OPTS} -Dspring.profiles.active=docker -jar /app/app.jar"]