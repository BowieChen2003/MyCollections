package com.ccb.backend.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVO implements Serializable {
    private Long id;
    private Long modelId;
    // 模型名称
    private String modelName;
    private float price;
    // 买家用户名
    private String username;
    private Long buyerId;
    private String notes;
    private LocalDateTime createTime;
}
