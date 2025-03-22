# 构建阶段
FROM maven:3.9.5-eclipse-temurin-21 AS build

# 设置工作目录
WORKDIR /app

# 复制 Maven 配置
COPY ./law-firm-dependencies/pom.xml ./law-firm-dependencies/pom.xml
COPY ./pom.xml ./pom.xml

# 复制各模块的pom.xml以便于缓存依赖
COPY ./law-firm-api/pom.xml ./law-firm-api/pom.xml
COPY ./law-firm-common/pom.xml ./law-firm-common/pom.xml
COPY ./law-firm-core/pom.xml ./law-firm-core/pom.xml
COPY ./law-firm-model/pom.xml ./law-firm-model/pom.xml
COPY ./law-firm-modules/pom.xml ./law-firm-modules/pom.xml
COPY ./law-firm-modules/law-firm-client/pom.xml ./law-firm-modules/law-firm-client/pom.xml
COPY ./law-firm-modules/law-firm-document/pom.xml ./law-firm-modules/law-firm-document/pom.xml
COPY ./law-firm-modules/law-firm-knowledge/pom.xml ./law-firm-modules/law-firm-knowledge/pom.xml
COPY ./law-firm-modules/law-firm-finance/pom.xml ./law-firm-modules/law-firm-finance/pom.xml
COPY ./law-firm-modules/law-firm-system/pom.xml ./law-firm-modules/law-firm-system/pom.xml

# 下载依赖，这一步可以被缓存，除非pom.xml发生变化
RUN mvn dependency:go-offline -B

# 复制源码
COPY ./law-firm-dependencies ./law-firm-dependencies
COPY ./law-firm-api ./law-firm-api
COPY ./law-firm-common ./law-firm-common
COPY ./law-firm-core ./law-firm-core
COPY ./law-firm-model ./law-firm-model
COPY ./law-firm-modules ./law-firm-modules

# 构建应用，跳过测试
RUN mvn clean package -DskipTests -pl law-firm-api -am

# 运行阶段
FROM eclipse-temurin:21-jre

# 设置工作目录
WORKDIR /app

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 设置JVM参数
ENV JAVA_OPTS="-Xms512m -Xmx1024m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:+HeapDumpOnOutOfMemoryError"

# 创建日志目录
RUN mkdir -p /app/logs

# 复制构建好的jar
COPY --from=build /app/law-firm-api/target/law-firm-api-1.0.0.jar /app/law-firm-api.jar

# 暴露端口
EXPOSE 8080

# 健康检查
HEALTHCHECK --interval=30s --timeout=30s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# 启动命令
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -Dspring.profiles.active=docker -jar /app/law-firm-api.jar"] 