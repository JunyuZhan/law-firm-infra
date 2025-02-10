package com.lawfirm.core.workflow;

import com.lawfirm.core.workflow.config.IntegrationTestConfig;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

/**
 * 工作流集成测试基类
 */
@SpringBootTest(classes = WorkflowApplication.class)
@Import(IntegrationTestConfig.class)
@ActiveProfiles("test")
@Transactional
public abstract class BaseIntegrationTest {

    @Autowired
    protected ProcessEngine processEngine;

    @Autowired
    protected RepositoryService repositoryService;

    @Autowired
    protected RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    @BeforeEach
    public void setUp() {
        // 清理历史数据
        clearDeployments();
    }

    @AfterEach
    public void tearDown() {
        // 清理测试数据
        clearDeployments();
    }

    /**
     * 清理所有部署
     */
    protected void clearDeployments() {
        repositoryService.createDeploymentQuery()
                .list()
                .forEach(deployment -> repositoryService.deleteDeployment(deployment.getId(), true));
    }

    /**
     * 部署流程定义
     */
    protected String deployProcessDefinition(String resourceName, String processDefinitionKey) {
        return repositoryService.createDeployment()
                .addClasspathResource("processes/" + resourceName)
                .key(processDefinitionKey)
                .deploy()
                .getId();
    }

    /**
     * 启动流程实例
     */
    protected String startProcessInstance(String processDefinitionKey) {
        return runtimeService.startProcessInstanceByKey(processDefinitionKey)
                .getProcessInstanceId();
    }

    /**
     * 完成任务
     */
    protected void completeTask(String taskId) {
        taskService.complete(taskId);
    }

    /**
     * 获取流程实例的当前任务
     */
    protected String getCurrentTaskId(String processInstanceId) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getId();
    }

    /**
     * 获取流程实例的当前任务名称
     */
    protected String getCurrentTaskName(String processInstanceId) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getName();
    }

    /**
     * 获取流程实例的当前任务处理人
     */
    protected String getCurrentTaskAssignee(String processInstanceId) {
        return taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getAssignee();
    }

    /**
     * 设置任务处理人
     */
    protected void setTaskAssignee(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
    }

    /**
     * 添加任务候选人
     */
    protected void addTaskCandidateUser(String taskId, String userId) {
        taskService.addCandidateUser(taskId, userId);
    }

    /**
     * 添加任务候选组
     */
    protected void addTaskCandidateGroup(String taskId, String groupId) {
        taskService.addCandidateGroup(taskId, groupId);
    }
} 