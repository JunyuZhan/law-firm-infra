# 使用多阶段构建
FROM eclipse-temurin:21-jre

# 设置工作目录
WORKDIR /app

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置JVM参数
ENV JAVA_OPTS="-Xms1024m -Xmx2048m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m -XX:+HeapDumpOnOutOfMemoryError"
ENV CONFIG_OPTS="-Dspring.main.lazy-initialization=true -Dspring.main.allow-bean-definition-overriding=true"
ENV EXCLUDE_OPTS="-Dspring.autoconfigure.exclude=org.flowable.spring.boot.FlowableAutoConfiguration,com.lawfirm.core.workflow.config.FlowableConfig"
ENV FEATURE_OPTS="-Dflowable.enabled=false -Dlawfirm.workflow.enabled=false"
ENV SCAN_OPTS="-Dspring.main.sources=com.lawfirm.api,com.lawfirm.common,com.lawfirm.model,com.lawfirm.core.system,com.lawfirm.core.storage"

# 创建目录
RUN mkdir -p /app/logs /app/storage /app/temp

# 复制预先构建好的jar和启动脚本
COPY target/law-firm-api-1.0.0.jar /app/law-firm-api.jar
COPY startup.sh /app/startup.sh

# 确保脚本有执行权限
RUN chmod +x /app/startup.sh

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动命令 - 使用启动脚本
ENTRYPOINT ["/app/startup.sh"] 