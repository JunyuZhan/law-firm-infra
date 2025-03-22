# 第一阶段：构建
FROM maven:3.9.5-eclipse-temurin-21 AS builder

# 设置工作目录
WORKDIR /app

# 复制Maven配置
COPY ./.m2/settings.xml /root/.m2/settings.xml

# 先复制pom文件，利用Docker缓存机制提高构建速度
COPY ./pom.xml /app/
COPY ./law-firm-dependencies/pom.xml /app/law-firm-dependencies/
COPY ./law-firm-common/pom.xml /app/law-firm-common/
COPY ./law-firm-model/pom.xml /app/law-firm-model/
COPY ./law-firm-core/pom.xml /app/law-firm-core/
COPY ./law-firm-modules/pom.xml /app/law-firm-modules/
COPY ./law-firm-api/pom.xml /app/law-firm-api/

# 子模块POM
COPY ./law-firm-common/**/pom.xml /app/law-firm-common/
COPY ./law-firm-model/**/pom.xml /app/law-firm-model/
COPY ./law-firm-core/**/pom.xml /app/law-firm-core/
COPY ./law-firm-modules/**/pom.xml /app/law-firm-modules/

# 下载依赖（这一步会被缓存）
RUN mvn dependency:go-offline -B

# 复制源码
COPY ./law-firm-dependencies/src /app/law-firm-dependencies/src
COPY ./law-firm-common/src /app/law-firm-common/src
COPY ./law-firm-model/src /app/law-firm-model/src
COPY ./law-firm-core/src /app/law-firm-core/src
COPY ./law-firm-modules/src /app/law-firm-modules/src
COPY ./law-firm-api/src /app/law-firm-api/src

# 构建项目（跳过测试）
RUN mvn clean package -DskipTests

# 第二阶段：运行
FROM eclipse-temurin:21-jre

LABEL maintainer="JunyuZhan <junyuzhan@outlook.com>"
LABEL description="律师事务所管理系统"

# 设置工作目录
WORKDIR /app

# 设置时区为中国时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置JVM参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:+UseG1GC -XX:MaxGCPauseMillis=200"

# 从构建阶段复制编译好的jar文件
COPY --from=builder /app/law-firm-api/target/law-firm-api-*.jar /app/law-firm-api.jar

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/law-firm-api.jar"] 