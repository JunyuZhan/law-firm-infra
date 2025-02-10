package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_oper_log")
@EqualsAndHashCode(callSuper = true)
public class SysOperLog extends ModelBaseEntity<SysOperLog> {
// ... existing code ...
} 