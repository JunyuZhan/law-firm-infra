package com.lawfirm.core.workflow;

import org.flowable.spring.boot.ProcessEngineAutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAutoConfiguration(exclude = {ProcessEngineAutoConfiguration.class})
public class WorkflowApplication {
} 