package com.ccb.backend.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelOnSaleVO {
    private Long id;
    private String brand;
    private String scale;
    private String manufacturer;
    private String description;
    private String color;
    private String limitedEdition;
    private Long ownerId;
    private String xianyuId;
    private String address;
    private String image;
    private String sellPrice;
}
