package com.lawfirm.finance.service.impl;

import com.lawfirm.finance.entity.Invoice;
import com.lawfirm.finance.mapper.InvoiceMapper;
import com.lawfirm.finance.repository.InvoiceRepository;
import com.lawfirm.common.core.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class InvoiceServiceImplTest {

    @Mock
    private InvoiceMapper invoiceMapper;

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice invoice;

    @BeforeEach
    void setUp() {
        invoice = new Invoice();
        invoice.setId(1L);
        invoice.setTitle("测试发票");
        invoice.setType((byte) 1);
        invoice.setAmount(new BigDecimal("1000.00"));
        invoice.setTaxRate(new BigDecimal("0.13"));
        invoice.setTaxAmount(new BigDecimal("130.00"));
        invoice.setIssueDate(LocalDateTime.now());
        invoice.setInvoiceNo("TEST123456");
        invoice.setStatus((byte) 1);
    }

    @Test
    void testCreateInvoice() {
        when(invoiceService.save(any(Invoice.class))).thenReturn(true);
        when(invoiceRepository.findByInvoiceNo(anyString())).thenReturn(null);

        Invoice result = invoiceService.createInvoice(invoice);

        assertNotNull(result);
        assertEquals("TEST123456", result.getInvoiceNo());
        verify(invoiceService, times(1)).save(any(Invoice.class));
    }

    @Test
    void testCreateInvoiceWithDuplicateNumber() {
        when(invoiceRepository.findByInvoiceNo("TEST123456")).thenReturn(invoice);

        assertThrows(BusinessException.class, () -> invoiceService.createInvoice(invoice));
        verify(invoiceService, never()).save(any(Invoice.class));
    }

    @Test
    void testVoidInvoice() {
        when(invoiceService.getById(1L)).thenReturn(invoice);
        when(invoiceRepository.save(any(Invoice.class))).thenReturn(invoice);

        assertDoesNotThrow(() -> invoiceService.voidInvoice(1L));

        verify(invoiceService, times(1)).getById(1L);
        verify(invoiceRepository, times(1)).save(any(Invoice.class));
    }

    @Test
    void testGetInvoicesByMatterId() {
        when(invoiceRepository.findByMatterId(1L)).thenReturn(Arrays.asList(invoice));

        List<Invoice> results = invoiceService.getInvoicesByMatterId(1L);

        assertNotNull(results);
        assertEquals(1, results.size());
        verify(invoiceRepository, times(1)).findByMatterId(1L);
    }

    @Test
    void testCalculateTotalInvoiceAmount() {
        when(invoiceRepository.findByMatterIdAndStatus(1L, (byte) 1)).thenReturn(Arrays.asList(invoice));

        BigDecimal total = invoiceService.calculateTotalInvoiceAmount(1L);

        assertEquals(new BigDecimal("1000.00"), total);
        verify(invoiceRepository, times(1)).findByMatterIdAndStatus(1L, (byte) 1);
    }

    @Test
    void testVoidInvoiceWithInvalidId() {
        when(invoiceService.getById(1L)).thenReturn(null);

        assertThrows(BusinessException.class, () -> invoiceService.voidInvoice(1L));
        verify(invoiceService, times(1)).getById(1L);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }

    @Test
    void testVoidInvoiceAlreadyVoided() {
        invoice.setStatus((byte) 2);
        when(invoiceService.getById(1L)).thenReturn(invoice);

        assertThrows(BusinessException.class, () -> invoiceService.voidInvoice(1L));
        verify(invoiceService, times(1)).getById(1L);
        verify(invoiceRepository, never()).save(any(Invoice.class));
    }
} 