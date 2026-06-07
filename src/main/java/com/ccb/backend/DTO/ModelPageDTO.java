package com.ccb.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelPageDTO {
    private String brand;

    private Long ownerId;

    private int pageNum;

    private int pageSize;


}
