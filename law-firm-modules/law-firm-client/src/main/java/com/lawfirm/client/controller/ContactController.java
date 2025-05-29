package com.lawfirm.client.controller;

import com.lawfirm.client.service.impl.ContactServiceImpl;
import com.lawfirm.model.base.controller.BaseController;
import com.lawfirm.common.core.api.CommonResult;
import com.lawfirm.model.client.dto.contact.ContactCreateDTO;
import com.lawfirm.model.client.dto.contact.ContactQueryDTO;
import com.lawfirm.model.client.entity.common.ClientContact;
import com.lawfirm.model.client.vo.ContactVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.lawfirm.client.constant.ClientConstants;
import static com.lawfirm.model.auth.constant.PermissionConstants.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * 客户联系人控制器
 */
@Tag(name = "客户联系人管理")
@Slf4j
@RestController("contactController")
@RequiredArgsConstructor
@RequestMapping(ClientConstants.API_CONTACT_PREFIX)
public class ContactController extends BaseController {

    private final ContactServiceImpl contactService;

    /**
     * 获取客户的联系人列表
     */
    @Operation(summary = "获取客户的联系人列表")
    @GetMapping("/list/{clientId}")
    @PreAuthorize("hasAuthority('" + CLIENT_VIEW + "')")
    public CommonResult<List<ContactVO>> list(@PathVariable("clientId") Long clientId) {
        return success(contactService.listClientContacts(clientId));
    }

    /**
     * 分页查询联系人
     */
    @Operation(summary = "分页查询联系人")
    @GetMapping("/page/{clientId}")
    @PreAuthorize("hasAuthority('" + CLIENT_VIEW + "')")
    public CommonResult<List<ContactVO>> page(
            @PathVariable("clientId") Long clientId,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        ContactQueryDTO queryDTO = new ContactQueryDTO();
        queryDTO.setClientId(clientId);
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        return success(contactService.listContacts(queryDTO));
    }

    /**
     * 获取联系人详情
     */
    @Operation(summary = "获取联系人详情")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + CLIENT_VIEW + "')")
    public CommonResult<ContactVO> getById(@PathVariable("id") Long id) {
        return success(contactService.getContact(id));
    }

    /**
     * 获取客户的默认联系人
     */
    @Operation(summary = "获取客户的默认联系人")
    @GetMapping("/default/{clientId}")
    @PreAuthorize("hasAuthority('" + CLIENT_VIEW + "')")
    public CommonResult<ContactVO> getDefaultContact(@PathVariable("clientId") Long clientId) {
        return success(contactService.getDefaultContact(clientId));
    }

    /**
     * 新增联系人
     */
    @Operation(summary = "新增联系人")
    @PostMapping
    @PreAuthorize("hasAuthority('" + CLIENT_CREATE + "')")
    public CommonResult<Long> add(@Validated @RequestBody ContactCreateDTO dto) {
        return success(contactService.createContact(dto));
    }

    /**
     * 修改联系人
     */
    @Operation(summary = "修改联系人")
    @PutMapping
    @PreAuthorize("hasAuthority('" + CLIENT_EDIT + "')")
    public CommonResult<Boolean> update(@Validated @RequestBody ContactCreateDTO dto) {
        contactService.updateContact(dto);
        return success(true);
    }

    /**
     * 删除联系人
     */
    @Operation(summary = "删除联系人")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('" + CLIENT_DELETE + "')")
    public CommonResult<Boolean> remove(@PathVariable("id") Long id) {
        contactService.deleteContact(id);
        return success(true);
    }

    /**
     * 设置默认联系人
     */
    @Operation(summary = "设置默认联系人")
    @PutMapping("/default/{id}")
    @PreAuthorize("hasAuthority('" + CLIENT_EDIT + "')")
    public CommonResult<Boolean> setDefault(@PathVariable("id") Long id) {
        contactService.setDefault(id, 1); // 1表示设为默认
        return success(true);
    }

    /**
     * 批量删除联系人
     */
    @Operation(summary = "批量删除联系人")
    @DeleteMapping("/batch/{ids}")
    @PreAuthorize("hasAuthority('" + CLIENT_DELETE + "')")
    public CommonResult<Boolean> batchRemove(@PathVariable("ids") Long[] ids) {
        for (Long id : ids) {
            contactService.deleteContact(id);
        }
        return success(true);
    }
}
