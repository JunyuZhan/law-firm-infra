package com.lawfirm.model.client.dto.contact;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 联系人更新DTO
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ContactUpdateDTO extends ContactCreateDTO {

    private static final long serialVersionUID = 1L;

    /**
     * 联系人ID
     */
    private Long id;
} 