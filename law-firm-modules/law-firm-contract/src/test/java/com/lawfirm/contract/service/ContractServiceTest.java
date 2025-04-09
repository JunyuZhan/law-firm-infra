package com.lawfirm.contract.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lawfirm.contract.service.impl.ContractServiceImpl;
import com.lawfirm.model.client.dto.ClientDTO;
import com.lawfirm.model.client.service.ClientService;
import com.lawfirm.model.contract.dto.ContractCreateDTO;
import com.lawfirm.model.contract.entity.Contract;
import com.lawfirm.model.contract.mapper.ContractMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
public class ContractServiceTest {

    @Mock
    private ClientService clientService;
    
    @Spy
    private TestContractServiceImpl contractService;
    
    // 创建一个测试专用的ContractServiceImpl子类，以便我们可以控制其行为
    public static class TestContractServiceImpl extends ContractServiceImpl {
        
        private final ContractMapper mockContractMapper;
        
        public TestContractServiceImpl() {
            super(mock(ContractMapper.class));
            this.mockContractMapper = mock(ContractMapper.class);
        }
        
        // 覆盖save方法，模拟保存操作
        @Override
        public boolean save(Contract entity) {
            if (entity != null) {
                entity.setId(1L); // 设置模拟ID
            }
            return true;
        }
        
        // 覆盖getBaseMapper方法，避免NPE异常，返回类型要与父类一致
        @Override
        public ContractMapper getBaseMapper() {
            return this.mockContractMapper;
        }
    }

    @BeforeEach
    void setUp() {
        // 使用lenient模式，避免UnnecessaryStubbingException
        MockitoAnnotations.openMocks(this);
        
        // 注入clientService
        ReflectionTestUtils.setField(contractService, "clientService", clientService);
    }

    @Test
    void createContract_WithValidClientId_ShouldSucceed() {
        // 准备测试数据
        ContractCreateDTO createDTO = new ContractCreateDTO();
        createDTO.setContractName("测试合同");
        createDTO.setClientId(1L);

        // 模拟客户服务返回
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setClientName("测试客户");
        when(clientService.getClientDetail(1L)).thenReturn(clientDTO);

        // 执行测试
        Long contractId = contractService.createContract(createDTO);

        // 验证结果
        assertNotNull(contractId);
        verify(clientService, times(1)).getClientDetail(1L);
    }

    @Test
    void createContract_WithInvalidClientId_ShouldThrowException() {
        // 准备测试数据
        ContractCreateDTO createDTO = new ContractCreateDTO();
        createDTO.setContractName("测试合同");
        createDTO.setClientId(999L);

        // 模拟客户服务返回null，表示客户不存在
        when(clientService.getClientDetail(999L)).thenReturn(null);

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> contractService.createContract(createDTO)
        );

        assertTrue(exception.getMessage().contains("客户不存在"));
        verify(clientService, times(1)).getClientDetail(999L);
    }

    @Test
    void createContract_WithNullClientId_ShouldLogWarning() {
        // 准备测试数据
        ContractCreateDTO createDTO = new ContractCreateDTO();
        createDTO.setContractName("测试合同");
        createDTO.setClientId(null);

        // 执行测试
        Long contractId = contractService.createContract(createDTO);

        // 验证结果
        assertNotNull(contractId);
        verify(clientService, never()).getClientDetail(any());
    }
} 