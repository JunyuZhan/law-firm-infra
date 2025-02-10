package com.lawfirm.model.system.entity;

import com.lawfirm.model.base.entity.ModelBaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "sys_notice_user")
@EqualsAndHashCode(callSuper = true)
public class SysNoticeUser extends ModelBaseEntity<SysNoticeUser> {
// ... existing code ...
} 