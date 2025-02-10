package com.lawfirm.client.test;

import com.lawfirm.client.service.IClientService;
import com.lawfirm.client.vo.request.ClientAddRequest;
import com.lawfirm.client.vo.request.ClientUpdateRequest;
import com.lawfirm.client.vo.response.ClientResponse;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 客户服务单元测试
 */
@SpringBootTest
@Transactional
public class ClientServiceTest {

    @Autowired
    private IClientService clientService;

    @Test
    public void testAddClient() {
        // 准备测试数据
        ClientAddRequest request = new ClientAddRequest();
        request.setClientNo("C001");
        request.setClientName("测试客户");
        request.setClientType(1);
        request.setCertificateType(1);
        request.setCertificateNo("110101199001011234");
        request.setPhone("13800138000");
        request.setEmail("test@example.com");
        request.setStatus(1);

        // 执行添加
        Long id = clientService.addClient(request);

        // 验证结果
        assertNotNull(id);
        ClientResponse client = clientService.getClientById(id);
        assertEquals(request.getClientNo(), client.getClientNo());
        assertEquals(request.getClientName(), client.getClientName());
        assertEquals(request.getClientType(), client.getClientType());
        assertEquals(request.getCertificateType(), client.getCertificateType());
        assertEquals(request.getCertificateNo(), client.getCertificateNo());
        assertEquals(request.getPhone(), client.getPhone());
        assertEquals(request.getEmail(), client.getEmail());
        assertEquals(request.getStatus(), client.getStatus());
    }

    @Test
    public void testAddClientWithDuplicateNo() {
        // 准备测试数据
        ClientAddRequest request1 = new ClientAddRequest();
        request1.setClientNo("C002");
        request1.setClientName("测试客户1");
        request1.setClientType(1);
        request1.setCertificateType(1);
        request1.setCertificateNo("110101199001011235");
        request1.setStatus(1);

        // 第一次添加
        clientService.addClient(request1);

        // 准备重复的客户编号
        ClientAddRequest request2 = new ClientAddRequest();
        request2.setClientNo("C002");
        request2.setClientName("测试客户2");
        request2.setClientType(1);
        request2.setCertificateType(1);
        request2.setCertificateNo("110101199001011236");
        request2.setStatus(1);

        // 验证重复添加会抛出异常
        assertThrows(BusinessException.class, () -> clientService.addClient(request2));
    }

    @Test
    public void testUpdateClient() {
        // 准备测试数据
        ClientAddRequest addRequest = new ClientAddRequest();
        addRequest.setClientNo("C003");
        addRequest.setClientName("测试客户");
        addRequest.setClientType(1);
        addRequest.setCertificateType(1);
        addRequest.setCertificateNo("110101199001011237");
        addRequest.setStatus(1);

        // 添加客户
        Long id = clientService.addClient(addRequest);

        // 准备更新数据
        ClientUpdateRequest updateRequest = new ClientUpdateRequest();
        updateRequest.setId(id);
        updateRequest.setClientName("更新后的客户");
        updateRequest.setPhone("13800138001");
        updateRequest.setEmail("update@example.com");

        // 执行更新
        clientService.updateClient(updateRequest);

        // 验证结果
        ClientResponse client = clientService.getClientById(id);
        assertEquals(updateRequest.getClientName(), client.getClientName());
        assertEquals(updateRequest.getPhone(), client.getPhone());
        assertEquals(updateRequest.getEmail(), client.getEmail());
    }

    @Test
    public void testDeleteClient() {
        // 准备测试数据
        ClientAddRequest request = new ClientAddRequest();
        request.setClientNo("C004");
        request.setClientName("测试客户");
        request.setClientType(1);
        request.setCertificateType(1);
        request.setCertificateNo("110101199001011238");
        request.setStatus(1);

        // 添加客户
        Long id = clientService.addClient(request);

        // 执行删除
        clientService.deleteClient(id);

        // 验证删除后无法查询到客户
        assertThrows(BusinessException.class, () -> clientService.getClientById(id));
    }
} 