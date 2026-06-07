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
public class Model implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String modelName;
    private String brand;
    private String scale;
    private String manufacturer;
    private Integer grade;
    private String description;
    private String color;
    private String limitedEdition;
    private Long shopId;

    private Long ownerId;

    private Integer status;
    private String image;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Long updateUser;

    private float price;
    private float sellPrice;
}
