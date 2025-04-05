package com.lawfirm.model.schedule.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 会议室视图对象
 */
@Data
@Accessors(chain = true)
public class MeetingRoomVO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会议室ID
     */
    private Long id;
    
    /**
     * 会议室名称
     */
    private String name;
    
    /**
     * 会议室地址
     */
    private String address;
    
    /**
     * 会议室容量
     */
    private Integer capacity;
    
    /**
     * 会议室设备描述
     */
    private String equipments;
    
    /**
     * 会议室设施列表
     * 使用transient关键字标记，避免序列化问题
     */
    private transient List<String> facilities;
    
    /**
     * 会议室状态
     * 0: 未启用
     * 1: 启用
     * 2: 维护中
     * 3: 已废弃
     */
    private Integer status;
    
    /**
     * 状态名称
     */
    private String statusName;
    
    /**
     * 管理员ID
     */
    private Long managerId;
    
    /**
     * 管理员姓名
     */
    private String managerName;
    
    /**
     * 备注
     */
    private String remarks;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
    
    /**
     * 当前是否可用（仅在查询可用会议室时返回）
     */
    private Boolean available;
    
    /**
     * 自定义序列化方法
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        // 将facilities转换为字符串数组后序列化
        if (facilities != null) {
            out.writeObject(facilities.toArray(new String[0]));
        } else {
            out.writeObject(null);
        }
    }
    
    /**
     * 自定义反序列化方法
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        // 读取字符串数组并转换回List
        String[] facilitiesArray = (String[]) in.readObject();
        if (facilitiesArray != null) {
            facilities = new ArrayList<>(facilitiesArray.length);
            for (String facility : facilitiesArray) {
                facilities.add(facility);
            }
        } else {
            facilities = new ArrayList<>();
        }
    }
} 