package com.lawfirm.core.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.entity.ProcessTemplateEntity;
import com.lawfirm.core.workflow.enums.ProcessTemplateEnum;
import com.lawfirm.core.workflow.repository.ProcessTemplateRepository;
import com.lawfirm.core.workflow.service.impl.ProcessTemplateServiceImpl;
import org.flowable.bpmn.converter.BpmnXMLConverter;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProcessTemplateServiceTest {

    @Mock
    private ProcessTemplateRepository processTemplateRepository;

    @Mock
    private RepositoryService repositoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private BpmnXMLConverter bpmnXMLConverter;

    @InjectMocks
    private ProcessTemplateServiceImpl processTemplateService;

    private ProcessTemplateEntity entity;
    private MockMultipartFile file;
    private ProcessTemplateEnum template;
    private BpmnModel bpmnModel;

    @BeforeEach
    void setUp() {
        template = ProcessTemplateEnum.CASE_CREATE;
        file = new MockMultipartFile(
                "file",
                "test.bpmn20.xml",
                "application/xml",
                "<definitions></definitions>".getBytes()
        );

        bpmnModel = new BpmnModel();
        // 不再mock BpmnXMLConverter，因为它的方法签名在不同版本可能不同
        // 改为在需要时直接使用bpmnModel

        entity = new ProcessTemplateEntity()
                .setProcessKey(template.getProcessKey())
                .setProcessName(template.getProcessName())
                .setCategory(template.getCategory())
                .setDeploymentId("1001")
                .setProcessDefinitionId("case-create:1:1")
                .setFileName("test.bpmn20.xml")
                .setXmlContent("<definitions></definitions>")
                .setVersion(1)
                .setLatest(true)
                .setCreateTime(LocalDateTime.now());
    }

    @Test
    void deployTemplate() {
        // Mock ProcessValidator
        ProcessValidator processValidator = mock(ProcessValidator.class);
        when(processValidator.validate(any())).thenReturn(Collections.emptyList());

        try (MockedStatic<ProcessValidatorFactory> factoryMockedStatic = mockStatic(ProcessValidatorFactory.class)) {
            ProcessValidatorFactory factory = mock(ProcessValidatorFactory.class);
            factoryMockedStatic.when(ProcessValidatorFactory::new).thenReturn(factory);
            when(factory.createDefaultProcessValidator()).thenReturn(processValidator);

            // Mock Deployment
            Deployment deployment = mock(Deployment.class);
            when(deployment.getId()).thenReturn("1001");
            when(repositoryService.createDeployment()).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any())).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any()).name(any())).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any()).name(any()).category(any())).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any()).name(any()).category(any()).deploy()).thenReturn(deployment);

            // Mock ProcessDefinition
            ProcessDefinition processDefinition = mock(ProcessDefinition.class);
            when(processDefinition.getId()).thenReturn("case-create:1:1");
            when(processDefinition.getVersion()).thenReturn(1);
            when(repositoryService.createProcessDefinitionQuery()).thenReturn(mock(org.flowable.engine.repository.ProcessDefinitionQuery.class));
            when(repositoryService.createProcessDefinitionQuery().deploymentId(any())).thenReturn(mock(org.flowable.engine.repository.ProcessDefinitionQuery.class));
            when(repositoryService.createProcessDefinitionQuery().deploymentId(any()).singleResult()).thenReturn(processDefinition);

            String deploymentId = processTemplateService.deployTemplate(template, file);

            assertNotNull(deploymentId);
            assertEquals("1001", deploymentId);

            verify(processTemplateRepository).findByProcessKeyAndLatestTrue(template.getProcessKey());
            verify(processTemplateRepository).save(any(ProcessTemplateEntity.class));
        }
    }

    @Test
    void updateTemplate() {
        when(processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey()))
                .thenReturn(Optional.of(entity));

        // Mock ProcessValidator
        ProcessValidator processValidator = mock(ProcessValidator.class);
        when(processValidator.validate(any())).thenReturn(Collections.emptyList());

        try (MockedStatic<ProcessValidatorFactory> factoryMockedStatic = mockStatic(ProcessValidatorFactory.class)) {
            ProcessValidatorFactory factory = mock(ProcessValidatorFactory.class);
            factoryMockedStatic.when(ProcessValidatorFactory::new).thenReturn(factory);
            when(factory.createDefaultProcessValidator()).thenReturn(processValidator);

            // Mock Deployment
            Deployment deployment = mock(Deployment.class);
            when(deployment.getId()).thenReturn("1002");
            when(repositoryService.createDeployment()).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any())).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any()).name(any())).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any()).name(any()).category(any())).thenReturn(mock(org.flowable.engine.repository.DeploymentBuilder.class));
            when(repositoryService.createDeployment().addInputStream(any(), any()).name(any()).category(any()).deploy()).thenReturn(deployment);

            // Mock ProcessDefinition
            ProcessDefinition processDefinition = mock(ProcessDefinition.class);
            when(processDefinition.getId()).thenReturn("case-create:2:1");
            when(processDefinition.getVersion()).thenReturn(2);
            when(repositoryService.createProcessDefinitionQuery()).thenReturn(mock(org.flowable.engine.repository.ProcessDefinitionQuery.class));
            when(repositoryService.createProcessDefinitionQuery().deploymentId(any())).thenReturn(mock(org.flowable.engine.repository.ProcessDefinitionQuery.class));
            when(repositoryService.createProcessDefinitionQuery().deploymentId(any()).singleResult()).thenReturn(processDefinition);

            String deploymentId = processTemplateService.updateTemplate(template, file);

            assertNotNull(deploymentId);
            assertEquals("1002", deploymentId);

            verify(repositoryService).deleteDeployment(entity.getDeploymentId(), true);
            verify(processTemplateRepository, times(2)).findByProcessKeyAndLatestTrue(template.getProcessKey());
            verify(processTemplateRepository).save(any(ProcessTemplateEntity.class));
        }
    }

    @Test
    void deleteTemplate() {
        when(processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey()))
                .thenReturn(Optional.of(entity));

        processTemplateService.deleteTemplate(template, true);

        verify(repositoryService).deleteDeployment(entity.getDeploymentId(), true);
        verify(processTemplateRepository).findByProcessKeyAndLatestTrue(template.getProcessKey());
        verify(processTemplateRepository).delete(entity);
    }

    @Test
    void getTemplateDefinitionId() {
        when(processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey()))
                .thenReturn(Optional.of(entity));

        String definitionId = processTemplateService.getTemplateDefinitionId(template);

        assertNotNull(definitionId);
        assertEquals(entity.getProcessDefinitionId(), definitionId);

        verify(processTemplateRepository).findByProcessKeyAndLatestTrue(template.getProcessKey());
    }

    @Test
    void getTemplateXml() {
        when(processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey()))
                .thenReturn(Optional.of(entity));

        InputStream inputStream = processTemplateService.getTemplateXml(template);

        assertNotNull(inputStream);

        verify(processTemplateRepository).findByProcessKeyAndLatestTrue(template.getProcessKey());
    }

    @Test
    void getTemplateDiagram() {
        when(processTemplateRepository.findByProcessKeyAndLatestTrue(template.getProcessKey()))
                .thenReturn(Optional.of(entity));
        when(repositoryService.getProcessDiagram(entity.getProcessDefinitionId()))
                .thenReturn(new ByteArrayInputStream("test".getBytes()));

        InputStream inputStream = processTemplateService.getTemplateDiagram(template);

        assertNotNull(inputStream);

        verify(processTemplateRepository).findByProcessKeyAndLatestTrue(template.getProcessKey());
        verify(repositoryService).getProcessDiagram(entity.getProcessDefinitionId());
    }

    @Test
    void listTemplates() {
        List<ProcessTemplateEnum> templates = processTemplateService.listTemplates("cases");

        assertNotNull(templates);
        assertFalse(templates.isEmpty());
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_CREATE));
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_CLOSE));
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_TRANSFER));
        assertTrue(templates.contains(ProcessTemplateEnum.CASE_ARCHIVE));
    }

    @Test
    void validateTemplate() {
        // Mock ProcessValidator
        ProcessValidator processValidator = mock(ProcessValidator.class);
        when(processValidator.validate(any())).thenReturn(Collections.emptyList());

        try (MockedStatic<ProcessValidatorFactory> factoryMockedStatic = mockStatic(ProcessValidatorFactory.class)) {
            ProcessValidatorFactory factory = mock(ProcessValidatorFactory.class);
            factoryMockedStatic.when(ProcessValidatorFactory::new).thenReturn(factory);
            when(factory.createDefaultProcessValidator()).thenReturn(processValidator);

            boolean valid = processTemplateService.validateTemplate(file);

            assertTrue(valid);
        }
    }

    @Test
    void validateTemplate_Invalid() {
        // Mock ProcessValidator
        ProcessValidator processValidator = mock(ProcessValidator.class);
        List<ValidationError> errors = Arrays.asList(
            mock(ValidationError.class),
            mock(ValidationError.class)
        );
        when(processValidator.validate(any())).thenReturn(errors);

        try (MockedStatic<ProcessValidatorFactory> factoryMockedStatic = mockStatic(ProcessValidatorFactory.class)) {
            ProcessValidatorFactory factory = mock(ProcessValidatorFactory.class);
            factoryMockedStatic.when(ProcessValidatorFactory::new).thenReturn(factory);
            when(factory.createDefaultProcessValidator()).thenReturn(processValidator);

            boolean valid = processTemplateService.validateTemplate(file);

            assertFalse(valid);
        }
    }
} 