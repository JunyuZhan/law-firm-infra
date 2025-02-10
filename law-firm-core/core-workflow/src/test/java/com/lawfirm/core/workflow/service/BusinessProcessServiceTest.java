package com.lawfirm.core.workflow.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawfirm.core.workflow.entity.BusinessProcessEntity;
import com.lawfirm.core.workflow.model.BusinessProcess;
import com.lawfirm.core.workflow.repository.BusinessProcessRepository;
import com.lawfirm.core.workflow.service.impl.BusinessProcessServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BusinessProcessServiceTest {

    @Mock
    private BusinessProcessRepository businessProcessRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private BusinessProcessServiceImpl businessProcessService;

    private BusinessProcess businessProcess;
    private BusinessProcessEntity entity;

    @BeforeEach
    void setUp() {
        businessProcess = new BusinessProcess()
                .setBusinessType("case")
                .setBusinessId("CASE-2024-001")
                .setBusinessTitle("测试案件")
                .setProcessInstanceId("1001")
                .setProcessDefinitionId("case-process:1:1")
                .setProcessDefinitionKey("case-process")
                .setStartUserId("test-user")
                .setStartTime(LocalDateTime.now())
                .setProcessStatus("running")
                .setCurrentTaskId("2001")
                .setCurrentTaskName("案件审核")
                .setCurrentAssignee("manager")
                .setFormData(new HashMap<>())
                .setProcessVariables(new HashMap<>())
                .setTenantId("default")
                .setRemark("测试备注");

        entity = mock(BusinessProcessEntity.class);
        // 设置mock对象的行为
        when(entity.getBusinessType()).thenReturn("case");
        when(entity.getBusinessId()).thenReturn("CASE-2024-001");
        when(entity.getBusinessTitle()).thenReturn("测试案件");
        when(entity.getProcessInstanceId()).thenReturn("1001");
        when(entity.getProcessDefinitionId()).thenReturn("case-process:1:1");
        when(entity.getProcessDefinitionKey()).thenReturn("case-process");
        when(entity.getStartUserId()).thenReturn("test-user");
        when(entity.getStartTime()).thenReturn(LocalDateTime.now());
        when(entity.getProcessStatus()).thenReturn("running");
        when(entity.getCurrentTaskId()).thenReturn("2001");
        when(entity.getCurrentTaskName()).thenReturn("案件审核");
        when(entity.getCurrentAssignee()).thenReturn("manager");
        when(entity.getFormData()).thenReturn("{}");
        when(entity.getProcessVariables()).thenReturn("{}");
        when(entity.getTenantId()).thenReturn("default");
        when(entity.getRemark()).thenReturn("测试备注");
    }

    @Test
    void createBusinessProcess() {
        when(businessProcessRepository.save(any())).thenReturn(entity);

        BusinessProcess result = businessProcessService.createBusinessProcess(businessProcess);

        assertNotNull(result);
        assertEquals(businessProcess.getBusinessType(), result.getBusinessType());
        assertEquals(businessProcess.getBusinessId(), result.getBusinessId());
        assertEquals(businessProcess.getBusinessTitle(), result.getBusinessTitle());
        assertEquals(businessProcess.getProcessInstanceId(), result.getProcessInstanceId());
        assertEquals(businessProcess.getProcessDefinitionId(), result.getProcessDefinitionId());
        assertEquals(businessProcess.getProcessDefinitionKey(), result.getProcessDefinitionKey());
        assertEquals(businessProcess.getStartUserId(), result.getStartUserId());
        assertEquals(businessProcess.getProcessStatus(), result.getProcessStatus());
        assertEquals(businessProcess.getCurrentTaskId(), result.getCurrentTaskId());
        assertEquals(businessProcess.getCurrentTaskName(), result.getCurrentTaskName());
        assertEquals(businessProcess.getCurrentAssignee(), result.getCurrentAssignee());
        assertEquals(businessProcess.getTenantId(), result.getTenantId());
        assertEquals(businessProcess.getRemark(), result.getRemark());

        verify(businessProcessRepository).save(any());
    }

    @Test
    void updateBusinessProcess() {
        when(businessProcessRepository.findByBusinessTypeAndBusinessId(anyString(), anyString()))
                .thenReturn(Optional.of(entity));
        when(businessProcessRepository.save(any())).thenReturn(entity);

        BusinessProcess result = businessProcessService.updateBusinessProcess(businessProcess);

        assertNotNull(result);
        assertEquals(businessProcess.getBusinessType(), result.getBusinessType());
        assertEquals(businessProcess.getBusinessId(), result.getBusinessId());
        assertEquals(businessProcess.getBusinessTitle(), result.getBusinessTitle());
        assertEquals(businessProcess.getProcessInstanceId(), result.getProcessInstanceId());
        assertEquals(businessProcess.getProcessDefinitionId(), result.getProcessDefinitionId());
        assertEquals(businessProcess.getProcessDefinitionKey(), result.getProcessDefinitionKey());
        assertEquals(businessProcess.getStartUserId(), result.getStartUserId());
        assertEquals(businessProcess.getProcessStatus(), result.getProcessStatus());
        assertEquals(businessProcess.getCurrentTaskId(), result.getCurrentTaskId());
        assertEquals(businessProcess.getCurrentTaskName(), result.getCurrentTaskName());
        assertEquals(businessProcess.getCurrentAssignee(), result.getCurrentAssignee());
        assertEquals(businessProcess.getTenantId(), result.getTenantId());
        assertEquals(businessProcess.getRemark(), result.getRemark());

        verify(businessProcessRepository).findByBusinessTypeAndBusinessId(anyString(), anyString());
        verify(businessProcessRepository).save(any());
    }

    @Test
    void deleteBusinessProcess() {
        businessProcessService.deleteBusinessProcess(businessProcess.getBusinessType(), businessProcess.getBusinessId());

        verify(businessProcessRepository).deleteByBusinessTypeAndBusinessId(
                businessProcess.getBusinessType(), businessProcess.getBusinessId());
    }

    @Test
    void getBusinessProcess() {
        when(businessProcessRepository.findByBusinessTypeAndBusinessId(
                businessProcess.getBusinessType(), businessProcess.getBusinessId()))
                .thenReturn(Optional.of(entity));

        BusinessProcess result = businessProcessService.getBusinessProcess(
                businessProcess.getBusinessType(), businessProcess.getBusinessId());

        assertNotNull(result);
        assertEquals(businessProcess.getBusinessType(), result.getBusinessType());
        assertEquals(businessProcess.getBusinessId(), result.getBusinessId());
        assertEquals(businessProcess.getBusinessTitle(), result.getBusinessTitle());
        assertEquals(businessProcess.getProcessInstanceId(), result.getProcessInstanceId());
        assertEquals(businessProcess.getProcessDefinitionId(), result.getProcessDefinitionId());
        assertEquals(businessProcess.getProcessDefinitionKey(), result.getProcessDefinitionKey());
        assertEquals(businessProcess.getStartUserId(), result.getStartUserId());
        assertEquals(businessProcess.getProcessStatus(), result.getProcessStatus());
        assertEquals(businessProcess.getCurrentTaskId(), result.getCurrentTaskId());
        assertEquals(businessProcess.getCurrentTaskName(), result.getCurrentTaskName());
        assertEquals(businessProcess.getCurrentAssignee(), result.getCurrentAssignee());
        assertEquals(businessProcess.getTenantId(), result.getTenantId());
        assertEquals(businessProcess.getRemark(), result.getRemark());

        verify(businessProcessRepository).findByBusinessTypeAndBusinessId(
                businessProcess.getBusinessType(), businessProcess.getBusinessId());
    }

    @Test
    void getByProcessInstanceId() {
        when(businessProcessRepository.findByProcessInstanceId(businessProcess.getProcessInstanceId()))
                .thenReturn(Optional.of(entity));

        BusinessProcess result = businessProcessService.getByProcessInstanceId(businessProcess.getProcessInstanceId());

        assertNotNull(result);
        assertEquals(businessProcess.getBusinessType(), result.getBusinessType());
        assertEquals(businessProcess.getBusinessId(), result.getBusinessId());
        assertEquals(businessProcess.getBusinessTitle(), result.getBusinessTitle());
        assertEquals(businessProcess.getProcessInstanceId(), result.getProcessInstanceId());
        assertEquals(businessProcess.getProcessDefinitionId(), result.getProcessDefinitionId());
        assertEquals(businessProcess.getProcessDefinitionKey(), result.getProcessDefinitionKey());
        assertEquals(businessProcess.getStartUserId(), result.getStartUserId());
        assertEquals(businessProcess.getProcessStatus(), result.getProcessStatus());
        assertEquals(businessProcess.getCurrentTaskId(), result.getCurrentTaskId());
        assertEquals(businessProcess.getCurrentTaskName(), result.getCurrentTaskName());
        assertEquals(businessProcess.getCurrentAssignee(), result.getCurrentAssignee());
        assertEquals(businessProcess.getTenantId(), result.getTenantId());
        assertEquals(businessProcess.getRemark(), result.getRemark());

        verify(businessProcessRepository).findByProcessInstanceId(businessProcess.getProcessInstanceId());
    }

    @Test
    void listBusinessProcesses() {
        List<BusinessProcessEntity> entities = Arrays.asList(entity);
        when(businessProcessRepository.findByBusinessTypeAndProcessStatusAndStartUserId(
                businessProcess.getBusinessType(), businessProcess.getProcessStatus(), businessProcess.getStartUserId()))
                .thenReturn(entities);

        List<BusinessProcess> results = businessProcessService.listBusinessProcesses(
                businessProcess.getBusinessType(), businessProcess.getProcessStatus(), businessProcess.getStartUserId());

        assertNotNull(results);
        assertEquals(1, results.size());
        BusinessProcess result = results.get(0);
        assertEquals(businessProcess.getBusinessType(), result.getBusinessType());
        assertEquals(businessProcess.getBusinessId(), result.getBusinessId());
        assertEquals(businessProcess.getBusinessTitle(), result.getBusinessTitle());
        assertEquals(businessProcess.getProcessInstanceId(), result.getProcessInstanceId());
        assertEquals(businessProcess.getProcessDefinitionId(), result.getProcessDefinitionId());
        assertEquals(businessProcess.getProcessDefinitionKey(), result.getProcessDefinitionKey());
        assertEquals(businessProcess.getStartUserId(), result.getStartUserId());
        assertEquals(businessProcess.getProcessStatus(), result.getProcessStatus());
        assertEquals(businessProcess.getCurrentTaskId(), result.getCurrentTaskId());
        assertEquals(businessProcess.getCurrentTaskName(), result.getCurrentTaskName());
        assertEquals(businessProcess.getCurrentAssignee(), result.getCurrentAssignee());
        assertEquals(businessProcess.getTenantId(), result.getTenantId());
        assertEquals(businessProcess.getRemark(), result.getRemark());

        verify(businessProcessRepository).findByBusinessTypeAndProcessStatusAndStartUserId(
                businessProcess.getBusinessType(), businessProcess.getProcessStatus(), businessProcess.getStartUserId());
    }
} 