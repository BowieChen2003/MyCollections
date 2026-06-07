package com.ccb.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelDTO implements Serializable {

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

//    private Long ownerId;

    private String status;
    private String image;
    private float price;
}
