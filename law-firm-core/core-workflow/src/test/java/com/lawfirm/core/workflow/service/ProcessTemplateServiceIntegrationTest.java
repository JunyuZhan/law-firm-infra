package com.lawfirm.core.workflow.service;

import com.lawfirm.core.workflow.BaseIntegrationTest;
import com.lawfirm.core.workflow.enums.ProcessTemplateEnum;
import org.flowable.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProcessTemplateServiceIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private ProcessTemplateService processTemplateService;

    @Test
    void deployTemplate() throws IOException {
        // 准备测试数据
        String processXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:flowable="http://flowable.org/bpmn"
                           targetNamespace="http://flowable.org/bpmn">
                    <process id="case-create" name="案件创建流程">
                        <startEvent id="start" name="开始"/>
                        <userTask id="task1" name="案件审核" flowable:assignee="${assignee}"/>
                        <endEvent id="end" name="结束"/>
                        <sequenceFlow id="flow1" sourceRef="start" targetRef="task1"/>
                        <sequenceFlow id="flow2" sourceRef="task1" targetRef="end"/>
                    </process>
                </definitions>
                """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "case-create.bpmn20.xml",
                "application/xml",
                processXml.getBytes(StandardCharsets.UTF_8)
        );

        // 部署流程模板
        String deploymentId = processTemplateService.deployTemplate(ProcessTemplateEnum.CASE_CREATE, file);

        // 验证部署结果
        assertNotNull(deploymentId);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploymentId)
                .singleResult();
        assertNotNull(processDefinition);
        assertEquals("case-create", processDefinition.getKey());
        assertEquals("案件创建流程", processDefinition.getName());
        assertEquals(1, processDefinition.getVersion());
    }

    @Test
    void updateTemplate() throws IOException {
        // 准备测试数据
        String processXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:flowable="http://flowable.org/bpmn"
                           targetNamespace="http://flowable.org/bpmn">
                    <process id="case-create" name="案件创建流程-新版本">
                        <startEvent id="start" name="开始"/>
                        <userTask id="task1" name="案件审核" flowable:assignee="${assignee}"/>
                        <userTask id="task2" name="案件复核" flowable:assignee="${reviewer}"/>
                        <endEvent id="end" name="结束"/>
                        <sequenceFlow id="flow1" sourceRef="start" targetRef="task1"/>
                        <sequenceFlow id="flow2" sourceRef="task1" targetRef="task2"/>
                        <sequenceFlow id="flow3" sourceRef="task2" targetRef="end"/>
                    </process>
                </definitions>
                """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "case-create.bpmn20.xml",
                "application/xml",
                processXml.getBytes(StandardCharsets.UTF_8)
        );

        // 部署旧版本
        String oldDeploymentId = processTemplateService.deployTemplate(ProcessTemplateEnum.CASE_CREATE, file);
        assertNotNull(oldDeploymentId);

        // 更新流程模板
        String newDeploymentId = processTemplateService.updateTemplate(ProcessTemplateEnum.CASE_CREATE, file);

        // 验证更新结果
        assertNotNull(newDeploymentId);
        assertNotEquals(oldDeploymentId, newDeploymentId);
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(newDeploymentId)
                .singleResult();
        assertNotNull(processDefinition);
        assertEquals("case-create", processDefinition.getKey());
        assertEquals("案件创建流程-新版本", processDefinition.getName());
        assertEquals(2, processDefinition.getVersion());
    }

    @Test
    void deleteTemplate() throws IOException {
        // 准备测试数据
        String processXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:flowable="http://flowable.org/bpmn"
                           targetNamespace="http://flowable.org/bpmn">
                    <process id="case-create" name="案件创建流程">
                        <startEvent id="start" name="开始"/>
                        <userTask id="task1" name="案件审核" flowable:assignee="${assignee}"/>
                        <endEvent id="end" name="结束"/>
                        <sequenceFlow id="flow1" sourceRef="start" targetRef="task1"/>
                        <sequenceFlow id="flow2" sourceRef="task1" targetRef="end"/>
                    </process>
                </definitions>
                """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "case-create.bpmn20.xml",
                "application/xml",
                processXml.getBytes(StandardCharsets.UTF_8)
        );

        // 部署流程模板
        String deploymentId = processTemplateService.deployTemplate(ProcessTemplateEnum.CASE_CREATE, file);
        assertNotNull(deploymentId);

        // 删除流程模板
        processTemplateService.deleteTemplate(ProcessTemplateEnum.CASE_CREATE, true);

        // 验证删除结果
        assertEquals(0, repositoryService.createDeploymentQuery().count());
        assertEquals(0, repositoryService.createProcessDefinitionQuery().count());
    }

    @Test
    void getTemplateXml() throws IOException {
        // 准备测试数据
        String processXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:flowable="http://flowable.org/bpmn"
                           targetNamespace="http://flowable.org/bpmn">
                    <process id="case-create" name="案件创建流程">
                        <startEvent id="start" name="开始"/>
                        <userTask id="task1" name="案件审核" flowable:assignee="${assignee}"/>
                        <endEvent id="end" name="结束"/>
                        <sequenceFlow id="flow1" sourceRef="start" targetRef="task1"/>
                        <sequenceFlow id="flow2" sourceRef="task1" targetRef="end"/>
                    </process>
                </definitions>
                """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "case-create.bpmn20.xml",
                "application/xml",
                processXml.getBytes(StandardCharsets.UTF_8)
        );

        // 部署流程模板
        String deploymentId = processTemplateService.deployTemplate(ProcessTemplateEnum.CASE_CREATE, file);
        assertNotNull(deploymentId);

        // 获取流程模板XML
        InputStream xmlStream = processTemplateService.getTemplateXml(ProcessTemplateEnum.CASE_CREATE);
        assertNotNull(xmlStream);
        String xml = StreamUtils.copyToString(xmlStream, StandardCharsets.UTF_8);
        assertTrue(xml.contains("案件创建流程"));
        assertTrue(xml.contains("案件审核"));
    }

    @Test
    void getTemplateDiagram() throws IOException {
        // 准备测试数据
        String processXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:flowable="http://flowable.org/bpmn"
                           targetNamespace="http://flowable.org/bpmn">
                    <process id="case-create" name="案件创建流程">
                        <startEvent id="start" name="开始"/>
                        <userTask id="task1" name="案件审核" flowable:assignee="${assignee}"/>
                        <endEvent id="end" name="结束"/>
                        <sequenceFlow id="flow1" sourceRef="start" targetRef="task1"/>
                        <sequenceFlow id="flow2" sourceRef="task1" targetRef="end"/>
                    </process>
                </definitions>
                """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "case-create.bpmn20.xml",
                "application/xml",
                processXml.getBytes(StandardCharsets.UTF_8)
        );

        // 部署流程模板
        String deploymentId = processTemplateService.deployTemplate(ProcessTemplateEnum.CASE_CREATE, file);
        assertNotNull(deploymentId);

        // 获取流程模板图片
        InputStream diagramStream = processTemplateService.getTemplateDiagram(ProcessTemplateEnum.CASE_CREATE);
        assertNotNull(diagramStream);
        byte[] diagram = StreamUtils.copyToByteArray(diagramStream);
        assertTrue(diagram.length > 0);
    }

    @Test
    void listTemplates() {
        // 获取流程模板列表
        List<ProcessTemplateEnum> templates = processTemplateService.listTemplates("cases");
        assertNotNull(templates);
        assertFalse(templates.isEmpty());
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_CREATE));
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_CLOSE));
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_TRANSFER));
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_ARCHIVE));
    }

    @Test
    void validateTemplate() throws IOException {
        // 准备测试数据
        String processXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:flowable="http://flowable.org/bpmn"
                           targetNamespace="http://flowable.org/bpmn">
                    <process id="case-create" name="案件创建流程">
                        <startEvent id="start" name="开始"/>
                        <userTask id="task1" name="案件审核" flowable:assignee="${assignee}"/>
                        <endEvent id="end" name="结束"/>
                        <sequenceFlow id="flow1" sourceRef="start" targetRef="task1"/>
                        <sequenceFlow id="flow2" sourceRef="task1" targetRef="end"/>
                    </process>
                </definitions>
                """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "case-create.bpmn20.xml",
                "application/xml",
                processXml.getBytes(StandardCharsets.UTF_8)
        );

        // 验证流程模板
        boolean valid = processTemplateService.validateTemplate(file);
        assertTrue(valid);
    }

    @Test
    void validateTemplate_Invalid() throws IOException {
        // 准备测试数据（缺少必要的连线）
        String processXml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <definitions xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
                           xmlns:flowable="http://flowable.org/bpmn"
                           targetNamespace="http://flowable.org/bpmn">
                    <process id="case-create" name="案件创建流程">
                        <startEvent id="start" name="开始"/>
                        <userTask id="task1" name="案件审核" flowable:assignee="${assignee}"/>
                        <endEvent id="end" name="结束"/>
                    </process>
                </definitions>
                """;
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "case-create.bpmn20.xml",
                "application/xml",
                processXml.getBytes(StandardCharsets.UTF_8)
        );

        // 验证流程模板
        boolean valid = processTemplateService.validateTemplate(file);
        assertFalse(valid);
    }
} 