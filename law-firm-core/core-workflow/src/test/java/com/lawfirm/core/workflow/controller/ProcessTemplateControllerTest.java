package com.lawfirm.core.workflow.controller;

import com.lawfirm.core.workflow.enums.ProcessTemplateEnum;
import com.lawfirm.core.workflow.service.ProcessTemplateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProcessTemplateController.class)
class ProcessTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProcessTemplateService processTemplateService;

    private ProcessTemplateEnum template;
    private MockMultipartFile file;

    @BeforeEach
    void setUp() {
        template = ProcessTemplateEnum.CASE_CREATE;
        file = new MockMultipartFile(
                "file",
                "test.bpmn20.xml",
                "application/xml",
                "<definitions></definitions>".getBytes()
        );
    }

    @Test
    void deployTemplate() throws Exception {
        when(processTemplateService.deployTemplate(eq(template), any(MockMultipartFile.class)))
                .thenReturn("1001");

        mockMvc.perform(multipart("/api/workflow/templates/{processKey}/deploy", template.name())
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("1001"));
    }

    @Test
    void updateTemplate() throws Exception {
        when(processTemplateService.updateTemplate(eq(template), any(MockMultipartFile.class)))
                .thenReturn("1002");

        mockMvc.perform(multipart("/api/workflow/templates/{processKey}/update", template.name())
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("1002"));
    }

    @Test
    void deleteTemplate() throws Exception {
        mockMvc.perform(delete("/api/workflow/templates/{processKey}", template.name())
                        .param("cascade", "true"))
                .andExpect(status().isOk());
    }

    @Test
    void getTemplateDefinitionId() throws Exception {
        when(processTemplateService.getTemplateDefinitionId(template))
                .thenReturn("case-create:1:1");

        mockMvc.perform(get("/api/workflow/templates/{processKey}/definition-id", template.name()))
                .andExpect(status().isOk())
                .andExpect(content().string("case-create:1:1"));
    }

    @Test
    void getTemplateXml() throws Exception {
        when(processTemplateService.getTemplateXml(template))
                .thenReturn(new ByteArrayInputStream("<definitions></definitions>".getBytes()));

        mockMvc.perform(get("/api/workflow/templates/{processKey}/xml", template.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_XML))
                .andExpect(content().string("<definitions></definitions>"));
    }

    @Test
    void getTemplateXml_NotFound() throws Exception {
        when(processTemplateService.getTemplateXml(template))
                .thenReturn(null);

        mockMvc.perform(get("/api/workflow/templates/{processKey}/xml", template.name()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTemplateDiagram() throws Exception {
        when(processTemplateService.getTemplateDiagram(template))
                .thenReturn(new ByteArrayInputStream("test".getBytes()));

        mockMvc.perform(get("/api/workflow/templates/{processKey}/diagram", template.name()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().string("test"));
    }

    @Test
    void getTemplateDiagram_NotFound() throws Exception {
        when(processTemplateService.getTemplateDiagram(template))
                .thenReturn(null);

        mockMvc.perform(get("/api/workflow/templates/{processKey}/diagram", template.name()))
                .andExpect(status().isNotFound());
    }

    @Test
    void listTemplates() throws Exception {
        List<ProcessTemplateEnum> templates = Arrays.asList(
                ProcessTemplateEnum.CASE_CREATE,
                ProcessTemplateEnum.CASE_CLOSE,
                ProcessTemplateEnum.CASE_TRANSFER,
                ProcessTemplateEnum.CASE_ARCHIVE
        );
        when(processTemplateService.listTemplates("cases"))
                .thenReturn(templates);

        mockMvc.perform(get("/api/workflow/templates")
                        .param("category", "cases"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("CASE_CREATE"))
                .andExpect(jsonPath("$[1]").value("CASE_CLOSE"))
                .andExpect(jsonPath("$[2]").value("CASE_TRANSFER"))
                .andExpect(jsonPath("$[3]").value("CASE_ARCHIVE"));
    }

    @Test
    void getTemplateForm() throws Exception {
        Map<String, Object> form = new HashMap<>();
        form.put("field1", "value1");
        form.put("field2", "value2");
        when(processTemplateService.getTemplateForm(template))
                .thenReturn(form);

        mockMvc.perform(get("/api/workflow/templates/{processKey}/form", template.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.field1").value("value1"))
                .andExpect(jsonPath("$.field2").value("value2"));
    }

    @Test
    void getTemplateNodes() throws Exception {
        Map<String, Object> nodes = new HashMap<>();
        nodes.put("node1", "value1");
        nodes.put("node2", "value2");
        when(processTemplateService.getTemplateNodes(template))
                .thenReturn(nodes);

        mockMvc.perform(get("/api/workflow/templates/{processKey}/nodes", template.name()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.node1").value("value1"))
                .andExpect(jsonPath("$.node2").value("value2"));
    }

    @Test
    void validateTemplate() throws Exception {
        when(processTemplateService.validateTemplate(any(MockMultipartFile.class)))
                .thenReturn(true);

        mockMvc.perform(multipart("/api/workflow/templates/validate")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }
} 