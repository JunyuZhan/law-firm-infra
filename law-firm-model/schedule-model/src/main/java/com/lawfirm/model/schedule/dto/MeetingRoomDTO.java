package com.lawfirm.model.schedule.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 会议室数据传输对象
 */
@Data
@Accessors(chain = true)
public class MeetingRoomDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 会议室ID
     */
    private Long id;
    
    /**
     * 会议室名称
     */
    @NotBlank(message = "会议室名称不能为空")
    @Size(max = 50, message = "会议室名称不能超过50个字符")
    private String name;
    
    /**
     * 会议室地址
     */
    @NotBlank(message = "会议室地址不能为空")
    @Size(max = 100, message = "会议室地址不能超过100个字符")
    private String address;
    
    /**
     * 会议室容量
     */
    @NotNull(message = "会议室容量不能为空")
    private Integer capacity;
    
    /**
     * 会议室设备描述
     */
    @Size(max = 500, message = "会议室设备描述不能超过500个字符")
    private String equipments;
    
    /**
     * 会议室设施
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
     * 管理员ID
     */
    private Long managerId;
    
    /**
     * 备注
     */
    @Size(max = 255, message = "备注不能超过255个字符")
    private String remarks;

    /**
     * 兼容getLocation方法，与旧代码兼容
     */
    public String getLocation() {
        return this.address;
    }
    
    /**
     * 兼容getFacilities方法，与旧代码兼容
     */
    public List<String> getFacilities() {
        return this.facilities;
    }
    
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