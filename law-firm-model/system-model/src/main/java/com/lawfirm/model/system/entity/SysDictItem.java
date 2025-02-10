package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_dict_item")
@EqualsAndHashCode(callSuper = true)
public class SysDictItem extends ModelBaseEntity<SysDictItem> {
// ... existing code ...
} 