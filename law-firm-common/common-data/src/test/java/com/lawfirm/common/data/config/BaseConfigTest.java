package com.lawfirm.common.data.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@ActiveProfiles("test")
@TestPropertySource(properties = {
    "spring.main.allow-bean-definition-overriding=true",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.data.redis.host=localhost",
    "spring.data.redis.port=6379",
    "spring.data.redis.database=0",
    "spring.elasticsearch.enabled=true",
    "spring.elasticsearch.uris=http://localhost:9200",
    "mybatis-plus.mapper-locations=classpath*:/mapper/**/*.xml",
    "mybatis-plus.type-aliases-package=com.lawfirm.**.entity"
})
public abstract class BaseConfigTest {
    // 基础测试类配置
} 