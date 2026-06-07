package com.ccb.backend.entity;

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
public class Order implements Serializable {

    private Long id;
    private Long modelId;
    private float price;
    private Long buyerId;
    private String notes;
    private LocalDateTime createTime;

}
