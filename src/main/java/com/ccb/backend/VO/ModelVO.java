package com.ccb.backend.VO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelVO implements Serializable {
    private Long id;
    private String modelName;
    private String brand;
    private String manufacturer;
    private String image;

    private String scale;
    private String grade;
    private String description;
    private String color;
    private String limitedEdition;
    private String shopName;
    private String ownerName;
    private String status;
    private String createTime;
    private String updateTime;
    private String updateUser;
    private float price;
}

