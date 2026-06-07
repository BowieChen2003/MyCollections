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
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long modelId;
    private float price;
    private String buyer;
    private Long buyerId;
    private String notes;
    private LocalDateTime createdTime;

}
